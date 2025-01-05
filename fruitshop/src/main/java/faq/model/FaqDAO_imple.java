package faq.model;

import java.io.UnsupportedEncodingException;
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

import faq.domain.FaqVO;
import notice.domain.NoticeVO;
import util.security.AES256;
import util.security.SecretMyKey;

public class FaqDAO_imple implements FaqDAO {
	
	private DataSource ds;  // DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다.
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	   
	private AES256 aes;
	   
    // 생성자
    public FaqDAO_imple() {
      
       try {
        Context initContext = new InitialContext();
        Context envContext  = (Context)initContext.lookup("java:/comp/env");
        ds = (DataSource)envContext.lookup("jdbc/semioracle");
        
        aes = new AES256(SecretMyKey.KEY);
        // SecretMyKey.KEY 은 우리가 만든 암호화/복호화 키이다.
        
       } catch(NamingException e) {
          e.printStackTrace();
       } catch(UnsupportedEncodingException e) {
    	   e.printStackTrace();
       }
    }
    
    private void close() {
 	   
        
	    try {
	        if(rs     != null) {rs.close();		rs=null;}
	        if(pstmt	 != null) {pstmt.close(); 	pstmt=null;}
	        if(conn	 != null) {conn.close(); 	conn=null;}
	    } catch(SQLException e) {
		    e.printStackTrace();
	    }
	   
    }// end of  private void close()---------------------------

    // faq 리스트를 가져오는 메소드
	@Override
	public List<FaqVO> faqListAll() throws SQLException {
		
		List<FaqVO> faqList = new ArrayList<>();
		
		String sql = " select faq_no, faq_title, faq_contents, faq_regidate, faq_viewcount "
				   + " from tbl_faq "
				   + " where faq_status = 1 "
				   + " order by faq_regidate desc ";
		
		try {
			
			conn = ds.getConnection();
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				FaqVO fvo = new FaqVO();
				
				fvo.setFaq_no(rs.getInt("faq_no"));
				fvo.setFaq_title(rs.getString("faq_title"));
				fvo.setFaq_contents(rs.getString("faq_contents"));
				fvo.setFaq_regidate(rs.getString("faq_regidate"));
				fvo.setFaq_viewcount(rs.getInt("faq_viewcount"));
				
				faqList.add(fvo);
				
			}
			
		}finally {
			close();
		}
		
		return faqList;
	}

	
	// faq 등록하는 메소드
	@Override
	public int FaqWrite(String faq_title, String faq_contents) throws SQLException {
		
		int n = 0;
		
		String sql = " insert into tbl_faq (faq_no, faq_title, faq_contents) "
				+ " values(faq_seq.nextval, ?, ?) ";
		
		try {
			
			conn = ds.getConnection();
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, faq_title);
			pstmt.setString(2, faq_contents);
			
			n = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		
		return n;
	}

	// 권한을 확인후 조회수를 업데이트 하는 메소드
	@Override
	public int setViewCountFaq(String faq_no) throws SQLException {
		
		int n = 0;
		
		String spl = " update tbl_faq set faq_viewcount =  faq_viewcount+1 "
				+ " where faq_no = ? ";
		
		try {
			
			conn = ds.getConnection();
			
			pstmt = conn.prepareStatement(spl);
			
			pstmt.setString(1, faq_no);
			
			n = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		
		return n;
	}

	// 자주하는 질문 상세정보 메소드
	@Override
	public FaqVO oneFaqDetail(String faq_no) throws SQLException {
	FaqVO fvo = null;
		
		String sql = " select faq_no, faq_title, faq_contents, faq_regidate, faq_viewcount "
				   + " from tbl_faq "
				   + " where faq_status = 1 and faq_no = ? ";
		
		try {
			
			conn = ds.getConnection();
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, faq_no);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				fvo = new FaqVO();
				
				fvo.setFaq_no(rs.getInt("faq_no"));
				fvo.setFaq_title(rs.getString("faq_title"));
				fvo.setFaq_contents(rs.getString("faq_contents"));
				fvo.setFaq_regidate(rs.getString("faq_regidate"));
				fvo.setFaq_viewcount(rs.getInt("faq_viewcount"));
			}
			
		} finally {
			close();
		}
		
		
		return fvo;
	}

	@Override
	public int deleteFaq(String faq_no) throws SQLException {
		
		int n = 0;
		
		String sql = " update tbl_faq "
					+" set faq_status = 0 "
					+" where  faq_no = ? ";
		
		try {
			
			conn = ds.getConnection();
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, faq_no);
			
			n = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		
		return n;
	}

	// 자주하는 질문 수정 메소드
	@Override
	public int updateFaq(Map<String, String> map) throws SQLException {
int n = 0;
		
		try {
			
			conn = ds.getConnection();
			
			String sql = " update tbl_faq "
					   + " set faq_title = ?, "
					   + " faq_contents = ? "
					   + " where faq_no = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, map.get("faq_title"));
			pstmt.setString(2, map.get("faq_contents"));
			pstmt.setInt(3, Integer.parseInt(map.get("faq_no")));
			
			n = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		
		return n;
	}

	// 총 페이지 수 알아오기
	@Override
	public int getTotalPage(Map<String, String> paraMap) throws SQLException {
		conn = ds.getConnection();
		
		int total = 0;
		try {
		
			String sql = " select ceil(count(*)/ 10) as cnt "
					+ " from tbl_faq"
					+ " where faq_status = 1 ";
			
			String colname = paraMap.get("searchType");
			String searchWord = paraMap.get("searchWord");
			
			if(!colname.isBlank() && !searchWord.isBlank()) {
				sql += " and "+colname +" like  '%' || ? || '%' ";
			}
		
			pstmt = conn.prepareStatement(sql);
			
			
			if(!colname.isBlank() && !searchWord.isBlank()) {
				
				pstmt.setString(1, searchWord);
				
			}
			
			rs = pstmt.executeQuery();
			
			rs.next();
			
			total = rs.getInt("cnt");
			
		} finally {
			close();
		}
		
		return total;
	}

	@Override
	public List<FaqVO> select_Faq_paging(Map<String, String> paraMap) throws SQLException {
		List<FaqVO> faqList = new ArrayList<>();
		
		try {
			
			conn = ds.getConnection();
			
			
			String sql = "SELECT RNO, faq_no,  faq_title,  faq_contents,  faq_regidate,  faq_status,  faq_viewcount "
					+ "  FROM "
					+ "  ( "
					+ "      SELECT rownum AS RNO, faq_no,  faq_title,  faq_contents,  faq_regidate,  faq_status,  faq_viewcount "
					+ "      FROM "
					+ "      ( "
					+ "        select  faq_no,  faq_title,  faq_contents,  faq_regidate,  faq_status,  faq_viewcount "
					+ "        from tbl_faq "
					+ "		   where faq_status = 1 ";

			
			String colname = paraMap.get("searchType");
			String searchWord = paraMap.get("searchWord");
			int sizePerPage = Integer.parseInt(paraMap.get("sizePerPage"));
			
			//if(colname != null && !colname.trim().isEmpty()) // jdk 1.8 방식
			if(!colname.isBlank() && !searchWord.isBlank()) {	// jdk 11 이상
				// 컬럼명과 테이블명은 위치홀더(?)로 사용하면 꽝!!! 이다.
	            // 위치홀더(?)로 들어오는 것은 컬럼명과 테이블명이 아닌 오로지 데이터값만 들어온다.!!!!
				sql += " and  " +colname+ " like '%'|| ? ||'%' "
						+ " order by faq_regidate desc ";
			}
			
			sql += " ) V "
					+ "  ) T "
					+ " WHERE T.RNO BETWEEN ? AND ? "
					+ " order by faq_regidate desc ";
			
			pstmt = conn.prepareStatement(sql);
			
			// where RNO between (조회하고자하는페이지번호 * 한페이지당보여줄행의개수) - (한페이지당보여줄행의개수 - 1) and (조회하고자하는페이지번호 * 한페이지당보여줄행의개수);
			
			int currentShowPageNo = Integer.parseInt(paraMap.get("currentShowPageNo"));
			
			//if( ( colname != null && !colname.trim().isEmpty() ) && 
		    //         ( searchWord != null && !searchWord.trim().isEmpty() ) ) {
			// 검색이 있는경우
			if(!colname.isBlank() && !searchWord.isBlank()) {
				pstmt.setString(1, searchWord);
				pstmt.setInt(2, (currentShowPageNo*sizePerPage) - (sizePerPage-1));	//  공
				pstmt.setInt(3, (currentShowPageNo*sizePerPage));
			}else {
				pstmt.setInt(1, (currentShowPageNo*sizePerPage) - (sizePerPage-1));	//  공
				pstmt.setInt(2, (currentShowPageNo*sizePerPage));
			}
			
			// 검색이 없는경우
			
			
			
			
			
			rs = pstmt.executeQuery();
			
			
			while(rs.next()) {
				
				FaqVO fvo = new FaqVO();
				
				fvo.setFaq_no(rs.getInt("faq_no"));
				fvo.setFaq_title(rs.getString("faq_title"));
				fvo.setFaq_contents(rs.getString("faq_contents"));
				fvo.setFaq_regidate(rs.getString("faq_regidate"));
				fvo.setFaq_viewcount(rs.getInt("faq_viewcount"));
				
				faqList.add(fvo);
				
			}
			
		} finally {
			close();
		}
		
		return faqList;
	}
	
    
	
}
