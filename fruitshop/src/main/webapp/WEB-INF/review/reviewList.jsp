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
		
<div>		
<div class="col-md-11 mx-auto my-5">
			<div class="table-responsive">
			    <!-- .table-responsive 반응형 테이블(테이블의 원래 크기를 보존해주기 위한 것으로써, 디바이스의 width가 작아지면 테이블 하단에 스크롤이 생김) -->
			 
				<table class="table text-center">
					<tr>
						
						<th>글 번호</th>
						<th class="w-50">제목</th>
						<th>작성자</th>
						<th>작성일자</th>
						<th>조회수</th>
					</tr>
					<%-- 글 리스트 --%>

			<c:if test="${not empty requestScope.revList}">
			
				<c:forEach var="revvo" items="${requestScope.revList}" varStatus="status">
					
					<tr>						
						<td>${revvo.review_no}</td>	 				
						<%-- <td><a href="#"><span class="text-body font-weight-bold">[${revvo.prod_name}]</span></a>--%>
						<td><a href="#" class="text-body font-weight-bold">[${revvo.prod_name}]</a>
							<a href ="#" style="text-decoration:none" class = "text-dark">${revvo.review_title}</a>
							<c:if test="${revvo.comment_count ne '0'}"><span class="text-danger">[${revvo.comment_count}]</span></c:if>
							
						</td>						
						<td>${revvo.userid}</td>
						<td>${revvo.review_regidate}</td>
						<td>${revvo.review_viewcount}</td>
					</tr>	
								
				</c:forEach>
							
			</c:if>																	

				</table>
				
		<div id ="Listsearch">
				<select class = "text-center" style="height:4%">
					<option>제목</option>
					<option>내용</option>
					<option>작성자</option>
					<option>글번호</option>				
				</select>
				<input type="text" style="height:4%"></input>
				<button type="button" class="mb-1 btn btn-outline-dark" style="height:4.1%">검색</button>
				<button type="button" class="btn btn-outline-dark float-right" style="height:4.1%">글쓰기</button>
		</div>
								
			<!-- 페이지네이션 -->
			<nav>
			  <ul class="pagination justify-content-center text-center pagination-sm mt-3">
			    <li class="page-item"><a class="page-link text-body" href="#">이전</a></li>
			    <li class="page-item"><a class="page-link text-body font-weight-bold" href="#">1</a></li>
			    <li class="page-item"><a class="page-link text-body" href="#">2</a></li>
			    <li class="page-item"><a class="page-link text-body" href="#">3</a></li>
			    <li class="page-item"><a class="page-link text-body" href="#">4</a></li>
			    <li class="page-item"><a class="page-link text-body" href="#">5</a></li>
			    <li class="page-item"><a class="page-link text-body" href="#">다음</a></li>
			  </ul>
			</nav>
		</div>
	</div>
</div>
</div>


