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

import mypage.wish.domain.WishVO;
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
	
	
	// 페이징 처리를 위해 검색이 있는 경우, 검색이 없는 경우에 대한 총페이지수 알아오기 (상품 관리 페이지 10개씩)
	@Override
	public int getTotalPage(Map<String, String> paraMap) throws SQLException {
		
		int totalPage = 0;

		try {
			conn = ds.getConnection();
			
			String sql = " select ceil(count(*)/10) "
					   + " from tbl_products ";
			
			String searchFruit = paraMap.get("searchFruit");
			
			if(!searchFruit.isBlank()) {
				// 검색이 있는 경우
				sql += " where prod_name like '%'|| ? ||'%' ";
				
				// 컬럼명과 테이블명은 위치홀더(?)로 사용하면 꽝!!! 이다.
	            // 위치홀더(?)로 들어오는 것은 컬럼명과 테이블명이 아닌 오로지 데이터값만 들어온다.!!!!
				// 컬럼명은 값이 바뀌어야 하기 때문에 변수 처리 해준다.
			}
			
			
			sql += "ORDER BY prod_regidate desc ";
			
			pstmt = conn.prepareStatement(sql);

			if(!searchFruit.isBlank()) {
				pstmt.setString(1, searchFruit);	
				
			}
			
			rs = pstmt.executeQuery();
					
			rs.next();
			
			totalPage = rs.getInt(1); // 첫번째 컬럼 값, 컬럼명을 AS로 따로 안주었기 때문에 몇 번째 컬럼인지 기입
			
		} finally {
			close();
		}
		
		return totalPage;
		
	} // end of public int getTotalPage(Map<String, String> paraMap)
	
	
		
	
	// 페이징 처리한 모든 과일 목록 , 검색되어진 과일 목록 보여주기 (상품 관리 페이지)
	@Override
	public List<ProductVO> prdListPaging(Map<String, String> paraMap) throws SQLException {
		
		List<ProductVO> prdList = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
			
			String sql = " SELECT RNO, prod_no, prod_name, prod_cost, prod_price, prod_thumnail, prod_descript, prod_inventory, fk_season_no, prod_regidate, prod_status "
					   + " FROM "
					   + " ( "
					   + "    SELECT rownum AS RNO, prod_no, prod_name, prod_cost, prod_price, prod_thumnail, prod_descript, prod_inventory, fk_season_no, prod_regidate, prod_status "
					   + "    FROM "
					   + "    ( "
					   + "        select prod_no, prod_name, prod_cost, prod_price, prod_thumnail, prod_descript, prod_inventory, fk_season_no, prod_regidate, prod_status "
					   + "        from tbl_products ";
	
			String searchFruit = paraMap.get("searchFruit");
					
			// 검색 값이 이는 경우
			if (!searchFruit.isBlank()) { 
				sql += " where prod_name like '%'|| ? ||'%' ";  
				// 컬럼명과 테이블명은 위치홀더(?)로 사용하면 꽝!!! 이다.
	            // 위치홀더(?)로 들어오는 것은 컬럼명과 테이블명이 아닌 오로지 데이터값만 들어온다.!!!!
				// 컬럼명은 값이 바뀌어야 하기 때문에 변수 처리 해준다.
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
			int sizePerPage = 10;
			
			pstmt = conn.prepareCall(sql);
			
			
			if(!searchFruit.isBlank()) {
				// 검색이 있는 경우
				pstmt.setString(1, searchFruit);
				pstmt.setInt(2, (currentShowPageNo * sizePerPage) - (sizePerPage - 1) );
				pstmt.setInt(3, (currentShowPageNo * sizePerPage) );
			}
			else {
				// 검색이 없는 경우
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
				prdvo.setProd_status(rs.getInt("prod_status"));
				
				prdList.add(prdvo);
				
			} // end of while 
			
		} finally {
			close();
		}
		
		return prdList;
	} // end of public List<ProductVO> prdListPaging(Map<String, String> paraMap)
	
	
	
	
	// 뷰단(admin_product_management.jsp)에서 "페이징 처리시 보여주는 순번 공식" 에서 사용하기 위해 검색이 있는 또는 검색이 없는 회원의 총개수 알아오기 (상품 관리 페이지)
	@Override
	public int getTotalProductCount(Map<String, String> paraMap) throws SQLException {
		
		int getTotalProductCount = 0;

		try {
			conn = ds.getConnection();
			
			String sql = " select count(*) "
					   + " from tbl_products ";
			
			String searchFruit = paraMap.get("searchFruit");
			
			
			if(!searchFruit.isBlank()) {
				// 검색이 있는 경우
				sql += " where prod_name like '%'|| ? ||'%' ";
				
				// 컬럼명과 테이블명은 위치홀더(?)로 사용하면 꽝!!! 이다.
	            // 위치홀더(?)로 들어오는 것은 컬럼명과 테이블명이 아닌 오로지 데이터값만 들어온다.!!!!
				// 컬럼명은 값이 바뀌어야 하기 때문에 변수 처리 해준다.
			}
			
			pstmt = conn.prepareStatement(sql);
			
			if(!searchFruit.isBlank()) {
				pstmt.setString(1, searchFruit);	
				
			}
			
			rs = pstmt.executeQuery();
					
			rs.next();
			
			getTotalProductCount = rs.getInt(1); // 첫번째 컬럼 값, 컬럼명을 AS로 따로 안주었기 때문에 몇 번째 컬럼인지 기입
			
		} finally {
			close();
		}
		
		return getTotalProductCount;
		
	} // end of public int getTotalProductCount(Map<String, String> paraMap)
	
	
	
	// 계절 테이블 정보를 알아온다. (상품 등록 페이지 출력을 위한)
	@Override
	public List<ProductVO> seasonInfo() throws SQLException {
		
		List<ProductVO> seasonInfo = new ArrayList<>();
		
		try {
			
			conn = ds.getConnection();
	          
	        String sql = " select season_no, season_name "
	                   + " from tbl_seasons ";
	        pstmt =conn.prepareStatement(sql);
	        
	        rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				ProductVO prdvo = new ProductVO();
				
				prdvo.setSeason_no(rs.getInt("season_no"));
				prdvo.setSeason_name(rs.getString("season_name"));
		
				seasonInfo.add(prdvo);	
			}

		} finally {
			close();
		}
		
		return seasonInfo;
	} // end of public String seasonInfo()
	
	
	
	
	// 상품 테이블에 제품 정보 insert 하기
	@Override
	public int productRegister(ProductVO prdvo) throws SQLException {
		
		int result = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql = " insert into tbl_products(prod_no, prod_name, prod_cost, prod_price, prod_thumnail, prod_descript, prod_inventory, fk_season_no) "
					   + " values (prod_no.NEXTVAL, ?, ?, ?, ?, ?, ?, ?) ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, prdvo.getProd_name()); 		// 상품명
			pstmt.setInt(2, prdvo.getProd_cost());			// 원가
			pstmt.setInt(3, prdvo.getProd_price());			// 가격
			pstmt.setString(4, prdvo.getProd_thumnail()); 	// 상품썸네일
			pstmt.setString(5, prdvo.getProd_descript());  	// 상세이미지
			pstmt.setInt(6, prdvo.getProd_inventory());	 	// 재고
			pstmt.setInt(7, prdvo.getFk_season_no());		// 계절 번호
			
			result = pstmt.executeUpdate();
							
		} finally {
			close();
		}
			
		return result;
		
	} // end of public int productRegister(ProductVO prdvo)

	
	
	// 상품 테이블에 제품 정보 Update 하기
	@Override
	public int productUpdate(ProductVO prdvo, String prod_no) throws SQLException {
		
		int result = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql = " update tbl_products "
					   + " set prod_name = ?, prod_cost = ?, prod_price = ?, prod_thumnail = ?, prod_descript = ?, prod_inventory = ?, "
					   + "	   fk_season_no = ?, prod_status = ? "
					   + " where prod_no = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, prdvo.getProd_name()); 		// 상품명
			pstmt.setInt(2, prdvo.getProd_cost());			// 원가
			pstmt.setInt(3, prdvo.getProd_price());			// 가격
			pstmt.setString(4, prdvo.getProd_thumnail()); 	// 상품썸네일
			pstmt.setString(5, prdvo.getProd_descript());  	// 상세이미지
			pstmt.setInt(6, prdvo.getProd_inventory());	 	// 재고
			pstmt.setInt(7, prdvo.getFk_season_no());		// 계절 번호
			pstmt.setInt(8, prdvo.getProd_status());		// 판매 여부 번호
			
			pstmt.setInt(9, Integer.parseInt(prod_no));		// 상품번호
			
			result = pstmt.executeUpdate();
			
			
		} finally {
			close();
		}
		
		
		return result;
	} // end of public int productUpdate(ProductVO prdvo)
	
	
	
	
	
	
	// 페이징 처리를 위해 검색이 있는 경우, 검색이 없는 경우, 계절을 클릭 한 경우에 대한 총페이지수 알아오기 (상품 목록 페이지 16개씩)
	@Override
	public int getTotalPage2(Map<String, String> paraMap) throws SQLException {
		
		int totalPage2 = 0;

		try {
			conn = ds.getConnection();
			
			String sql = " select ceil(count(*)/16) "
					   + " from tbl_products "
					   + " where prod_status = 1 ";
			
			String searchFruit = paraMap.get("searchFruit");
			String seasonNo = paraMap.get("seasonNo");
			
			if(!searchFruit.isBlank()) {
				// 검색이 있는 경우
				sql += " and prod_name like '%'|| ? ||'%' ";
				
				// 컬럼명과 테이블명은 위치홀더(?)로 사용하면 꽝!!! 이다.
	            // 위치홀더(?)로 들어오는 것은 컬럼명과 테이블명이 아닌 오로지 데이터값만 들어온다.!!!!
				// 컬럼명은 값이 바뀌어야 하기 때문에 변수 처리 해준다.
			}
			
			// 계절 카테고리를 클력한 경우
			if (!seasonNo.isBlank()) {
				sql += " and fk_season_no = ? ";
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
			
			totalPage2 = rs.getInt(1); // 첫번째 컬럼 값, 컬럼명을 AS로 따로 안주었기 때문에 몇 번째 컬럼인지 기입
			
		} finally {
			close();
		}
		
		return totalPage2;
	} // end of public int getTotalPage(Map<String, String> paraMap)
	
	
	
	// 페이징 처리한 모든 과일 목록 , 검색되어진 과일목록 또는 계절 카테고리 클릭 시 과일 목록 보여주기 (상품 목록 페이지)
	@Override
	public List<ProductVO> prdListPaging2(Map<String, String> paraMap) throws SQLException {
		
		
		List<ProductVO> prdList = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
			
			String sql = " SELECT RNO, prod_no, prod_name, prod_cost, prod_price, prod_thumnail, prod_descript, prod_inventory, fk_season_no, prod_regidate, prod_status "
					   + " FROM "
					   + " ( "
					   + "    SELECT rownum AS RNO, prod_no, prod_name, prod_cost, prod_price, prod_thumnail, prod_descript, prod_inventory, fk_season_no, prod_regidate, prod_status "
					   + "    FROM "
					   + "    ( "
					   + "        select prod_no, prod_name, prod_cost, prod_price, prod_thumnail, prod_descript, prod_inventory, fk_season_no, prod_regidate, prod_status "
					   + "        from tbl_products "
					   + "		  where prod_status = 1 ";
	
			String searchFruit = paraMap.get("searchFruit");
			String seasonNo = paraMap.get("seasonNo");
			
			
			// 검색 값이 이는 경우
			if (!searchFruit.isBlank()) { 
				sql += " and prod_name like '%'|| ? ||'%' ";  
				// 컬럼명과 테이블명은 위치홀더(?)로 사용하면 꽝!!! 이다.
	            // 위치홀더(?)로 들어오는 것은 컬럼명과 테이블명이 아닌 오로지 데이터값만 들어온다.!!!!
				// 컬럼명은 값이 바뀌어야 하기 때문에 변수 처리 해준다.
			}
			
			
			// 계절 카테고리를 클력한 경우
			if (!seasonNo.isBlank()) {
				sql += " and fk_season_no = ? ";
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
				// 검색 또는 계절 번호를 선택하지 않은 경우 
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
				prdvo.setProd_status(rs.getInt("prod_status"));
				
				prdList.add(prdvo);
				
			} // end of while 
			
		} finally {
			close();
		}
		
		return prdList;
	} // end of public List<ProductVO> prdListPaging(Map<String, String> paraMap)
	
	
	
	// 찜 목록 확인해오기
	@Override
	public List<WishVO> wishList() throws SQLException {
		
		List<WishVO> wishList = new ArrayList<>();
		
		try {
			
			conn = ds.getConnection();
	          
	        String sql = " select wish_no, fk_user_no, fk_prod_no "
	                   + " from tbl_wish ";
	        pstmt =conn.prepareStatement(sql);
	        
	        rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				WishVO wsvo = new WishVO();
				
				wsvo.setWish_no(rs.getInt("wish_no"));
				wsvo.setFk_user_no(rs.getInt("fk_user_no"));
				wsvo.setFk_prod_no(rs.getInt("fk_prod_no"));
		
				wishList.add(wsvo);	
			}

		} finally {
			close();
		}
		
		return wishList;
	} // end of public List<WishVO> wishList()
	
	
	
	
	
	
	// 상품 번호를 가지고 상품 상세 페이지 보여주기
	@Override
	public ProductVO prdDetails(String prodNo) throws SQLException {

		
		ProductVO prdvo = null;
		
		try {
			conn = ds.getConnection();
			
			String sql  = " SELECT prod_no, prod_name, prod_cost, prod_price, prod_thumnail, prod_descript, prod_inventory, fk_season_no, prod_regidate, prod_status "
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
				prdvo.setProd_status(rs.getInt("prod_status"));
			}
			
		} finally {
			close();
		}	
		
		return prdvo;
		
	} // end of public List<ProductVO> prdDetails(String prodNo)
	
	
	
	/*
	// 페이징 처리를 위해 해당 상품 번호에 대한 후기가 있는 경우 총페이지수 알아오기 (5개씩)
	@Override
	public int getReviewTotalPage(Map<String, String> paraMap) throws SQLException {
		
		int reviewTotalPage = 0;

		try {
			conn = ds.getConnection();
			
			String sql = " select ceil(count(*)/5) "
					   + " from tbl_reviews "
					   + " where fk_prod_no = ? "
					   + " order by review_regidate desc ";
			
			pstmt = conn.prepareStatement(sql);


			pstmt.setString(1, paraMap.get("prodNo"));	
				
	
			rs = pstmt.executeQuery();
					
			rs.next();
			
			reviewTotalPage = rs.getInt(1);
			
		} finally {
			close();
		}
		
		return reviewTotalPage;
		
	} // end of public int getReviewTotalPage(Map<String, String> paraMap)
	*/
	
	
	
	// 뷰단(productdetail.jsp)에서 후기에서 사용하기 위해 입력받은 상품 번호에 대한 후기 총개수 알아오기
	@Override
	public int getReviewCount(Map<String, String> paraMap) throws SQLException {
		
		int getReviewCount = 0;

		try {
			conn = ds.getConnection();
			
			String sql = " select count(*) "
					   + " from tbl_reviews "
					   + " where fk_prod_no = ? ";
			
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, paraMap.get("prodNo"));	

			
			rs = pstmt.executeQuery();
					
			rs.next();
			
			getReviewCount = rs.getInt(1); // 첫번째 컬럼 값, 컬럼명을 AS로 따로 안주었기 때문에 몇 번째 컬럼인지 기입
			
		} finally {
			close();
		}
		
		return getReviewCount;
	} // end of public int getReviewProductCount(Map<String, String> paraMap)
	
	
	
	// 로그인 유저가 제품 구매이력이 있는 지 조회한다.
	@Override
	public boolean isOrder(Map<String, String> paraMap) throws SQLException {
		
		boolean bool = false;
	      
	    try {
	       conn = ds.getConnection();
	        
	       String sql = " select D.ordetail_no " + 
	                    " from tbl_orderdetail D JOIN tbl_order O " + 
	                    " on D.fk_order_no = O.order_no " + 
	                    " where D.fk_prod_no = ? and O.fk_user_no = ? ";
	         
	       pstmt = conn.prepareStatement(sql);
	       pstmt.setString(1, paraMap.get("fk_prod_no"));
	       pstmt.setString(2, paraMap.get("fk_user_no"));
	         
	       rs = pstmt.executeQuery();
	         
	       bool = rs.next();
	         
	    } finally {
	       close();
	    }
	      
	    return bool;
	} // end of public boolean isOrder(int user_no)
	
	
	

	
	// 후기 테이블에서 입력받은 상품번호에 대한 페이징 처리한 후기 리스트를 조회해온다.
	@Override
	public List<ProductVO> prd_reviewList(Map<String, String> paraMap) throws SQLException {
		
		List<ProductVO> prd_reviewList = new ArrayList<>();

		try {
			conn = ds.getConnection();

			String sql = " SELECT RNO, review_no, review_title, review_contents, T.userid, review_regidate "
					   + " FROM "
					   + " ( "
					   + "    SELECT rownum AS RNO, review_no, review_title, review_contents, V.userid, review_regidate "
					   + "    FROM "
					   + "    ( "
					   + "    select review_no, review_title, review_contents, M.userid, review_regidate "
					   + "    from tbl_reviews R "
					   + "    join tbl_member M on R.fk_user_no = M.user_no "
					   + "    where R.fk_prod_no = ? "
					   + "    order by review_regidate desc "
					   + "    ) V "
					   + " ) T "
					   + " WHERE T.RNO BETWEEN ? AND ? ";
			
			/*
			=== 페이징처리의 공식 ===
			where RNO between (조회하고자하는페이지번호 * 한페이지당보여줄행의개수) - (한페이지당보여줄행의개수 - 1) and (조회하고자하는페이지번호 * 한페이지당보여줄행의개수);
			*/
			
			int currentShowPageNo = Integer.parseInt(paraMap.get("currentShowPageNo"));
			int sizePerPage = 10;

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paraMap.get("prodNo"));
			pstmt.setInt(2, (currentShowPageNo * sizePerPage) - (sizePerPage - 1) );
			pstmt.setInt(3, (currentShowPageNo * sizePerPage) );
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				ProductVO prdvo = new ProductVO();
				
				prdvo.setReview_no(rs.getInt("review_no"));
				prdvo.setReview_title(rs.getString("review_title"));
				prdvo.setReview_contents(rs.getString("review_contents"));
				prdvo.setUserid(rs.getString("userid"));
				prdvo.setReview_regidate(rs.getString("review_regidate"));
				
				prd_reviewList.add(prdvo);
			} // end of while(rs.next())----------------------------------

		} finally {
			close();
		}

		return prd_reviewList;
		
	} // end of public List<ProductVO> prd_review(String prodNo)
	
	
	
	
	
	
	// 문의 테이블에서 입력받은 상품번호에 대한 페이징 처리한 문의 리스트를 조회해온다.
	@Override
	public List<ProductVO> prd_qnaList(Map<String, String> paraMap) throws SQLException {
		
		List<ProductVO> prd_qnaList = new ArrayList<>();

		try {
			conn = ds.getConnection();	
			
			String sql = " SELECT RNO, qna_no, qna_title, qna_contents, T.userid, T.user_no, qna_regidate "
					   + " FROM "
					   + " ( "
					   + "   SELECT rownum AS RNO, qna_no, qna_title, qna_contents, V.userid, V.user_no, qna_regidate "
					   + "   FROM "
					   + "   ( "
					   + "   select qna_no, qna_title, qna_contents, M.userid, M.user_no, qna_regidate "
					   + "   from tbl_qna Q "
					   + "   join tbl_member M "
				 	   + "   on Q.fk_user_no = M.user_no "
					   + "   where Q.fk_prod_no = ? "
					   + "   order by qna_regidate desc "
					   + "   ) V "
					   + " ) T "
					   + " WHERE T.RNO BETWEEN ? AND ? ";
			
			/*
			=== 페이징처리의 공식 ===
			where RNO between (조회하고자하는페이지번호 * 한페이지당보여줄행의개수) - (한페이지당보여줄행의개수 - 1) and (조회하고자하는페이지번호 * 한페이지당보여줄행의개수);
			*/
			
			int currentShowPageNo = Integer.parseInt(paraMap.get("currentShowPageNo"));
			int sizePerPage = 10;

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paraMap.get("prodNo"));
			pstmt.setInt(2, (currentShowPageNo * sizePerPage) - (sizePerPage - 1) );
			pstmt.setInt(3, (currentShowPageNo * sizePerPage) );
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				ProductVO prdvo = new ProductVO();
				
				prdvo.setQna_no(rs.getInt("qna_no"));
				prdvo.setQna_title(rs.getString("qna_title"));
				prdvo.setQna_contents(rs.getString("qna_contents"));
				prdvo.setUserid(rs.getString("userid"));
				prdvo.setUser_no(rs.getInt("user_no"));
				prdvo.setQna_regidate(rs.getString("qna_regidate"));
				
				prd_qnaList.add(prdvo);
			} // end of while(rs.next())----------------------------------

		} finally {
			close();
		}

		
		return prd_qnaList;
	} // end of public QnaListVO qnaList(String prodNo)
	
	
	
	// 뷰단(productdetail.jsp)에서 문의에서 사용하기 위해 입력받은 상품 번호에 대한 문의 총개수 알아오기
	@Override
	public int getQnaCount(Map<String, String> paraMap) throws SQLException {
		
		int getQnaCount = 0;

		try {
			conn = ds.getConnection();
			
			String sql = " select count(*) "
					   + " from tbl_qna "
					   + " where fk_prod_no = ? ";
			
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, paraMap.get("prodNo"));	

			
			rs = pstmt.executeQuery();
					
			rs.next();
			
			getQnaCount = rs.getInt(1); // 첫번째 컬럼 값, 컬럼명을 AS로 따로 안주었기 때문에 몇 번째 컬럼인지 기입
			
		} finally {
			close();
		}
		
		return getQnaCount;
		
	} // end of public int getQnaCount
	
	
	
	
	// 상품 상세페이지 내 입력받은 상품번호에 후기 수량 표시를 위해 후기 개수를 조회해온다.
	@Override
	public int review_cnt(String prodNo) throws SQLException {
		
		int review_cnt = 0;
		
		try {
			conn = ds.getConnection();
	          
	        String sql = " select count(*) "
	                   + " from tbl_reviews "
	                   + " where fk_prod_no = ? ";	
	        pstmt =conn.prepareCall(sql);
	        pstmt.setString(1, prodNo);
	        
	        rs = pstmt.executeQuery();
			
	        rs.next();
	        
	        review_cnt = rs.getInt(1);
		} finally {
			close();
		}
		
		return review_cnt;
	} // end of public int review_cnt(String prodNo)
	
	

	

	

	
	
}
