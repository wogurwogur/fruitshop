package admin.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface StatisticsDAO {

	// 최근 일주일 가입자수 통계
	Map<String, Integer> getregiUserWeek() throws SQLException;

	// 2주전 가입자 수 통계
	Map<String, Integer> getregiUser2Week() throws SQLException;

	// 최근 6개월 가입자 수 통계
	Map<String, Integer> getregiUserMonth() throws SQLException;

	// 전년 동월 가입자수 통계
	Map<String, Integer> getregiUserMonthAgo() throws SQLException;

	// 최근 일주일 방문자수 통계
	Map<String, Integer> getvisitUserWeek() throws SQLException;

	// 2주전 방문자수 통계
	Map<String, Integer> getvisitUser2Week() throws SQLException;

	// 최근 6개월 방문자수 통계
	Map<String, Integer> getvisitUserMonth() throws SQLException;

	// 전년 동월 방문자수 통계
	Map<String, Integer> getvisitUser2Month() throws SQLException;

	// 최근 일주일 주문건수 통계
	Map<String, Integer> getOrderCntWeek() throws SQLException;

	// 2주전 주문건수 통계
	Map<String, Integer> getOrderCnt2Week() throws SQLException;

	// 최근 6개월 주문건수 통계
	Map<String, Integer> getOrderCntMonth() throws SQLException;

	// 전년 동월 주문건수 통계
	Map<String, Integer> getOrderCntMonthAgo() throws SQLException;

	// 최근 일주일 매출액 통계
	Map<String, Integer> getRevenueWeek() throws SQLException;

	// 2주전 매출액 통계
	Map<String, Integer> getRevenue2Week() throws SQLException;

	// 최근 6개월 매출액 통계
	Map<String, Integer> getRevenueMonth() throws SQLException;

	// 전년 동월 매출액 통계
	Map<String, Integer> getRevenueMonthAgo() throws SQLException;

	// 최근 일주일 영업이익 통계
	Map<String, Integer> getIncomeWeek() throws SQLException;

	// 2주전 영업이익 통계
	Map<String, Integer> getIncome2Week() throws SQLException;

	// 최근 6개월 영업이익 통계
	Map<String, Integer> getIncomeMonth() throws SQLException;

	// 전년 동월 영업이익 통계
	Map<String, Integer> getIncomeMonthAgo() throws SQLException;

	// 이번달 방문한 회원 순위
	List<Map<String, String>> getVisitUserRank(Map<String, String> paraMap) throws SQLException;

	// 주간 계절별 주문건수
	Map<String, Integer> getSeasonOrderWeek() throws SQLException;

	
	
	// 최근 일주일 상품별 주문건수 랭킹
//	List<Map<String, String>> getorderRankWeek() throws SQLException;

	
}
