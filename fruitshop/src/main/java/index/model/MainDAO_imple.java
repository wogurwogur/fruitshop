package index.model;

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

import index.domain.MainVO;

public class MainDAO_imple implements MainDAO {

	private DataSource ds;	// DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다.
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	
	public MainDAO_imple() {	
		try {
			Context initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc/semioracle");		// 파라미터가 web.xml, tomcat 내 context.xml 에 정의한 name 과 일치 해야 함
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}// end of public MainDAO_imple() --------------------
	
	// 사용한 자원을 반납하는 close() 메소드 생성하기
	private void close() {
		try {
			if (rs != null) 	{ rs.close(); rs = null;}
			if (pstmt != null)  { pstmt.close(); pstmt = null;}
			if (conn != null)   { conn.close(); conn = null;}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}// end of private void close() -----------------
	
	///////////////////////////////////////////////////////////////
	// == custom method start == //
	
	// 메인페이지에 보여지는 상품 이미지 파일 명을 모두 조회(select)하는 메소드
	@Override
	public List<MainVO> imgaeSelectAll() throws SQLException {
		
		List<MainVO> imgList = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
			
			String sql  = " SELECT imgno, imgname, imgfilename "
						+ "   FROM tbl_main_page "
						+ " ORDER BY imgno ";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MainVO imgvo = new MainVO();
				imgvo.setImgno(rs.getInt("imgno"));
				imgvo.setImgname(rs.getString("imgname"));
				imgvo.setImgfilename(rs.getString("imgfilename"));
				
				imgList.add(imgvo);
			}
			
		} finally {
			close();
		}
	
		return imgList;
	}// end of public List<MainVO> imgaeSelectAll() throws SQLException -----------------------

}
