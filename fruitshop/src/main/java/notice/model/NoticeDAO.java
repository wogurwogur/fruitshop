package notice.model;

import java.sql.SQLException;
import java.util.List;

import notice.domain.NoticeVO;

public interface NoticeDAO {

	// 공지사항 글을 모두 가져오는 메소드
	List<NoticeVO> noticeSelectAll() throws SQLException;

}
