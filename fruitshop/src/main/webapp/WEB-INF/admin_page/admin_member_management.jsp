<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="admin.model.AdminDAO, admin.model.AdminDAO_imple" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% 
	String ctxPath = request.getContextPath();
%>

<script src="<%=ctxPath %>/js/admin/admin_member_detail.js"></script>
<script type="text/javascript">

function memberDetail(user_no){
	
	const memberfrm = document.member_management_frm;
	
	
	memberfrm.method = "post";
	memberfrm.action = "<%=ctxPath%>/admin/adminMemberDetail.ddg";
	
	memberfrm.detail_user_no.value = user_no;
	
	memberfrm.submit();
	
}


</script>
<style>

div#admin_top_nav{
	display: flex;
	margin-top: 1%;
}

div#admin_top_nav_center{
	margin: 0 auto;
}


/* search start */
form.example input[type=text] {
  padding: 10px;
  font-size: 17px;
  border: 1px solid grey;
  float: left;
  width: 80%;
  background: #f1f1f1;
}

form.example button {
  width: 20%;
  padding: 10px;
  background: #2196F3;
  color: white;
  font-size: 17px;
  border: 1px solid grey;
  border-left: none;
  cursor: pointer;
}


/* search end  */
</style>

<%-- div top nav start --%>

	<form name="memberSerchSort">
	<div class="container-fluid " id="admin_top_nav">
	<%-- dropdown div start --%>
	<div>
	  <select name="select_sort" class="form -select form-select-lg mb-3" aria-label=".form-select-lg example">
	    <option>정렬방식</option>
	    <option value="1">Audi</option>
	    <option value="2">BMW</option>
	    <option value="3">Citroen</option>
	    <option value="4">Ford</option>
	  </select>
	</div>
	<%-- dropdown div end --%>
	
	
	<%-- center div start --%>
	<div id="admin_top_nav_center">
		<span>회원관리 명단</span>
	</div>
	<%-- center div end --%>
		
		
	<%-- search div start --%>
	<div>
		
		  <input type="text" placeholder="Search.." name="search2">
		  <button type="submit"><i class="fa fa-search"></i></button>
		  <input type="hidden" name="detail_user_no">
		
			
	</div>
	<%-- search div end --%>
	</div>
</form> 

<%-- div top nav end --%>

<div class="container-fluid ">
	
	<c:if test="${!empty requestScope.member_allList}">
		<table class="table">
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
					<tr onclick="memberDetail('${memberinfo.user_no}')">
						<td>${memberinfo.user_no}</td>
						<td>${memberinfo.name}</td>
						<td>${memberinfo.address}&nbsp;${memberinfo.detailaddress}&nbsp;${memberinfo.extraaddress}</td>
						<td>${memberinfo.gender}</td>
						<td>${memberinfo.tel}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
	</c:if>
	<c:if test="${empty requestScope.member_allList}">
		<span>등록된 회원이 없습니다.</span>
	</c:if>
</div>


