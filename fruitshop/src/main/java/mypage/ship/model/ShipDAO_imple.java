package mypage.ship.model;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import mypage.ship.domain.ShipVO;
import util.security.AES256;
import util.security.SecretMyKey;



public class ShipDAO_imple implements ShipDAO {

	private DataSource ds; 
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private AES256 aes;
	
	public ShipDAO_imple() {
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			ds = (DataSource) envContext.lookup("jdbc/semioracle");
			aes = new AES256(SecretMyKey.KEY);

		} catch (NamingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	} //

	// 자원 반납 메소드
	private void close() {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (pstmt != null) {
				pstmt.close();
				pstmt = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} //

	// 로그인 유저의 모든 배송지를 조회하는 메소드
	@Override
	public List<ShipVO> shipSelectAll(int user_no) throws SQLException {
		
		List<ShipVO> shipList = null;
		
		try {
			conn = ds.getConnection();

			String sql = " select SHIP_NO, SHIP_NAME, SHIP_POSTCODE, SHIP_ADDRESS, SHIP_DETAILADDRESS, SHIP_EXTRAADRESS, SHIP_DEFAULT, SHIP_RECEIVER, SHIP_RECEIVERTEL "
					   + " from tbl_ship "
					+    " where FK_USER_NO = ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, user_no);
		
			rs = pstmt.executeQuery();

			while(rs.next()) {
				
				ShipVO svo = new ShipVO();
				
				svo.setShip_no(rs.getInt("SHIP_NO"));
				svo.setShip_name(rs.getString("SHIP_NAME"));
				svo.setShip_postcode(rs.getString("SHIP_POSTCODE"));
				
				
				
				svo.setShip_receivertel(aes.decrypt(rs.getString("SHIP_RECEIVERTEL")));
				
			}

		} catch (UnsupportedEncodingException | SQLException | GeneralSecurityException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		
		
		return shipList;
	}
	
	
	
	
	
	
	

}
