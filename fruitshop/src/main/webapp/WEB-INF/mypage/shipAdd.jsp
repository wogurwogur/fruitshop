<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<jsp:include page="../common/header.jsp"></jsp:include>


<style type="text/css">

* {
	font-family: 'Noto Sans KR', sans-serif;
}
</style>

<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/mypage/shipAdd.css" />

<script type="text/javascript" src="<%= request.getContextPath()%>/js/mypage/shipAdd.js"></script>

<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<script type="text/javascript">
	$(()=>{
		
	
	});

</script>


<div class="container" style="margin-bottom: 100px;">

	<form name="addFrm">

		<div style="width: 450px; margin: 10px auto;">

			<div class="text-center"
				style="margin-top: 50px; margin-bottom: 50px;">
				<h2 style="font-weight: bold;">배송지 추가</h2>
				(<span class="star text-danger">*</span>표시는 필수입력사항)
			</div>


			<div>

				<table id="tblMemberRegister" class="w-100">
				
					<tbody>
						<tr>
							<td>
								<span class="star text-danger">*</span>
							</td>
							<td style="height: 80px; vertical-align:top;">
								<input type="text" name="ship_name" id="ship_name" maxlength="40" class="requiredInfo info" placeholder="배송지명" /><br>
								<span id="ship_nameError" class="error"></span><span class="rule">(영문소문자/숫자, 1~10자)</span>
							</td>
						</tr>


						<tr>
							<td>
								<span class="star text-danger">*</span>
							</td>
							<td style="height: 80px; vertical-align:top;">
								<input type="text" name="ship_receiver" id="ship_receiver" maxlength="30" class="requiredInfo info" placeholder="받는사람 성명" /><br>
								<span class="error"></span><span class="rule">(한글, 2자~6자)</span>
							</td>
						</tr>

						<tr>
							<td>
								<span class="star text-danger">*</span>
							</td>
							<td style="height: 80px; vertical-align:top;">
								<select name="ship_receivertel1" id="ship_receivertel1" style="width: 100px;" required>
									<option value="010">010</option>
									<option value="011">011</option>
									<option value="016">016</option>
									<option value="017">017</option>
									<option value="018">018</option>
								</select>&nbsp;-&nbsp;
								<input type="text" name="ship_receivertel2" id="ship_receivertel2" class="info" style="width: 100px;" size="6" maxlength="4" />&nbsp;-&nbsp;
								<input type="text" name="ship_receivertel3"id="ship_receivertel3" class="info" style="width: 100px;" size="6" maxlength="4" /><br>
								<span class="error"></span>
							</td>
						</tr>


						<tr>
							<td>
								<span class="star text-danger">*</span>
							</td>
							<td>
								<input type="text" name="ship_postcode" id="ship_postcode" style="width: 100px;" size="6" maxlength="5" placeholder="우편번호"/>&nbsp;&nbsp;
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
								<input type="text" name="ship_address" id="ship_address" size="40" maxlength="200" placeholder="주소" style="margin:3px 0;" />
								<input type="text" name="ship_extraadress" id="ship_extraadress" size="40" maxlength="200" placeholder="참고항목" style="margin:3px 0;" />
								<input type="text" name="ship_detailaddress" id="ship_detailaddress" size="40" maxlength="200" placeholder="상세주소" style="margin:3px 0 auto 0;" />
								<span class="error"></span>
							</td>
						</tr>

						<tr>
							<td>
								<span class="star text-danger"></span>
							</td>
							<td class="text-center" style="height: 100px;">
								<input type="button" class="btn btn-success btn-lg mr-5" value="가입하기" onclick="goAdd()" />
								<input type="button" class="btn btn-danger btn-lg" value="취소하기" onclick="goBack()" />
							</td>
						</tr> 

					</tbody>

				</table>
			</div>

		</div>
	</form>






</div>


<jsp:include page="../common/footer.jsp"></jsp:include>