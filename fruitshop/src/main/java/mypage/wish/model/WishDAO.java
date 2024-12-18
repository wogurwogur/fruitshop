package mypage.wish.model;

import java.sql.SQLException;
import java.util.List;

import mypage.wish.domain.WishVO;

public interface WishDAO {

	// 관심상품 리스트
	List<WishVO> wishListSelectAll(int user_no) throws SQLException;
	
	/*
	// 상품 특정 1개 행(데이터)만 읽어오는(select) 추상메소드(미완성메소드)
	WishVO selectOne(String wish_no) throws SQLException;
	
	// 상품 특정 1개 행 데이터 삭제(delete) 추상메소드(미완성메소드)
	int deletePerson(String wish_no) throws SQLException;
	*/

}
