package faq.model;

import java.sql.SQLException;
import java.util.List;

import faq.domain.FaqVO;
import notice.domain.NoticeVO;

public interface FaqDAO {

	// faq 리스트를 가져오는 메소드
	List<FaqVO> faqListAll() throws SQLException;

	// faq 등록하는 메소드
	int FaqWrite(String faq_title, String faq_contents) throws SQLException;

	// 조회수를 올리는 메소드
	int setViewCountFaq(String faq_no) throws SQLException ;

	// 자주하는 질문 상세 불러오는 메소드
	FaqVO oneFaqDetail(String faq_no) throws SQLException;

	// 자주하는 질문 삭제 메소드
	int deleteFaq(String faq_no) throws SQLException;

}
