package coupon.domain;

public class CouponVO {
	
	private int coupon_no;
	private int fk_user_no;
	private String coupon_name;
	private String coupon_descript;
	private String coupon_expire;
	private int coupon_discount;
	 
	 
	 
	
	public int getCoupon_no() {
		return coupon_no;
	}
	public void setCoupon_no(int coupon_no) {
		this.coupon_no = coupon_no;
	}
	public int getFk_user_no() {
		return fk_user_no;
	}
	public void setFk_user_no(int fk_user_no) {
		this.fk_user_no = fk_user_no;
	}
	public String getCoupon_name() {
		return coupon_name;
	}
	public void setCoupon_name(String coupon_name) {
		this.coupon_name = coupon_name;
	}
	public String getCoupon_descript() {
		return coupon_descript;
	}
	public void setCoupon_descript(String coupon_descript) {
		this.coupon_descript = coupon_descript;
	}
	public String getCoupon_expire() {
		return coupon_expire;
	}
	public void setCoupon_expire(String coupon_expire) {
		this.coupon_expire = coupon_expire;
	}
	public int getCoupon_discount() {
		return coupon_discount;
	}
	public void setCoupon_discount(int coupon_discount) {
		this.coupon_discount = coupon_discount;
	}
	
}
