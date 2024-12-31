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

	// 장바구니에 담긴 개별 상품의 정보를 가져온다.
	Map<String, String> getCartItem(Map<String, String> paraMap) throws SQLException;

	// 주문번호 가져오기 (채번)
	int getOrderNo() throws SQLException;

	// 주문 성공 시 각 테이블에 insert
	int insertOrder(Map<String, String> paraMap) throws SQLException;

	// 쿠폰 사용 시
	int isUseCoupon(Map<String, String> paraMap) throws SQLException;

	// 회원의 주문내역을 가져온다.
	String getOrderList(Map<String, String> paraMap) throws SQLException;

	// 필터에 따른 전체 주문내역 개수
	int totalOrderCount(Map<String, String> paraMap) throws SQLException;

	// 해당 주문번호의 상세내역을 가져온다.
	List<Map<String, String>> getOrderDetail(Map<String, String> paraMap) throws SQLException;


}
