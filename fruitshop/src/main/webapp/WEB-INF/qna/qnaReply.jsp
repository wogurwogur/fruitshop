<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    String ctxPath = request.getContextPath();
%> 

<% 
    String prodNo = request.getParameter("prodNo");
%>


<% 
    String qnaNo = request.getParameter("qnaNo");
%>
 
<script type="text/javascript">

	function rvRegister(prod_no, qna_no){
	
	const frm = document.qnaWriteFrm;
		
	console.log(prod_no);
	
	const qna_answer = $("textarea[name='qna_answer']").val();
	
	//const qna_no = $("input:text[name='qnaNo']").val();
	
	frm.prodNo.value = prod_no;	
	frm.qnaNo.value = qna_no;	
	frm.qna_answer.value = qna_answer;
	
	console.log(qna_answer);
	
	frm.action = "<%=ctxPath%>/qna/qnaReply.ddg";
	frm.method = "post";
	   
	frm.submit();
	
};



</script>
 
 
   
   
<jsp:include page="../common/header.jsp"></jsp:include>   

<div class="container-fluid">

	<div>
		<div class="text-center" style="margin-top: 4%; font-size:40pt">Community</div>
	<div class="font-weight-lighter text-center my-3" style="font-size:13pt">여러분의 이야기를 들려주세요</div>
	</div>
	<div style="font-size:14pt; font-weight:500;">
		<ul class="nav nav-pills navb ar-light nav justify-content-center mt-4">
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
		    <a class="nav-link" href="#" style="color: black;" tabindex="-1" aria-disabled="true">자주하는 질문</a>
		  </li>
		</ul>
		
	</div> 
	
	<%-- 제목 --%>
<form name="qnaWriteFrm">

<%-- 내용 --%>

	<div id="contents">
		<div class="d-flex mt-5">
			<span class="mr-5" style="margin-left:20%; font-size:17pt;">답글</span>
			<textarea name="qna_answer" cols="130" rows="20" style="resize:none;"></textarea>
		</div>	
	</div>
	<input type="hidden" name="prodNo"/>
	<input type="hidden" name="qnaNo"/>
</form>
	<div id="buttons">
		<div class="d-flex" style="margin-left:25%;">
			<button type="button" class="btn btn-outline-dark mt-3" style="width:120px;" onclick="location.href='<%=ctxPath%>/qna/qnaList.ddg'">목록</button>					
			<div class="ml-auto d-flex align-items-center" style="margin-right:21%; width:30%;" >
				<button type="submit" class="btn btn-outline-dark mt-3" style="width:120px;" onclick="rvRegister(<%= prodNo%>, <%= qnaNo%> )">답글 작성</button>
				<button type="reset" class="btn btn-outline-dark ml-5 mt-3" style="width:120px;">취소</button>
			</div>					
		</div>	
	</div>
	
	
</div>




<jsp:include page="../common/footer.jsp"></jsp:include> 