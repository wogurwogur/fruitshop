package mypage.ship.model;

import java.sql.SQLException;
import java.util.List;

import mypage.ship.domain.ShipVO;

public interface ShipDAO {

	// 로그인 유저의 모든 배송지를 조회하는 메소드
	List<ShipVO> shipSelectAll(int user_no) throws SQLException;

	// 배송지를 추가하는 메소드
	int shipAdd(ShipVO svo) throws SQLException;

	// 배송지를 삭제해주는 메소드
	int deleteShipInfo(String ship_no) throws SQLException;

	// 특정 배송지정보를 조회하는 메소드
	ShipVO shipSelectOne(String ship_no) throws SQLException;

	// 모든 배송지를 기본배송지가 아니게 설정하는 메소드
	int noDefault(int user_no) throws SQLException;

	// 특정 배송지를 기본배송지로 변경하는 메소드
	int oneDefault(int ship_no) throws SQLException;

	// 배송지를 수정하는 메소드
	int shipUpdate(ShipVO svo) throws SQLException;

}
