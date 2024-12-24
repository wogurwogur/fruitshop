package order.domain;

import member.domain.MemberVO;
import product.domain.ProductVO;

public class OrderVO {
	private int 	order_no;  			// 주문번호
	private int 	fk_user_no;     	// 회원번호
	private String 	order_date; 		// 주문일자
	private String 	order_request; 		// 요청사항
	private int 	order_tprice;       // 총주문금액
	private int 	order_status;       /* 주문 상태 (1: 주문 / 2: 교환/반품 / 3: 환불 / 4: 취소 / 5: 구매확정) */
	private String 	order_changedate; 	/* 주문 상태 변경일자 */
	
	
	// SELECT 용 부모 테이블
	private MemberVO membverVO;
	private ProductVO productVO;
	
	
	public MemberVO getMembverVO() {
		return membverVO;
	}
	public void setMembverVO(MemberVO membverVO) {
		this.membverVO = membverVO;
	}
	public ProductVO getProductVO() {
		return productVO;
	}
	public void setProductVO(ProductVO productVO) {
		this.productVO = productVO;
	}
	public int getOrder_no() {
		return order_no;
	}
	public void setOrder_no(int order_no) {
		this.order_no = order_no;
	}
	public int getFk_user_no() {
		return fk_user_no;
	}
	public void setFk_user_no(int fk_user_no) {
		this.fk_user_no = fk_user_no;
	}
	public String getOrder_date() {
		return order_date;
	}
	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}
	public String getOrder_request() {
		return order_request;
	}
	public void setOrder_request(String order_request) {
		this.order_request = order_request;
	}
	public int getOrder_tprice() {
		return order_tprice;
	}
	public void setOrder_tprice(int order_tprice) {
		this.order_tprice = order_tprice;
	}
	public int getOrder_status() {
		return order_status;
	}
	public void setOrder_status(int order_status) {
		this.order_status = order_status;
	}
	public String getOrder_changedate() {
		return order_changedate;
	}
	public void setOrder_changedate(String order_changedate) {
		this.order_changedate = order_changedate;
	}
	
	
	
}
