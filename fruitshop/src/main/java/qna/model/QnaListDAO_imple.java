package qna.model;

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

import qna.domain.QnaListVO;
import review.domain.ReviewListVO;

public class QnaListDAO_imple implements QnaListDAO {
	
	private DataSource ds;	// DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다.
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	
	public QnaListDAO_imple() {
		
		try {
		Context initContext = new InitialContext();
	    Context envContext  = (Context)initContext.lookup("java:/comp/env");
	    ds = (DataSource)envContext.lookup("jdbc/semioracle"); // webxml에서 myoracle -> jdbc/semioracle 바뀌어야됨
	    
	    
		} catch(NamingException e) {
			e.printStackTrace();
		} 
		
	} // end of public QnaListDAO_imple() {
	
	
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

	
	// 문의 게시글(리뷰)리스트 전부 보여주는 메소드
	@Override
	public List<QnaListVO> qnaListall() throws SQLException {
		
		List<QnaListVO> qnaList = new ArrayList<>();
		
		try {
			
			conn = ds.getConnection();
			
			String sql = " select q.qna_no, q.qna_title, q.qna_viewcount, q.qna_regidate, q.fk_user_no, "
					+ " m.user_no, func_userid_block(m.userid) as userid , p.prod_name "
					+ " from tbl_qna q INNER JOIN tbl_member m "
					+ " on q.fk_user_no = m.user_no "
					+ " INNER JOIN tbl_products p "
					+ " on q.fk_prod_no = p.prod_no "
					+ " where q.qna_status = 1"
					+ " order by qna_no desc ";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				QnaListVO qnavo = new QnaListVO();
				qnavo.setQna_no(rs.getInt("qna_no"));
				qnavo.setQna_title(rs.getString("qna_title"));
				qnavo.setQna_viewcount(rs.getString("qna_viewcount"));
				qnavo.setQna_regidate(rs.getString("qna_regidate"));
				qnavo.setFk_user_no(rs.getInt("Fk_user_no"));
				qnavo.setUserid(rs.getString("userid"));
				qnavo.setProd_name(rs.getString("prod_name"));
				qnaList.add(qnavo);
								
				
			} // end of while ----------------		

									
		} finally {
			close();
		}
			
		
		  
		return qnaList;
	}

	// qna 게시판글 클릭시 글 내용 보여주는 메소드
	@Override
	public QnaListVO qnaReadAll(String qna_no) throws SQLException {
		
		QnaListVO qvo = null;	
		
		try {
			conn=ds.getConnection();
		
		
		String sql = " select q.qna_answer, q.qna_regidate , q.qna_no, q.qna_title, q.fk_user_no, q.qna_contents, "
				+ " m.user_no, func_userid_block(m.userid) as userid , p.prod_no, p.prod_name, p.prod_price, p.prod_thumnail "
				+ " from tbl_qna q "
				+ " INNER JOIN tbl_member m "
				+ " on q.fk_user_no = m.user_no "
				+ " INNER JOIN tbl_products p "
				+ " on q.fk_prod_no = p.prod_no "
				+ " where q.qna_no = ? ";
		
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, qna_no);
			
			rs = pstmt.executeQuery();
		
			
			if (rs.next() ) {
				
				qvo = new QnaListVO();
				
				qvo.setQna_no(rs.getInt("qna_no"));
				qvo.setQna_title(rs.getString("qna_title"));
				qvo.setQna_contents(rs.getString("qna_contents"));
				qvo.setQna_regidate(rs.getString("qna_regidate"));
				qvo.setQna_answer(rs.getString("qna_answer"));
				qvo.setUserid(rs.getString("userid"));
				qvo.setProd_no(rs.getInt("prod_no"));
				qvo.setProd_thumnail(rs.getString("prod_thumnail"));
				qvo.setProd_name(rs.getString("prod_name"));
				qvo.setProd_price(rs.getInt("prod_price"));
				
				
				
			}
			
		} finally{
			close();
		}
		
		return qvo;
	}

	
	
	
	// qna 게시판 글 조회수 올려주는 메소드
	@Override
	public int setViewCount(String qna_no) throws SQLException {
		
		int n = 0;
		
		String spl = " update tbl_qna set qna_viewcount =  qna_viewcount+1 "
					+ " where qna_no = ? ";
		
		try {
			
			conn = ds.getConnection();
			
			pstmt = conn.prepareStatement(spl);
			
			pstmt.setString(1, qna_no);
			
			n = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		
		return n;
	}

	
	
	// qna 게시판 글 쓰기 메소드
	@Override
	public int qnaWrite(QnaListVO qnaWrite) throws SQLException {

		int result = 0;
		
		conn = ds.getConnection();
		
		String sql = " insert into tbl_qna(qna_no, qna_title, qna_contents, fk_user_no, fk_prod_no) "
					+ " values (review_seq.nextval, ?, ?, ?, ?) ";
		
		pstmt = conn.prepareStatement(sql);

		pstmt.setString(1 , qnaWrite.getQna_title());
		pstmt.setString(2 , qnaWrite.getQna_contents());
		pstmt.setInt(3 , qnaWrite.getFk_user_no());
		pstmt.setInt(4 , qnaWrite.getProd_no());
		
		result = pstmt.executeUpdate();		
		
		return result;
		
	}

	
	// qna 게시판 글쓰기 상품 등록하기 리스트 보여주기
	@Override
	public List<QnaListVO> qproductFind() throws SQLException {
		
		List<QnaListVO> qproductList = new ArrayList<>();
		
		try {
			
			conn=ds.getConnection();
		
			String sql = " select prod_no, prod_name, prod_price, prod_thumnail "
						+ " from tbl_products "
						+ " where prod_status = 1 ";
		
				pstmt = conn.prepareStatement(sql);
				
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
				
					QnaListVO qpl = new QnaListVO();
				
				qpl.setProd_no(rs.getInt("prod_no"));
				qpl.setProd_name(rs.getString("prod_name"));
				qpl.setProd_price(rs.getInt("prod_price"));
				qpl.setProd_thumnail(rs.getString("prod_thumnail"));
				
								
				qproductList.add(qpl);
				
				}
				
		} finally {
			close();
		}
		
		
			
		return qproductList;
	}

	
	
	// qna 게시판 글 삭제하는 메소드
	@Override
	public int qnaDelete(String qna_no) throws SQLException {
		
		int n = 0;
		
		String sql = " update tbl_qna "
					+" set qna_status = 0 "
					+" where  qna_no = ? ";
		
		try {
			
			conn = ds.getConnection();
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, qna_no);
			
			n = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		
		return n;
	}

	
	
	// qna 글 상품등록 클릭했을때 reviewWrite로 보내주기
	@Override
	public QnaListVO productSelect(String prod_no) throws SQLException {
		
		QnaListVO qvo = null;
		
		try {
			
			conn = ds.getConnection();
			
			String sql = " select prod_no, prod_name, prod_price, prod_thumnail "
						+ " from tbl_products "
						+ " where prod_no = ? ";
			
						
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, prod_no);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				qvo = new QnaListVO();
				
				qvo.setProd_no(rs.getInt("prod_no"));
				qvo.setProd_name(rs.getString("prod_name"));
				qvo.setProd_price(rs.getInt("prod_price"));
				qvo.setProd_thumnail(rs.getString("prod_thumnail"));
			}
			
		} finally {
			close();
		}
		

		return qvo;
	}


	
	
	// qna 게시판 글 수정하는 메소드
	@Override
	public int qnaEdit(QnaListVO qnaList) throws SQLException {
		
		int result = 0;
		
		conn = ds.getConnection();
		
		String sql = " update tbl_qna set qna_title = ? , "
										+ " qna_contents = ? , "
										+ " fk_user_no = ? , "
										+ " fk_prod_no = ? "
										+ " where qna_no = ? ";
		
		pstmt = conn.prepareStatement(sql);

		pstmt.setString(1 , qnaList.getQna_title());
		pstmt.setString(2 , qnaList.getQna_contents());
		pstmt.setInt(3 , qnaList.getFk_user_no());
		pstmt.setInt(4 , qnaList.getProd_no());
		pstmt.setInt(5, qnaList.getQna_no());
		
		result = pstmt.executeUpdate();		
		
		return result;
	}

	
	// qna 글 페이징 처리를 위한 검색이 있는 또는 검색이 없는 회원에 대한 총페이지수 알아오기 //
	@Override
	public int getTotalPage(Map<String, String> paraMap) throws SQLException {
		
		int totalPage=0;
		
		try {
			conn = ds.getConnection();
			
			String sql = " select CEIL(COUNT(*)/?) "
					+ " from tbl_qna "
					+ " where qna_status = 1 ";
			
			String colname = paraMap.get("searchType");
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

	/* >>> 뷰단(memberList.jsp)에서 "페이징 처리시 보여주는 순번 공식" 에서 사용하기 위해 
    검색이 있는 또는 검색이 없는 qna 글 총개수 알아오기 시작 <<< */
	@Override
	public int getTotalMemberCount(Map<String, String> paraMap) throws SQLException {
		
		int totalMemberCount = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql 	= " SELECT COUNT(*) "
						+ " FROM tbl_qna "
						+ " where qna_status = 1 ";
			
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
			
			totalMemberCount = rs.getInt(1);		// select 된 것들 중 첫 번째 컬럼
			
		} finally {
			close();
		}
		
		return totalMemberCount;
	}

	// qna 글 페이징 처리를 한 모든 회원 목록 또는 검색되어진 회원 목록 보여주기 **** //
	@Override
	public List<QnaListVO> select_Member_paging(Map<String, String> paraMap) throws SQLException {
		
		List<QnaListVO> qnaList = new ArrayList<>();
		
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
			    	    + "    SELECT ROW_NUMBER() OVER (ORDER BY A.qna_no DESC) AS RNO, "
			    	    + "           A.qna_answer, A.qna_status, A.qna_contents, A.qna_no, A.qna_title, "
			    	    + "           A.qna_viewcount, A.qna_regidate, A.user_no, A.userid, "
			    	    + "           A.prod_name "
			    	    + "    FROM ( "
			    	    + "        SELECT q.qna_answer, q.qna_status, q.qna_no, q.qna_title, q.qna_viewcount, "
			    	    + "               q.qna_regidate, q.fk_user_no, q.qna_contents, "
			    	    + "               m.user_no, func_userid_block(m.userid) AS userid, "
			    	    + "               p.prod_name "
			    	    + "        FROM tbl_qna q "
			    	    + "        INNER JOIN tbl_member m ON q.fk_user_no = m.user_no "
			    	    + "        INNER JOIN tbl_products p ON q.fk_prod_no = p.prod_no "
			    	    + "        WHERE q.qna_status = 1 "
			    	    + "          AND q." + colname + " LIKE '%' || ? || '%' "
			    	    + "    ) A "
			    	    + " ) C "
			    	    + " WHERE C.RNO BETWEEN ? AND ? ";
			    	} else {
			    	    // 검색 조건이 없는 경우
			    	    sql = " SELECT * "
			    	        + " FROM ( "
			    	        + "    SELECT ROWNUM AS RNO, A.qna_status, A.qna_contents, A.qna_no, A.qna_title, "
			    	        + "           A.qna_answer, A.qna_viewcount, A.qna_regidate, A.user_no, A.userid, A.prod_name "
			    	        + "    FROM ( "
			    	        + "        SELECT q.qna_answer, q.qna_status, q.qna_no, q.qna_title, q.qna_viewcount, q.qna_regidate, "
			    	        + "               q.fk_user_no, q.qna_contents, m.user_no, func_userid_block(m.userid) AS userid, p.prod_name "
			    	        + "        FROM tbl_qna q "
			    	        + "        INNER JOIN tbl_member m ON q.fk_user_no = m.user_no "
			    	        + "        INNER JOIN tbl_products p ON q.fk_prod_no = p.prod_no "
			    	        + "        WHERE q.qna_status = 1 "
			    	        + "        ORDER BY q.qna_no DESC "
			    	        + "    ) A "
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
				
				QnaListVO qvo = new QnaListVO();
				
				qvo.setQna_no(rs.getInt("qna_no"));
				qvo.setQna_title(rs.getString("qna_title"));
				qvo.setQna_viewcount(rs.getString("qna_viewcount"));
				qvo.setQna_regidate(rs.getString("qna_regidate"));
				qvo.setQna_answer(rs.getString("qna_answer"));
				qvo.setFk_user_no(rs.getInt("user_no"));
				qvo.setUserid(rs.getString("userid"));
				qvo.setProd_name(rs.getString("prod_name"));

				
				qnaList.add(qvo);
				
				
				
			}
			
			
		} finally {
			close();
		}
		
		
		return qnaList;
	}

	// 관리자 qna 글 답글 달기
	@Override
	public int qnaReply(QnaListVO qnaReply) throws SQLException {
		
		int result = 0;
		
		conn = ds.getConnection();
		
		String sql = " update tbl_qna "
					+ " set qna_answer = ? "
					+ " where qna_no = ? ";
		
		pstmt = conn.prepareStatement(sql);

		pstmt.setString(1 , qnaReply.getQna_answer());
		pstmt.setInt(2 , qnaReply.getQna_no());

		result = pstmt.executeUpdate();		
		
		return result;
		
	}
	
	
}
