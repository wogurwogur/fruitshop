package member.model;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
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

public class MemberDAO_imple implements MemberDAO {

	private DataSource ds; // DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다.
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	private AES256 aes;

	// 기본 생성자
	public MemberDAO_imple() {
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			ds = (DataSource) envContext.lookup("jdbc/semioracle");
			aes = new AES256(SecretMyKey.KEY);

		} catch (NamingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
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

	// 회원가입을 처리해주는 메소드
	@Override
	public int registerMember(MemberVO member) throws SQLException {

		int result = 0;
		
		try {
			
			conn = ds.getConnection();
			
			String sql = " insert into tbl_member(user_no, userid, passwd, name, email, tel, postcode, address, detailaddress, extraaddress, gender, birthday) "
					   + " values(user_seq.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
			
			pstmt = conn.prepareStatement(sql); 
			
			pstmt.setString(1, member.getUserid());
			pstmt.setString(2, Sha256.encrypt(member.getPasswd())); 
			pstmt.setString(3, member.getName());
			pstmt.setString(4, aes.encrypt(member.getEmail())); 
			pstmt.setString(5, aes.encrypt(member.getTel()));
			pstmt.setString(6, member.getPostcode());
			pstmt.setString(7, member.getAddress());
			pstmt.setString(8, member.getDetailaddress());
			pstmt.setString(9, member.getExtraaddress());
			pstmt.setString(10, member.getGender());
			pstmt.setString(11, member.getBirthday());

			result= pstmt.executeUpdate();
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return result;
	} //

	// 아이디 중복 검사를 처리해주는 메소드
	@Override
	public Boolean idDuplicateCheck(String userid) throws SQLException {
		
		boolean result = false;
		
		try {
			conn = ds.getConnection();
			
			String sql = " select userid "
					   + " from tbl_member "
					   + " where userid = ? ";
			
			pstmt = conn.prepareStatement(sql); 
			pstmt.setString(1, userid);
			
			rs = pstmt.executeQuery();
			
			result = rs.next();
			
		} finally {
			close();
		}
		
		return result;
	} //
	
	// 이메일 중복 검사를 처리해주는 메소드
	@Override
	public Boolean emailDuplicateCheck(String email) throws SQLException {
		boolean result = false;
		
		try {
			conn = ds.getConnection();
			
			String sql = " select email "
					   + " from tbl_member "
					   + " where email = ? ";
			
			pstmt = conn.prepareStatement(sql); 
			pstmt.setString(1, aes.encrypt(email));
	
			rs = pstmt.executeQuery();
			
			result = rs.next();
			
		} catch (UnsupportedEncodingException | SQLException | GeneralSecurityException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return result;
	} //

	// 로그인 처리해주는 메소드
	@Override
	public MemberVO login(Map<String, String> paraMap) throws SQLException {

		MemberVO member = null;

		int user_no = 0;

		try {

			String sql = " SELECT user_no " + " FROM tbl_member " + " WHERE status = 1 and userid = ? and passwd = ? ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, paraMap.get("userid"));
			pstmt.setString(2, Sha256.encrypt(paraMap.get("passwd")));

			rs = pstmt.executeQuery();
				
				
				
				if (rs.next()) {
	
					member = new MemberVO();
					member.setUser_no(rs.getInt("user_no"));
					user_no = rs.getInt("user_no");
					
					sql = " SELECT userid, name, point, pwdchangegap, "
						+ " NVL( lastlogingap, TRUNC( months_between(sysdate, registerday)) ) AS lastlogingap, "
						+ " idle, email, tel, postcode, address, detailaddress, extraaddress "
						+ " FROM "
						+ " ( "
						+ " SELECT userid, name, point, "
						+ " trunc( months_between(sysdate, lastpwdchangedate) ) AS pwdchangegap, "
						+ " registerday, idle, email, tel, postcode, address, detailaddress, extraaddress "
						+ " FROM tbl_member "
						+ " WHERE status = 1 AND user_no = ? "
						+ " ) M "
						+ " CROSS JOIN "
						+ " ( "
						+ " SELECT TRUNC( months_between(sysdate, MAX(login_date))) AS lastlogingap "
						+ " FROM tbl_loginhistory "
						+ " WHERE fk_user_no = ? "
						+ " ) H ";
					
					
					pstmt = conn.prepareStatement(sql);
	
					
					System.out.println(member.getUser_no());
					
					pstmt.setInt(1, member.getUser_no());
					pstmt.setInt(2, member.getUser_no());
	
					rs = pstmt.executeQuery();
					
					if(rs.next()) {
							 
						member.setUserid(rs.getString("userid"));
						member.setName(rs.getString("name"));
						member.setPoint(rs.getInt("point"));
						member.setIdle(rs.getInt("idle"));
						
						if( rs.getInt("lastlogingap") >= 12 ) {
								 
							member.setIdle(0);
								 
							if(rs.getInt("idle") == 1) {
						
									 
								sql = " update tbl_member set idle = 0 "
									+ " where userid = ? ";
									 
								pstmt = conn.prepareStatement(sql);
								pstmt.setString(1, paraMap.get("userid"));
									 
								pstmt.executeUpdate();
							} 
						}
							 
						
						if( rs.getInt("lastlogingap") < 12 ) {
							
			
							
							sql = " insert into tbl_loginhistory(loghis_no, fk_user_no, CLIENTIP) "
								+ " values(login_seq.nextval, ?, ?) ";
								 
				
							
							pstmt = conn.prepareStatement(sql);
							
							System.out.println(user_no);
							System.out.println(member.getUser_no());
							
							pstmt.setInt(1, member.getUser_no());
							pstmt.setString(2, paraMap.get("clientip"));
								 
							pstmt.executeUpdate();
								 
							if( rs.getInt("pwdchangegap") >= 3 ) {
									 
								member.setRequirePwdChange(true);
							}
								 
						}
						member.setEmail( aes.decrypt(rs.getString("email")) );
						member.setTel( aes.decrypt(rs.getString("tel")) );
						member.setPostcode( rs.getString("postcode") );
						member.setAddress( rs.getString("address") );
						member.setDetailaddress( rs.getString("detailaddress") );
						member.setExtraaddress( rs.getString("extraaddress") );
							 
							
					
						// 장바구니 갯수 //
						sql = " select cart_no "
							+ " from tbl_cart "
							+ " where fk_user_no = ? ";
						
						pstmt = conn.prepareStatement(sql);
		
						pstmt.setInt(1, member.getUser_no());
						
						rs = pstmt.executeQuery();
						
						int cart_cnt=0;
						while(rs.next()) {
							cart_cnt++;
						}
						
						member.setCart_cnt(cart_cnt);
						
						System.out.println(cart_cnt);
					}
					
				} // end of if(rs.next())--------------------

		} catch (GeneralSecurityException | UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return member;
	} //

	// 활동이 가능한 아이디인지 확인하는 메소드
	@Override
	public boolean isExistUser(String userid) throws SQLException {
		
		boolean bool = false;

		conn = ds.getConnection();

		String sql = " SELECT user_no "
				   + " FROM tbl_member "
				   + " WHERE status = 1 and userid = ? ";
		
		pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, userid);

		rs = pstmt.executeQuery();
		
		bool = rs.next();
		
		return bool;
	}

	// 아이디 찾기
	@Override
	public String findUserid(Map<String, String> paraMap) throws SQLException {
		String userid = null;
		
		try {
			conn = ds.getConnection();
			
			String sql = " select userid "
					   + " from tbl_member "
					   + " where status = 1 and name = ? and email = ? ";
			
			pstmt = conn.prepareStatement(sql); 
			pstmt.setString(1, paraMap.get("name"));
			pstmt.setString(2, aes.encrypt(paraMap.get("email")));
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				userid = rs.getString("userid");
			}
			
			
		} catch (UnsupportedEncodingException | SQLException | GeneralSecurityException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return userid;
	}
	
	
	
	
	// 비밀번호 찾기(아이디, 이메일을 입력받아서 해당 사용자가 존재하는지 유무를 알려준다)
	@Override
	public boolean findPwd(Map<String, String> paraMap) throws SQLException {

		boolean isUserExist = false;

		try {
			conn = ds.getConnection();

			String sql = " select userid " + " from tbl_member " + " where status = 1 and userid = ? and email = ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paraMap.get("userid"));
			pstmt.setString(2, aes.encrypt(paraMap.get("email")));

			rs = pstmt.executeQuery();

			isUserExist = rs.next();

		} catch (UnsupportedEncodingException | SQLException | GeneralSecurityException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return isUserExist;
	} ////////////////////
	
	// 비밀번호 변경하기
	@Override
	public int pwdUpdate(Map<String, String> paraMap) throws SQLException {

		int result = 0;

		try {
			conn = ds.getConnection();

			String sql = " update tbl_member set pwd = ?, lastpwdchangedate = sysdate " + " where userid = ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, Sha256.encrypt(paraMap.get("new_pwd"))); // 암호를 SHA256 알고리즘으로 단방향 암호화 시킨다.
			pstmt.setString(2, paraMap.get("userid"));

			result = pstmt.executeUpdate();

		} finally {
			close();
		}

		return result;

	}
	
	
	
	
	
	
	
} // 

