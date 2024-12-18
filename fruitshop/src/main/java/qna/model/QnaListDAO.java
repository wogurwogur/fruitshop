package qna.model;

import java.sql.SQLException;
import java.util.List;
import qna.domain.QnaListVO;
public interface QnaListDAO {

	
	// 문의 게시글(리뷰)리스트 전부 보여주는 메소드
	List<QnaListVO> qnaListall() throws SQLException;

}
