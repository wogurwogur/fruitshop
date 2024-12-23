package coupon.model;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import util.security.AES256;
import util.security.SecretMyKey;

public class CouponDAO_imple implements CouponDAO {
	
	private DataSource ds;  // DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다.
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	   
	private AES256 aes;
	   
    // 생성자
    public CouponDAO_imple() {
      
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

    // 회원번호 및 쿠폰정보를 받아 특정 회원에게 쿠폰을 주는 메소드 
	@Override
	public int reciptCoupon(Map<String, String> paraMap) throws SQLException {
		
		int n = 0;
		
		conn = ds.getConnection();
		
		String sql = " insert into tbl_coupons(coupon_no, fk_user_no, coupon_name, coupon_descript, coupon_expire, coupon_discount) "
				+ " values(coupon_seq.nextval, ?, ?, ?, ?, ?) ";
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, paraMap.get("user_no"));
			pstmt.setString(2, paraMap.get("coupon_name"));
			pstmt.setString(3, paraMap.get("coupon_descript"));
			pstmt.setString(4, paraMap.get("coupon_expire"));
			pstmt.setInt(5, Integer.parseInt(paraMap.get("coupon_discount")));
			
			n = pstmt.executeUpdate(); 	
		}finally {
			close();
		}
		
		
		
		return n;
	}
    
    
    
	
	
}
