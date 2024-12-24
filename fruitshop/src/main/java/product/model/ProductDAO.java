package product.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import product.domain.ProductVO;
import review.domain.ReviewListVO;

public interface ProductDAO {
	
	
	// 페이징 처리를 위해 검색이 있는 경우, 검색이 없는 경우, 계절을 클릭 한 경우에 대한 총페이지수 알아오기
	int getTotalPage(Map<String, String> paraMap) throws SQLException;

	
	// 페이징 처리한 모든 과일 목록 , 검색되어진 과일목록 또는 계절 카테고리 클릭 시 과일 목록 보여주기 //
	List<ProductVO> prdListPaging(Map<String, String> paraMap) throws SQLException;

	
	// 입력받은 상품 번호를 가지고 상품 상세 페이지 보여주기
	ProductVO prdDetails(String prodNo) throws SQLException;

	
	// 입력받은 상품번호에 대한 reivew 리스트를 조회해온다.
	ReviewListVO reviewList(String prodNo) throws SQLException;
	

}
