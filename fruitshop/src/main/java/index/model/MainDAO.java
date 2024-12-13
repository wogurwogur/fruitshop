package index.model;

import java.sql.SQLException;
import java.util.List;

import index.domain.MainVO;

public interface MainDAO {
	
	// 메인페이지에 보여지는 상품 이미지 파일 명을 모두 조회(select)하는 메소드
	List<MainVO> imgaeSelectAll() throws SQLException;
	
}
