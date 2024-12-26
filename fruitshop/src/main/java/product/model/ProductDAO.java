package product.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import product.domain.ProductVO;

public interface ProductDAO {
	
	
	// 페이징 처리를 위해 검색이 있는 경우, 검색이 없는 경우에 대한 총페이지수 알아오기 (상품 관리 페이지 10개씩)
	int getTotalPage(Map<String, String> paraMap) throws SQLException;
	
	
	// 페이징 처리한 모든 과일 목록 , 검색되어진 과일 목록 보여주기 (상품 관리 페이지)
	List<ProductVO> prdListPaging(Map<String, String> paraMap) throws SQLException;
	
	
	// 뷰단(admin_product_management.jsp)에서 "페이징 처리시 보여주는 순번 공식" 에서 사용하기 위해 검색이 있는 또는 검색이 없는 회원의 총개수 알아오기 (상품 관리 페이지)
	int getTotalProductCount(Map<String, String> paraMap) throws SQLException;
	
	
	// 페이징 처리를 위해 검색이 있는 경우, 검색이 없는 경우, 계절을 클릭 한 경우에 대한 총페이지수 알아오기 (상품 목록 페이지 16개씩)
	int getTotalPage2(Map<String, String> paraMap) throws SQLException;

	
	// 페이징 처리한 모든 과일 목록 , 검색되어진 과일목록 또는 계절 카테고리 클릭 시 과일 목록 보여주기 (상품 목록 페이지)
	List<ProductVO> prdListPaging2(Map<String, String> paraMap) throws SQLException;

	
	// 입력받은 상품 번호를 가지고 상품 상세 페이지 보여주기
	ProductVO prdDetails(String prodNo) throws SQLException;
	

//	// 페이징 처리를 위해 해당 상품 번호에 대한 후기가 있는 경우 총페이지수 알아오기
//	int getReviewTotalPage(Map<String, String> paraMap) throws SQLException;
	
	
	
	
	
	
	
	
	// 페이징 처리 한 후기 테이블에서 입력받은 상품번호에 대한 후기 리스트를 조회해온다.
	List<ProductVO> prd_reviewList(Map<String, String> paraMap) throws SQLException;

	
	// 페이징 처리 한 문의 테이블에서 입력받은 상품번호에 대한 문의 리스트를 조회해온다.
	List <ProductVO> prd_qnaList(Map<String, String> paraMap) throws SQLException;

	
	// 상품 상세페이지 내 입력받은 상품번호에 후기 수량 표시를 위해 후기 개수를 조회해온다.
	int review_cnt(String prodNo) throws SQLException;

	



	
	


	





	

}
