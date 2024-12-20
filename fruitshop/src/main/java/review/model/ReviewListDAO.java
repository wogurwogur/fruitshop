package review.model;

import java.sql.SQLException;
import java.util.List;

import member.domain.MemberVO;
import review.domain.ReviewListVO;

public interface ReviewListDAO {

	// 구매후기 게시글(리뷰)리스트 전부 보여주는 메소드
	List<ReviewListVO> reviewListall() throws SQLException;
	
	// 구매후기 조회수 베스트 글 보여주는 메소드
	List<ReviewListVO> breviewListall() throws SQLException;

	// 구매후기 댓글 베스트 글 보여주는 메소드
	List<ReviewListVO> creviewListall() throws SQLException;
		
	// 구매후기 글 클릭시 글 내용 보여주는 메소드
	ReviewListVO reviewReadall(String review_no) throws SQLException;




} // end of public interface ReviewListDAO {
