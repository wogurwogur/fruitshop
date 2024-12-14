package admin.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface AdminDAO {
	
	// 회원의 모든 정보를 가져오는 메소드
	List<Map<String,String>> MemberSelectAll() throws SQLException;
	
}
