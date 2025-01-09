<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String ctxPath = request.getContextPath();
%>   
 

<%-- Custom CSS
<link rel="stylesheet" href="<%= request.getContextPath()%>/css/reviewWrite/reviewWrite.css"> --%>

<jsp:include page="../common/header.jsp"></jsp:include>

<script type="text/javascript">

function faqRegister(){
	
	const frm = document.faqWriteFrm;
	
	/* 제목 유효성 검사 시작 */
	
	const faqTitle_Reg = /^[가-힣\s~!@#$%^&*()-_`=+?><;:]{2,50}$/;
	const faq_title = frm.faq_title.value;
	
	if(faq_title == ""){
		
		alert("제목을 작성해주세요.");
		return;
		
	}
	
	if(!faqTitle_Reg.test(faq_title)){
		
		alert("제목은 한글로 2글자 이상 50자 이하로 작성해주세요");
		return;
		
	}
	/* 제목 유효성 검사 끝 */
	/* 내용 유효성 검사 시작 */
	const faqContent_Reg = /^[가-힣\s~!@#$%^&*()-_`=+?><;:]{2,200}$/;
	const faq_content = frm.faq_contents.value;
	
	if(faq_content == ""){
		
		alert("내용을 작성해주세요.");
		return;
		
	}
	
	if(!faqContent_Reg.test(faq_content)){
		
		alert("내용은 한글로 2글자 이상 200자 이하로 작성해주세요");
		return;
		
	}
	/* 내용 유효성 검사 끝 */
	
	frm.action = "<%=ctxPath%>/faq/faqWrite.ddg"
	frm.method = "post";
	frm.faq_title.value = faq_title;
	frm.faq_contents.value = faq_content;
	
	
	frm.submit();
	
}

function onReset(){
	
	const frm = document.faqWriteFrm;
	frm.faq_title.value = "";
	frm.faq_contents.value = "";
	
	
}

</script>


<div class="container-fluid">

	<div>
		<div class="text-center" style="margin-top: 4%; font-size:40pt">Community</div>
	<div class="font-weight-lighter text-center my-3" style="font-size:13pt">여러분의 이야기를 들려주세요</div>
	</div>
	<div style="font-size:14pt; font-weight:500;">
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
		    <a class="nav-link" href="<%= ctxPath%>/faq/faqList.ddg" style="color: black; border-bottom: solid black 2px;" tabindex="-1" aria-disabled="true">자주하는 질문</a>
		  </li>
		</ul>
		
	</div>




<hr style="width:63%; margin-left:15.5%;">


	<div id="product" class="d-flex justify-content-center mt-3"> 
	
		<div class="" style="display:flex; width:58%; height:250px; background-color:#F7F7F7">

			
			<%-- 그냥 볼때 --%>
				<div class="ml-4 mt-4 d-flex" style="width:15%; height:80%;">		
					<div class="d-flex justify-content-center" id="productSelectEnd">					
							<img src="<%= ctxPath%>/images/review/singsingbw.png" style="width:202px; height:202px;"/>					
					</div>				
				</div>
			<%-- 상품 이름 --%>
			<div class="" style="width:50%; height:80%; margin-top:3%;">
				<div id="productSelectEnd2" style="margin-left:8%; margin-top:7%;" >
					<span style="font-size:17pt;"></span>			
				</div>
			
			</div>
				

			

			
		</div>
	</div>
	
<hr style="width:63%; margin-left:15.5%;">


<%-- 제목 --%>
<form name="faqWriteFrm">
	<div id="title">	
		<div class="d-flex mt-5">
			<span class="mr-5" style="margin-left:20%; font-size:17pt;">제목</span>
			<textarea name="faq_title" cols="130" rows="1" style="resize:none;"></textarea>
		</div>	
	</div>

<hr class="mt-5" style="width:63%; margin-left:15.5%;">

<%-- 내용 --%>

	<div id="contents">
		<div class="d-flex mt-5">
			<span class="mr-5" style="margin-left:20%; font-size:17pt;">내용</span>
			<textarea name="faq_contents" cols="130" rows="20" style="resize:none;"></textarea>
		</div>	
	</div>
</form>
	<div id="buttons">
		<div class="d-flex" style="margin-left:25%;">
			<button type="button" class="btn btn-outline-dark mt-3" style="width:120px;" onclick="location.href='/fruitshop/faq/faqList.ddg'">목록</button>	
				
			<div class="ml-auto d-flex align-items-center" style="margin-right:21%; width:30%;" >
				<button type="submit" class="btn btn-outline-dark mt-3" style="width:120px;" onclick="faqRegister()">등록</button>
				<button type="reset" class="btn btn-outline-dark ml-5 mt-3" style="width:120px;" onclick="onReset()">취소</button>
			</div>					
		</div>	
	</div>
	
	
	






<%-- 경고문구 --%>
	<div class="d-flex justify-content-center mt-3 text-muted" style="width: 53%; margin-left:24.8%;">
		<span>상품과 관련없는 내용 또는 이미지, 욕설/비방, 개인정보유출, 광고/홍보글 등 적절하지 않은 게시물은 별도의 고지없이 비공개 처리 될 수 있습니다.
작성된 게시물은 운영 및 마케팅에  활용될 수 있습니다.</span>
	</div>


</div>






<jsp:include page="../common/footer.jsp"></jsp:include>