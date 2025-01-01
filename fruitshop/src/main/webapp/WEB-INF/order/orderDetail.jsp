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
	
	$("table#orderList tr.productRow").css({"cursor": "pointer"}).click(function(e) {
		const prod_no = $(e.target).parent().find("#prod_no").val();
		const index   = $("table#orderList tr.productRow td").index($(e.target));
		const length  = $("table#orderList tr.productRow td").length
		
		// console.log("확인용 prod_no: "+ prod_no);
		// console.log("확인용 length: "+ $("table#orderList tr.productRow td").length);
		// console.log("확인용 index: "+ $("table#orderList tr.productRow td").index($(e.target)));
		// alert("확인용 prod_no"+ prod_no);
		
		if (index == length-1) {
			// 상품후기작성으로 보내기
			location.href = `${pageContext.request.contextPath}`;
		}
		else {
			// 상품 상세페이지로 보내기
			location.href = `${pageContext.request.contextPath}/product/productDetail.ddg?prodNo=`+prod_no;
		}
		
	});// end of $("table#orderList tr.productRow").css({"cursor": "pointer"}).click(function(e) {}) --------
	

	$("table#orderList tr.productRow td:last-child").addClass("btn");
	$("table#orderList tr.productRow td:last-child").addClass("btn-sm");
	$("table#orderList tr.productRow td:last-child").addClass("btn-success");
	
	$("table#orderList tr.productRow td:last-child").hover(function(){
		// mouseover
		
	}, function() {
		// mouserout
	});
	
});// end of $(document).ready(()=> {}) --------------------------- 


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
				<span style="font-wieght: 500; color: black;">주문일시</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<span>${requestScope.orderDetail.order_date}</span>
			</div>
			<div>
				<span style="font-wieght: 500; color: black;">주문번호</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
							<th></th>
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
								<td>${orderItem.prod_name}</td>
								<td class="prod_count">${orderItem.ordetail_count}개</td>
								<td class="prod_price"><fmt:formatNumber value="${orderItem.ordetail_price}" pattern="#,###" />원</td>
								<td class="prod_shipStatus">
									<c:if test="${orderItem.ship_status == 1}">배송준비중</c:if>	
									<c:if test="${orderItem.ship_status == 2}">배송중</c:if>	
									<c:if test="${orderItem.ship_status == 3}">배송완료</c:if>	
								</td>
								<td>후기작성</td>
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
            	<span>주문상품</span><span id="total_oprice" style="float: right"><fmt:formatNumber value="${orderPrice - shipPirce}" pattern="#,###" />원</span><br><br>
            	<span>배송비</span><span style="float: right">2,500원</span><br>
            	<c:if test="${requestScope.orderDetail.order_dicount != 0}">
            		<br><span>할인액</span><span id="discount" style="float: right">${requestScope.orderDetail.order_dicount}원</span><br>
            	</c:if>
            	
            </div>
            <%-- <hr style="border: solid 0.3px black;">--%>
            <hr>
            <%-- 결제 정보 확인 끝 --%>
            
            
            <div style="width: 90%; margin: 0 auto;">
            	<span>총결제액</span><span class="total_price" style="float: right"><fmt:formatNumber value="${requestScope.orderDetail.order_tprice}" pattern="#,###" />원</span><br><br>
            	<span>적립금</span><span id="point" style="float: right"><fmt:formatNumber value="${(orderPrice - shipPirce)*0.01}" pattern="#,###" />원</span>
            </div>
            
            <div style="width: 50%; margin: 5% auto 2% auto; display: flex;">
	            <button type="button" class="btn btn-light requestbtn">교환/반품</button>
	            <button style="margin-left: auto;" type="button" class="btn btn-light requestbtn">구매확정</button>
			</div>
	</div>
	<%-- 주문정보 확인 및 결제 끝 --%>
</div>


<jsp:include page="../common/footer.jsp"></jsp:include>