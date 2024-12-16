<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String ctxPath = request.getContextPath();
%>   
 

<%-- Custom CSS --%>
<link rel="stylesheet" href="<%= request.getContextPath()%>/css/review/reviewList.css">

<jsp:include page="../common/header.jsp"></jsp:include>

<div class="container">
	<div>
	<h1 class="text-center" style="margin-top: 4%;">Community</h1>
	<div class="font-weight-lighter text-center my-1">우리함께 나누는 싱싱한 이야기</div>
	</div>
	<div>
		<ul class="nav nav-pills navbar-light nav justify-content-center ">
		  <li class="nav-item">
		    <a class="nav-link" href="#" style="color: black;">공지사항</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link" href="#" style="color: black;">구매후기</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link" href="#" style="color: black;">QnA</a>
		  </li>
		  <li class="nav-item">	
		    <a class="nav-link" href="#" style="color: black;" tabindex="-1" aria-disabled="true">자주하는 질문</a>
		  </li>
		</ul>
		
	</div>
		
<div>		
<div class="col-md-11 mx-auto my-5">
			<div class="table-responsive">
			    <!-- .table-responsive 반응형 테이블(테이블의 원래 크기를 보존해주기 위한 것으로써, 디바이스의 width가 작아지면 테이블 하단에 스크롤이 생김) -->
			<hr> 
				<table class="table text-center">
					<tr>
						<th>글 번호</th>
						<th>제목</th>
						<th>작성자</th>
						<th>작성일자</th>
						<th>조회수</th>
					</tr>																			
					<tr>
						<td>1</td>
						<td>제목입니다제목입니다제목입니다제목입니다제목입니다제목입니다</td>
						<td>2</td>
						<td>3</td>
						<td>4</td>
					</tr>
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
			  <ul class="pagination justify-content-center text-center pagination-sm">
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

<jsp:include page="../common/footer.jsp"></jsp:include>
