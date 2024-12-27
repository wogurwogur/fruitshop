/**
 * 
 */


$(document).ready(()=> {
    // === 전체 datepicker 옵션 일괄 설정하기 ===  
    //     한번의 설정으로 $("input#fromDate"), $('input#toDate')의 옵션을 모두 설정할 수 있다.
    $(function() {
        //모든 datepicker에 대한 공통 옵션 설정
        $.datepicker.setDefaults({
            dateFormat: 'yy-mm-dd' //Input Display Format 변경
            ,showOtherMonths: true //빈 공간에 현재월의 앞뒤월의 날짜를 표시
            ,showMonthAfterYear:true //년도 먼저 나오고, 뒤에 월 표시
            ,changeYear: true //콤보박스에서 년 선택 가능
            ,changeMonth: true //콤보박스에서 월 선택 가능                
        // ,showOn: "both" //button:버튼을 표시하고,버튼을 눌러야만 달력 표시됨. both:버튼을 표시하고,버튼을 누르거나 input을 클릭하면 달력 표시됨.  
        // ,buttonImage: "http://jqueryui.com/resources/demos/datepicker/images/calendar.gif" //버튼 이미지 경로
        // ,buttonImageOnly: true //기본 버튼의 회색 부분을 없애고, 이미지만 보이게 함
        // ,buttonText: "선택" //버튼에 마우스 갖다 댔을 때 표시되는 텍스트                
            ,yearSuffix: "년" //달력의 년도 부분 뒤에 붙는 텍스트
            ,monthNamesShort: ['1','2','3','4','5','6','7','8','9','10','11','12'] //달력의 월 부분 텍스트
            ,monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'] //달력의 월 부분 Tooltip 텍스트
            ,dayNamesMin: ['일','월','화','수','목','금','토'] //달력의 요일 부분 텍스트
            ,dayNames: ['일요일','월요일','화요일','수요일','목요일','금요일','토요일'] //달력의 요일 부분 Tooltip 텍스트
        // ,minDate: "-1M" //최소 선택일자(-1D:하루전, -1M:한달전, -1Y:일년전)
        // ,maxDate: "+1M" //최대 선택일자(+1D:하루후, -1M:한달후, -1Y:일년후)                    
        });

        // input을 datepicker로 선언
        $("input#fromDate").datepicker();                    
        $("input#toDate").datepicker();
        
        // From의 초기값을 3개월 전으로 설정
        $('input#fromDate').datepicker('setDate', '-3M+1D'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, +1M:한달후, +1Y:일년후)
        
        // To의 초기값을 오늘로 설정
        $('input#toDate').datepicker('setDate', 'today'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, +1M:한달후, +1Y:일년후)
    });
	
	
	
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
		}
		
		alert("시작일 : "+ fromDate+ "\n종료일 : "+ toDate);
		
		// ajax 사용하여 where 절에 활용
	});
	// === 필터 버튼 조회 시 이벤트 처리 끝 === //
	
	
	// === 기간 필터 버튼 이벤트 처리 시작 === //
	$("button.btn-outline-dark").on("click", e => {
		// == 버튼 별 기간에 따라 분기하여 알맞은 날짜를 필터링 한다. == //
		
		// alert("버튼 몇번? :"+ $("button.btn-outline-dark").index($(e.target)));
		const btn_index = $("button.btn-outline-dark").index($(e.target));
		
		switch (btn_index) {			
			case 0:		// 오늘 버튼
				$("input#fromDate").val(getDate(btn_index));
				$("input#toDate").val(getDate(btn_index));
				
			case 1:		// 일주일 버튼
				$("input#fromDate").val(getDate(btn_index));
				$("input#toDate").val(getDate(0));	// 오늘 고정
				
			case 2:		// 1개월 버튼
				$("input#fromDate").val(getDate(btn_index));
				$("input#toDate").val(getDate(0));	// 오늘 고정
				
			case 3:		// 3개월 버튼
				$("input#fromDate").val(getDate(btn_index));
				$("input#toDate").val(getDate(0));	// 오늘 고정
				
			case 4:		// 6개월 버튼
				$("input#fromDate").val(getDate(btn_index));
				$("input#toDate").val(getDate(0));	// 오늘 고정
				
		} 
	});// end of $("button.btn-outline-dark").on("click", e => {}) ---------------------
	// === 기간 필터 버튼 이벤트 처리 끝 === //
	
	
	// === 주문정보 클릭 시 주문상세정보 모달 띄우기 시작 === //
	
	$(document).on("click", "table#orderList > tbody td", e => {
		//alert("테이블 버튼 몇번? :"+ $(e.target).parent().index());
		//console.log("테이블 버튼 몇번? :", $(e.target).parent().index());
		
		
		const order_no = $(e.target).parent().find(".order_no").text();			// 주문번호
		// 주문번호를 읽어와 ajax로 DB 조회 후 값 넣어주기
		
		const order_date = $(e.target).parent().find(".order_date").text();		// 주문일자
		const name = $(e.target).parent().find(".name").text();					// 주문자
		const prod_name = $(e.target).parent().find(".prod_name").text();		// 상품명
		const order_tprice = $(e.target).parent().find(".order_tprice").text();	// 총주문가격
		const ship_status = $(e.target).parent().find(".ship_status").val();	// 배송상태
		
		
		console.log("주문번호: ", order_no);
		console.log("주문일자: ", order_date);
		console.log("주문자: ", name);
		console.log("상품명: ", prod_name);
		console.log("총주문가격: ", order_tprice);
		console.log("배송상태: ", ship_status);
		
		$("input#order_no").val(order_no);
		$("input#order_date").val(order_date);
		$("input#name").val(name);
		$("input#prod_name").val(prod_name);
		$("input#order_tprice").val(order_tprice);
		$("select#modal_ship_status").val(ship_status);
		
		
		$("div#orderDetailInfo").modal();
		
	});
	// === 주문정보 클릭 시 주문상세정보로 모달 띄우기 끝 === //
	
	
	
	
	
	
}); // end of $(document).ready(()=> {}) ---------------------------



// Function Declaration
function getDate(index) {
	// == 기간 별 버튼에 따라 분기 하여 해당하는 날짜를 리턴해주는 함수. == //
	
	const now = new Date();     // 자바스크립트에서 현재날짜시각을 가져옴

    // console.log(now);
    // Mon Nov 11 2024 15:07:34 GMT+0900 (Korean Standard Time)
    // 요일 월 일 연 시 분 초 TZ

    // console.log(now.toLocaleString());
    // 11/11/2024, 3:09:05 PM
	
    const year  = now.getFullYear();     // 현재연도(2024)
    let month   = now.getMonth() + 1;    // 월은 0부터 시작하므로 +1 해야 현재월(11) 이 나옴
    let date    = now.getDate();         // 현재일(11)
    
	switch (index) {
		case 0:		// 오늘 버튼
			break;
			
		case 1:		// 일주일 버튼
			date = date - 6;
			break;
			
		case 2:		// 1개월 버튼
			month = month - 1;
			date = date + 1;
			break;
			
		case 3:		// 3개월 버튼
			month = month - 3;
			date = date + 1;
			break;
			
		case 4:		// 6개월 버튼
			month = month - 6;
			date = date + 1;
			break;
	}// end of switch -------------------
	
    if (month < 10)
        month = "0" + month;
    
    if (date < 10)
        date = "0" + date;

	return `${year}-${month}-${date}`;
}// end of function getDate(index) ----------------------




 
