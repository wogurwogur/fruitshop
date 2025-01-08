<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page import="java.util.*, mypage.ship.domain.ShipVO" %>

<%
	List<ShipVO> shipList = (ArrayList)request.getAttribute("shipList");

	String ship_default = "";
	String receiver = "";
	String postcode = "";
	String address = "";
	String detailAddress = "";
	String extraAddress = "";
	String hp2 = "";
	String hp3 = "";
	
	if (shipList != null) {
		
		for (int i=0; i<shipList.size(); i++) {
			
			if (shipList.get(i).getShip_default() == 1) {
				ship_default 	= "Y";
				receiver 		= shipList.get(i).getShip_receiver();
				postcode 		= shipList.get(i).getShip_postcode();
				address 		= shipList.get(i).getShip_address();
				detailAddress 	= shipList.get(i).getShip_detailAddress();
				extraAddress 	= shipList.get(i).getShip_extraAddress();
				hp2 			= shipList.get(i).getShip_receivertel().substring(3, 7);
				hp3 			= shipList.get(i).getShip_receivertel().substring(7, 11);
				break;
			}
		}// end of for() ---------------------------
	}
%>

<jsp:include page="../common/header.jsp"></jsp:include>

<%-- Custom CSS --%>
<link rel="stylesheet" href="<%= request.getContextPath()%>/css/order/orderCheckout.css">

<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/js/order/orderCheckout.js"></script>

<script type="text/javascript">
	$(document).ready(() => {
		$("div.shipInput_title").on("click", e => {
			// alert("야야호"+ $("div.shipInput_title").index($(e.target)));
			
			const index = $("div.shipInput_title").index($(e.target));
			
			switch (index) {
				case 0:
					useUserShipDefault();
					break;
					
				case 1:
					useUserInfo();
					break;
				/*	
				case 2:
					useUserShipInfo();
					break;
				*/	
				case 3:
					useUserInput();
					break;
			}
			
			$("div.shipInput_title").removeClass("active");
			$(e.target).addClass("active");
		});// end of $("div.shipInput_title").on("click", e => {}) --------------
		
		$("div.shipInput_title").eq(0).trigger("click");
	});// end of $(document).ready(() => {}) -------------
	
	// 기본 배송지를 사용
	function useUserShipDefault() {
		// 기본배송지 지정 여부에 따라 분기한다		
		
		if ("<%= ship_default%>" == "Y") {
			$("input:text[name='name']").val("<%= receiver %>");
			$("input#postcode").val("<%= postcode %>");
			$("input#address").val("<%= address %>");
			$("input#detailAddress").val("<%= detailAddress %>");
			$("input#extraAddress").val("<%= extraAddress %>");
			$("input#hp2").val("<%= hp2 %>");
			$("input#hp3").val("<%= hp3 %>");
			$("input:text[name='email']").val('${sessionScope.loginuser.email}');
		}
		
	}// end of function useUserShipDefault() ----------------------- 
	
	
	// 회원정보를 배송지로 사용
	function useUserInfo() {
		const tel = '${sessionScope.loginuser.tel}';
		
		//console.log(tel.substring(3, 7));
		//console.log(tel.substring(7, 11));
		
		$("input:text[name='name']").val('${sessionScope.loginuser.name}');
		$("input#postcode").val('${sessionScope.loginuser.postcode}');
		$("input#address").val('${sessionScope.loginuser.address}');
		$("input#detailAddress").val('${sessionScope.loginuser.detailaddress}');
		$("input#extraAddress").val('${sessionScope.loginuser.extraaddress}');
		$("input#hp2").val(tel.substring(3, 7));
		$("input#hp3").val(tel.substring(7, 11));
		$("input:text[name='email']").val('${sessionScope.loginuser.email}');
	}// end of function useUserInfo() ----------------------
	
	// 배송지 직접입력
	function useUserInput() {
		$("input:text[name='name']").val("");
		$("input#postcode").val("");
		$("input#address").val("");
		$("input#detailAddress").val('');
		$("input#extraAddress").val('');
		$("input#hp2").val('');
		$("input#hp3").val("");
		$("input:text[name='email']").val('');
	}// end of function useUserInput() ----------------
	
</script>



<div id="container">
	<%-- 주문/결제 타이틀 --%>
	<div style="background-color: black; color: white;" class="text-center">
		<span id="order_title">주문/결제</span>
	</div>

	<%-- 배송지 입력 정보 선택 시작 --%>
	<div id="shipInput_selector">
		<div class="shipInput_title active">
			기본 배송지
		</div>
		<div class="shipInput_title">
			회원 정보 사용
		</div>
		<div id="selectShip" class="shipInput_title" data-toggle="modal" data-target="#userShipInfo" data-dismiss="modal">
			배송지 선택
		</div>
		<div class="shipInput_title">
			직접 입력
		</div>
	</div>
	<%-- 배송지 입력 정보 선택 끝 --%>
	
	
	<%-- 배송지 선택 모달 시작 --%>
	<div class="modal fade" id="userShipInfo"> <%-- 만약에 모달이 안보이거나 뒤로 가버릴 경우에는 모달의 class 에서 fade 를 뺀 class="modal" 로 하고서 해당 모달의 css 에서 zindex 값을 1050; 으로 주면 된다. --%>  
		<div class="modal-dialog modal-dialog-centered modal-xl">
	  		<div class="modal-content">
	  
	    		<!-- Modal header -->
				<div class="modal-header">
	  				<h4 class="modal-title text-center">배송지 선택</h4>
	  				<button type="button" class="close userShipInfo" data-dismiss="modal">&times;</button>
				</div>
	
				<!-- Modal body -->
				<div class="modal-body">
	 				<div id="idFind">
		     			<%--<iframe id="iframe_idFind" style="border: none; width: 100%; height: 350px;" src="<%= request.getContextPath()%>/login/idFind.up"> 
		     			</iframe>
		     			 --%>
		     			 <table class="table text-center table-hover" id="shipInfo">
		     			 	<thead>
		     			 		<tr>
		     			 			<th>배송지번호</th>
		     			 			<th>배송지명</th>
		     			 			<th>받는사람</th>
		     			 			<th>연락처</th>
		     			 			<th>우편번호</th>
		     			 			<th>주소</th>
		     			 			<th>상세주소</th>
		     			 			<th>참고사항</th>
		     			 			<th>기본배송지</th>
		     			 		</tr>
		     			 	</thead>
		     			 	<tbody>
		     			 		<c:if test="${empty requestScope.shipList}">
		     			 			<tr>
										<td colspan="9">등록된 배송지가 존재하지 않습니다.</td>
									</tr>
		     			 		</c:if>
		     			 		<c:if test="${!empty requestScope.shipList}">
			     			 		<c:forEach var="shipItem" items="${requestScope.shipList}" varStatus="status">
			     			 			<c:set var="mobile" value="${shipItem.ship_receivertel}" />
										<tr style="cursor: pointer;">
											<td>${status.count}</td>
											<td class="ship_name">${shipItem.ship_name}</td>
											<td class="receiver">${shipItem.ship_receiver}</td>
											<td class="receivertel">${fn:substring(mobile, 0, 3)}-${fn:substring(mobile, 3, 7)}-${fn:substring(mobile, 7, 11)}</td>
											<td class="postcode">${shipItem.ship_postcode}</td>
				     			 			<td class="address">${shipItem.ship_address}</td>
				     			 			<td class="detailaddress">${shipItem.ship_detailAddress}</td>
				     			 			<td class="extraaddress">${shipItem.ship_extraAddress}</td>
				     			 			<td class="ship_default">
												<c:if test="${shipItem.ship_default == 0}">N</c:if>
												<c:if test="${shipItem.ship_default == 1}">Y</c:if>
											</td>
										</tr>							
									</c:forEach>
								</c:if>
		     			 	</tbody>
		     			 </table>
	  				</div>
				</div>
	
				<!-- Modal footer -->
	      		<div class="modal-footer">
	        		<button type="button" class="btn btn-danger userShipInfo" data-dismiss="modal">Close</button>
	      		</div>
	    	</div>
		</div>
	</div>
	<%-- 배송지 선택 모달 끝 --%>
	
	
	<%-- 주문정보 확인 및 결제 시작 --%>
	<div id="shipInfo_place">
		<span id="input_guide">* 표시는 필수 입력 사항입니다.</span>
		<form name="shipInfo">
			<%-- 회원정보 입력 시작 --%>
			<label class="text-left" style="margin-top: 2%; margin-left: 1%; width: 15%;">받는사람 &nbsp;*</label><input id="name" style="width:80%" type="text" name="name"/>
			
			<%-- 주소 --%>
			<label class="text-left" style="margin-top: 2%; margin-left: 1%; width: 15%;">주소 &nbsp;*</label><input style="width:10%" type="text" name="postcode" id="postcode" size="6" maxlength="5" placeholder="우편번호" readonly/>
			<span style="width: 90px; height: 30px; font-size: 12px; margin-left: 1%; margin-bottom: 0.3%;" class="btn btn-outline-secondary">우편번호찾기</span>
			
			<input style="margin-top: 0.75%; margin-left: 16%; width: 80%;" name="address" id="address" size="40" maxlength="200" placeholder="주소" readonly/>
			<input style="margin-top: 1.25%; margin-left: 16%; width: 39%;" type="text" name="detailaddress" id="detailAddress" size="40" maxlength="200" placeholder="상세주소" />
			<input style="margin-left: 2%; width: 39%;" type="text" name="extraaddress" id="extraAddress" size="40" maxlength="200" readonly />
			<%-- 주소 --%>
			
			<label class="text-left" style="margin-left: 1%; width: 14.5%;">연락처 &nbsp;*</label>
			<input style="margin-top: 2%; width:10%" type="text" name="hp1" id="hp1" size="6" maxlength="3" value="010" readonly /> &nbsp;-&nbsp;
            <input style="width: 10%;" type="text" name="hp2" id="hp2" size="6" maxlength="4" />&nbsp;-&nbsp;
            <input style="width: 10%;" type="text" name="hp3" id="hp3" size="6" maxlength="4" /><span style="margin-left: 2%;" class="error">휴대폰 형식이 아닙니다.</span><br>
            
            <label class="text-left" style="margin-left: 1%; width: 14.5%;">이메일 &nbsp;*</label>
            <input style="margin-top: 2%; width: 57%" type="text" name="email" id="email" maxlength="60" class="requiredInfo" />
            <span style="margin-left: 2%;" class="error">이메일 형식에 맞지 않습니다.</span><br>
            
            <label class="text-left" style="margin-left: 1%; width: 15%;">요청사항 &nbsp;</label><input style="margin-top: 2%; width:80%" type="text" name="order_request" value="빠른 배송 부탁드립니다."/>
            
            
            <label for="setDefaultShip" class="text-left" style="cursor: pointer; margin-left: 1%; width: 15%;">기본배송지 설정 &nbsp;</label>
            <input id="setDefaultShip" style="margin-top: 2%;" type="checkbox" />
            
            <%-- 회원정보 입력 끝 --%>
            
            
   
            <%-- 주문정보 확인 시작 --%>
            <div style="margin-left: 1%;" class="h6 mt-5">주문 상품 정보</div>
			<hr style="border: solid 1px black;">
            <div>
				<table id="orderList" class="table table-border text-center">
					<thead>
						<tr>
							<th></th>
							<th style="width: 50%">상품명</th>
							<th>수량</th>
							<th>가격</th>
						</tr>
					</thead>
					<tbody>
						<%-- 장바구니를 통한 주문과 상품페이지 직접 주문 여부에 따라 분기한다. --%>
						<%-- 상품 개별페이지에서 주문한 경우. --%>
						<c:if test="${empty requestScope.cartList}">
							<tr class="productRow">
								<td>
									<input type="hidden" id="prod_no" name="prod_no" value="${requestScope.pvo.prod_no}" />
									<img style= "width: 50px; heigth: 30px;" src="<%= request.getContextPath()%>/images/product/thumnail/${requestScope.pvo.prod_thumnail}">
								</td>
								<td><input type="hidden" id="prod_name" name="prod_name" value="${requestScope.pvo.prod_name}" />${requestScope.pvo.prod_name}</td>
								<td class="prod_count">${requestScope.prodCnt}</td>
								<fmt:parseNumber var="count" type="number" value="${requestScope.prodCnt}" />
								<fmt:parseNumber var="price" type="number" value="${requestScope.pvo.prod_price}" />
								<td class="prod_price"><fmt:formatNumber value="${count * price}" pattern="#,###" /><input type="hidden" id="prod_price" name="prod_price" value="${count * price}" /></td>
							</tr>	
						</c:if>
						
						<c:if test="${!empty requestScope.cartList}">
						<%-- 상품리스트 반복문 --%>
							<c:forEach var="cartItem" items="${requestScope.cartList}" varStatus="status">
								<tr class="productRow">
									<td>
										<input type="hidden" id="prod_no" name="prod_no" value="${cartItem.prod_no}" />
										<img style= "width: 50px; heigth: 30px;" src="<%= request.getContextPath()%>/images/product/thumnail/${cartItem.prod_thumnail}">
									</td>
									<td><input type="hidden" id="prod_name" name="prod_name" value="${cartItem.prod_name}" />${cartItem.prod_name}</td>
									<td class="prod_count">${cartItem.cart_prodcount}</td>
									<fmt:parseNumber var="count" type="number" value="${cartItem.cart_prodcount}" />
									<fmt:parseNumber var="price" type="number" value="${cartItem.prod_price}" />
									<td class="prod_price"><fmt:formatNumber value="${count * price}" pattern="#,###" /><input type="hidden" id="prod_price" name="prod_price" value="${count * price}" /></td>
								</tr>							
							</c:forEach>
						<%-- 상품리스트 반복문 --%>
						</c:if>
					</tbody>
				</table>
			</div>
            <%-- 주문정보 확인 끝 --%>
            
            
            
            
            <%-- 쿠폰 사용 정보 시작 --%>
            <div style="margin-left: 1%;" class="h6 mt-5">쿠폰 적용</div>
			<hr style="border: solid 1px black;">
            <div style="width: 90%; margin: 0 auto;">
            	<%-- <span style="display: inline-block; padding-top: 1%;">쿠폰선택</span> --%>
            	<span data-toggle="modal" data-target="#userCouponInfo" data-dismiss="modal" class="btn btn-success"style="font-size: 14px; width: 60px; heigth:20px; float: right">선택</span><br><br>
            	<table id="couponSelect" class="table table-border text-center">
					<thead>
						<tr>
							<th>쿠폰명</th>
							<th style="width: 50%">만료일자</th>
							<th>할인액</th>
							<th>삭제</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td id="coupon_name"></td>
							<td id="coupon_expdate"></td>
							<td id="coupon_discount"></td>
							<td><span style="width:45px; height: 25px; font-size: 10px;" class="btn btn-outline-danger">제거</span></td>
						</tr>
					</tbody>
				</table>
            </div>
            <%-- 쿠폰 사용 정보 끝 --%>
            
            
            
            <%-- 쿠폰 정보 모달 시작 --%>
            <div class="modal fade" id="userCouponInfo"> <%-- 만약에 모달이 안보이거나 뒤로 가버릴 경우에는 모달의 class 에서 fade 를 뺀 class="modal" 로 하고서 해당 모달의 css 에서 zindex 값을 1050; 으로 주면 된다. --%>  
				<div class="modal-dialog modal-dialog-centered modal-lg">
			  		<div class="modal-content">
			  
			    		<!-- Modal header -->
						<div class="modal-header">
			  				<h4 class="modal-title">쿠폰 선택</h4>
			  				<button type="button" class="close userCouponInfo" data-dismiss="modal">&times;</button>
						</div>
			
						<!-- Modal body -->
						<div class="modal-body">
			 				<div id="idFind">
				     			<%--<iframe id="iframe_idFind" style="border: none; width: 100%; height: 350px;" src="<%= request.getContextPath()%>/login/idFind.up"> 
				     			</iframe>
				     			 --%>
				     			 <table id="couponInfo" class="table text-center table-hover">
				     			 	<thead>
				     			 		<tr>
				     			 			<th>쿠폰명</th>
											<th style="width: 50%">만료일자</th>
											<th>할인액</th>
				     			 		</tr>
				     			 	</thead>
				     			 	<tbody>
				     			 	<%-- 쿠폰리스트 반복문 --%>
				     			 	<%-- 쿠폰 번호 가져와야? --%>
									<c:forEach var="couponItem" items="${requestScope.couponList}" varStatus="status">
										<tr style="cursor: pointer;">
											<td class="coupon_name">${couponItem.coupon_name}<input type="hidden" class="coupon_no" value="${couponItem.coupon_no}"></td>
											<td class="coupon_expdate">${couponItem.coupon_expire}</td>											
											<fmt:parseNumber var="discount" type="number" value="${couponItem.coupon_discount}" />
											<td class="coupon_discount"><fmt:formatNumber value="${discount}" pattern="#,###" /></td>
										</tr>							
									</c:forEach>
									<%-- 쿠폰리스트 반복문 --%>
				     			 	</tbody>
				     			 </table>
			  				</div>
						</div>
			
						<!-- Modal footer -->
			      		<div class="modal-footer">
			        		<button type="button" class="btn btn-danger userCouponInfo" data-dismiss="modal">Close</button>
			      		</div>
			    	</div>
				</div>
			</div>
            <%-- 쿠폰 정보 모달 끝 --%>
            
            
            <%-- 결제 정보 확인 시작 --%>
            <div style="margin-left: 1%;" class="h6 mt-5">결제 정보</div>
			<hr style="border: solid 1px black;">
            <div style="width: 90%; margin: 0 auto;">
            	<span>주문상품</span><span id="total_oprice" style="float: right"></span><br><br>
            	<span>배송비</span><span style="float: right">2,500원</span><br><br>
            	<span>할인액</span><span id="discount" style="float: right"></span><br>
            </div>
            <%-- <hr style="border: solid 0.3px black;">--%>
            <hr>
            <%-- 결제 정보 확인 끝 --%>
            
            
            <div style="width: 90%; margin: 0 auto;">
            	<span>결제예정금액</span><input type="hidden" name="order_tprice" value=""/><span class="total_price" style="float: right"></span><br><br>
            	<span>적립예정금액</span><input type="hidden" name="point" value=""/><span id="point" style="float: right"></span>
            </div>
            
            <button id="payments" type="button" class="btn btn-primary"><span class="total_price"></span> 결제하기</button>

			<%-- DB 전송 필요항목 숨김 태그 --%>
			<input type="hidden" name="userNo" value="${sessionScope.loginuser.user_no}" />
			<input type="hidden" name="coupon_name" />
			<input type="hidden" name="coupon_no" />
			<input type="hidden" name="coupon_discount" />
			<input type="hidden" name="ordetail_count" />
			<input type="hidden" id="contextPath" value="<%= request.getContextPath()%>"/>
			<input type="hidden" id="ship_default" name="ship_default" />
			<input type="hidden" id="productArr" name="productArr" />
            
		</form>	
	</div>
	<%-- 주문정보 확인 및 결제 끝 --%>
</div>


<jsp:include page="../common/footer.jsp"></jsp:include>