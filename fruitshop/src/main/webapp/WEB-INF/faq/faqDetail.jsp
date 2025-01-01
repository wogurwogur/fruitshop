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

function deletefaq(){
	
	const frm = document.faqDetailForm;
	
	frm.action = "<%=ctxPath%>/faq/deletefaq.ddg";
	frm.method = "post";
	
	frm.faq_no.value = '${requestScope.faqDetail.faq_no}';
	
	if(!confirm("정말 삭제하시겠습니까?")){
		return;
	}
	
	frm.submit();
	
}

</script>

<style type="text/css">

.delShow{
	display:inline;
}

.delHide{
	display:none;
}

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
		    <a class="nav-link" href="/faq/faqList.ddg" style="color: black;" tabindex="-1" aria-disabled="true">자주하는 질문</a>
		  </li>
		</ul>		
	</div>
</div>

		
<div>		
<div class="container mt-5">
			<div class="table-responsive">
			    <!-- .table-responsive 반응형 테이블(테이블의 원래 크기를 보존해주기 위한 것으로써, 디바이스의 width가 작아지면 테이블 하단에 스크롤이 생김) -->
			 
				<table class="table">
					<thead style="text-align: center;">
						<tr>
							<th width="33%;"></th>
							<th style="text-align: center;" width="33%">
								${(requestScope.faqDetail).faq_title}
							</th>
							<td style="text-align: right; border-bottom: solid #DEE2E6 2px;" width="33%">
								조회수 :&nbsp;${(requestScope.faqDetail).faq_viewcount}&nbsp;&nbsp;&nbsp;작성일&nbsp;:&nbsp;${fn:substring((requestScope.faqDetail).faq_regidate,0,10)}
							</td>
						</tr>
					</thead>
					<tbody>
						<tr style="border: solid #DEE2E6 2px;">
							<td colspan="3" height="400">
								<div class="container">
									${(requestScope.faqDetail).faq_contents}
								</div>
							</td>
						</tr>
					</tbody>
				
				</table>				
				<button type="button" class="btn btn-outline-secondary" onclick="location.href='<%=ctxPath%>/faq/faqList.ddg'" style="float: right; ">돌아가기</button>
				<c:if test="${sessionScope.loginuser.role == 2}">
				<button type="button" class="btn btn-outline-danger" onclick="deletefaq()" style="float: right; margin-right:0.8%">글삭제</button>
				</c:if>
				<form name="faqDetailForm">
					<input type="hidden" name="faq_no">				
				</form>
				
			<!-- 페이지네이션 -->
			 
		</div>
	</div>
	
</div>
</div>



	
<jsp:include page="../common/footer.jsp"></jsp:include>