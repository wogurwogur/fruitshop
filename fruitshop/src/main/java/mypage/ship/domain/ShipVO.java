package mypage.ship.domain;

public class ShipVO {
	
	
	private int ship_no;					// 배송지 번호 //시퀀스명 : ship_seq
	private String fk_user_no;				// 회원 번호
	private String ship_name;				// 배송지명 (최대 10글자)
	private String ship_postcode;			// 우편번호
	private String ship_address; 			// 주소
	private String ship_detailaddress;		// 상세주소
	private String ship_extraadress;		// 참고항목
	private int ship_default;				// 기본배송지여부 (0: 그외 / 1: 기본배송지)
	
	
	
	////////////////////////////////////////////////////////////////////////
	
	// Getter & Setter //
	
	public int getShip_no() {
		return ship_no;
	}
	public void setShip_no(int ship_no) {
		this.ship_no = ship_no;
	}
	public String getFk_user_no() {
		return fk_user_no;
	}
	public void setFk_user_no(String fk_user_no) {
		this.fk_user_no = fk_user_no;
	}
	public String getShip_name() {
		return ship_name;
	}
	public void setShip_name(String ship_name) {
		this.ship_name = ship_name;
	}
	public String getShip_postcode() {
		return ship_postcode;
	}
	public void setShip_postcode(String ship_postcode) {
		this.ship_postcode = ship_postcode;
	}
	public String getShip_address() {
		return ship_address;
	}
	public void setShip_address(String ship_address) {
		this.ship_address = ship_address;
	}
	public String getShip_detailaddress() {
		return ship_detailaddress;
	}
	public void setShip_detailaddress(String ship_detailaddress) {
		this.ship_detailaddress = ship_detailaddress;
	}
	public String getShip_extraadress() {
		return ship_extraadress;
	}
	public void setShip_extraadress(String ship_extraadress) {
		this.ship_extraadress = ship_extraadress;
	}
	public int getShip_default() {
		return ship_default;
	}
	public void setShip_default(int ship_default) {
		this.ship_default = ship_default;
	}

	

}
