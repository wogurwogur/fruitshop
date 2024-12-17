<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="admin.model.AdminDAO, admin.model.AdminDAO_imple" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% 
	String ctxPath = request.getContextPath();
%>

<script src="<%=ctxPath %>/js/admin/admin_member_management.js"></script>
<script type="text/javascript">

</script>
<style>

div#admin_top_nav{
	display: flex;
	margin-top: 1%;
}

div#admin_top_nav_center{
	margin: 0 auto;
}

/* drop down start */
.dropbtn {
  background-color: #04AA6D;
  color: white;
  padding: 16px;
  font-size: 16px;
  border: none;
}

.dropdown {
  position: relative;
  display: inline-block;
}

.dropdown-content {
  display: none;
  position: absolute;
  background-color: #f1f1f1;
  min-width: 160px;
  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
  z-index: 1;
}

.dropdown-content button {
  color: black;
  padding: 12px 16px;
  text-decoration: none;
  display: block;
  border: none;
}

.dropdown-content a:hover {background-color: #ddd;}

.dropdown:hover .dropdown-content {display: block;}

.dropdown:hover .dropbtn {background-color: #3e8e41;}

/* drop down end */


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
<div class="container-fluid " id="admin_top_nav">

	<%-- dropdown div start --%>
	<div class="dropdown">
	  <button class="dropbtn">정렬</button>
	  <div class="dropdown-content">
	    <button type="button">징계받은 회원</button>
	    <button type="button">----</button>
	    <button type="button">----</button>
	  </div>
	</div>
	<%-- dropdown div end --%>
	
	
	<%-- center div start --%>
	<div id="admin_top_nav_center">
		<span>회원관리 명단</span>
	</div>
	<%-- center div end --%>
		
		
	<%-- search div start --%>
	<div>
		<form class="example" id="member_management_frm" style="margin:auto;max-width:300px">
		  <input type="text" placeholder="Search.." name="search2">
		  <button type="submit"><i class="fa fa-search"></i></button>
		</form>
	</div>
	<%-- search div end --%>
	
</div>
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
					<tr data-toggle="modal" data-target="#userdetail" data-dismiss="modal">
						<td onclick="memberDetail('${memberinfo.user_no}')">${memberinfo.user_no}</td>
						<td onclick="memberDetail('${memberinfo.user_no}')">${memberinfo.name}</td>
						<td onclick="memberDetail('${memberinfo.user_no}')">${memberinfo.address}&nbsp;${memberinfo.detailaddress}&nbsp;${memberinfo.extraaddress}</td>
						<td onclick="memberDetail('${memberinfo.user_no}')">${memberinfo.gender}</td>
						<td onclick="memberDetail('${memberinfo.user_no}')">${memberinfo.tel}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		
	</c:if>
	<c:if test="${empty requestScope.member_allList}">
		<span>등록된 회원이 없습니다.</span>
	</c:if>
</div>

<%-- <div class="modal fade" id="userIdfind"> --%> <%-- 만약에 모달이 안보이거나 뒤로 가버릴 경우에는 모달의 class 에서 fade 를 뺀 class="modal" 로 하고서 해당 모달의 css 에서 zindex 값을 1050; 으로 주면 된다. --%> 
  <div class="modal fade" id="userdetail" data-backdrop="static"> <%-- 만약에 모달이 안보이거나 뒤로 가버릴 경우에는 모달의 class 에서 fade 를 뺀 class="modal" 로 하고서 해당 모달의 css 에서 zindex 값을 1050; 으로 주면 된다. --%>  
    <div class="modal-dialog modal-lg">
      <div class="modal-content">
      
        <!-- Modal header -->
        <div class="modal-header">
          <h4 class="modal-title">회원 상세정보</h4>
          <button type="button" class="close idFindClose" data-dismiss="modal">&times;</button>
        </div>
        
        <!-- Modal body -->
        <div class="modal-body">
          <div id="idFind">
             <iframe id="iframe_idFind" style="border: none; width: 100%; height: 350px;" src="/admin/adminMemberDetail.ddg"> 
             </iframe>
          </div>
        </div>
        
        <!-- Modal footer -->
        <div class="modal-footer">
          <button type="button" class="btn btn-danger idFindClose" data-dismiss="modal">Close</button>
        </div>
      </div>
      
    </div>
  </div>
