package admin.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
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
	public Map<String, Integer> getvisitUserWeek() throws SQLException {
		
		Map<String, Integer> map = null;
		
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
			
			if (rs.next()) {
				map = new HashMap<>();
				
				map.put("total", rs.getInt("total"));
				map.put("before_6days", rs.getInt("before_6days"));
				map.put("before_5days", rs.getInt("before_5days"));
				map.put("before_4days", rs.getInt("before_4days"));
				map.put("before_3days", rs.getInt("before_3days"));
				map.put("before_2days", rs.getInt("before_2days"));
				map.put("before_1days", rs.getInt("before_1days"));
				map.put("today", rs.getInt("today"));
				
			}// end of if (rs.next()) ----------------- 
			
		} finally {
			close();
		}
		
		return map;
	}// end of public List<Map<String, String>> getvisitUserWeek() throws SQLException --------------------------- 


	// 2주전 가입자 수 통계
	@Override
	public Map<String, Integer> getvisitUser2Week() throws SQLException {
		Map<String, Integer> map = null;
		
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
			
			if (rs.next()) {
				map = new HashMap<>();
				
				map.put("total", rs.getInt("total"));
				map.put("before_13days", rs.getInt("before_13days"));
				map.put("before_12days", rs.getInt("before_12days"));
				map.put("before_11days", rs.getInt("before_11days"));
				map.put("before_10days", rs.getInt("before_10days"));
				map.put("before_9days", rs.getInt("before_9days"));
				map.put("before_8days", rs.getInt("before_8days"));
				map.put("before_7days", rs.getInt("before_7days"));
				
			}// end of if (rs.next()) ----------------- 
			
		} finally {
			close();
		}
		
		return map;
	}// end of public Map<String, Integer> getvisitUser2Week() throws SQLException ---------------------
    
    
    
    
    
    
    
    
    
    
    
}
