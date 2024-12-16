package admin.model;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import member.domain.MemberVO;
import util.security.AES256;
import util.security.SecretMyKey;


// 회원의 모든 정보를 가져오는 메소드
public class AdminDAO_imple implements AdminDAO {

	private DataSource ds;  // DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다.
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	   
	private AES256 aes;
	   
    // 생성자
    public AdminDAO_imple() {
      
       try {
        Context initContext = new InitialContext();
        Context envContext  = (Context)initContext.lookup("java:/comp/env");
        ds = (DataSource)envContext.lookup("jdbc/semioracle");
        
        aes = new AES256(SecretMyKey.KEY);
        // SecretMyKey.KEY 은 우리가 만든 암호화/복호화 키이다.
        
       } catch(NamingException e) {
          e.printStackTrace();
       } catch(UnsupportedEncodingException e) {
    	   e.printStackTrace();
       }
    }
    
    private void close() {
 	   
        
	    try {
	        if(rs     != null) {rs.close();		rs=null;}
	        if(pstmt	 != null) {pstmt.close(); 	pstmt=null;}
	        if(conn	 != null) {conn.close(); 	conn=null;}
	    } catch(SQLException e) {
		    e.printStackTrace();
	    }
	   
    }// end of  private void close()---------------------------
    
	
    // 회원의 모든 정보를 가져오는 메소드 시작-----------------------
	@Override
	public List<MemberVO> MemberSelectAll() throws SQLException{
		
		List<MemberVO> member_allList = new ArrayList<>();
		
		conn = ds.getConnection();
		
		String sql = " select userid, user_no, passwd, tel, role, name, birthday, email, "
				   + " postcode, address, detailaddress, extraaddress, gender, point,"
				   + " registerday, lastpwdchangedate, idle, status "
				   + " from tbl_member ";
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			
			while(rs.next()) {
				MemberVO mvo = new MemberVO();
				
				mvo.setUser_no(rs.getInt("user_no"));
				mvo.setUserid(rs.getString("userid"));
				mvo.setPasswd(rs.getString("passwd"));
				mvo.setName(rs.getString("name"));
				mvo.setBirthday(rs.getString("birthday"));
				mvo.setEmail(rs.getString("email"));
				mvo.setTel(aes.decrypt(rs.getString("tel")));
				mvo.setPostcode(rs.getString("postcode"));
				mvo.setAddress(rs.getString("address"));
				mvo.setDetailaddress(rs.getString("detailaddress"));
				mvo.setExtraaddress(aes.decrypt(rs.getString("extraaddress")));
				mvo.setGender(rs.getString("gender")); 
				mvo.setPoint(rs.getInt("point")); 
				mvo.setRegisterday(rs.getString("registerday"));
				mvo.setLastpwdchangedate(rs.getString("lastpwdchangedate"));
				mvo.setIdle(rs.getInt("idle")); 
				mvo.setStatus(rs.getInt("status")); 
				mvo.setRole(rs.getInt("role")); 
				
				member_allList.add(mvo);
			
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			
			close();
			
		}
		
		
		
		
		return member_allList;
	} // 회원의 모든 정보를 가져오는 메소드 끝-----------------------

	
	
	
}
