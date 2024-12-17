package cart.model;

import java.sql.SQLException;
import java.util.List;

import cart.domain.CartVO;

public interface CartDAO {
	
	// 장바구니 리스트 보기 // 
	List<CartVO> cartListSelectAll() throws SQLException;

}
