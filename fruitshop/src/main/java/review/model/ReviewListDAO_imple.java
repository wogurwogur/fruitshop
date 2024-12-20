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
					+ " ON A.review_no = B.fk_review_no "
					+ " order by review_no desc ";
			
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

	// 구매후기 조회수 베스트 글 보여주는 메소드 
	@Override
	public List<ReviewListVO> breviewListall() throws SQLException {
		
		List<ReviewListVO> brevList = new ArrayList<>();
		
		
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
						+ " ON A.review_no = B.fk_review_no "
						+ " where review_viewcount = (select max(review_viewcount) "
						+ "                            from tbl_reviews "
						+ "                            where substr(sysdate, 1, 5) = substr(review_regidate, 1, 5) ) ";
			
				pstmt = conn.prepareStatement(sql);
				
				rs = pstmt.executeQuery();
				
				rs.next();
					
					ReviewListVO brevvo = new ReviewListVO();
					brevvo.setReview_no(rs.getInt("review_no"));
					brevvo.setReview_title(rs.getString("review_title"));
					brevvo.setReview_viewcount(rs.getString("review_viewcount"));
					brevvo.setReview_regidate(rs.getString("review_regidate"));
					brevvo.setFk_user_no(rs.getInt("user_no"));
					brevvo.setUserid(rs.getString("userid"));
					brevvo.setProd_name(rs.getString("prod_name"));
					brevvo.setComment_count(rs.getInt("comment_count"));
					
					brevList.add(brevvo);
									
					// System.out.println(brevList);
				// end of while ----------------
			
		} finally {
			close();
		}
		
		return brevList;
	}

	// 구매후기 댓글 베스트 글 보여주는 메소드
	@Override
	public List<ReviewListVO> creviewListall() throws SQLException {
		
		List<ReviewListVO> crevList = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
			
			String sql = " select A.review_no, A.review_title, A.review_viewcount, A.review_regidate, A.user_no, A.userid , A.prod_name, nvl(B.com_count, 0) as comment_count "
						+ " FROM "
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
						+ " ON A.review_no = B.fk_review_no "
						+ " where substr(sysdate, 1, 5) = substr(A.review_regidate, 1, 5) "
						+ " order by comment_count desc ";
				
			
					pstmt = conn.prepareStatement(sql);
			
					rs = pstmt.executeQuery();
					
					rs.next();
					
						ReviewListVO crevvo = new ReviewListVO();
						
						crevvo.setReview_no(rs.getInt("review_no"));
						crevvo.setReview_title(rs.getString("review_title"));
						crevvo.setReview_viewcount(rs.getString("review_viewcount"));
						crevvo.setReview_regidate(rs.getString("review_regidate"));
						crevvo.setFk_user_no(rs.getInt("user_no"));
						crevvo.setUserid(rs.getString("userid"));
						crevvo.setProd_name(rs.getString("prod_name"));
						crevvo.setComment_count(rs.getInt("comment_count"));
						
						crevList.add(crevvo);
					
					
			
		} finally {
			close();
		}
			
		return crevList;
					
	}

	
	
	// 구매후기 글 클릭시 글 내용 보여주는 메소드
	@Override
	public ReviewListVO reviewReadall(String review_no) throws SQLException {
		
		
		ReviewListVO reviewRead1 = null;	
		
		try {
			conn=ds.getConnection();
		
		
		String sql = " select r.review_no, r.review_title, r.fk_user_no, r.review_contents, "
				+ " m.user_no, func_userid_block(m.userid) as userid , p.prod_name, p.prod_price, p.prod_thumnail "
				+ " from tbl_reviews r INNER JOIN tbl_member m "
				+ " on r.fk_user_no = m.user_no "
				+ " INNER JOIN tbl_products p "
				+ " on r.fk_prod_no = p.prod_no "
				+ " where r.review_no = ? ";
		
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, review_no);
			
			rs = pstmt.executeQuery();
		
			
			if (rs.next() ) {
				// 번호 제목 글쓴이 내용 상품썸넬 상품이름 상품 가격
				reviewRead1 = new ReviewListVO();
				
				reviewRead1.setReview_no(rs.getInt("review_no"));
				reviewRead1.setReview_title(rs.getString("review_title"));
				reviewRead1.setReview_contents(rs.getString("review_contents"));
				reviewRead1.setUserid(rs.getString("userid"));
				reviewRead1.setProd_thumnail(rs.getString("prod_thumnail"));
				reviewRead1.setProd_name(rs.getString("prod_name"));
				reviewRead1.setProd_price(rs.getInt("prod_price"));
				
				
			}
			
		} finally{
			close();
		}
		
		return reviewRead1;
	}

	
	

} // end of public class ReviewListDAO_imple implements ReviewListDAO {
