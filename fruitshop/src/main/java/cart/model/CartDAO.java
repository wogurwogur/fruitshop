package cart.model;

import java.sql.SQLException;
import java.util.List;

import cart.domain.CartVO;

public interface CartDAO {
	
	// 장바구니 리스트 보기 // 
	List<CartVO> cartListSelectAll(int user_no) throws SQLException;
	
	// 장바구니 X버튼 눌렀을 때 한 행 삭제 //
	boolean deleteCartItem(int cart_no) throws SQLException;

}
