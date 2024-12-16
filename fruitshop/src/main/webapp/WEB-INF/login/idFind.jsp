<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:include page="../common/header.jsp"></jsp:include>

<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/member/memberRegister.css" />

<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>



<div class="container">

	<form name="registerFrm">

		<div style="width: 450px; margin: 10px auto;">

			<div class="text-center"
				style="margin-top: 50px; margin-bottom: 50px;">
				<h2 style="font-weight: bold;">아이디 찾기</h2>
			</div>


			<div>

				<table id="tblMemberRegister" class="w-100">
				
					<tbody>
				
						

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
								<span class="error"></span>
							</td>
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

		</div>
	</form>
</div>


<jsp:include page="../common/footer.jsp"></jsp:include>


