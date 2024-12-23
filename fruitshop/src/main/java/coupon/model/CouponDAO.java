package coupon.model;

import java.sql.SQLException;
import java.util.Map;

public interface CouponDAO {

    // 회원번호 및 쿠폰정보를 받아 특정 회원에게 쿠폰을 주는 메소드  
	int reciptCoupon(Map<String, String> paraMap) throws SQLException;

	// 모든 회원에게 쿠폰을 증정하는 메소드
	int reciptCouponAll(Map<String, String> paraMap) throws SQLException;

}
