package mypage.wish.model;

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

import cart.domain.CartVO;
import mypage.wish.domain.WishVO;
import product.domain.ProductVO;

public class WishDAO_imple implements WishDAO {
	
	private DataSource ds;	// DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다.
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public WishDAO_imple() {	
		
		try {
			Context initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc/semioracle");		// 파라미터가 web.xml, tomcat 내 context.xml 에 정의한 name 과 일치 해야 함
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}// end of public CartDAO_imple() --------------------
	
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
	
	
	
	// 관심상품 리스트
	@Override
	public List<WishVO> wishListSelectAll() throws SQLException {
		
		List<WishVO> wishList = new ArrayList<>();
		
		
		try {
	        conn = ds.getConnection();

	        String sql = " ";

	        pstmt = conn.prepareStatement(sql);
	        rs = pstmt.executeQuery();


	    } finally {
	        close();
	    }
		
		
		return wishList;
	}

}
