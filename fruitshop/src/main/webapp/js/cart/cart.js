
$(document).ready(function () {
	
    // + 버튼 클릭 시
    $("button.plus").click(function () {
        const cartItem = $(this).parents(".cart_item"); // 상품 정보
        const input_prodcount = cartItem.find("input.prodcount");
        const price = parseInt(cartItem.find(".price").text().replace(/,/g, ""), 10); // 가격
        const prod_su = parseInt(cartItem.find("input[name='prodinventory']").val(), 10); // 상품 재고
        let count = parseInt(input_prodcount.val(), 10) || 1; // 수량

        if (count < prod_su) { // 수량이 재고보다 작다면
            count++;
            input_prodcount.val(count); // 수량 업데이트
            updateItemTotal(cartItem, count, price); // 상품 총액 업데이트
            updateCartTotal(); // 장바구니 합계 업데이트
        } else {
            alert("주문 가능한 최대 수량은 " + prod_su + "개입니다.");
        }
    });

    // - 버튼 클릭 시
    $("button.minus").click(function () {
        const cartItem = $(this).parents(".cart_item"); // 상품 정보
        const input_prodcount = cartItem.find("input.prodcount");
        const price = parseInt(cartItem.find(".price").text().replace(/,/g, ""), 10); // 가격
        let count = parseInt(input_prodcount.val(), 10) || 1; // 수량

        if (count > 1) { // 수량이 1보다 크다면
            count--;
            input_prodcount.val(count); // 수량 업데이트
            updateItemTotal(cartItem, count, price); // 총상품금액 업데이트
            updateCartTotal(); // 장바구니 합계 업데이트
        }
    });

    // 수량 직접 입력(keyup 사용)
    $("input.prodcount").on("keyup", function () {
        const cartItem = $(this).parents(".cart_item"); // 상품 정보
        const price = parseInt(cartItem.find(".price").text().replace(/,/g, ""), 10); // 가격
        const prod_su = parseInt(cartItem.find("input[name='prodinventory']").val(), 10); // 상품 재고
        let count = parseInt($(this).val(), 10); // 수량

        if (isNaN(count)) { // 숫자가 아니라면
            alert("숫자를 입력해주세요");
            count = 1;
            $(this).val(count);
        } else if (count < 1) { // 1보다 작다면
            alert("최소 주문 수량은 1개입니다.");
            count = 1;
            $(this).val(count);
        } else if (count > prod_su) { // 재고보다 많다면
            alert("주문 가능한 최대 수량은 " + prod_su + "개입니다.");
            count = prod_su;
            $(this).val(count);
        }

        updateItemTotal(cartItem, count, price); // 총상품금액 업데이트
        updateCartTotal(); // 장바구니 합계 업데이트
    });

    // 총상품금액
    function updateItemTotal(cartItem, count, price) {
		
        const totalPrice = price * count; // 총액 계산

        cartItem.find(".prodtotal").text(totalPrice.toLocaleString() + "원"); 
    }

    // 장바구니 합계
    function updateCartTotal() {
        let totalSum = 0;

        $(".cart_item").each(function () {
            const count = parseInt($(this).find("input.prodcount").val(), 10) || 1; // 수량
            const price = parseInt($(this).find(".price").text().replace(/,/g, ""), 10); // 숫자형 가격

            totalSum += count * price;
        });

        $(".cartsum p").html(`장바구니 합계&nbsp; : &nbsp;${totalSum.toLocaleString()}원`);
    }

    updateCartTotal();
	
	/////////////////////////////////////////////////////////////////////////////////////////////////
		
	   // 선택한 상품들의 합계
	   function updateSelectedTotal() {
		
	       let selectedSum = 0;
	
	       // 선택한 상품의 가격,수량
	       $("input:checkbox[name='selectedItems']:checked").each(function () {
	           const cartItem = $(this).parents(".cart_item");
	           const count = parseInt(cartItem.find("input.prodcount").val(), 10) || 1;
	           const price = parseInt(cartItem.find(".price").text().replace(/,/g, ""), 10);
	
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
