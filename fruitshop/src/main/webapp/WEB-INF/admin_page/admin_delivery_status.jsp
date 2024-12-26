<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<link rel="stylesheet" href="<%= request.getContextPath()%>/css/adminpage/orderManagement.css">
<script type="text/javascript" src="<%= request.getContextPath()%>/js/admin/orderManagement.js"></script>


<script type="text/javascript">


</script>

<div id="container">
	
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
			<li>기본적으로 최근 3개월간의 자료가 조회됩니다.</li>
			<li>주문번호를 클릭하시면 해당 주문에 대한 상세내역을 확인 및 주문상태를 수정할 수 있습니다.</li>
		</ul>
	</div>
	<%-- 기간 필터 끝 --%>
	
	<%-- 주문 상품 내역 보여주기 시작 --%>
	<div style="margin-left: 1%;" class="h6 mt-5">
		<span>주문 상품 정보</span> 
		<select style="float: right;" name="ship_status">
			<option value="">선택</option>			
			<option value="1">주문완료(배송준비중)</option>			
			<option value="2">배송중</option>			
			<option value="3">배송완료</option>
		</select>
	</div>
	<hr style="border: solid 1px black;">	
	<div>
		<table id="orderList" class="table table-hover text-center">
			<thead>
				<tr>
					<th>주문번호</th>
					<th>주문일자</th>
					<th>주문자</th>
					<th style="width: 35%">상품명</th>
					<th>결제금액</th>
					<th>주문처리상태</th>
				</tr>
			</thead>
			<tbody>
			<%-- 상품리스트 반복문 들어와야 함 --%>
				<tr class="order_row" data-toggle="modal" data-target="#userShipInfo" data-dismiss="modal">
					<td class="order_no">12321</td>
					<td class="order_date">2024-12-26</td>
					<td class="name">이원모</td>
					<td class="prod_name">딸기 5kg</td>
					<td class="order_tprice">51,000</td>
					<td class="order_status">주문완료(배송준비중)<input type="hidden" class="ship_status" value="1" /></td>
				</tr>
				<tr class="order_row" data-toggle="modal" data-target="#userShipInfo" data-dismiss="modal">
					<td class="order_no">12321321</td>
					<td class="order_date">2025-12-26</td>
					<td class="name">이원모</td>
					<td class="prod_name">포도 5kg</td>
					<td class="order_tprice">61,000</td>
					<td class="order_status">배송중<input type="hidden" class="ship_status" value="2" /></td>
				</tr>
				<%-- 상품리스트 반복문 들어와야 함 --%>
			</tbody>
		</table>
	</div>
	<%-- 주문 상품 내역 보여주기 끝 --%>
	
	
	<%-- 주문상태변경 모달 시작 --%>
	<div class="modal fade" id="orderDetailInfo"> <%-- 만약에 모달이 안보이거나 뒤로 가버릴 경우에는 모달의 class 에서 fade 를 뺀 class="modal" 로 하고서 해당 모달의 css 에서 zindex 값을 1050; 으로 주면 된다. --%>  
		<div class="modal-dialog modal-dialog-centered modal-lg">
	  		<div class="modal-content">
	  
	    		<!-- Modal header -->
				<div class="modal-header">
	  				<h4 class="modal-title text-center">주문상태변경</h4>
	  				<button type="button" class="close userShipInfo" data-dismiss="modal">&times;</button>
				</div>
	
				<!-- Modal body -->
				<div class="modal-body">
	 				<div id="orderInfo">
		     			<form name="orderInfoUpdate">
		     				
		     				<label class="text-left" style="margin: 2% 0.6% 0 1%; width: 30%;">주문번호 &nbsp;</label><input id="order_no" style="width:60%;" type="text" name="order_no" readonly/>
		     				<label class="text-left" style="margin: 2% 0.6% 0 1%; width: 30%;">주문자 &nbsp;</label><input id="name" style="width:60%;" type="text" name="name" readonly/>
		     				<label class="text-left" style="margin: 2% 0.6% 0 1%; width: 30%;">상품명 &nbsp;</label><input id="prod_name" style="width:60%;" type="text" name="prod_name" readonly/>
		     				<label class="text-left" style="margin: 2% 0.6% 0 1%; width: 30%;">주문금액 &nbsp;</label><input id="order_tprice" style="width:60%;" type="text" name="order_tprice" readonly/>
		     				<label class="text-left" style="margin: 2% 0.6% 0 1%; width: 30%;">주문일자 &nbsp;</label><input id="order_date" style="width:60%;" type="text" name="order_date" readonly/>
		     				<label class="text-left" style="margin-top: 2%; margin-left: 1%; width: 30%;">주문상태 &nbsp;</label>
		     				<select id="modal_ship_status" style="width:60%;" name="ship_status">
								<option value="">선택</option>			
								<option value="1">주문완료(배송준비중)</option>			
								<option value="2">배송중</option>			
								<option value="3">배송완료</option>
							</select>
							
							<label class="text-left" style="margin-top: 2%; margin-left: 1.1%; width: 30%;">주소 &nbsp;</label>
							<input style="width:15%" type="text" name="postcode" id="postcode" size="6" maxlength="5" placeholder="우편번호" readonly/>							
							<input style="margin-top: 0.75%; margin-left: 31.6%; width: 60%;" name="address" id="address" size="40" maxlength="200" placeholder="주소" readonly/>
							<input style="margin-top: 1.25%; margin-left: 31.6%; width: 28.85%;" type="text" name="detailaddress" id="detailAddress" size="40" maxlength="200" placeholder="상세주소" readonly />
							<input style="margin-left: 2%; width: 28.5%;" type="text" name="extraaddress" id="extraAddress" size="40" maxlength="200" readonly />
							
		     			</form>
		     			
	  				</div>
				</div>
	
				<!-- Modal footer -->
	      		<div class="modal-footer text-center">
	      			<div style="width:100%; margin: 0 auto;">
		        		<button style="margin-right: 10%;" type="button" class="btn btn-success modalFooter">변경</button>
		        		<button type="button" class="btn btn-danger modalFooter" data-dismiss="modal">취소</button>
	        		</div>
	      		</div>
	    	</div>
		</div>
	</div>
	<%-- 주문상태변경 선택 모달 끝 --%>
	
	
	
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
	<div style="margin-top: 5%; display: flex;">
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