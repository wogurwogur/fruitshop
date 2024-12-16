<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="<%= request.getContextPath()%>/css/mypage/wishlist.css">


<jsp:include page="../common/header.jsp"></jsp:include>

<jsp:include page="mypage_list.jsp"></jsp:include>


<div class="container-fluid" >


<%--
    <!-- 관심상품안에 상품 있는지 없는지 -->
    <c:choose>
    
    	
         관심상품안에 상품이 있는 경우 
        <c:when test="${not empty cartItems}">
        
            <!-- 상품 리스트 반복 출력 -->
            
            <c:forEach var="item" items="${requestScope.cartItems}">
                <div class="cart_item" style="border-bottom: 1px solid #cccccc; padding: 20px 0; display: flex; align-items: center;">
                    
                    
                    <!-- 상품 이미지 -->
                    <div style="flex: 2; text-align: center;">
                        <img src="${item.image}" style="width: 80px; height: auto;">
                    </div>
                    <!-- 상품 정보(상품명,상품가격) -->
                    <div style="flex: 6;">
                        <p>${item.name} </p>
                        <p>${item.price} </p>
                    </div>
                   
                    <!-- 삭제 -->
                    <div style="flex: 2; text-align: center;">
                        <button class="btn btn-danger" style="width: 30px; height: 30px;">X</button>
                    </div>
                </div>
            </c:forEach>

        </c:when>
		

        <!-- 관심상품에 상품이 없는 경우 -->
        <c:otherwise> --%>
            <div class="jumbotron" style="border: solid 1px #cccccc; background-color: white; margin-top: 8%; font-weight: bold; ">
    			<p align="center">관심상품 내역이 없습니다.</p>
			</div>
            
            <%-- 
        </c:otherwise>
    </c:choose>
    --%>
    
     
    
     <%-- 주문하기, 비우기 버튼 --%>
	<div class="ec-base-button gColumn">
	    <a href="#" class="btnpick">선택상품 장바구니에담기</a>  
	    <a href="#" class="btnremove">관심상품 비우기</a>       
    </div>

</div>


<jsp:include page="../common/footer.jsp"></jsp:include>