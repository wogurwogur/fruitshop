package admin.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class StatisticsDAO_imple implements StatisticsDAO {
	private DataSource ds;  // DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다.
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	   
//	private AES256 aes;
	   
    // 생성자
    public StatisticsDAO_imple() {
      
       try {
        Context initContext = new InitialContext();
        Context envContext  = (Context)initContext.lookup("java:/comp/env");
        ds = (DataSource)envContext.lookup("jdbc/semioracle");
        
//        aes = new AES256(SecretMyKey.KEY);
        // SecretMyKey.KEY 은 우리가 만든 암호화/복호화 키이다.
        
       } catch(NamingException e) {
          e.printStackTrace();
       } 
//       catch(UnsupportedEncodingException e) {
//    	   e.printStackTrace();
//       }
    }// end of public OrderDAO_imple() ----------------

    private void close() {
	    try {
	        if(rs     != null) {rs.close();		rs=null;}
	        if(pstmt	 != null) {pstmt.close(); 	pstmt=null;}
	        if(conn	 != null) {conn.close(); 	conn=null;}
	    } catch(SQLException e) {
		    e.printStackTrace();
	    }
	   
    }// end of  private void close()---------------------------

    
    
    // 최근 일주일 가입자수 통계
	@Override
	public Map<String, Integer> getregiUserWeek() throws SQLException {
		
		Map<String, Integer> map = new HashMap<>();
		
		try {
			
			conn = ds.getConnection();
			
			String sql 	= " SELECT COUNT(*) AS total "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -6, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(registerday, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS before_6days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -5, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(registerday, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS before_5days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -4, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(registerday, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS before_4days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -3, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(registerday, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS before_3days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -2, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(registerday, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS before_2days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -1, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(registerday, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS before_1days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(registerday, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS today "
						+ "   FROM tbl_member "
						+ "  WHERE TO_CHAR(registerday, 'yyyy-mm-dd') BETWEEN TO_CHAR(registerday -6, 'yyyy-mm-dd') AND TO_CHAR(SYSDATE, 'yyyy-mm-dd') ";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			rs.next();
			
			map.put("total", rs.getInt("total"));
			map.put("before_6days", rs.getInt("before_6days"));
			map.put("before_5days", rs.getInt("before_5days"));
			map.put("before_4days", rs.getInt("before_4days"));
			map.put("before_3days", rs.getInt("before_3days"));
			map.put("before_2days", rs.getInt("before_2days"));
			map.put("before_1days", rs.getInt("before_1days"));
			map.put("today", rs.getInt("today"));
				
			
		} finally {
			close();
		}
		
		return map;
	}// end of public List<Map<String, String>> getvisitUserWeek() throws SQLException --------------------------- 


	// 2주전 가입자 수 통계
	@Override
	public Map<String, Integer> getregiUser2Week() throws SQLException {
		Map<String, Integer> map = new HashMap<>();
		
		try {
			
			conn = ds.getConnection();
			
			String sql 	= " SELECT COUNT(*) AS total "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -13, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(registerday, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS before_13days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -12, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(registerday, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS before_12days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -11, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(registerday, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS before_11days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -10, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(registerday, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS before_10days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -9, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(registerday, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS before_9days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -8, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(registerday, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS before_8days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -7, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(registerday, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS before_7days "
						+ "   FROM tbl_member "
						+ "  WHERE TO_CHAR(registerday, 'yyyy-mm-dd') BETWEEN TO_CHAR(registerday -13, 'yyyy-mm-dd') AND TO_CHAR(SYSDATE, 'yyyy-mm-dd') ";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			rs.next();
			
			map.put("total", rs.getInt("total"));
			map.put("before_13days", rs.getInt("before_13days"));
			map.put("before_12days", rs.getInt("before_12days"));
			map.put("before_11days", rs.getInt("before_11days"));
			map.put("before_10days", rs.getInt("before_10days"));
			map.put("before_9days", rs.getInt("before_9days"));
			map.put("before_8days", rs.getInt("before_8days"));
			map.put("before_7days", rs.getInt("before_7days"));
				
			
		} finally {
			close();
		}
		
		return map;
	}// end of public Map<String, Integer> getvisitUser2Week() throws SQLException ---------------------

	
	// 최근 6개월 가입자 수 통계
	@Override
	public Map<String, Integer> getregiUserMonth() throws SQLException {
		
		Map<String, Integer> map = new HashMap<>();
		
		try {
			
			conn = ds.getConnection();
			
			String sql 	= " SELECT COUNT(*) AS total "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -6), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(registerday, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS before_6months "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -5), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(registerday, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS before_5months "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -4), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(registerday, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS before_4months "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -3), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(registerday, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS before_3months "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -2), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(registerday, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS before_2months "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -1), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(registerday, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS before_1months "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(sysdate, 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(registerday, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS this_month "
						+ "   FROM tbl_member "
						+ "  WHERE TO_CHAR(registerday, 'yyyy-mm') BETWEEN TO_CHAR((ADD_MONTHS(registerday, -6)), 'yyyy-mm') AND TO_CHAR(SYSDATE, 'yyyy-mm') ";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			rs.next();
			
			map.put("total", rs.getInt("total"));
			map.put("before_6months", rs.getInt("before_6months"));
			map.put("before_5months", rs.getInt("before_5months"));
			map.put("before_4months", rs.getInt("before_4months"));
			map.put("before_3months", rs.getInt("before_3months"));
			map.put("before_2months", rs.getInt("before_2months"));
			map.put("before_1months", rs.getInt("before_1months"));
			map.put("this_month", rs.getInt("this_month"));
				
			
		} finally {
			close();
		}
		
		return map;
	}// end of public Map<String, Integer> getvisitUserMonth() throws SQLException --------------------- 

	
	// 전년 동월 가입자수 통계
	@Override
	public Map<String, Integer> getregiUserMonthAgo() throws SQLException {
		Map<String, Integer> map = new HashMap<>();
		
		try {
			
			conn = ds.getConnection();
			
			String sql 	= " SELECT COUNT(*) AS total "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -18), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(registerday, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS before_18months "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -17), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(registerday, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS before_17months "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -16), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(registerday, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS before_16months "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -15), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(registerday, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS before_15months "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -14), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(registerday, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS before_14months "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -13), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(registerday, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS before_13months "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -12), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(registerday, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS before_12months "
						+ "   FROM tbl_member "
						+ "  WHERE TO_CHAR(registerday, 'yyyy-mm') BETWEEN TO_CHAR((ADD_MONTHS(registerday, -18)), 'yyyy-mm') AND TO_CHAR(SYSDATE, 'yyyy-mm') ";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			rs.next();
			
			map.put("total", rs.getInt("total"));
			map.put("before_18months", rs.getInt("before_18months"));
			map.put("before_17months", rs.getInt("before_17months"));
			map.put("before_16months", rs.getInt("before_16months"));
			map.put("before_15months", rs.getInt("before_15months"));
			map.put("before_14months", rs.getInt("before_14months"));
			map.put("before_13months", rs.getInt("before_13months"));
			map.put("before_12months", rs.getInt("before_12months"));
				
			
		} finally {
			close();
		}
		
		return map;
	}// end of public Map<String, Integer> getregiUserMonthAgo() throws SQLException ------------------------- 

	
	// 최근 일주일 방문자수 통계
	@Override
	public Map<String, Integer> getvisitUserWeek() throws SQLException {
		Map<String, Integer> map = new HashMap<>();
		
		try {
			
			conn = ds.getConnection();
			
			String sql 	= " SELECT COUNT(*) AS total "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -6, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(login_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS before_6days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -5, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(login_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS before_5days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -4, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(login_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS before_4days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -3, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(login_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS before_3days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -2, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(login_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS before_2days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -1, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(login_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS before_1days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(login_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS today "
						+ "   FROM tbl_loginhistory "
						+ "  WHERE TO_CHAR(login_date, 'yyyy-mm-dd') BETWEEN TO_CHAR(login_date -6, 'yyyy-mm-dd') AND TO_CHAR(SYSDATE, 'yyyy-mm-dd') ";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			rs.next();
				
			map.put("total", rs.getInt("total"));
			map.put("before_6days", rs.getInt("before_6days"));
			map.put("before_5days", rs.getInt("before_5days"));
			map.put("before_4days", rs.getInt("before_4days"));
			map.put("before_3days", rs.getInt("before_3days"));
			map.put("before_2days", rs.getInt("before_2days"));
			map.put("before_1days", rs.getInt("before_1days"));
			map.put("today", rs.getInt("today"));
				
			
		} finally {
			close();
		}
		
		return map;
	}// end of public Map<String, Integer> getvisitUserWeek() throws SQLException ------------------------

	
	// 2주전 방문자수 통계
	@Override
	public Map<String, Integer> getvisitUser2Week() throws SQLException {
		Map<String, Integer> map = new HashMap<>();
		
		try {
			
			conn = ds.getConnection();
			
			String sql 	= " SELECT COUNT(*) AS total "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -13, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(login_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS before_13days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -12, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(login_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS before_12days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -11, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(login_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS before_11days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -10, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(login_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS before_10days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -9, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(login_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS before_9days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -8, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(login_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS before_8days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -7, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(login_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS before_7days "
						+ "   FROM tbl_loginhistory "
						+ "  WHERE TO_CHAR(login_date, 'yyyy-mm-dd') BETWEEN TO_CHAR(login_date -13, 'yyyy-mm-dd') AND TO_CHAR(SYSDATE, 'yyyy-mm-dd') ";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			rs.next();
				
			map.put("total", rs.getInt("total"));
			map.put("before_13days", rs.getInt("before_13days"));
			map.put("before_12days", rs.getInt("before_12days"));
			map.put("before_11days", rs.getInt("before_11days"));
			map.put("before_10days", rs.getInt("before_10days"));
			map.put("before_9days", rs.getInt("before_9days"));
			map.put("before_8days", rs.getInt("before_8days"));
			map.put("before_7days", rs.getInt("before_7days"));
				
			
		} finally {
			close();
		}
		
		return map;
	}// end of public Map<String, Integer> getvisitUser2Week() throws SQLException --------------------

	
	// 최근 6개월 방문자수 통계
	@Override
	public Map<String, Integer> getvisitUserMonth() throws SQLException {
		Map<String, Integer> map = new HashMap<>();
		
		try {
			
			conn = ds.getConnection();
			
			String sql 	= " SELECT COUNT(*) AS total "
						+ "     , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -6), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(login_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS before_6months "
						+ "     , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -5), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(login_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS before_5months "
						+ "     , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -4), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(login_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS before_4months "
						+ "     , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -3), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(login_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS before_3months "
						+ "     , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -2), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(login_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS before_2months "
						+ "     , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -1), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(login_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS before_1months "
						+ "     , SUM(CASE WHEN TO_DATE(TO_CHAR(sysdate, 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(login_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS this_month "
						+ "  FROM tbl_loginhistory "
						+ " WHERE TO_CHAR(login_date, 'yyyy-mm') BETWEEN TO_CHAR((ADD_MONTHS(login_date, -6)), 'yyyy-mm') AND TO_CHAR(SYSDATE, 'yyyy-mm') ";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			rs.next();
				
			map.put("total", rs.getInt("total"));
			map.put("before_6months", rs.getInt("before_6months"));
			map.put("before_5months", rs.getInt("before_5months"));
			map.put("before_4months", rs.getInt("before_4months"));
			map.put("before_3months", rs.getInt("before_3months"));
			map.put("before_2months", rs.getInt("before_2months"));
			map.put("before_1months", rs.getInt("before_1months"));
			map.put("this_month", rs.getInt("this_month"));
				
			
		} finally {
			close();
		}
		
		return map;
	}// end of public Map<String, Integer> getvisitUserMonth() throws SQLException ------------------
	
	// 전년 동월 방문자수 통계
	@Override
	public Map<String, Integer> getvisitUser2Month() throws SQLException {
		Map<String, Integer> map = new HashMap<>();
		
		try {
			
			conn = ds.getConnection();
			
			String sql 	= " SELECT COUNT(*) AS total "
						+ "     , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -18), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(login_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS before_18months "
						+ "     , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -17), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(login_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS before_17months "
						+ "     , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -16), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(login_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS before_16months "
						+ "     , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -15), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(login_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS before_15months "
						+ "     , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -14), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(login_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS before_14months "
						+ "     , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -13), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(login_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS before_13months "
						+ "     , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -12), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(login_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS before_12months "
						+ "  FROM tbl_loginhistory "
						+ " WHERE TO_CHAR(login_date, 'yyyy-mm') BETWEEN TO_CHAR((ADD_MONTHS(login_date, -18)), 'yyyy-mm') AND TO_CHAR(SYSDATE, 'yyyy-mm') ";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			rs.next();
				
			map.put("total", rs.getInt("total"));
			map.put("before_18months", rs.getInt("before_18months"));
			map.put("before_17months", rs.getInt("before_17months"));
			map.put("before_16months", rs.getInt("before_16months"));
			map.put("before_15months", rs.getInt("before_15months"));
			map.put("before_14months", rs.getInt("before_14months"));
			map.put("before_13months", rs.getInt("before_13months"));
			map.put("before_12months", rs.getInt("before_12months"));
				
			
		} finally {
			close();
		}
		
		return map;
	}// end of public Map<String, Integer> getvisitUser2Month() throws SQLException --------------------

	
	
	// 최근 일주일 주문건수 통계
	@Override
	public Map<String, Integer> getOrderCntWeek() throws SQLException {
		Map<String, Integer> map = new HashMap<>();
		
		try {
			
			conn = ds.getConnection();
			
			String sql 	= " SELECT COUNT(*) AS total "
						+ "     , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -6, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS before_6days "
						+ "     , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -5, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS before_5days "
						+ "     , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -4, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS before_4days "
						+ "     , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -3, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS before_3days "
						+ "     , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -2, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS before_2days "
						+ "     , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -1, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS before_1days "
						+ "     , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS today "
						+ "  FROM tbl_order "
						+ " WHERE TO_CHAR(order_date, 'yyyy-mm-dd') BETWEEN TO_CHAR(order_date -6, 'yyyy-mm-dd') AND TO_CHAR(SYSDATE, 'yyyy-mm-dd') ";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			rs.next();
				
			map.put("total", rs.getInt("total"));
			map.put("before_6days", rs.getInt("before_6days"));
			map.put("before_5days", rs.getInt("before_5days"));
			map.put("before_4days", rs.getInt("before_4days"));
			map.put("before_3days", rs.getInt("before_3days"));
			map.put("before_2days", rs.getInt("before_2days"));
			map.put("before_1days", rs.getInt("before_1days"));
			map.put("today", rs.getInt("today"));
				
			
		} finally {
			close();
		}
		
		return map;
	}// end of public Map<String, Integer> getOrderCntWeek() throws SQLException

	
	// 2주전 주문건수 통계
	@Override
	public Map<String, Integer> getOrderCnt2Week() throws SQLException {
		Map<String, Integer> map = new HashMap<>();
		
		try {
			
			conn = ds.getConnection();
			
			String sql 	= " SELECT COUNT(*) AS total "
						+ "     , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -13, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS before_13days "
						+ "     , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -12, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS before_12days "
						+ "     , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -11, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS before_11days "
						+ "     , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -10, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS before_10days "
						+ "     , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -9, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS before_9days "
						+ "     , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -8, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS before_8days "
						+ "     , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -7, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS before_7days "
						+ "  FROM tbl_order "
						+ " WHERE TO_CHAR(order_date, 'yyyy-mm-dd') BETWEEN TO_CHAR(order_date -13, 'yyyy-mm-dd') AND TO_CHAR(SYSDATE, 'yyyy-mm-dd') ";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			rs.next();
				
			map.put("total", rs.getInt("total"));
			map.put("before_13days", rs.getInt("before_13days"));
			map.put("before_12days", rs.getInt("before_12days"));
			map.put("before_11days", rs.getInt("before_11days"));
			map.put("before_10days", rs.getInt("before_10days"));
			map.put("before_9days", rs.getInt("before_9days"));
			map.put("before_8days", rs.getInt("before_8days"));
			map.put("before_7days", rs.getInt("before_7days"));
				
			
		} finally {
			close();
		}
		
		return map;
	}// end of public Map<String, Integer> getOrderCnt2Week() throws SQLException -------------------- 


	// 최근 6개월 주문건수 통계
	@Override
	public Map<String, Integer> getOrderCntMonth() throws SQLException {
		Map<String, Integer> map = new HashMap<>();
		
		try {
			
			conn = ds.getConnection();
			
			String sql 	= " SELECT COUNT(*) AS total "
						+ "     , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -6), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS before_6months "
						+ "     , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -5), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS before_5months "
						+ "     , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -4), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS before_4months "
						+ "     , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -3), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS before_3months "
						+ "     , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -2), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS before_2months "
						+ "     , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -1), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS before_1months "
						+ "     , SUM(CASE WHEN TO_DATE(TO_CHAR(sysdate, 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS this_month "
						+ "  FROM tbl_order "
						+ " WHERE TO_CHAR(order_date, 'yyyy-mm') BETWEEN TO_CHAR((ADD_MONTHS(order_date, -6)), 'yyyy-mm') AND TO_CHAR(SYSDATE, 'yyyy-mm') ";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			rs.next();
				
			map.put("total", rs.getInt("total"));
			map.put("before_6months", rs.getInt("before_6months"));
			map.put("before_5months", rs.getInt("before_5months"));
			map.put("before_4months", rs.getInt("before_4months"));
			map.put("before_3months", rs.getInt("before_3months"));
			map.put("before_2months", rs.getInt("before_2months"));
			map.put("before_1months", rs.getInt("before_1months"));
			map.put("this_month", rs.getInt("this_month"));
				
			
		} finally {
			close();
		}
		
		return map;
	}// end of public Map<String, Integer> getOrderCntMonth() throws SQLException ------------------------

	
	// 전년 동월 주문건수 통계
	@Override
	public Map<String, Integer> getOrderCntMonthAgo() throws SQLException {
		Map<String, Integer> map = new HashMap<>();
		
		try {
			
			conn = ds.getConnection();
			
			String sql 	= " SELECT COUNT(*) AS total "
					+ "     , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -18), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS before_18months "
					+ "     , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -17), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS before_17months "
					+ "     , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -16), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS before_16months "
					+ "     , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -15), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS before_15months "
					+ "     , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -14), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS before_14months "
					+ "     , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -13), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS before_13months "
					+ "     , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -12), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS before_12months "
					+ "  FROM tbl_order "
					+ " WHERE TO_CHAR(order_date, 'yyyy-mm') BETWEEN TO_CHAR((ADD_MONTHS(order_date, -18)), 'yyyy-mm') AND TO_CHAR(SYSDATE, 'yyyy-mm') ";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			rs.next();
				
			map.put("total", rs.getInt("total"));
			map.put("before_18months", rs.getInt("before_18months"));
			map.put("before_17months", rs.getInt("before_17months"));
			map.put("before_16months", rs.getInt("before_16months"));
			map.put("before_15months", rs.getInt("before_15months"));
			map.put("before_14months", rs.getInt("before_14months"));
			map.put("before_13months", rs.getInt("before_13months"));
			map.put("before_12months", rs.getInt("before_12months"));
				
			
		} finally {
			close();
		}
		
		return map;
	}// end of public Map<String, Integer> getOrderCntMonthAgo() throws SQLException ------------------------- 

	
	// 최근 일주일 매출액 통계
	@Override
	public Map<String, Integer> getRevenueWeek() throws SQLException {
		Map<String, Integer> map = new HashMap<>();
		
		try {
			
			conn = ds.getConnection();
			
			String sql 	= " SELECT sum(order_tprice) AS total "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -6, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN order_tprice ELSE 0 END) AS before_6days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -5, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN order_tprice ELSE 0 END) AS before_5days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -4, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN order_tprice ELSE 0 END) AS before_4days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -3, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN order_tprice ELSE 0 END) AS before_3days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -2, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN order_tprice ELSE 0 END) AS before_2days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -1, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN order_tprice ELSE 0 END) AS before_1days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN order_tprice ELSE 0 END) AS today "
						+ "   FROM tbl_order "
						+ "  WHERE TO_CHAR(order_date, 'yyyy-mm-dd') BETWEEN TO_CHAR(order_date -6, 'yyyy-mm-dd') AND TO_CHAR(SYSDATE, 'yyyy-mm-dd') ";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			rs.next();
				
			map.put("total", rs.getInt("total"));
			map.put("before_6days", rs.getInt("before_6days"));
			map.put("before_5days", rs.getInt("before_5days"));
			map.put("before_4days", rs.getInt("before_4days"));
			map.put("before_3days", rs.getInt("before_3days"));
			map.put("before_2days", rs.getInt("before_2days"));
			map.put("before_1days", rs.getInt("before_1days"));
			map.put("today", rs.getInt("today"));
				
			
		} finally {
			close();
		}
		
		return map;
	}// end of public Map<String, Integer> getRevenueWeek() throws SQLException ------------------------- 

	
	// 2주전 매출액 통계
	@Override
	public Map<String, Integer> getRevenue2Week() throws SQLException {
		Map<String, Integer> map = new HashMap<>();
		
		try {
			
			conn = ds.getConnection();
			
			String sql 	= " SELECT sum(order_tprice) AS total "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -13, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN order_tprice ELSE 0 END) AS before_13days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -12, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN order_tprice ELSE 0 END) AS before_12days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -11, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN order_tprice ELSE 0 END) AS before_11days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -10, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN order_tprice ELSE 0 END) AS before_10days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -9, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN order_tprice ELSE 0 END) AS before_9days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -8, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN order_tprice ELSE 0 END) AS before_8days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -7, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN order_tprice ELSE 0 END) AS before_7days "
						+ "   FROM tbl_order "
						+ "  WHERE TO_CHAR(order_date, 'yyyy-mm-dd') BETWEEN TO_CHAR(order_date -13, 'yyyy-mm-dd') AND TO_CHAR(SYSDATE, 'yyyy-mm-dd') ";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			rs.next();
				
			map.put("total", rs.getInt("total"));
			map.put("before_13days", rs.getInt("before_13days"));
			map.put("before_12days", rs.getInt("before_12days"));
			map.put("before_11days", rs.getInt("before_11days"));
			map.put("before_10days", rs.getInt("before_10days"));
			map.put("before_9days", rs.getInt("before_9days"));
			map.put("before_8days", rs.getInt("before_8days"));
			map.put("before_7days", rs.getInt("before_7days"));
				
			
		} finally {
			close();
		}
		
		return map;
	}// end of public Map<String, Integer> getRevenue2Week() throws SQLException --------------------

	
	// 최근 6개월 주문건수 통계
	@Override
	public Map<String, Integer> getRevenueMonth() throws SQLException {
		Map<String, Integer> map = new HashMap<>();
		
		try {
			
			conn = ds.getConnection();
			
			String sql 	= " SELECT sum(order_tprice) AS total "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -6), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN order_tprice ELSE 0 END) AS before_6months "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -5), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN order_tprice ELSE 0 END) AS before_5months "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -4), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN order_tprice ELSE 0 END) AS before_4months "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -3), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN order_tprice ELSE 0 END) AS before_3months "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -2), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN order_tprice ELSE 0 END) AS before_2months "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -1), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN order_tprice ELSE 0 END) AS before_1months "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(sysdate, 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN order_tprice ELSE 0 END) AS this_month "
						+ "   FROM tbl_order "
						+ "  WHERE TO_CHAR(order_date, 'yyyy-mm') BETWEEN TO_CHAR((ADD_MONTHS(order_date, -6)), 'yyyy-mm') AND TO_CHAR(SYSDATE, 'yyyy-mm') ";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			rs.next();
				
			map.put("total", rs.getInt("total"));
			map.put("before_6months", rs.getInt("before_6months"));
			map.put("before_5months", rs.getInt("before_5months"));
			map.put("before_4months", rs.getInt("before_4months"));
			map.put("before_3months", rs.getInt("before_3months"));
			map.put("before_2months", rs.getInt("before_2months"));
			map.put("before_1months", rs.getInt("before_1months"));
			map.put("this_month", rs.getInt("this_month"));
				
			
		} finally {
			close();
		}
		
		return map;
	}// end of public Map<String, Integer> getRevenueMonth() throws SQLException ---------------------- 

	
	// 전년 동월 주문건수 통계
	@Override
	public Map<String, Integer> getRevenueMonthAgo() throws SQLException {
		Map<String, Integer> map = new HashMap<>();
		
		try {
			
			conn = ds.getConnection();
			
			String sql 	= " SELECT sum(order_tprice) AS total "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -18), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN order_tprice ELSE 0 END) AS before_18months "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -17), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN order_tprice ELSE 0 END) AS before_17months "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -16), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN order_tprice ELSE 0 END) AS before_16months "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -15), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN order_tprice ELSE 0 END) AS before_15months "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -14), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN order_tprice ELSE 0 END) AS before_14months "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -13), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN order_tprice ELSE 0 END) AS before_13months "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -12), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN order_tprice ELSE 0 END) AS before_12months "
						+ "   FROM tbl_order "
						+ "  WHERE TO_CHAR(order_date, 'yyyy-mm') BETWEEN TO_CHAR((ADD_MONTHS(order_date, -18)), 'yyyy-mm') AND TO_CHAR(SYSDATE, 'yyyy-mm') ";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			rs.next();
				
			map.put("total", rs.getInt("total"));
			map.put("before_18months", rs.getInt("before_18months"));
			map.put("before_17months", rs.getInt("before_17months"));
			map.put("before_16months", rs.getInt("before_16months"));
			map.put("before_15months", rs.getInt("before_15months"));
			map.put("before_14months", rs.getInt("before_14months"));
			map.put("before_13months", rs.getInt("before_13months"));
			map.put("before_12months", rs.getInt("before_12months"));
				
			
		} finally {
			close();
		}
		
		return map;
	}// end of public Map<String, Integer> getRevenueMonthAgo() throws SQLException ----------------------- 

	
	
    
}
