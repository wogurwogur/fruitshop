<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String ctxPath = request.getContextPath();
%>   
 

<%-- Custom CSS --%>
<link rel="stylesheet" href="<%= request.getContextPath()%>/css/review/reviewRead.css">

<jsp:include page="../common/header.jsp"></jsp:include>

<div class="container-fluid">

	<div>
	<h1 class="text-center" style="margin-top: 4%;">Community</h1>
	<div class="font-weight-lighter text-center my-3">우리함께 나누는 싱싱한 이야기</div>
	</div>
	<div>
		<ul class="nav nav-pills navbar-light nav justify-content-center mt-4">
		  <li class="nav-item">
		    <a class="nav-link mr-5" href="#" style="color: black;">공지사항</a>
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
	
	<hr style="width:84.8%;">
	
	<c:if test="${not empty requestScope.rvo}">
			<div id="productpage" style="display:flex; height:25%; margin-left:5%; margin-right:5%;">
			
				<div id = "productimg" class="mt-4 ml-5" style="display:inline-block; height:180px; width:10%">
					<div style="text-align:center"><span style="line-height: 165px"><img src="<%= ctxPath%>/images/product/thumnail/${rvo.prod_thumnail}" style="width:170px; height: 170px;"/></span></div>			
				</div>
				<h4><div class=" mt-5 ml-5">상품명 : ${rvo.prod_name}</div><div class=" mt-5 ml-5">가격 : ${rvo.prod_price}원</div></h4>
				
				<div class="my-auto" style="margin-left:39%; line-height: 100px;">
					<button type="button" class="btn btn-outline-dark" style="height:30%;">상품 상세페이지 보기</button>
				</div>
				
			</div>	
			<hr style="width:84.8%;">
			
			<div class="" style="display:flex;">
				
				<div class= "" style="width:15%; margin-left:7.5%;">
					<div class= "justify-contents-end" style="margin:10% auto 20% 55%"><h4>제목</h4></div>
					
					<div class= "justify-contents-end" style="margin-left:55%; margin-bottom:20%;"><h4>작성자</h4></div>
					<div class= "justify-contents-end" style="margin-left:55%"><h4>내용</h4></div>
				</div>
				
				<div class= "" style="width:70%;">
					<div class="ml-5 " style="margin:2.2%">${rvo.review_title}</div>
					<div class="ml-5 " style="margin:4.2%">${rvo.userid}</div>
					<div class="ml-5 " style="margin:4.2%; margin-right: 15%;">${rvo.review_contents}
					</div>
				</div>
			
			</div>
	</c:if>
	
	
	<hr style="width:84.8%;">
<div class="" style="display:flex;">
				
		<div class= "" style="width:15%; margin-left:7.5%;">
		
			<div class= "justify-contents-end " style="margin:10% auto 20% 55%"><h5>댓글</h5></div>
				
			<div class= "justify-contents-end " style="margin-left:55%; margin-top:45%;"><h5 class="mt-5">댓글쓰기</h5></div>
			<div class= "justify-contents-end " style="margin-left:55%; font-size:10pt;">작성자 : leess</div>
		</div>
		
		
	 	
		<div class= "" style="width:70%;">
		
			<div class="ml-5 " style="margin:2.2%; font-size:12pt; width:82%;">머하누으하하하하하하하</div>
				
				<div style="display:flex;">
				
					<div class="ml-5 " style="font-size:10pt; width:15%;">작성자 : </div>
					
					<div class="ml-2 " style="font-size:10pt; width:15%;">2024/12/12</div>
					
					
					<div class="" style="font-size:10pt; width:15%; margin-left:25.4%; display:flex;">
						<div style="width:50%">
							비밀번호
						</div>
						
						<form style="width:100%">
								<input type="number" style="width:80%; height:90%;"/>
						</form>
					</div>
					<i class="fa-solid fa-pen"></i>&nbsp;&nbsp;&nbsp;&nbsp;
					<i class="fa-solid fa-square-xmark"></i>
					
					
				</div>
				<hr style="width:100%;">
			<div style="margin-left:3.5%;">
			<form style="height:30%">
				<textarea name="contents" cols="50" rows="4" style="margin-bottom:5%; margin-top:2%; width:78.2%; height:100%; font-size:12pt; resize:none;"></textarea>
			</form>
			<div>
				<div style="margin-left:72.7%; margin-top:3%;" ><input class ="border bg-light "type="submit" style="width:20%" value="작성"/></div>
			</div>
		</div>
		
	</div>					
</div>
	
	
	
	
	
	
	
	<%-- 보류//////////////////////////
	<div class="border"; style="width:85%; height:100px; margin:auto"><h5 style="margin-left:5%; margin-top:3%">제목 </h5>
	
	</div>
	<div class="border"; style="width:85%; margin:auto"><h5 style="margin-left:3.65%; margin-top:1%">작성자 </h5></div>
	<div class="border"; style="width:85%; margin:auto"><h5 style="margin-left:5%; margin-top:3%">내용</h5>
		<div class="border" id="contents"></div>
	</div>
	--%>
	
	
	
	
	
	
	
	
	
	
	
</div>


















<jsp:include page="../common/footer.jsp"></jsp:include>