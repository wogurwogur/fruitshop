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

// 상품 상세보기
function productDetail(prod_no){
	
	const frm = document.readProductFrm;

	
	frm.prodNo.value = prod_no;
	frm.method = "get";
	frm.action = "<%=ctxPath%>/product/productDetail.ddg";
	
	frm.submit();
	
	
}


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



//댓글 쓰기
function commentWrite(reviewNo) {
	

	
    const commentContents = document.querySelector('textarea[name="comment_Contents"]').value;
    const commentPwd = document.querySelector('input[name="commentPwd"]').value;

    
    $.ajax({
        url: "<%= ctxPath%>/review/reviewComment.ddg",
        type: "post",
        data: {
            "review_no": reviewNo,
            "comment_Contents": commentContents,
            "commentPwd": commentPwd,
        },
        dataType: "json",
        success: function (json) {
            if (json.result == 1) {
                alert("댓글 작성 성공!");
                location.href="<%= ctxPath%>/review/reviewRead.ddg?review_no="+reviewNo ;
            } else {
                alert("댓글 작성 실패. 다시 시도해주세요.");
            }
        },
        error: function (request, status, error) {
            alert("서버와 통신 중 오류가 발생했습니다.");
            
        }
    });
}


// 댓글 삭제하기
function commentDelete(comment_no, review_no) {
	
	const commentPwdED = document.querySelectorAll('input[name="commentPwdED"]').value;
	
    $.ajax({
        url: "<%= ctxPath%>/review/reviewCommentDelete.ddg",
        type: "post",
        data: {
        	"review_no":review_no,
            "comment_no": comment_no,
            "commentPwdED": comment_PwdED,
        },
        dataType: "json",
        success: function (json) {
            if (json.result == 1) {
                alert("댓글 삭제 성공!");
                location.href="<%= ctxPath%>/review/reviewRead.ddg?review_no="+reviewNo ;
            } else {
                alert("댓글 삭제 실패 !! 다시 시도해주세요.");
            }
        },
        error: function (request, status, error) {
            alert("서버와 통신 중 오류가 발생했습니다.");
            
        }
    });
	
	
}


// 구매후기 글 수정하기 버튼 누르면
function reviewEdit(prod_no) {
	
	console.log(prod_no);
	
	const frm = document.readProductFrm;
	
	frm.prodNo.value = prod_no;
	frm.method = "get";
	frm.action = "<%=ctxPath%>/review/reviewEdit.ddg";
	
	frm.submit();

	
}


// 구매후기 글 삭제하기
function reviewDelete() {
	
const frm = document.commentWriteFrm;
	
	frm.action = "<%=ctxPath%>/review/reviewDelete.ddg";
	frm.method = "post";
	
	frm.review_no.value = "${requestScope.rvo.review_no}";
	
	console.log(frm.review_no.value);
	
	if(!confirm("정말 삭제하시겠습니까?")){
		return;
	}
	
	frm.submit();
	
	setTimeout(() => {
	    location.href = "<%= ctxPath %>/review/reviewList.ddg";
	}, 100);
	
	
}





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
		<form id="readProductForm" name="readProductFrm">
			<div id="productpage" class="justify-content-center" style="display:flex; height:10%; cursor:pointer;" onclick="productDetail('${rvo.prod_no}');" >			
				<div id = "productimg" class="mt-3 ml-5" style="display:inline-block; height:70px; width:70px">
					<div style="text-align:center"><span style="line-height: 150px"><img src="<%= ctxPath%>/images/product/thumnail/${rvo.prod_thumnail}" style="width:100%; height: 100%;"/></span></div>			
				</div>
				<div class="">
					<div class=" mt-4 ml-5" style="font-size:20pt">${rvo.prod_name}</div>
				</div>
				
									
			</div>
		
			<input type="text" name="prodNo" style="display: none"/>
		</form>
		
		
				
<hr style="width:63%; margin-left:15.5%;">	

	<div class="d-flex" style="width:78%;">
		<div id = "contentContainer1" style="margin-left:19.9%; width:100%;">
			<table style="width:100%;">			
					<tr style="">
						<td class="" style="border-top: none; font-size:17pt; width:12.5%;">제목</td>
						<td class="" style="border-top: none; width:75%; margin-bottom:2%;">${rvo.review_title }</td>
					</tr>
					<tr><td><hr></td><td><hr style="width:101%"></td></tr>			
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
									
								</tr>
							</div>
							<div class="d-flex">
								<tr>
									<td style="border-top: none; font-size:10pt;">작성자 : ${cml.cuserid }</td>
									<td style="width:25%; border-top: none; font-size:9pt; ">작성일자 : ${cml.comment_regidate }</td>
									
									<td colspan="2" style="border-top: none;" class="pl-3">
										<a onclick="commentEdit()"class=""><i class="fa-solid fa-pen" style="color:black"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;
										<a onclick="commentDelete('${cml.comment_no}','${cml.review_no} }')" class=""><i class="fa-solid fa-square-xmark" style="color:black"></i></a>
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
								<td style="border-top: none; font-size:10pt;">작성자 :&nbsp;&nbsp;${sessionScope.loginuser.userid}</td>
							</tr>										
						</div>
										
				</table>
			</div>
			
			<div id="writeContainer2">
				<div>
					<form name="commentWriteFrm" style="">
						<div style="width: 77%; margin-left:2%;">
							<textarea name="comment_Contents" cols="165" rows="4" style="margin-bottom:1.5%; margin-top:1.5%; width:100%; font-size:12pt; resize:none;"></textarea>				
						</div>
						<div class="d-flex float-right" style="margin-right:22%;">
							<div class="mt-2" style="width:70px; border-top: none; font-size:10pt;"><span>비밀번호</span>
							</div>
							<div class="mr-3" style="width:100px; border-top: none;">								
								<input id="commentPwd" name="commentPwd" type="password" style="width:100px"/>								
							</div>						
							<div class=""> 
								<button type="button" class="btn btn-outline-dark mb-1" style="width:70px; height:35px;" onclick="commentWrite('${rvo.review_no}');">작성</button>
							</div>
						</div>
					<input type="text" value="${sessionScope.loginuser.userid}" style="display:none;"/>
					<input type="text" name="review_no" style="display:none;"/>
					
					</form>
			
				</div>

			</div>

		</div>
		
		<div style ="margin-left:15.5%;">
			<tr>
				<td class=""><button class="btn btn-outline-dark" onclick="location.href='<%=ctxPath%>/review/reviewList.ddg'">목록</button></td>
				<td class=""><button class="btn btn-outline-dark" onclick="reviewEdit('${rvo.prod_no}')">본문 수정</button></td>
				<td class=""><button class="btn btn-outline-dark" onclick="reviewDelete()">본문 삭제</button></td>
			</tr>						
		</div>
			
		
</div>
							
	









<jsp:include page="../common/footer.jsp"></jsp:include>