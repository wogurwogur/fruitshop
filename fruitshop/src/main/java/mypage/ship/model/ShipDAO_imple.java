package mypage.ship.model;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
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

import mypage.ship.domain.ShipVO;
import util.security.AES256;
import util.security.SecretMyKey;
import util.security.Sha256;



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
		
		List<ShipVO> shipList = new ArrayList<>();
		
		try {
			conn = ds.getConnection();

			String sql = " select SHIP_NO, SHIP_NAME, SHIP_POSTCODE, SHIP_ADDRESS, SHIP_DETAILADDRESS, SHIP_EXTRAADRESS, SHIP_DEFAULT, SHIP_RECEIVER, SHIP_RECEIVERTEL "
					   + " from tbl_ship "
					   + " where FK_USER_NO = ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, user_no);
		
			rs = pstmt.executeQuery();

			while(rs.next()) {
				
				ShipVO svo = new ShipVO();
				
				svo.setShip_no(rs.getInt("SHIP_NO"));
				svo.setShip_name(rs.getString("SHIP_NAME"));
				svo.setShip_postcode(rs.getString("SHIP_POSTCODE"));
				svo.setShip_address(rs.getString("SHIP_ADDRESS"));
				svo.setShip_detailAddress(rs.getString("SHIP_DETAILADDRESS"));
				svo.setShip_extraAddress(rs.getString("SHIP_EXTRAADRESS"));
				svo.setShip_receiver(rs.getString("SHIP_RECEIVER"));
				svo.setShip_receivertel(aes.decrypt(rs.getString("SHIP_RECEIVERTEL")));
				svo.setShip_default(rs.getInt("SHIP_DEFAULT"));
				
				shipList.add(svo);
			}
			

		} catch (UnsupportedEncodingException | SQLException | GeneralSecurityException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		
		
		return shipList;
	}

	// 배송지를 추가하는 메소드
	@Override
	public int shipAdd(ShipVO svo) throws SQLException {
		
		int result = 0;
		
		try {
			
			conn = ds.getConnection();
			
			String sql = " insert into tbl_ship(ship_no, fk_user_no, ship_name, ship_receiver, ship_receivertel, ship_postcode, ship_address, ship_detailaddress, ship_extraadress) "
					   + " values(ship_seq.nextval, ?, ?, ?, ?, ?, ?, ?, ? ) ";
			
			pstmt = conn.prepareStatement(sql); 
			
			pstmt.setInt(1, svo.getFk_user_no());
			pstmt.setString(2, svo.getShip_name());
			pstmt.setString(3, svo.getShip_receiver());
			pstmt.setString(4, aes.encrypt(svo.getShip_receivertel())); 
			pstmt.setString(5, svo.getShip_postcode());
			pstmt.setString(6, svo.getShip_address());
			pstmt.setString(7, svo.getShip_detailAddress());
			pstmt.setString(8, svo.getShip_extraAddress());

			result= pstmt.executeUpdate();
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return result;
	}
	
	// 배송지를 삭제해주는 메소드
	@Override
	public int deleteShipInfo(String ship_no) throws SQLException {
		
		int result = 0;
		
		try {
			
			conn = ds.getConnection();
			
			String sql = " delete from tbl_ship "
					   + " where ship_no = ? ";
			
			pstmt = conn.prepareStatement(sql); 
			pstmt.setInt(1, Integer.parseInt(ship_no));

			result= pstmt.executeUpdate();
		} finally {
			close();
		}
		return result;
	}
	
	// 특정 배송지정보를 조회하는 메소드
	@Override
	public ShipVO shipSelectOne(String ship_no) throws SQLException {
		
		ShipVO svo = null;
		
		try {
			conn = ds.getConnection();

			String sql = " select ship_no, fk_user_no, SHIP_NAME, SHIP_POSTCODE, SHIP_ADDRESS, SHIP_DETAILADDRESS, SHIP_EXTRAADRESS, SHIP_DEFAULT, SHIP_RECEIVER, SHIP_RECEIVERTEL "
					   + " from tbl_ship "
					   + " where ship_no = ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(ship_no));
		
			rs = pstmt.executeQuery();

			if(rs.next()) {
				
				svo = new ShipVO();
				
				svo.setShip_no(rs.getInt("ship_no"));
				svo.setFk_user_no(rs.getInt("fk_user_no"));
				svo.setShip_name(rs.getString("SHIP_NAME"));
				svo.setShip_postcode(rs.getString("SHIP_POSTCODE"));
				svo.setShip_address(rs.getString("SHIP_ADDRESS"));
				svo.setShip_detailAddress(rs.getString("SHIP_DETAILADDRESS"));
				svo.setShip_extraAddress(rs.getString("SHIP_EXTRAADRESS"));
				svo.setShip_receiver(rs.getString("SHIP_RECEIVER"));
				svo.setShip_receivertel(aes.decrypt(rs.getString("SHIP_RECEIVERTEL")));
				svo.setShip_default(rs.getInt("SHIP_DEFAULT"));
			} 
		} catch (UnsupportedEncodingException | GeneralSecurityException | SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return svo;
	}

	// 모든 배송지를 기본배송지가 아니게 설정하는 메소드
	@Override
	public int noDefault(int user_no) throws SQLException {
		
		int n = 0;
		
		try {
			
			conn = ds.getConnection();
			
			String sql = " update tbl_ship set ship_default = 0 "
					   + " where fk_user_no = ? ";
			
			pstmt = conn.prepareStatement(sql); 
			pstmt.setInt(1, user_no);
			
			n = pstmt.executeUpdate();

		} finally {
			close();
		}

		return n;
	}

	// 특정 배송지를 기본배송지로 변경하는 메소드
	@Override
	public int oneDefault(int ship_no) throws SQLException {
		int n = 0;
		
		try {
			
			conn = ds.getConnection();
			
			String sql = " update tbl_ship set ship_default = 1 "
					   + " where ship_no = ? ";
			
			pstmt = conn.prepareStatement(sql); 
			pstmt.setInt(1, ship_no);
			
			n = pstmt.executeUpdate();

		} finally {
			close();
		}

		return n;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
