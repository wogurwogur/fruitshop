package admin.model;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
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

import util.security.AES256;
import util.security.SecretMyKey;

public class StatisticsDAO_imple implements StatisticsDAO {
	private DataSource ds;  // DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다.
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	   
	private AES256 aes;
	   
    // 생성자
    public StatisticsDAO_imple() {
      
       try {
        Context initContext = new InitialContext();
        Context envContext  = (Context)initContext.lookup("java:/comp/env");
        ds = (DataSource)envContext.lookup("jdbc/semioracle");
        
        aes = new AES256(SecretMyKey.KEY);
        // SecretMyKey.KEY 은 우리가 만든 암호화/복호화 키이다.
        
       } catch(NamingException e) {
          e.printStackTrace();
       } 
       catch(UnsupportedEncodingException e) {
    	   e.printStackTrace();
       }
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

	
	// 최근 6개월 매출액 통계
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

	
	// 전년 동월 매출액 통계
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

	
	// 최근 일주일 영업이익 통계
	@Override
	public Map<String, Integer> getIncomeWeek() throws SQLException {
		Map<String, Integer> map = new HashMap<>();
		
		try {
			
			conn = ds.getConnection();
			
			String sql 	= " SELECT sum(income) AS total "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -6, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN income ELSE 0 END) AS before_6days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -5, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN income ELSE 0 END) AS before_5days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -4, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN income ELSE 0 END) AS before_4days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -3, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN income ELSE 0 END) AS before_3days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -2, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN income ELSE 0 END) AS before_2days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -1, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN income ELSE 0 END) AS before_1days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN income ELSE 0 END) AS today "
						+ "   FROM (SELECT o.order_date, od.fk_prod_no, od.ordetail_price, od.ordetail_count, p.prod_cost, od.ordetail_price - (p.prod_cost * od.ordetail_count) AS income "
						+ "		  	  FROM tbl_order o JOIN tbl_orderdetail od "
						+ "				ON o.order_no = od.fk_order_no "
						+ "		  	  JOIN tbl_products p "
						+ "		    	ON p.prod_no = od.fk_prod_no) "
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
	}// end of public Map<String, Integer> getIncomeWeek() throws SQLException ---------------------

	
	// 2주전 영업이익 통계
	@Override
	public Map<String, Integer> getIncome2Week() throws SQLException {
		Map<String, Integer> map = new HashMap<>();
		
		try {
			
			conn = ds.getConnection();
			
			String sql 	= " SELECT sum(income) AS total "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -13, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN income ELSE 0 END) AS before_13days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -12, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN income ELSE 0 END) AS before_12days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -11, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN income ELSE 0 END) AS before_11days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -10, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN income ELSE 0 END) AS before_10days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -9, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN income ELSE 0 END) AS before_9days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -8, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN income ELSE 0 END) AS before_8days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -7, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN income ELSE 0 END) AS before_7days "
						+ "   FROM (SELECT o.order_date, od.fk_prod_no, od.ordetail_price, od.ordetail_count, p.prod_cost, od.ordetail_price - (p.prod_cost * od.ordetail_count) AS income "
						+ "		  	  FROM tbl_order o JOIN tbl_orderdetail od "
						+ "				ON o.order_no = od.fk_order_no "
						+ "		  	  JOIN tbl_products p "
						+ "		    	ON p.prod_no = od.fk_prod_no) "
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
	}// end of public Map<String, Integer> getIncome2Week() throws SQLException ---------------------- 

	
	// 최근 6개월 영업이익 통계
	@Override
	public Map<String, Integer> getIncomeMonth() throws SQLException {
		Map<String, Integer> map = new HashMap<>();
		
		try {
			
			conn = ds.getConnection();
			
			String sql 	= " SELECT sum(income) AS total "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -6), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN income ELSE 0 END) AS before_6months "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -5), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN income ELSE 0 END) AS before_5months "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -4), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN income ELSE 0 END) AS before_4months "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -3), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN income ELSE 0 END) AS before_3months "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -2), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN income ELSE 0 END) AS before_2months "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -1), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN income ELSE 0 END) AS before_1months "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(sysdate, 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN income ELSE 0 END) AS this_month "
						+ "   FROM (SELECT o.order_date, od.fk_prod_no, od.ordetail_price, od.ordetail_count, p.prod_cost, od.ordetail_price - (p.prod_cost * od.ordetail_count) AS income "
						+ "		  	  FROM tbl_order o JOIN tbl_orderdetail od "
						+ "				ON o.order_no = od.fk_order_no "
						+ "		  	  JOIN tbl_products p "
						+ "		    	ON p.prod_no = od.fk_prod_no) "
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
	}// end of public Map<String, Integer> getIncomeMonth() throws SQLException --------------- 

	// 전년 동월 매출액 통계
	@Override
	public Map<String, Integer> getIncomeMonthAgo() throws SQLException {
		Map<String, Integer> map = new HashMap<>();
		
		try {
			
			conn = ds.getConnection();
			
			String sql 	= " SELECT sum(income) AS total "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -18), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN income ELSE 0 END) AS before_18months "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -17), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN income ELSE 0 END) AS before_17months "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -16), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN income ELSE 0 END) AS before_16months "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -15), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN income ELSE 0 END) AS before_15months "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -14), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN income ELSE 0 END) AS before_14months "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -13), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN income ELSE 0 END) AS before_13months "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -12), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN income ELSE 0 END) AS before_12months "
						+ "   FROM (SELECT o.order_date, od.fk_prod_no, od.ordetail_price, od.ordetail_count, p.prod_cost, od.ordetail_price - (p.prod_cost * od.ordetail_count) AS income "
						+ "		  	  FROM tbl_order o JOIN tbl_orderdetail od "
						+ "				ON o.order_no = od.fk_order_no "
						+ "		  	  JOIN tbl_products p "
						+ "		    	ON p.prod_no = od.fk_prod_no) "
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
	}// end of public Map<String, Integer> getIncomeMonthAgo() throws SQLException  -----------------------

	
	// 이번달 방문한 회원 순위
	@Override
	public List<Map<String, String>> getVisitUserRank(Map<String, String> paraMap) throws SQLException {
		List<Map<String, String>> loginRankList = new ArrayList<>(); 
		
		
		try {
			conn = ds.getConnection();
			
			String sql 	= " select m.user_no, m.name, A.visit_cnt, m.tel, m.email "
						+ "   from "
						+ " 	( "
						+ " 	select fk_user_no, count(*) AS visit_cnt "
						+ "  	  from tbl_loginhistory "
						+ "  	 where TO_CHAR(login_date, 'yyyy-mm') = TO_CHAR((add_months(SYSDATE, -?)), 'yyyy-mm') "
						+ " 	group by fk_user_no "
						+ " 	) A JOIN tbl_member m "
						+ " 	ON A.fk_user_no = m.user_no "
						+ "  where m.role = 1 AND m.status = 1 and rownum <= 10 "
						+ " order by visit_cnt "+paraMap.get("searchRange");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paraMap.get("searchMonth"));
//			pstmt.setString(2, paraMap.get("searchRange"));
//			order by 절에 는 위치홀더 사용불가..
			
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Map<String, String> map = new HashMap<>();
				
				map.put("user_no", rs.getString("user_no"));
				map.put("name", rs.getString("name"));
				map.put("visit_cnt", rs.getString("visit_cnt"));
				map.put("tel", aes.decrypt(rs.getString("tel")));
				map.put("email", aes.decrypt(rs.getString("email")));
				
				loginRankList.add(map);
				
			}// end of while (rs.next()) --------------------- 
			
		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return loginRankList;
	}// end of public List<Map<String, String>> getVisitUserRank() throws SQLException  ----------------------- 

	
	// 주간 계절별 주문건수
	@Override
	public Map<String, Integer> getSeasonOrderWeek() throws SQLException {
		Map<String, Integer> map = new HashMap<>();
		
		try {
			
			conn = ds.getConnection();
			
			// 봄상품 주문건
			String sql 	= " SELECT count(*) AS total "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -6, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS spring_before_6days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -5, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS spring_before_5days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -4, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS spring_before_4days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -3, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS spring_before_3days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -2, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS spring_before_2days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -1, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS spring_before_1days "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS spring_today "
						+ "   FROM (SELECT p.fk_season_no, o.order_date "
						+ "		  	  FROM tbl_order o JOIN tbl_orderdetail od "
						+ "		    	ON o.order_no = od.fk_order_no JOIN tbl_products p "
						+ "		    	ON od.fk_prod_no = p.prod_no "
						+ "		 	 WHERE p.fk_season_no = 1) "
						+ "  WHERE TO_CHAR(order_date, 'yyyy-mm-dd') BETWEEN TO_CHAR(order_date -6, 'yyyy-mm-dd') AND TO_CHAR(SYSDATE, 'yyyy-mm-dd') ";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			rs.next();
				
			map.put("spring_before_6days", rs.getInt("spring_before_6days"));
			map.put("spring_before_5days", rs.getInt("spring_before_5days"));
			map.put("spring_before_4days", rs.getInt("spring_before_4days"));
			map.put("spring_before_3days", rs.getInt("spring_before_3days"));
			map.put("spring_before_2days", rs.getInt("spring_before_2days"));
			map.put("spring_before_1days", rs.getInt("spring_before_1days"));
			map.put("spring_today", rs.getInt("spring_today"));
			
			// 여름상품 주문건
			sql 	= " SELECT count(*) AS total "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -6, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS summer_before_6days "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -5, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS summer_before_5days "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -4, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS summer_before_4days "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -3, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS summer_before_3days "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -2, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS summer_before_2days "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -1, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS summer_before_1days "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS summer_today "
					+ "   FROM (SELECT p.fk_season_no, o.order_date "
					+ "		  	  FROM tbl_order o JOIN tbl_orderdetail od "
					+ "		    	ON o.order_no = od.fk_order_no JOIN tbl_products p "
					+ "		    	ON od.fk_prod_no = p.prod_no "
					+ "		 	 WHERE p.fk_season_no = 2) "
					+ "  WHERE TO_CHAR(order_date, 'yyyy-mm-dd') BETWEEN TO_CHAR(order_date -6, 'yyyy-mm-dd') AND TO_CHAR(SYSDATE, 'yyyy-mm-dd') ";
		
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			rs.next();
			
			map.put("summer_before_6days", rs.getInt("summer_before_6days"));
			map.put("summer_before_5days", rs.getInt("summer_before_5days"));
			map.put("summer_before_4days", rs.getInt("summer_before_4days"));
			map.put("summer_before_3days", rs.getInt("summer_before_3days"));
			map.put("summer_before_2days", rs.getInt("summer_before_2days"));
			map.put("summer_before_1days", rs.getInt("summer_before_1days"));
			map.put("summer_today", rs.getInt("summer_today"));
			
			// 가을상품 주문건
			sql 	= " SELECT count(*) AS total "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -6, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS autumn_before_6days "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -5, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS autumn_before_5days "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -4, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS autumn_before_4days "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -3, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS autumn_before_3days "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -2, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS autumn_before_2days "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -1, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS autumn_before_1days "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS autumn_today "
					+ "   FROM (SELECT p.fk_season_no, o.order_date "
					+ "		  	  FROM tbl_order o JOIN tbl_orderdetail od "
					+ "		    	ON o.order_no = od.fk_order_no JOIN tbl_products p "
					+ "		    	ON od.fk_prod_no = p.prod_no "
					+ "		 	 WHERE p.fk_season_no = 3) "
					+ "  WHERE TO_CHAR(order_date, 'yyyy-mm-dd') BETWEEN TO_CHAR(order_date -6, 'yyyy-mm-dd') AND TO_CHAR(SYSDATE, 'yyyy-mm-dd') ";
		
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			rs.next();
			
			map.put("autumn_before_6days", rs.getInt("autumn_before_6days"));
			map.put("autumn_before_5days", rs.getInt("autumn_before_5days"));
			map.put("autumn_before_4days", rs.getInt("autumn_before_4days"));
			map.put("autumn_before_3days", rs.getInt("autumn_before_3days"));
			map.put("autumn_before_2days", rs.getInt("autumn_before_2days"));
			map.put("autumn_before_1days", rs.getInt("autumn_before_1days"));
			map.put("autumn_today", rs.getInt("autumn_today"));
			
			// 겨울상품 주문건
			sql 	= " SELECT count(*) AS total "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -6, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS winter_before_6days "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -5, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS winter_before_5days "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -4, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS winter_before_4days "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -3, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS winter_before_3days "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -2, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS winter_before_2days "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -1, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS winter_before_1days "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS winter_today "
					+ "   FROM (SELECT p.fk_season_no, o.order_date "
					+ "		  	  FROM tbl_order o JOIN tbl_orderdetail od "
					+ "		    	ON o.order_no = od.fk_order_no JOIN tbl_products p "
					+ "		    	ON od.fk_prod_no = p.prod_no "
					+ "		 	 WHERE p.fk_season_no = 4) "
					+ "  WHERE TO_CHAR(order_date, 'yyyy-mm-dd') BETWEEN TO_CHAR(order_date -6, 'yyyy-mm-dd') AND TO_CHAR(SYSDATE, 'yyyy-mm-dd') ";
		
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			rs.next();
			
			map.put("winter_before_6days", rs.getInt("winter_before_6days"));
			map.put("winter_before_5days", rs.getInt("winter_before_5days"));
			map.put("winter_before_4days", rs.getInt("winter_before_4days"));
			map.put("winter_before_3days", rs.getInt("winter_before_3days"));
			map.put("winter_before_2days", rs.getInt("winter_before_2days"));
			map.put("winter_before_1days", rs.getInt("winter_before_1days"));
			map.put("winter_today", rs.getInt("winter_today"));
			
			
			// 2주전 봄 상품
			sql 	= " SELECT count(*) AS total "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -13, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS spring_before_13days "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -12, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS spring_before_12days "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -11, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS spring_before_11days "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -10, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS spring_before_10days "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -9, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS spring_before_9days "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -8, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS spring_before_8days "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -7, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS spring_before_7days "
					+ "   FROM (SELECT p.fk_season_no, o.order_date "
					+ "		  	  FROM tbl_order o JOIN tbl_orderdetail od "
					+ "		    	ON o.order_no = od.fk_order_no JOIN tbl_products p "
					+ "		    	ON od.fk_prod_no = p.prod_no "
					+ "		 	 WHERE p.fk_season_no = 1) "
					+ "  WHERE TO_CHAR(order_date, 'yyyy-mm-dd') BETWEEN TO_CHAR(order_date -13, 'yyyy-mm-dd') AND TO_CHAR(SYSDATE, 'yyyy-mm-dd') ";
		
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			rs.next();
				
			map.put("spring_before_13days", rs.getInt("spring_before_13days"));
			map.put("spring_before_12days", rs.getInt("spring_before_12days"));
			map.put("spring_before_11days", rs.getInt("spring_before_11days"));
			map.put("spring_before_10days", rs.getInt("spring_before_10days"));
			map.put("spring_before_9days", rs.getInt("spring_before_9days"));
			map.put("spring_before_8days", rs.getInt("spring_before_8days"));
			map.put("spring_before_7days", rs.getInt("spring_before_7days"));
		
			
			// 2주전 여름상품
			sql 	= " SELECT count(*) AS total "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -13, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS summer_before_13days "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -12, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS summer_before_12days "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -11, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS summer_before_11days "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -10, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS summer_before_10days "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -9, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS summer_before_9days "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -8, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS summer_before_8days "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -7, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS summer_before_7days "
					+ "   FROM (SELECT p.fk_season_no, o.order_date "
					+ "			  FROM tbl_order o JOIN tbl_orderdetail od "
					+ "		   	 	ON o.order_no = od.fk_order_no JOIN tbl_products p "
					+ "		    	ON od.fk_prod_no = p.prod_no "
					+ "		 	 WHERE p.fk_season_no = 2) "
					+ "  WHERE TO_CHAR(order_date, 'yyyy-mm-dd') BETWEEN TO_CHAR(order_date -13, 'yyyy-mm-dd') AND TO_CHAR(SYSDATE, 'yyyy-mm-dd') ";
		
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			rs.next();
			
			map.put("summer_before_13days", rs.getInt("summer_before_13days"));
			map.put("summer_before_12days", rs.getInt("summer_before_12days"));
			map.put("summer_before_11days", rs.getInt("summer_before_11days"));
			map.put("summer_before_10days", rs.getInt("summer_before_10days"));
			map.put("summer_before_9days", rs.getInt("summer_before_9days"));
			map.put("summer_before_8days", rs.getInt("summer_before_8days"));
			map.put("summer_before_7days", rs.getInt("summer_before_7days"));
			
			
			// 2주전 가을상품
			sql 	= " SELECT count(*) AS total "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -13, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS autumn_before_13days "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -12, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS autumn_before_12days "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -11, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS autumn_before_11days "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -10, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS autumn_before_10days "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -9, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS autumn_before_9days "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -8, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS autumn_before_8days "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -7, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS autumn_before_7days "
					+ "   FROM (SELECT p.fk_season_no, o.order_date "
					+ "		 	  FROM tbl_order o JOIN tbl_orderdetail od "
					+ "		    	ON o.order_no = od.fk_order_no JOIN tbl_products p "
					+ "		    	ON od.fk_prod_no = p.prod_no "
					+ "		 	 WHERE p.fk_season_no = 3) "
					+ "  WHERE TO_CHAR(order_date, 'yyyy-mm-dd') BETWEEN TO_CHAR(order_date -13, 'yyyy-mm-dd') AND TO_CHAR(SYSDATE, 'yyyy-mm-dd') ";
		
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			rs.next();
			
			map.put("autumn_before_13days", rs.getInt("autumn_before_13days"));
			map.put("autumn_before_12days", rs.getInt("autumn_before_12days"));
			map.put("autumn_before_11days", rs.getInt("autumn_before_11days"));
			map.put("autumn_before_10days", rs.getInt("autumn_before_10days"));
			map.put("autumn_before_9days", rs.getInt("autumn_before_9days"));
			map.put("autumn_before_8days", rs.getInt("autumn_before_8days"));
			map.put("autumn_before_7days", rs.getInt("autumn_before_7days"));
			
			
			// 2주전 겨울상품
			sql 	= " SELECT count(*) AS total "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -13, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS winter_before_13days "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -12, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS winter_before_12days "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -11, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS winter_before_11days "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -10, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS winter_before_10days "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -9, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS winter_before_9days "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -8, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS winter_before_8days "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(SYSDATE -7, 'yyyy-mm-dd'), 'yyyy-mm-dd') - TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'),'yyyy-mm-dd') = 0 THEN 1 ELSE 0 END) AS winter_before_7days "
					+ "   FROM (SELECT p.fk_season_no, o.order_date "
					+ "			  FROM tbl_order o JOIN tbl_orderdetail od "
					+ "		    	ON o.order_no = od.fk_order_no JOIN tbl_products p "
					+ "		    	ON od.fk_prod_no = p.prod_no "
					+ "		 	 WHERE p.fk_season_no = 4) "
					+ "  WHERE TO_CHAR(order_date, 'yyyy-mm-dd') BETWEEN TO_CHAR(order_date -13, 'yyyy-mm-dd') AND TO_CHAR(SYSDATE, 'yyyy-mm-dd') ";
		
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			rs.next();
			
			map.put("winter_before_13days", rs.getInt("winter_before_13days"));
			map.put("winter_before_12days", rs.getInt("winter_before_12days"));
			map.put("winter_before_11days", rs.getInt("winter_before_11days"));
			map.put("winter_before_10days", rs.getInt("winter_before_10days"));
			map.put("winter_before_9days", rs.getInt("winter_before_9days"));
			map.put("winter_before_8days", rs.getInt("winter_before_8days"));
			map.put("winter_before_7days", rs.getInt("winter_before_7days"));
			
		} finally {
			close();
		}
		
		return map;
	}// end of public Map<String, Integer> getSeasonOrderWeek() throws SQLException ----------------------------- 

	
	// 월간 계절별 주문건수
	@Override
	public Map<String, Integer> getSeasonOrderMonth() throws SQLException {
		Map<String, Integer> map = new HashMap<>();
		
		try {
			
			conn = ds.getConnection();
			
			// 월간 봄상품 주문건
			String sql 	= " SELECT count(*) AS total "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -6), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS spring_before_6months "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -5), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS spring_before_5months "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -4), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS spring_before_4months "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -3), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS spring_before_3months "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -2), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS spring_before_2months "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -1), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS spring_before_1months "
						+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(sysdate, 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS spring_this_month "
						+ "   FROM (SELECT p.fk_season_no, o.order_date "
						+ "		 	  FROM tbl_order o JOIN tbl_orderdetail od "
						+ "		   	  	ON o.order_no = od.fk_order_no JOIN tbl_products p "
						+ "		    	ON od.fk_prod_no = p.prod_no "
						+ "		 	 WHERE p.fk_season_no = 1) "
						+ "  WHERE TO_CHAR(order_date, 'yyyy-mm') BETWEEN TO_CHAR((ADD_MONTHS(order_date, -6)), 'yyyy-mm') AND TO_CHAR(SYSDATE, 'yyyy-mm') ";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			rs.next();
				
			map.put("spring_before_6months", rs.getInt("spring_before_6months"));
			map.put("spring_before_5months", rs.getInt("spring_before_5months"));
			map.put("spring_before_4months", rs.getInt("spring_before_4months"));
			map.put("spring_before_3months", rs.getInt("spring_before_3months"));
			map.put("spring_before_2months", rs.getInt("spring_before_2months"));
			map.put("spring_before_1months", rs.getInt("spring_before_1months"));
			map.put("spring_this_month", rs.getInt("spring_this_month"));
			
			// 월간 여름상품 주문건
			sql 	= " SELECT count(*) AS total\n"
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -6), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS summer_before_6months "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -5), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS summer_before_5months "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -4), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS summer_before_4months "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -3), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS summer_before_3months "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -2), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS summer_before_2months "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -1), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS summer_before_1months "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(sysdate, 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS summer_this_month "
					+ "   FROM (SELECT p.fk_season_no, o.order_date "
					+ "		  	  FROM tbl_order o JOIN tbl_orderdetail od "
					+ "		    	ON o.order_no = od.fk_order_no JOIN tbl_products p "
					+ "		    	ON od.fk_prod_no = p.prod_no "
					+ "		 	 WHERE p.fk_season_no = 2) "
					+ "  WHERE TO_CHAR(order_date, 'yyyy-mm') BETWEEN TO_CHAR((ADD_MONTHS(order_date, -6)), 'yyyy-mm') AND TO_CHAR(SYSDATE, 'yyyy-mm') ";
		
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			rs.next();
			
			map.put("summer_before_6months", rs.getInt("summer_before_6months"));
			map.put("summer_before_5months", rs.getInt("summer_before_5months"));
			map.put("summer_before_4months", rs.getInt("summer_before_4months"));
			map.put("summer_before_3months", rs.getInt("summer_before_3months"));
			map.put("summer_before_2months", rs.getInt("summer_before_2months"));
			map.put("summer_before_1months", rs.getInt("summer_before_1months"));
			map.put("summer_this_month", rs.getInt("summer_this_month"));
			
			// 월간 가을상품 주문건
			sql 	= " SELECT count(*) AS total\n"
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -6), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS autumn_before_6months "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -5), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS autumn_before_5months "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -4), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS autumn_before_4months "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -3), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS autumn_before_3months "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -2), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS autumn_before_2months "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -1), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS autumn_before_1months "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(sysdate, 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS autumn_this_month "
					+ "   FROM (SELECT p.fk_season_no, o.order_date "
					+ "		  	  FROM tbl_order o JOIN tbl_orderdetail od "
					+ "		    	ON o.order_no = od.fk_order_no JOIN tbl_products p "
					+ "		    	ON od.fk_prod_no = p.prod_no "
					+ "		 	 WHERE p.fk_season_no = 3) "
					+ "  WHERE TO_CHAR(order_date, 'yyyy-mm') BETWEEN TO_CHAR((ADD_MONTHS(order_date, -6)), 'yyyy-mm') AND TO_CHAR(SYSDATE, 'yyyy-mm') ";
		
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			rs.next();
			
			map.put("autumn_before_6months", rs.getInt("autumn_before_6months"));
			map.put("autumn_before_5months", rs.getInt("autumn_before_5months"));
			map.put("autumn_before_4months", rs.getInt("autumn_before_4months"));
			map.put("autumn_before_3months", rs.getInt("autumn_before_3months"));
			map.put("autumn_before_2months", rs.getInt("autumn_before_2months"));
			map.put("autumn_before_1months", rs.getInt("autumn_before_1months"));
			map.put("autumn_this_month", rs.getInt("autumn_this_month"));
			
			
			// 월간 겨울상품 주문건
			sql 	= " SELECT count(*) AS total "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -6), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS winter_before_6months "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -5), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS winter_before_5months "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -4), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS winter_before_4months "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -3), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS winter_before_3months "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -2), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS winter_before_2months "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -1), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS winter_before_1months "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(sysdate, 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS winter_this_month "
					+ "   FROM (SELECT p.fk_season_no, o.order_date "
					+ "		  	  FROM tbl_order o JOIN tbl_orderdetail od "
					+ "		    	ON o.order_no = od.fk_order_no JOIN tbl_products p "
					+ "		    	ON od.fk_prod_no = p.prod_no "
					+ "		 	 WHERE p.fk_season_no = 4) "
					+ "  WHERE TO_CHAR(order_date, 'yyyy-mm') BETWEEN TO_CHAR((ADD_MONTHS(order_date, -6)), 'yyyy-mm') AND TO_CHAR(SYSDATE, 'yyyy-mm') ";
		
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			rs.next();
			
			map.put("winter_before_6months", rs.getInt("winter_before_6months"));
			map.put("winter_before_5months", rs.getInt("winter_before_5months"));
			map.put("winter_before_4months", rs.getInt("winter_before_4months"));
			map.put("winter_before_3months", rs.getInt("winter_before_3months"));
			map.put("winter_before_2months", rs.getInt("winter_before_2months"));
			map.put("winter_before_1months", rs.getInt("winter_before_1months"));
			map.put("winter_this_month", rs.getInt("winter_this_month"));
			
			
			// 전년도 월간 봄 상품
			sql 	= " SELECT count(*) AS total "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -18), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS spring_before_18months "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -17), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS spring_before_17months "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -16), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS spring_before_16months "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -15), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS spring_before_15months "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -14), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS spring_before_14months "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -13), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS spring_before_13months "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -12), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS spring_before_12months "
					+ "   FROM (SELECT p.fk_season_no, o.order_date "
					+ "		  	  FROM tbl_order o JOIN tbl_orderdetail od "
					+ "		    	ON o.order_no = od.fk_order_no JOIN tbl_products p "
					+ "		    	ON od.fk_prod_no = p.prod_no "
					+ "		 	 WHERE p.fk_season_no = 1) "
					+ "  WHERE TO_CHAR(order_date, 'yyyy-mm') BETWEEN TO_CHAR((ADD_MONTHS(order_date, -18)), 'yyyy-mm') AND TO_CHAR(SYSDATE, 'yyyy-mm') ";
		
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			rs.next();
				
			map.put("spring_before_18months", rs.getInt("spring_before_18months"));
			map.put("spring_before_17months", rs.getInt("spring_before_17months"));
			map.put("spring_before_16months", rs.getInt("spring_before_16months"));
			map.put("spring_before_15months", rs.getInt("spring_before_15months"));
			map.put("spring_before_14months", rs.getInt("spring_before_14months"));
			map.put("spring_before_13months", rs.getInt("spring_before_13months"));
			map.put("spring_before_12months", rs.getInt("spring_before_12months"));
		
			
			// 전년도 월간 여름상품
			sql 	= " SELECT count(*) AS total "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -18), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS summer_before_18months "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -17), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS summer_before_17months "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -16), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS summer_before_16months "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -15), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS summer_before_15months "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -14), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS summer_before_14months "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -13), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS summer_before_13months "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -12), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS summer_before_12months "
					+ "   FROM (SELECT p.fk_season_no, o.order_date "
					+ "		  	  FROM tbl_order o JOIN tbl_orderdetail od "
					+ "		    	ON o.order_no = od.fk_order_no JOIN tbl_products p "
					+ "		    	ON od.fk_prod_no = p.prod_no "
					+ "		 	 WHERE p.fk_season_no = 2) "
					+ "  WHERE TO_CHAR(order_date, 'yyyy-mm') BETWEEN TO_CHAR((ADD_MONTHS(order_date, -18)), 'yyyy-mm') AND TO_CHAR(SYSDATE, 'yyyy-mm') ";
		
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			rs.next();
			
			map.put("summer_before_18months", rs.getInt("summer_before_18months"));
			map.put("summer_before_17months", rs.getInt("summer_before_17months"));
			map.put("summer_before_16months", rs.getInt("summer_before_16months"));
			map.put("summer_before_15months", rs.getInt("summer_before_15months"));
			map.put("summer_before_14months", rs.getInt("summer_before_14months"));
			map.put("summer_before_13months", rs.getInt("summer_before_13months"));
			map.put("summer_before_12months", rs.getInt("summer_before_12months"));
			
			
			// 전년도 월간 가을상품
			sql 	= " SELECT count(*) AS total "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -18), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS autumn_before_18months "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -17), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS autumn_before_17months "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -16), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS autumn_before_16months "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -15), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS autumn_before_15months "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -14), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS autumn_before_14months "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -13), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS autumn_before_13months "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -12), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS autumn_before_12months "
					+ "   FROM (SELECT p.fk_season_no, o.order_date "
					+ "		  	  FROM tbl_order o JOIN tbl_orderdetail od "
					+ "		    	ON o.order_no = od.fk_order_no JOIN tbl_products p "
					+ "		    	ON od.fk_prod_no = p.prod_no "
					+ "		 	 WHERE p.fk_season_no = 3) "
					+ "  WHERE TO_CHAR(order_date, 'yyyy-mm') BETWEEN TO_CHAR((ADD_MONTHS(order_date, -18)), 'yyyy-mm') AND TO_CHAR(SYSDATE, 'yyyy-mm') ";
		
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			rs.next();
			
			map.put("autumn_before_18months", rs.getInt("autumn_before_18months"));
			map.put("autumn_before_17months", rs.getInt("autumn_before_17months"));
			map.put("autumn_before_16months", rs.getInt("autumn_before_16months"));
			map.put("autumn_before_15months", rs.getInt("autumn_before_15months"));
			map.put("autumn_before_14months", rs.getInt("autumn_before_14months"));
			map.put("autumn_before_13months", rs.getInt("autumn_before_13months"));
			map.put("autumn_before_12months", rs.getInt("autumn_before_12months"));
			
			
			// 전년도 월간 겨울상품
			sql 	= " SELECT count(*) AS total "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -18), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS winter_before_18months "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -17), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS winter_before_17months "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -16), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS winter_before_16months "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -15), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS winter_before_15months "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -14), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS winter_before_14months "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -13), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS winter_before_13months "
					+ "      , SUM(CASE WHEN TO_DATE(TO_CHAR(ADD_MONTHS(SYSDATE, -12), 'yyyymm'), 'yyyymm') - TO_DATE(TO_CHAR(order_date, 'yyyymm'),'yyyymm') = 0 THEN 1 ELSE 0 END) AS winter_before_12months "
					+ "   FROM (SELECT p.fk_season_no, o.order_date "
					+ "		  FROM tbl_order o JOIN tbl_orderdetail od "
					+ "		    ON o.order_no = od.fk_order_no JOIN tbl_products p "
					+ "		    ON od.fk_prod_no = p.prod_no "
					+ "		 WHERE p.fk_season_no = 4) "
					+ "  WHERE TO_CHAR(order_date, 'yyyy-mm') BETWEEN TO_CHAR((ADD_MONTHS(order_date, -18)), 'yyyy-mm') AND TO_CHAR(SYSDATE, 'yyyy-mm') ";
		
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			rs.next();
			
			map.put("winter_before_18months", rs.getInt("winter_before_18months"));
			map.put("winter_before_17months", rs.getInt("winter_before_17months"));
			map.put("winter_before_16months", rs.getInt("winter_before_16months"));
			map.put("winter_before_15months", rs.getInt("winter_before_15months"));
			map.put("winter_before_14months", rs.getInt("winter_before_14months"));
			map.put("winter_before_13months", rs.getInt("winter_before_13months"));
			map.put("winter_before_12months", rs.getInt("winter_before_12months"));
			
		} finally {
			close();
		}
		
		return map;
	}// end of public Map<String, Integer> getSeasonOrderMonth() throws SQLException  -------------------------

	
	// 상품의 판매량 랭킹 정보를 가져온다.
	@Override
	public List<Map<String, String>> getProdListRank(Map<String, String> paraMap) throws SQLException {
		List<Map<String, String>> prodRankList = new ArrayList<>(); 
		
		
		try {
			conn = ds.getConnection();
			
			String sql 	= " SELECT p.prod_no, p.prod_name, p.prod_price, A.sold_cnt, A.prod_revenue, A.revenue_pct "
						+ "   FROM "
						+ "		( "
						+ "		SELECT p.prod_no, sum(od.ordetail_count) AS sold_cnt, sum(od.ordetail_price) AS prod_revenue, round(sum(od.ordetail_price) / ( SELECT sum(ordetail_price) FROM tbl_orderdetail) * 100, 2) AS revenue_pct "
						+ "	  	  FROM tbl_order o JOIN tbl_orderdetail od "
						+ "	    	ON o.order_no = od.fk_order_no "
						+ "	  	  JOIN tbl_products p "
						+ "	    	ON od.fk_prod_no = p.prod_no "
						+ "	 WHERE TO_CHAR(order_date, 'yyyy-mm') = TO_CHAR(add_months(SYSDATE, -?), 'yyyy-mm') AND rownum <= 10 "
						+ "	GROUP BY p.prod_no "
						+ "		) A JOIN tbl_products p "
						+ "		ON A.prod_no = p.prod_no "
						+ " ORDER BY "+ paraMap.get("orderSearchType") + "   "+ paraMap.get("orderSearchRange");
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paraMap.get("ordersearchMonth"));
//			pstmt.setString(2, paraMap.get("searchRange"));
//			order by 절에 는 위치홀더 사용불가..
			
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				Map<String, String> map = new HashMap<>();
				
				map.put("prod_no", rs.getString("prod_no"));
				map.put("prod_name", rs.getString("prod_name"));
				map.put("prod_price", rs.getString("prod_price"));
				map.put("sold_cnt", rs.getString("sold_cnt"));
				map.put("prod_revenue", rs.getString("prod_revenue"));
				map.put("revenue_pct", rs.getString("revenue_pct"));
				
				prodRankList.add(map);
				
			}// end of while (rs.next()) --------------------- 
			
		} finally {
			close();
		}
		
		return prodRankList;
	}// end of public List<Map<String, String>> getProdListRank(Map<String, String> paraMap) throws SQLException -------------------- 

	
}
