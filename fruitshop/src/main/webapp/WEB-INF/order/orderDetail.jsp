<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../common/header.jsp"></jsp:include>

<c:forEach var="orderItem" items="${requestScope.orderDetailList}" varStatus="status">
	${orderItem.order_no}<br>
	${orderItem.fk_user_no}<br>
	${orderItem.order_request}<br>
	${orderItem.order_date}<br>
	${orderItem.order_postcode}<br>
	${orderItem.order_address}<br>
	${orderItem.order_detailaddress}<br>
	${orderItem.order_extraadress}<br>
	${orderItem.order_receiver}<br>
	${orderItem.order_receivertel}<br>
	${orderItem.ordetail_count}<br>
	${orderItem.ordetail_price}<br>
	${orderItem.ship_status}<br>
	${orderItem.pay_refund}<br>
</c:forEach>

<jsp:include page="../common/footer.jsp"></jsp:include>