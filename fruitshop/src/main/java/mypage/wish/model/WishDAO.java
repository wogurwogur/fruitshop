package mypage.wish.model;

import java.sql.SQLException;
import java.util.List;

import mypage.wish.domain.WishVO;

public interface WishDAO {

	// 관심상품 리스트
	List<WishVO> wishListSelectAll() throws SQLException;

}
