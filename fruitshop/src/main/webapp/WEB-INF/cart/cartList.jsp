<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="<%= request.getContextPath()%>/css/cart/cart.css">

<jsp:include page="../common/header.jsp"></jsp:include>

<div class="container" style="margin-top: 2%;">

    <div class="titleArea">
        <h3>Cart</h3>
    </div>
    
<%--
    <!-- 장바구니안에 상품 있는지 없는지 -->
    <c:choose>
    
    	
         장바구니안에 상품이 있는 경우 
        <c:when test="${not empty cartItems}">
        
            <!-- 상품 리스트 반복 출력 -->
            
            <c:forEach var="item" items="${requestScope.cartItems}">
                <div class="cart_item" style="border-bottom: 1px solid #cccccc; padding: 20px 0; display: flex; align-items: center;">
                    <!-- 상품 이미지 -->
                    <div style="flex: 1; text-align: center;">
                        <img src="${item.image}" style="width: 80px; height: auto;">
                    </div>
                    <!-- 상품 정보(상품명,상품가격) -->
                    <div style="flex: 4;">
                        <p>${item.name} </p>
                        <p>${item.price} </p>
                    </div>
                    <!-- 수량 변경 -->
                    <div style="flex: 2; display: flex; align-items: center; justify-content: center;">
                        <button class="btn btn-light" style="width: 30px; height: 30px;">-</button>
                        <span style="margin: 0 10px; font-size: 16px;">${item.count}</span>
                        <button class="btn btn-light" style="width: 30px; height: 30px;">+</button>
                    </div>
                    <!-- 주문 버튼 -->
                    <div style="flex: 2; text-align: center;">
                        <button class="btn btn-dark" style="width: 100%; padding: 5px 10px;">주문하기(${item.price}원)</button>
                    </div>
                    <!-- 삭제 -->
                    <div style="flex: 1; text-align: center;">
                        <button class="btn btn-danger" style="width: 30px; height: 30px;">X</button>
                    </div>
                </div>
            </c:forEach>

            <!-- 총 금액 및 버튼 -->
            <div class="cartsum" style="margin-top: 20px; padding: 20px 0; text-align: center; border-top: 1px solid #cccccc;">
                <p style="font-size: 16px;">
                    <span style="font-weight: bold;">총 상품 금액:</span> ${requestScope.totalPrice}원 +
                    <span style="font-weight: bold;">배송비:</span> ${requestScope.배송비}원 =
                    <span style="font-weight: bold;">결제 금액:</span> ${requestScope.totalPrice + requestScope.배송비}원
                </p>
                
            </div>
        </c:when>
		

        <!-- 장바구니에 상품이 없는 경우 -->
        <c:otherwise> --%>
            <div class="jumbotron" style="border: solid 1px #cccccc; background-color: white; margin-top: 5%; font-weight: bold;">
                <p align="center">장바구니가 비어 있습니다.</p>
            </div>
            
            <%-- 
        </c:otherwise>
    </c:choose>
    --%>
	
	
	<div class="ec-base-button gColumn">
    <a href="#" class="btnpick">선택상품 주문하기</a>  
    <a href="#" class="btnSubmit">전체상품 주문하기</a>
    <a href="./cartList.ddg" class="btnremove">장바구니 비우기</a>       
    </div>
	
    <%-- 이용 안내  --%>
    <div class="xans-element- xans-order xans-order-basketguide ec-base-help ">
<div class="inner">
        <h6>장바구니 이용안내</h6>
        <ol>
			<li class="item1">[선택 상품 주문하기] 버튼을 누르시면 장바구니의 선택된 상품들에 대한 주문/결제가 이루어집니다.</li>
			<li class="item2">[전체 상품 주문하기] 버튼을 누르시면 장바구니의 구분없이 선택된 모든 상품에 대한 주문/결제가 이루어집니다.</li>
            <li class="item3">선택하신 상품의 수량을 변경하시려면 수량 [변경(+,-)] 버튼을 누르시면 됩니다.</li>
            <li class="item4">장바구니와 관심상품을 이용하여 원하시는 상품만 주문하거나 관심상품으로 등록하실 수 있습니다.</li>
            <li class="item5">파일첨부 옵션은 동일상품을 장바구니에 추가할 경우 마지막에 업로드 한 파일로 교체됩니다.</li>
            <li class="item6">장바구니와 관심상품을 이용하여 원하시는 상품만 주문하거나 관심상품으로 등록하실 수 있습니다.</li>
            <li class="item7">실제 배송비는 함께 주문하는 상품에 따라 적용되오니 주문서 하단의 배송비 정보를 참고해주시기 바랍니다.</li>
        </ol>
</div>
</div>
    
</div>

<jsp:include page="../common/footer.jsp"></jsp:include>
