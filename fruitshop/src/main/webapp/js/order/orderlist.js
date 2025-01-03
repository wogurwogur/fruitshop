/**
 * 
 */

let len   = 5;
//let start = "1";

$(document).ready(()=> {

    
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
	
	
	// === 주문정보 클릭 시 주문상세정보로 넘기는 이벤트 시작 === //
	$(document).on("click", "table#orderList > tbody td", e => {
		//alert("테이블 버튼 몇번? :"+ $(e.target).parent().index());
		//console.log("테이블 버튼 몇번? :", $(e.target).parent().index());
		
		const index = $(e.target).parent().index(); 	// 각 주문번호로 가야 함
		
		goOrderDetail(index);
		
	});
	// === 주문정보 클릭 시 주문상세정보로 넘기는 이벤트 끝 === //

	
	
	
	// === 필터 버튼 조회 시 이벤트 처리 시작 === //
	$("button.btn-secondary").on("click", () => {
		$("table#orderList > tfoot").html(`		
						<tr>
							<td colspan="3"></td>
							<td><button id="btnMore" type="button" class="btn btn-primary" value="">더보기</button></td>
							<td colspan="3"></td>
						</tr>`);
		$("input#countOrder").val("0");
		$("table#orderList > tbody").empty();
		getOrderCount();
		getOrderList("1");
	});// end of $("button.btn-secondary").on("click", () => {}) -----------------------
	// === 필터 버튼 조회 시 이벤트 처리 끝 === //
	
	

	setTimeout(() => {
		getOrderList("1");
	}, 300);
	

	setTimeout(() => {
		getOrderCount();
	}, 500);

	
	// == 주문목록 더보기 버튼 클릭액션 이벤트 == //
	$(document).on("click", "button#btnMore", function() {
		getOrderList($(this).val());
	});// end of $("button#btnMoreHIT").on("click", function() {}) --------------------
	
	
	// === 주문목록 각 행을 클릭 했을 때 이벤트 시작 === //
	$(document).on("click", "tr.orderItem", e => {
		const order_no = $(e.target).parent().find(".order_no").text();
		const ctxPath  = document.querySelector("input#contextPath").value;
		// alert("주문번호:"+ order_no);
		goOrderDetail(order_no, ctxPath);
	});// end of $(document).on("click", "tr.orderItem", e => {}) ---------------------


	// 주문번호 검색에 엔터 입력 시
	$(document).on("keydown", "input#searchWord", function(e) {
		if (e.keyCode == 13) {
			$("table#orderList > tfoot").html(`		
							<tr>
								<td colspan="3"></td>
								<td><button id="btnMore" type="button" class="btn btn-primary" value="">더보기</button></td>
								<td colspan="3"></td>
							</tr>`);
			$("input#countOrder").val("0");
			$("table#orderList > tbody").empty();
			getOrderCount();
			setTimeout(() => {
					getOrderList("1");
			}, 100);
		}
	});// end of $("input[name='searchWord']").on("keyup", e => {}) ----------------- 
	
	// 검색버튼 클릭 시
	$("span#goSearch").click(() => {
		$("table#orderList > tfoot").html(`		
						<tr>
							<td colspan="3"></td>
							<td><button id="btnMore" type="button" class="btn btn-primary" value="">더보기</button></td>
							<td colspan="3"></td>
						</tr>`);
		$("input#countOrder").val("0");
		$("table#orderList > tbody").empty();
		getOrderCount();
		
		setTimeout(() => {
			getOrderList("1");
		}, 100);
		
	});// end of $("button#goSearch").click(() => {}) ----------------
	
	
	// 배송상태 변경 시 이벤트 
	$("select[name='searchShip']").on("change", function() {
		$("table#orderList > tfoot").html(`		
						<tr>
							<td colspan="3"></td>
							<td><button id="btnMore" type="button" class="btn btn-primary" value="">더보기</button></td>
							<td colspan="3"></td>
						</tr>`);
		$("input#countOrder").val("0");
		$("table#orderList > tbody").empty();
		getOrderCount();
		setTimeout(() => {
			getOrderList("1");
		}, 100);
		
	});// end of $("select[name='searchShip']").on("change", function() {}) --------------~
	
	
	// 주문상태 변경 시 이벤트 
	$("select[name='searchType']").on("change", function() {
		$("table#orderList > tfoot").html(`		
						<tr>
							<td colspan="3"></td>
							<td><button id="btnMore" type="button" class="btn btn-primary" value="">더보기</button></td>
							<td colspan="3"></td>
						</tr>`);
		$("input#countOrder").val("0");
		$("table#orderList > tbody").empty();
		getOrderCount();
		setTimeout(() => {
			getOrderList("1");
		}, 100);
		
	});// end of $("select[name='sizePerPage']").on("change", function() {}) --------------------
	
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





// 현재 연월일 가져오는 함수
function getToday() {
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
	
    if (month < 10)
        month = "0" + month;
    
    if (date < 10)
        date = "0" + date;

	return `${year}-${month}-${date}`;
}// end of function getToday() -------------------------------




// == 필터링 된 주문의 목록을 구해오는 함수 == //
function getOrderList(start) {
	// const fromDate = $("input#fromDate").val();
	// const toDate = $("input#toDate").val();
	const fromDate    = document.querySelector("input#fromDate").value;
	const toDate      = document.querySelector("input#toDate").value;
	const ctxPath     = document.querySelector("input#contextPath").value;
	const searchWord  = document.querySelector("input#searchWord").value;
	const searchShip  = document.querySelector("select[name='searchShip']").value;
	const searchType  = document.querySelector("select[name='searchType']").value;
	
	// alert("시작일:"+ fromDate +"\n마지막일: "+ toDate);
	const today = getToday();
	
	// console.log("날짜 빼기?: ", toDate - fromDate);
	
	if (fromDate > toDate) {
		alert("시작일은 마지막일보다 이후여야 합니다.");
		return;
	}
	
	if (toDate > today) {
		alert("마지막일은 오늘보다 이전이어야 합니다.");
		//document.querySelector("input#toDate").value = getToday();
		history.go(0);
		return;
	}
	
	
	// DB 에서 조회 해와야 함 (ajax 통신 사용할 것)
	$.ajax({
		//url: "<%= request.getContextPath()%>/order/orderList.ddg",
		url: ctxPath+"/order/orderList.ddg",
		data: {"fromDate": fromDate, "toDate": toDate, "len": len, "start": start, "searchShip": searchShip, "searchType": searchType, "searchWord": searchWord},
		type: "GET",
		dataType: "JSON",
		success: function(json) {
			// console.log("결과확인용: ",json);
			
			let html = ``;
			
			if (start == 1 && json.length == 0) {
				html = `
					<tr>
						<td colspan="7">주문하신 상품이 존재하지 않습니다.</td>
					</tr>
				`;	
				$("table#orderList > tbody").html(html);
				$("table#orderList > tfoot").hide();					
			}
			else if (json.length > 0) {
				$.each(json, function(index, item) {
					//console.log("item => ", item);

					// console.log("item.ship_status => ", item.ship_status);
					// console.log("item.ship_status type => ", typeof item.ship_status);

					// console.log("json.length => ", typeof item.ship_status);

					let ship_status = "";
					switch (item.ship_status){
						case 1:
							ship_status = "배송준비중";
							break;
						case 2:
							ship_status = "배송중";
							break;
						case 3:
							ship_status = "배송완료";
							break;
					}
					let order_status = "";
					
					switch (item.order_status){
						case 1:
							order_status = "주문완료";
							break;
						case 2:
						case 3:
						case 4:
							order_status = "교환/반품";
							break;
						case 5:
							order_status = "구매확정";
							break;
					}

					html += `
						<tr class="orderItem" style="cursor: pointer;">
							<td class="order_no">${item.order_no}</td>
							<td>${item.order_date}</td>
							<td><img src='${ctxPath}/images/product/thumnail/${item.prod_thumnail}' style="width: 80px; height: 50px;" /></td>
							<td>${item.prod_name}</td>
							<td>${item.order_tprice.toLocaleString('en')} 원</td>
							<td>${order_status}</td>
							<td>${ship_status}</td>
						</tr>
					`;
				});// end of $.each(json, function(index, item) {}) ------------------- 
				
				$("table#orderList > tbody").append(html);
				$("table#orderList > tfoot").show();
				
				// input 태그에 여태까지 읽어온 값을 더함
				$("input#countOrder").val(Number($("input#countOrder").val()) + json.length);   // json.length: 실제로 상품을 읽어온 개수

				// 더보기... 버튼의 value 속성에 값을 지정하기
				$("button#btnMore").val(Number(start) + len);

				// 더보기... 버튼을 계속해서 클릭하여 countHIT 값과 totalHITCount 값이 일치하는 경우
				const totalOrderCount = Number($("input#totalOrderCount").val());
				const countOrder      = Number($("input#countOrder").val());

				console.log("totalOrderCount => ", totalOrderCount);
				console.log("countOrder => ", countOrder);
				console.log("json.length => ", json.length);

				if (totalOrderCount == countOrder) {
					html = `
						<tr>
							<td colspan="7">더이상 조회할 주문이 존재하지 않습니다.</td>
						</tr>
					`
					$("table#orderList > tfoot").html(html);
				}
			}
		},
		error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
				// alert("경로를 어디로 가야함???");
			}
	});
	
}// end of function getOrderList() -------------------


// == 필터링 된 주문의 총 개수를 구해오는 함수 == //
function getOrderCount() {
	const fromDate = document.querySelector("input#fromDate").value;
	const toDate   = document.querySelector("input#toDate").value;
	const ctxPath  = document.querySelector("input#contextPath").value;
	const searchWord  = document.querySelector("input#searchWord").value;
	const searchShip  = document.querySelector("select[name='searchShip']").value;
	const searchType  = document.querySelector("select[name='searchType']").value;
	// alert("시작일:"+ fromDate +"\n마지막일: "+ toDate);
	
	if (fromDate > toDate) {
		alert("시작일은 마지막일보다 이후여야 합니다.");
		return;
	}
	else {
		// DB 에서 조회 해와야 함 (ajax 통신 사용할 것)

		$.ajax({
			//url: "<%= request.getContextPath()%>/order/orderList.ddg",
			url: ctxPath+"/order/getOrderCount.ddg",
			data: {"fromDate": fromDate, "toDate": toDate, "searchShip": searchShip, "searchType": searchType, "searchWord": searchWord},
			type: "GET",
			dataType: "JSON",
			success: function(json) {
				console.log("json => ", json);

				$("input#totalOrderCount").val(json.totalOrderCount);
			},
			error: function(request, status, error){
					alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
					// alert("경로를 어디로 가야함???");
				}
		});
	}
}// end of function getOrderCount() --------------


function goOrderDetail(order_no, ctxPath) {
	// == 주문 상세 페이지로 넘기기 위한 함수 == //
	location.href=`${ctxPath}/order/orderDetail.ddg?order_no=${order_no}`;
}// end of function goOrderDetail(index) -------------- 
