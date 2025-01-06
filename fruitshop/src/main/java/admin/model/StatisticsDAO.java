package admin.model;

import java.sql.SQLException;
import java.util.Map;

public interface StatisticsDAO {

	// 최근 일주일 가입자수 통계
	Map<String, Integer> getvisitUserWeek() throws SQLException;

	// 2주전 가입자 수 통계
	Map<String, Integer> getvisitUser2Week() throws SQLException;

}
