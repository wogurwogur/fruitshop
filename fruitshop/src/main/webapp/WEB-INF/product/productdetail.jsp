<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>   



<jsp:include page="../common/header.jsp"></jsp:include>

<%-- Custom CSS --%>
<link rel="stylesheet" href="<%= request.getContextPath()%>/css/product/productdetail.css">

<%-- Custom JS --%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/product/productdetail.js"></script>


<script type="text/javascript">

$(document).ready(function(){
	
		// --------- 수량 증감에 따라 총 금액 및 수량 알아오기 시작 --------- //
	
		let qty = 1; // 수량 초기값 설정
		const maxQty = 20; // 수량 최대값 설정
		let prod_inventory = ${requestScope.prdvo.prod_inventory}; // 현재 상품 재고량
		
		
		// 상품 상세페이지에서 - 버튼을 클릭한 경우
	    $("button.minus").click(function(){
			if (qty > 1 && prod_inventory != 0) {
				qty--;
				$("input.qty").val(qty);
				totalPrice();
			}
			else if (prod_inventory == 0) {
				alert("현재 해당 상품은 품절입니다.")
			}
	    });
	
		// 상품 상세페이지에서 + 버튼을 클릭한 경우
		$("button.plus").click(function(){
			if (qty < maxQty && prod_inventory != 0) {
			    qty++;
				$("input.qty").val(qty);
				totalPrice();
			}
			else if (qty >= maxQty) {
				alert("주문 가능한 최대 수량은 20개입니다.")
				return;
			}
			else if (prod_inventory == 0) {
				alert("현재 해당 상품은 품절입니다.")
			}
		});

		
		totalPrice(); // 페이지 처음 들어왔을 때 총 금액 및 수량 설정
		
	
		// 총 금액 및 수량 함수 ($(document).ready 외부에 위치 시 동작안해서 내부에 위치시켜놓음)
		function totalPrice(){
			const prdPrice = ${requestScope.prdvo.prod_price}; 
			const totalPrice = (prdPrice * qty)
			// console.log(qty);
			$("span#totalPrice").text(totalPrice.toLocaleString() + ' 원(' + qty + '개)');
		};
		// --------- 수량 증감에 따라 총 금액 및 수량 알아오기 끝 --------- //
		
			
		// 장바구니 클릭 
		$("div.cart").click(function() {
			const prdCnt = $("input.qty").val();
			// alert(prdCnt)
			location.href=`${pageContext.request.contextPath}/cart/cartInsert.ddg?prodNo=${requestScope.prdvo.prod_no}&userNo=${sessionScope.loginuser.user_no}&prdCnt=`+ prdCnt;
		});
	
		
		// 구매하기 클릭
		$("div.purchase").click(function() {
			alert("구매하기 클릭")
		});
		
		
		
		
		// 상품후기 쓰기 클릭
		
		
		// 상품후기 모두보기 클릭
		$("div.reveiwAllsee").click(function(){
			// alert("후기 모두보기 클릭")
			location.href =`${pageContext.request.contextPath}/review/reviewList.ddg`;
		});
		
		
		// 문의하기 클릭
		
		// 문의하기 모두보기 클릭
		$("div.inquireAllsee").click(function(){
			location.href =`${pageContext.request.contextPath}/qna/qnaList.ddg`;
		});
		
		
		
}); // end of $(document).ready(function(){
	

</script>


<div id="container">

	<%-- 상품 정보 시작 --%>
	<div id="detailWrap" style="display: flex; max-width: 1400px;">
	
		<%-- 썸네일 --%>
		<div id="prod_thumnail" style="flex: 1; max-width: 700px;">
			<img src="<%=request.getContextPath()%>/images/product/thumnail/${requestScope.prdvo.prod_thumnail}" style="width: 700px; height: 700px;">
		</div>
		
		<%-- 상품정보 --%>
		<div id="prod_info" style="flex: 1; max-width: 700px;">
			
			<%-- 상품명 --%>
			<div>
				<span style="font-size: 24pt; font-weight: bold;">${requestScope.prdvo.prod_name}</span>
			</div>
			
			<%-- 상품 가격 --%>
			<div style="text-align: right;">
				<span style="font-size: 24pt; font-weight: bold;">
					<fmt:formatNumber value="${requestScope.prdvo.prod_price}" pattern="###,###" /> 원
				</span>			
			</div>
			<hr style="border: none; border-top: 1px solid black; margin-bottom: 4%;">  <%-- 검정 실선 --%>
			
			<%-- 재고 수량이 0이라면, 품절 이미지 표시 시작 --%>
			<c:if test="${requestScope.prdvo.prod_inventory == 0}"> 
				<div>
	            	<img src="<%=request.getContextPath()%>/images/product/soldout.png" style="top: 0; left: 0; width: 37px; height: 20px; opacity: 1.0;">        
				</div>
            </c:if>
            <%-- 재고 수량이 0이라면,품절 이미지 표시 끝 --%>
            
            <%-- 배송비 --%>
			<div style="align-items: center;">
				<span style="font-size: 12pt">배송비</span>
				<span class="deliveryCost" style="font-size: 12pt">2,500원</span>
			</div>
			
			<hr style="border: none; border-top: 1px solid #DADADA; margin-top: 4%;"> <%-- 회색 실선 1 --%>
			
			<%-- 수량 --%>			
			<div style="display: flex; align-items: center;">
				<span>수량</span>
				<span class="quantity">
					<button type="button" class="minus">-</button>
					<input type="text" class="qty" value="1" readonly></input>
					<button type="button" class="plus">+</button>
				</span>
			</div>
			
			<hr style="border: none; border-top: 1px solid #DADADA; margin-bottom: 6%;"> <%-- 회색 실선 2 --%>
			
			<%-- 총 상품금액(수량) --%>	
			<div style="display: flex; align-items: center;">
				<span style="font-size: 12pt; font-weight: bold;">총 상품금액(수량): </span>
				<span id="totalPrice" style="font-size: 15.6pt; font-weight: bold; margin-left: auto;"></span>			
			</div>
			
			<hr style="border: none; border-top: 1px solid #DADADA; margin-top: 6%;"> <%-- 회색 실선 3 --%>
			
			<%-- 장바구니 or 구매 --%>
			<div style="display: flex; margin-top: 6%;">
				<c:if test="${requestScope.prdvo.prod_inventory > 0}">
					<div class="cart" style="flex: 1; height: 52px; display: flex; justify-content: center; align-items: center;"> <%-- height 여기만 줌 --%>
						<span>장바구니 담기</span>
					</div>
					<div class="purchase" style="flex: 1; display: flex; justify-content: center; align-items: center;">
						<span>바로 구매하기</span>
					</div>
				</c:if>
				
				<%-- 재고 0일 경우 SOLD OUT --%>
				<c:if test="${requestScope.prdvo.prod_inventory == 0}">
					<div class="soldout" style="flex: 1; height: 52px; display: flex; justify-content: center; align-items: center;">
						<span>SOLD OUT</span>
					</div>
				</c:if>
				<%-- 재고 0일 경우 SOLD OUT 끝 --%>
			</div>
		</div>
		
	</div>
	<%-- 상품 정보 끝 --%>
	
	
	<%-- 상세정보 / 이용안내 / 상품후기 / 문의하기 시작--%>
	<div id="detailWrap2" style="max-width: 1400px;">
		
		<%-- 상세정보 --%>
		<div id="detailInfo" >
			<ul style="display: flex;">
				<li class="detail"><a href="#detail">상세정보</a></li>
				<li><a href="#guide">이용안내</a></li>
				<li><a href="#review">상품후기</a><span class="reviewCnt">0</span></li>
				<li><a href="#inquire">문의하기</a></li>	
			</ul>
			<p id="detail">
				<img src="<%=request.getContextPath()%>/images/product/thumnail/${requestScope.prdvo.prod_thumnail}" style="width: 700px; height: 700px;">
			</p>
		</div>
		
		<%-- 이용안내 --%>
		<div id="guideInfo" >
			<ul style="display: flex;">
				<li><a href="#detail">상세정보</a></li>
				<li class="guide"><a href="#guide">이용안내</a></li>
				<li><a href="#review">상품후기</a><span class="reviewCnt">0</span></li>
				<li><a href="#inquire">문의하기</a></li>	
			</ul>
			<div id="guide" class="guideText" style="height: auto;">
				<span class="paymentTextTitle">상품결제정보</span>
				<span class="paymentTextContetns">
					<br><br>
					고액결제의 경우 안전을 위해 카드사에서 확인전화를 드릴 수도 있습니다. 확인과정에서 도난 카드의 사용이나 타인 명의의 주문등 정상적인 주문이 아니라고 판단될 경우 임의로 주문을 보류 또는 취소할 수 있습니다.  
					<br><br>
					무통장 입금은 상품 구매 대금은 PC뱅킹, 인터넷뱅킹, 텔레뱅킹 혹은 가까운 은행에서 직접 입금하시면 됩니다.
					<br>  
					주문시 입력한 입금자명과 실제입금자의 성명이 반드시 일치하여야 하며, 7일 이내로 입금을 하셔야 하며 입금되지 않은 주문은 자동취소 됩니다
					<br><br><br><br><br><br>
				</span>
				
				<span class="shippingTextTitle">배송정보</span>
				<span class="shippingTextContetns">
					<br><br>
					배송 방법 : 직접 배송  
					<br>
					배송 지역 : 전국지역
					<br>  
					배송 비용 : 무료
					<br>
					배송 기간 : 3일 ~ 7일
					<br>
					배송 안내 : 산간벽지나 도서지방은 별도의 추가금액을 지불하셔야 하는 경우가 있습니다.
					<br>
					고객님께서 주문하신 상품은 입금 확인후 배송해 드립니다. 다만, 상품종류에 따라서 상품의 배송이 다소 지연될 수 있습니다.
					<br><br><br><br><br><br>
				</span>
				
				<span class="changeTextTitle">교환 및 반품 정보</span>
				<span class="changeTextContetns">
					<br><br>
						<span style="font-weight: bold;">교환 및 반품 주소</span>
					<br>
					- 주소 : 서울시 마포구 서교동 홍대입구 인근 3층
					<br><br>
						<span style="font-weight: bold;">교환 및 반품이 가능한 경우</span>
					<br>
					- 계약내용에 관한 서면을 받은 날부터 7일. 단, 그 서면을 받은 때보다 재화등의 공급이 늦게 이루어진 경우에는 재화등을 공급받거나 재화등의 공급이 시작된 날부터 7일 이내
					<br>
					- 공급받으신 상품 및 용역의 내용이 표시.광고 내용과 다르거나 계약내용과 다르게 이행된 때에는 당해 재화 등을 공급받은 날 부터 3월이내, 그사실을 알게 된 날 또는 알 수 있었던 날부터 30일이내
					<br><br>
						<span style="font-weight: bold;">교환 및 반품이 불가능한 경우</span>
					<br>
					- 이용자에게 책임 있는 사유로 재화 등이 멸실 또는 훼손된 경우(다만, 재화 등의 내용을 확인하기 위하여 포장 등을 훼손한 경우에는 청약철회를 할 수 있습니다)
					<br>
					- 이용자의 사용 또는 일부 소비에 의하여 재화 등의 가치가 현저히 감소한 경우
					<br>
					- 시간의 경과에 의하여 재판매가 곤란할 정도로 재화등의 가치가 현저히 감소한 경우
					<br>
					- 복제가 가능한 재화등의 포장을 훼손한 경우
					<br>
					- 개별 주문 생산되는 재화 등 청약철회시 판매자에게 회복할 수 없는 피해가 예상되어 소비자의 사전 동의를 얻은 경우
					<br>
					- 디지털 콘텐츠의 제공이 개시된 경우, (다만, 가분적 용역 또는 가분적 디지털콘텐츠로 구성된 계약의 경우 제공이 개시되지 아니한 부분은 청약철회를 할 수 있습니다.)
					<br><br>
					※ 고객님의 마음이 바뀌어 교환, 반품을 하실 경우 상품반송 비용은 고객님께서 부담하셔야 합니다.
					<br>
					(색상 교환, 사이즈 교환 등 포함)
					<br><br><br><br><br><br>
				</span>		
				
				<span class="serviceTextTitle">서비스문의</span>
				<span class="serviceTextContetns">
					<br><br>
					09:00~12:00
					<br>
					13:00~18:00
				</span>		
			</div>
		</div>
		
		<%-- 상품후기 --%>	
		<div id="prdReview" >
			<ul style="display: flex;">
				<li><a href="#detail">상세정보</a></li>
				<li><a href="#guide">이용안내</a></li>
				<li class="review"><a href="#review">상품후기</a><span class="reviewCnt">0</span></li>
				<li><a href="#inquire">문의하기</a></li>	
			</ul>
			<div id="review" class="reviewBoard" style="height: auto;">
				
				<%-- 상품 후기 없는 경우 --%>
				<span style="font-size: 15pt; font-weight:bold">REVIEW</span>
				<span style="font-size: 12pt; margin-left: 1%;">상품의 사용후기를 적어주세요</span>
				
				<hr style="border: none; border-top: 1px solid #DADADA; margin-top: 1%;"> <%-- 상품후기 회색실선 --%>
				
				<p style="font-size: 10.5pt; color: #555555; padding: 6% 0;">
					게시물이 없습니다.
				</p>
				
				<hr style="border: none; border-top: 1px solid #DADADA; margin-top: 1%;"> <%-- 상품후기 회색실선2 --%>
				
				<%-- 상품후기 쓰기 / 모두 보기 --%>
				<div style="display: flex; margin-top: 0.5%; justify-content: right;">
					<div class="reveiwBtn" style="width: 9%; height: 45px; margin-right: 1%; display: flex; justify-content: center; align-items: center;"> 
						<span>상품후기 쓰기</span>
					</div>
					<div class="reveiwAllsee" style="width: 9%; display: flex; justify-content: center; align-items: center;">
						<span>모두보기</span>
					</div>
				</div>
				
				<%-- 상품 후기 있는 경우 --%>
				
				
				
				
				
				
			</div>
		</div>			
		
		<%-- 문의하기 --%>	
		<div id="prdInquire" >
			<ul style="display: flex;">
				<li><a href="#detail">상세정보</a></li>
				<li><a href="#guide">이용안내</a></li>
				<li><a href="#review">상품후기</a><span class="reviewCnt">0</span></li>
				<li class="inquire"><a href="#inquire">문의하기</a></li>	
			</ul>
			
			<div id="inquire" class="inquireBoard" style="height: auto;">
				
				<%-- 문의 하기 없는 경우 --%>
				<span style="font-size: 15pt; font-weight:bold">Q&A</span>
				<span style="font-size: 12pt; margin-left: 1%;">구매하시려는 상품에 대해 궁금한 점이 있으면 문의주시기 바랍니다.</span>
				
				<hr style="border: none; border-top: 1px solid #DADADA; margin-top: 1%;"> <%-- 상품후기 회색실선 --%>
				
				<p style="font-size: 10.5pt; color: #555555; padding: 6% 0;">
					게시물이 없습니다.
				</p>
				
				<hr style="border: none; border-top: 1px solid #DADADA; margin-top: 1%;"> <%-- 상품후기 회색실선2 --%>
				
				<%-- 상품문의 하기 / 모두 보기 --%>
				<div style="display: flex; margin-top: 0.5%; justify-content: right;">
					<div class="inquireBtn" style="width: 9%; height: 45px; margin-right: 1%; display: flex; justify-content: center; align-items: center;"> 
						<span>상품문의 하기</span>
					</div>
					<div class="inquireAllsee" style="width: 9%; display: flex; justify-content: center; align-items: center;">
						<span>모두보기</span>
					</div>
				</div>
				<%-- 문의 하기 있는 경우 --%>
		
			</div>					
		
		</div>
	
	</div>
	<%-- 상세정보 / 이용안내 / 상품후기 / 문의하기 끝 --%>

</div>


<jsp:include page="../common/footer.jsp"></jsp:include>