<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String ctxPath = request.getContextPath();
%>   
 

<%-- Custom CSS --%>
<link rel="stylesheet" href="<%= request.getContextPath()%>/css/review/reviewList.css">

<jsp:include page="../common/header.jsp"></jsp:include>

<div class="container-fluid">

	<div>
	<h1 class="text-center" style="margin-top: 4%;">Community</h1>
	<div class="font-weight-lighter text-center my-3">우리함께 나누는 싱싱한 이야기</div>
	</div>
	<div>
		<ul class="nav nav-pills navbar-light nav justify-content-center mt-4">
		  <li class="nav-item">
		    <a class="nav-link mr-5" href="#" style="color: black;">공지사항</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link mx-5" href="<%= ctxPath%>/review/reviewList.ddg" style="color: black;">구매후기</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link mx-5" href="<%= ctxPath%>/qna/qnaList.ddg" style="color: black;">QnA</a>
		  </li>
		  <li class="nav-item ml-5">	
		    <a class="nav-link" href="#" style="color: black;" tabindex="-1" aria-disabled="true">자주하는 질문</a>
		  </li>
		</ul>		
	</div>
	
	<hr style="width:85%;">
	<div id="productpage" style="display:flex; height:25%; margin-left:5%; margin-right:5%;">
	
		<div id = "productimg" class="border mt-4 ml-5" style="display:inline-block; height:180px; width:10%">
			<div style="text-align:center"><span style="line-height: 165px">IMG</span></div>			
		</div>
		<h4><div class=" mt-5 ml-5">상품명 : 그린키위 2kg</div><div class=" mt-5 ml-5">가격 : 79,000원</div></h4>
		
		<div class="m-auto" style="margin-left:30%; line-height: 100px;">
		<button type="button" class="btn btn-outline-dark float-right" style="height:30%">상품 상세페이지 보기</button>
		</div>
		
	</div>	
	<hr style="width:85%;">
	
	
	
	
	
	
	
	
	
	
</div>





















<jsp:include page="../common/footer.jsp"></jsp:include>