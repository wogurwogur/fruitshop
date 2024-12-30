package mypage.wish.model;

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
	}// end of public WishDAO_imple() --------------------
	
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
	public List<WishVO> wishListSelectAll(int user_no) throws SQLException {
		
		List<WishVO> wishList = new ArrayList<>();
		
		
		try {
	        conn = ds.getConnection();

	        String sql = " SELECT w.fk_user_no, w.wish_no, "
	        		   + " p.prod_no, p.prod_thumnail, p.prod_name, p.prod_price "
	        		   + " FROM tbl_wish w "
	        		   + " INNER JOIN tbl_products p "
	        		   + " ON w.fk_prod_no = p.prod_no "
	        		   + " where fk_user_no = ? ";

	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, user_no);
	        rs = pstmt.executeQuery();
	        
	        while (rs.next()) {
	            // ProductVO 설정
	            ProductVO pdvo = new ProductVO();
	            pdvo.setProd_no(rs.getInt("prod_no"));
	            pdvo.setProd_thumnail(rs.getString("prod_thumnail"));
	            pdvo.setProd_name(rs.getString("prod_name"));
	            pdvo.setProd_price(rs.getInt("prod_price"));

	            // CartVO 설정
	            WishVO wishvo = new WishVO();
	            wishvo.setWish_no(rs.getInt("wish_no"));
	            wishvo.setProduct(pdvo);

	            wishList.add(wishvo);
	        }
	        

	    } finally {
	        close();
	    }
		
		
		return wishList;
	}
	
	
	// 관심상품 1행 삭제(X버튼 클릭시)
	@Override
	public boolean deleteWishItem(int wish_no) throws SQLException {
		
		boolean isDeleted = false;

        try {
            conn = ds.getConnection();
            String sql = " delete from tbl_wish "
            		   + " where wish_no = ? ";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, wish_no);

            int n = pstmt.executeUpdate();
            
            if (n != 0) {
            	isDeleted = true;
            }
            
            
        } finally {
            close();
        }

        return isDeleted;
		
	}
	
	
	// 관심 상품 비우기 // 
	@Override
	public boolean WishDeleteAll(int user_no) throws SQLException {
		
		boolean isDeleted = false;

        try {
            conn = ds.getConnection();
            String sql = " delete from tbl_wish "
            		   + " where fk_user_no = ? ";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, user_no);

            int n = pstmt.executeUpdate();
            
            if (n != 0) {
            	isDeleted = true;
            }
            
            
        } finally {
            close();
        }

        return isDeleted;
	}
	
	
	// 관심상품안에 같은 상품이 있는지 정보 보기
	@Override
	public WishVO selectproduct(Map<String, String> paraMap) throws SQLException {
		
		WishVO wvo = null;
		
		 try {
	            conn = ds.getConnection();
	            String sql = " select fk_user_no, fk_prod_no "
	            		   + " from tbl_wish "
	            		   + " where fk_user_no = ? and fk_prod_no = ? ";
	            
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setString(1, paraMap.get("fk_user_no"));
	            pstmt.setString(2, paraMap.get("fk_prod_no"));
	            
	            rs = pstmt.executeQuery();
	            
	            if(rs.next()) {
	            	
	            	wvo = new WishVO();
	            	
	            	wvo.setFk_user_no(rs.getInt("fk_user_no"));
	            	wvo.setFk_prod_no(rs.getInt("fk_prod_no"));
	            	
	            }
	            
	            
		 } finally {
	            close();
	        }
		
		return wvo;
	}
	
	
	// 관심상품 등록
	@Override
	public int insertWish(Map<String, String> paraMap) throws SQLException {
		
		int n = 0;
		
		 try {
	            conn = ds.getConnection();
	            String sql = " insert into tbl_wish(wish_no, fk_user_no, fk_prod_no) "
	            		   + " values(wish_seq.nextval, ?, ?) ";
	            
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setString(1, paraMap.get("fk_user_no"));
	            pstmt.setString(2, paraMap.get("fk_prod_no"));
	            
	            n = pstmt.executeUpdate();
		
		 } finally {
	            close();
	        }
		
		
		return n;
		
	}
	
	
	// 같은 상품 있다면 관심상품 지우기 //
		@Override
		public int deleteWish(Map<String, String> paraMap) throws SQLException {
			
			int delete = 0;
			
			try {
	            conn = ds.getConnection();
	            String sql = " delete from tbl_wish "
	            		   + " where fk_user_no = ? and fk_prod_no = ? ";
	            
	            pstmt = conn.prepareStatement(sql);
	            
	            pstmt.setString(1, paraMap.get("fk_user_no"));
	            pstmt.setString(2, paraMap.get("fk_prod_no"));
	            
	            delete = pstmt.executeUpdate();
	            
	            
	            
			 } finally {
	            close();
	         }
			
			return delete;
		}
		
		
		@Override
		public int getWishCount(int user_no) throws SQLException {
			
			int cnt = 0;
			
			try {
	            conn = ds.getConnection();
	            String sql = " select count(*) "
	            		   + " from tbl_wish "
	            		   + " where fk_user_no = ? ";
	            
	            pstmt = conn.prepareStatement(sql);
	            
	            pstmt.setInt(1, user_no);
	            
	            rs = pstmt.executeQuery();
	            
	            rs.next();
	            
	            cnt = rs.getInt(1);
	            
			} finally {
	            close();
	         }
			
			
			return cnt;
		}
	
	
	
	

}
