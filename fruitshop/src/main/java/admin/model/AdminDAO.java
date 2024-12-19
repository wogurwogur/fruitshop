package admin.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import member.domain.MemberVO;

public interface AdminDAO {
	
	// 회원의 모든 정보를 가져오는 메소드
	List<MemberVO> MemberSelectAll(String user_id) throws SQLException;

	// 한 회원의 상세정보를 확인하는 메소드
	MemberVO memberDetailInfo(String detail_user_no) throws SQLException;
	
	// 관리자가 권한 부여 및 박탈하는 메소드
	int roleAddandRemove(String role, String user_no) throws SQLException;
	
	// 총 페이지수 알아오기
	int getTotalPage(Map<String, String> paraMap) throws SQLException;

	
	// 페이징처리를 한 모든회원 목록 보여주기
	List<MemberVO> select_Member_paging(Map<String, String> paraMap) throws SQLException;
	
}
