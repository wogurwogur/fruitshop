
package review.domain;


public class ReviewListVO {
	
	private int 	review_no;
	private int  	fk_user_no;
	private int  	fk_prod_no;
	private String 	review_title;
	private String 	review_contents;
	private String 	review_status;
	private String 	review_viewcount;
	private String 	review_image;
	private String	review_regidate;
	private String 	userid;
	private String 	prod_name;
	private int 	comment_count;
	
	
	
	

	// 외부키 가져오는데 VO 추가해야하나 ???
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	public String getProd_name() {
		return prod_name;
	}
	public void setProd_name(String prod_name) {
		this.prod_name = prod_name;
	}
	public int getComment_count() {
		return comment_count;
	}
	public void setComment_count(int comment_count) {
		this.comment_count = comment_count;
	}

/////////////////////////////////////////////////
	

	public int getReview_no() {
		return review_no;
	}
	public void setReview_no(int review_no) {
		this.review_no = review_no;
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
	public String getReview_status() {
		return review_status;
	}
	public void setReview_status(String review_status) {
		this.review_status = review_status;
	}
	public String getReview_viewcount() {
		return review_viewcount;
	}
	public void setReview_viewcount(String review_viewcount) {
		this.review_viewcount = review_viewcount;
	}
	public String getReview_image() {
		return review_image;
	}
	public void setReview_image(String review_image) {
		this.review_image = review_image;
	}
	public String getReview_regidate() {
		return review_regidate;
	}
	public void setReview_regidate(String review_regidate) {
		this.review_regidate = review_regidate;
	}
	
} // end of public class ReviewListVO {
