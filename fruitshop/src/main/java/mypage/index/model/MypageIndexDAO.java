package mypage.index.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import coupon.domain.CouponVO;

public interface MypageIndexDAO {

	// 특정 회원(회원번호)의 쿠폰 리스트를 불러오는 메소드
	List<CouponVO> couponInfo(int user_no) throws SQLException;

	// 특정 회원의 배송 건수 정보를 가져오는 메소드
	Map<String, String> shipStatus(int user_no) throws SQLException;

}
