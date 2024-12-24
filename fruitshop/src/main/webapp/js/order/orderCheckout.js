/**
 * 
 */

$(document).ready(() => {
		$("span.error").hide();
		$("table#couponSelect").hide();
		
		
		// === 모달창 주소 고르기 시작 === //
		$("table#shipInfo tr").on("dblclick", e => {
			const receiver = $(e.target).parent().children(".receiver").text();
			const receivertel = $(e.target).parent().children(".receivertel").text();
			const postcode = $(e.target).parent().children(".postcode").text();
			const address = $(e.target).parent().children(".address").text();
			const detailaddress = $(e.target).parent().children(".detailaddress").text();
			const extraaddress = $(e.target).parent().children(".extraaddress").text();
			
			console.log("receiver : ", receiver);
			console.log("postcode : ", postcode);
			console.log("address : ", address);
			console.log("detailaddress : ", detailaddress);
			console.log("extraaddress : ", extraaddress);
			
			
			$("input#name").val(receiver);
			$("input#hp1").val(receivertel.substring(0,3));
			$("input#hp2").val(receivertel.substring(4,8));
			$("input#hp3").val(receivertel.substring(9,13));
			$("input#postcode").val(postcode);
			$("input#address").val(address);
			$("input#detailAddress").val(detailaddress);
			$("input#extraAddress").val(extraaddress);
			
			$('#userShipInfo').modal('hide');
			
		});// end of $("table#shipInfo tr").on("click", e => {}) -----------------
		// === 모달창 주소 고르기 끝 === //
		
		
		// === 모달창 쿠폰 고르기 이벤트 시작 === //
		$("table#couponInfo tr").on("dblclick", e => {
			const coupon_name = $(e.target).parent().children(".coupon_name").text();
			const coupon_expdate = $(e.target).parent().children(".coupon_expdate").text();
			const coupon_discount = $(e.target).parent().children(".coupon_discount").text();
			
			//console.log("coupon_name : ", coupon_name);
			//console.log("coupon_expdate : ", coupon_expdate);
			//console.log("coupon_discount : ", coupon_discount);
			
			$("td#coupon_name").html(coupon_name);
			$("td#coupon_expdate").html(coupon_expdate);
			$("td#coupon_discount").html(coupon_discount);
			
			$("span#discount").html(coupon_discount+"원");
			
			const discount = Number(coupon_discount.split(",").join(""));
			const tprice = Number($("input:hidden[name='order_tprice']").val());
			
			const dcPrice = tprice - discount;
			
			// DB에 넘어갈 값 추가
			$("input:hidden[name='order_tprice']").val(dcPrice);
			console.log("DB전송 총결제금액", $("input:hidden[name='order_tprice']").val());
			
			// 총금액들에 추가
			$("span.total_price").html(dcPrice.toLocaleString("en")+"원");
			
			$("table#couponSelect").show();
			
			$('#userCouponInfo').modal('hide');
			
		});// end of $("table#shipInfo tr").on("click", e => {}) -----------------
		// === 모달창 쿠폰 고르기 이벤트 끝 === //
		
		// === 주소 직접입력시 이벤트 처리 시작 === //
		$("span.btn-outline-secondary").on("click", function() {
			new daum.Postcode({
	            oncomplete: function(data) {
	                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
	    
	                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
	                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
	                let addr = ''; // 주소 변수
	                let extraAddr = ''; // 참고항목 변수
	    
	                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
	                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
	                    addr = data.roadAddress;
	                } else { // 사용자가 지번 주소를 선택했을 경우(J)
	                    addr = data.jibunAddress;
	                }
	    
	                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
	                if(data.userSelectedType === 'R'){
	                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
	                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
	                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
	                        extraAddr += data.bname;
	                    }
	                    // 건물명이 있고, 공동주택일 경우 추가한다.
	                    if(data.buildingName !== '' && data.apartment === 'Y'){
	                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
	                    }
	                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
	                    if(extraAddr !== ''){
	                        extraAddr = ' (' + extraAddr + ')';
	                    }
	                    // 조합된 참고항목을 해당 필드에 넣는다.
	                    document.getElementById("extraAddress").value = extraAddr;
	                
	                } else {
	                    document.getElementById("extraAddress").value = '';
	                }
	    
	                // 우편번호와 주소 정보를 해당 필드에 넣는다.
	                document.getElementById('postcode').value = data.zonecode;
	                document.getElementById("address").value = addr;
	                // 커서를 상세주소 필드로 이동한다.
	                document.getElementById("detailAddress").focus();
	            }
	        }).open();
		});// end of $("btn.btn-outline-secondary").on("click", function() {}) ---------------
		
		
		// === 주문상품 결제금액 구하기 시작 === //
		let price_sum  = 0;
		let price_ship = 2500;
		$("table#orderList td.prod_price").each((index, element) => {
			
			//console.log("가격: ", $(element).text());
			
			price_sum += Number($(element).text().split(",").join(""));
			
			
			if (index+1 == $("table#orderList td.prod_price").length){
				//price_sum += price_ship;
				$("span#total_oprice").html(price_sum.toLocaleString("en")+"원");
				$("span.total_price").html((price_sum+price_ship).toLocaleString("en")+"원");
			}
			
			
		});// end of $("table#orderList td.prod_price").each((index, element) => {}) ----------------- 
		
		
		// 실제 DB에 들어갈 값 지정하기			
		$("input:hidden[name='order_tprice']").val(price_sum + price_ship);
		$("input:hidden[name='point']").val(price_sum * 0.01);
		$("span#point").text((price_sum * 0.01).toLocaleString("en")+"원");
		
		console.log("DB전송포인트: ", $("input:hidden[name='point']").val());
		console.log("DB전송 총결제금액", $("input:hidden[name='order_tprice']").val());
			
		// === 주문상품 결제금액 구하기 끝 === //
		
		
		
		// === 쿠폰 삭제 버튼 클릭시 이벤트 시작 === //
		$("span.btn-outline-danger").on("click", e => {
			const discount = Number($("td#coupon_discount").text().split(",").join(""));
			
			
			//console.log($("span.total_price").text().indexOf("원"));
			const index = $("span.total_price").text().indexOf("원");
			//console.log($("span.total_price").text().substring(0, index));
			
			const currentPrice = Number($("span.total_price").text().substring(0, index).split(",").join(""));
			
			const changePrice = currentPrice + discount;
			
			$("input:hidden[name='order_tprice']").val(changePrice);
			console.log("DB전송 총결제금액", $("input:hidden[name='order_tprice']").val());
			
			
			$("span.total_price").html(changePrice.toLocaleString("en")+"원");
			$("span#discount").html("");
			$("table#couponSelect").hide();
		});// end of $("span.btn-outline-danger").on("click", e => {}) ----------------
		
});// end of $(document).ready(() => {}) --------------------- 






