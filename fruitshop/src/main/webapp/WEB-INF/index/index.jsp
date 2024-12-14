<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<jsp:include page="../common/header.jsp"></jsp:include>
<link rel="stylesheet" href="<%= request.getContextPath()%>/css/index/index.css">


<script type="text/javascript">
	/*
	찜목록 눌렀을 시 상품을 찜목록에 추가, 버튼모양 토글 필요
	*/
	
	function wishToggle(e) {
		// alert(ctxPath); 상품 객체 받아야 할 듯?
		//console.log("찜번호 몇번째? ", $("i.heart").index());
		//console.log("찜번호 몇번째? ", $(e.target));
//		alert($("i.heart"));
		e.classList.toggle("fa-solid");
	}
</script>

<div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
	
	  <ol class="carousel-indicators">
	    <c:if test="${!empty requestScope.imgList}">
	    	<c:forEach items="${requestScope.imgList}" varStatus="status">
	    		<c:if test="${status.index == 0}">
	    			<li data-target="#carouselExampleIndicators" data-slide-to="${status.index}" class="active"></li>
	    		</c:if>
	    		
	    		<c:if test="${status.index > 0}">
	    			<li data-target="#carouselExampleIndicators" data-slide-to="${status.index}"></li>
	    		</c:if>
	    	</c:forEach>
	    </c:if>
	  </ol>
	  
	  <div class="carousel-inner">
	     <c:if test="${!empty requestScope.imgList}">
	     	<c:forEach var="imgvo" items="${requestScope.imgList}" varStatus="status">
	     		<c:if test="${status.index == 0}">
	     			<div class="carousel-item active">
	     				<a href="#" class="product"><img src="<%= request.getContextPath() %>/images/index/${imgvo.imgfilename}" class="d-block w-100" alt="..."></a>
	     				<div class="carousel-caption d-none d-md-block">
	     					<%-- <h5>${imgvo.imgname}</h5> --%>
	     				</div>
	     			</div>
	     		</c:if>
	     		
	     		<c:if test="${status.index > 0}">
	    			<div class="carousel-item">
	     				<a href="#" class="product"><img src="<%= request.getContextPath() %>/images/index/${imgvo.imgfilename}" class="d-block w-100" alt="..."></a>
	     				<div class="carousel-caption d-none d-md-block">
	     					<%-- <h5>${imgvo.imgname}</h5> --%>
	     				</div>
	     			</div>
	    		</c:if>
	     	</c:forEach>
	     </c:if>
	  </div>
	  
	  <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
	    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
	    <span class="sr-only">Previous</span>
	  </a>
	  <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
	    <span class="carousel-control-next-icon" aria-hidden="true"></span>
	    <span class="sr-only">Next</span>
	  </a>
</div>


<%-- 계절테마 메뉴 시작 --%>
<div style="width: 70%; margin: 0 auto;" class="my-5">
    <%-- <p style="margin-left: 14.5%;" class="h2 mb-5">계절테마</p>--%>
    <div id="categoty_season" class="h2 mb-5">계절 과일</div>
    <div style="display: flex;">
    	<%-- 반복문 돌려야 함 --%>
        <div class="mx-auto">
        	<a href="<%= request.getContextPath() %>/product/springProduct.ddg" class="product">
	            <img src="<%= request.getContextPath() %>/images/index/tomato.png" style="border-radius: 50%;">
	            <span style="display: block;" class="text-center h4 mt-3 season">봄</span>
            </a>
        </div>
        <div class="mx-auto">
	        <a href="<%= request.getContextPath() %>/product/summerProduct.ddg" class="product">
	            <img src="<%= request.getContextPath() %>/images/index/banana.png" style="border-radius: 50%;">
	            <span style="display: block;" class="text-center h4 mt-3 season">여름</span>
            </a>
        </div>
        <div class="mx-auto">
        	<a href="<%= request.getContextPath() %>/product/autumnProduct.ddg" class="product">
	            <img src="<%= request.getContextPath() %>/images/index/melon.png" style="border-radius: 50%;">
	            <span style="display: block;" class="text-center h4 mt-3 season">가을</span>
	        </a>
        </div>
        <div class="mx-auto">
	        <a href="<%= request.getContextPath() %>/product/winterProduct.ddg" class="product">
	            <img src="<%= request.getContextPath() %>/images/index/orange.png" style="border-radius: 50%;">
	            <span style="display: block;" class="text-center h4 mt-3 season">겨울</span>
	        </a>
        </div>
        <%-- 반복문 돌려야 함 --%>
    </div>
</div>
<%-- 계절테마 메뉴 끝 --%>

<%-- 신상품 메뉴 시작 --%>
<div style="width: 70%; margin:0 auto;" class="my-5">
	<div class="mb-5" style="display: flex;">
    	<div class="h2">New Arrivals</div>
    	<div style="margin-left: auto;">
      		<a href="<%= request.getContextPath() %>/product/newArrivals.ddg"><span class="btn btn-outline-secondary">상품보러가기&nbsp;&gt;</span></a>
      	</div>
	</div>
      <div style="display: flex;">
      <%-- 반복문 돌려야 함 --%>
        <div class="mx-auto">
            <a href="" class="product">
                <img src="<%= request.getContextPath() %>/images/index/best_banana.png" style="width: 250px; height: 250px;">
            </a>
           	<div style="display: flex;">
            	<div class="mt-3">
	            	<span class="h5 mt-3 product">커팅(팩)바나나 3kg</span>
	            </div>
	            <div class ="wishbutton">
	            	<i onclick="wishToggle(this)" class="fa-regular fa-heart fa-lg heart" ></i>
	            </div>
            </div>
            <span style="display: block;" class="mt-3">15,000원</span>
        </div>
        <div class="mx-auto">
            <a href="" class="product">
                <img src="<%= request.getContextPath() %>/images/index/best_strawberry.png" style="width: 250px; height: 250px;">
            </a>
            <div style="display: flex;">
            	<div class="mt-3">
	            	<span class="h5 mt-3 product">딸기</span>
	            </div>
	            <div class ="wishbutton">
	            	<i onclick="wishToggle(this)" class="fa-regular fa-heart fa-lg heart" ></i>
	            </div>
            </div>
            <span style="display: block;" class="mt-3">21,000원</span>
        </div>
        <div class="mx-auto">
            <a href="" class="product">
                <img src="<%= request.getContextPath() %>/images/index/best_tomato.png" style="width: 250px; height: 250px;">
            </a>
            <div style="display: flex;">
            	<div class="mt-3">
	            	<span class="h5 mt-3 product">방울토마토 5kg</span> <%-- 링크를 JS 로 줘야하나? --%>
	            </div>
	            <div class ="wishbutton">
	            	<i onclick="wishToggle(this)" class="fa-regular fa-heart fa-lg heart" ></i>
	            </div>
            </div>
            <span style="display: block;" class="mt-3">41,000원</span>
        </div>
        <div class="mx-auto">
            <a href="" class="product">
                <img src="<%= request.getContextPath() %>/images/index/best_watermelon.png" style="width: 250px; height: 250px;">
            </a>
            <div style="display: flex;">
            	<div class="mt-3">
	            	<span class="h5 mt-3 product">수박 5kg</span>
	            </div>
	            <div class ="wishbutton">
	            	<i onclick="wishToggle(this)" class="fa-regular fa-heart fa-lg heart"></i>
	            </div>
            </div>
            <span style="display: block;" class="mt-3">31,000원</span>
        </div>
        <%-- 반복문 돌려야 함 --%>
    </div>
</div>
<%-- 베스트셀러 메뉴 끝 --%>

<jsp:include page="../common/footer.jsp"></jsp:include>

