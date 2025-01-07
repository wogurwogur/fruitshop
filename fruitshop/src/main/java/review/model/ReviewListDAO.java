package review.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
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
	
	// 구매후기 글 클릭시 해당 글 댓글 보여주는 메소드
	List<ReviewListVO> commentListAll(String review_no) throws SQLException;

	// 구매후기 글 페이징 처리를 위한 검색이 있는 또는 검색이 없는 회원에 대한 총페이지수 알아오기 //
	int getTotalPage(Map<String, String> paraMap) throws SQLException;

	// 구매후기 글 페이징 처리를 한 모든 회원 목록 또는 검색되어진 회원 목록 보여주기 **** //	
	List<ReviewListVO> select_Member_paging(Map<String, String> paraMap) throws SQLException;
	
	/* >>> 뷰단(memberList.jsp)에서 "페이징 처리시 보여주는 순번 공식" 에서 사용하기 위해 
    검색이 있는 또는 검색이 없는 구매후기 글 총개수 알아오기 시작 <<< */
	int getTotalMemberCount(Map<String, String> paraMap) throws SQLException;
	
	// 구매후기 글 작성하여 테이블에 인서트하기
	int reviewWrite(ReviewListVO reviewList) throws SQLException;
	
	// 구매후기 글 쓸때 상품 등록하기에서 상품리스트 보여주기 
	List<ReviewListVO> rproductFind() throws SQLException;
	
	// 구매후기 글 상품등록 클릭했을때 reviewWrite로 보내주기
	ReviewListVO productSelect(String prod_no) throws SQLException;
	
	// 구매후기 글 삭제하기
	int reviewDelete(String review_no) throws SQLException;
	
	// 구매후기 글 수정하기
	int reviewEdit(ReviewListVO reviewList) throws SQLException;
		
	// 구매후기 글 댓글 작성하는 메소드
	int commentWrite(ReviewListVO cmw) throws SQLException;
	
	// 구매후기 글 댓글 삭제하는 메소드
	int commentDelete(String comment_no, String fk_review_no, String comment_pwd) throws SQLException;

	// 구매후기 글 댓글 수정하는 메소드
	int commentEdit(ReviewListVO cmw) throws SQLException;
	
	// 구매후기 글 봤을때 조회수 올려주는 메소드 
	int setViewCount(String review_no) throws SQLException;


		

	

	
	

	
	




} // end of public interface ReviewListDAO {
