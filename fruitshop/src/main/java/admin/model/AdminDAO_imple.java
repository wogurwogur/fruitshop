package admin.model;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
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

import org.apache.catalina.connector.Request;

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
	public List<MemberVO> MemberSelectAll(String user_id) throws SQLException{
		
		List<MemberVO> member_allList = new ArrayList<>();
		
		
		conn = ds.getConnection();
		
		String sql = " select userid, user_no, passwd, tel, role, name, birthday, email, "
				   + " postcode, address, detailaddress, extraaddress, gender, point, "
				   + " registerday, lastpwdchangedate, idle, status "
				   + " from tbl_member "
				   + " where userid != ? "
				   + " order by registerday desc ";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user_id);
			rs = pstmt.executeQuery();
			
			
			while(rs.next()) {
				MemberVO mvo = new MemberVO();
				
				mvo.setUser_no(rs.getInt("user_no"));
				mvo.setUserid(rs.getString("userid"));
				mvo.setPasswd(rs.getString("passwd"));
				mvo.setName(rs.getString("name"));
				mvo.setBirthday(rs.getString("birthday"));
				mvo.setEmail(aes.decrypt(rs.getString("email")));
				mvo.setTel(aes.decrypt(rs.getString("tel")));
				mvo.setPostcode(rs.getString("postcode"));
				mvo.setAddress(rs.getString("address"));
				mvo.setDetailaddress(rs.getString("detailaddress"));
				mvo.setExtraaddress(rs.getString("extraaddress"));
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

	
	// 한 회원의 상세정보를 확인하는 메소드
	@Override
	public MemberVO memberDetailInfo(String detail_user_no) throws SQLException {
		
		MemberVO detailMember = new MemberVO();
		
		conn = ds.getConnection();
		
		try {
			
			
			String sql = " select user_no, userid,  name, birthday, email, tel,"
					+ " postcode, address, detailaddress, extraaddress, gender, point, registerday,"
					+ " lastpwdchangedate, idle, status, role"
					+ " from tbl_member "
					+ "	where user_no = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, Integer.parseInt(detail_user_no));
			
			rs = pstmt.executeQuery();
			
			rs.next();
			
			detailMember.setUser_no(rs.getInt("user_no"));
			detailMember.setUserid(rs.getString("userid"));
			detailMember.setName(rs.getString("name"));
			detailMember.setBirthday(rs.getString("birthday"));
			detailMember.setEmail(aes.decrypt(rs.getString("email")));
			detailMember.setTel(aes.decrypt(rs.getString("tel")));
			detailMember.setPostcode(rs.getString("postcode"));
			detailMember.setAddress(rs.getString("address"));
			detailMember.setDetailaddress(rs.getString("detailaddress"));
			detailMember.setExtraaddress(rs.getString("extraaddress"));
			detailMember.setGender(rs.getString("gender"));
			detailMember.setPoint(rs.getInt("point"));
			detailMember.setRegisterday(rs.getString("registerday"));
			detailMember.setLastpwdchangedate(rs.getString("lastpwdchangedate"));
			detailMember.setIdle(rs.getInt("idle"));
			detailMember.setStatus(rs.getInt("status"));
			detailMember.setRole(rs.getInt("role"));
			
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		
		return detailMember;
	}

	
	// 관리자가 권한 부여 및 박탈하는 메소드
	@Override
	public int roleAddandRemove(String role, String user_no) throws SQLException{
		int n = 0;
		
		conn = ds.getConnection();
		
		String sql = " update tbl_member "
				   + " set role = ? "
				   + " where user_no = ? ";
		
		
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			int result_role = Integer.parseInt(role);
			
			if(result_role == 1) {
				result_role = 2;
			}else {
				result_role = 1;
			}
			
			pstmt.setInt(1, result_role);
			pstmt.setInt(2, Integer.parseInt(user_no));
			
			n = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		
		
		
		return n;
	}

	
	// 총 페이지수 알아오기
	@Override
	public int getTotalPage(Map<String, String> paraMap) throws SQLException {
		
		conn = ds.getConnection();
		
		int total = 0;
		try {
		
			String sql = " select ceil(count(*)/ 10) "
					+ " from tbl_member "
					+ " where userid != 'admin' ";
			
			String colname = paraMap.get("searchType");
			String searchWord = paraMap.get("searchWord");
			
			if("email".equals(colname)) {
				searchWord = aes.encrypt(searchWord);
				
			}
			
			if(!colname.isBlank() && !searchWord.isBlank()) {
				sql += " and "+colname +" like  '%' || ? || '%' ";
			}
		
			pstmt = conn.prepareStatement(sql);
			
			
			if(!colname.isBlank() && !searchWord.isBlank()) {
				
				pstmt.setString(1, searchWord);
				
			}
			
			rs = pstmt.executeQuery();
			
			rs.next();
			
			total = rs.getInt(1);
			
		}catch (UnsupportedEncodingException | GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		
		return total;
	}

	
	// 페이징처리를 한 모든회원 목록 보여주기
	@Override
	public List<MemberVO> select_Member_paging(Map<String, String> paraMap) throws SQLException {
		List<MemberVO> memberList = new ArrayList<>();
		
		try {
			
			conn = ds.getConnection();
			
			
			String sql = "SELECT RNO, userid, name, email, gender, user_no, address, detailaddress, extraaddress, tel, registerday "
					+ "  FROM "
					+ "  ( "
					+ "      SELECT rownum AS RNO, userid, name, email, gender, user_no, address, detailaddress, extraaddress, tel, registerday "
					+ "      FROM "
					+ "      ( "
					+ "        select userid, name, email, gender, user_no, address, detailaddress, extraaddress, tel, registerday "
					+ "        from tbl_member "
					+ "        where userid != ? ";

			
			String colname = paraMap.get("searchType");
			String searchWord = paraMap.get("searchWord");
			int sizePerPage = Integer.parseInt(paraMap.get("sizePerPage"));
			
			if("email".equals(colname)) {
				searchWord = aes.encrypt(searchWord);
				
			}
			
			//if(colname != null && !colname.trim().isEmpty()) // jdk 1.8 방식
			if(!colname.isBlank() && !searchWord.isBlank()) {	// jdk 11 이상
				// 컬럼명과 테이블명은 위치홀더(?)로 사용하면 꽝!!! 이다.
	            // 위치홀더(?)로 들어오는 것은 컬럼명과 테이블명이 아닌 오로지 데이터값만 들어온다.!!!!
				sql += " and " +colname+ " like '%'|| ? ||'%' ";
			}
			
			sql += " ) V "
					+ "  ) T "
					+ " WHERE T.RNO BETWEEN ? AND ? "
					+ " order by registerday desc ";
			
			pstmt = conn.prepareStatement(sql);
			
			// where RNO between (조회하고자하는페이지번호 * 한페이지당보여줄행의개수) - (한페이지당보여줄행의개수 - 1) and (조회하고자하는페이지번호 * 한페이지당보여줄행의개수);
			
			int currentShowPageNo = Integer.parseInt(paraMap.get("currentShowPageNo"));
			
			//if( ( colname != null && !colname.trim().isEmpty() ) && 
		    //         ( searchWord != null && !searchWord.trim().isEmpty() ) ) {
			// 검색이 있는경우
			if(!colname.isBlank() && !searchWord.isBlank()) {
				pstmt.setString(1, paraMap.get("userid"));
				pstmt.setString(2, searchWord);
				pstmt.setInt(3, (currentShowPageNo*sizePerPage) - (sizePerPage-1));	//  공
				pstmt.setInt(4, (currentShowPageNo*sizePerPage));
				
			}else {
				pstmt.setString(1, paraMap.get("userid"));
				pstmt.setInt(2, (currentShowPageNo*sizePerPage) - (sizePerPage-1));	//  공
				pstmt.setInt(3, (currentShowPageNo*sizePerPage));
			}
			
			// 검색이 없는경우
			
			
			
			
			
			rs = pstmt.executeQuery();
			
			
			while(rs.next()) {
				
				MemberVO mvo = new MemberVO();
				
				mvo.setUserid(rs.getString("userid"));
				mvo.setName(rs.getString("name"));
				mvo.setEmail(aes.decrypt(rs.getString("email")));
				mvo.setGender(rs.getString("gender"));
				mvo.setUser_no(rs.getInt("user_no"));
				mvo.setAddress(rs.getString("address"));
				mvo.setDetailaddress(rs.getString("detailaddress"));
				mvo.setExtraaddress(rs.getString("extraaddress"));
				mvo.setTel(aes.decrypt(rs.getString("tel")));
				
				memberList.add(mvo);
				
			}
			
		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		
		return memberList;
	}

	// 회원상세에서 회원의 쿠폰 개수를 볼수 있게하는 메소드
	@Override
	public String memberCouponCnt(String detail_user_no) throws SQLException {
		
		String memberCoupon = "";
		
		conn = ds.getConnection();
		
		String sql = " select C.coupon_cnt "
				+ " from "
				+ " ( "
				+ " select user_no "
				+ " from tbl_member "
				+ " where user_no = ? "
				+ " )M "
				+ " cross join "
				+ " ( "
				+ " select  count(coupon_no) as coupon_cnt "
				+ " from tbl_coupons  "
				+ " where fk_user_no = ? "
				+ " )C ";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, detail_user_no);
		pstmt.setString(2, detail_user_no);
		
		rs = pstmt.executeQuery();
		
		rs.next();
		
		memberCoupon = rs.getString(1);
		
		return memberCoupon;
	}

	
	
	
}
