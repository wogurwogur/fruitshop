package mypage.wish.model;

import java.sql.SQLException;
import java.util.List;

import mypage.wish.domain.WishVO;

public interface WishDAO {

	// 관심상품 리스트
	List<WishVO> wishListSelectAll(int user_no) throws SQLException;

	// 관심상품 1행 삭제(X버튼 클릭시)
    boolean deleteWishItem(int wish_no) throws SQLException;
    
    // 관심 상품 비우기 
//	boolean deleteAll(int user_no) throws SQLException;
    
   
    
    
    
}
