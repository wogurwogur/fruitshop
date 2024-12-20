package mypage.ship.model;

import java.sql.SQLException;
import java.util.List;

import mypage.ship.domain.ShipVO;

public interface ShipDAO {

	// 로그인 유저의 모든 배송지를 조회하는 메소드
	List<ShipVO> shipSelectAll(int user_no) throws SQLException;

}
