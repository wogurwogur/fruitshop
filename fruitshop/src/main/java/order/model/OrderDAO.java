package order.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import coupon.domain.CouponVO;
import mypage.ship.domain.ShipVO;
import product.domain.ProductVO;


public interface OrderDAO {

	// 해당 회원의 장바구니에 있는 품목을 가져온다
	List<Map<String, String>> getCartList(Map<String, String> paraMap) throws SQLException;

	// 해당 회원이 가지고 있는 쿠폰 목록을 가져온다
	List<CouponVO> getCouponList(Map<String, String> paraMap) throws SQLException;

	// 회원이 등록한 배송지 목록을 가져온다.
	List<ShipVO> getShipList(Map<String, String> paraMap) throws SQLException;

	// 상품 정보 및 재고를 확인한다.
	ProductVO checkProd(Map<String, String> paraMap) throws SQLException;

}
