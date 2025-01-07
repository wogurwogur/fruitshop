<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>   
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 


<jsp:include page="../common/header.jsp"></jsp:include>

<%-- Custom CSS --%>
<link rel="stylesheet" href="<%= request.getContextPath()%>/css/product/productdetail.css">



<script type="text/javascript">




let isOrderOK = false; // 로그인한 유저가 상품을 구매했는지 확인하기 위한 용도

$(document).ready(function(){
	
/* 		//위치 이동을 위한 스크롤 확인
		window.addEventListener('scroll', function() {
		    let scrollPosition = window.scrollY;
		    console.log("현재 스크롤 위치:", scrollPosition);
		}); */

	
		// --------- 수량 증감에 따라 총 금액 및 수량 알아오기 시작 --------- //
	
		let qty = 1; // 수량 초기값 설정
		let prod_inventory = ${requestScope.prdvo.prod_inventory}; // 현재 상품 재고량
		
		
		if(prod_inventory != 0) { // 재고량이 0이 아닌 경우에만
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
				if (qty < prod_inventory && prod_inventory != 0) {
				    qty++;
					$("input.qty").val(qty);
					totalPrice();
				}
				else if (qty >= prod_inventory) {
					alert("현재 해당 상품은 " + prod_inventory + "개 까지 구매 가능합니다.")
					return;
				}
				else if (prod_inventory == 0) {
					alert("현재 해당 상품은 품절입니다.")
				}
			});
		}
		
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
            
         if( ${not empty sessionScope.loginuser} ) {
         
              $.ajax({
                  url: `${pageContext.request.contextPath}/cart/cartInsert.ddg`,
                  type: 'get',
                  data: {
                     "prodNo": "${requestScope.prdvo.prod_no}", // 상품번호
                     "userNo": "${sessionScope.loginuser.user_no}", // 회원번호
                     "prdCnt": $("input.qty").val()   // 상품수량
                  },
                  success: function(response) {
                     if(confirm("장바구니에 상품을 추가했습니다.\n장바구니를 확인하시겠습니까?")){
                      const prdCnt = $("input.qty").val();
                      location.href=`${pageContext.request.contextPath}/cart/cartList.ddg`;
                   }
                      else{
                          location.href="javascript:history.go(0);";
                      }
                  },
                  error: function(error) {
                     alert("장바구니 추가 오류:", error);
                  }
              });
         }
         else{
        	alert("로그인 후 이용가능합니다!");
            location.href=`${pageContext.request.contextPath}/login/login.ddg`;
         }
               
       }); // end of $("div.cart").click(function()
	
		
		   
		// 구매하기 클릭
		$("div.purchase").click(function() {
			const prodCnt = $("input.qty").val();
			location.href=`${pageContext.request.contextPath}/order/orderCheckout.ddg?prodNo=${requestScope.prdvo.prod_no}&userNo=${sessionScope.loginuser.user_no}&prodCnt=`+ prodCnt;
		});
		
	    
       
        // 로그인 한 유저가 상품을 구매한 이력이 있는지 확인하기
        $.ajax({
      	    url:`${pageContext.request.contextPath}/product/isOrder.ddg`,
            type:"get",
            data:{"fk_prod_no":"${requestScope.prdvo.prod_no}"
          	     ,"fk_user_no":"${sessionScope.loginuser.user_no}"},
            dataType:"json",
            
            //async:true, 	// 비동기 처리(기본값)
            async:false,	// 동기 처리   
            success:function(json){
					
            	   console.log("~~ 확인용 : " + JSON.stringify(json));

                   isOrderOK = json.isOrder;
                   // json.isOrder 값이 true  이면 로그인한 사용자가 해당 상품을 구매한 경우이고
                   // json.isOrder 값이 false 이면 로그인한 사용자가 해당 상품을 구매하지 않은 경우이다.
            },
            error: function(request, status, error){
            		alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
            }              
        }); // end of $.ajax 로그인 한 유저가 상품을 구매한 이력이 있는지 확인하기
       
       
		
		// 상품후기 쓰기 클릭
		$("div.reveiwBtn").click(function() {
			
			if (${not empty sessionScope.loginuser.user_no}) {
				
				if (!isOrderOK) {
					alert("제품을 구매한 후에 후기를 작성하실 수 있습니다.")
				}
				else {
					location.href=`${pageContext.request.contextPath}/community/productCarrier.ddg?prod_no=${requestScope.prdvo.prod_no}&user_no=${sessionScope.loginuser.user_no}`;
				}
			}
			else {
				alert("로그인 후 이용가능합니다!");
				location.href=`${pageContext.request.contextPath}/login/login.ddg`;	
			}
		});
	
		
		
		
		// 상품후기 모두보기 클릭
		$("div.reveiwAllsee").click(function(){
			// alert("후기 모두보기 클릭")
			location.href =`${pageContext.request.contextPath}/review/reviewList.ddg`;
		});
		
		
		
		
		// 문의하기 클릭
		$("div.inquireBtn").click(function() {
			
			if (${not empty sessionScope.loginuser.user_no}) {

				location.href=`${pageContext.request.contextPath}/community/productCarrier.ddg?prod_no=${requestScope.prdvo.prod_no}&user_no=${sessionScope.loginuser.user_no}`;
				
			}
			else {
				alert("로그인 후 이용가능합니다!");
				location.href=`${pageContext.request.contextPath}/login/login.ddg`;	
			}
		});
		
		
		
		// 문의하기 모두보기 클릭
		$("div.inquireAllsee").click(function(){
			location.href =`${pageContext.request.contextPath}/qna/qnaList.ddg`;
		});
		
		
		
		
		
		//---------------------------------------------------------------------------------------//
		if( ${not empty sessionScope.loginuser} ) {  // 로그인했을때
      
      	// 세션스토리지안에 있는 arr_product를 가져왔는데 이게 null 일때
         if(sessionStorage.getItem("arr_product") == null){ 
            const arr_product = []; // 새로운 상품배열을 만든다.
            const product = {prod_no:"${requestScope.prdvo.prod_no}", thumbnail:"${requestScope.prdvo.prod_thumnail}", name:"${requestScope.prdvo.prod_name}", price:"${requestScope.prdvo.prod_price}"};
            // 새로운 상품객체를 만든다.
            arr_product.push(product);
            // 만든 상품객체를 만든 상품의 배열에 넣어준다.
            sessionStorage.setItem("arr_product", JSON.stringify(arr_product));
            // 세션스토리지에 담아준다.
         }
         
         else{ // 세션스토리지안에 저장된게 있다면
            
            const arr_product = JSON.parse(sessionStorage.getItem("arr_product"));   // 저장된 것을 가져온다 ("key");
            
            let exist = false;
            
            for(let i =0; i<arr_product.length; i++){  // 중복된 상품번호가 있는지 반복문으로 알아온다. 
               
               // product = {prod_no: '35', thumbnail: 'melon.png', name: '국내산 고당도 멜론 개당 1.6kg', price: '18500'};
                
               if(arr_product[i].prod_no == "${requestScope.prdvo.prod_no}"){
                  
                  arr_product.splice(i,1);
                  
                  const product = {prod_no:"${requestScope.prdvo.prod_no}", thumbnail:"${requestScope.prdvo.prod_thumnail}", name:"${requestScope.prdvo.prod_name}", price:"${requestScope.prdvo.prod_price}"};
                  // {prod_no: '35', thumbnail: 'melon.png', name: '국내산 고당도 멜론 개당 1.6kg', price: '18500'}
                  // {prod_no: '34', thumbnail: 'koreanmelon.png', name: '성주 꿀참외 5kg', price: '26000'}
                  // {prod_no:"${requestScope.prdvo.prod_no}", thumbnail:"${requestScope.prdvo.prod_thumnail}", name:"${requestScope.prdvo.prod_name}", price:"${requestScope.prdvo.prod_price}"}
                  arr_product.push(product); 
                  exist = true;
                  break;
               }
               
            }// end of for---------------------

            if(!exist){
               const product = {prod_no:"${requestScope.prdvo.prod_no}", thumbnail:"${requestScope.prdvo.prod_thumnail}", name:"${requestScope.prdvo.prod_name}", price:"${requestScope.prdvo.prod_price}"};
               // {prod_no: '35', thumbnail: 'melon.png', name: '국내산 고당도 멜론 개당 1.6kg', price: '18500'}
               // {prod_no: '34', thumbnail: 'koreanmelon.png', name: '성주 꿀참외 5kg', price: '26000'}
               // {prod_no:"${requestScope.prdvo.prod_no}", thumbnail:"${requestScope.prdvo.prod_thumnail}", name:"${requestScope.prdvo.prod_name}", price:"${requestScope.prdvo.prod_price}"}
               arr_product.push(product); 
            }
            
            sessionStorage.setItem("arr_product", JSON.stringify(arr_product)); // 세션스토리지에 담아준다.
            
         }
         
         console.log(JSON.parse(sessionStorage.getItem("arr_product")));      
       }	
		
		
}); // end of $(document).ready(function(){
	
	
// 상품 후기 글 클릭 시 리뷰 페이지로 넘어가기
function goReview(click_review_no) {
	const review_no = click_review_no;
	location.href =`${pageContext.request.contextPath}/review/reviewRead.ddg?review_no=`+review_no;
}


// 상품 문의 글 클릭 시 문의 페이지로 넘어가기
function goQna(click_qna_no) {
	const qna_no = click_qna_no;
	location.href =`${pageContext.request.contextPath}/qna/qnaRead.ddg?qna_no=`+qna_no;
}

	

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
					<c:if test="${requestScope.prdvo.prod_inventory > 0}">
						<input type="text" class="qty" value="1" readonly></input>
					</c:if>
					<c:if test="${requestScope.prdvo.prod_inventory == 0}">
						<input type="text" class="qty" value="0" readonly></input>
					</c:if>
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
	
	<div id="godetail" class="detailEmptyDiv"></div>
	
	<%-- 상세정보 / 이용안내 / 상품후기 / 문의하기 시작--%>
	<div id="detailWrap2" style="max-width: 1400px;">
		
		<%-- 상세정보 --%>
		<div id="detailInfo" class="fixed">
			<ul style="display: flex;">
				<li class="detail"><a href="#godetail" class="detailBar">상세정보</a></li>
				<li><a href="#goguide" class="detailBar">이용안내</a></li>
				<li><a href="#goreview" class="detailBar">상품후기<span class="reviewCnt">${requestScope.review_cnt}</span></a></li>
				<li><a href="#goinquire" class="detailBar">문의하기</a></li>	
			</ul>
			<p>
				<img src="<%=request.getContextPath()%>/images/product/description/${requestScope.prdvo.prod_descript}" style="width: 850px; height: auto;">
				<div id="goguide" class="guideEmptyDiv"></div>	
			</p>
		</div>
		
		
		<%-- 이용안내 --%>
		<div id="guideInfo" >
			<ul style="display: flex;">
				<li><a href="#godetail" class="detailBar">상세정보</a></li>
				<li class="guide"><a href="#goguide" class="detailBar">이용안내</a></li>
				<li><a href="#goreview" class="detailBar">상품후기<span class="reviewCnt">${requestScope.review_cnt}</span></a></li>
				<li><a href="#goinquire" class="detailBar">문의하기</a></li>	
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
		
		<div id="goreview" class="reviewEmptyDiv"></div>
		
		<%-- 상품후기 --%>	
		<div id="prdReview" >
			<ul style="display: flex;">
				<li><a href="#godetail" class="detailBar">상세정보</a></li>
				<li><a href="#goguide" class="detailBar">이용안내</a></li>
				<li class="review"><a href="#goreview" class="detailBar">상품후기<span class="reviewCnt">${requestScope.review_cnt}</span></a></li>
				<li><a href="#goinquire" class="detailBar">문의하기</a></li>	
			</ul>
			<div id="review" class="reviewBoard" style="height: auto;">
				
				<span style="font-size: 15pt; font-weight:bold">REVIEW</span>
				<span style="font-size: 12pt; margin-left: 1%;">상품의 사용후기를 적어주세요</span>
			
				
				<%-- 상품 후기 있는 경우 --%>
				<c:if test="${not empty requestScope.prd_reviewList}">
					<hr style="border: none; border-top: 1px solid black; margin-top: 1%;"> <%-- 상품후기 있을 때 검정 실선 --%>
					
					<table class="table table-borderless">
						<colgroup> <%-- 테이블 간 간격 설정 --%>
						<col style="width: 10%;">
						<col style="width: 60%;">
						<col style="width: 10%;">
						<col style="width: 20%;">
						<colgroup>
				        <thead>
					        <tr class="reviewInfoTitle">
						        <th scope="col">번호</th>
						        <th scope="col">제목</th>
						        <th scope="col">작성자</th>
						        <th scope="col">작성날짜</th>
					        </tr>
				        </thead>
				        <tbody>
				        	<c:forEach var="prd_review" items="${requestScope.prd_reviewList}" varStatus="status">
					        	<tr class="reviewInfo" onclick="goReview('${prd_review.review_no}')">
					        		<fmt:parseNumber var="currentShowPageNo" value="${requestScope.currentShowPageNo}" /> <%-- fmt:parseNumber 은 문자열을 숫자형식으로 형변환 시키는 것이다. --%>
					        		<td>${(requestScope.totalReviewCount) - (currentShowPageNo - 1) * 10 - (status.index)}</td> <%-- 10개씩 보여줌 --%>
					        		<td class="reviewTitle">${prd_review.review_title}</td>
					        		<td>
					        		<c:choose>
								        <c:when test="${fn:length(prd_review.userid) > 3}">
								            ${fn:substring(prd_review.userid, 0, fn:length(prd_review.userid) - 3)}*** <!-- 뒤 3글자 마스킹 -->
								        </c:when>
								        <c:otherwise>
								            ${prd_review.userid} <!-- 3자 이하일 경우 그대로 출력 -->
								        </c:otherwise>
							    	</c:choose>					     
					        		</td>
					        		<td>${prd_review.review_regidate}</td>
					        	</tr>
				        	</c:forEach>
				        </tbody>
			        </table>
			        <%-- 
					<div id="pageBar">
						 ${requestScope.reviewPageBar}
					</div>
					--%>
		        </c:if>
		        
		        <c:if test="${empty requestScope.prd_reviewList}"> 
			        <hr style="border: none; border-top: 1px solid #DADADA; margin-top: 1%;"> <%-- 상품후기 회색실선 --%>
					
					<p style="font-size: 10.5pt; color: #555555; padding: 6% 0;">
						게시물이 없습니다.
					</p>
				</c:if>
			
			
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
			</div>
		</div>		
		
		<div id="goinquire" class="inquireEmptyDiv"></div>	
		
		<%-- 문의하기 --%>	
		<div id="prdInquire" >
			<ul style="display: flex;">
				<li><a href="#godetail" class="detailBar">상세정보</a></li>
				<li><a href="#goguide" class="detailBar">이용안내</a></li>
				<li><a href="#goreview" class="detailBar">상품후기<span class="reviewCnt">${requestScope.review_cnt}</span></a></li>
				<li class="inquire"><a href="#goinquire" class="detailBar">문의하기</a></li>	
			</ul>
			
			<div id="inquire" class="inquireBoard" style="height: auto;">
				
				<span style="font-size: 15pt; font-weight:bold">Q&A</span>
				<span style="font-size: 12pt; margin-left: 1%;">구매하시려는 상품에 대해 궁금한 점이 있으면 문의주시기 바랍니다.</span>
				
				
				<%-- 문의 하기 있는 경우 --%>
				<c:if test="${not empty requestScope.prd_qnaList}">	
				
					<hr style="border: none; border-top: 1px solid black; margin-top: 1%;"> <%-- 문의후기 있을 때 검정 실선 --%>
					
					<table class="table table-borderless">
						<colgroup> <%-- 테이블 간 간격 설정 --%>
						<col style="width: 10%;">
						<col style="width: 60%;">
						<col style="width: 10%;">
						<col style="width: 20%;">
					   	<colgroup>
				        <thead>
					        <tr class="qnaInfoTitle">
						        <th scope="col">번호</th>
						        <th scope="col">제목</th>
						        <th scope="col">작성자</th>
						        <th scope="col">작성날짜</th>
					        </tr>
				        </thead>
				        <tbody>
				        	<c:forEach var="prd_qna" items="${requestScope.prd_qnaList}" varStatus="status">
					        	<tr class="qnaInfo" onclick="goQna('${prd_qna.qna_no}')">
					        		<fmt:parseNumber var="currentShowPageNo" value="${requestScope.currentShowPageNo}" /> <%-- fmt:parseNumber 은 문자열을 숫자형식으로 형변환 시키는 것이다. --%>
					        		<td>${(requestScope.totalQnaCount) - (currentShowPageNo - 1) * 10 - (status.index)}</td> <%-- 10개씩 보여줌 --%>
					        		<td>${prd_qna.qna_title}</td>
					        		<td>
					        		<c:choose>
								        <c:when test="${fn:length(prd_qna.userid) > 3}">
								            ${fn:substring(prd_qna.userid, 0, fn:length(prd_qna.userid) - 3)}*** <!-- 뒤 3글자 마스킹 -->
								        </c:when>
								        <c:otherwise>
								            ${prd_qna.userid} <!-- 3자 이하일 경우 그대로 출력 -->
								        </c:otherwise>
							    	</c:choose>					    
					        		</td>
					        		<td>${prd_qna.qna_regidate}</td>
					        	</tr>
				        	</c:forEach>
				        </tbody>
				     </table>   
			    </c:if>
			        
			    <%-- 문의 하기 없는 경우 --%>
				<c:if test="${empty requestScope.prd_qnaList}">	
					<hr style="border: none; border-top: 1px solid #DADADA; margin-top: 1%;">  <%-- 문의후기 없을 때 회색실선 --%>
					
					<p style="font-size: 10.5pt; color: #555555; padding: 6% 0;">
						게시물이 없습니다.
					</p>
				</c:if>
			        

				<hr style="border: none; border-top: 1px solid #DADADA; margin-top: 1%;"> <%-- 문의후기 회색실선2 --%>
				
				<%-- 상품문의 하기 / 모두 보기 --%>
				<div style="display: flex; margin-top: 0.5%; justify-content: right;">
					<div class="inquireBtn" style="width: 9%; height: 45px; margin-right: 1%; display: flex; justify-content: center; align-items: center;"> 
						<span>상품문의 하기</span>
					</div>
					<div class="inquireAllsee" style="width: 9%; display: flex; justify-content: center; align-items: center;">
						<span>모두보기</span>
					</div>
				</div>
		
			</div>					
		
		</div>
	
	</div>
	<%-- 상세정보 / 이용안내 / 상품후기 / 문의하기 끝 --%>

</div>


<jsp:include page="../common/footer.jsp"></jsp:include>