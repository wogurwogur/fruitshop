<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<jsp:include page="../common/header.jsp"></jsp:include>


<style type="text/css">

* {
	font-family: 'Noto Sans KR', sans-serif;
}
</style>

<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/mypage/shipAdd.css" />

<script type="text/javascript" src="<%= request.getContextPath()%>/js/mypage/shipUpdate.js"></script>

<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

<script type="text/javascript">
	$(()=>{
		
		$("input#ship_name").val("${requestScope.svo.ship_name}");
		$("input#ship_receiver").val("${requestScope.svo.ship_receiver}");
		
		const ship_receivertel1 = "${requestScope.svo.ship_receivertel}".substring(0,3);
		const ship_receivertel2 = "${requestScope.svo.ship_receivertel}".substring(3,7);
		const ship_receivertel3 = "${requestScope.svo.ship_receivertel}".substring(7,11);
		$("input#ship_receivertel1").val(ship_receivertel1);
		$("input#ship_receivertel2").val(ship_receivertel2);
		$("input#ship_receivertel3").val(ship_receivertel3);
		
		$("input#ship_postcode").val("${requestScope.svo.ship_postcode}");
		$("input#ship_address").val("${requestScope.svo.ship_address}");
		$("input#ship_extraAddress").val("${requestScope.svo.ship_extraAddress}");
		$("input#ship_detailAddress").val("${requestScope.svo.ship_detailAddress}");
	
	});

</script>


<div class="container" style="margin-bottom: 100px;">

	<form name="updateFrm">

		<div style="width: 450px; margin: 10px auto;">

			<div class="text-center"
				style="margin-top: 50px; margin-bottom: 50px;">
				<h2 style="font-weight: bold;">배송지 수정</h2>
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
								<input type="text" name="ship_extraAddress" id="ship_extraAddress" size="40" maxlength="200" placeholder="참고항목" style="margin:3px 0;" />
								<input type="text" name="ship_detailAddress" id="ship_detailAddress" size="40" maxlength="200" placeholder="상세주소" style="margin:3px 0 auto 0;" />
								<span class="error"></span>
							</td>
						</tr>

						<tr>
							<td>
								<span class="star text-danger"></span>
							</td>
							<td class="text-center" style="height: 100px;">
								<input type="button" class="btn btn-outline-success btn-lg mr-5" value="수정하기" onclick="goUpdate()" />
								<input type="button" class="btn btn-outline-danger btn-lg" value="취소하기" onclick="goBack()" />
							</td>
						</tr> 

					</tbody>

				</table>
			</div>

		</div>
		
		
		<input type="hidden" name="ship_no" value="${requestScope.svo.ship_no}"/>
		
	</form>






</div>


<jsp:include page="../common/footer.jsp"></jsp:include>