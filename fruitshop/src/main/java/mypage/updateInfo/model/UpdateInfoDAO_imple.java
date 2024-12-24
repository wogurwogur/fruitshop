package mypage.updateInfo.model;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import member.domain.MemberVO;
import util.security.AES256;
import util.security.SecretMyKey;
import util.security.Sha256;

public class UpdateInfoDAO_imple implements UpdateInfoDAO {

	private DataSource ds; 
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private AES256 aes;
	
	
	public UpdateInfoDAO_imple() {
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			ds = (DataSource) envContext.lookup("jdbc/semioracle");
			aes = new AES256(SecretMyKey.KEY);

		} catch (NamingException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	} //

	
	// 자원 반납 메소드
	private void close() {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (pstmt != null) {
				pstmt.close();
				pstmt = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} //

	// 이메일 중복 검사를 처리해주는 메소드
	@Override
	public Boolean emailDuplicateCheck(String newEmail) throws SQLException {
		
		boolean result = false;
		
		try {
			conn = ds.getConnection();
			
			String sql = " select email "
					   + " from tbl_member "
					   + " where email = ? ";
			
			pstmt = conn.prepareStatement(sql); 
			pstmt.setString(1, aes.encrypt(newEmail));
	
			rs = pstmt.executeQuery();
			
			result = rs.next();
			
		} catch (UnsupportedEncodingException | SQLException | GeneralSecurityException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return result;
	}

	// 회원정보 수정 메소드
	@Override
	public int updateMember(MemberVO member) throws SQLException {
		
		int result = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql = " update tbl_member set name = ? " 
					   + "                     , email = ? "
	                   + "                     , tel = ? "
	                   + "                     , postcode = ? " 
	                   + "                     , address = ? "
	                   + "                     , detailaddress = ? "
	                   + "                     , extraaddress = ? ";
	                   
			if( !member.getPasswd().isBlank() ) {
	       		   sql += "                     , passwd = ? , lastpwdchangedate = sysdate ";
	       	}       
	                   
	               sql += " where userid = ? ";
			
			pstmt = conn.prepareStatement(sql);
	         
	        pstmt.setString(1, member.getName());
	        pstmt.setString(2, aes.encrypt(member.getEmail()) );  
	        pstmt.setString(3, aes.encrypt(member.getTel()) ); 
	        pstmt.setString(4, member.getPostcode());
	        pstmt.setString(5, member.getAddress());
	        pstmt.setString(6, member.getDetailaddress());
	        pstmt.setString(7, member.getExtraaddress());

	        if( !member.getPasswd().isBlank() ) {
	        	pstmt.setString(8, Sha256.encrypt(member.getPasswd()) );
		        pstmt.setString(9, member.getUserid());
	     
	        }
	        else {
		        pstmt.setString(8, member.getUserid());
	        }
	        
	        result = pstmt.executeUpdate();
	
	        
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return result;
	}

	// 회원 탈퇴 메소드 (status 변경)
	@Override
	public int memberWithdrawal(int user_no) throws SQLException {
		
		int result = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql = " update tbl_member set status = 0 "
					   + " where user_no = ? ";
			
			pstmt = conn.prepareStatement(sql);
	         
	        pstmt.setInt(1, user_no);
	        
	        result = pstmt.executeUpdate();
	        
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return result;
	}
	
	
	
	
	
	
	

}
