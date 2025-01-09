<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="../common/header.jsp"></jsp:include>
<%-- Custom CSS --%>
<link rel="stylesheet" href="<%= request.getContextPath()%>/css/order/orderDetail.css">

<script type="text/javascript">

$(document).ready(()=> {
	
	$("table#orderList tr.productRow").css({"cursor": "pointer"});
	
	$("table#orderList td.prod_name").click(function() {
		const prod_no = $(this).parent().find("#prod_no").val();
		location.href=`${pageContext.request.contextPath}/product/productDetail.ddg?prodNo=\${prod_no}`;
	});// end of $("table#orderList td").click(function() {}) -----------------
	

	$("button#orderCommit").click(e => {
		// 구매확정 시 주문 테이블의 주문 상태값을 변경한다.
		$("div#commitConfirm").modal();
	});// end of $("button#orderCommit").click(e => {}) ----------------
	
	$("button#orderCancel").click(() => {
		$("div#cancelConfirm").modal();
	});// end of $("button#orderCancel").click(() => {}) -----------------
	
});// end of $(document).ready(()=> {}) --------------------------- 


// 구매후기로 가는 함수
function goReview(prod_no) {
	//alert("주문번호:"+ prod_no);
	location.href=`${pageContext.request.contextPath}/community/productCarrier.ddg?prod_no=\${prod_no}`;
}// end of function goReview() ------------------- 



// 구매확정하는 함수
function goCommit() {
	$.ajax({
		url: "${pageContext.request.contextPath}/order/orderCommit.ddg",
		data: {"order_no": "${requestScope.orderDetail.order_no}", "user_no": "${requestScope.orderDetail.user_no}"},
		type: "POST",
		dataType: "JSON",
		async: true,
		success: function(json) {
			alert("구매확정을 완료했습니다.");
			
			if (json.isComplete) {
				location.href = `orderList.ddg`;
			}
			
		},
		error: function(request, status, error){
       		alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
       		// alert("경로를 어디로 가야함???");
       	}
	});
}// end of function goCommit() -------------------


// 교환/반품 신청하는 함수
function goCancel() {
	$.ajax({
		url: "${pageContext.request.contextPath}/order/orderCancel.ddg",
		data: {"order_no": "${requestScope.orderDetail.order_no}", "user_no": "${requestScope.orderDetail.user_no}"},
		type: "POST",
		dataType: "JSON",
		async: true,
		success: function(json) {
			alert("교환/반품 신청을 완료했습니다.");
			
			if (json.isComplete) {
				location.href = `orderList.ddg`;				
			}
			
		},
		error: function(request, status, error){
       		alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
       		// alert("경로를 어디로 가야함???");
       	}
	});
}// end of function goCancel() -------------------

</script>


<div id="container">
	<%-- 주문/결제 타이틀 --%>
	<div style="background-color: black; color: white;" class="text-center">
		<span id="order_title">주문상세내역</span>
	</div>

	<%-- 주문번호 시작 --%>
	<div id="orderNumberInfo">
		<div id="orderNumber">
			<div>
				<span style="font-weight: 500; color: black;">주문일시</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<span>${requestScope.orderDetail.order_date}</span>
			</div>
			<div>
				<span style="font-weight: 500; color: black;">주문번호</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<span>${requestScope.orderDetail.order_no}</span>
			</div>
		</div>
	</div>
	<%-- 주문번호 끝 --%>
	
	
	
	<%-- 주문정보 확인 및 결제 시작 --%>
	<div id="orderInfo_place">
            <%-- 주문정보 확인 시작 --%>
            <div class="h6 mt-5 orderInfo_title">주문 상품 정보</div>
			<hr style="border: solid 1px black;">
            <div>
				<table id="orderList" class="table table-border text-center">
					<thead>
						<tr>
							<th>이미지</th>
							<th style="width: 50%">상품명</th>
							<th>수량</th>
							<th>가격</th>
							<th>배송상태</th>
							<th>후기작성</th>
						</tr>
					</thead>
					<tbody>					
						<%-- 상품리스트 반복문 --%>
						<c:forEach var="orderItem" items="${requestScope.orderDetailList}" varStatus="status">
							<tr class="productRow">
								<td>
									<input type="hidden" id="prod_no" name="prod_no" value="${orderItem.fk_prod_no}" />
									<img style= "width: 50px; heigth: 30px;" src="<%= request.getContextPath()%>/images/product/thumnail/${orderItem.prod_thumnail}">
								</td>
								<td class="prod_name">${orderItem.prod_name}</td>
								<td class="prod_count">${orderItem.ordetail_count}개</td>
								<td class="prod_price"><fmt:formatNumber value="${orderItem.ordetail_price}" pattern="#,###" />원</td>
								<td class="prod_shipStatus">
									<c:if test="${orderItem.ship_status == 1}">배송준비중</c:if>	
									<c:if test="${orderItem.ship_status == 2}">배송중</c:if>	
									<c:if test="${orderItem.ship_status == 3}">배송완료</c:if>	
								</td>
								<td>
									<button type="button" class="btn btn-success btn-sm" onclick="goReview('${orderItem.fk_prod_no}')">후기작성</button>  
								</td>
							</tr>							
						</c:forEach>
						<%-- 상품리스트 반복문 --%>
					</tbody>
				</table>
			</div>
            <%-- 주문정보 확인 끝 --%>
            
            
            <%-- 배송지 정보 시작 --%>
            <div class="h6 mt-5 orderInfo_title">배송지</div>
			<hr style="border: solid 1px black;">
            <div id="shipInfo" style="width: 90%; margin: 0 auto;">
            	<span id="receiver">${requestScope.orderDetail.order_receiver}</span>
            	<span id="tel">${fn:substring(requestScope.orderDetail.order_receivertel,0,3)}-${fn:substring(requestScope.orderDetail.order_receivertel,3,7)}-${fn:substring(requestScope.orderDetail.order_receivertel,7,11)}</span>
            	<span id="address">${requestScope.orderDetail.order_address}${requestScope.orderDetail.order_extraadress}&nbsp;${requestScope.orderDetail.order_detailaddress}&nbsp;(${requestScope.orderDetail.order_postcode})</span>
            </div>
            <%-- 배송지 정보 끝 --%>
            
            
            
            <%-- 결제 정보 확인 시작 --%>
            <div class="h6 mt-5 orderInfo_title">결제 정보</div>
			<hr style="border: solid 1px black;">
            <div style="width: 90%; margin: 0 auto;">
            	<fmt:parseNumber var="orderPrice" type="number" value="${requestScope.orderDetail.order_tprice}" />
            	<fmt:parseNumber var="shipPirce" type="number" value="2500" />
            	
            	<span>주문상품</span><span id="total_oprice" style="float: right">
            	<c:if test="${(orderPrice - shipPirce) < 0}">0</c:if>
            	<c:if test="${(orderPrice - shipPirce) > 0}"><fmt:formatNumber value="${orderPrice - shipPirce}" pattern="#,###" /></c:if>
            	 원</span><br><br>
            	
            	
            	<span>배송비</span><span style="float: right">2,500 원</span><br>
            	<c:if test="${requestScope.orderDetail.order_dicount != 0}">
            		<br><span>할인액</span><span id="discount" style="float: right"><fmt:formatNumber value="${requestScope.orderDetail.order_dicount}" pattern="#,###" /> 원</span><br>
            	</c:if>
            	
            </div>
            <%-- <hr style="border: solid 0.3px black;">--%>
            <hr>
            <%-- 결제 정보 확인 끝 --%>
            
            
            <div style="width: 90%; margin: 0 auto;">
            	<span>총결제액</span><span class="total_price" style="float: right"><fmt:formatNumber value="${requestScope.orderDetail.order_tprice}" pattern="#,###" /> 원</span><br><br>
            	<span>적립금</span><span id="point" style="float: right">
            	<c:if test="${(orderPrice - shipPirce) < 0}">0</c:if>
            	<c:if test="${(orderPrice - shipPirce) > 0}"><fmt:formatNumber value="${(orderPrice - shipPirce)*0.01}" pattern="#,###" /></c:if> 원</span>
            </div>
            
            <c:choose>
	   	        <c:when test="${requestScope.orderDetail.order_status != 5}">
	    	        <div style="width: 50%; margin: 5% auto 2% auto; display: flex;">
			            <button id="orderCancel" type="button" class="btn btn-light requestbtn">교환/반품</button>
			            <button id="orderCommit" style="margin-left: auto;" type="button" class="btn btn-light requestbtn">구매확정</button>
					</div>
				</c:when>
				<c:otherwise>
					<div style="margin-top: 3% ;"></div>
				</c:otherwise>
			</c:choose>
			
	</div>
	<%-- 주문정보 확인 및 결제 끝 --%>
	
	<%-- 확인여부 모달 시작 --%>
	<div class="modal fade" id="commitConfirm"> <%-- 만약에 모달이 안보이거나 뒤로 가버릴 경우에는 모달의 class 에서 fade 를 뺀 class="modal" 로 하고서 해당 모달의 css 에서 zindex 값을 1050; 으로 주면 된다. --%>  
		<div class="modal-dialog modal-dialog-centered modal-sm">
	  		<div class="modal-content">
	  
	    		<!-- Modal header -->
				<div class="modal-header">
	  				<h4 class="modal-title text-center"></h4>
	  				<button type="button" class="close #updateConfirm" data-dismiss="modal">&times;</button>
				</div>
	
				<!-- Modal body -->
				<div class="modal-body">
	 				<div class="text-center">
	 					구매확정을 하시겠습니까?
	  				</div>
				</div>
	
				<!-- Modal footer -->
	      		<div class="modal-footer text-center">
	      			<div style="width:100%; margin: 0 auto;">
		        		<button style="margin-right: 10%;" type="button" class="btn btn-success modalFooter" onclick="goCommit()">확인</button>
		        		<button type="button" class="btn btn-danger modalFooter" data-dismiss="modal">취소</button>
	        		</div>
	      		</div>
	    	</div>
		</div>
	</div>
	<%-- 확인여부 모달 시작 --%>
	
	<%-- 교환/반품신청 모달 시작 --%>
	<div class="modal fade" id="cancelConfirm"> <%-- 만약에 모달이 안보이거나 뒤로 가버릴 경우에는 모달의 class 에서 fade 를 뺀 class="modal" 로 하고서 해당 모달의 css 에서 zindex 값을 1050; 으로 주면 된다. --%>  
		<div class="modal-dialog modal-dialog-centered modal-sm">
	  		<div class="modal-content">
	  
	    		<!-- Modal header 
				<div class="modal-header">
	  				<h4 class="modal-title text-center">교환 / 반품 신청</h4>
	  				<button type="button" class="close #updateConfirm" data-dismiss="modal">&times;</button>
				</div>-->
	
				<!-- Modal body -->
				<div class="modal-body">
	 				<div class="text-center">
	 					교환 / 반품 을 신청하시겠습니까?
	  				</div>
				</div>
	
				<!-- Modal footer -->
	      		<div class="modal-footer text-center">
	      			<div style="width:100%; margin: 0 auto;">
		        		<button style="margin-right: 10%;" type="button" class="btn btn-success modalFooter" onclick="goCancel()">확인</button>
		        		<button type="button" class="btn btn-danger modalFooter" data-dismiss="modal">취소</button>
	        		</div>
	      		</div>
	    	</div>
		</div>
	</div>
	<%-- 교환/반품신청 모달 시작 --%>
	
	
</div>

<jsp:include page="../common/footer.jsp"></jsp:include>