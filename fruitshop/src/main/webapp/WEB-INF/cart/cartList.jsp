<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 


<jsp:include page="../common/header.jsp"></jsp:include>

<link rel="stylesheet" href="<%= request.getContextPath()%>/css/cart/cart.css">
<script type="text/javascript" src="<%= request.getContextPath() %>/js/cart/cart.js"></script>


<script type="text/javascript">

function Orderpick() {
	
	const check = $("input:checkbox[name='selectedItems']:checked").length;
	//console.log(check);
	
	
	if(check == 0){
		
		alert("선택된 상품이 없습니다.");
	}
	else{
		
	 	if(confirm("선택한 상품을 주문하시겠습니까?")){
	 		
	 		const arr_cart_no = [];
			
			$("input:checkbox[name='selectedItems']:checked").each(function (index, elmt) {
				console.log("체크박스 값 : ", $(elmt).val());
				arr_cart_no.push($(elmt).val());
			});
	 		
	 		const cart_no = arr_cart_no.join(",");
			
			$("input#selectedItem").val(cart_no);
	 		
	 		const frm = document.getElementById("checked");
	 		
	 		frm.action = `${pageContext.request.contextPath}/order/orderCheckout.ddg`;
	 		frm.method = "get";
	 		frm.submit();
	 	}
	}
	
}// end of function goCartList() {}-----------------------------

<%--  전체상품 주문하기 누를때 --%>
function OrderAll() {
	
	
	if(confirm("장바구니에 있는 전체상품을 주문하시겠습니까?")){
		
		location.href=`${pageContext.request.contextPath}/order/orderCheckout.ddg?cartTotal=Y&userNo=${sessionScope.loginuser.user_no}`;
	}
	
}// end of function Orderpick() {}-----------------------------

	<%--  전체상품 주문하기 누를때 --%>
	function OrderAll() {
		
		let soldout = false; 
		let soldOutItems = [];
		
		    $(".cart_item").each(function () {
		        const cartItem = $(this);
		        // console.log(cartItem);
		        const prod_su = parseInt(cartItem.find("input[name='prodinventory']").val(), 10); // 상품 재고
        		// console.log(prod_su);
        		const prod_name = cartItem.find(".prod_name").text().trim(); // 상품 이름
        		console.log("상품이름 : ", prod_name);
        		
		        if (prod_su === 0) {
		           soldout = true;
		           soldOutItems.push(prod_name);
		        }
		        	
		    });
		    
		    if (soldout) {
		        // 품절된 상품 목록 알림
		        alert(`장바구니에 품절된 상품이 있습니다:\${soldOutItems.join(", ")}\n 품절상품을 제외하고 주문하시길 바랍니다.`);
		        return; // 품절된 상품이 있으면 주문 진행 중단
		    }
		    
		    if (!soldout) {
			    if(confirm("장바구니에 있는 전체상품을 주문하시겠습니까?")){
	    			
	    			location.href=`${pageContext.request.contextPath}/order/orderCheckout.ddg?cartTotal=Y&userNo=${sessionScope.loginuser.user_no}`;
	    		}
		    }
        
		 
		
		
		
	}// end of function OrderAll() {}---------------------------------------
	
	
	 <%-- 장바구니 비우기 누를때 --%> 
	function CartDeleteAll() {
		
		if( confirm("정말로 장바구니를 모두 비우시겠습니까?") ){
			
		const frm = document.getElementById("deleteAll");
			frm.submit();
		}
		
	}
	
	
</script>

<div class="container" style="margin-top: 2%;">

    <div class="titleArea">
        <h3>Cart <i class="bi bi-cart2"></i></h3>
        <i class="fa-solid fa-cart-shopping"></i>
    </div>
    
    <%-- 장바구니 상품 목록 --%>
    <c:choose>
        <c:when test="${not empty cartList}">
            <div class="cart-items">
                <c:forEach var="item" items="${cartList}">
                <c:set var="itemTotalPrice" value="${item.product.prod_price * item.cart_prodcount}" />
                <c:set var="totalPrice" value="${totalPrice + itemTotalPrice}"/>
                    <div class="cart_item" style="display: flex; align-items: center; padding: 2% 0; border-bottom: solid 1px #ccc; border-top: solid 1px #ccc; margin-top: 2%;">
                    
                    <%-- 상품 수량 숨기기 --%>
                    <input type="hidden" name="prodinventory" value="${item.product.prod_inventory}" />

 						            
                          <%-- 각각의 상품 체크박스 --%>
				        <div style="flex: 0.1; text-align: center;">
				        	<form id="checked">
				        	    <input type="hidden" id="selectedItem" name="selectedItem">
				            	<input type="checkbox" id="${item.cart_no}" name="selectedItems" value="${item.cart_no}">
				            	<input type="hidden" name="userNo" value="${sessionScope.loginuser.user_no}">
				            </form>    
				        </div>
                        
                        <%-- 상품 이미지 --%>
                        <div class="img" style="flex: 1; text-align: center;">
                            <label for="${item.cart_no}">
                            <img src="<%= request.getContextPath()%>/images/product/thumnail/${item.product.prod_thumnail}" style="width: 120px; height: auto;">
                            </label>
                        </div>
                        
                        <%-- 상품 정보 --%>
                        <div style="flex: 1;">
                        <p  class="prod_name"  style="font-size: 15pt; margin-left: 3%; font-family: 'Noto Sans KR', sans-serif;">${item.product.prod_name}</p>
                       <p style="font-size: 15pt; margin-left: 3%; font-family: 'Noto Sans KR', sans-serif;" 
					   class="price" data-price="${item.product.prod_price}">
					   <fmt:formatNumber value="${item.product.prod_price}" pattern="###,###" />원 </p>

                        </div>

                        <%-- 수량 조절 --%>
                         <div style="flex: 2.9; display: flex; align-items: center; justify-content: center;">
                            <button class="minus" style="width: 8%; font-size: 25pt; background-color: white; border: white;">-</button>
                            <input type='text' min='0' value='${item.cart_prodcount}' class="prodcount" style=" width: 10%; border:solid 1px #ccc;" />
                            <button class="plus" style="width: 8%; font-size: 25pt; background-color: white; border: white;">+</button>
                        </div>

                        <%-- 상품의 총액 --%>
                        <div style="flex: 1.6; text-align: center;">
                        <button class="prodtotal" style="background-color: white; border: 1px solid white; color: black; padding: 4% 30%; font-family: 'Noto Sans KR', sans-serif; font-size: 16pt;">
					    <fmt:formatNumber value="${item.product.prod_price * item.cart_prodcount}" pattern="###,###" />원
						</button>
                        </div>
                        

                        <%-- 삭제 버튼 --%>
                        <div style="flex: 0.5; text-align: center;">
                           <form method="post" action="<%= request.getContextPath() %>/cart/cartList.ddg" onsubmit= "return confirm('정말 삭제하시겠습니까?'); ">
                                <input type="hidden" name="cart_no" value="${item.cart_no}">
                                <button type="submit" style="background-color: white; color: black; font-size: 20pt; border: solid 1px white; margin-left: 10%; color: gray;">X</button>
                            </form>
                        </div>
                    </div>
                </c:forEach>
            </div>

		
			
			<div class="sum" >
		    <!-- 선택상품 합계 -->
		    <div class="selectsum" style="float: left; margin-left: 48%; padding: 3% 0;">
		        <p style="font-size: 17pt;">선택상품 합계&nbsp; : &nbsp;원</p>
		    </div>
		
		    <!-- 장바구니 합계 -->
		    <div class="cartsum" style="float: right; padding: 3% 0; margin-right: 3%;">
		        <p style="font-size: 17pt;">장바구니 합계&nbsp; : &nbsp;
		        <fmt:formatNumber value="${totalPrice}" pattern="###,###" />원</p>
		    </div>
		</div>
		  

	<div class="ec-base-button gColumn">
	    <a href="#" onclick="Orderpick();" class="btnpick">선택상품 주문하기</a>  
	    <a href="#" onclick="OrderAll();" class="btnSubmit">전체상품 주문하기</a>
	    <a href="#" onclick="CartDeleteAll(); return false;" class="btnremove">장바구니 비우기</a>       
	    </div>
	    
	    
	  
	    <form method="post" action="<%= request.getContextPath() %>/cart/cartList.ddg" id="deleteAll">
		<input type="hidden" name="delete_all" value="true">
		</form>
		
        </c:when>
		
        <%-- 장바구니에 상품이 없는 경우 --%>
        <c:otherwise> 
            <div class="jumbotron" style="border: solid 1px #cccccc; background-color: white; margin-top: 5%; font-weight: bold;">
                <p align="center">장바구니가 비어 있습니다.</p>
            </div>
            
            
        </c:otherwise>
    </c:choose>
    
	
	
    <%-- 이용 안내  --%>
    <div class="xans-element- xans-order xans-order-basketguide ec-base-help ">
    <div class="inner">
        <h6>장바구니 이용안내</h6>
        <ol>
			<li class="item1">[선택 상품 주문하기] 버튼을 누르시면 장바구니의 선택된 상품들에 대한 주문/결제가 이루어집니다.</li>
			<li class="item2">[전체 상품 주문하기] 버튼을 누르시면 장바구니의 구분없이 선택된 모든 상품에 대한 주문/결제가 이루어집니다.</li>
			<li class="item3">[장바구니 비우기] 버튼을 누르시면 장바구니에 있는 모든 상품들이 없어집니다.</li>
            <li class="item4">선택하신 상품의 수량을 변경하시려면 수량변경 버튼과 키보드로 입력하시면 됩니다.</li>
            <li class="item5">장바구니와 관심상품을 이용하여 원하시는 상품만 주문하거나 관심상품으로 등록하실 수 있습니다.</li>
            <li class="item6">파일첨부 옵션은 동일상품을 장바구니에 추가할 경우 마지막에 업로드 한 파일로 교체됩니다.</li>
            <li class="item7">장바구니와 관심상품을 이용하여 원하시는 상품만 주문하거나 관심상품으로 등록하실 수 있습니다.</li>
        </ol>
</div>
</div>
    
</div>



<jsp:include page="../common/footer.jsp"></jsp:include>
