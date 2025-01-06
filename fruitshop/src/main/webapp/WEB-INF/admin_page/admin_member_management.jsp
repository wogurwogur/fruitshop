<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="admin.model.AdminDAO, admin.model.AdminDAO_imple" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<% 
	String ctxPath = request.getContextPath();
%>

<script src="<%=ctxPath %>/js/admin/admin_member_detail.js"></script>
<script type="text/javascript">

$(document).ready(function(){
	
	const searchWord = '${requestScope.searchWord}';
	
	const searchType = '${requestScope.searchType}';
	
	if(searchWord != ""){
		$("input:text[name='searchWord']").text(searchWord);
	}
	
	if(searchType != ""){
		$("select[name='searchType']")
	}
	
	$("input:text[name='searchWord']").bind("keydown", function(e){
		
		if(e.keyCode == 13){
			memberSearch();
		}
		
	});
	
});

function memberDetail(user_no, currentShowPageNo){
	
	const memberfrm = document.member_management_frm;
	
	const searchWord = $("input:text[name='searchWord']").val();
	
	const searchType = $("select[name='searchType']").val();
	
	memberfrm.detail_user_no.value = user_no;
	memberfrm.currentShowPageNo.value = currentShowPageNo;
	memberfrm.searchType.value = searchType;
	memberfrm.searchWord.value = searchWord;
	memberfrm.action = "<%=ctxPath%>/admin/adminMemberDetail.ddg";
	memberfrm.method = "post";
	
	
	memberfrm.submit();
	
}

function memberSearch(){
	
	const searchType = $("select[name='searchType']").val();
	
	if(searchType == ""){
		alert("검색대상을 선택하세요!!");
		return;
	}
	
	const searchWord = $("input:text[name='searchWord']").val();
	
	if(searchWord.trim() == ""){
		alert("검색어를 입력하세요!!");
		return;
	}
	
	
	
	const memberfrm = document.member_management_frm;
	
	
	memberfrm.searchType.value = searchType;
	memberfrm.searchWord.value = searchWord;
	memberfrm.currentShowPageNo.value = ${requestScope.currentShowPageNo};
	memberfrm.action = "<%=ctxPath%>/admin/adminManagement.ddg";
	memberfrm.method = "post";
	memberfrm.submit();
	
}

$(document).ready(function(){
	const modalOpenButton = document.getElementById('couponAllReceiptOpen');
	const modalCloseButton = document.getElementById('couponAllReceiptClose');
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
		
		if(!confirm("정말 쿠폰을 증정하시겠습니까?")){
			return;
		}
		
		const frm = document.member_management_frm;
		
		frm.action = "<%=ctxPath%>/coupon/receiptCouponAll.ddg";
		frm.method = "post";
		
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

#couponAllReceiptOpen{
	height: 36px;
}

#modalContainer.hidden {
  display: none;
}

div#admin_top_nav{
	display: flex;
	margin-top: 3%;
	margin-bottom: 0.5%;
}

div#admin_top_nav_center{
	margin: 0 1% 0 auto;
}

button#couponAllReceipt{
	vertical-align: middle;
	border: 0px;
}

select#searchType{
	height: 36px;
	vertical-align: middle;
}

#searchTypeWord{
	vertical-align: middle;
	height: 36px;
	padding: 0px;
}
#searchButton{
	vertical-align: middle;
	height: 36px;
	width: 35px;
	border: 0px;
}
#admin_top_nav_right{
	margin-right: 1%;
}



/* 페이징 숫자 처리 시작 */
div.pagination {
	border:solid 0px red;
	display: inline-block;
	margin: 0 auto;
}

div.pagination a {
  color: black;
  float: left;
  padding: 8px 16px;
  text-decoration: none;
  transition: background-color .3s;
  border-radius: 5px;
}

div.pagination a.active {
  /*background-color: #4CAF50;*/
  background-color: black;
  color: white;
  border-radius: 5px;
}

div.pagination a:hover:not(.active) {background-color: #ddd;}

</style>

<%-- div top nav start --%>

	<form name="member_management_frm">
	<div class="container" id="admin_top_nav">
		<%-- dropdown div start --%>
		<div>
	
		</div>
		<%-- dropdown div end --%>
		 
		
		<%-- center div start --%>
		
		<div id="admin_top_nav_center">
			<button type="button" class="btn btn-outline-success" id="couponAllReceiptOpen">쿠폰일괄증정</button>
		</div>
		<%-- center div end --%>
		
	<%-- search div start --%>
	<div id="admin_top_nav_right">
	<select name="searchType" class="form -select form-select-lg mb-3" aria-label=".form-select-lg example" id ="searchType">
	    <option value="">검색대상</option>
	    <option value="name">회원명</option>
	    <option value="userid">아이디</option>
	    <option value="email">이메일</option>
	    <option value="user_no">회원번호</option>
	  </select>
	</div> 
	<div>
	  <input type="text" placeholder="입력란" name="searchWord" id="searchTypeWord">
	  <button type="button" onclick="memberSearch()" id="searchButton"><i class="fa fa-search"></i></button>
	  <input type="hidden" name="detail_user_no">
	</div>		

	<%-- search div end --%>
	</div>
 

<%-- div top nav end --%>

<div class="container">
	
	<c:if test="${!empty requestScope.member_allList}">
		<table class="table table-hover"  style="text-align:center;">
			<thead>
				<tr>
					<th>회원번호</th>
					<th>회원이름</th>
					<th>주소</th>
					<th>성별</th>
					<th>전화번호</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="memberinfo" items="${member_allList}">
					<tr onclick="memberDetail('${memberinfo.user_no}','${requestScope.currentShowPageNo}')" style="cursor:pointer;">
						<td>${memberinfo.user_no}</td>
						<td>${memberinfo.name}</td>
						<td style="text-align:left;">${memberinfo.address}&nbsp;${memberinfo.detailaddress}&nbsp;${memberinfo.extraaddress}</td>
						<td>${memberinfo.gender}</td>
						<td>${fn:substring(memberinfo.tel,0,3)}-${fn:substring(memberinfo.tel,3,7)}-${fn:substring(memberinfo.tel,7,11)}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
	</c:if>
	<c:if test="${empty requestScope.member_allList}">
		<span>등록된 회원이 없습니다.</span>
	</c:if>
	
	<div style="margin-top: 5%; display: flex;">
		<div class='pagination'>
			${requestScope.pageBar}
		</div>
	</div>
	
   	<div id="modalContainer" class="hidden">
	  <div id="modalContent">
	    <div class="container mt-5">
	    	<table class="table" style="text-align:center;">
	    		<thead>
	    			<tr>
	    				<th colspan="2">모든회원 쿠폰 증정</th>
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
	    				<td><button class="btn btn-outline-secondary" type="button" id="couponAllReceiptClose">나가기</button></td>
	    			</tr>
	    		</tbody>
	    	</table>
	    	
		    <input type="text" name="currentShowPageNo" style="display:none;"/>
	    </div>
	  </div>
	</div>
</div>
	</form>

