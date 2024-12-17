package cart.domain;

import product.domain.ProductVO;

public class CartVO {
    // 장바구니 테이블
    private int cart_no;         // 장바구니 번호
    private int fk_user_no;      // 회원 번호
    private int fk_prod_no;      // 상품 번호
    private int cart_prodcount;  // 상품 수량

    // 상품 정보 추가
    private ProductVO product;   

    public int getCart_no() {
        return cart_no;
    }
    public void setCart_no(int cart_no) {
        this.cart_no = cart_no;
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
    public int getCart_prodcount() {
        return cart_prodcount;
    }
    public void setCart_prodcount(int cart_prodcount) {
        this.cart_prodcount = cart_prodcount;
    }

    
    public ProductVO getProduct() {
        return product;
    }
    public void setProduct(ProductVO product) {
        this.product = product;
    }
}
