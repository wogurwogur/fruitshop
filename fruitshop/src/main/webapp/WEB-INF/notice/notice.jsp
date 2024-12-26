<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String ctxPath = request.getContextPath();
%>   
 

<%-- Custom CSS --%>
<link rel="stylesheet" href="<%= request.getContextPath()%>/css/review/reviewList.css">

<jsp:include page="../common/header.jsp"></jsp:include>

<script type="text/javascript">

function delShow(){
	
	$("button#delShowBtn").addClass("delHide");
	
	$("button#delCancleBtn").removeClass("delHide");
	$("button#delCkBtn").removeClass("delHide");
	$("input:text[name='notice_no']").removeClass("delHide");
	
	$("button#delCkBtn").addClass("delShow");
	$("input:text[name='notice_no']").addClass("delShow");
	$("button#delCancleBtn").addClass("delShow");
	
}

function delCancler(){
	
	$("button#delShowBtn").removeClass("delHide");
	
	$("button#delCkBtn").removeClass("delShow");
	$("input:text[name='notice_no']").removeClass("delShow");
	
	$("button#delCancleBtn").removeClass("delShow");
	
	$("button#delShowBtn").addClass("delShow");
	$("button#delCkBtn").addClass("delHide");
	$("input:text[name='notice_no']").addClass("delHide");
	$("button#delCancleBtn").addClass("delHide");
	
	$("input:text[name='notice_no']").val("");
	
}

function delCheck(){
	
	const frm = document.noticeForm;
	
	const notice_no = Number(frm.notice_no.value);
	
	const notice_noReg = /^[0-9]*$/;
	
	if(notice_no == ""){
		alert("글번호를 입력해주세요");
		return;
	}
	
	if(!notice_noReg.test(notice_no)){
		alert("숫자로만 입력해주세요");
		return;
	}
	
	$.ajax({
		url:"deleteNotice.ddg",
		type:"post",
		data:{
			"notice_no":notice_no
		},
		dataType:"json",
		success:function(json){
			
			let v_html = "";
			
			console.log(json);
			
			if(json.length == 0){
				v_html += "<tr><td colspan='5'>존재하는 글이 없습니다.</td></tr>";
			}else{
				
				$.each(json, function(index, item){
	                v_html += `
					<tr>						
						<td>\${item.notice_no}</td>						
						<td>\${item.notice_title}</td>						
						<td>\${item.notice_regidate}</td>
						<td>\${item.notice_viewcount}</td>
					</tr>
	                `;
	            });// end of $.each(json, function(index, item){})---------
				
			}
			
            $("#notice_list").html(v_html);
            
            delCancler();
			
		},
		error: function(request, status, error){
            alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
        }
	});
	
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
		    <a class="nav-link mr-5" href="#" style="color: black; border-bottom: solid black 2px;">공지사항</a>
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
</div>

		
<div>		
<div class="col-md-11 mx-auto my-5">
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
			<c:if test="${not empty requestScope.noticeList}">
				<c:forEach var="nvo" items="${requestScope.noticeList}" varStatus="status">
					<tr>						
						<td>${nvo.notice_no}</td>						
						<td>${nvo.notice_title}</td>						
						<td>${nvo.notice_regidate}</td>
						<td>${nvo.notice_viewcount}</td>
					</tr>				
				</c:forEach>			
			</c:if>																	
			</tbody>
			<c:if test="${empty requestScope.noticeList}">
				<tr>
					<td colspan="5">존재하는 글이 없습니다.</td>
				</tr>
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
				<c:if test="${sessionScope.loginuser.role == 2}">
				<form name="noticeForm">
					<a href="<%=ctxPath%>/notice/noticeWrite.ddg">
						<button type="button" class="btn btn-outline-dark float-right delShow" style="height:4.1%">글쓰기</button>
					</a>
					<button type="button" class="btn btn-outline-danger float-right" id="delShowBtn" style="height:4.1%; margin-right:0.8%;" onclick="delShow()">삭제하기</button>
					<button type="button" class="btn btn-outline-dark float-right delHide" id="delCancleBtn" style="height:4.1%; margin-right:0.8%;" onclick="delCancler()">취소</button>
					<button type="button" class="btn btn-outline-danger float-right delHide" id="delCkBtn" style="height:4.1%; margin-right:0.8%;" onclick="delCheck()">확인</button>
					<input type="text" name="notice_no" class="delHide" id="notice_no" size="4">
					<input type="text" style="display:none"/>
				</form>
				</c:if>
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



	
<jsp:include page="../common/footer.jsp"></jsp:include>