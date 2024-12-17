<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="<%= request.getContextPath()%>/css/mypage/wishlist.css">


<jsp:include page="../common/header.jsp"></jsp:include>

<jsp:include page="mypage_list.jsp"></jsp:include>


<div class="container" style="margin-top: 2%;">

    <div class="titleArea">
        <h3>관심 상품</h3>
    </div>

    <%-- 관심상품안에 상품 있는지 없는지 --%>
    <c:choose>
        <c:when test="${not empty wishList}">
            <div class="wish-items">
                <c:forEach var="item" items="${wishList}">

                    <div class="wish_item" style="display: flex; align-items: center; padding: 2% 0; border-bottom: 1px solid #ccc; border-top: 1px solid #ccc; margin-top: 3%;">
                          <%-- 각각의 상품 체크박스 --%>
				        <div style="flex: 0.5; text-align: center;">
				            <input type="checkbox" name="selectedItems" value="${item.wish_no}">
				        </div>
                        
                        <%-- 상품 이미지 --%>
                        <div style="flex: 1; text-align: center;">
                            <img src="<%= request.getContextPath()%>/images/product/${item.product.prod_thumnail}" style="width: 80%; height: auto;">
                            
                        </div>
                        
                        <%-- 상품 이름 --%>
                        <div style="flex: 2.5;">
                            <p style="font-size: 18pt; margin-left: 3%; font-family: 'Noto Sans KR', sans-serif;">${item.product.prod_name}</p>
                        </div>
                        
                        <%-- 상품 가격 --%>
                        <div style="flex: 2;">
                            <p style="font-size: 15pt; margin-left: 3%; font-family: 'Noto Sans KR', sans-serif;">${item.product.prod_price} 원</p>
                        </div>

                        <%-- 삭제 버튼 --%>
                        <div style="flex: 1; text-align: center;">
                            <button onclick="deleteItem(${item.wish_no})" style="background-color: white; color: black; font-size: 20pt; border: solid 1px white; margin-left: 10%; color: gray;">X</button>
                        </div>
                    </div>
                </c:forEach>
            </div>
			
		 	<%-- 주문하기, 비우기 버튼 --%>
			<div class="ec-base-button gColumn">
			    <a href="#" class="btnpick">선택상품 장바구니에담기</a>  
			    <a href="#" class="btnremove">관심상품 비우기</a>       
		    </div>

        </c:when>
		
        <%-- 관심상품에 상품이 없는 경우 --%>
        <c:otherwise> 
            <div style="border: solid 1px #cccccc; background-color: white; height: 30%; font-weight: bold; margin: 7% auto; ">
    			<p align="center" style="margin-top: 10%;">관심상품 내역이 없습니다.</p>
			</div>
            
            
        </c:otherwise>
    </c:choose>

    
    

</div>


<jsp:include page="../common/footer.jsp"></jsp:include>