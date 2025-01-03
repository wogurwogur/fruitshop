package order.domain;

public class OrderDetailVO {

	private int ordetail_no;	// 주문상세번호 
	private int ordetail_count;	// 개별상품수량 
	private int ordetail_price;	// 개별상품가격
	private int ship_status;	// 배송상태 
	
	
	
	public int getOrdetail_no() {
		return ordetail_no;
	}
	public void setOrdetail_no(int ordetail_no) {
		this.ordetail_no = ordetail_no;
	}
	public int getOrdetail_count() {
		return ordetail_count;
	}
	public void setOrdetail_count(int ordetail_count) {
		this.ordetail_count = ordetail_count;
	}
	public int getOrdetail_price() {
		return ordetail_price;
	}
	public void setOrdetail_price(int ordetail_price) {
		this.ordetail_price = ordetail_price;
	}
	public int getShip_status() {
		return ship_status;
	}
	public void setShip_status(int ship_status) {
		this.ship_status = ship_status;
	}
	
}
