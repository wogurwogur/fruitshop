<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String ctxPath = request.getContextPath();
%>   
 

<%-- Custom CSS --%>
<link rel="stylesheet" href="<%= request.getContextPath()%>/css/qna/qnaList.css">

<jsp:include page="../common/header.jsp"></jsp:include>

<script type="text/javascript">


$(document).ready(function(){
	
$("select[name='searchType']").val("${requestScope.searchType}");
	
	$("input:text[name='searchWord']").val("${requestScope.searchWord}");
	
	$("select[name='sizePerPage']").val("${requestScope.sizePerPage}");
	
	// **** select 태그에 대한 이벤트는 click 이 아니라 change 이다. **** // 
	$("select[name='sizePerPage']").bind("change", function(){
		const frm = document.qna_search_frm;

		frm.submit();
		
});



	/*
	$("table#qnaReadTbl tr.qnaRead").click( e=> {
		
		const qna_no = $(e.target).parent().children(".qna_no").text();
		// alert(qna_no);
		
		const frm = document.qnaRead_frm;
		frm.qna_no.value = qna_no;
		
		frm.action = "${pageContext.request.contextPath}/qna/qnaRead.ddg";
		frm.method = "get"
		frm.submit();
		
});*/
	
 
	 
}); // end of $(document).ready(function(){


function listSearch(){
	
	const searchType = $("select[name='searchType']").val();				
	
	if(searchType == ""){
		alert("검색 대상을 선택하세요 !! ");
		return; 
	}
	const frm = document.qna_search_frm;
	// frm.action = "memberList.up"; // form 태그에 action 이 명기되지 않았으면 현재보이는 URL 경로로 submit 되어진다.
	// frm.method = "get"; // form 태그에 method 를 명기하지 않으면 "get" 방식이다.
	frm.submit();
	
} // end of function goSearch(){
	
	
function trclick1(qna_no){
	
	const frm = document.tr_frm1;
	frm.qna_noo.value = qna_no;
	frm.currentShowPageNo.value = '${requestScope.currentShowPageNo}';
	
	frm.action = "${pageContext.request.contextPath}/qna/qnaRead.ddg";
	frm.method = "get"
	frm.submit();
	
		
}
	


</script>


<style type="text/css">

div.pagination {
   border:solid 0px red;
   margin: 0 a uto;
}

div.pagination a {
  color: black;

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
	<div style="">
		<ul class="nav nav-pills navbar-light nav justify-content-center mt-4">
		  <li class="nav-item">
		    <a class="nav-link mr-5" href="<%= ctxPath%>/notice/noticeList.ddg" style="color: black;">공지사항</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link mx-5" href="<%= ctxPath%>/review/reviewList.ddg" style="color: black;">구매후기</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link mx-5" href="<%= ctxPath%>/qna/qnaList.ddg" style="color: black; border-bottom: solid black 2px;">QnA</a>
		  </li>
		  <li class="nav-item ml-5">	
		    <a class="nav-link" href="<%= ctxPath%>/faq/faqList.ddg" style="color: black;" tabindex="-1" aria-disabled="true">자주하는 질문</a>
		  </li>
		</ul>		
	</div>
</div>



		
<div>		
<div class="col-md-7 mx-auto my-4">
				<form name="qna_search_frm">	
				<div id ="Listsearch" style="float:right;">
					<select name="searchType" class = "text-center" style="height:4%">
						<option value="">검색기준</option>
						<option value="qna_title">제목</option>
						<option value="qna_contents">내용</option>				
					</select>
					<input type="text" placeholder="입력란" name="searchWord" style="height:4%"></input>
					<input type="text" style="display: none;" />
					<button type="button" class="mb-1 btn btn-outline-dark" style="height:4.1%" onclick="listSearch()"><i class="fa fa-search"></i></button>				
				</div>
			</form>
			<div class="table-responsive">
			    <!-- .table-responsive 반응형 테이블(테이블의 원래 크기를 보존해주기 위한 것으로써, 디바이스의 width가 작아지면 테이블 하단에 스크롤이 생김) -->
			 
				<table class="table text-center" id="qnaReadTbl">
					<tr>
						
						<th>글 번호</th>
						<th class="w-50">제목</th>
						<th>작성자</th>
						<th>작성일자</th>
						<th>조회수</th>
					</tr>
					<%-- 글 리스트 --%>	
				<c:if test="${not empty requestScope.qnaList}">
					<form name="tr_frm1">
						<input type="text" name="currentShowPageNo" style="display:none" />
						<input type="text" style="display:none"  />
							<c:forEach var="qvo" items="${requestScope.qnaList}" varStatus="status">
								<tr class="qnaRead" onclick="trclick1('${qvo.qna_no}')">					
									<td class="qna_no">${qvo.qna_no}</td>														
									<td><span class="text-body font-weight-bold">[${qvo.prod_name}]</span>&nbsp;&nbsp;${qvo.qna_title}
									<c:if test="${qvo.qna_answer != null}">
										<span class="text-danger font-weight-bold">[답변 완료]</span>						
									</c:if>		
									</td>						
									<td>${qvo.userid}</td>
									<td>${qvo.qna_regidate}</td>
									<td>${qvo.qna_viewcount}</td>						
								</tr>						
							</c:forEach>
							<input type="hidden" id="qna_noo" name="qna_noo"/>
					</form>
				</c:if>
							
					<c:if test="${empty requestScope.qnaList }">
			      		<tr>
			                <td colspan="6">데이터가 존재하지 않습니다.</td>
			            </tr>
		      		</c:if>																	
				</table>
				
		
		<a href="<%= ctxPath%>/qna/qnaWrite.ddg"><button type="button" class="btn btn-outline-dark float-right" style="height:4.1%">글쓰기</button></a>								
		
	</div>
	
			<div style="margin-top: 5%; display: flex; ">
      			<div class='pagination' style="margin: 0 auto;">
         			${requestScope.pageBar}
      			</div>
      		</div>
	</div>
				
</div>
<!-- 페이지네이션 -->

<form name = "qnaRead_frm">
	<input type="hidden" name="qna_no" />
	<input type="text" style="display:none" />
</form>



	
<jsp:include page="../common/footer.jsp"></jsp:include>