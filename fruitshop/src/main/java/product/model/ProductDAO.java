package product.model;

import java.sql.SQLException;
import java.util.List;

import product.domain.ProductVO;

public interface ProductDAO {
	
	// 상품페이지 출력을 위해 상품 정보를 등록일순으로 모두 조회(select)하는 메소드
	List<ProductVO> productListSelectAll() throws SQLException;

}
