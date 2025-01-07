/**
 * 
 */


$(document).ready(()=> {
    
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
	
	
	$("select#searchYear").on("change", e => {
		// alert($(e.target).val());
		// console.log($(e.target).val());
		
		const year = $(e.target).val();
		
		if (year == "") {
			$("input#fromDate").val(getDate(3));
			$("input#toDate").val(getDate(0));
			return;
		}
		
		$("input#fromDate").val(year+"-01-01");
		$("input#toDate").val(year+"-12-31");
		
	});// end of $("select#searchYear").on("change", e => {}) ------------------- 
	
	
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
	
    let year  	= now.getFullYear();     // 현재연도(2024)
    let month   = now.getMonth() + 1;    // 월은 0부터 시작하므로 +1 해야 현재월(11) 이 나옴
    let date    = now.getDate();         // 현재일(11)
    
	switch (index) {
		case 0:		// 오늘 버튼
			break;
			
		case 1:		// 일주일 버튼
			now.setDate(now.getDate() -6)
			
			year  = now.getFullYear();
			month = now.getMonth() + 1;
			date  = now.getDate();
			
			break;
			
		case 2:		// 1개월 버튼
			now.setMonth(now.getMonth()-1);
			now.setDate(now.getDate()+1);
			
			year  = now.getFullYear();
			month = now.getMonth() + 1;
			date  = now.getDate();
			
			break;
			
		case 3:		// 3개월 버튼
			now.setMonth(now.getMonth()-3);
			now.setDate(now.getDate()+1);
			
			year  = now.getFullYear();
			month = now.getMonth() + 1;
			date  = now.getDate();
			
			break;
			
		case 4:		// 6개월 버튼
			now.setMonth(now.getMonth()-6);
			now.setDate(now.getDate()+1);
			
			year  = now.getFullYear();
			month = now.getMonth() + 1;
			date  = now.getDate();
			break;
	}// end of switch -------------------
	
    if (month < 10)
        month = "0" + month;
    
    if (date < 10)
        date = "0" + date;

	return `${year}-${month}-${date}`;
}// end of function getDate(index) ----------------------

