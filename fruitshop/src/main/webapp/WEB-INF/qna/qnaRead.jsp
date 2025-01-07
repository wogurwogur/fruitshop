<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    String ctxPath = request.getContextPath();
%>  

<%-- Custom CSS --%>
<link rel="stylesheet" href="<%= request.getContextPath()%>/css/qna/qnaRead.css">

<jsp:include page="../common/header.jsp"></jsp:include>


<script type="text/javascript">

//상품 상세보기
function productDetail(prod_no){
	
	const frm = document.readProductFrm;

	
	frm.prodNo.value = prod_no;
	frm.method = "get";
	frm.action = "<%=ctxPath%>/product/productDetail.ddg";
	
	frm.submit();
	
	
}


//QnA 글 삭제하기
function qnaDelete() {
	
const frm = document.readProductFrm;
	
	frm.action = "<%=ctxPath%>/qna/qnaDelete.ddg";
	frm.method = "post";
	
	frm.qna_no.value = "${requestScope.qvo.qna_no}";
		
	if(!confirm("정말 삭제하시겠습니까?")){
		return;
	}
	
	frm.submit();
	
	setTimeout(() => {
	    location.href = "<%= ctxPath %>/qna/qnaList.ddg";
	}, 100);

	
	
}


//QnA 수정하기 버튼 누르면
function qnaEdit(prod_no, qna_no) {
	
	
	
	console.log(prod_no);
	console.log(qna_no);
	
	const frm = document.readProductFrm;
	
	frm.qna_no.value = qna_no;
	frm.prodNo.value = prod_no;
	frm.method = "get";
	frm.action = "<%=ctxPath%>/qna/qnaEdit.ddg";
	
	frm.submit();

	
}

// admin 답글달기 누르면
function qnaReply(prod_no, qna_no) {
	
	console.log(qna_no);
	console.log(prod_no);
	
	const frm = document.readProductFrm;
	
	frm.qnaNo.value = qna_no;
	frm.prodNo.value = prod_no;
	frm.method = "get";
	frm.action = "<%=ctxPath%>/qna/qnaReply.ddg";
	
	frm.submit();
	
}


</script>

<div class="container-fluid">
	
	<div>
		<div class="text-center" style="margin-top: 4%; font-size:30pt">Community</div>
	<div class="font-weight-lighter text-center my-3" style="font-size:12pt">우리함께 나누는 싱싱한 이야기</div>
	</div>
	<div class="mb-4"; style="font-size:14pt; font-weight:500;">
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
		    <a class="nav-link" href="#" style="color: black;" tabindex="-1" aria-disabled="true">자주하는 질문</a>
		  </li>
		</ul>
		
	</div>
	
	
	<form id="readProductForm" name="readProductFrm">
			<div id="productpage" class="justify-content-center" style="display:flex; height:10%; cursor:pointer;" onclick="productDetail('${qvo.prod_no}');" >			
				<div id = "productimg" class="mt-1 ml-5" style="display:inline-block; height:120px; width:120px">
					<div style=""><span style="line-height: 170px"><img src="<%= ctxPath%>/images/product/thumnail/${qvo.prod_thumnail}" style="width:100%; height: 100%;"/></span></div>			
				</div>
				<div style="text-align:center; width:25%;">
					<div style="font-size:20pt; margin-left:10%; margin-top:8%; ">${qvo.prod_name}</div>
				</div>								
			</div>		

			<input type="text" name="qna_no" style="display: none"/>		
			
	</form>
		
		
		
		<div class="container mt-5">
			<div class="table-responsive">
			    <!-- .table-responsive 반응형 테이블(테이블의 원래 크기를 보존해주기 위한 것으로써, 디바이스의 width가 작아지면 테이블 하단에 스크롤이 생김) -->			 
				<table class="table">
					<thead style="text-align: center;">
						<tr>
							<th width="33%;"></th>
							<th style="text-align: center;" width="33%">
								${qvo.qna_title}
							</th>
							<td style="text-align: right; border-bottom: solid #DEE2E6 2px;" width="33%">
								작성일&nbsp;:&nbsp;${qvo.qna_regidate}
							</td>
						</tr>
					</thead>
					<tbody>
						<tr style="border: solid #DEE2E6 2px;">
							<td colspan="3" height="400">
								<div class="container">
									${qvo.qna_contents}
								</div>
							</td>
						</tr>
					</tbody>
			</table>	
		
		</div>
	</div>
		<div style ="margin-left:20.3%;">
			<tr>
				<td class=""><button class="btn btn-outline-dark" onclick="location.href='<%=ctxPath%>/qna/qnaList.ddg'">목록</button></td>
				<td class=""><button class="btn btn-outline-dark" onclick="qnaEdit('${qvo.prod_no}', '${qvo.qna_no}')">QnA 수정</button></td>
				<td class=""><button class="btn btn-outline-dark" onclick="qnaDelete()">QnA 삭제</button></td>
			<c:if test="${sessionScope.loginuser.role == 2}">

				<td><button type="button" class="btn btn-outline-dark delShow" onclick="qnaReply('${qvo.prod_no}', '${qvo.qna_no }')">답글달기</button></td>						
			</c:if>
			</tr>						
		</div>
		
		
	<c:if test="${qvo.qna_answer != null }">
		<div class="container mt-5">
			<div class="table-responsive">
			    <!-- .table-responsive 반응형 테이블(테이블의 원래 크기를 보존해주기 위한 것으로써, 디바이스의 width가 작아지면 테이블 하단에 스크롤이 생김) -->			 
				<table class="table">
					<thead style="text-align: center;">
						<tr>
							<th width="33%;"></th>
							<th style="text-align: center;" width="33%">
								RE : ${qvo.qna_title}
							</th>
							<td style="text-align: right; border-bottom: solid #DEE2E6 2px;" width="33%">

							</td>
						</tr>
					</thead>
					<tbody>
						<tr style="border: solid #DEE2E6 2px;">
							<td colspan="3" height="400">
								<div class="container">
									${qvo.qna_answer}
								</div>
							</td>
						</tr>
					</tbody>
			</table>
		</div>		
	</div>
	</c:if>






<jsp:include page="../common/footer.jsp"></jsp:include>