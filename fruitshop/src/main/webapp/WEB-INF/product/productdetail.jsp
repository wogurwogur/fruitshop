<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>   



<%-- Custom CSS --%>
<link rel="stylesheet" href="<%= request.getContextPath()%>/css/product/productdetail.css">

 
    
<jsp:include page="../common/header.jsp"></jsp:include>


<div id="container">
	<div id="detailWrap" style="display: flex; justify-content: center;">
		<div id="prod_thumnail">상품썸네일</div>
		<div id="prod_info">
			<div>상품명</div>
			<div>가격</div>
			<div>품절이미지</div>
			<div>배송비</div>
			<div>사이즈</div>
			<div>총상품금액(수량)</div>
			<div>구매/장바구니 버튼 or soldout</div>
		</div>
	</div>
	<div id="detailinfo" style="border:solid 1px red;">상세정보 클릭</div>
	<div id="detailImage" style="border:solid 1px red;">상세이미지</div>
	<div id="guide" style="border:solid 1px red;" >이용안내 클릭</div>
	<div id="guideDetail" style="border:solid 1px red;">이용안내 내용</div>
	<div id="review" style="border:solid 1px red;">상품후기 클릭</div>
	<div id="reviewContents" style="border:solid 1px red;">상품후기 내용</div>
	<div style="border:solid 1px red;" >상품후기 쓰기 / 모두보기</div>
	<div id="inquire" style="border:solid 1px red;" >문의하기 클릭</div>
	<div id="inquireContents" style="border:solid 1px red;" >문의하기 내용</div>
	<div style="border:solid 1px red;">상품문의하기 / 모두보기</div>
</div>



<jsp:include page="../common/footer.jsp"></jsp:include>