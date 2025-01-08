<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:include page="../common/header.jsp"></jsp:include>

<jsp:include page="mypage_list.jsp"></jsp:include>

<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/mypage/updateInfo.css" />

<script type="text/javascript" src="<%= request.getContextPath() %>/js/mypage/updateInfo.js"></script>

<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<style type="text/css">

* {
	font-family: 'Noto Sans KR', sans-serif;
}
</style>

<script type="text/javascript">
	$(()=>{
		
		
		$("input#userid").val("${sessionScope.loginuser.userid}");
		$("input#name").val("${sessionScope.loginuser.name}");
		$("input#email").val("${sessionScope.loginuser.email}");
		$("input#tel1").val("${sessionScope.loginuser.tel}".substring(0,3));
		$("input#tel2").val("${sessionScope.loginuser.tel}".substring(3,7));
		$("input#tel3").val("${sessionScope.loginuser.tel}".substring(7,11));
		$("input#postcode").val("${sessionScope.loginuser.postcode}");
		$("input#address").val("${sessionScope.loginuser.address}");
		$("input#extraAddress").val("${sessionScope.loginuser.extraaddress}");
		$("input#detailAddress").val("${sessionScope.loginuser.detailaddress}");
		
		$("input#userid").attr("readonly", true);
	});
</script>

<div class="container" style="margin-bottom: 100px;">

	<form name="updateInfoFrm">

		<div style="width: 450px; margin: 10px auto;">

			<div class="text-center"
				style="margin-top: 50px; margin-bottom: 50px;">
				<h4 style="font-weight: bold;">회원정보 수정</h4>
				(<span class="star text-danger">*</span>표시는 필수입력사항)
			</div>


			<div>

				<table id="tblMemberRegister" class="w-100">
				
					<tbody>
						<tr>
							<td>
								<span class="star text-danger"> </span>
							</td>
							<td style="height: 50px; vertical-align:top;">
								<input type="text" name="userid" id="userid" maxlength="40" class="requiredInfo info" placeholder="아이디" /><br>
							</td>
						</tr>

						<tr>
							<td>
								<span class="star text-danger"> </span>
							</td>
							<td style="height: 50px; vertical-align:top;">
								<input type="password" name="passwd" id="passwd" maxlength="20" class="requiredInfo info" placeholder="비밀번호" /><br>
							</td>
						</tr>

						<tr>
							<td>
								<span class="star text-danger"> </span>
							</td>
							<td style="height: 80px; vertical-align:top;">
								<input type="password" id="passwdcheck" maxlength="20" class="requiredInfo info" placeholder="비밀번호 확인" /><br>
								<span id="pwdError" class="error"></span><span class="rule">(영문/숫자/특수문자 조합, 8자~20자)</span>
							</td>
						</tr>

						<tr>
							<td>
								<span class="star text-danger">*</span>
							</td>
							<td style="height: 80px; vertical-align:top;">
								<input type="text" name="name" id="name" maxlength="30" class="requiredInfo info" placeholder="성명" /><br>
								<span class="error"></span>
								<span class="rule">(한글, 2자~6자)</span>
							</td>
						</tr>

						<tr>
							<td>
								<span class="star text-danger">*</span>
							</td>
							<td style="height: 80px; vertical-align:top;">
								<input type="text" name="email" id="email" maxlength="60" class="requiredInfo info" placeholder="이메일" /><br>
								<span id="emailError" class="error"></span>
							</td>
						</tr>

						<tr>
							<td>
								<span class="star text-danger">*</span>
							</td>
							<td style="height: 80px; vertical-align:top;">
								<select name="tel1" id="tel1" style="width: 100px;" required>
									<option value="010">010</option>
									<option value="011">011</option>
									<option value="016">016</option>
									<option value="017">017</option>
									<option value="018">018</option>
								</select>&nbsp;-&nbsp;
								<input type="text" name="tel2" id="tel2" class="info" style="width: 100px;" size="6" maxlength="4" />&nbsp;-&nbsp;
								<input type="text" name="tel3"id="tel3" class="info" style="width: 100px;" size="6" maxlength="4" /><br>
								<span class="error"></span>
							</td>
						</tr>

						<tr>
							<td>
								<span class="star text-danger">*</span>
							</td>
							<td>
								<input type="text" name="postcode" id="postcode" style="width: 100px;" size="6" maxlength="5" placeholder="우편번호"/>&nbsp;&nbsp;
								<%-- 우편번호 찾기 --%>
								<img src="<%= request.getContextPath() %>/images/memberRegister/b_zipcode.gif" id="zipcodeSearch" width="90" height="30" />
								<span class="error"></span>
							</td>
							
						</tr>

						<tr>
							<td>
								<span class="star text-danger">*</span>
							</td>
							<td style="height:160px; vertical-align: top;">
								<input type="text" name="address" id="address" size="40" maxlength="200" placeholder="주소" style="margin:3px 0;" />
								<input type="text" name="extraaddress" id="extraAddress" size="40" maxlength="200" placeholder="참고항목" style="margin:3px 0;" />
								<input type="text" name="detailaddress" id="detailAddress" size="40" maxlength="200" placeholder="상세주소" style="margin:3px 0 auto 0;" />
								<span class="error"></span>
							</td>
						</tr>

			

						<tr>
							<td>
								<span class="star text-danger"></span>
							</td>
							<td class="text-center" style="height: 100px;">
								<input type="button" class="btn btn-outline-success btn-lg mr-3" value="변경하기" onclick="goUpdate()" />
								<input type="button" class="btn btn-outline-danger btn-lg mr-3" value="취소하기" onclick="goBack()" />
								<input type="button" class="btn btn-outline-warning btn-lg" value="탈퇴하기" onclick="goWithdrawal()" />
							</td>
						</tr> 

					</tbody>

				</table>
			</div>

		</div>
	</form>
</div>



<jsp:include page="../common/footer.jsp"></jsp:include>