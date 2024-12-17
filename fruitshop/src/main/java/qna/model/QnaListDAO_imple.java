package qna.model;

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

import qna.domain.QnaListVO;

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
		
	
	
}
