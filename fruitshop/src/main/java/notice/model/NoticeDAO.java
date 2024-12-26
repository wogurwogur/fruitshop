package notice.model;

import java.sql.SQLException;
import java.util.List;

import notice.domain.NoticeVO;

public interface NoticeDAO {

	// 공지사항 글을 모두 가져오는 메소드
	List<NoticeVO> noticeSelectAll() throws SQLException;

	// 공지사항 글 작성하는 메소드
	int noticeInsert(String notice_title, String notice_contents) throws SQLException;

}
