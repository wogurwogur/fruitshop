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
					   + " where fk_user_no = ? and coupon_expire > sysdate "
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

	// 특정 회원의 현재 배송 정보를 가져오는 메소드
	@Override
	public List<Map<String, Integer>> shipStatus(int user_no) throws SQLException {
		
		List<Map<String, Integer>> shipStatusList = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
			
			String sql = " select ship_status, count(ship_status) as ship_cnt "
					   + " from "
					   + " ( "
					   + "    select distinct O.order_no, D.ship_status "
					   + "    from "
					   + "    ( "
					   + "        select fk_order_no, ship_status "
					   + "        from tbl_orderdetail "
					   + "    )D "
					   + "    join "
					   + "    ( "
					   + "    	  select order_no, fk_user_no "
					   + "    	  from tbl_order "
					   + "    )O "
					   + "    on D.fk_order_no = O.order_no "
					   + "    where O.fk_user_no = ? "
					   + " )R "
					   + " group by ship_status ";
			
			pstmt = conn.prepareStatement(sql); 
			pstmt.setInt(1, user_no);

			rs = pstmt.executeQuery();
		
			while(rs.next()) {
				
				Map<String, Integer> shipStatusMap = new HashMap<>();
				
				shipStatusMap.put("ship_status", rs.getInt("ship_status"));
				shipStatusMap.put("ship_cnt", rs.getInt("ship_cnt"));

				shipStatusList.add(shipStatusMap);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return shipStatusList;
	}
}
