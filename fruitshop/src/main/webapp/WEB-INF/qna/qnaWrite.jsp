<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    String ctxPath = request.getContextPath();
%> 

<% 
    String prod_no = request.getParameter("prod_no");
%> 
<jsp:include page="../common/header.jsp"></jsp:include>

<script type="text/javascript">


$(document).ready(function(){
	
	// 다른데서 넘어온지 확인(모달 버튼 유무)
	if(<%= prod_no%> == null){
	
	const writeModalOpen = document.getElementById('writeModalOpen');
	const writeModalClose = document.getElementById('writeModalClose');
	const modal = document.getElementById('modalContainer');


	writeModalOpen.addEventListener('click', () => {
	  modal.classList.remove('hidden');
	});

	writeModalClose.addEventListener('click', () => {
	  modal.classList.add('hidden');
	});	
	
	}
	else{
		
		$('input[name="prodNo"]').val(<%= prod_no%>);
		console.log('prodNo의 값:', $('input[name="prodNo"]').val());
		
	}
		
});

function productSelect(prod_no){
	
	const modal = document.getElementById('modalContainer');
	// alert(prod_no)
	
	$.ajax({
		
		
		
		url: "<%= ctxPath%>/community/productSelect.ddg",
		type:"get",		
		data: {
			
			"prod_no":prod_no
		},
		dataType:"json",
		success:function(json){
			
			modal.classList.add('hidden');
			
			let p1_html = ``;
			let p2_html = ``;
			let p3_html = ``;
			
			const prod_price = (json.prod_price).toLocaleString('ko-KR');
			
			p1_html = "<img src = <%= ctxPath%>/images/product/thumnail/" + json.prod_thumnail + " width='200' height='200'/>";
			
			$("div#productSelectEnd").html(p1_html);
						
			p2_html = "<span style='font-size:17pt;'>"+ json.prod_name +"</span>";
			
			$("div#productSelectEnd2").html(p2_html);
			
			p3_html = "<span style='font-size:17pt;'>"+ prod_price +"원</span>";
			
			$("div#productSelectEnd3").html(p3_html);
			
			
			
			const frm = document.qnaWriteFrm;
			
			frm.prodNo.value = json.prod_no;
			 

			
			
				
		},
		error: function (request, status, error) {
            alert("서버와 통신 중 오류가 발생했습니다.");
            
        }
	
	
	});
	
	
};


function qaRegister(){
	
	const frm = document.qnaWriteFrm;
	
	    frm.action = "<%=ctxPath%>/qna/qnaWrite.ddg";
	    frm.method = "post";
	    
	    alert("QnA 작성 완료 !!")
	  	    
	    frm.submit();
	    
	    setTimeout(() => {
	    location.href="<%= ctxPath%>/qna/qnaList.ddg"
	    }, 300);
};


function getUrlParameter(name) {
    var url = window.location.href; // 현재 페이지 URL
    var regex = new RegExp('[?&]' + name + '=([^&#]*)', 'i'); // 파라미터 추출을 위한 정규식
    var result = regex.exec(url); // 정규식 실행
    return result ? decodeURIComponent(result[1]) : null; // 파라미터 값 반환   
    
}
	var prod_no = getUrlParameter('prod_no');

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
		<div class="text-center" style="margin-top: 4%; font-size:40pt">Community</div>
	<div class="font-weight-lighter text-center my-3" style="font-size:13pt">여러분의 이야기를 들려주세요</div>
	</div>
	<div style="">
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
		    <a class="nav-link" href="<%= ctxPath%>/faq/faqList.ddg" style="color: black;" tabindex="-1" aria-disabled="true">자주하는 질문</a>
		  </li>
		</ul>
		
	</div>
	
<hr style="width:60%; margin-left:17.1%;">


	<div id="product" class="d-flex justify-content-center mt-3"> 
	
		<div class="" style="display:flex; width:60%; height:250px; margin-right:6%; background-color:#F7F7F7">
			
			<%-- 타페이지에서 값 받아올때 --%>		
			<%-- 상품 썸네일 --%>		
			<c:if test="${not empty qpcarrier.prod_no}">
				<div class="border ml-4 mt-4 d-flex" style="width:15%; height:80%;">		
					<div class="d-flex justify-content-center" id="productSelectEnd">					
							<img src="<%= ctxPath%>/images/product/thumnail/${(requestScope.qpcarrier).prod_thumnail}" width="200" height="200"/>					
					</div>				
				</div>
				<%-- 상품 이름 --%>
				<div class="" style="width:50%; height:80%; margin-top:3%;">
					<div id="productSelectEnd2" style="margin-left:8%; margin-top:7%;" >
						<span style="font-size:17pt;">${(requestScope.qpcarrier).prod_name}</span>			
					</div>
				
				<%-- 상품 가격 --%>
					<div id="productSelectEnd3" style="margin-left:8%; margin-top:5%;">
						<span style="font-size:17pt;"><fmt:formatNumber value="${(requestScope.qpcarrier).prod_price}" pattern="#,###"/>원</span>
					</div>
				</div>						
			</c:if>
			
			
			<%-- 그냥 볼때 --%>
			<c:if test="${empty qpcarrier.prod_no}">
			<%-- 상품 썸네일 --%>
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
			
				<%-- 상품 가격 --%>
				<div id="productSelectEnd3" style="margin-left:8%; margin-top:5%;">
					<span style="font-size:17pt;"></span>
				</div>
			</div>					
			<%-- 상품정보선택 버튼 , 상품 상세페이지 버튼 --%>			
			<div class="ml-auto d-flex align-items-center" style="margin-right:10%;">
				<a style="cursor: pointer;" data-toggle="modal" data-target="#productFind" data-dismiss="modal"><button type="button" class="btn btn-outline-dark" style="width:200px; height:50px;" id="writeModalOpen">상품 등록하기</button></a>									
			</div>
			</c:if>	
		</div>
	</div>
	
	<div id="modalContainer" class="hidden" >
	  <div id="modalContent">
	  	<div style="text-align:center; margin-top:2%;"><h5>내가 구매한 상품</h5></div>
	    <div class="container" style="overflow: auto; width:440px; height:380px; margin: auto;">	    
	    	<table class="table" style="text-align:center;">
	    		<thead>
	    			<tr>
						<th style="width:15%;">이미지</th>
						<th style="width:25%;">과일명</th>
						<th style="width:10%;">판매가</th>	
					</tr>
	    		</thead>
	    		<tbody style="text-align:center;">
	    			<c:forEach var="oqpl" items="${requestScope.oqproductList}">
		    			<tr onclick="productSelect('${oqpl.prod_no}')">
							<td><img src="<%= ctxPath%>/images/product/thumnail/${oqpl.prod_thumnail}" width="50" height="50"/></td>
							<td>${oqpl.prod_name}</td>	
							<td style=""><fmt:formatNumber pattern="###,###">${oqpl.prod_price}</fmt:formatNumber>원</td>
						</tr>
					</c:forEach>	
					<tr>
	    				<td style="border-top:none"><button class="btn btn-outline-dark" type="button" id="writeModalClose">나가기</button></td>
	    			</tr>
	    		</tbody>
	    	</table>
	    </div>
	  </div>
	</div> 
	
<hr style="width:60%; margin-left:17.1%;">


<%-- 제목 --%>
<form name="qnaWriteFrm">
	<div id="title">	
		<div class="d-flex mt-5">
			<span class="mr-5" style="margin-left:20%; font-size:17pt;">제목</span>
				<textarea name="qna_title" cols="80" rows="1" style="resize:none;"></textarea>
			<span class="ml-5 mt-1" style="font-weight:bold; font-size:12pt;">작성자 : ${sessionScope.loginuser.userid}</span>
		</div>	
	</div>

<hr class="mt-5" style="width:60%; margin-left:17.1%;">

<%-- 내용 --%>

	<div id="contents">
		<div class="d-flex mt-5">
			<span class="mr-5" style="margin-left:20%; font-size:17pt;">내용</span>
			<textarea name="qna_contents" cols="130" rows="20" style="resize:none;"></textarea>
		</div>	
	</div>
	<input type="hidden" name="prodNo"/>
	<%-- <input type="hidden" name=""/>--%>
</form>
	<div id="buttons">
		<div class="d-flex" style="margin-left:25%;">
			<button type="button" class="btn btn-outline-dark mt-3" style="width:120px;" onclick="location.href='<%=ctxPath%>/review/reviewList.ddg'">목록</button>	
				
			<div class="ml-auto d-flex align-items-center" style="margin-right:21%; width:30%;" >
				<button type="submit" class="btn btn-outline-dark mt-3" style="width:120px;" onclick="qaRegister()">등록</button>
				<button type="reset" class="btn btn-outline-dark ml-5 mt-3" style="width:120px;">취소</button>
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