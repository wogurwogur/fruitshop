package mypage.wish.domain;

import product.domain.ProductVO;

public class WishVO {
	
	// 찜목록 테이블
	
	private int wish_no;      // 찜번호
	private int fk_user_no;	// 회원번호 
	private int fk_prod_no;  // 상품번호
	
	 // 상품 정보 추가
    private ProductVO product;   
	
	
	public ProductVO getProduct() {
		return product;
	}
	public void setProduct(ProductVO product) {
		this.product = product;
	}
	public int getWish_no() {
		return wish_no;
	}
	public void setWish_no(int wish_no) {
		this.wish_no = wish_no;
	}
	public int getFk_user_no() {
		return fk_user_no;
	}
	public void setFk_user_no(int fk_user_no) {
		this.fk_user_no = fk_user_no;
	}
	public int getFk_prod_no() {
		return fk_prod_no;
	}
	public void setFk_prod_no(int fk_prod_no) {
		this.fk_prod_no = fk_prod_no;
	}

	

}
