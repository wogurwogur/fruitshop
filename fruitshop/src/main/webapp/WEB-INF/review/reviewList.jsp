<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    String ctxPath = request.getContextPath();
%>   
 

<%-- Custom CSS --%>
<link rel="stylesheet" href="<%= request.getContextPath()%>/css/review/reviewList.css">

<jsp:include page="../common/header.jsp"></jsp:include>

<div id="container">
	<div>
	<h1 style="margin-top: 4%; text-align:center;">Community</h1>
	<div class="font-weight-light text-center my-1">우리함께 나누는 싱싱한 이야기</div>
	</div>
	<div>
		<ul class="nav nav-pills nav:light">
		  <li class="nav-item">
		    <a class="nav-link" href="#">공지사항</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link" href="#">구매후기</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link" href="#">QnA</a>
		  </li>
		  <li class="nav-item">	
		    <a class="nav-link" href="#" tabindex="-1" aria-disabled="true">자주하는 질문</a>
		  </li>
		</ul>
		
	</div>
		
<div>		
<div class="col-md-11 mx-auto my-5">
<hr>
			<div class="table-responsive">
			    <!-- .table-responsive 반응형 테이블(테이블의 원래 크기를 보존해주기 위한 것으로써, 디바이스의 width가 작아지면 테이블 하단에 스크롤이 생김) --> 
				<table class="table">
					<tr>
						<th>글 번호</th>
						<th>제목</th>
						<th>작성자</th>
						<th>작성일자</th>
						<th>조회수</th>
					</tr>																			
				</table>
				<hr>
				<div>
				<table>
					<th>작성된 글이 없습니다.</th>
				</table>
				</div>
				<hr>
				<div>
				<select>
					<option>제목</option>
					<option>내용</option>
					<option>작성자</option>
					<option>글번호</option>				
				</select>
				<input type="text"></input>
				<button type="button" class="mb-1 btn btn-outline-dark">검색</button>
				<button type="button" class="btn btn-outline-dark float-right ">글쓰기</button>
				</div>
				
				

				
			<!-- 페이지네이션 -->
			<nav>
			  <ul class="pagination justify-content-center text-center pagination-sm">
			    <li class="page-item"><a class="page-link" href="#">이전</a></li>
			    <li class="page-item"><a class="page-link font-weight-bold" href="#">1</a></li>
			    <li class="page-item"><a class="page-link" href="#">2</a></li>
			    <li class="page-item"><a class="page-link" href="#">3</a></li>
			    <li class="page-item"><a class="page-link" href="#">4</a></li>
			    <li class="page-item"><a class="page-link" href="#">5</a></li>
			    <li class="page-item"><a class="page-link" href="#">다음</a></li>
			  </ul>
			</nav>
		</div>
	</div>
</div>
</div>

<jsp:include page="../common/footer.jsp"></jsp:include>
