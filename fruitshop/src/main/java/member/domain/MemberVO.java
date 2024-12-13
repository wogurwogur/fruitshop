package member.domain;

public class MemberVO {
	
	// 필드 //
	
	private int user_no;				// 회원 번호
	private String userid; 				// 회원 아이디
	private String passwd; 				// 회원 비밀번호 (SHA-256 암호화 대상)
	private String name; 				// 회원명
	private String birthday; 			// 생년월일
	private String email; 				// 이메일 (AES-256 암호화/복호화 대상)
	private String tel; 				// 연락처 (AES-256 암호화/복호화 대상)
	private String postcode; 			// 우편번호
	private String address; 			// 주소
	private String detailaddress; 		// 상세주소
	private String extraaddress; 		// 참고항목
	private String gender; 				// 성별 (남/여)
	private int point; 					// 적립금
	private String registerday; 		// 가입일자
	private String lastpwdchangedate; 	// 마지막으로 암호를 변경한 날짜
	private int idle;					// 회원휴면상태(0:휴면 / 1:활동중)
	private int status; 				// 회원가입상태(0:탈퇴 / 1:가입중)
	private int role;					// 회원권한(1:일반유저 / 2: 관리자)
	
	private boolean requirePwdChange = false;
	// 마지막으로 암호를 변경한 날짜가 현재시각으로 부터 3개월이 지났으면 true
	// 마지막으로 암호를 변경한 날짜가 현재시각으로 부터 3개월이 지나지 않았으면 false
	
	/////////////////////////////////////////////////////////////////////
	
	// Getter & Setter //
	
	public int getUser_no() {
		return user_no;
	}

	public void setUser_no(int userno) {
		this.user_no = userno;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDetailaddress() {
		return detailaddress;
	}

	public void setDetailaddress(String detailaddress) {
		this.detailaddress = detailaddress;
	}

	public String getExtraaddress() {
		return extraaddress;
	}

	public void setExtraaddress(String extraaddress) {
		this.extraaddress = extraaddress;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public String getRegisterday() {
		return registerday;
	}

	public void setRegisterday(String registerday) {
		this.registerday = registerday;
	}

	public String getLastpwdchangedate() {
		return lastpwdchangedate;
	}

	public void setLastpwdchangedate(String lastpwdchangedate) {
		this.lastpwdchangedate = lastpwdchangedate;
	}

	public int getIdle() {
		return idle;
	}

	public void setIdle(int idle) {
		this.idle = idle;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}
	
	//////////////////////////////////////////////////////////////
	
	public void setRequirePwdChange(boolean requirePwdChange) {
		this.requirePwdChange = requirePwdChange;
	}
	public boolean isRequirePwdChange() {
		return requirePwdChange;
	}

	

}
