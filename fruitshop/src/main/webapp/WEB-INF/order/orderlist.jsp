<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%-- Custom CSS --%>
<link rel="stylesheet" href="<%= request.getContextPath()%>/css/order/orderlist.css">

<jsp:include page="../common/header.jsp"></jsp:include>

<script type="text/javascript" src="<%= request.getContextPath()%>/js/order/orderlist.js"></script>

<script type="text/javascript">
	$(document).ready(() => {
		
		$("div.order_title").click(e => {
			// alert("야야호"+ $(e.target).index());
			$("div.order_title").removeClass("active");
			$(e.target).addClass("active");
		});// end of $("div.order_title").click(e => {}) --------------
		
		/*
		// === 필터 버튼 조회 시 이벤트 처리 시작 === //
		$("button.btn-secondary").on("click", () => {
			// const fromDate = $("input#fromDate").val();
			// const toDate = $("input#toDate").val();
			const fromDate = document.querySelector("input#fromDate").value;
			const toDate = document.querySelector("input#toDate").value;
			
			// alert("시작일:"+ fromDate +"\n마지막일: "+ toDate);
			
			if (fromDate > toDate) {
				alert("시작일은 마지막일보다 이후여야 합니다.");
				return;
			}
			else {
				// DB 에서 조회 해와야 함 (ajax 통신 사용할 것)
				
				$.ajax({
					url: "<%= request.getContextPath()%>/order/orderList.ddg",
					data: {"fromDate": fromDate, "toDate": toDate},
					type: "GET",
					dataType: "JSON",
					success: function(json) {
						console.log("결과확인용: ",json);
						
						let html = ``;
						
						if (json.length == 0) {
							html += `
								<tr>
									<td colspan="7">주문하신 상품이 존재하지 않습니다.</td>
								</tr>
							`;	
							$("table#orderList > tbody").html(html);
						}
						else {
							
							$.each(json, function(index, item) {
								console.log("item => ", item);
								html += `
									<tr>
										<td>${item.order_no}</td>
										<td>${item.order_date}</td>
										<td>"${pageContext.request.contextPath}/images/product/thumnail/${item.prod_thumnail}"</td>
										<td>${item.prod_name}</td>
										<td>${item.order_tprice}</td>
										<td>${item.order_status}</td>
										<td></td>
									</tr>
								`;
							});// end of $.each(json, function(index, item) {}) -------------------							
							$("table#orderList > tbody").append(html);
						}
						
						
						
					},
					error: function(request, status, error){
	               		alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
	               		// alert("경로를 어디로 가야함???");
	               	}
				});
			}
		});// end of $("button.btn-secondary").on("click", () => {}) -----------------------
		// === 필터 버튼 조회 시 이벤트 처리 끝 === //
		
		setTimeout(() => {
			$("button.btn-secondary").trigger("click");	
		}, 300);
		*/
	});// end of $(document).ready(() => {}) -------------------------
	
</script>

<jsp:include page="../mypage/mypage_list.jsp"></jsp:include>

<div style="margin-top: 3%;" id="container">

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
			<button type="button" class="btn btn-outline-dark" id="today">오늘</button>
			<button type="button" class="btn btn-outline-dark" id="week">일주일</button>
			<button type="button" class="btn btn-outline-dark" id="month">1개월</button>
			<button type="button" class="btn btn-outline-dark" id="3month">3개월</button>
			<button type="button" class="btn btn-outline-dark" id="6month">6개월</button>
		</div>
		
		<div style="margin-top: 1%; padding: 1%;">
			<form name="dateFilter">
				<input style="width: 120px; height: 40px; text-align: center;" type="text" name="fromDate" id="fromDate" maxlength="10" />
				<label style="cursor:pointer" for="fromDate"><img src="<%= request.getContextPath() %>/images/order/calendar.png" /></label>
				&nbsp;~&nbsp;&nbsp;
				<input style="width: 120px; height: 40px; text-align: center;" type="text" name="toDate" id="toDate" maxlength="10" />
				<label style="cursor:pointer" for="toDate"><img src="<%= request.getContextPath() %>/images/order/calendar.png" /></label>
			</form>
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
	<div style="margin-left: 1%; font-family: 'Noto Sans KR', sans-serif; font-weight: 400;" class="h6 mt-5">주문 상품 정보</div>
	<hr style="border: solid 1px black;">	
	<div>
		<table id="orderList" class="table table-hover text-center">
			<thead>
				<tr>
					<th>주문번호</th>
					<th>주문일자</th>
					<th>이미지</th>
					<th style="padding:1%; width: 30%">상품명</th>
					<th>주문금액</th>
					<th>주문처리상태</th>
					<th>취소/교환/반품</th>
				</tr>
			</thead>
			<tbody>
			<%-- 상품리스트 반복문 들어와야 함 --%>
				<tr>
					<%--
					<td>123</td>
					<td>2024-12-29</td>
					<td>이미지</td>
					<td>딸기 5kg</td>
					<td>51,000</td>
					<td>주문완료</td>
					<td></td>
					 --%>
				</tr>
				<%-- 상품리스트 반복문 들어와야 함 --%>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="3"></td>
					<td><button id="btnMore" type="button" class="btn btn-primary" value="">더보기</button></td>
					<td colspan="3"></td>
				</tr>
			</tfoot>
		</table>
	</div>
	<%-- 주문 상품 내역 보여주기 끝 --%>
	
	<input type="hidden" id="contextPath" value="<%= request.getContextPath()%>"/>
	<input type="hidden" id="countOrder" />
	<input type="hidden" id="totalOrderCount" />
</div>




<jsp:include page="../common/footer.jsp"></jsp:include>