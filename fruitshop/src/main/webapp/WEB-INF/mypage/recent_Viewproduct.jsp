<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" href="<%= request.getContextPath()%>/css/mypage/recent_Viewproduct.css">




<jsp:include page="../common/header.jsp"></jsp:include>

<jsp:include page="mypage_list.jsp"></jsp:include>


<div class="container" style="margin-top: 2%;">

    <div class="titleArea">
        <h3>최근본상품</h3>
    </div>

    <%-- 관심상품안에 상품 있는지 없는지 --%>
    <c:choose>
        <c:when test="${not empty wishList}">
        <div class="info">
            <a>상품사진</a>
            <a>상품명</a>
            <a>판매가</a>
            <a>선택</a>
		</div>
		
		
            <div class="wish-items">
                <c:forEach var="item" items="${wishList}">

                    <div class="wish_item" style="display: flex; align-items: center; padding: 2% 0; border-bottom: 1px solid #ccc; border-top: 1px solid #ccc; margin-top: 1.8%;">
                         
                        <%-- 상품 이미지 --%>
                        <div style="flex: 1; text-align: center;">
                            <img src="<%= request.getContextPath()%>/images/product/thumnail/${item.product.prod_thumnail}" style="width: 80%; height: auto;">
                            
                        </div>
                        
                        <%-- 상품 이름 --%>
                        <div style="flex: 2.1;">
                            <p style="font-size: 14pt; margin-left: 3%; font-family: 'Noto Sans KR', sans-serif;">${item.product.prod_name}</p>
                        </div>
                        
                        <%-- 상품 가격 --%>
                        <div style="flex: 1;">
                            <p style="font-size: 14pt; margin-left: 3%; font-family: 'Noto Sans KR', sans-serif; m"><fmt:formatNumber value="${item.product.prod_price}" pattern="###,###" />원</p>
                        </div>

                        <%-- 삭제 버튼 --%>
                        <div style="flex: 0.9; text-align: center;">
                            <form method="post" action="<%= request.getContextPath() %>/mypage/recent_Viewproduct.ddg" onsubmit= "return confirm('정말 삭제하시겠습니까?'); ">
                                <input type="hidden" name="wish_no" value="${item.wish_no}">
                                <button type="submit" style="background-color: white; color: black; font-size: 20pt; border: solid 1px white; margin-left: 10%; color: gray;">X</button>
                            </form>
                        </div>
                        
                    </div>
                </c:forEach>
                
             
            </div>

        </c:when>
		
        <%-- 최근 본 상품이 없는 경우 --%>
        <c:otherwise> 
            <div style="border: solid 1px #cccccc; background-color: white; height: 38%; font-weight: bold; margin: 3% auto; ">
    			<p align="center" style="margin: 10% auto; padding-top: 4%; ">최근 본 상품 내역이 없습니다.</p>
			</div>
            
    		
        </c:otherwise>
    </c:choose>
	

    
    

</div>


<jsp:include page="../common/footer.jsp"></jsp:include>