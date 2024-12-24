package cart.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import cart.domain.CartVO;

public interface CartDAO {
	
	// 장바구니 리스트 보기 // 
	List<CartVO> cartListSelectAll(int user_no) throws SQLException;
	
	// 장바구니 X버튼 눌렀을 때 한 행 삭제 //
	boolean deleteCartItem(int cart_no) throws SQLException;
	
	// 장바구니 비우기 //
	boolean CartDeleteAll(int user_no) throws SQLException;
	
	// 장바구니 등록 // 
	int insertCart(Map<String, String> paraMap) throws SQLException;
	

}
