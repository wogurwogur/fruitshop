<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 


<link rel="stylesheet" href="<%= request.getContextPath()%>/css/cart/cart.css">

<jsp:include page="../common/header.jsp"></jsp:include>


<script type="text/javascript">

	
	$(document).ready(function(){
		
		$("input:number[name='prodcount']").bind("keyup", function(cart_prodcount){
	        if(cart_prodcount.keyCode == 13) {
	        	alert("rr!!");
	          
	        }
	        
	     });// $("input:number[name='prodcount']").bind("keyup", function(e){------- 
		
	});// end of $(document).ready(function(){}----------------------------
	
	
	
	<%-- 선택상품 주문하기 누를때
	function Orderpick() {
		
		confirm("선택한 상품을 주문하시겠습니까?");
		
	}// end of function goCartList() {}-----------------------------

	  전체상품 주문하기 누를때 
	function OrderAll() {
		
		confirm("장바구니에 있는 전체상품을 주문하시겠습니까?");
		
	}
	
	--%>
	
	 <%-- 장바구니 비우기 누를때 --%> 
	function CartDeleteAll() {
		
		confirm("정말로 관심상품을 모두 비우시겠습니까?");
		
	}
	
	
	
</script>

<div class="container" style="margin-top: 2%;">

    <div class="titleArea">
        <h3>Cart</h3>
    </div>
    
    <%-- 장바구니 상품 목록 --%>
    <c:choose>
        <c:when test="${not empty cartList}">
            <div class="cart-items">
                <c:forEach var="item" items="${cartList}">
                <c:set var="itemTotalPrice" value="${item.product.prod_price * item.cart_prodcount}" />
                <c:set var="totalPrice" value="${totalPrice + itemTotalPrice}"/>
                    <div class="cart_item" style="display: flex; align-items: center; padding: 2% 0; border-bottom: solid 1px #ccc; border-top: solid 1px #ccc; margin-top: 2%;">
                          <%-- 각각의 상품 체크박스 --%>
				        <div style="flex: 0.1; text-align: center;">
				            <input type="checkbox" name="selectedItems" value="${item.cart_no}">
				        </div>
                        
                        <%-- 상품 이미지 --%>
                        <div style="flex: 1; text-align: center;">
                            <img src="<%= request.getContextPath()%>/images/product/thumnail/${item.product.prod_thumnail}" style="width: 120px; height: auto;">
                            
                        </div>
                        
                        <%-- 상품 정보 --%>
                        <div style="flex: 1;">
                            <p style="font-size: 15pt; margin-left: 3%; font-family: 'Noto Sans KR', sans-serif;">${item.product.prod_name}</p>
                            <p style="font-size: 15pt; margin-left: 3%; font-family: 'Noto Sans KR', sans-serif;"><fmt:formatNumber value="${item.product.prod_price}" pattern="###,###" />원</p>
                        </div>

                        <%-- 수량 조절 --%>
                         <div style="flex: 2.9; display: flex; align-items: center; justify-content: center;">
                            <button style="width: 8%; font-size: 25pt; background-color: white; border: white;">-</button>
                            <input type='number' min='1' value='${item.cart_prodcount}' name="prodcount" style=" width: 10%; border:solid 1px #ccc;" />
                            <button style="width: 8%; font-size: 23pt; background-color: white; border: white;">+</button>
                        </div>

                        <%-- 상품의 총액 --%>
                        <div style="flex: 1.6; text-align: center;">
                        <button style=" background-color: white; border: 1px solid white; color: black; padding: 4% 30%; font-family: 'Noto Sans KR', sans-serif; font-size: 16pt;">
                               <fmt:formatNumber value="${item.product.prod_price * item.cart_prodcount}" pattern="###,###" />원
                         </button>
                        </div>

                        <%-- 삭제 버튼 --%>
                        <div style="flex: 0.5; text-align: center;">
                           <form method="post" action="<%= request.getContextPath() %>/cart/cartList.ddg" onsubmit= "return confirm('정말 삭제하시겠습니까?'); ">
                                <input type="hidden" name="wish_no" value="${item.cart_no}">
                                <button type="submit" style="background-color: white; color: black; font-size: 20pt; border: solid 1px white; margin-left: 10%; color: gray;">X</button>
                            </form>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <%-- 총 금액 계산 --%>
           <div class="cartsum" style="text-align: center; margin: 5% auto;">
    <table style="margin: 5% auto; text-align: center; font-size: 16pt; width: 80%; border-collapse: collapse; font-family: 'Noto Sans KR', sans-serif;">
        <tr>
            <td style="padding: 2% 5%;">총 상품금액</td>
            <td style="padding: 2%;"></td>
            <td style="padding: 2% 5%;">배송비</td>
            <td style="padding: 2%;"></td>
            <td style="padding: 2% 5%;">총 결제금액</td>
        </tr>
        <tr>
            <td style="padding: 2% 5%; font-size: 14pt;">
             <fmt:formatNumber value="${totalPrice}" pattern="###,###" />원
            </td>
            <td style="padding: 2% -20%; ">+</td>
            <td style="padding: 2% 5%; font-size: 14pt;">2,500원</td>
            <td style="padding: 2%;">=</td>
            <td style="padding: 2% 5%; font-size: 14pt;">
            <fmt:formatNumber value="${totalPrice + 2500}" pattern="###,###" />원
            </td>
        </tr>
    </table>
</div>

	<div class="ec-base-button gColumn">
	    <a href="#" onclick="Orderpick();" class="btnpick">선택상품 주문하기</a>  
	    <a href="#" onclick="OrderAll();" class="btnSubmit">전체상품 주문하기</a>
	    <a href="#" onclick="CartDeleteAll();" class="btnremove">장바구니 비우기</a>       
	    </div>
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
