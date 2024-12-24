package product.model;


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

import product.domain.ProductVO;
import review.domain.ReviewListVO;

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
	
	
	// 페이징 처리를 위해 검색이 있는 경우, 검색이 없는 경우, 계절을 클릭 한 경우에 대한 총페이지수 알아오기
	@Override
	public int getTotalPage(Map<String, String> paraMap) throws SQLException {
		
		int totalPage = 0;

		try {
			conn = ds.getConnection();
			
			String sql = " select ceil(count(*)/16) "
					   + " from tbl_products ";
			
			String searchFruit = paraMap.get("searchFruit");
			String seasonNo = paraMap.get("seasonNo");
			
			if(!searchFruit.isBlank()) {
				// 검색이 있는 경우
				sql += " where prod_name like '%'|| ? ||'%' ";
				
				// 컬럼명과 테이블명은 위치홀더(?)로 사용하면 꽝!!! 이다.
	            // 위치홀더(?)로 들어오는 것은 컬럼명과 테이블명이 아닌 오로지 데이터값만 들어온다.!!!!
				// 컬럼명은 값이 바뀌어야 하기 때문에 변수 처리 해준다.
			}
			
			// 계절 카테고리를 클력한 경우
			if (!seasonNo.isBlank()) {
				sql += " where fk_season_no = ? ";
			}
			
			
			sql += "ORDER BY prod_regidate desc ";
			
			pstmt = conn.prepareStatement(sql);

			if(!searchFruit.isBlank()) {
				pstmt.setString(1, searchFruit);	
				
			}
			
			if(!seasonNo.isBlank()) {
				pstmt.setString(1, seasonNo);	
				
			}
			
			rs = pstmt.executeQuery();
					
			rs.next();
			
			totalPage = rs.getInt(1); // 첫번째 컬럼 값, 컬럼명을 AS로 따로 안주었기 때문에 몇 번째 컬럼인지 기입
			
		} finally {
			close();
		}
		
		return totalPage;
	} // end of public int getTotalPage(Map<String, String> paraMap)
	
	
	
	// 페이징 처리한 모든 과일 목록 , 검색되어진 과일목록 또는 계절 카테고리 클릭 시 과일 목록 보여주기 //
	@Override
	public List<ProductVO> prdListPaging(Map<String, String> paraMap) throws SQLException {
		
		
		List<ProductVO> prdList = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
			
			String sql = " SELECT RNO, prod_no, prod_name, prod_cost, prod_price, prod_thumnail, prod_descript, prod_inventory, fk_season_no, prod_regidate "
					   + " FROM "
					   + " ( "
					   + "    SELECT rownum AS RNO, prod_no, prod_name, prod_cost, prod_price, prod_thumnail, prod_descript, prod_inventory, fk_season_no, prod_regidate "
					   + "    FROM "
					   + "    ( "
					   + "        select prod_no, prod_name, prod_cost, prod_price, prod_thumnail, prod_descript, prod_inventory, fk_season_no, prod_regidate "
					   + "        from tbl_products ";
	
			String searchFruit = paraMap.get("searchFruit");
			String seasonNo = paraMap.get("seasonNo");
			
			
			// 검색 값이 이는 경우
			if (!searchFruit.isBlank()) { 
				sql += " where prod_name like '%'|| ? ||'%' ";  
				// 컬럼명과 테이블명은 위치홀더(?)로 사용하면 꽝!!! 이다.
	            // 위치홀더(?)로 들어오는 것은 컬럼명과 테이블명이 아닌 오로지 데이터값만 들어온다.!!!!
				// 컬럼명은 값이 바뀌어야 하기 때문에 변수 처리 해준다.
			}
			
			
			// 계절 카테고리를 클력한 경우
			if (!seasonNo.isBlank()) {
				sql += " where fk_season_no = ? ";
			}
			
			
			sql += " order by prod_regidate desc "
				+  "  ) V "
				+  " ) T "
				+  " WHERE T.RNO BETWEEN ? AND ? ";
			
			/*
				=== 페이징처리의 공식 ===
				where RNO between (조회하고자하는페이지번호 * 한페이지당보여줄행의개수) - (한페이지당보여줄행의개수 - 1) and (조회하고자하는페이지번호 * 한페이지당보여줄행의개수);
			*/
			
			
			int currentShowPageNo = Integer.parseInt(paraMap.get("currentShowPageNo"));
			int sizePerPage = 16;
			
			pstmt = conn.prepareCall(sql);
			
			
			if(!searchFruit.isBlank()) {
				// 검색이 있는 경우
				pstmt.setString(1, searchFruit);
				pstmt.setInt(2, (currentShowPageNo * sizePerPage) - (sizePerPage - 1) );
				pstmt.setInt(3, (currentShowPageNo * sizePerPage) );
			}
			else if (!seasonNo.isBlank()) {
				// 계절 카테고리를 클릭한 경우
				pstmt.setString(1, seasonNo);
				pstmt.setInt(2, (currentShowPageNo * sizePerPage) - (sizePerPage - 1) );
				pstmt.setInt(3, (currentShowPageNo * sizePerPage) );
			}
			else {
				// 검색 또는 계절 번호를 선택하지 않은 경우 경우 
				pstmt.setInt(1, (currentShowPageNo * sizePerPage) - (sizePerPage - 1) );
				pstmt.setInt(2, (currentShowPageNo * sizePerPage) );
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
				
			} // end of while 
			
		} finally {
			close();
		}
		
		return prdList;
	} // end of public List<ProductVO> prdListPaging(Map<String, String> paraMap)
	
	
	
	// 상품 번호를 가지고 상품 상세 페이지 보여주기
	@Override
	public ProductVO prdDetails(String prodNo) throws SQLException {

		
		ProductVO prdvo = null;
		
		try {
			conn = ds.getConnection();
			
			String sql  = " SELECT prod_no, prod_name, prod_cost, prod_price, prod_thumnail, prod_descript, prod_inventory, fk_season_no, prod_regidate "
						+ " FROM tbl_products " 			
						+ " WHERE prod_no = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, prodNo);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				prdvo = new ProductVO();
				
				prdvo.setProd_no(rs.getInt("prod_no"));
				prdvo.setProd_name(rs.getString("prod_name"));
				prdvo.setProd_cost(rs.getInt("prod_cost"));
				prdvo.setProd_price(rs.getInt("prod_price"));
				prdvo.setProd_thumnail(rs.getString("prod_thumnail"));
				prdvo.setProd_descript(rs.getString("prod_descript"));
				prdvo.setProd_inventory(rs.getInt("prod_inventory"));
				prdvo.setFk_season_no(rs.getInt("fk_season_no"));
				prdvo.setProd_regidate(rs.getString("prod_regidate"));
			}
			
			
			
			
		} finally {
			close();
		}	
		
		return prdvo;
		
	} // end of public List<ProductVO> prdDetails(String prodNo)
	
	
	// 입력받은 상품번호에 대한 reivew 리스트를 조회해온다.
	@Override
	public ReviewListVO reviewList(String prodNo) throws SQLException {
		
		return null;
	}
	
		
}
