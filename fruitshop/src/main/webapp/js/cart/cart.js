
$(document).ready(function () {
	
    // + 버튼 클릭 시
    $("button.plus").click(function () {
        const cartItem = $(this).parents(".cart_item"); // 부모 요소에서 cart_item을 찾음
        const cartNo = cartItem.find("input[name='cart_no']").val() // 장바구니 번호
        const inputProdCount = cartItem.find("input.prodcount");
        const price = parseInt(cartItem.find(".price").text().replace(/,/g, ""), 10);
        const inventory = parseInt(cartItem.find("input[name='prodinventory']").val(), 10); // 상품 재고
        let count = parseInt(inputProdCount.val(), 10) || 1;

        console.log(cartNo); // cartNo 확인
        console.log(count); // 현재 수량 확인

        if (count < inventory) { // 수량이 재고보다 작다면
            count++;
            inputProdCount.val(count); // 수량 업데이트
            updateItemTotal(cartItem, count, price); // 상품 총액 업데이트
            updateCartTotal(); // 장바구니 합계 업데이트
            updateCartQuantity(cartNo, count); // 서버로 수량 업데이트 요청
        } else {
            alert("주문 가능한 최대 수량은 " + inventory + "개입니다.");
        }
    });

    // - 버튼 클릭 시
    $("button.minus").click(function () {
        const cartItem = $(this).parents(".cart_item"); // 부모 요소에서 cart_item을 찾음
        const cartNo = cartItem.find("input[name='cart_no']").val() // 장바구니 번호
        const inputProdCount = cartItem.find("input.prodcount");
        const price = parseInt(cartItem.find(".price").text().replace(/,/g, ""), 10);
        let count = parseInt(inputProdCount.val(), 10) || 1;

        console.log(cartNo); // cartNo 확인
        console.log(count); // 현재 수량 확인

        if (count > 1) { // 수량이 1보다 크다면
            count--;
            inputProdCount.val(count); // 수량 업데이트
            updateItemTotal(cartItem, count, price); // 상품 총액 업데이트
            updateCartTotal(); // 장바구니 합계 업데이트
            updateCartQuantity(cartNo, count); // 서버로 수량 업데이트 요청
        }
    });

    // 수량 직접 입력(keyup 사용)
    $("input.prodcount").on("keyup", function () {
        const cartItem = $(this).parents(".cart_item"); // 부모 요소에서 cart_item을 찾음
        const cartNo = cartItem.find("input[name='cart_no']").val() // 장바구니 번호
        const price = parseInt(cartItem.find(".price").text().replace(/,/g, ""), 10);
        const inventory = parseInt(cartItem.find("input[name='prodinventory']").val(), 10); // 상품 재고
        let count = parseInt($(this).val(), 10);

        console.log(cartNo); 
        console.log(count); 

        if (isNaN(count)) { // 숫자가 아니라면
            alert("숫자를 입력해주세요");
            count = 1;
            $(this).val(count);
        } else if (count < 1) { // 1보다 작다면
            alert("최소 주문 수량은 1개입니다.");
            count = 1;
            $(this).val(count);
        } else if (count > inventory) { // 재고보다 많다면
            alert("주문 가능한 최대 수량은 " + inventory + "개입니다.");
            count = inventory;
            $(this).val(count);
        }

        updateItemTotal(cartItem, count, price); // 총 상품 금액 업데이트
        updateCartTotal(); // 장바구니 합계 업데이트
        updateCartQuantity(cartNo, count); // 서버로 수량 업데이트 요청
    });

    //  수량 업데이트 
    function updateCartQuantity(cartNo, newQuantity) {
		$.ajax({
		    url: "/fruitshop/cart/cartEdit.ddg", // 요청 URL
		    type: "POST",
		    data: {
		        cart_no: cartNo,
		        cart_prodcount: newQuantity
		    },
		    success: function (response) {
		       // alert("수량 업데이트 성공);
		    },
		    error: function () {
		        alert("수량 업데이트 실패");
		    }
		});
    }

    // 총상품금액 업데이트
    function updateItemTotal(cartItem, count, price) {
        const totalPrice = price * count; // 총액 계산
        cartItem.find(".prodtotal").text(totalPrice.toLocaleString() + "원");
    }

    // 장바구니 합계 업데이트
    function updateCartTotal() {
        let totalSum = 0;

        $(".cart_item").each(function () {
            const count = parseInt($(this).find("input.prodcount").val(), 10) || 1; // 수량
            const price = parseInt($(this).find(".price").text().replace(/,/g, ""), 10); // 숫자형 가격
            totalSum += count * price;
        });

        $(".cartsum p").html(`장바구니 합계&nbsp; : &nbsp;${totalSum.toLocaleString()}원`);
    }

    updateCartTotal(); // 페이지 로드 시 초기 합계 계산


	
	/////////////////////////////////////////////////////////////////////////////////////////////////
		
	   // 선택한 상품들의 합계
	   function updateSelectedTotal() {
		
	       let selectedSum = 0;
	
	       // 선택한 상품의 가격,수량
	       $("input:checkbox[name='selectedItems']:checked").each(function () {
	           const cartItem = $(this).parents(".cart_item");
	           const count = parseInt(cartItem.find("input.prodcount").val(), 10) || 1;
	           const price = parseInt(cartItem.find(".price").text().replace(/,/g, ""), 10);
			   const inventory = parseInt(cartItem.find("input[name='prodinventory']").val(), 10); // 상품 재고
				
			   console.log(inventory);
			   
			   if(inventory == 0){
			   		alert(" 품절된 상품입니다.\n 품절상품을 제외하고 주문해주시기 바랍니다.");
					$(this).prop("checked", false);
			   }
			   
			   
			   
	           selectedSum += count * price; // 선택상품의 총합
	       });
		   
		  
	
	       if (selectedSum > 0) {
	           $(".selectsum").show(); // 선택 합계 표시
	           $(".selectsum p").html(`선택상품 합계&nbsp; : &nbsp;${selectedSum.toLocaleString()}원`);
	       } else {
	           $(".selectsum").hide(); // 선택 합계 숨기기
	       }
	   }

	   // 체크박스 상태 변경 시
	   $("input:checkbox[name='selectedItems']").change(function () {
	       updateSelectedTotal();
	   });

	   // 수량 버튼 클릭(+, -) 시
	   $("button.plus, button.minus").click(function () {
	       const cartItem = $(this).parents(".cart_item");
	       const countInput = cartItem.find("input.prodcount");
	       const count = parseInt(countInput.val(), 10) || 1;

	       if (count > 0) {
	           updateSelectedTotal();
	       }
	   });

	   // 수량 직접 입력(keyup 사용)
	   $("input.prodcount").on("keyup", function () {
	       const cartItem = $(this).parents(".cart_item");
	       const countInput = cartItem.find("input.prodcount");
	       let count = parseInt(countInput.val(), 10);

	       if (isNaN(count) || count < 1) {
	           alert("최소 주문 수량은 1개입니다.");
	           count = 1;
	           countInput.val(count);
	       }
	       updateSelectedTotal();
	   });

	   // 초기 합계 숨기기
	   $(".selectsum").hide();
	
});// end of $(document).ready(function () {})------------------------------------------------------
