package notice.model;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
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

import member.domain.MemberVO;
import notice.domain.NoticeVO;
import util.security.AES256;
import util.security.SecretMyKey;

public class NoticeDAO_imple implements NoticeDAO {
	
	private DataSource ds;  // DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool)이다.
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	   
	private AES256 aes;
	   
    // 생성자
    public NoticeDAO_imple() {
      
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

	// 공지사항 글을 모두 가져오는 메소드
	@Override
	public List<NoticeVO> noticeSelectAll() throws SQLException {
		
		List<NoticeVO> noticeList = new ArrayList<>();
		
		
		String sql = " select notice_no, notice_title, notice_contents, notice_regidate, notice_viewcount "
				   + " from tbl_notice "
				   + " where notice_status = 1 "
				   + " order by notice_regidate desc ";
		
		try {
			
			conn = ds.getConnection();
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				NoticeVO nvo = new NoticeVO();
				
				nvo.setNotice_no(rs.getInt("notice_no"));
				nvo.setNotice_contents(rs.getString("notice_contents"));
				nvo.setNotice_title(rs.getString("notice_title"));
				nvo.setNotice_regidate(rs.getString("notice_regidate"));
				nvo.setNotice_viewcount(rs.getInt("notice_viewcount"));
				
				noticeList.add(nvo);
				
			}
			
		} finally {
			close();
		}
		
		return noticeList;
	}

	// 공지사항 글 작성하는 메소드
	@Override
	public int noticeInsert(String notice_title, String notice_contents) throws SQLException {
		
		int n = 0;
		
		String sql = " insert into tbl_notice(notice_no, notice_title, notice_contents) "
				   + " values(notice_seq.nextval, ?, ?) ";
		
		try {
			
			conn = ds.getConnection();
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, notice_title);
			pstmt.setString(2, notice_contents);
			
			n = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		
		return n;
	}

	// notice_no을 받아서 공지사항을 삭제하는 메소드
	@Override
	public int deleteNotice(String notice_no) throws SQLException {
		
		int n = 0;
		
		String sql = " update tbl_notice "
					+" set  notice_status = 0 "
					+" where  notice_no = ? ";
		
		try {
			
			conn = ds.getConnection();
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, notice_no);
			
			n = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		
		return n;
	}

	
	// 공지사항 정보 1개를 가져오는 매소드
	@Override
	public NoticeVO oneNoticeDetail(String notice_no) throws SQLException {
		
		NoticeVO nvo = null;
		
		String sql = " select notice_no, notice_title, notice_contents, notice_regidate, notice_viewcount "
				   + " from tbl_notice "
				   + " where notice_status = 1 and notice_no = ? ";
		
		try {
			
			conn = ds.getConnection();
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, notice_no);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				nvo = new NoticeVO();
				
				nvo.setNotice_no(rs.getInt("notice_no"));
				nvo.setNotice_title(rs.getString("notice_title"));
				nvo.setNotice_contents(rs.getString("notice_contents"));
				nvo.setNotice_regidate(rs.getString("notice_regidate"));
				nvo.setNotice_viewcount(rs.getInt("notice_viewcount"));
			}
			
		} finally {
			close();
		}
		
		
		return nvo;
	}

	// 권한을 확인후 조회수를 업데이트 하는 메소드
	@Override
	public int setViewCount(String notice_no) throws SQLException {
		
		int n = 0;
		
		String spl = " update tbl_notice set notice_viewcount =  notice_viewcount+1 "
				+ " where notice_no = ? ";
		
		try {
			
			conn = ds.getConnection();
			
			pstmt = conn.prepareStatement(spl);
			
			pstmt.setString(1, notice_no);
			
			n = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		
		return n;
	}

	
	// 공지사항을 수정하는 메소드
	@Override
	public int updateNotice(Map<String, String> map) throws SQLException{
		int n = 0;
		
		try {
			
			conn = ds.getConnection();
			
			String sql = " update tbl_notice "
					   + " set notice_title = ?, "
					   + " notice_contents = ? "
					   + " where notice_no = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, map.get("notice_title"));
			pstmt.setString(2, map.get("notice_contents"));
			pstmt.setInt(3, Integer.parseInt(map.get("notice_no")));
			
			n = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		
		return n;
	}
	
	// 총 페이지수 알아오기
	@Override
	public int getTotalPage(Map<String, String> paraMap) throws SQLException {
		
		conn = ds.getConnection();
		
		int total = 0;
		try {
		
			String sql = " select ceil(count(*)/ 10) "
					+ " from tbl_notice "
					+ " where notice_status = 1 ";
			
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
			
			total = rs.getInt(1);
			
		} finally {
			close();
		}
		
		return total;
	}
	
	// 페이징처리를 한 모든회원 목록 보여주기
	@Override
	public List<NoticeVO> select_Notice_paging(Map<String, String> paraMap) throws SQLException {
		List<NoticeVO> noticeList = new ArrayList<>();
		
		try {
			
			conn = ds.getConnection();
			
			
			String sql = "SELECT RNO, notice_no,  notice_title,  notice_contents,  notice_regidate,  notice_status,  notice_viewcount "
					+ "  FROM "
					+ "  ( "
					+ "      SELECT rownum AS RNO, notice_no,  notice_title,  notice_contents,  notice_regidate,  notice_status,  notice_viewcount "
					+ "      FROM "
					+ "      ( "
					+ "        select  notice_no,  notice_title,  notice_contents,  notice_regidate,  notice_status,  notice_viewcount "
					+ "        from tbl_notice "
					+ "		   where notice_status = 1 ";

			
			String colname = paraMap.get("searchType");
			String searchWord = paraMap.get("searchWord");
			int sizePerPage = Integer.parseInt(paraMap.get("sizePerPage"));
			
			//if(colname != null && !colname.trim().isEmpty()) // jdk 1.8 방식
			if(!colname.isBlank() && !searchWord.isBlank()) {	// jdk 11 이상
				// 컬럼명과 테이블명은 위치홀더(?)로 사용하면 꽝!!! 이다.
	            // 위치홀더(?)로 들어오는 것은 컬럼명과 테이블명이 아닌 오로지 데이터값만 들어온다.!!!!
				sql += " and  " +colname+ " like '%'|| ? ||'%' "
						+ " order by notice_regidate desc ";
			}
			
			sql += " ) V "
					+ "  ) T "
					+ " WHERE T.RNO BETWEEN ? AND ? "
					+ " order by notice_regidate desc ";
			
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
				
				NoticeVO nvo = new NoticeVO();
				
				nvo.setNotice_no(rs.getInt("notice_no"));
				nvo.setNotice_title(rs.getString("notice_title"));
				nvo.setNotice_contents(rs.getString("notice_contents"));
				nvo.setNotice_regidate(rs.getString("notice_regidate"));
				nvo.setNotice_viewcount(rs.getInt("notice_viewcount"));
				
				noticeList.add(nvo);
				
			}
			
		} finally {
			close();
		}
		
		return noticeList;
	}
	
}
