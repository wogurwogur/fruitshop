<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.Calendar" %>

<%
	Calendar time = Calendar.getInstance();
	int year = time.get(Calendar.YEAR);
%>

<link rel="stylesheet" href="<%= request.getContextPath()%>/css/adminpage/statistics.css">
<script type="text/javascript" src="<%= request.getContextPath()%>/js/admin/statistics.js"></script>

<%-- chart.js --%>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<script type="text/javascript">
	$(document).ready(() => {
	
		<%-- 차트 그리기 시작 --%>
		const ctx = document.getElementById('myChart');

		  new Chart(ctx, {
		    type: 'line',
		    data: {
		      labels: ['6개월전', '5개월전', '4개월전', '3개월전', '2개월전', '1개월전', '이번달'],
		      datasets: [{
		        label: '월간 가입자수 추이',
		        data: [12, 19, 3, 5, 2, 3, 30],
		        borderWidth: 1
		      }]
		    },
		    options: {
		      scales: {
		        y: {
		          beginAtZero: true
		        }
		      }
		    }
		  });// end of new Chart(ctx, {}) ---------------------------
	  <%-- 차트 그리기 시작 --%>
		
		
		$("div.order_title").click(e => {
			// alert("야야호"+ $(e.target).index());
			$("div.order_title").removeClass("active");
			$(e.target).addClass("active");
		});
	});// end of $(document).ready(() => {}) -------------------------
	
	function getUserRegister() {
		
		
		
	}// end of function getUserRegister() ------------------------------------
	
	
</script>

<div style="margin-top: 3%;" id="container">

<%-- 페이지 도착 시 기본 조회 기간은 3개월 --%>
	<%-- 주문내역조회 메뉴 바 시작 --%>
	<div id="menu_filter">
	
		<div class="menu_title active">
			회원관련<%-- url 필요 --%>
		</div>
		<div class="menu_title">
			상품관련<%-- url 필요 --%>
		</div>
		<div class="menu_title">
			매출/이익<%-- url 필요 --%>
		</div>
		<div id="menu_title_nbsp">
		</div>
	</div>
	<%-- 주문내역조회 메뉴 바 끝 --%>
	
	<%-- 기간 필터 시작 --%>
	<div id="order_time">
		<div style="padding: 2%;" class="btn-group" role="group" aria-label="Date Select Filter">
			<%-- <button type="button" class="btn btn-outline-dark" id="today">오늘</button>
			<button type="button" class="btn btn-outline-dark" id="week">일주일</button> --%>
			<button type="button" class="btn btn-outline-dark" id="month">1개월</button>
			<button type="button" class="btn btn-outline-dark" id="3month">3개월</button>
			<button type="button" class="btn btn-outline-dark" id="6month">6개월</button>
			<button type="button" class="btn btn-outline-dark" id="year">올해</button>
			<select id="searchYear">
	        	<option value="">연도선택</option>
	         	<option><%= year-1 %></option>
	         	<option><%= year-2 %></option>
	         	<option><%= year-3 %></option>
	      	</select>
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
			<li>기본적으로 최근 3개월간의 자료가 조회되며, 특정 기간을 선택하여 조회할 수 있습니다.</li>
		</ul>
	</div>
	<%-- 기간 필터 끝 --%>
	
	<%-- 통계 본문 보여주기 시작 --%>
	<div style="margin-left: 1%;" class="h6 mt-5">회원 통계 정보</div>
	<hr style="border: solid 1px black;">	
	<div style="display: flex;">
		<div class="" style="width: 50%; border: solid 1px green;">
			<div>
  				<canvas id="myChart"></canvas>
			</div>
		</div>
		<div class="" style="width: 50%; border: solid 1px green;">
			파이 차트
		</div>
	</div>
	<%-- 통계 본문 보여주기 끝 --%>
	
</div>