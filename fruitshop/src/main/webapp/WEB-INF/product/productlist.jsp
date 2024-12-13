<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>



<jsp:include page="../common/header.jsp"></jsp:include>


<%-- 베스트셀러 메뉴 시작 --%>
<div class="container my-5">
	<div class="mb-5" style="display: flex;">
    	<div class="h2">Best Seller</div>
    	<div style="margin-left: auto;">
      		<span class="btn btn-outline-secondary">상품보러가기&nbsp;&gt;</span>
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
	            <div style="margin-left: auto; margin-top: 11%;">
	            	<span><i onclick="wishToggle()" class="fa-solid fa-heart fa-lg heart" ></i></span>
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
	            <div style="margin-left: auto; margin-top: 11%;">
	            	<span><i onclick="wishToggle()" class="fa-solid fa-heart fa-lg heart" ></i></span>
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
	            <div style="margin-left: auto; margin-top: 11%;">
	            	<span><i onclick="wishToggle()" class="fa-solid fa-heart fa-lg heart" ></i></span>
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
	            <div style="margin-left: auto; margin-top: 11%;">
	            	<span><i onclick="wishToggle()" class="fa-solid fa-heart fa-lg heart" ></i></span>
	            </div>
            </div>
            <span style="display: block;" class="mt-3">31,000원</span>
        </div>
        <%-- 반복문 돌려야 함 --%>
    </div>
</div>
<%-- 베스트셀러 메뉴 끝 --%>


<jsp:include page="../common/footer.jsp"></jsp:include>