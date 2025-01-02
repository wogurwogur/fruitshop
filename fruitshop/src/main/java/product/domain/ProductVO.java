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
	private int prod_status;		// 판매여부
	
	

	// 타 테이블
	
	private int user_no;			// 멤버 번호
	private String userid; 			// 멤버 회원아이디
	
	private int qna_no;				// 문의 번호
	private String qna_title;		// 문의 제목
	private String qna_contents;	// 문의 내용
	private String qna_regidate;	// 문의 등록일자
	
	private int review_no;			// 후기 번호
	private String review_title;	// 후기 제목
	private String review_contents; // 후기 내용
	private String review_regidate; // 후기 등록일자
	
	private int season_no; 			// 계절 번호
	private String season_name;		// 계절명
	
	
	
		
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
	
	public int getProd_status() {
		return prod_status;
	}

	public void setProd_status(int prod_status) {
		this.prod_status = prod_status;
	}




	// 타 테이블
	public int getUser_no() {
		return user_no;
	}

	public void setUser_no(int user_no) {
		this.user_no = user_no;
	}


	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	
	public int getQna_no() {
		return qna_no;
	}

	public void setQna_no(int qna_no) {
		this.qna_no = qna_no;
	}

	public String getQna_title() {
		return qna_title;
	}

	public void setQna_title(String qna_title) {
		this.qna_title = qna_title;
	}
	
	public String getQna_contents() {
		return qna_contents;
	}

	public void setQna_contents(String qna_contents) {
		this.qna_contents = qna_contents;
	}

	public String getQna_regidate() {
		return qna_regidate;
	}

	public void setQna_regidate(String qna_regidate) {
		this.qna_regidate = qna_regidate;
	}
	

	public int getReview_no() {
		return review_no;
	}

	public void setReview_no(int review_no) {
		this.review_no = review_no;
	}

	public String getReview_title() {
		return review_title;
	}

	public void setReview_title(String review_title) {
		this.review_title = review_title;
	}

	public String getReview_contents() {
		return review_contents;
	}

	public void setReview_contents(String review_contents) {
		this.review_contents = review_contents;
	}

	public String getReview_regidate() {
		return review_regidate;
	}

	public void setReview_regidate(String review_regidate) {
		this.review_regidate = review_regidate;
	}

	public int getSeason_no() {
		return season_no;
	}

	public void setSeason_no(int season_no) {
		this.season_no = season_no;
	}

	public String getSeason_name() {
		return season_name;
	}

	public void setSeason_name(String season_name) {
		this.season_name = season_name;
	}






	
	
}
