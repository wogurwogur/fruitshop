<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% String ctxPath = request.getContextPath(); %>

<jsp:include page="../common/header.jsp"></jsp:include>

<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/member/memberRegister.css" />

<script type="text/javascript" src="<%= ctxPath%>/js/member/memberRegister.js"></script>

<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>



<div class="container">

	<form name="registerFrm">

		<div style="width: 450px; margin: 10px auto;">

			<div class="text-center"
				style="margin-top: 100px; margin-bottom: 50px;">
				<h2 style="font-weight: bold;">회원가입</h2>
				(<span class="star text-danger">*</span>표시는 필수입력사항)
			</div>


			<div>

				<table id="tblMemberRegister" class="w-100">
				
					<tbody>
						<tr>
							<td>
								<span class="star text-danger">*</span>
							</td>
							<td>
								<input type="text" name="userid" id="userid" style="width:440px;" maxlength="40" class="requiredInfo" placeholder="아이디 *" /><br>
								<span id="idcheckResult"></span>
								<span id="useridError" class="error"></span>
								<span>(영문소문자/숫자, 4~16자)</span>
							</td>
						</tr>

						<tr>
							<td>
								<span class="star text-danger">*</span>
							</td>
							<td>
								<input type="password" name="pwd" id="pwd" style="width:440px;" maxlength="15" class="requiredInfo" placeholder="비밀번호" /><br>
								<span id="pwdError" class="error"></span>
								<span>(영문 대소문자/숫자/특수문자 중 3가지 이상 조합, 8자~16자)</span>
							</td>
						</tr>

						<tr>
							<td>
								<span class="star text-danger">*</span>
							</td>
							<td>
								<input type="password" id="pwdcheck" style="width:440px;" maxlength="15" class="requiredInfo" placeholder="비밀번호 확인" /><br>
								<span id="pwdcheckError" class="error"></span>
							</td>
						</tr>

						<tr>
							<td>
								<span class="star text-danger">*</span>
							</td>
							<td>
								<input type="text" name="name" id="name" style="width:440px;" maxlength="30" class="requiredInfo" placeholder="성명" /><br>
								<span class="error"></span>
								<span>(한글, 2자~6자)</span>
							</td>
						</tr>

						<tr>
							<td>
								<span class="star text-danger">*</span>
							</td>
							<td>
								<input type="text" name="email" id="email" style="width:440px;" maxlength="60" class="requiredInfo" placeholder="이메일" />
								 <%-- 이메일중복체크 --%> 
								<span id="emailcheck">이메일중복확인</span><br>
								<span class="error">이메일 형식에 맞지 않습니다.</span>
								<span id="emailCheckResult"></span>
							</td>
						</tr>

						<tr>
							<td>
								<span class="star text-danger">*</span>
							</td>
							<td><select name="hp1" id="hp1" style="width: 100px;"
								required>
									<option value="010">010</option>
									<option value="011">011</option>
									<option value="016">016</option>
									<option value="017">017</option>
									<option value="018">018</option>
							</select>&nbsp;-&nbsp; <input type="text" name="hp2" id="hp2" size="6"
								maxlength="4" />&nbsp;-&nbsp; <input type="text" name="hp3"
								id="hp3" size="6" maxlength="4" /> <span class="error">휴대폰
									형식이 아닙니다.</span></td>
						</tr>

						<tr>
							<td>
								<span class="star text-danger">*</span>
							</td>
							<td><input type="text" name="postcode" id="postcode"
								size="6" maxlength="5" />&nbsp;&nbsp; <%-- 우편번호 찾기 --%> <img
								src="<%= ctxPath%>/images/b_zipcode.gif" id="zipcodeSearch" />
								<span class="error">우편번호 형식에 맞지 않습니다.</span></td>
						</tr>

						<tr>
							<td>
								<span class="star text-danger">*</span>
							</td>
							<td><input type="text" name="address" id="address" size="40"
								maxlength="200" placeholder="주소" /><br> <input type="text"
								name="extraaddress" id="extraAddress" size="40" maxlength="200"
								placeholder="참고항목" /><br> <input type="text"
								name="detailaddress" id="detailAddress" size="40"
								maxlength="200" placeholder="상세주소" /> <span class="error">주소를
									입력하세요.</span></td>
						</tr>

						<tr>
							<td>
								<span class="star text-danger">*</span>
							</td>
							<td><input type="radio" name="gender" value="1" id="male" /><label
								for="male" style="margin-left: 1.5%;">남자</label> <input
								type="radio" name="gender" value="2" id="female"
								style="margin-left: 10%;" /><label for="female"
								style="margin-left: 1.5%;">여자</label></td>
						</tr>

						<tr>
							<td>
								<span class="star text-danger">*</span>
							</td>
							<td><input type="text" name="birthday" id="datepicker"
								maxlength="10" /> <span class="error">생년월일은 마우스로만 클릭하세요.</span>
							</td>
						</tr>

						<tr>
							<td>
								<span class="star text-danger">*</span>
							</td>
							<td><label for="agree">이용약관에 동의합니다</label>&nbsp;&nbsp;<input
								type="checkbox" id="agree" /></td>
						</tr>

						<tr>
							<td>
								<span class="star text-danger"></span>
							</td>
							<td><iframe
									src="<%= ctxPath%>/iframe_agree/agree.html" width="100%"
									height="150px" style="border: solid 1px navy;"></iframe></td>
						</tr>

						<tr>
							<td>
								<span class="star text-danger"></span>
							</td>
							<td class="text-center">
								<input type="button" class="btn btn-success btn-lg mr-5" value="가입하기" onclick="goRegister()" />
								<input type="reset" class="btn btn-danger btn-lg" value="취소하기" onclick="goReset()" />
							</td>
						</tr> 

					</tbody>



				</table>
			</div>
			<%--    
          <div>
              <button onclick="goGaib()">type이 없으면 submit 임</button>&nbsp; 
              <button type="button" onclick="goGaib()">type이 button 인것</button>&nbsp;
              <button type="submit">type이 submit 인 것</button>
          </div>
       --%>
		</div>
	</form>
</div>





<jsp:include page="../common/footer.jsp"></jsp:include>


