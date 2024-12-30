package mypage.wish.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import mypage.wish.domain.WishVO;

public interface WishDAO {

	// 관심상품 리스트
	List<WishVO> wishListSelectAll(int user_no) throws SQLException;

	// 관심상품 1행 삭제(X버튼 클릭시)
    boolean deleteWishItem(int wish_no) throws SQLException;
    
    // 관심상품 비우기
	boolean WishDeleteAll(int user_no) throws SQLException ;
	
	// 관심상품안에 같은 상품이 있는지 정보 보기
	WishVO selectproduct(Map<String, String> paraMap) throws SQLException;
	
	// 관심상품이 이미 있다면 삭제
	int deleteWish(Map<String, String> paraMap) throws SQLException;
	
	// 관심상품이 없다면 추가
	int insertWish(Map<String, String> paraMap) throws SQLException;
	
	
	int getWishCount(int user_no) throws SQLException;

	

	
	
	
	

	
    
   
    
    
    
}
