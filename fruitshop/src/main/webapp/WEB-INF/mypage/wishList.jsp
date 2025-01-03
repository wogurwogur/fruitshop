<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<link rel="stylesheet" href="<%= request.getContextPath()%>/css/mypage/wishlist.css">

<jsp:include page="../common/header.jsp"></jsp:include>

<jsp:include page="mypage_list.jsp"></jsp:include>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/wishList/wishList.js"></script>

<script type="text/javascript">


$(document).ready(function(){
	
    // 상품 디테일로 가는 함수
    function goProductDetail(e) {
    	
    	const prod_no = $(e.target).parents(".wish_item").find("input[name='prodno']").val(); // 상품번호
		
	    location.href = "${pageContext.request.contextPath}" + "/product/productDetail.ddg?prodNo=" + prod_no;
    }

    // 썸네일 클릭
    $("div.thumnail").click(function (prod_no) {
    	goProductDetail(prod_no);
    });

    // 상품명 클릭
    $("div.name").click(function (prod_no) {
    	goProductDetail(prod_no); 
    });

    // 가격 클릭
    $("div.price").click(function (prod_no) {
    	goProductDetail(prod_no);
    });
    
});// end of $(document).ready(function(){}------------------------------------------------------

		
<%-- 관심상품 비우기 누를때 --%>
function WishDeleteAll() {

	if( confirm("정말로 관심상품을 모두 비우시겠습니까?") ){
		
	const frm = document.getElementById("deleteAll");
		frm.submit();
	}
	
}

</script>

<div class="container" style="margin-top: 2%; margin-bottom: 5%;">

    <div class="titleArea">
        <h3>관심 상품</h3>
    </div>

    <%-- 관심상품 안에 상품 있는지 없는지 --%>
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
                <div class="wish_item" style="display: flex; align-items: center; padding: 2% 0; border-bottom: 1px solid #ccc; border-top: 1px solid #ccc; margin-top: 1.8%; ">
                    
                    <%-- 상품 번호 숨기기 --%>
                    <input type="hidden" name="prodno" value="${item.product.prod_no}" />
                    
                    <%-- 썸네일 --%>
                    <div class="thumnail" style="flex: 1; text-align: center;">
                        <img src="<%= request.getContextPath()%>/images/product/thumnail/${item.product.prod_thumnail}" style="width: 60%; height: auto;">
                    </div>
                    
                    <%-- 상품 이름 --%>
                    <div class="name" style="flex: 1;">
                        <p style="font-size: 14pt; margin-left: 3%; font-family: 'Noto Sans KR', sans-serif;">${item.product.prod_name}</p>
                    </div>
                    
                    <%-- 상품 가격 --%>
                    <div class="price" style="flex: 0.5;">
                        <p style="font-size: 14pt; margin-left: 3%; font-family: 'Noto Sans KR', sans-serif;">
                            <fmt:formatNumber value="${item.product.prod_price}" pattern="###,###" />원
                        </p>
                    </div>

                    <%-- 삭제 버튼 --%>
                    <div style="flex: 0.5; text-align: center;">
                        <form method="post" action="<%= request.getContextPath() %>/mypage/wishList.ddg" onsubmit="return confirm('정말 삭제하시겠습니까?');">
                            <input type="hidden" name="wish_no" value="${item.wish_no}">
                            <button type="submit" style="background-color: white; color: black; font-size: 20pt; border: solid 1px white; margin-left: 10%; color: gray;">X</button>
                        </form>
                    </div>
                </div>
            </c:forEach>
        </div>
        
        <%-- 비우기 버튼 --%>
        <div class="ec-base-button gColumn">
            <a href="#" onclick="WishDeleteAll(); return false;" class="btnremove">관심상품 비우기</a>
        </div>

        <form method="post" action="<%= request.getContextPath() %>/mypage/wishList.ddg" id="deleteAll">
            <input type="hidden" name="delete_all" value="true">
        </form>
        </c:when>
        
        <%-- 관심상품이 없는 경우 --%>
        <c:otherwise>
            <div style="border: solid 1px #cccccc; background-color: white; height: 38%; margin: 3% auto; ">
                <p style="font-size: 14pt; font-family: 'Noto Sans KR', sans-serif; text-align: center; padding: 15%; ">관심상품 내역이 없습니다.</p>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="../common/footer.jsp"></jsp:include>
