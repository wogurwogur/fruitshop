package notice.model;

import java.sql.SQLException;
import java.util.List;

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
	
	

}
