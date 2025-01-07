package notice.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import member.domain.MemberVO;
import notice.domain.NoticeVO;

public interface NoticeDAO {

	// 공지사항 글을 모두 가져오는 메소드
	List<NoticeVO> noticeSelectAll() throws SQLException;

	// 공지사항 글 작성하는 메소드
	int noticeInsert(String notice_title, String notice_contents) throws SQLException;

	// notice_no을 받아서 공지사항을 삭제하는 메소드
	int deleteNotice(String notice_no) throws SQLException;

	// 공지사항 정보 1개를 가져오는 메소드
	NoticeVO oneNoticeDetail(String notice_no) throws SQLException;

	// 권한을 확인후 조회수를 업데이트 하는 메소드
	int setViewCount(String notice_no) throws SQLException;

	// 공지사항을 수정하는 메소드
	int updateNotice(Map<String, String> map) throws SQLException;

	// 페이징을 위해 총 페이지 수를 계산하는 메소드
	int getTotalPage(Map<String, String> paraMap) throws SQLException;

	// 페이징처리한 리스트를 가져오는 메소드
	List<NoticeVO> select_Notice_paging(Map<String, String> paraMap) throws SQLException;
	
	

}
