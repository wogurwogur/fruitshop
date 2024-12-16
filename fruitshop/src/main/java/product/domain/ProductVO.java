package product.domain;

public class ProductVO {
	
	// 상품 테이블

	private int prod_no;			// 상품번호
	private String prod_name;		// 상품명
	private int prod_cost;			// 상품원가
	private int prod_price;			// 상품가격
	private String prod_thumnail;	// 상품썸네일(이미지)
	private String prod_descript;	// 상품설명상세내용(이미지?)
	private int prod_inventory;		// 상품재고량
	private int fk_season_no; 		// 계절 테이블 계절 번호
	private String prod_regidate;	// 상품등록일
	
	
		
	public int getProd_no() {
		return prod_no;
	}
	
	public void setProd_no(int prod_no) {
		this.prod_no = prod_no;
	}
	
	public String getProd_name() {
		return prod_name;
	}
	
	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
	}
	
	public int getProd_cost() {
		return prod_cost;
	}
	
	public void setProd_cost(int prod_cost) {
		this.prod_cost = prod_cost;
	}
	
	public int getProd_price() {
		return prod_price;
	}
	
	public void setProd_price(int prod_price) {
		this.prod_price = prod_price;
	}
	
	public String getProd_thumnail() {
		return prod_thumnail;
	}
	
	public void setProd_thumnail(String prod_thumnail) {
		this.prod_thumnail = prod_thumnail;
	}
	
	public String getProd_descript() {
		return prod_descript;
	}
	
	public void setProd_descript(String prod_descript) {
		this.prod_descript = prod_descript;
	}
	
	public int getProd_inventory() {
		return prod_inventory;
	}
	
	public void setProd_inventory(int prod_inventory) {
		this.prod_inventory = prod_inventory;
	}

	public int getFk_season_no() {
		return fk_season_no;
	}
	
	public void setFk_season_no(int fk_season_no) {
		this.fk_season_no = fk_season_no;
	}
	
	public String getProd_regidate() {
		return prod_regidate;
	}

	public void setProd_regidate(String prod_regidate) {
		this.prod_regidate = prod_regidate;
	}
	

}
