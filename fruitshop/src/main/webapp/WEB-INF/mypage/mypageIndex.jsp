<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../common/header.jsp"></jsp:include>

<jsp:include page="mypage_list.jsp"></jsp:include>

<style type="text/css">

* {
	font-family: 'Noto Sans KR', sans-serif;
}
</style>

<div class="container">
	
	<div class="row mt-5">
		<div class="col-md-6 text-center p-0">
			  	<img src="<%= request.getContextPath()%>/images/mypage/빈프로필.jpg" class="rounded img-fluid" alt="round" width="20%">
			  	<span class="ml-3">감사합니다. ${sessionScope.loginuser.name} 회원님</span>
		</div>
		<div class="col-md-6"></div>
		
	</div>
	
	<div class="row mt-3 rounded border">
	
		<div class="col-sm-4 border p-3" style="line-height:30px; font-size:14pt">
		  	<ul>
		  		<li>적립금 : </li>
		  		<li>쿠폰 : </li>
		  	</ul>
		</div>
		
		<div class="col-sm-4">
		
		</div>
		
		<div class="col-sm-4">
		
		</div>

		
		
		
		
		
		
	</div>
</div>

<jsp:include page="../common/footer.jsp"></jsp:include>