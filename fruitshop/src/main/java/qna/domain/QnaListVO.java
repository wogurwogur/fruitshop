package qna.domain;

public class QnaListVO {

	private int 	qna_no;
	private int  	fk_user_no;
	private int  	fk_prod_no;
	private String 	qna_title;
	private String 	qna_contents;
	private String 	qna_status;
	private String 	qna_viewcount;
	private String	qna_regidate;
	private String  qna_answer;
	

	private String 	userid;
	private String 	prod_name;
	private int 	prod_no;
	private int 	prod_price;
	private String  prod_thumnail;

	
	
	
	public int getProd_no() {
		return prod_no;
	}
	public void setProd_no(int prod_no) {
		this.prod_no = prod_no;
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
	public int getQna_no() {
		return qna_no;
	}
	public void setQna_no(int qna_no) {
		this.qna_no = qna_no;
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
	public String getQna_status() {
		return qna_status;
	}
	public void setQna_status(String qna_status) {
		this.qna_status = qna_status;
	}
	public String getQna_viewcount() {
		return qna_viewcount;
	}
	public void setQna_viewcount(String qna_viewcount) {
		this.qna_viewcount = qna_viewcount;
	}
	public String getQna_regidate() {
		return qna_regidate;
	}
	public void setQna_regidate(String qna_regidate) {
		this.qna_regidate = qna_regidate;
	}
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
		
	public String getQna_answer() {
		return qna_answer;
	}
	public void setQna_answer(String qna_answer) {
		this.qna_answer = qna_answer;
	}
	
	
	
	
	
}
