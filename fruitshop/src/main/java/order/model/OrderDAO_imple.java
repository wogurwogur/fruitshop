package order.model;

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
import mypage.ship.domain.ShipVO;
import product.domain.ProductVO;
import util.security.AES256;
import util.security.SecretMyKey;


public class OrderDAO_imple implements OrderDAO {
	
	private DataSource ds;  // DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다.
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	   
	private AES256 aes;
	   
    // 생성자
    public OrderDAO_imple() {
      
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
    }// end of public OrderDAO_imple() ----------------

    private void close() {
	    try {
	        if(rs     != null) {rs.close();		rs=null;}
	        if(pstmt	 != null) {pstmt.close(); 	pstmt=null;}
	        if(conn	 != null) {conn.close(); 	conn=null;}
	    } catch(SQLException e) {
		    e.printStackTrace();
	    }
	   
    }// end of  private void close()---------------------------

    
    // 해당 회원의 장바구니에 있는 품목을 가져온다
	@Override
	public List<Map<String, String>> getCartList(Map<String, String> paraMap) throws SQLException {
		List<Map<String, String>> cartList = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
			
			String sql	= " SELECT P.prod_no, A.cart_prodcount, P.prod_price, P.prod_thumnail, P.prod_name "
						+ "   FROM "
						+ "     ( "
						+ "     SELECT fk_user_no, fk_prod_no, cart_prodcount "
						+ "       FROM tbl_cart "
						+ "      WHERE fk_user_no = ? "
						+ "     ) A  "
						+ "   JOIN tbl_products P "
						+ "     ON A.fk_prod_no = P.prod_no ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paraMap.get("user_no"));
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Map<String, String> resultMap = new HashMap<>();
				
				resultMap.put("prod_no", String.valueOf(rs.getInt("prod_no")));
				resultMap.put("cart_prodcount", String.valueOf(rs.getInt("cart_prodcount")));	// 개수		
				resultMap.put("prod_price", String.valueOf(rs.getInt("prod_price")));			// 상품별 가격
				resultMap.put("prod_thumnail", rs.getString("prod_thumnail"));				
				resultMap.put("prod_name", rs.getString("prod_name"));				
				
				cartList.add(resultMap);
				
			}// end of while(rs.next()) -------------------
			
		} finally {
			close();
		}
		
		return cartList;
	}// end of public List<OrderVO> getCartList(Map<String, String> paraMap) throws SQLException ------------------------ 

	
	// 해당 회원이 가지고 있는 쿠폰 목록을 가져온다
	@Override
	public List<CouponVO> getCouponList(Map<String, String> paraMap) throws SQLException {
		List<CouponVO> couponList = new ArrayList<>();
		
		try {
			
			conn = ds.getConnection();
			
			String sql	= " SELECT coupon_name, TO_CHAR(coupon_expire, 'yyyy-mm-dd') AS coupon_expire, coupon_discount "
						+ "   FROM tbl_coupons "
						+ "  WHERE fk_user_no = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paraMap.get("user_no"));
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				CouponVO couponVO = new CouponVO();
				
				couponVO.setCoupon_name(rs.getString("coupon_name"));
				couponVO.setCoupon_expire(rs.getString("coupon_expire"));
				couponVO.setCoupon_discount(rs.getInt("coupon_discount"));
				
				couponList.add(couponVO);
				
			}// end of while(rs.next()) -------------------
			
		} finally {
			close();
		}
		
		return couponList;
	}// end of public List<CouponVO> getCouponList(Map<String, String> paraMap) throws SQLException ------------------- 

	
	// 회원이 등록한 배송지 목록을 가져온다.
	@Override
	public List<ShipVO> getShipList(Map<String, String> paraMap) throws SQLException {
		
		List<ShipVO> shipList = new ArrayList<>();
		
		try {
			
			conn = ds.getConnection();
			
			String sql	= " SELECT ship_name, ship_postcode, ship_address, ship_detailaddress, ship_extraadress, ship_default, ship_receiver, ship_receivertel "
						+ "   FROM tbl_ship "
						+ "  WHERE fk_user_no = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paraMap.get("user_no"));
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ShipVO shipVO = new ShipVO();
				
				shipVO.setShip_name(rs.getString("ship_name"));
				shipVO.setShip_postcode(rs.getString("ship_postcode"));
				shipVO.setShip_address(rs.getString("ship_address"));
				shipVO.setShip_detailAddress(rs.getString("ship_detailaddress"));
				shipVO.setShip_extraAddress(rs.getString("ship_extraadress"));
				shipVO.setShip_default(rs.getInt("ship_default"));
				shipVO.setShip_receiver(rs.getString("ship_receiver"));
				shipVO.setShip_receivertel(aes.decrypt(rs.getString("ship_receivertel")));
				
				shipList.add(shipVO);
				
			}// end of while(rs.next()) -------------------
			
		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return shipList;
	}// end of public List<ShipVO> getShipList(String user_no) throws SQLException -----------------------

	
	// 상품 정보 및 재고를 확인한다.
	@Override
	public ProductVO checkProd(Map<String, String> paraMap) throws SQLException {
		ProductVO pvo = null;
		
		try {
			
			conn = ds.getConnection();
			
			String sql	= " SELECT prod_no, prod_name, prod_price, prod_thumnail, prod_inventory "
						+ "   FROM TBL_PRODUCTS "
						+ "  WHERE prod_no = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paraMap.get("prod_no"));
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				pvo = new ProductVO();
				
				pvo.setProd_no(rs.getInt("prod_no"));
				pvo.setProd_name(rs.getString("prod_name"));
				pvo.setProd_price(rs.getInt("prod_price"));
				pvo.setProd_thumnail(rs.getString("prod_thumnail"));
				pvo.setProd_inventory(rs.getInt("prod_inventory"));
				
			}// end of if(rs.next()) -------------------
			
		} finally {
			close();
		}
		
		
		return pvo;
	}// end of public ProductVO checkProd(Map<String, String> paraMap) throws SQLException -----------------------
    
    
    
    
    
}
