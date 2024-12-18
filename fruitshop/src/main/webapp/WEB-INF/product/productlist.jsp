<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<%
    String ctxPath = request.getContextPath();
%>



<%-- Custom CSS --%>
<link rel="stylesheet" href="<%= request.getContextPath()%>/css/product/productlist.css">

<%-- AOS CSS / JS --%>
<link rel="stylesheet" href="https://unpkg.com/aos@2.3.1/dist/aos.css" >
<script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>


<jsp:include page="../common/header.jsp"></jsp:include>


<script type="text/javascript">

	$(document).ready(function(){
		$("span.seasonList").bind("click", function(e){
			// alert($("span.seasonList").index($(e.target)));  seasonNo 값 test
			
			const seasonNo = $("span.seasonList").index($(e.target)); // 계절 버튼 클릭 시 계절번호 값
			
			location.href = "<%= ctxPath%>/product/seasonProduct.ddg?seasonNo="+seasonNo;
			// 계절 번호 값 데이터 가지고 URL 이동
			
		});	// end of $("span.seasonList").bind("click", function(e)
		
	}); // end of $(document).ready(function()
			
</script>



<div id="container">
    <div id="title" style="text-align:center;">
        <a href="javascript:location.reload()" class="product">
            <span class="title">SING SING FRUIT</span>
        </a>
    </div>
    
    <%-- 상품 정렬 방식 시작 --%>
    <div id="seasonList" style="display: flex; justify-content: center;">
    	<div>
    		<a href="<%= ctxPath%>/product/productList.ddg" class="season"><span class="seasonList">전 체</span></a>
    	</div>
 	    <div>
    		<a class="season"><span class="seasonList">봄 과일</span></a>
    	</div>
    	<div>
    		<a class="season"><span class="seasonList">여름 과일</span></a>
    	</div>
    	<div>
    		<a class="season"><span class="seasonList">가을 과일</span></a>
    	</div>
    	<div>
    		<a class="season"><span class="seasonList">겨울 과일</span></a>
    	</div>
    </div>
    <%-- 상품 정렬 방식 시작 --%>
    
    
    <c:if test="${not empty requestScope.prdList}">
        <div class="product-list" style="display: grid; grid-template-columns: repeat(4, 1fr);"> <%-- 한 줄에 4개씩 출력 --%>
            <c:forEach var="prdvo" items="${requestScope.prdList}" varStatus="status">
            	<%-- 한 페이지에 상품 16개 씩 보이게 처리 시작 --%>
                <c:if test="${status.index < 16 }">
                    
                    <%-- 상품 시작 --%>
                    <div class="product-item" data-aos="fade-up" data-aos-delay="0">
                        <div class="my-5" style="position: relative;">
                            <a href="<%= ctxPath%>/product/productDetail.ddg"> <%-- 이미지 클릭 시 상세페이지 열기 --%>
                                <img src="<%=request.getContextPath()%>/images/product/thumnail/${prdvo.prod_thumnail}" style="width: 100%; height: auto;">
                            </a>
                            
                            <%-- 재고 수량이 0이라면, 이미지 상단에 품절 이미지 표시 시작 --%>
                            <c:if test="${prdvo.prod_inventory == 0}">   
                                <img src="<%=request.getContextPath()%>/images/product/soldout.png" style="position: absolute; top: 0; left: 0; width: 37px; height: 20px; opacity: 1.0;">
                            </c:if>
                            <%-- 재고 수량이 0이라면, 이미지 상단에 품절 이미지 표시 끝 --%>

                            <div style="display: flex;">
                                <div>
                                    <a href="#" class="product">
                                        <span class="mt-3 product">${prdvo.prod_name}</span>
                                    </a>
                                </div>
                                <div class="ml-auto">
                                    <span><i onclick="wishToggle()" class="fa-solid fa-heart fa-lg heart mt-3"></i></span>
                                </div>
                            </div>
                            <span style="display: block;" class="mt-3 price">
                                <fmt:formatNumber value="${prdvo.prod_price}" pattern="###,###" /> 원
                            </span>
                        </div>
                    </div>
                    <%-- 상품 끝 --%>
                    
                </c:if>
                 <%-- 한 페이지에 상품 16개 씩 보이게 처리 끝 --%>
            </c:forEach>
        </div>
    </c:if>    
    
</div>


<jsp:include page="../common/footer.jsp"></jsp:include>


<%-- AOS 초기화 --%>
<script>
  // AOS 초기화
  AOS.init({
    duration: 1000,  // 애니메이션 지속 시간
    once: false,      // 애니메이션을 한 번만 실행
    offset: 100      // 스크롤 위치에 따라 애니메이션 시작 지점 설정
  });
</script>

