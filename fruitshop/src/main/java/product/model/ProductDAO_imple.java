package product.model;

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

import product.domain.ProductVO;

public class ProductDAO_imple implements ProductDAO {

	private DataSource ds;	// DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다.
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	
	public ProductDAO_imple() {	
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
	
	
	// 상품페이지 출력을 위해 상품 정보를 등록일순으로 모두 조회(select)하는 메소드
	@Override
	public List<ProductVO> productListSelectAll() throws SQLException {
		
		List<ProductVO> prdList = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
			
			String sql  = " SELECT prod_no, prod_name, prod_cost, prod_price, prod_thumnail, prod_descript, prod_inventory, fk_season_no, prod_regidate "
						+ " FROM tbl_products " 			
						+ " ORDER BY prod_regidate DESC ";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				ProductVO prdvo = new ProductVO();
				
				prdvo.setProd_no(rs.getInt("prod_no"));
				prdvo.setProd_name(rs.getString("prod_name"));
				prdvo.setProd_cost(rs.getInt("prod_cost"));
				prdvo.setProd_price(rs.getInt("prod_price"));
				prdvo.setProd_thumnail(rs.getString("prod_thumnail"));
				prdvo.setProd_descript(rs.getString("prod_descript"));
				prdvo.setProd_inventory(rs.getInt("prod_inventory"));
				prdvo.setFk_season_no(rs.getInt("fk_season_no"));
				prdvo.setProd_regidate(rs.getString("prod_regidate"));
				
				prdList.add(prdvo);
			}
					
		} finally {
			close();
		}	
		
		return prdList;
	} // end of public List<ProductVO> productListSelectAll()
	
	
	
	// 상품페이지 카테고리(계절)별로 조회(select) 하는 메소드
	@Override
	public List<ProductVO> seasonProduct(String seasonNo) throws SQLException {
		
		List<ProductVO> prdList = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
			
			String sql  = " SELECT prod_no, prod_name, prod_cost, prod_price, prod_thumnail, prod_descript, prod_inventory, fk_season_no, prod_regidate "
						+ " FROM tbl_products "; 			
			
			
			
			if(!seasonNo.isBlank()) {
				sql += 	" WHERE fk_season_no = ? ";
			}
			
			sql += " ORDER BY prod_regidate DESC ";
			
			
			pstmt = conn.prepareStatement(sql);
			
			
			if(!seasonNo.isBlank()) {
				pstmt.setString(1, seasonNo);
			}
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				ProductVO prdvo = new ProductVO();
				
				prdvo.setProd_no(rs.getInt("prod_no"));
				prdvo.setProd_name(rs.getString("prod_name"));
				prdvo.setProd_cost(rs.getInt("prod_cost"));
				prdvo.setProd_price(rs.getInt("prod_price"));
				prdvo.setProd_thumnail(rs.getString("prod_thumnail"));
				prdvo.setProd_descript(rs.getString("prod_descript"));
				prdvo.setProd_inventory(rs.getInt("prod_inventory"));
				prdvo.setFk_season_no(rs.getInt("fk_season_no"));
				prdvo.setProd_regidate(rs.getString("prod_regidate"));
				
				prdList.add(prdvo);
			}
					
		} finally {
			close();
		}	
		
		return prdList;
		
	} // end of public List<ProductVO> seasonProduct(String seasonNo)
	
	
	
}
