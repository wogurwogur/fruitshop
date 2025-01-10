package qna.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import qna.domain.QnaListVO;
public interface QnaListDAO {

	
	// qna 게시판 리스트 전부 보여주는 메소드
	List<QnaListVO> qnaListall() throws SQLException;

	// qna 게시판글 클릭시 글 내용 보여주는 메소드 사용안할듯? 페이징때문에
	QnaListVO qnaReadAll(String qna_no) throws SQLException;
	
	// qna 게시판 글 조회수 올려주는 메소드
	int setViewCount(String qna_no) throws SQLException;
	
	// qna 게시판 글 쓰기 메소드
	int qnaWrite(QnaListVO qnaWrite) throws SQLException;
	
	// qna 게시판 글쓰기 상품 등록하기 리스트 보여주기
	List<QnaListVO> oqproductFind(int fk_user_no) throws SQLException;
	
	// qna 게시판 글 삭제하는 메소드
	int qnaDelete(String qna_no) throws SQLException;
	
	// qna 글 상품등록 클릭했을때 reviewWrite로 보내주기
	QnaListVO productSelect(String prod_no) throws SQLException;

	// qna 게시판 글 수정하는 메소드
	int qnaEdit(QnaListVO qnaList) throws SQLException;

	// qna 글 페이징 처리를 위한 검색이 있는 또는 검색이 없는 회원에 대한 총페이지수 알아오기 //
	int getTotalPage(Map<String, String> paraMap) throws SQLException;

	/* >>> 뷰단(memberList.jsp)에서 "페이징 처리시 보여주는 순번 공식" 에서 사용하기 위해 
    검색이 있는 또는 검색이 없는 qna 글 총개수 알아오기 시작 <<< */
	int getTotalMemberCount(Map<String, String> paraMap) throws SQLException;

	// qna 글 페이징 처리를 한 모든 회원 목록 또는 검색되어진 회원 목록 보여주기 **** //
	List<QnaListVO> select_Member_paging(Map<String, String> paraMap) throws SQLException;
	
	// 관리자 qna 글 답글 달기
	int qnaReply(QnaListVO qnaReply) throws SQLException;

	


	

}
