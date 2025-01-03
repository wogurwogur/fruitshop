<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<link rel="stylesheet" href="<%= request.getContextPath()%>/css/adminpage/orderManagement.css">
<script type="text/javascript" src="<%= request.getContextPath()%>/js/admin/orderManagement.js"></script>


<script type="text/javascript">


$(document).ready(()=> {
	
	$("select[name='searchType']").val("${requestScope.searchType}");
	$("select[name='searchShip']").val("${requestScope.searchShip}");
	$("input:text[name='searchWord']").val("${requestScope.searchWord}");
	
	
	// === 필터 버튼 조회 시 이벤트 처리 시작 === //
	$("button.btn-secondary").on("click", () => {
		const fromDate = $("input#fromDate").val();
		const toDate = $("input#toDate").val();
		
		if (fromDate > toDate) {
			alert("시작일은 마지막일보다 이후여야 합니다.");
			return;
		}
		else {
			// DB 에서 조회 해와야 함 (ajax 통신 사용할 것)
			goSearch();
		}
		
		// ajax 사용하여 where 절에 활용
	});// end of $("button.btn-secondary").on("click", () => {}) ----------------------
	// === 필터 버튼 조회 시 이벤트 처리 끝 === //
	
	$("select[name='searchType']").on("change", function() {
		goSearch();
		
	});// end of $("select[name='sizePerPage']").on("change", function() {}) --------------------
	
	$("select[name='searchShip']").on("change", function() {
		goSearch();
	});// end of $("select[name='searchShip']").on("change", function() {}) --------------
	
	
	$("span#goSearch").click(() => {
		goSearch();
	});// end of $("button#goSearch").click(() => {}) ----------------
	
	
	
	$(document).on("keydown", "input#searchWord", function(e) {
		if (e.keyCode == 13) {
			
			goSearch();
		}
		
	});// end of $("input[name='searchWord']").on("keyup", e => {}) ----------------- 
	
	
});// end of $(document).ready(()=> {}) ------------------

	
	//Function Declaration 
	function goSearch() {
		const searchType = $("select[name='searchType']").val();
		
		const fromDate = document.querySelector("input#fromDate").value;
		const toDate = document.querySelector("input#toDate").value;
		
		// alert("시작일: "+ fromDate+"\n마지막일: "+toDate);
		
		document.querySelector("input[name='fromDate']").value 	= fromDate;
		document.querySelector("input[name='toDate']").value 	= toDate;
		
		const frm  = document.order_searchFrm;
		// frm.action = "memberList.up";	// form 태그에 action 이 명기되지 않았으면 현재보이는 URL 경로로 submit 되어진다.
		// frm.method = "GET";				// form 태그에 method 를 명기하지 않으면 "get" 방식이다.
		frm.submit();
			
	}// end of function goSearch() -------------------
	
</script>

<div id="container">
	
	<%-- 주문내역조회 메뉴 바 시작 --%>
	<div id="order_filter">
	
		<div class="order_title active">
			주문/배송 상태관리
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
				<input style="width: 120px; height: 40px; text-align: center;" type="text" id="fromDate" maxlength="10" />
				<label style="cursor:pointer" for="fromDate"><img src="<%= request.getContextPath() %>/images/order/calendar.png" /></label>
				&nbsp;~&nbsp;&nbsp;
				<input style="width: 120px; height: 40px; text-align: center;" type="text" id="toDate" maxlength="10" />
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
			<li>주문번호를 클릭하시면 해당 주문에 대한 상세내역을 확인 및 주문, 배송상태를 수정할 수 있습니다.</li>
		</ul>
	</div>
	<%-- 기간 필터 끝 --%>
	
	<%-- 주문 상품 내역 보여주기 시작 --%>
	
	<div id="filterMenu" style="margin-left: 1%;" class="h6 mt-5 mb-1">
		<form name="order_searchFrm">
			<span id="bodyTitle">주문 상품 정보</span>
			
			<select style="float: right; height: 30px;" name="searchShip">
				<option value="">배송필터</option>			
				<option value="1">배송준비중</option>			
				<option value="2">배송중</option>			
				<option value="3">배송완료</option>
			</select>
			<select style="margin-right: 0.5%; float: right; height: 30px;" name="searchType">
				<option value="">주문필터</option>			
				<option value="1">주문완료</option>			
				<option value="2">교환/반품</option>			
				<option value="5">구매확정</option>
			</select>
			
			
			<span style="margin: 0 1% 0 0.5%; float: right;" id="goSearch" class="btn btn-secondary btn-sm">검색</span>
			<input style=" height: 30px; float: right;" id="searchWord" type="text" name="searchWord" placeholder="주문번호검색" />
			
			<input type="hidden" name="fromDate" />
			<input type="hidden" name="toDate" />
		</form>
	</div>
	
	<hr style="border: solid 1px black;">
		
	<div>
		<table id="orderList" class="table table-hover text-center">
			<thead>
				<tr>
					<th>주문번호</th>
					<th>주문일시</th>
					<th>주문자</th>
					<th style="width: 35%;">상품명</th>
					<th>결제금액</th>
					<th>주문처리상태</th>
					<th>배송상태</th>
				</tr>
			</thead>
			<tbody>
				<%-- 상품리스트 반복문 들어와야 함 --%>
				<c:if test="${empty requestScope.orderList}">
					<tr>
						<td colspan="7">주문내역이 존재하지 않습니다.</td>
					</tr>
				</c:if>
				
				<c:if test="${!empty requestScope.orderList}">
					<c:forEach var="orderItem" items="${requestScope.orderList}" varStatus="status">
						<tr class="order_row" data-toggle="modal" data-target="#orderDetailInfo" data-dismiss="modal">
							<td class="order_no">${orderItem.order_no}</td>
							<td class="order_date">${orderItem.order_date}</td>
							<td class="name">${orderItem.name}</td>
							<td class="prod_name">${orderItem.prod_name}</td>
							<fmt:parseNumber var="orderPrice" type="number" value="${orderItem.ordetail_price}" />
							<td class="order_price"><fmt:formatNumber value="${orderPrice}" pattern="#,###" />원</td>
							<td>
								<c:if test="${orderItem.order_status == 1}">주문완료<input type="hidden" class="order_status" value="${orderItem.order_status}" /></c:if>
								<c:if test="${orderItem.order_status == 2}">교환/반품<input type="hidden" class="order_status" value="${orderItem.order_status}" /></c:if>
								<c:if test="${orderItem.order_status == 5}">구매확정<input type="hidden" class="order_status" value="${orderItem.order_status}" /></c:if>
							</td>
							<td>
								<c:if test="${orderItem.ship_status == 1}">배송준비중<input type="hidden" class="ship_status" value="${orderItem.ship_status}" /></c:if>
								<c:if test="${orderItem.ship_status == 2}">배송중<input type="hidden" class="ship_status" value="${orderItem.ship_status}" /></c:if>
								<c:if test="${orderItem.ship_status == 3}">배송완료<input type="hidden" class="ship_status" value="${orderItem.ship_status}" /></c:if>
								
								<%-- 상세정보에 들어갈 값 넣어주기 --%>
								<input type="hidden" class="postcode" value="${orderItem.order_postcode}" />
								<input type="hidden" class="address" value="${orderItem.order_address}" />
								<input type="hidden" class="detailAddress" value="${orderItem.order_detailaddress}" />
								<input type="hidden" class="extraAddress" value="${orderItem.order_extraadress}" />
								<input type="hidden" class="order_receiver" value="${orderItem.order_receiver}" />
								<input type="hidden" class="prod_no" value="${orderItem.prod_no}" />
								<%-- 상세정보에 들어갈 값 넣어주기 --%>
							</td>
						</tr>
					</c:forEach>
				</c:if>
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
	  				<button type="button" class="close orderDetailInfo" data-dismiss="modal">&times;</button>
				</div>
	
				<!-- Modal body -->
				<div class="modal-body">
	 				<div id="orderInfo">
		     			<form name="orderInfoUpdate">
		     				
		     				<label class="text-left" style="margin: 2% 0.6% 2% 1%; width: 30%;">주문번호 &nbsp;</label><input id="order_no" style="width:60%;" type="text" name="order_no" readonly/>
		     				<label class="text-left" style="margin: 2% 0.6% 2% 1%; width: 30%;">주문자 &nbsp;</label><input id="name" style="width:60%;" type="text"readonly/>
		     				<label class="text-left" style="margin: 2% 0.6% 2% 1%; width: 30%;">수령인 &nbsp;</label><input id="order_receiver" style="width:60%;" type="text" readonly/>
		     				<label class="text-left" style="margin: 2% 0.6% 2% 1%; width: 30%;">상품명 &nbsp;</label><input id="prod_name" style="width:60%;" type="text" readonly/>
		     				<label class="text-left" style="margin: 2% 0.6% 2% 1%; width: 30%;">상품금액 &nbsp;</label><input id="order_tprice" style="width:60%;" type="text" readonly/>
		     				<label class="text-left" style="margin: 2% 0.6% 2% 1%; width: 30%;">주문일시 &nbsp;</label><input id="order_date" style="width:60%;" type="text" readonly/>
		     				<label class="text-left" style="margin-top: 2%; margin-left: 1%; width: 30%;">주문상태 &nbsp;</label>
		     				<select id="modal_order_status" style="width:60%;" name="order_status">
								<option value="">선택</option>			
								<option value="1">주문완료</option>
								<option value="2">교환/반품</option>			
								<option value="5">구매확정</option>
							</select>

							
							<label class="text-left" style="margin-top: 2%; margin-left: 1%; width: 30%;">배송상태 &nbsp;</label>
							<select id="modal_ship_status" style="width:60%;" name="ship_status">
								<option value="">선택</option>			
								<option value="1">배송준비중</option>			
								<option value="2">배송중</option>			
								<option value="3">배송완료</option>
							</select>
							
							<label class="text-left" style="margin-top: 2%; margin-left: 1.1%; width: 30%;">주소 &nbsp;</label>
							<input style="width:15%" type="text" id="postcode" size="6" maxlength="5" placeholder="우편번호" readonly/>							
							<input style="margin-top: 0.75%; margin-left: 31.6%; width: 60%;" id="address" size="40" maxlength="200" placeholder="주소" readonly/>
							<input style="margin-top: 1.25%; margin-left: 31.6%; width: 28.85%;" type="text" id="detailAddress" size="40" maxlength="200" placeholder="상세주소" readonly />
							<input style="margin-left: 2%; width: 28.5%;" type="text" id="extraAddress" size="40" maxlength="200" readonly />
							
							<input type="hidden" id="prod_no" name="prod_no" />
		     			</form>
		     			
	  				</div>
				</div>
	
				<!-- Modal footer -->
	      		<div class="modal-footer text-center">
	      			<div style="width:100%; margin: 0 auto;">
		        		<button style="margin-right: 10%;" type="button" class="btn btn-success modalFooter" id="confirm" data-toggle="modal" data-target="#updateConfirm">변경</button>
		        		<button type="button" class="btn btn-danger modalFooter" data-dismiss="modal">취소</button>
	        		</div>
	      		</div>
	    	</div>
		</div>
	</div>
	<%-- 주문상태변경 선택 모달 끝 --%>
	
	<%-- 확인여부 모달 시작 --%>
	<div class="modal fade" id="updateConfirm"> <%-- 만약에 모달이 안보이거나 뒤로 가버릴 경우에는 모달의 class 에서 fade 를 뺀 class="modal" 로 하고서 해당 모달의 css 에서 zindex 값을 1050; 으로 주면 된다. --%>  
		<div class="modal-dialog modal-dialog-centered modal-sm">
	  		<div class="modal-content">
	  
	    		<!-- Modal header -->
				<div class="modal-header">
	  				<h4 class="modal-title text-center">주문상태변경</h4>
	  				<button type="button" class="close #updateConfirm" data-dismiss="modal">&times;</button>
				</div>
	
				<!-- Modal body -->
				<div class="modal-body">
	 				<div class="text-center">
						변경하시겠습니까?     			
	  				</div>
				</div>
	
				<!-- Modal footer -->
	      		<div class="modal-footer text-center">
	      			<div style="width:100%; margin: 0 auto;">
		        		<button style="margin-right: 10%;" type="button" class="btn btn-success modalFooter" onclick="orderUpdate('<%=request.getContextPath()%>')">확인</button>
		        		<button type="button" class="btn btn-danger modalFooter" data-dismiss="modal">취소</button>
	        		</div>
	      		</div>
	    	</div>
		</div>
	</div>
	<%-- 확인여부 모달 시작 --%>
	
	<%-- 페이징 처리 부분 --%>
	<div style="margin-top: 5%; display: flex;">
		${requestScope.pageBar}
	</div>
	<%-- 페이징 처리 부분 --%>
	
</div>