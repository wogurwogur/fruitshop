<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<% 
	String ctxPath = request.getContextPath();
%>

<script type="text/javascript">

function adminRoleAddandRemove(role, user_no){
	
	const frm = document.roleAddRemove;
	
	frm.role.value = role;
	frm.user_no.value = user_no;
	
	frm.action = "<%=ctxPath%>/admin/adminMemberRole.ddg";
	frm.method = "post";
	
	let ck_adminRole = "";
	
	if(role == 1){
		 ck_adminRole = confirm("관리자 권한을 부여하시겠습니까?");
	}else{
		 ck_adminRole = confirm("관리자 권한을 박탈하시겠습니까?");
	}
	
	
	if(ck_adminRole == true){
		frm.submit();
	}
	
	
}

$(document).ready(function(){
	const modalOpenButton = document.getElementById('couponModalOpen');
	const modalCloseButton = document.getElementById('couponModalClose');
	const modal = document.getElementById('modalContainer');
	const couponModalSubmit = document.getElementById('getElementById');
	let coupon_discount_ck = false;
	let coupon_discount_result;

	modalOpenButton.addEventListener('click', () => {
	  modal.classList.remove('hidden');
	});

	modalCloseButton.addEventListener('click', () => {
	  modal.classList.add('hidden');
	});
	const coupon_discount = $("input:text[name='coupon_discount']");
	
	coupon_discount.bind("blur",function(){
		
		const discount_Reg = /^[0-9]{2,7}$/;
		
		if(discount_Reg.test(coupon_discount.val())){
			
			coupon_discount_result = coupon_discount.val();
			
			const coupon_discount_str = (Number(coupon_discount.val())).toLocaleString();
			
			coupon_discount.val(coupon_discount_str);
			coupon_discount_ck = true;
		}else if(coupon_discount.val() == ""){
			coupon_discount_ck = false;
		}else{
			alert("숫자만 입력해주세요.");
			coupon_discount_ck = false;
		}
		
		
	});
	
	
	
	
	$("button#couponModalSubmit").click(function(){
		
		
		const coupon_name = $("input:text[name='coupon_name']").val();
		const couponname_Reg = /^[가-힣]{2,8}$/;
		
		if(coupon_name == ""){
			alert("쿠폰명을 입력해주세요.");
			return;
		}
		if(!couponname_Reg.test(coupon_name)){
			alert("쿠폰명은 한글로 2글자 이상 8 글자 이하로 입력해주세요.");
			return;
		}
		
		
		const coupon_descript = $("textarea[name='coupon_descript']").val();
		const coupondescript_Reg = /^[가-힣\s~!@#$%^&*()-_`=+?><;:]{4,40}$/;
		
		if(coupon_descript == ""){
			alert("쿠폰설명을 입력해주세요.");
			return;
		}
		if(!coupondescript_Reg.test(coupon_descript)){
			alert("쿠폰설명은 한글로 4글자 이상 40 글자 이하로 입력해주세요.");
			return;
		}
		
		const coupon_expire_str = $("input[name='coupon_expire']").val();
		const coupon_expire = new Date(coupon_expire_str);
		const now = new Date();
		
		if(now > coupon_expire){
			alert("현재 날짜 이후로 설정해주세요.");
			return;
		}
		
		
		
		const coupon_discount = $("input:text[name='coupon_discount']").val();
		
		
		if(coupon_discount == ""){
			alert("할인금액을 입력해주세요.");
			return;
		}
		
		if(coupon_discount_ck == false){
			alert("쿠폰금액을 다시입력해주세요.");
			return;
		}
		
		
		const frm = document.roleAddRemove;
		
		frm.action = "<%=ctxPath%>/coupon/receiptCoupon.ddg";
		frm.method = "post";
		
		frm.user_no.value = "${requestScope.detailMember.user_no}";
		frm.coupon_name.value = coupon_name;
		frm.coupon_descript.value = coupon_descript;
		frm.coupon_expire.value = coupon_expire_str;
		frm.coupon_discount.value = coupon_discount_result;
		
		frm.submit();
		
		
		
	});
	
});



</script>

<style>

#modalOpenButton, #modalCloseButton {
  cursor: pointer;
}

#modalContainer {
  width: 100%;
  height: 100%;
  position: fixed;
  top: 0;
  left: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  background: rgba(0, 0, 0, 0.5);
}

#modalContent {
  position: absolute;
  background-color: #ffffff;
  width: 500px;
  height: 480px;
  padding: 15px;
  border-radius: 20%;
}

#modalContainer.hidden {
  display: none;
}
#modalContainer {
  width: 100%;
  height: 100%;
  position: fixed;
  top: 0;
  left: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  background: rgba(0, 0, 0, 0.5);
}

#modalContainer.hidden {
  display: none;
}
input[type="number"]{
	width: 5em;
}

</style>

<div class="container">
	
	<table class="table" style="text-align:center; margin-top:2%;">
		<thead>
			<tr>
				<th colspan="2">${requestScope.detailMember.name} 님의 회원정보 입니다.</th>
			</tr>
		</thead>
		<tbody>
		
			<tr>
				<td>회원번호</td>
				<td>${requestScope.detailMember.user_no}</td>
			</tr>
			<tr>
				<td>생년월일</td>
				<td>${requestScope.detailMember.birthday}</td>
			</tr>
			<tr>
				<td>이메일</td>
				<td>${requestScope.detailMember.email}</td>
			</tr>
			<tr>
				<td>연락처</td>
				<c:if test="${not empty requestScope.detailMember.tel}"><td>${fn:substring(requestScope.detailMember.tel,0,3)}-${fn:substring(requestScope.detailMember.tel,3,7)}-${fn:substring(requestScope.detailMember.tel,7,11)}</td></c:if>
				<c:if test="${empty requestScope.detailMember.tel}">없음</c:if>
			</tr>
			<tr>
				<td>우편번호</td>
				<td>${requestScope.detailMember.postcode}</td>
			</tr>
			<tr>
				<td>주소</td>
				<td>
				<c:if test="${empty requestScope.detailMember.address}"></c:if>
				<c:if test="${not empty requestScope.detailMember.address}">${requestScope.detailMember.address}</c:if>				
				<c:if test="${empty requestScope.detailMember.detailaddress}"></c:if>
				<c:if test="${not empty requestScope.detailMember.detailaddress}">${requestScope.detailMember.detailaddress}</c:if>
				<c:if test="${empty requestScope.detailMember.extraaddress}"></c:if>
				<c:if test="${not empty requestScope.detailMember.extraaddress}">${requestScope.detailMember.extraaddress}</c:if>
				</td>
			</tr>
			<tr>
				<td>성별</td>
				<td>
					${requestScope.detailMember.gender}
				</td>
			</tr>
			<tr>
				<td>적립금</td>
				
				<td><fmt:parseNumber value="${requestScope.detailMember.point}" pattern="#,###"></fmt:parseNumber></td>
			</tr>
			<tr>
				<td>가입일자</td>
				<td>${fn:substring(requestScope.detailMember.registerday,0,11)}</td>
			</tr>
			<tr>
				<td>마지막 비밀번호 변경일자</td>
				<td>${fn:substring(requestScope.detailMember.lastpwdchangedate,0,11)}</td>
			</tr>
			<tr>
				<td>회원상태</td>
				<c:if test="${requestScope.detailMember.idle eq '0'}"><td>휴면상태</td></c:if>
				<c:if test="${requestScope.detailMember.idle eq '1'}"><td>활동중</td></c:if>
			</tr>
			<tr>
				<td>회원가입 상태</td>
				<c:if test="${requestScope.detailMember.status eq '0'}"><td>탈퇴</td></c:if>
				<c:if test="${requestScope.detailMember.status eq '1'}"><td>가입중</td></c:if>
			</tr>
			<tr>
				<td>회원 권한</td>
				<c:if test="${requestScope.detailMember.role eq '1'}"><td>일반회원</td></c:if>
				<c:if test="${requestScope.detailMember.role eq '2'}"><td>관리자</td></c:if>
			</tr>
			<tr>
				<td>보유 쿠폰개수</td>
				<c:if test="${!empty requestScope.memberCoupon}"><td>${requestScope.memberCoupon}</td></c:if>
				<c:if test="${empty requestScope.memberCoupon}"><td>0</td></c:if>
			</tr>
		</tbody>
	
	</table>
	<table class="table" style="text-align:center;">
		<tbody>
			<tr>
				<td><button type="button" class="btn btn-outline-success" id="couponModalOpen">쿠폰증정</button></td>
				<!-- <td><button type="button" class="btn btn-outline-success" id="couponModal">징계처분</button></td> -->
				<c:if test="${requestScope.detailMember.role eq '1'}"><td><button type="button" class="btn btn-outline-success" onclick="adminRoleAddandRemove('${requestScope.detailMember.role}','${requestScope.detailMember.user_no}')">관리자권한부여</button></td></c:if>
				<c:if test="${requestScope.detailMember.role eq '2'}"><td><button type="button" class="btn btn-outline-danger" onclick="adminRoleAddandRemove('${requestScope.detailMember.role}','${requestScope.detailMember.user_no}')">관리자권한박탈</button></td></c:if>
				<td><button type="button" class="btn btn-outline-secondary" onclick="javascript:location.href='<%=ctxPath%>/admin/adminManagement.ddg'">돌아가기</button></td>
			</tr>
		</tbody>
	</table>
	
	<form name="roleAddRemove">
		<input type="hidden" name="role"/>
		<input type="hidden" name="user_no"/>

	
	<div id="modalContainer" class="hidden">
	  <div id="modalContent">
	    <div class="container mt-5">
	    	<table class="table" style="text-align:center;">
	    		<thead>
	    			<tr>
	    				<th colspan="2">${requestScope.detailMember.name} 님께 쿠폰 증정</th>
	    			</tr>
	    		</thead>
	    		<tbody>
	    			<tr>
	    				<td>쿠폰이름</td>
	    				<td><input type="text" name="coupon_name" size="8"></td>
	    			</tr>
	    			<tr>
	    				<td>쿠폰설명</td>
	    				<td><textarea name="coupon_descript"></textarea></td>
	    			</tr>
	    			<tr>
	    				<td>쿠폰유효기간</td>
	    				<td><input type="date" name="coupon_expire" size="8"></td>
	    			</tr>
	    			<tr>
	    				<td>할인금액</td>
	    				<td><input type="text" name="coupon_discount" min="500" step="500"></td>
	    			</tr>
	    			<tr>
	    				<td><button class="btn btn-outline-success" type="button" id="couponModalSubmit">쿠폰수령하기</button></td>
	    				<td><button class="btn btn-outline-secondary" type="button" id="couponModalClose">나가기</button></td>
	    			</tr>
	    		</tbody>
	    	</table>
	    	
		    <input type="text" style="display:none;"/>
	    </div>
	  </div>
	</div>
	</form>
</div>
