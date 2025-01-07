<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    String ctxPath = request.getContextPath();
%>

<% 
    String currentShowPageNo = request.getParameter("currentShowPageNo");
%>   
 

<%-- Custom CSS --%>
<link rel="stylesheet" href="<%= request.getContextPath()%>/css/review/reviewRead.css">

<jsp:include page="../common/header.jsp"></jsp:include>

<script type="text/javascript">

$(document).ready(function () {
    $(document).on('click', '.editModalOpen', function () {
        const modalIndex = $(this).attr('data-index').trim(); // Get clicked button's unique index
        console.log(this)
        console.log("modalIndex : ", modalIndex);
        const modalId = `modalContainer_\${modalIndex}`; // Construct modal ID
        console.log("modalId : ", modalId);  // modalId 생성 시점 확인

        const modal = document.querySelector();
        console.log(modal);
        
        modal.classList.remove('hidden');
        
    });

    // Close modal when clicking close button
    $(document).on('click', '[id^="editModalClose_"]', function () {
        const modalIndex = this.id.split('_')[1];
        const modalId = `modalContainer_\${modalIndex}`;
        const modal = document.getElementById(modalId);

        if (modal) {
            modal.classList.add('hidden');
        } else {
            console.error(`Modal with ID ${modalId} not found.`);
        }
    });
});


/* $(document).ready(function(){
	// const editModalOpen = document.getElementById('editModalOpen');
	const editModalClose = document.getElementById('editModalClose');
	const modal = document.getElementById('modalContainer'); 

	
    $(document).on('click', '[id^="editModalOpen"]', function() {
        // 클릭한 버튼의 id를 가져와서 확인
        const modalId = $(this).attr('id'); // 'editModalOpen0', 'editModalOpen1' 등의 값
        
        modal.classList.remove('hidden');
        
        // 모달에서 수정 버튼을 클릭한 항목에 맞는 작업 수행
        // 예를 들어, 클릭한 id로 특정 내용을 채운다던지 할 수 있음
    });

	editModalClose.addEventListener('click', () => {
	  modal.classList.add('hidden');
	});	
		
}); */

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

// 댓글 수정하기
function commentEdit(button) {
	
	const review_no = button.getAttribute('data-review-no');
    const comment_no = button.getAttribute('data-comment-no');
	
	console.log('review_no:', review_no);
    console.log('comment_no:', comment_no);
	
	const comment_Edit_Contents = document.querySelector('textarea[name="comment_Edit_Contents"]').value;
    const comment_Edit_Pwd = document.querySelector('input[name="comment_Edit_Pwd"]').value;

    
    $.ajax({
        url: "<%= ctxPath%>/review/reviewCommentEdit.ddg",
        type: "post",
        data: {
        	"comment_no": comment_no,
            "review_no": review_no,
            "comment_Edit_Contents": comment_Edit_Contents,
            "comment_Edit_Pwd": comment_Edit_Pwd,	
        },
        dataType: "json",
        success: function (json) {
            if (json.result == 1) {
                alert("댓글 수정 성공!");
                location.href="<%= ctxPath%>/review/reviewRead.ddg?review_no="+review_no ;
            } else {
                alert("댓글 수정 실패. 다시 시도해주세요.");
            }
        },
        error: function (request, status, error) {
            alert("서버와 통신 중 오류가 발생했습니다.");
            
        }
    });
	
}


// 댓글 삭제하기
function commentDelete(comment_no, review_no) {
	
	const comment_PwdD = document.querySelectorAll('input[name="comment_PwdD"]').value;
	
    $.ajax({
        url: "<%= ctxPath%>/review/reviewCommentDelete.ddg",
        type: "post",
        data: {
        	"review_no":review_no,
            "comment_no": comment_no,
            "comment_PwdD": comment_PwdD,
        },
        dataType: "json",
        success: function (json) {
            if (json.result == 1) {
                alert("댓글 삭제 성공!");
                location.href="<%= ctxPath%>/review/reviewRead.ddg?review_no="+review_no ;
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
function reviewEdit(prod_no, review_no) {
	
	console.log(prod_no);
	console.log(review_no);
	
	const frm = document.readProductFrm;
	frm.review_no.value = review_no;
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

<style>
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

.hidden {
  display: none;
}


.modal-open {
  display: block !important;
}

</style>




<div class="container-fluid">

	<div>
	<h1 class="text-center" style="margin-top: 4%;">Community</h1>
	<div class="font-weight-lighter text-center my-3">우리함께 나누는 싱싱한 이야기</div>
	</div>
	<div class="mb-4"; style="font-size:14pt; font-weight:500;">
		<ul class="nav nav-pills navbar-light nav justify-content-center mt-4">
		  <li class="nav-item">
		    <a class="nav-link mr-5" href="<%= ctxPath%>/notice/noticeList.ddg" style="color: black;">공지사항</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link mx-5" href="<%= ctxPath%>/review/reviewList.ddg" style="color: black; border-bottom: solid black 2px;">구매후기</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link mx-5" href="<%= ctxPath%>/qna/qnaList.ddg" style="color: black;">QnA</a>
		  </li>
		  <li class="nav-item ml-5">	
		    <a class="nav-link" href="#" style="color: black;" tabindex="-1" aria-disabled="true">자주하는 질문</a>
		  </li>
		</ul>		
	</div>
	
	
	
	<%-- <c:if test="${not empty requestScope.rvo}">--%>
		<form id="readProductForm" name="readProductFrm">
			<div id="productpage" class="justify-content-center" style="display:flex; height:10%; cursor:pointer;" onclick="productDetail('${rvo.prod_no}');" >			
				<div id = "productimg" class="mt-1 ml-5" style="display:inline-block; height:120px; width:120px">
					<div style=""><span style="line-height: 170px"><img src="<%= ctxPath%>/images/product/thumnail/${rvo.prod_thumnail}" style="width:100%; height: 100%;"/></span></div>			
				</div>
				<div style="text-align:center; width:25%;">
					<div style="font-size:20pt; margin-left:10%; margin-top:8%; ">${rvo.prod_name}</div>
				</div>								
			</div>
			<input type="text" name="review_no" style="display: none"/>
			<input type="text" name="prodNo" style="display: none"/>
		</form>
		


	<div class="container mt-5">
			<div class="table-responsive">
			    <!-- .table-responsive 반응형 테이블(테이블의 원래 크기를 보존해주기 위한 것으로써, 디바이스의 width가 작아지면 테이블 하단에 스크롤이 생김) -->			 
				<table class="table">
					<thead style="text-align: center;">
						<tr>
							<th width="33%;"></th>
							<th style="text-align: center;" width="33%">
								${rvo.review_title}
							</th>
							<td style="text-align: right; border-bottom: solid #DEE2E6 2px;" width="33%">
								작성일&nbsp;:&nbsp;${rvo.review_regidate}
							</td>
						</tr>
					</thead>
					<tbody>
						<tr style="border: solid #DEE2E6 2px;">
							<td colspan="3" height="400">
								<div class="container">
									${rvo.review_contents}
								</div>
							</td>
						</tr>
					</tbody>
				
				</table>				
				<%-- <button type="button" class="btn btn-outline-secondary" onclick="location.href='<%=ctxPath%>/notice/noticeList.ddg'" style="float: right; ">돌아가기</button>
			<c:if test="${sessionScope.loginuser.role == 2}">
				<button type="button" class="btn btn-outline-danger" onclick="deleteNotice()" style="float: right; margin-right:0.8%">글삭제</button>
				<button type="button" class="btn btn-outline-success" style="float: right; margin-right:0.8%" id="NoticeModalUpdateOpen">글수정</button>
				</c:if>
				<form name="noticeDetailForm">
					<input type="hidden" name="notice_no">				
			</form>--%>
				
			<!-- 페이지네이션 -->
			 
		</div>
	</div>
	
<hr style="width:60%; margin-left:20%;">	
			



	<div class="d-flex" style="width: 133%;">
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
						<table class="table mt-2" style="margin-left:5%;">
							<div>
								<tr style="border-top: none;">
									<td style="width:50%; border-top: none; font-size:12pt">${cml.comment_contents}</td>
									<td style="border-top: none;"></td>
									<td style="border-top: none;"></td>
									
								</tr>
							</div>
							<div class="d-flex">
								<tr>
									<td style="border-top: none; font-size:10pt;">작성자 : ${cml.cuserid }</td>
									<td style="width:17%; border-top: none; font-size:9pt; ">작성일자 : ${cml.comment_regidate }</td>
									
									<td colspan="2" style="border-top: none;" class="pl-3">
										<a class="editModalOpen" data-index="${status.index}">
										<i class="fa-solid fa-pen"  style="color:black"></i>
										</a>&nbsp;&nbsp;&nbsp;&nbsp;
										<a onclick="commentDelete('${cml.comment_no}','${rvo.review_no}')" class=""><i class="fa-solid fa-square-xmark" style="color:black"></i></a>
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
		
<hr style="width:60%; margin-left:20%;">		
		
		<div class="d-flex" style="width:133%;">
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
							<textarea name="comment_Contents" cols="165" rows="4" style="margin-bottom:1.5%; margin-top:1.5%; width:95%; font-size:12pt; resize:none;"></textarea>				
						</div>
						<div class="d-flex float-right" style="margin-right:25%;">
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
		
		<div style ="margin-left:20%;">
			<tr>
				<td class=""><button class="btn btn-outline-dark" onclick="location.href='<%=ctxPath%>/review/reviewList.ddg?searchType=&sizePerPage=10&searchWord=&currentShowPageNo=<%= currentShowPageNo%> '">목록</button></td>
				<td class=""><button class="btn btn-outline-dark" onclick="reviewEdit('${rvo.prod_no}', '${rvo.review_no }')">후기 수정</button></td>
				<td class=""><button class="btn btn-outline-dark" onclick="reviewDelete()">후기 삭제</button></td>
			</tr>						
		</div>
			
		
</div>

<c:forEach var="cml" items="${requestScope.commentList}" varStatus="status">
	<div id="modalContainer_${status.index} data-index="${status.index}" class="hidden" >
		  <div id="modalContent">		  	
		   	<form>
		   		<div style="">
		   			<textarea name="comment_Edit_Contents" cols="30" rows="5" style=" margin-left:10%; margin-top:25%; width:80%; font-size:12pt; resize:none"></textarea>	   			   		
		   		</div>
		   			
		   		<div style="display:flex; height:40px; margin-left:10%; margin-top:2%;">
		   			<button class="btn btn-outline-dark" type="button" id="editModalClose_${status.index}">나가기</button>
		   			<span style="margin-left:22%; margin-top:3%; margin-right: 3%; font-size:10pt;">비밀번호</span>
			   		<input style=" width:80px; "type="password" name="comment_Edit_Pwd"/>
			   		<button class="btn btn-outline-dark ml-3" type="button" data-review-no="${rvo.review_no}" data-comment-no="${cml.comment_no}" onclick="commentEdit(this)">수정</button>   					   		   	
			   	</div>
		   	</form>		   
		  </div>
	</div>
</c:forEach>
							
	









<jsp:include page="../common/footer.jsp"></jsp:include>