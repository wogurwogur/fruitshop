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
	
	// 장바구니안에 같은 상품이 있는지 정보 보기 //
	CartVO selectproduct(Map<String, String> paraMap) throws SQLException;
	
	// 같은 상품 있을때 상품수량 업데이트 // 
	int updatecount(Map<String, String> paraMap) throws SQLException;
	
	// 장바구니 수량 가져오기 //
	int getcartCount(int user_no) throws SQLException;
	
	// 장바구니 수량 업데이트 //
	boolean updateCartQuantity(Map<String, String> paraMap) throws SQLException;
	

	

}
