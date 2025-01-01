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

$(document).ready(function(){
	const FaqModalUpdateOpen = document.getElementById('FaqModalUpdateOpen');
	const FaqModalUpdateClose = document.getElementById('FaqModalUpdateClose');
	const modal = document.getElementById('modalContainer');
	const FaqModalUpdateSubmit = document.getElementById('FaqModalUpdateSubmit');

	FaqModalUpdateOpen.addEventListener('click', () => {
	  modal.classList.remove('hidden');
	});

	FaqModalUpdateClose.addEventListener('click', () => {
	  modal.classList.add('hidden');
	});	
	
	$("button#FaqModalUpdateSubmit").click(function(){
		
		
		const faq_title = $("input:text[name='faq_title']").val();
		const faqtitle_Reg = /^[가-힣\s~!@#$%^&*()-_`=+?><;:]{2,50}$/;
		
		if(faq_title == ""){
			alert("자주하는질문 제목을 입력해주세요.");
			return;
		}
		if(!faqtitle_Reg.test(faq_title)){
			alert("공지사항 제목은은 한글로 2글자 이상 50글자 이하로 입력해주세요.");
			return;
		}
		
		
		const faq_contents = $("textarea[name='faq_contents']").val();
		const faqcontents_Reg = /^[가-힣\s~!@#$%^&*()-_`=+?><;:]{2,200}$/;
		
		if(faq_contents == ""){
			alert("공지사항 내용을 입력해주세요.");
			return;
		}
		if(!faqcontents_Reg.test(faq_contents)){
			alert("자주하는질문 내용은 한글로 글자 이상 200 글자 이하로 입력해주세요.");
			return;
		}
			
		
		if(!confirm("자주하는질문을 수정하시겠습니까?")){
			return;
		}
		
		$.ajax({
              url : "${pageContext.request.contextPath}/faq/updatefaq.ddg",
              type : "post",
              data : {
            	"faq_no":"${(requestScope.faqDetail).faq_no}",
            	"faq_title":faq_title,
            	"faq_contents":faq_contents
              },
              dataType:"json",
              success:function(json){
                 console.log("~~~ 확인용 : " + JSON.stringify(json));
                 // ~~~ 확인용 : {"result":1}
                 
                 if(json.n == 1) {
                    location.href="${pageContext.request.contextPath}/faq/faqDetail.ddg?faq_no_detail="+json.faq_no;
                 }
                 
              },
              error: function(request, status, error){
                 alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
                 
              }
              
 			});
		
		
		
	});
	
});


</script>

<style type="text/css">

#modalOpenButton, #modalCloseButton {
  cursor: pointer;
}

#modalContainer {
  width: 100%;
  height: 100%;
  position: fixed;
  top: 0;
  left: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  background: rgba(0, 0, 0, 0.5);
}

#modalContent {
  position: absolute;
  background-color: #ffffff;
  width: 500px;
  height: 480px;
  padding: 15px;
  border-radius: 20%;
}

#modalContainer.hidden {
  display: none;
}
#modalContainer {
  width: 100%;
  height: 100%;
  position: fixed;
  top: 0;
  left: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  background: rgba(0, 0, 0, 0.5);
}

#modalContainer.hidden {
  display: none;
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
				<button type="button" class="btn btn-outline-success" style="float: right; margin-right:0.8%" id="FaqModalUpdateOpen">글수정</button>
				</c:if>
				<form name="faqDetailForm">
					<input type="hidden" name="faq_no">				
				</form>
				
			<!-- 페이지네이션 -->
			 
		</div>
	</div>
			<div id="modalContainer" class="hidden">
	  <div id="modalContent">
	    <div class="container mt-5">
	    	<table class="table" style="text-align:center;">
	    		<thead>
	    			<tr>
	    				<th colspan="2">${requestScope.faqDetail.faq_title} 수정하기</th>
	    			</tr>
	    		</thead>
	    		<tbody>
	    			<tr>
	    				<td>제목</td>
	    				<td><input type="text" name="faq_title" size="8"></td>
	    			</tr>
	    			<tr>
	    				<td>내용</td>
	    				<td><textarea name="faq_contents"></textarea></td>
	    			</tr>
	    			<tr>
	    				<td><button class="btn btn-outline-success" type="button" id="FaqModalUpdateSubmit">수정하기</button></td>
	    				<td><button class="btn btn-outline-secondary" type="button" id="FaqModalUpdateClose">나가기</button></td>
	    			</tr>
	    		</tbody>
	    	</table>
	    	
		    <input type="text" style="display:none;"/>
	    </div>
	  </div>
	</div>
	
</div>
</div>



	
<jsp:include page="../common/footer.jsp"></jsp:include>