<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


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
			
			if (index == 0) {
				useUserInfo();
			}
			
			if (index == 2) {
				useUserInput();
			}
			
			$("div.shipInput_title").removeClass("active");
			$(e.target).addClass("active");
		});// end of $("div.shipInput_title").on("click", e => {}) --------------
		
	});// end of $(document).ready(() => {}) -------------
	
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
	}
	
	function useUserInput() {
		$("input:text[name='name']").val("");
		$("input#postcode").val("");
		$("input#address").val("");
		$("input#detailAddress").val('');
		$("input#extraAddress").val('');
		$("input#hp2").val('');
		$("input#hp3").val("");
		$("input:text[name='email']").val('');
	}
	
</script>



<div id="container">
	<%-- 주문/결제 타이틀 --%>
	<div style="background-color: black; color: white;" class="text-center">
		<span id="order_title">주문/결제</span>
	</div>

	<%-- 배송지 입력 정보 선택 시작 --%>
	<div id="shipInput_selector">
		<div class="shipInput_title active">
			회원 정보 사용
		</div>
		<div class="shipInput_title" data-toggle="modal" data-target="#userShipInfo" data-dismiss="modal">
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
	  				<h4 class="modal-title">배송지 선택</h4>
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
		     			 			<th>우편번호</th>
		     			 			<th style="width: 30%;">주소</th>
		     			 			<th>상세주소</th>
		     			 			<th>참고사항</th>
		     			 			<th>기본배송지</th>
		     			 		</tr>
		     			 	</thead>
		     			 	<tbody>
		     			 		<tr style="cursor: pointer;">
		     			 			<td>1</td>
		     			 			<td>집</td>
		     			 			<td class="postcode">12234</td>
		     			 			<td class="address">서울시 마포구 길가로 홍대입구</td>
		     			 			<td class="detailaddress">9번출구</td>
		     			 			<td class="extraaddress">동교동</td>
		     			 			<td>N</td>
		     			 		</tr>
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
			<label class="text-left" style="margin-top: 2%; margin-left: 1%; width: 15%;">받는사람 &nbsp;*</label><input style="width:80%" type="text" name="name"/>
			
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
					<%-- 상품리스트 반복문 들어와야 함 --%>
						<tr>
							<td><img style= "width: 50px; heigth: 30px;" src="<%= request.getContextPath()%>/images/product/thumnail/apple.png"></td>
							<td>사과 5kg</td>
							<td>1</td>
							<td>51,000</td>
						</tr>
						<tr>
							<td><img style= "width: 50px; heigth: 30px;" src="<%= request.getContextPath()%>/images/product/thumnail/grape.png"></td>
							<td>포도 5kg</td>
							<td>1</td>
							<td>51,000</td>
						</tr>
					<%-- 상품리스트 반복문 들어와야 함 --%>
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
						</tr>
					</thead>
					<tbody>
						<tr>
							<td id="coupon_name"></td>
							<td id="coupon_expdate"></td>
							<td id="coupon_discount"></td>
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
				     			 <table class="table text-center table-hover" id="shipInfo">
				     			 	<thead>
				     			 		<tr>
				     			 			<th>쿠폰명</th>
											<th style="width: 50%">만료일자</th>
											<th>할인액</th>
				     			 		</tr>
				     			 	</thead>
				     			 	<tbody>
				     			 		<tr style="cursor: pointer;">
				     			 			<td class="coupon_name">오예스</td>
											<td class="coupon_expdate">2024-12-31</td>
											<td class="coupon_discount">1,000원</td>
				     			 		</tr>
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
            	<span>주문상품</span><span style="float: right">51,000원</span><br><br>
            	<span>배송비</span><span style="float: right">2,500원</span><br><br>
            	<span>할인액</span><span style="float: right">2,500원</span><br>
            </div>
            <%-- <hr style="border: solid 0.3px black;">--%>
            <hr>
            <%-- 결제 정보 확인 끝 --%>
            
            
            <div style="width: 90%; margin: 0 auto;">
            	<span>총 주문금액</span><input type="hidden" name="order_tprice" value="51000"/><span style="float: right">51,000원</span>
            </div>
            
            
            <button style="margin-top: 3%; width: 100%; height: 50px;" type="button" class="btn btn-primary">51,000원 결제하기</button>
            
		</form>	
	</div>
	<%-- 주문정보 확인 및 결제 끝 --%>
</div>




<jsp:include page="../common/footer.jsp"></jsp:include>