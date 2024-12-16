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

<div style="border: solid 1px red; margin-top: 2%;" id="container">

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
	
	<%-- 기간 필터 시작 --%>
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
	<div id="filter_desc">
		<ul>
			<li>기본적으로 최근 3개월간의 자료가 조회되며, 기간 검색시 주문처리완료 후 36개월 이내의 주문내역을 조회하실 수 있습니다.</li>
			<li>완료 후 36개월 이상 경과한 주문은 [과거주문내역]에서 확인할 수 있습니다.</li>
			<li>주문번호를 클릭하시면 해당 주문에 대한 상세내역을 확인하실 수 있습니다.</li>
		</ul>
	</div>
	<%-- 기간 필터 끝 --%>
	
	<%-- 주문 상품 내역 보여주기 시작 --%>
	<div style="margin-left: 1%;" class="h6">주문 상품 정보</div>
	<hr style="border: solid 1px black;">	
	<div>
		<table class="table table-hover text-center">
			<thead>
				<tr>
					<th>주문번호</th>
					<th>이미지<br></th>
					<th style="padding:1%; width: 30%">상품명</th>
					<th>수량</th>
					<th>가격</th>
					<th>주문처리상태</th>
					<th>취소/교환/반품</th>
				</tr>
			</thead>
			<tbody>
			<%-- 상품리스트 반복문 들어와야 함 --%>
				<tr>
					<td>123</td>
					<td>이미지</td>
					<td>딸기 5kg</td>
					<td>1</td>
					<td>51,000</td>
					<td>주문완료</td>
					<td></td>
				</tr>
				<tr>
					<td>123</td>
					<td>이미지</td>
					<td>딸기 5kg</td>
					<td>1</td>
					<td>51,000</td>
					<td>주문완료</td>
					<td></td>
				</tr>
				<tr>
					<td>123</td>
					<td>이미지</td>
					<td>딸기 5kg</td>
					<td>1</td>
					<td>51,000</td>
					<td>주문완료</td>
					<td></td>
				</tr>
				<tr>
					<td>123</td>
					<td>이미지</td>
					<td>딸기 5kg</td>
					<td>1</td>
					<td>51,000</td>
					<td>주문완료</td>
					<td></td>
				</tr>
				<%-- 상품리스트 반복문 들어와야 함 --%>
			</tbody>
		</table>
	</div>
	<%-- 주문 상품 내역 보여주기 끝 --%>
	
	<%-- 페이징 처리 부분 --%>
	<%--
	<nav aria-label="Page navigation example">
		<ul class="pagination justify-content-center">
			<li class="page-item">
				<a class="page-link" href="#" tabindex="-1">Previous</a>
			</li>
			<li class="page-item active"><a class="page-link" href="#">1</a></li>
			<li class="page-item"><a class="page-link" href="#">2</a></li>
			<li class="page-item"><a class="page-link" href="#">3</a></li>
			<li class="page-item">
				<a class="page-link" href="#">Next</a>
			</li>
		</ul>
	</nav>
	--%>
	<div style="margin-top: 3%; display: flex;">
		<div class="pagination">
			<a href="#">&laquo;</a>
			<a href="#">&lsaquo;</a>
			<%-- 페이지 수만큼 반복문 --%>
			<a href="#">1</a>
			<a href="#" class="active">2</a>
			<a href="#">3</a>
			<a href="#">4</a>
			<a href="#">5</a>
			<%-- 페이지 수만큼 반복문 --%>
			<a href="#">&rsaquo;</a>
			<a href="#">&raquo;</a>
		</div>
	</div>


	<%-- 페이징 처리 부분 --%>
</div>




<jsp:include page="../common/footer.jsp"></jsp:include>