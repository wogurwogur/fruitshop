package review.model;

import java.sql.SQLException;
import java.util.List;

import review.domain.ReviewListVO;

public interface ReviewListDAO {

	// 구매후기 게시글(리뷰)리스트 전부 보여주는 메소드
	List<ReviewListVO> reviewListall() throws SQLException;

} // end of public interface ReviewListDAO {
