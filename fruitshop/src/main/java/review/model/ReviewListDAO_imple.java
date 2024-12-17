package review.model;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import review.domain.ReviewListVO;

public class ReviewListDAO_imple implements ReviewListDAO {
	
	private DataSource ds;	// DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다.
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	
	public ReviewListDAO_imple() {
		
		try {
		Context initContext = new InitialContext();
	    Context envContext  = (Context)initContext.lookup("java:/comp/env");
	    ds = (DataSource)envContext.lookup("jdbc/semioracle"); // webxml에서 myoracle -> jdbc/semioracle 바뀌어야됨
	    
	    
		} catch(NamingException e) {
			e.printStackTrace();
		} 
		
	} // end of public ReviewListDAO_imple() {
	
	
	// 사용한 자원을 반납하는 close() 메소드 생성하기
	private void close() {
		
		try {
			if(rs !=null) 	 {rs.close(); 	 rs=null;}
			if(pstmt !=null) {pstmt.close(); pstmt=null;}
			if(conn !=null)  {conn.close();  conn=null;}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
	} // end of private void close() { --------------------
	
	
	
	
	// 구매후기 게시글(리뷰)리스트 전부 보여주는 메소드
	@Override
	public List<ReviewListVO> reviewListall() throws SQLException {
		
		List<ReviewListVO> revList = new ArrayList<>();
		
		try {
			
			conn = ds.getConnection();
			
			String sql = " select A.review_no, A.review_title, A.review_viewcount, A.review_regidate, A.user_no, A.userid , A.prod_name, nvl(B.com_count, 0) as comment_count "
					+ " FROM  "
					+ " ( "
					+ " select r.review_no, r.review_title, r.review_viewcount, r.review_regidate, r.fk_user_no, "
					+ " m.user_no, func_userid_block(m.userid) as userid , p.prod_name "
					+ " from tbl_reviews r INNER JOIN tbl_member m "
					+ " on r.fk_user_no = m.user_no "
					+ " INNER JOIN tbl_products p "
					+ " on r.fk_prod_no = p.prod_no "
					+ " order by review_no desc "
					+ " ) A "
					+ " LEFT OUTER JOIN "
					+ " ( "
					+ " select count(comment_no) as com_count, fk_review_no "
					+ " from tbl_comments "
					+ " group by fk_review_no "
					+ " ) B "
					+ " ON A.review_no = B.fk_review_no ";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				ReviewListVO revvo = new ReviewListVO();
				revvo.setReview_no(rs.getInt("review_no"));
				revvo.setReview_title(rs.getString("review_title"));
				revvo.setReview_viewcount(rs.getString("review_viewcount"));
				revvo.setReview_regidate(rs.getString("review_regidate"));
				revvo.setFk_user_no(rs.getInt("user_no"));
				revvo.setUserid(rs.getString("userid"));
				revvo.setProd_name(rs.getString("prod_name"));
				revvo.setComment_count(rs.getInt("comment_count"));
				
				revList.add(revvo);
								
				
			} // end of while ----------------		

									
		} finally {
			close();
		}
			
		
		  
		return revList;
	}
	
	

} // end of public class ReviewListDAO_imple implements ReviewListDAO {
