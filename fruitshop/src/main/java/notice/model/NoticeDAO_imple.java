package notice.model;

import java.io.UnsupportedEncodingException;
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
				   + " order by notice_regidate ";
		
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
	
}
