package review.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
			
			String sql = " select A.review_status, A.review_no, A.review_title, A.review_viewcount, A.review_regidate, A.user_no, A.userid , A.prod_name, nvl(B.com_count, 0) as comment_count "
					+ " FROM  "
					+ " ( "
					+ " select r.review_status, r.review_no, r.review_title, r.review_viewcount, r.review_regidate, r.fk_user_no, "
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
					+ " where A.review_status = 1 "
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
						+ " m.user_no, func_userid_block(m.userid) as userid, p.prod_name "
						+ " from tbl_reviews r INNER JOIN tbl_member m "
						+ " on r.fk_user_no = m.user_no "
						+ " INNER JOIN tbl_products p "
						+ " on r.fk_prod_no = p.prod_no "
						+ " ) A "
						+ " LEFT OUTER JOIN "
						+ " ( "
						+ " select count(comment_no) as com_count, fk_review_no "
						+ " from tbl_comments "
						+ " group by fk_review_no "
						+ " ) B "
						+ " ON A.review_no = B.fk_review_no "
						+ " where substr(sysdate, 1, 5) = substr(review_regidate, 1, 5) "
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
		
		
		ReviewListVO reviewRead = null;	
		
		try {
			conn=ds.getConnection();
		
		
		String sql = " select r.review_regidate , r.review_no, r.review_title, r.fk_user_no, r.review_contents, r.review_viewcount, "
				+ " m.user_no, func_userid_block(m.userid) as userid , p.prod_no, p.prod_name, p.prod_price, p.prod_thumnail "
				+ " from tbl_reviews r "
				+ " INNER JOIN tbl_member m "
				+ " on r.fk_user_no = m.user_no "
				+ " INNER JOIN tbl_products p "
				+ " on r.fk_prod_no = p.prod_no "
				+ " where r.review_no = ? ";
		
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, review_no);
			
			rs = pstmt.executeQuery();
		
			
			if (rs.next() ) {
				
				reviewRead = new ReviewListVO();
				
				reviewRead.setReview_no(rs.getInt("review_no"));
				reviewRead.setReview_title(rs.getString("review_title"));
				reviewRead.setReview_contents(rs.getString("review_contents"));
				reviewRead.setReview_regidate(rs.getString("review_regidate"));
				reviewRead.setReview_viewcount(rs.getString("review_viewcount"));
				reviewRead.setUserid(rs.getString("userid"));
				reviewRead.setProd_no(rs.getInt("prod_no"));
				reviewRead.setProd_thumnail(rs.getString("prod_thumnail"));
				reviewRead.setProd_name(rs.getString("prod_name"));
				reviewRead.setProd_price(rs.getInt("prod_price"));
				
				
				
			}
			
		} finally{
			close();
		}
		
		return reviewRead;
	}
	
	// 구매후기 글 클릭시 해당 글 댓글 보여주는 메소드
	@Override
	public List<ReviewListVO> commentListAll(String review_no) throws SQLException {
		
		
		List<ReviewListVO> commentList = new ArrayList<>();
		
		try {
			
			conn=ds.getConnection();
		
		String sql = " select comment_no, comment_contents, comment_pwd, comment_regidate, m.user_no, m.userid as cuserid, r.review_no "
					+ " from tbl_comments c "
					+ " INNER JOIN tbl_member m "
					+ " on c.fk_user_no = m.user_no "
					+ " INNER JOIN tbl_reviews r "
					+ " on c.fk_review_no = r.review_no "
					+ " where review_no = ? ";
		
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, review_no);
				
				rs = pstmt.executeQuery();
				
				while (rs.next() ) {
				
				ReviewListVO cml = new ReviewListVO();
				
				cml.setComment_no(rs.getInt("comment_no"));
				cml.setComment_contents(rs.getString("comment_contents"));
				cml.setComment_regidate(rs.getString("comment_regidate"));
				cml.setCuserid(rs.getString("cuserid"));
				cml.setComment_pwd(rs.getString("comment_pwd"));
				cml.setReview_no(rs.getInt("review_no"));
				
				commentList.add(cml);
				
				}
				
		} finally {
			close();
		}
		
		
			
		return commentList;
	}
	
	
	
	
	// 페이징 처리를 위한 검색이 있는 또는 검색이 없는 게시글에 대한 총페이지수 알아오기 //
	@Override
	public int getTotalPage(Map<String, String> paraMap) throws SQLException {
		
		int totalPage = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql = " select CEIL(COUNT(*)/?) "
					+ " from tbl_reviews "
					+ " where review_status = 1 ";
			
			String colname 	  = paraMap.get("searchType");
			String searchWord = paraMap.get("searchWord");	
			
			
			if (!colname.isBlank() && !searchWord.isBlank()) {
				// 검색이 있는 경우
				sql += " AND "+ colname +" LIKE '%'||?||'%' ";
			}
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, Integer.parseInt(paraMap.get("sizePerPage")) );
			
			if (!colname.isBlank() && !searchWord.isBlank()) {				
				pstmt.setString(2, searchWord);
			}
			
			rs = pstmt.executeQuery();
			
			rs.next();
			
			totalPage = rs.getInt(1);
		
		} finally {
			close();
		}
		
		
		return totalPage;
	}

	
	// 구매후기 글 페이징 처리를 한 모든 회원 목록 또는 검색되어진 회원 목록 보여주기 **** //
	@Override
	public List<ReviewListVO> select_Member_paging(Map<String, String> paraMap) throws SQLException {
		
		List<ReviewListVO> reviewList = new ArrayList<>();
		
		try {
			
			conn = ds.getConnection();
			
			String colname = paraMap.get("searchType");
			String searchWord = paraMap.get("searchWord");
			int currentShowPageNo = Integer.parseInt(paraMap.get("currentShowPageNo")); // 조회하고자 하는 페이지 번호
			int sizePerPage = Integer.parseInt(paraMap.get("sizePerPage")); // 페이지당 보여줄 행의 개수

			String sql;

			if (!colname.isBlank() && !searchWord.isBlank()) {

			    // 검색 조건이 있는 경우
			    sql = " SELECT * "
			            + " FROM ( "
			            + "    SELECT ROW_NUMBER() OVER (ORDER BY A.review_no DESC) AS RNO, "
			            + "           A.review_status, A.review_contents, A.review_no, A.review_title, "
			            + "           A.review_viewcount, A.review_regidate, A.user_no, A.userid, "
			            + "           A.prod_name, NVL(B.com_count, 0) AS comment_count "
			            + "    FROM ( "
			            + "        SELECT r.review_status, r.review_no, r.review_title, r.review_viewcount, "
			            + "               r.review_regidate, r.fk_user_no, r.review_contents, "
			            + "               m.user_no, func_userid_block(m.userid) AS userid, "
			            + "               p.prod_name "
			            + "        FROM tbl_reviews r "
			            + "        INNER JOIN tbl_member m ON r.fk_user_no = m.user_no "
			            + "        INNER JOIN tbl_products p ON r.fk_prod_no = p.prod_no "
			            + "        WHERE r.review_status = 1 "
			            + "          AND r." + colname + " LIKE '%' || ? || '%' "
			            + "    ) A "
			            + "    LEFT OUTER JOIN ( "
			            + "        SELECT COUNT(comment_no) AS com_count, fk_review_no "
			            + "        FROM tbl_comments "
			            + "        GROUP BY fk_review_no "
			            + "    ) B ON A.review_no = B.fk_review_no "
			            + " ) C "
			            + " WHERE C.RNO BETWEEN ? AND ?";
			} else {
			    // 검색 조건이 없는 경우
			    sql = " SELECT * "
			        + " FROM ( "
			        + "    SELECT ROWNUM AS RNO, A.review_status, A.review_contents, A.review_no, A.review_title, "
			        + "           A.review_viewcount, A.review_regidate, A.user_no, A.userid, A.prod_name, NVL(B.com_count, 0) AS comment_count "
			        + "    FROM ( "
			        + "        SELECT r.review_status, r.review_no, r.review_title, r.review_viewcount, r.review_regidate, "
			        + "               r.fk_user_no, r.review_contents, m.user_no, func_userid_block(m.userid) AS userid, p.prod_name "
			        + "        FROM tbl_reviews r "
			        + "        INNER JOIN tbl_member m ON r.fk_user_no = m.user_no "
			        + "        INNER JOIN tbl_products p ON r.fk_prod_no = p.prod_no "
			        + "        WHERE r.review_status = 1 "
			        + "        ORDER BY r.review_no DESC "
			        + "    ) A "
			        + "    LEFT OUTER JOIN ( "
			        + "        SELECT COUNT(comment_no) AS com_count, fk_review_no "
			        + "        FROM tbl_comments "
			        + "        GROUP BY fk_review_no "
			        + "    ) B ON A.review_no = B.fk_review_no "
			        + " ) C "
			        + " WHERE C.RNO BETWEEN ? AND ? ";
			}

			pstmt = conn.prepareStatement(sql);

			// 파라미터 바인딩
			if (!colname.isBlank() && !searchWord.isBlank()) {
			    pstmt.setString(1, searchWord);
			    pstmt.setInt(2, (currentShowPageNo * sizePerPage) - (sizePerPage - 1));
			    pstmt.setInt(3, currentShowPageNo * sizePerPage);
			} else {
			    pstmt.setInt(1, (currentShowPageNo * sizePerPage) - (sizePerPage - 1));
			    pstmt.setInt(2, currentShowPageNo * sizePerPage);
			}

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
				
				reviewList.add(revvo);
				
				
				
			}
			
			
		} finally {
			close();
		}
		
		
		return reviewList;
	}

	/* >>> 뷰단(reviewList.jsp)에서 "페이징 처리시 보여주는 순번 공식" 에서 사용하기 위해 
    검색이 있는 또는 검색이 없는 구매후기 글 총개수 알아오기 시작 <<< */
	@Override
	public int getTotalMemberCount(Map<String, String> paraMap) throws SQLException {
		
		int totalMemberCount = 0;
		
		try {
			
			conn = ds.getConnection();
			
			String sql 	= " SELECT COUNT(*) "
						+ " FROM tbl_reviews "
						+ " where review_status = 1 ";
			
			String colname 	  = paraMap.get("searchType");
			String searchWord = paraMap.get("searchWord");
			
			
			if (!colname.isBlank() && !searchWord.isBlank()) {
				// 검색이 있는 경우
				sql += " AND "+ colname +" LIKE '%'||?||'%' ";
			}
		
			pstmt = conn.prepareStatement(sql);
						
			if (!colname.isBlank() && !searchWord.isBlank()) {				
				pstmt.setString(1, searchWord);
			}
			
			rs = pstmt.executeQuery();
			
			
			rs.next();
			
			totalMemberCount = rs.getInt(1); // select 된 것들 중 첫 번째 컬럼
			
		} finally {
			close();
		}
		
		return totalMemberCount;
	}
	
	
	
	// 구매후기글 상품 등록하기 상품 리스트 보여주기
	@Override
	public List<ReviewListVO> rproductFind() throws SQLException {
			
		
			List<ReviewListVO> rproductList = new ArrayList<>();
		
		try {
			
			conn=ds.getConnection();
		
			String sql = " select prod_no, prod_name, prod_price, prod_thumnail "
						+ " from tbl_products ";
		
				pstmt = conn.prepareStatement(sql);
				
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
				
				ReviewListVO rpl = new ReviewListVO();
				
				rpl.setProd_no(rs.getInt("prod_no"));
				rpl.setProd_name(rs.getString("prod_name"));
				rpl.setProd_price(rs.getInt("prod_price"));
				rpl.setProd_thumnail(rs.getString("prod_thumnail"));
				
								
				rproductList.add(rpl);
				
				}
				
		} finally {
			close();
		}
		
		
			
		return rproductList;
	}

	
	// 상품 등록하기 상품 보여주기 끝 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ //
	

	
	// 구매후기 글 상품등록 클릭했을때 reviewWrite로 보내주기
	@Override
	public ReviewListVO productSelect(String prod_no) throws SQLException {
		
		ReviewListVO rvo = null;
		
		try {
			
			conn = ds.getConnection();
			
			String sql = " select prod_no, prod_name, prod_price, prod_thumnail "
						+ " from tbl_products "
						+ " where prod_no = ? ";
			
						
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, prod_no);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				rvo = new ReviewListVO();
				
				rvo.setProd_no(rs.getInt("prod_no"));
				rvo.setProd_name(rs.getString("prod_name"));
				rvo.setProd_price(rs.getInt("prod_price"));
				rvo.setProd_thumnail(rs.getString("prod_thumnail"));
			}
			
		} finally {
			close();
		}
		

		return rvo;
	}
	
	

	
	
	
	// 구매후기 글 제목, 내용 등록버튼 눌러 테이블에 인서트하기
	@Override
	public int reviewWrite(ReviewListVO reviewList) throws SQLException {
		
		int result = 0;
		
		conn = ds.getConnection();
		
		String sql = " insert into tbl_reviews(review_no, review_title, review_contents, fk_user_no, fk_prod_no) "
					+ " values (review_seq.nextval, ?, ?, ?, ?) ";
		
		pstmt = conn.prepareStatement(sql);

		pstmt.setString(1 , reviewList.getReview_title());
		pstmt.setString(2 , reviewList.getReview_contents());
		pstmt.setInt(3 , reviewList.getFk_user_no());
		pstmt.setInt(4 , reviewList.getProd_no());
		
		result = pstmt.executeUpdate();		
		
		return result;
	}	// end of public int reviewWrite(ReviewListVO reviewList) throws SQLException {



	// 구매후기 글 삭제하기(상태 0으로 업데이트)
	@Override
	public int reviewDelete(String review_no) throws SQLException {
		
			int n = 0;
		
			String sql = " update tbl_reviews "
						+" set  review_status = 0 "
						+" where  review_no = ? ";
			
			try {
				
				conn = ds.getConnection();
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, review_no);
				
				n = pstmt.executeUpdate();
				
			} finally {
				close();
			}
			
			return n;
	}

	
	
	// 구매후기 글 수정하기
	@Override
	public int reviewEdit(ReviewListVO reviewList) throws SQLException {
		
		int result = 0;
		
		conn = ds.getConnection();
		
		String sql = " update tbl_reviews set review_title = ? , "
										+ " review_contents = ? , "
										+ " fk_user_no = ? , "
										+ " fk_prod_no = ? "
										+ " where review_no = ? ";
		
		pstmt = conn.prepareStatement(sql);

		pstmt.setString(1 , reviewList.getReview_title());
		pstmt.setString(2 , reviewList.getReview_contents());
		pstmt.setInt(3 , reviewList.getFk_user_no());
		pstmt.setInt(4 , reviewList.getProd_no());
		pstmt.setInt(5, reviewList.getReview_no());
		
		result = pstmt.executeUpdate();		
		
		return result;
	}

	
	// 구매후기 글 댓글 작성하는 메소드
		@Override
		public int commentWrite(ReviewListVO cmw) throws SQLException {
			
			int result = 0;
			
			try {
				
				conn = ds.getConnection();
				
				String sql  = " insert into tbl_comments(comment_no, fk_user_no, fk_review_no, comment_contents, comment_pwd, comment_regidate) "
							+ " values ((comment_seq.nextval), ?, ?, ?, ?, sysdate) ";
				
				pstmt = conn.prepareStatement(sql);
				
				
				pstmt.setInt(1, cmw.getFk_user_no());
				pstmt.setInt(2, cmw.getReview_no());
				pstmt.setString(3, cmw.getComment_contents());
				pstmt.setString(4, cmw.getComment_pwd());
		
				
				result = pstmt.executeUpdate();
				
			
			} finally {
				close();
			}
						
			return result;
					
	} // end of public int commentWrite(ReviewListVO cmw) throws SQLException {
		
		
		
	
	
	
	
	// 구매후기 글 댓글 삭제하는 메소드
	@Override
	public int commentDelete(String comment_no, String fk_review_no, String comment_PwdD) throws SQLException {
		
		int n = 0;
		
		String sql = " delete from tbl_comments "
				+ " where comment_no = ? and fk_review_no = ? and comment_pwd = ? ";
		
		try {
			
			conn = ds.getConnection();
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, comment_no);
			pstmt.setString(2, fk_review_no);
			pstmt.setString(3, comment_PwdD);
			
			n = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		
		return n;
	}

	
	// 구매후기 글 댓글 수정하는 메소드
	@Override
	public int commentEdit(ReviewListVO cmw) throws SQLException {
		
		
		int result = 0;
		
		try {
			
			conn = ds.getConnection();
			
			String sql  = " update tbl_comments "
						+ " set fk_user_no = ?, fk_review_no =?, comment_contents = ?, comment_pwd = ? "
						+ " where comment_no = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			
			pstmt.setInt(1, cmw.getFk_user_no());
			pstmt.setInt(2, cmw.getReview_no());
			pstmt.setString(3, cmw.getComment_contents());
			pstmt.setString(4, cmw.getComment_pwd());
			pstmt.setInt(5, cmw.getComment_no());
	
			
			result = pstmt.executeUpdate();
			
		
		} finally {
			close();
		}
					
		return result;
	}


	// 구매후기 글 봤을때 조회수 올려주는 메소드
	@Override
	public int setViewCount(String review_no) throws SQLException {
		
		int n = 0;
		
		String spl = " update tbl_reviews set review_viewcount =  review_viewcount+1 "
					+ " where review_no = ? ";
		
		try {
			
			conn = ds.getConnection();
			
			pstmt = conn.prepareStatement(spl);
			
			pstmt.setString(1, review_no);
			
			n = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		
		return n;
	}


	@Override
	public ReviewListVO ProductCarrier(String prod_no) throws SQLException {
		
		ReviewListVO pcarrier = new ReviewListVO();
		
		try {
			
			conn = ds.getConnection();
			
			String sql = " select prod_no, prod_name, prod_price, prod_thumnail "
						+ " from tbl_products "
						+ " where prod_no = ? ";
			
						
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, prod_no);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				pcarrier = new ReviewListVO();
				
				pcarrier.setProd_no(rs.getInt("prod_no"));
				pcarrier.setProd_name(rs.getString("prod_name"));
				pcarrier.setProd_price(rs.getInt("prod_price"));
				pcarrier.setProd_thumnail(rs.getString("prod_thumnail"));
			}
			
		} finally {
			close();
		}
		

		return pcarrier;
	}
	





	
	





	
	
	
	
	

} // end of public class ReviewListDAO_imple implements ReviewListDAO {
