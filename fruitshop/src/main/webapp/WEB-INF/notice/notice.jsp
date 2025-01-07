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

$(document).ready(function(){
	$("input:text[name='searchWord']").bind("keydown", function(e){
		
		if(e.keyCode == 13){
			noticeSearch();
		}
		
	});
	
});

function noticeDetail(notice_no){
	
	const frm = document.noticeForm;
	const searchWord = $("input:text[name='searchWord']").val();
	const searchType = $("select[name='searchType']").val();
	frm.notice_no_detail.value = notice_no;
	
	frm.searchType.value = searchType;
	
	frm.searchWord.value = searchWord;
	
	frm.currentShowPageNo.value = ${requestScope.currentShowPageNo};
	
	frm.action = "<%=ctxPath%>/notice/detailNotice.ddg";
	
	frm.submit();
	
}

function noticeSearch(){
	
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
	
	
	
	const noticefrm = document.noticeForm;
	
	
	noticefrm.searchType.value = searchType;
	noticefrm.searchWord.value = searchWord;
	noticefrm.currentShowPageNo.value = ${requestScope.currentShowPageNo};
	noticefrm.action = "<%=ctxPath%>/notice/noticeList.ddg";
	noticefrm.submit();
	
}

</script>

<style type="text/css">

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



<div class="container-fluid">

	<div>
	<h1 class="text-center" style="margin-top: 4%;">Community</h1>
	<div class="font-weight-lighter text-center my-3">우리함께 나누는 싱싱한 이야기</div>
	</div>
	<div>
		<ul class="nav nav-pills navbar-light nav justify-content-center mt-4">
		  <li class="nav-item">
		    <a class="nav-link mr-5" href="<%= ctxPath%>/notice/noticeList.ddg" style="color: black; border-bottom: solid black 2px;">공지사항</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link mx-5" href="<%= ctxPath%>/review/reviewList.ddg" style="color: black;">구매후기</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link mx-5" href="<%= ctxPath%>/qna/qnaList.ddg" style="color: black;">QnA</a>
		  </li>
		  <li class="nav-item ml-5">	
		    <a class="nav-link" href="<%= ctxPath%>/faq/faqList.ddg" style="color: black;" tabindex="-1" aria-disabled="true">자주하는 질문</a>
		  </li>
		</ul>		
	</div>
</div>

		
<div>	
	
<div class="container mt-4">
<form name="noticeForm">
<input type="text" name="currentShowPageNo" style="display:none;"/>
		<div style="float: right; display:flex;">
			<div>
				<select name="searchType" class="form -select form-select-lg mb-3" aria-label=".form-select-lg example" id ="searchType">
				    <option value="">검색대상</option>
				    <option value="notice_title">제목</option>
				    <option value="notice_contents">내용</option> 
				</select>
			</div>
			<div>
			  <input type="text" placeholder="입력란" name="searchWord" id="searchTypeWord">
			  <button type="button" onclick="noticeSearch()" id="searchButton"><i class="fa fa-search"></i></button>
			  <input type="hidden" name="detail_user_no">
			</div>
		</div>

			<div class="table-responsive">
			    <!-- .table-responsive 반응형 테이블(테이블의 원래 크기를 보존해주기 위한 것으로써, 디바이스의 width가 작아지면 테이블 하단에 스크롤이 생김) -->
			 
				<table class="table text-center table-hover">
					<tr>
						
						<th>글 번호</th>
						<th class="w-50">제목</th>
						<th>작성일자</th>
						<th>조회수</th>
					</tr>
					<%-- 글 리스트 --%>	
			<tbody id="notice_list">
			<c:if test="${not empty requestScope.notice_allList}">
				<c:forEach var="nvo" items="${requestScope.notice_allList}" varStatus="status">
					<tr onclick="noticeDetail('${nvo.notice_no}')" style="cursor:pointer;">						
						<td>${nvo.notice_no}</td>						
						<td>${nvo.notice_title}</td>						
						<td>${fn:substring(nvo.notice_regidate,0,10)}</td>
						<td>${nvo.notice_viewcount}</td>
					</tr>				
				</c:forEach>			
			</c:if>																	
			</tbody>
			<c:if test="${empty requestScope.notice_allList}">
				<tr>
					<td colspan="5">존재하는 글이 없습니다.</td>
				</tr>
			</c:if>
			
				</table>
				
		<div id ="Listsearch">

				<c:if test="${sessionScope.loginuser.role == 2}">
				
					<a href="<%=ctxPath%>/notice/noticeWrite.ddg">
						<button type="button" class="btn btn-outline-dark float-right delShow" style="height:4.1%; margin-right:0.8%;">글쓰기</button>
						
					</a>
				
				</c:if>
				<input type="text" name="notice_no_detail" style="display: none"/>
				<input type="text" name="loginuserRole" style="display: none"/>
				<input type="text" style="display: none"/>
		</div>
								
			<!-- 페이지네이션 -->
			<div style="margin-top: 5%; display: flex;">
		<div class='pagination'>
			${requestScope.pageBar}
		</div>
		</div>
		</div>
		</form>
	</div>
</div>
</div>



	
<jsp:include page="../common/footer.jsp"></jsp:include>