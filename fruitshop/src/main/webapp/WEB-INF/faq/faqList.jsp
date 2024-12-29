<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String ctxPath = request.getContextPath();
%>   
 

<%-- Custom CSS --%>
<link rel="stylesheet" href="<%= request.getContextPath()%>/css/review/reviewList.css">

<jsp:include page="../common/header.jsp"></jsp:include>

<script type="text/javascript">

function faqWrite(){
	
	const frm = document.faqForm;
	
	frm.action = "<%=ctxPath%>/faq/faqWrite.ddg";
	
	frm.submit();
	
}

function faqDetail(faq_no){
	
	const frm = document.faqForm;
	
	frm.action = "<%=ctxPath%>/faq/faqDetail.ddg";
	
	frm.faq_no_detail.value = faq_no;
	
	frm.submit();
	
}

</script>

<style type="text/css">



</style>



<div class="container-fluid">

	<div>
	<h1 class="text-center" style="margin-top: 4%;">Community</h1>
	<div class="font-weight-lighter text-center my-3">우리함께 나누는 싱싱한 이야기</div>
	</div>
	<div>
		<ul class="nav nav-pills navbar-light nav justify-content-center mt-4">
		  <li class="nav-item">
		    <a class="nav-link mr-5" href="<%= ctxPath%>/notice/noticeList.ddg" style="color: black;">공지사항</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link mx-5" href="<%= ctxPath%>/review/reviewList.ddg" style="color: black;">구매후기</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link mx-5" href="<%= ctxPath%>/qna/qnaList.ddg" style="color: black;">QnA</a>
		  </li>
		  <li class="nav-item ml-5">	
		    <a class="nav-link" href="<%= ctxPath%>/faq/faqList.ddg" style="color: black; border-bottom: solid black 2px;" tabindex="-1" aria-disabled="true" >자주하는 질문</a>
		  </li>
		</ul>		
	</div>
</div>

		
<div>		
<div class="container mt-4">
		<div style="float: right;">
				<select class = "text-center" style="height:4%">
					<option>제목</option>
					<option>내용</option>
					<option>작성자</option>
					<option>글번호</option>				
				</select>
				<input type="text" style="height:4%"></input>
				<button type="button" class="mb-1 btn btn-outline-dark" style="height:4.1%; margin-right: auto;">검색</button>
		</div>
<form name="faqForm">
			<div class="table-responsive">
			    <!-- .table-responsive 반응형 테이블(테이블의 원래 크기를 보존해주기 위한 것으로써, 디바이스의 width가 작아지면 테이블 하단에 스크롤이 생김) -->
			 
				<table class="table text-center">
					<tr>
						
						<th>글 번호</th>
						<th class="w-50">제목</th>
						<th>작성일자</th>
						<th>조회수</th>
					</tr>
					<%-- 글 리스트 --%>	
			<tbody id="notice_list">
			<c:if test="${not empty requestScope.faqList}">
				<c:forEach var="fvo" items="${requestScope.faqList}">
					<tr onclick="faqDetail('${fvo.faq_no}')">						
						<td>${fvo.faq_no}</td>						
						<td>${fvo.faq_title}</td>						
						<td>${fn:substring(fvo.faq_regidate,0,10)}</td>
						<td>${fvo.faq_viewcount}</td>
					</tr>				
				</c:forEach>			
			</c:if>																	
			</tbody>
			<c:if test="${empty requestScope.faqList}">
				<tr>
					<td colspan="5">존재하는 글이 없습니다.</td>
				</tr>
			</c:if>
			
				</table>
				
		<div id ="Listsearch">

				<c:if test="${sessionScope.loginuser.role == 2}">
				<a href="<%=ctxPath%>/faq/faqWrite.ddg">
					<button type="button" class="btn btn-outline-dark float-right delShow" style="height:4.1%; margin-right:0.8%;">글쓰기</button>
				</a>
				</c:if>
				<input type="text" name="faq_no_detail" style="display: none"/>
				<input type="text" name="loginuserRole" style="display: none"/>
				<input type="text" style="display: none"/>
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
		</form>
	</div>
</div>
</div>



	
<jsp:include page="../common/footer.jsp"></jsp:include>