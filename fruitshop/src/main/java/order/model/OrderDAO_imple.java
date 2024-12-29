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

import org.json.JSONArray;
import org.json.JSONObject;

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
			
			String sql	= " SELECT coupon_no, coupon_name, TO_CHAR(coupon_expire, 'yyyy-mm-dd') AS coupon_expire, coupon_discount "
						+ "   FROM tbl_coupons "
						+ "  WHERE fk_user_no = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paraMap.get("user_no"));
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				CouponVO couponVO = new CouponVO();
				
				couponVO.setCoupon_no(rs.getInt("coupon_no"));
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

	
	// 장바구니에 담긴 개별 상품의 정보를 가져온다.
	@Override
	public Map<String, String> getCartItem(Map<String, String> paraMap) throws SQLException {
		
		Map<String, String> resultMap = null;
		
		try {
			
			conn = ds.getConnection();
			
			String sql	= " SELECT P.prod_no, A.cart_prodcount, P.prod_price, P.prod_thumnail, P.prod_name "
						+ "   FROM "
						+ "     ( "
						+ "     SELECT fk_user_no, fk_prod_no, cart_prodcount "
						+ "       FROM tbl_cart "
						+ "      WHERE fk_user_no = ? AND cart_no = ? "
						+ "     ) A  "
						+ "   JOIN tbl_products P "
						+ "     ON A.fk_prod_no = P.prod_no ";
		
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paraMap.get("user_no"));
			pstmt.setString(2, paraMap.get("cart_no"));
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				resultMap = new HashMap<>();
				
				resultMap.put("prod_no", String.valueOf(rs.getInt("prod_no")));
				resultMap.put("cart_prodcount", String.valueOf(rs.getInt("cart_prodcount")));	// 개수		
				resultMap.put("prod_price", String.valueOf(rs.getInt("prod_price")));			// 상품별 가격
				resultMap.put("prod_thumnail", rs.getString("prod_thumnail"));				
				resultMap.put("prod_name", rs.getString("prod_name"));				
			
				
			}// end of if(rs.next()) -------------------
			
		} finally {
			close();
		}
		
		return resultMap;
	}// end of public Map<String, String> getCartItem(Map<String, String> paraMap) throws SQLException -----------------

	
	// 주문번호 가져오기 (채번)
	@Override
	public int getOrderNo() throws SQLException {
		int order_no = 0;
	      
	    try {
	        conn = ds.getConnection();
	         
	        String sql = " select order_seq.nextval AS order_no "
	                   + " from dual ";
	         
	        pstmt = conn.prepareStatement(sql);
	        rs = pstmt.executeQuery();
	         
	        rs.next();
	        order_no = rs.getInt(1);
	         
	    } finally {
	    	close();
	    }
	      
	    return order_no;
	}// end of public int getOrderNo() throws SQLException -------------------- 

	
	// 주문 완료 시 테이블에 insert
	@Override
	public int insertOrder(Map<String, String> paraMap) throws SQLException {
		int result = 0;
		
		 try {
			 conn = ds.getConnection();
			 
			 conn.setAutoCommit(false);      // 수동 커밋
		     
			 // 주문 테이블 처리
			 String sql	= " INSERT INTO tbl_order(order_no, fk_user_no, order_request, order_tprice, order_postcode, order_address, order_detailaddress, order_extraadress, order_receiver) "
					 	+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ";
		         
			 pstmt = conn.prepareStatement(sql);
		        
		     pstmt.setString(1, paraMap.get("order_no"));   
		     pstmt.setInt(2, Integer.parseInt(paraMap.get("fk_user_no")));   
		     pstmt.setString(3, paraMap.get("order_request"));   
		     pstmt.setInt(4, Integer.parseInt(paraMap.get("order_tprice")));   
		     pstmt.setString(5, paraMap.get("postcode"));   
		     pstmt.setString(6, paraMap.get("address"));   
		     pstmt.setString(7, paraMap.get("detailaddress"));   
		     pstmt.setString(8, paraMap.get("extraaddress"));   
		     pstmt.setString(9, paraMap.get("order_receiver"));   
		      
		     int a = pstmt.executeUpdate();
 
		     
		     // 주문 상세 테이블 처리
		     int b = 0;
		     int prod_length = 0;
		     if (a == 1) {
		    	 sql	= " INSERT INTO tbl_orderdetail(ordetail_no, fk_order_no, fk_prod_no, ordetail_count, ordetail_price) "
		    	 		+ " VALUES (orderdetail_seq.nextval, ?, ?, ?, ?) ";
		    	 
		    	 pstmt = conn.prepareStatement(sql);
		    	 
		    	 JSONArray jsonArr = new JSONArray(paraMap.get("productArr"));
		    	 prod_length = jsonArr.length();
		    	 
		    	 for (int i=0; i<jsonArr.length(); i++) { 
		    		 JSONObject jsonObj = jsonArr.getJSONObject(i);
					
		    		 String prod_no = jsonObj.getString("prod_no");
		    		 String prod_count = jsonObj.getString("prod_count");
		    		 String prod_price = jsonObj.getString("prod_price");
					
		    		 pstmt.setString(1, paraMap.get("order_no"));
		    		 pstmt.setInt(2, Integer.parseInt(prod_no));
		    		 pstmt.setInt(3, Integer.parseInt(prod_count));
		    		 pstmt.setInt(4, Integer.parseInt(prod_price));
		    		 
		    		 b += pstmt.executeUpdate();
		    		 
		    	 }// end of for() -------------------
		    	 
		     }// end of 주문상세 ----------------------
		     
		     
		     // 결제 테이블 처리
		     if (b == prod_length) {
		    	 sql	= " INSERT INTO tbl_payments(pay_no, fk_order_no, fk_user_no) "
		    	 		+ " VALUES (payments_seq.nextval, ?, ?) ";
			    	 
		    	 pstmt = conn.prepareStatement(sql);
		    	 
		    	 pstmt.setString(1, paraMap.get("order_no"));
		    	 pstmt.setInt(2, Integer.parseInt(paraMap.get("fk_user_no")));
		     }
		     int c = pstmt.executeUpdate();
		     
		     
		     
		     // 장바구니 비우기 시작
		     if (c == 1) {
		    	 JSONArray jsonArr = new JSONArray(paraMap.get("productArr"));
		    	 
		    	 // 장바구니 상품의 개수만큼 반복 해야 한다.
		    	 for (int i=0; i<prod_length; i++) {
		    		 
		    		 // 장바구니에 상품이 있는지 조회
		    		 sql	= " SELECT * "
		    		 		+ "   FROM tbl_cart "
		    		 		+ "  WHERE fk_user_no = ? AND fk_prod_no = ? ";
		    		 
		    		 pstmt = conn.prepareStatement(sql);
		    		 
		    		 JSONObject jsonObj = jsonArr.getJSONObject(i);
		    		 String prod_no = jsonObj.getString("prod_no");
		    		 
		    		 pstmt.setInt(1, Integer.parseInt(paraMap.get("fk_user_no")));
		    		 pstmt.setInt(2, Integer.parseInt(prod_no));
		    		 
		    		 rs = pstmt.executeQuery();
		    		 
		    		 
		    		 if (rs.next()) {
		    			// 장바구니에 상품이 존재한다면
		    			 sql	= " DELETE "
			    		 		+ "   FROM tbl_cart "
			    		 		+ "  WHERE fk_user_no = ? AND fk_prod_no = ? ";
		    			 
		    			 pstmt = conn.prepareStatement(sql);
		    			 
		    			 pstmt.setInt(1, Integer.parseInt(paraMap.get("fk_user_no")));
			    		 pstmt.setInt(2, Integer.parseInt(prod_no));
			    		 
			    		 pstmt.executeUpdate();
		    		 }
		    		 
		    	 }// end of for() -----------------
		    	 
		     }// end of 장바구니 비우기 -------------
		     
		     
		     // 상품 재고 차감
		     int d = 0;
		     if (c == 1) {
		    	 d = 0;
		    	 
		    	 JSONArray jsonArr = new JSONArray(paraMap.get("productArr"));
		    	 
		    	 // 주문 상품의 개수만큼 반복 해야 한다.
		    	 for (int i=0; i<prod_length; i++) {
		    		 
		    		 // 장바구니에 상품이 있는지 조회
		    		 sql	= " UPDATE tbl_products SET prod_inventory = prod_inventory - ? "
		    		 		+ "  WHERE prod_no = ? ";
		    		 
		    		 pstmt = conn.prepareStatement(sql);
		    		 
		    		 JSONObject jsonObj = jsonArr.getJSONObject(i);
		    		 String prod_no = jsonObj.getString("prod_no");
		    		 String prod_count = jsonObj.getString("prod_count");
		    		 
		    		 pstmt.setInt(1, Integer.parseInt(prod_count));
		    		 pstmt.setInt(2, Integer.parseInt(prod_no));
		    		 
		    		 d += pstmt.executeUpdate();
		    		 
		    		 
		    	 }// end of for() -----------------
		    	 
		     }// end of 상품 재고 차감 -------------------
		     
		     
		     // 기본배송지 설정을 체크 했을 경우
		     if ("1".equals(paraMap.get("ship_default"))) {
		    	 
		    	 // 배송지 등록한 것이 있는지 조회
		    	 sql	= " SELECT count(*) "
	    		 		+ "   FROM tbl_ship "
	    		 		+ "  WHERE fk_user_no = ? ";
		    	 
		    	 pstmt = conn.prepareStatement(sql);
		    	 
		    	 pstmt.setInt(1, Integer.parseInt(paraMap.get("fk_user_no")));
		    	 
		    	 rs = pstmt.executeQuery();
		    	 
		    	 
		    	 if (rs.next()) {
		    		 // 등록한 배송지가 있다면
		    		 // 모든 기본배송지 설정을 해제
		    		 sql	= " UPDATE tbl_ship SET ship_default = 0 "
			    	 		+ " WHERE fk_user_no = ? ";
		    			
		    		 pstmt = conn.prepareStatement(sql);
		    		 
	    			 pstmt.setInt(1, Integer.parseInt(paraMap.get("fk_user_no")));
	    			 
	    			 int n = pstmt.executeUpdate();
	    			 
	    			 if (n == rs.getInt(1)) {
	    				 // 새로운 배송지를 등록
	    				 sql	= " INSERT INTO tbl_ship(ship_no, fk_user_no, ship_name, ship_postcode, ship_address, ship_detailaddress, ship_extraadress, ship_default, ship_receiver, ship_receivertel) "
	    				 		+ " VALUES (ship_seq.nextval, ?, '기본배송지', ?, ?, ?, ?, 1, ?, ?) ";
	 		    			
	 		    		 pstmt = conn.prepareStatement(sql);
	 		    		 
	 	    			 pstmt.setInt(1, Integer.parseInt(paraMap.get("fk_user_no")));
	 	    			 pstmt.setString(2, paraMap.get("postcode"));
	 	    			 pstmt.setString(3, paraMap.get("address"));
	 	    			 pstmt.setString(4, paraMap.get("detailaddress"));
	 	    			 pstmt.setString(5, paraMap.get("extraaddress"));
	 	    			 pstmt.setString(6, paraMap.get("order_receiver"));
	 	    			 pstmt.setString(7, aes.encrypt(paraMap.get("mobile")));
	 	    			 
	 	    			 pstmt.executeUpdate();
	    			 }
	    			 
		    	 }// end of if (rs.next())
		    	 
		     }// end of 기본배송지 설정
		     
		     // 회원 포인트 지급
		     if (d == prod_length) {
		    	 sql	= " UPDATE tbl_member SET point = point + ? "
		    	 		+ "  WHERE user_no = ? ";
		    	 
		    	 pstmt = conn.prepareStatement(sql);
		    	 pstmt.setInt(1, Integer.parseInt(paraMap.get("point")));
		    	 pstmt.setInt(2, Integer.parseInt(paraMap.get("fk_user_no")));
		     }
		     int e = pstmt.executeUpdate();		     
		     
		     if (e == 1) {
		    	 conn.commit();
                 result = 1;
		     }
		     
		     
		 } catch (UnsupportedEncodingException | GeneralSecurityException e) {
				e.printStackTrace();
		 } catch (SQLException e) {
			 try {
				 conn.rollback();
			 } catch (SQLException ex) {
				 throw new RuntimeException(ex);
			 }
			 e.printStackTrace();
		 } catch (Exception e) {
			 try {
				 conn.rollback();
			 } catch (SQLException ex) {
				 throw new RuntimeException(ex);
			 }
			 e.printStackTrace();
		 }
		 finally {
			 conn.setAutoCommit(true);	// Auto Commit 으로 복원시킨다.
			 close();
		 }
		
		 return result;
	}// end of public int insertOrder(Map<String, String> paraMap) throws SQLException -----------------

	
	
	@Override
	public int isUseCoupon(Map<String, String> paraMap) throws SQLException {
		int result = 0;
	      
	    try {
	    	conn = ds.getConnection();
	         
	        String sql 	= " DELETE FROM tbl_coupons "
	        			+ "  WHERE fk_user_no = ? AND coupon_no = ? ";

	        pstmt = conn.prepareStatement(sql);
	        
	        pstmt.setInt(1, Integer.parseInt(paraMap.get("fk_user_no")));
	        pstmt.setInt(2, Integer.parseInt(paraMap.get("coupon_no")));
	        
	        result = pstmt.executeUpdate();
	        
	    } finally {
	    	close();
	    }
	    return result;				
	}// end of public int insertOrderDetail(Map<String, String> paraMap) throws SQLException ------------------------

    
}
