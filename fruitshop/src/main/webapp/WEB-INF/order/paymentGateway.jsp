<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    String ctxPath = request.getContextPath();
    //    /MyMVC 
%>    

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.min.js" ></script>
<script type="text/javascript" src="https://service.iamport.kr/js/iamport.payment-1.1.2.js"></script>

<script type="text/javascript">

$(document).ready(function() {
	//	여기 링크를 꼭 참고하세용 http://www.iamport.kr/getstarted
   var IMP = window.IMP;     // 생략가능
   IMP.init('imp38080720');  // 중요!!  아임포트에 가입시 부여받은 "가맹점 식별코드". 
	
   // 결제요청하기
   IMP.request_pay({
       pg : 'html5_inicis', // 결제방식 PG사 구분
       pay_method : 'card',	// 결제 수단
       merchant_uid : 'merchant_' + new Date().getTime(), // 가맹점에서 생성/관리하는 고유 주문번호
       name : '${requestScope.productName}',   // 코인충전 또는 order 테이블에 들어갈 주문명 혹은 주문 번호. (선택항목)원활한 결제정보 확인을 위해 입력 권장(PG사 마다 차이가 있지만) 16자 이내로 작성하기를 권장
       amount : ${requestScope.productPrice},  // '${coinmoney}'  결제 금액 number 타입. 필수항목. 
       buyer_email : '${requestScope.email}',  // 구매자 email
       buyer_name : '${requestScope.name}',	   // 구매자 이름 
       buyer_tel : '${requestScope.mobile}',   // 구매자 전화번호 (필수항목)
       buyer_addr : '',  
       buyer_postcode : '',
       m_redirect_url : ''  // 휴대폰 사용시 결제 완료 후 action : 컨트롤러로 보내서 자체 db에 입력시킬것!
   }, function(rsp) {
       /*
		   if ( rsp.success ) {
			   var msg = '결제가 완료되었습니다.';
			   msg += '고유ID : ' + rsp.imp_uid;
			   msg += '상점 거래ID : ' + rsp.merchant_uid;
			   msg += '결제 금액 : ' + rsp.paid_amount;
			   msg += '카드 승인번호 : ' + rsp.apply_num;
		   } else {
			   var msg = '결제에 실패하였습니다.';
			   msg += '에러내용 : ' + rsp.error_msg;
		   }
		   alert(msg);
	   */

		if ( rsp.success ) { // PC 데스크탑용
			// === 결제 성공 시 주문 테이블에 insert 완료 후 주문상세, 결제 테이블에 정보를 insert 한다 === //
			
			<%--
			// 주문 테이블에 정보 등록
			$.ajax({
				url: "<%= ctxPath %>/order/orderCheckoutEnd.ddg",
				data: {
					"name": "${requestScope.name}", "order_no": "${requestScope.order_no}", "user_no": "${requestScope.user_no}",
					"order_tprice": "${requestScope.order_tprice}", "email": "${requestScope.email}", "mobile": "${requestScope.mobile}",
					"postcode": "${requestScope.postcode}", "address": "${requestScope.address}", "detailaddress": "${requestScope.detailaddress}",
					"extraaddress":"${requestScope.extraaddress}", "order_request": "${requestScope.order_request}", "ship_default": "${requestScope.ship_default}",
					"point": "${requestScope.point}", "coupon_no": "${requestScope.coupon_no}", "order_receiver": "${requestScope.order_receiver}"
				},
				type: "POST",
				dataType: "JSON",
				success: function(json) {
					
				},
				error: function(request, status, error){
               		alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
               	}
			});
			--%>
			
			
			
        } else {
        	// 취소했을 경우
        	$.ajax({
				url: "<%= ctxPath %>/order/orderCheckoutEnd.ddg",
				data: {
					"name": "${requestScope.name}", "order_no": "${requestScope.order_no}", "user_no": "${requestScope.user_no}",
					"order_tprice": "${requestScope.order_tprice}", "email": "${requestScope.email}", "mobile": "${requestScope.mobile}",
					"postcode": "${requestScope.postcode}", "address": "${requestScope.address}", "detailaddress": "${requestScope.detailaddress}",
					"extraaddress":"${requestScope.extraaddress}", "order_request": "${requestScope.order_request}", "ship_default": "${requestScope.ship_default}",
					"point": "${requestScope.point}", "coupon_no": "${requestScope.coupon_no}", "order_receiver": "${requestScope.order_receiver}",
					"productArr": '${requestScope.productArr}', "coupon_name": "${requestScope.coupon_name}", "coupon_discount": "${requestScope.coupon_discount}"
				},
				type: "POST",
				dataType: "JSON",
				async: false,
				success: function(json) {
					console.log("확인용 json", json);
					
					alert("주문을 성공했습니다. "+ json);
					
					if (json.isComplete == 1) {
						
					}
				},
				error: function(request, status, error){
               		alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
               		// alert("경로를 어디로 가야함???");
               	}
			});
        	
        	<%--
        	console.log("확인용 name => "+ "${requestScope.name}");
       	   	console.log("확인용 order_no => "+ "${requestScope.order_no}");
       	   	console.log("확인용 productPrice => "+ "${requestScope.productPrice}");
       	   	console.log("확인용 order_tprice => "+ "${requestScope.order_tprice}");
       	 	console.log("확인용 email => "+ "${requestScope.email}");
       	   	console.log("확인용 mobile => "+ "${requestScope.mobile}");
       	   	console.log("확인용 postcode => "+ "${requestScope.postcode}");
       	   	console.log("확인용 address => "+ "${requestScope.address}");
       	   	console.log("확인용 detailaddress => "+ "${requestScope.detailaddress}");
       	   	console.log("확인용 extraaddress => "+ "${requestScope.extraaddress}");
       	   	console.log("확인용 order_request => "+ "${requestScope.order_request}");
       	   	console.log("확인용 ship_default => "+ "${requestScope.ship_default}");			
       	   	console.log("확인용 user_no => "+ "${requestScope.user_no}");
       	   	console.log("확인용 point => "+ "${requestScope.point}");			
       	   	console.log("확인용 prodNo => "+ "${requestScope.prodNo}");	
       	   	console.log("확인용 coupon_no => "+ "${requestScope.coupon_no}");	
       	   	console.log("확인용 coupon_name => "+ "${requestScope.coupon_name}");	
       	   	console.log("확인용 coupon_discount => "+ "${requestScope.coupon_discount}");	
       	   	console.log("확인용 order_receiver => "+ "${requestScope.order_receiver}");	
       	   	console.log("확인용 productArr => "+ "${requestScope.productArr}");	
	       	 --%>
            //location.href="/fruitshop";
            //alert("결제에 실패하였습니다.");
       }

   }); // end of IMP.request_pay()----------------------------
  	 
}); // end of $(document).ready()-----------------------------

</script>
</head>	

<body>
</body>
</html>
