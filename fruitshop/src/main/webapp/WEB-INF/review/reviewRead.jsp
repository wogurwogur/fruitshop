<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String ctxPath = request.getContextPath();
%>   
 

<%-- Custom CSS --%>
<link rel="stylesheet" href="<%= request.getContextPath()%>/css/review/reviewRead.css">

<jsp:include page="../common/header.jsp"></jsp:include>

<script type="text/javascript">


$(document).ready(function(){
	
	
	
	
	
	
	
});


function commentWrite(){
	
	/*
	const cmtWt = $("textarea#comment_Contents").val().trim();

    if(comtWt == ""){
        alert("댓글을 입력하세요")
        return; // goRegister() 함수를 종료한다.
    }
    else{
    
	const frm = document.commentWriteFrm;
    frm.action = "reviewRead.ddg";
    frm.method = "post";
    frm.submit();
    
    } 이전의 잔재   */
    
    
	$.ajax({
        <%-- url : "<%= ctxPath%>/shop/admin/productRegister.up", --%>
             url : "${pageContext.request.contextPath}/review/reviewComment.ddg",
             type : "post",
             data : formData,
             // processData:false,  // 파일 전송시 설정 
             // contentType:false,  // 파일 전송시 설정
             dataType:"json",
             success:function(json){
         	   	  console.log("~~~ 확인용 : " + JSON.stringify(json));
                
         	   	  if(json.result == 1) {
         	         location.href="${pageContext.request.contextPath}/review/reviewRead.ddg"; 
                  }
                 
             },
             error: {

		     }
             
         });
    
    
	
	
	
} // end of function commentWrite(){ 





</script>


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
	
	<%-- <c:if test="${not empty requestScope.rvo}">--%>
			<div id="productpage" style="display:flex; height:25%; margin-left:13%; margin-right:5%;">
			
				<div id = "productimg" class="mt-4 ml-5" style="display:inline-block; height:180px; width:10%">
					<div style="text-align:center"><span style="line-height: 165px"><img src="<%= ctxPath%>/images/product/thumnail/${rvo.prod_thumnail}" style="width:100%; height: 100%;"/></span></div>			
				</div>
				<div>
					<div class=" mt-5 ml-5" style="font-size:20pt">상품명 : ${rvo.prod_name}</div>
					<div class=" mt-5 ml-5" style="font-size:20pt">가격 : ${rvo.prod_price}원</div>
				</div>
				<div class="my-auto ml-5" style="line-height: 100px;">
					<button type="button" class="btn btn-outline-dark" style="width:200px; height:50px;">상품 상세페이지 보기</button>
				</div>
				
				
			</div>
				
<hr style="width:63%; margin-left:15.5%;">	

	<div class="d-flex" style="width:78%;">
		<div id = "contentContainer1" style="margin-left:19.9%; width:100%;">
			<table style="width:100%;">			
					<tr style="">
						<td class="" style="border-top: none; font-size:17pt; width:12.5%;">제목</td>
						<td class="" style="border-top: none; width:75%; margin-bottom:2%;">${rvo.review_title }</td>
					</tr>
					<tr><td><hr></td><td><hr></td></tr>			
					<tr>
						<td class="" style=" font-size:17pt; width:12.5%;">내용</td>
						<td class="" style=" width:75%;">${rvo.review_contents}</td>
					</tr>										
			</table>
		</div>	
	</div>
	
<hr style="width:63%; margin-left:15.5%;">	
			



	<div class="d-flex">
			<div id = "commentContainer1" style="margin-left:15%;">	
			<table class="table">
					<div>
						<tr>
							<td style="border-top: none; font-size:17pt; text-align:center;">댓글</td>
						</tr>										
					</div>								
			</table>
		
			</div>
			<c:if test="${not empty requestScope.commentList }">
				<div id="commentContainer2" style="width:55%;">
					<c:forEach var="cml" items="${requestScope.commentList}" varStatus="status">
						<table class="table mt-2" style="margin-left:10%;">
							<div>
								<tr style="border-top: none;">
									<td style="width:65%; border-top: none; font-size:12pt">${cml.comment_contents}</td>
									<td style="border-top: none;"></td>
									<td style="border-top: none;"></td>
									<td colspan="2" style="border-top: none;" class="pl-3">
										<a onclick="commentEdit()"class=""><i class="fa-solid fa-pen"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;
										<a onclick="commentDelete()" class=""><i class="fa-solid fa-square-xmark"></i></a>
									</td>
								</tr>
							</div>
							<div class="d-flex">
								<tr>
									<td style="border-top: none; font-size:10pt;">작성자 : ${cml.cuserid }</td>
									<td style="width:25%; border-top: none; font-size:9pt; ">작성일자 : ${cml.comment_regidate }</td>
									<td class="pl-4" style="width:20%; border-top: none; font-size:10pt;">비밀번호</td>
									<td class="pl-0" style="width:15%; border-top: none;">
										<form style="">
											<input type="password" style="width:100px"/>
										</form>
									</td>							
																				
								</tr>
							</div>	
					</c:forEach>
				</div>
			</c:if>
					
			<c:if test="${empty requestScope.commentList }">
			<table class="mb-3" style="margin-left:6%;">
		      	<tr>
		        	<td colspan="4" style="font-size:12pt">아직 댓글이 없습니다.</td>
		        </tr>
		    </table>
	      	</c:if>	
      					
		</table>
				
				
				
					
			
			</div>
		</div>
		
<hr style="width:62.5%; margin-left:15.5%;">		
		
		<div class="d-flex">
			<div id = "writeContainer2" style="margin-left:15.2%;">	
				<table class="table">
						<div>
							<tr>
								<td style="border-top: none; font-size:17pt;">댓글 쓰기</td>
							</tr>
							<tr>
								<td style="border-top: none; font-size:10pt;">작성자 : 로그인ID</td>
							</tr>										
						</div>
														
				</table>
			</div>
			
			<div id="writeContainer2">
				<div>
					<form name="commentWriteFrm" style="">
						<div style="width: 76%; margin-left:4.5%;">
							<textarea name="comment_Contents" cols="165" rows="4" style="margin-bottom:1.5%; margin-top:1.5%; width:100%; font-size:12pt; resize:none;"></textarea>				
						</div>
						<div class=""> 
							<button type="submit" class="btn btn-outline-dark float-right" style="margin-right:19.5%;" onclick="commentWrite();">작성</button>
						</div>
					</form>
				
				
				
				</div>
			
			
			</div>
		
		
		</div>
		
</div>
							
	









<jsp:include page="../common/footer.jsp"></jsp:include>