package mypage.index.model;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
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

import coupon.domain.CouponVO;
import util.security.AES256;
import util.security.SecretMyKey;

public class MypageIndexDAO_imple implements MypageIndexDAO {

	private DataSource ds;  // DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다.
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private ResultSet rs2;
	   
	private AES256 aes;
	   
    // 생성자
    public MypageIndexDAO_imple() {
      
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

    
    
    // 특정 회원(회원번호)의 쿠폰 리스트를 불러오는 메소드
	@Override
	public List<CouponVO> couponInfo(int user_no) throws SQLException {
		
		List<CouponVO> couponList = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
			
			String sql = " select coupon_no, coupon_name, coupon_descript, to_char(coupon_expire, 'yyyy-mm-dd') as coupon_expire, coupon_discount "
					   + " from tbl_coupons "
					   + " where fk_user_no = ? "
					   + " order by coupon_expire asc ";
			
			pstmt = conn.prepareStatement(sql); 
			pstmt.setInt(1, user_no);

			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				CouponVO cvo = new CouponVO();
				cvo.setCoupon_no(rs.getInt(1));
				cvo.setCoupon_name(rs.getString(2));
				cvo.setCoupon_descript(rs.getString(3));
				cvo.setCoupon_expire(rs.getString(4));
				cvo.setCoupon_discount(rs.getInt(5));
				
				couponList.add(cvo);
			}

			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return couponList;
	}

	@Override
	public Map<String, String> shipStatus(int user_no) throws SQLException {
		
		Map<String, String> shipStatus_count = new HashMap<>();
		
		int cnt_1 = 0;
		int cnt_2 = 0;
		int cnt_3 = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql = " select order_no"
					   + " from tbl_order "
					   + " where fk_user_no = ? ";
			
			pstmt = conn.prepareStatement(sql); 
			pstmt.setInt(1, user_no);

			rs = pstmt.executeQuery();
			
			String sql_2 = "";
			
			while(rs.next()) {
				
				int order_no = rs.getInt("order_no");
				
				sql_2 = " select ship_status "
					  + " from tbl_orderdetail "
					  + " where fk_order_no = ? "
					  + " group by ship_status ";
				
				pstmt = conn.prepareStatement(sql_2); 
				pstmt.setInt(1, order_no);
				
				rs2 = pstmt.executeQuery();
				
				while(rs2.next()) {
					
					if(rs2.getInt("ship_status") == 1) {
						cnt_1++;
					}
					else if(rs2.getInt("ship_status") == 2) {
						cnt_2++;
					}
					else if(rs2.getInt("ship_status") == 3) {
						cnt_3++;
					}
					
				}
			}
			
			shipStatus_count.put("cnt_1", String.valueOf(cnt_1));
			shipStatus_count.put("cnt_2", String.valueOf(cnt_2));
			shipStatus_count.put("cnt_3", String.valueOf(cnt_3));

			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		
		
		return shipStatus_count;
	}
}
