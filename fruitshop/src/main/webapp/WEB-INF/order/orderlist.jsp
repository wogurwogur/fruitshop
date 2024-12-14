<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- Custom CSS --%>
<link rel="stylesheet" href="<%= request.getContextPath()%>/css/order/orderlist.css">

<jsp:include page="../common/header.jsp"></jsp:include>

<script type="text/javascript" src="<%= request.getContextPath()%>/js/order/orderlist.js"></script>

<script type="text/javascript">
	$(document).ready(() => {
		$("a.ordermenu").click(e => {
			// alert("야야호"+ $(e.target).index());
			$("a.ordermenu").removeClass("active");
			$(e.target).addClass("active");
		});
		
		
		$("div.order_title").click(e => {
			// alert("야야호"+ $(e.target).index());
			$("div.order_title").removeClass("active");
			$(e.target).addClass("active");
		});
	});
</script>

<div style="border: solid 1px red; margin-top: 2%;" class="container-fluid">

<%-- 페이지 도착 시 기본 조회 기간은 3개월 --%>
	<%-- 주문내역조회 메뉴 바 시작 --%>
	<div id="order_filter">
	
		<div class="order_title active">
			주문내역조회(0)
		</div>
		<div class="order_title">
			취소/반품/교환 내역(0)
		</div>
		<div class="order_title">
			과거주문내역(0)
		</div>
		<div id="order_title_nbsp">
		</div>
	</div>
	<%-- 주문내역조회 메뉴 바 끝 --%>
	
	<div id="order_time">
		<div style="padding: 2%;" class="btn-group" role="group" aria-label="Date Select Filter">
			<button type="button" class="btn btn-outline-dark">오늘</button>
			<button type="button" class="btn btn-outline-dark">일주일</button>
			<button type="button" class="btn btn-outline-dark">1개월</button>
			<button type="button" class="btn btn-outline-dark">3개월</button>
			<button type="button" class="btn btn-outline-dark">6개월</button>
		</div>
		
		<div style="margin-top: 1%; padding: 1%;">
			<%--<form name="dateFilter"> --%>
				<input style="width: 120px; height: 40px; text-align: center;" type="text" name="fromDate" id="fromDate" maxlength="10" />
				<label style="cursor:pointer" for="fromDate"><img src="<%= request.getContextPath() %>/images/order/calendar.png" /></label>
				&nbsp;~&nbsp;&nbsp;
				<input style="width: 120px; height: 40px; text-align: center;" type="text" name="toDate" id="toDate" maxlength="10" />
				<label style="cursor:pointer" for="toDate"><img src="<%= request.getContextPath() %>/images/order/calendar.png" /></label>
			<%-- </form>--%>
		</div>
		
		<div style="margin-top: 2%;">
			<button style="width: 80px; height: 40px;" type="button" class="btn btn-secondary">조회</button>
		</div>
	</div>
	
	
</div>




<jsp:include page="../common/footer.jsp"></jsp:include>