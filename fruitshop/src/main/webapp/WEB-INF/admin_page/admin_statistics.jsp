	<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.Calendar" %>

<%
	Calendar time = Calendar.getInstance();
	int year = time.get(Calendar.YEAR);
	String ctxPath = request.getContextPath(); 
%>
<style type="text/css">

.highcharts-figure,
.highcharts-data-table table {
    min-width: 320px;
    max-width: 800px;
    margin: 1em auto;
}

.highcharts-data-table table {
    font-family: Verdana, sans-serif;
    border-collapse: collapse;
    border: 1px solid #ebebeb;
    margin: 10px auto;
    text-align: center;
    width: 100%;
    max-width: 500px;
}

.highcharts-data-table caption {
    padding: 1em 0;
    font-size: 1.2em;
    color: #555;
}

.highcharts-data-table th {
    font-weight: 600;
    padding: 0.5em;
}

.highcharts-data-table td,
.highcharts-data-table th,
.highcharts-data-table caption {
    padding: 0.5em;
}

.highcharts-data-table thead tr,
.highcharts-data-table tr:nth-child(even) {
    background: #f8f8f8;
}

.highcharts-data-table tr:hover {
    background: #f1f7ff;
}
</style>

<%-- chart.js --%>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<%-- HighCharts --%>
<script src="<%= ctxPath %>/Highcharts-10.3.1/code/highcharts.js"></script>
<script src="<%= ctxPath %>/Highcharts-10.3.1/code/modules/exporting.js"></script>
<script src="<%= ctxPath %>/Highcharts-10.3.1/code/modules/export-data.js"></script>
<script src="<%= ctxPath %>/Highcharts-10.3.1/code/modules/accessibility.js"></script>


<link rel="stylesheet" href="<%= request.getContextPath()%>/css/adminpage/statistics.css">
<script type="text/javascript" src="<%= request.getContextPath()%>/js/admin/statistics.js"></script>


<script type="text/javascript">
	$(document).ready(() => {
		
		$("input#fromDate").val("${requestScope.fromDate}");
		$("input#toDate").val("${requestScope.toDate}");
		
		visitStatistics();
		visitRankStatistics();
		$("div#prodOrderStatistics").hide();		// 주문상품 통계창 숨기기
		
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
	        
	        if (${empty requestScope.fromDate}){
		        // From의 초기값을 3개월 전으로 설정
		        $('input#fromDate').datepicker('setDate', '-3M+1D'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, +1M:한달후, +1Y:일년후)
	        }
	        
	        if (${empty requestScope.fromDate}){
	            // To의 초기값을 오늘로 설정
		        $('input#toDate').datepicker('setDate', 'today'); //(-1D:하루전, -1M:한달전, -1Y:일년전), (+1D:하루후, +1M:한달후, +1Y:일년후)
	        }
	    });
	
		
		$("div.menu_title").click(e => {
			// alert("야야호"+ $(e.target).index());
			$("div.menu_title").removeClass("active");
			$(e.target).addClass("active");
			
			const index = $("div.menu_title").index($(e.target));
			
			switch (index) {
				case 0:		// 회원관련 메뉴
					$("div#memberInfoTable").show();
					$("div#statistics_container").show();		// 그래프 통계창 보이기
					$("div#prodOrderStatistics").hide();		// 주문상품 통계창 숨기기
					
					$("div.btn-group").html(`
							<button type="button" class="btn btn-outline-dark member" id="today">방문자수</button>
							<button type="button" class="btn btn-outline-dark member" id="week">가입자수</button>
							`);
					
					visitStatistics();
					break;
				case 1:		// 상품관련 메뉴
					$("div#memberInfoTable").hide();
					$("div#statistics_container").show();		// 그래프 통계창 보이기
					$("div#prodOrderStatistics").hide();		// 주문상품 통계창 숨기기
					
					$("div.btn-group").html(`
							<button type="button" class="btn btn-outline-dark order">전체주문건</button>
							<button type="button" class="btn btn-outline-dark order">계절별주문</button>
							<button type="button" class="btn btn-outline-dark order">상품별주문</button>
							`);
					orderCntStatistics();
					break;
				case 2:		// 매출, 이익관련 메뉴
					$("div#memberInfoTable").hide();
					$("div#statistics_container").show();		// 그래프 통계창 보이기
					$("div#prodOrderStatistics").hide();		// 주문상품 통계창 숨기기
					
					$("div.btn-group").html(`
							<button type="button" class="btn btn-outline-dark revenue">매출</button>
							<button type="button" class="btn btn-outline-dark revenue">영업이익</button>
							`);
					revenueStatistics();
					break;
			}// end of switch -----------------
			
		});// end of $("div.menu_title").click(e => {}) ------------------------ 
		
		// === 회원관련 버튼 클릭 시 이벤트 시작 === //
		$(document).on("click", "button.member", function() {
			
			const index = $("button.member").index($(this));
			
			switch (index) {
				case 0 :	// 방문자수
					$("div#memberInfoTable").show();
					$("div#statistics_container").show();		// 그래프 통계창 보이기
					$("div#prodOrderStatistics").hide();		// 주문상품 통계창 숨기기
					
					visitStatistics();
					visitRankStatistics();
					break;
				case 1 :	// 가입자수
					$("div#memberInfoTable").hide();
					$("div#statistics_container").show();		// 그래프 통계창 보이기
					$("div#prodOrderStatistics").hide();		// 주문상품 통계창 숨기기
					
					regiStatistics();
					break;
			}// end of switch -----------------
			
		});// end of $("button.btn-outline-dark").on("click", function() => {}) ------------------------- 
		// === 회원관련 버튼 클릭 시 이벤트 끝 === //
		
		// === 상품관련 버튼 클릭 시 이벤트 시작 === //
		$(document).on("click", "button.order", function() {
			
			const index = $("button.order").index($(this));
			
			switch (index) {
				case 0 :	// 전체주문건
					$("div#memberInfoTable").hide();
					$("div#statistics_container").show();		// 그래프 통계창 보이기
					$("div#prodOrderStatistics").hide();		// 주문상품 통계창 숨기기
					
					orderCntStatistics();
					break;
				case 1 :	// 계절별상품 주문건
					$("div#memberInfoTable").hide();
					$("div#statistics_container").show();		// 그래프 통계창 보이기
					$("div#prodOrderStatistics").hide();		// 주문상품 통계창 숨기기
					
					seasonProdStatistics();
					break;
				case 2 :	// 상품별 주문건 랭킹
					$("div#memberInfoTable").hide();
					$("div#statistics_container").hide();		// 그래프 통계창 보이기
					$("div#prodOrderStatistics").show();		// 주문상품 통계창 숨기기
					groupByProdOrder();
					break;
			}// end of switch -----------------
			
		});// end of $("button.btn-outline-dark").on("click", function() => {}) ------------------------- 
		// === 상품관련 버튼 클릭 시 이벤트 끝 === //
		
		// === 매출/이익 관련 버튼 클릭시 이벤트 시작 === //
		$(document).on("click", "button.revenue", function() {
			const index = $("button.revenue").index($(this));
			
			switch (index) {
				case 0 :	// 매출
					$("div#memberInfoTable").hide();
					$("div#statistics_container").show();		// 그래프 통계창 보이기
					$("div#prodOrderStatistics").hide();		// 주문상품 통계창 숨기기
					
					revenueStatistics();
					break;
				case 1 :	// 영업이익
					$("div#memberInfoTable").hide();
					$("div#statistics_container").show();		// 그래프 통계창 보이기
					$("div#prodOrderStatistics").hide();		// 주문상품 통계창 숨기기
					incomeStatistics();
					break;
			}// end of switch -----------------
		});// end of $(document).on("click", "button.revenue", function() {}) -------------------
		// === 매출/이익 관련 버튼 클릭시 이벤트 끝 === //
		
		
		// === 방문자 랭킹 회원을 클릭 했을 시 회원의 상세정보로 이동 이벤트 시작 === //
		$(document).on("click", "tr.userInfo", function() {
			
			const user_no = $(this).find(".user_no").text().trim();
			console.log("확인용 user_no : "+ user_no);
			
			//alert(user_no);
			
			location.href="${pageContext.request.contextPath}/admin/adminMemberDetail.ddg?detail_user_no="+user_no;
		});// end of $(document).on("click", "tr.userInfo", function() {}) ------------------ 
		
		// === 방문자 랭킹 회원을 클릭 했을 시 회원의 상세정보로 이동 이벤트 끝 === //
		
		
		// === 정렬 기준 변화에 따라 이달의 방문자 오름차순/내림차순 정렬 시작 === //
		$("select[name='searchRange']").on("change", () => {
			visitRankStatistics();
		});
		// === 정렬 기준 변화에 따라 이달의 방문자 오름차순/내림차순 정렬 끝 === //
		
		// === 날짜 기준 변화에 따라 이달의 방문자 오름차순/내림차순 정렬 시작 === //
		$("select[name='searchMonth']").on("change", () => {
			visitRankStatistics();
		});
		// === 날짜 기준 변화에 따라 이달의 방문자 오름차순/내림차순 정렬 끝 === //
		
		
		$("select[name='orderSearchRange']").on("change", () => {
			groupByProdOrder();
		});// end of $("select[name='orderSearchRange']").on("change", () => {}) ----------------
		
		$("select[name='orderSearchType']").on("change", () => {
			groupByProdOrder();
		});// end of $("select[name='orderSearchRange']").on("change", () => {}) ----------------
		
		$("select[name='ordersearchMonth']").on("change", () => {
			groupByProdOrder();
		});// end of $("select[name='orderSearchRange']").on("change", () => {}) ----------------
		
		
		$("select#searchType").change(function(e){
	        func_choice($(e.target).val());
	        // $(e.target).val() 은 "" 또는 "myPurchase_byCategory" 또는 "myPurchase_byMonth_byCategory" 이다. 
	    });// end of $("select#searchType").change(function(e){}) --------------------
		
		
		
	});// end of $(document).ready(() => {}) -------------------------
	
	//Function Declaration 
	// == 날짜를 입력받아 오늘로부터 며칠 이전 날짜를 리턴해주는 함수. == //
	function getWeek(day) {
		
		const now = new Date();     // 자바스크립트에서 현재날짜시각을 가져옴

	    now.setDate(now.getDate() - day);
	    
	    let year  	= now.getFullYear();     // 현재연도(2024)
	    let month   = now.getMonth() + 1;    // 월은 0부터 시작하므로 +1 해야 현재월(11) 이 나옴
	    let date    = now.getDate();         // 현재일(11)
	    
	  	//console.log("month", month);
	  	//console.log("date: ",date);
	    
	    if (month < 10)
	        month = "0" + month;
	    
	    if (date < 10)
	        date = "0" + date;

		return `\${month}.\${date}`;
	}// end of function getDate(index) ----------------------
	
	
	// == 날짜를 입력받아 오늘로부터 몇 달 이전 날짜를 리턴해주는 함수. == //
	function getMonth(months) {
		
		const now = new Date();     // 자바스크립트에서 현재날짜시각을 가져옴

	    now.setMonth(now.getMonth() - months);
	    
	    let year  	= now.getFullYear();     // 현재연도(2024)
	    let month   = now.getMonth() + 1;    // 월은 0부터 시작하므로 +1 해야 현재월(11) 이 나옴
	    let date    = now.getDate();         // 현재일(11)
	    
	  	//console.log("month", month);
	  	//console.log("date: ",date);
	    
	    if (month < 10)
	        month = "0" + month;
	    
	    if (date < 10)
	        date = "0" + date;

	     year = String(year).substring(2);
	    
		return `\${year}.\${month}`;
	}// end of function getDate(index) ----------------------
	
	// === 방문횟수 유저 랭킹을 출력해주는 함수 === //
	function visitRankStatistics() {
		
		if(${empty sessionScope.loginuser}) {
			alert("로그인이 필요합니다.");
			location.href = "${pageContext.request.contextPath}/login/login.ddg";
            return; // 종료
	   	}
		
		const searchRange = $("select[name='searchRange']").val();
		const searchMonth = $("select[name='searchMonth']").val();
		
		$.ajax({
			url: "${pageContext.request.contextPath}/admin/statistics/visitMemberRank.ddg",
			type: "GET",
			data: {"viewType": 1, "searchRange": searchRange, "searchMonth": searchMonth},
			dataType: "JSON",
			success: function(json) {
				//console.log (json);
				let html = ``;
				
				if (json.length == 0) {
					html = `
						<tr>
							<td colspan="5">방문한 회원이 존재하지 않습니다.</td>
						</tr>
					`;
				}
				else if(json.length > 0){
					$.each(json, function(index, item) {
						
						const mobile = item.tel.substring(0,3) +"-"+ item.tel.substring(3,7) +"-"+ item.tel.substring(7,11);
						
						html += `
							<tr class="userInfo">
								<td class="user_no">\${item.user_no}<input type="hidden"  value="\${item.user_no}"</td>
								<td>\${item.name}</td>
								<td>\${mobile}</td>
								<td>\${item.email}</td>
								<td>\${item.visit_cnt}</td>
							</tr>
						`;
					});// end of $.each(json, function(index, item) {}) ----------------------
				  	
					$("table#visitUserRank > tbody").html(html);	
				}
			},
			error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
				// alert("경로를 어디로 가야함???");
			}
		});
		
	}// end of function visitStatistics() ------------------- 
	// === 방문횟수 유저 랭킹을 출력해주는 함수 === //
	
	
	// === 방문횟수 통계 정보를 출력해주는 함수 === //
	function visitStatistics() {
		$.ajax({
			url: "${pageContext.request.contextPath}/admin/statistics/visitMember.ddg",
			type: "GET",
			data: {"viewType": 1},
			dataType: "JSON",
			success: function(json) {
				//console.log (json);
				
				<%-- 주간 방문자 수 추이 차트 그리기 시작 --%>
				const ctx1 = document.getElementById('userWeek');

				// 차트가 그려져 있다면 삭제
				if (Chart.getChart(ctx1)) {
				    Chart.getChart(ctx1)?.destroy();
				}
				
			  	new Chart(ctx1, {
			    	type: 'line',
			    	data: {
			      	labels: [getWeek(6), getWeek(5), getWeek(4), getWeek(3), getWeek(2), getWeek(1), getWeek(0)],
			      	datasets: [{
			        label: '주간 방문자수 추이',
			        data: [
			        	`\${json.before_6days}`, `\${json.before_5days}`, `\${json.before_4days}`,
			        	`\${json.before_3days}`, `\${json.before_2days}`, `\${json.before_1days}`,
			        	`\${json.today}`
			        ],
			        borderWidth: 1,
					borderColor:'rgb(0, 102, 255)'
			      	},	
			      		{
					        label: '2주전 방문자수 추이',
					        data: [
					        	`\${json.before_13days}`, `\${json.before_12days}`, `\${json.before_11days}`,
					        	`\${json.before_10days}`, `\${json.before_9days}`, `\${json.before_8days}`,
					        	`\${json.before_7days}`
					        ],
					        borderWidth: 1,
					        borderColor:'rgb(0, 102, 0)'
				        }
			      	]
			    	},
			    	options: {
			      		scales: {
				        	y: {
				          		beginAtZero: false
				        	}
			      		}
			    	}
			  	});// end of new Chart(ctx, {}) ---------------------------
			  	<%-- 주간 방문자 수 추이 차트 그리기 끝 --%>
			  	
			  	<%-- 주간 방문자 수 비교 차트 그리기 시작 --%>
				const ctx2 = document.getElementById('userWeekcompare');

				// 차트가 그려져 있다면 삭제
				if (Chart.getChart(ctx2)) {
				    Chart.getChart(ctx2)?.destroy();
				}
			  	new Chart(ctx2, {
			    	type: 'bar',
			    	data: {
			      	labels: [getWeek(6), getWeek(5), getWeek(4), getWeek(3), getWeek(2), getWeek(1), getWeek(0)],
			      	datasets: [
				      	{
					        label: '전주 대비 방문자수 증감',
					        data: [
					        	Number(`\${json.before_6days}`) - Number(`\${json.before_13days}`),
					        	Number(`\${json.before_5days}`) - Number(`\${json.before_12days}`),
					        	Number(`\${json.before_4days}`) - Number(`\${json.before_11days}`),
					        	Number(`\${json.before_3days}`) - Number(`\${json.before_10days}`),
					        	Number(`\${json.before_2days}`) - Number(`\${json.before_9days}`),
					        	Number(`\${json.before_1days}`) - Number(`\${json.before_8days}`),
					        	Number(`\${json.today}`) - Number(`\${json.before_7days}`)
					        	
					        ],
					       // borderWidth: 1,
					        borderColor: '#FF6384',
					        backgroundColor: '#FFB1C1',
				      	}
			      	]
			    	},
			    	options: {
			      		scales: {
				        	y: {
				          		beginAtZero: true
				        	}
			      		}
			    	}
			  	});// end of new Chart(ctx, {}) ---------------------------
			  	<%-- 주간 방문자 수 비교 차트 그리기 끝 --%>
				
			  	<%-- 월간 방문자 수 추이 차트 그리기 시작 --%>
				const ctx3 = document.getElementById('userMonth');
				// 차트가 그려져 있다면 삭제
				if (Chart.getChart(ctx3)) {
				    Chart.getChart(ctx3)?.destroy();
				}
				
			  	new Chart(ctx3, {
			    	type: 'line',
			    	data: {
			      	labels: [getMonth(6), getMonth(5), getMonth(4), getMonth(3), getMonth(2), getMonth(1), getMonth(0)],
			      	datasets: [{
			        label: '월간 방문자수 추이',
			        data: [
			        	`\${json.before_6months}`, `\${json.before_5months}`, `\${json.before_4months}`,
			        	`\${json.before_3months}`, `\${json.before_2months}`, `\${json.before_1months}`,
			        	`\${json.this_month}`
			        ],
			        borderWidth: 1,
			        borderColor:'rgb(0, 102, 255)'
			      	},	
			      		{
					        label: '전년도 방문자수 추이',
					        data: [
					        	`\${json.before_18months}`, `\${json.before_17months}`, `\${json.before_16months}`,
					        	`\${json.before_15months}`, `\${json.before_14months}`, `\${json.before_13months}`,
					        	`\${json.before_12months}`
					        ],
					        borderWidth: 1,
					        borderColor:'rgb(0, 102, 0)'
				        }
			      	]
			    	},
			    	options: {
			      		scales: {
				        	y: {
				          		beginAtZero: true
				        	}
			      		}
			    	}
			  	});// end of new Chart(ctx, {}) ---------------------------
			  	<%-- 월간 방문자 수 추이 차트 그리기 끝 --%>
			  	
			  	<%-- 월간 방문자 수 비교 차트 그리기 시작 --%>
				const ctx4 = document.getElementById('userMonthcompare');
				// 차트가 그려져 있다면 삭제
				if (Chart.getChart(ctx4)) {
				    Chart.getChart(ctx4)?.destroy();
				}
				
			  	new Chart(ctx4, {
			    	type: 'bar',
			    	data: {
			      	labels: [getMonth(6), getMonth(5), getMonth(4), getMonth(3), getMonth(2), getMonth(1), getMonth(0)],
			      	datasets: [
				      	{
					        label: '전년 동월 대비 방문자 수 증감',
					        data: [
					        	Number(`\${json.before_6months}`) - Number(`\${json.before_18months}`),
					        	Number(`\${json.before_5months}`) - Number(`\${json.before_17months}`),
					        	Number(`\${json.before_4months}`) - Number(`\${json.before_16months}`),
					        	Number(`\${json.before_3months}`) - Number(`\${json.before_15months}`),
					        	Number(`\${json.before_2months}`) - Number(`\${json.before_14months}`),
					        	Number(`\${json.before_1months}`) - Number(`\${json.before_13months}`),
					        	Number(`\${json.this_month}`) - Number(`\${json.before_12months}`)
					        ],
					       // borderWidth: 1,
					        borderColor: '#FF6384',
					        backgroundColor: '#FFB1C1',
				      	}
			      	]
			    	},
			    	options: {
			      		scales: {
				        	y: {
				          		beginAtZero: true
				        	}
			      		}
			    	}
			  	});// end of new Chart(ctx, {}) ---------------------------
			  	<%-- 월간 가입자 수 비교 차트 그리기 끝 --%>
			  	
			},
			error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
				// alert("경로를 어디로 가야함???");
			}
		});
		
	}// end of function visitStatistics() ------------------- 
	// === 방문횟수 통계 정보를 출력해주는 함수 === //
	
	
	// === 가입자수 통계 정보를 출력해주는 함수 === //
	function regiStatistics() {
		$.ajax({
			url: "${pageContext.request.contextPath}/admin/statistics/regiMember.ddg",
			type: "GET",
			data: {"viewType": 1},
			dataType: "JSON",
			success: function(json) {
				//console.log (json);
				
				<%-- 주간 가입자 수 추이 차트 그리기 시작 --%>
				const ctx1 = document.getElementById('userWeek');

				// 차트가 그려져 있다면 삭제
				if (Chart.getChart(ctx1)) {
				    Chart.getChart(ctx1)?.destroy();
				}
				
			  	new Chart(ctx1, {
			    	type: 'line',
			    	data: {
			      	labels: [getWeek(6), getWeek(5), getWeek(4), getWeek(3), getWeek(2), getWeek(1), getWeek(0)],
			      	datasets: [{
			        label: '주간 가입자수 추이',
			        data: [
			        	`\${json.before_6days}`, `\${json.before_5days}`, `\${json.before_4days}`,
			        	`\${json.before_3days}`, `\${json.before_2days}`, `\${json.before_1days}`,
			        	`\${json.today}`
			        ],
			        borderWidth: 1,
			        borderColor:'rgb(0, 102, 255)'
			      	},	
			      		{
					        label: '2주전 가입자수 추이',
					        data: [
					        	`\${json.before_13days}`, `\${json.before_12days}`, `\${json.before_11days}`,
					        	`\${json.before_10days}`, `\${json.before_9days}`, `\${json.before_8days}`,
					        	`\${json.before_7days}`
					        ],
					        borderWidth: 1,
					        borderColor:'rgb(0, 102, 0)'
				        }
			      	]
			    	},
			    	options: {
			      		scales: {
				        	y: {
				          		beginAtZero: false
				        	}
			      		}
			    	}
			  	});// end of new Chart(ctx, {}) ---------------------------
			  	<%-- 주간 가입자 수 추이 차트 그리기 끝 --%>
			  	
			  	<%-- 주간 가입자 수 비교 차트 그리기 시작 --%>
				const ctx2 = document.getElementById('userWeekcompare');

				// 차트가 그려져 있다면 삭제
				if (Chart.getChart(ctx2)) {
				    Chart.getChart(ctx2)?.destroy();
				}
			  	new Chart(ctx2, {
			    	type: 'bar',
			    	data: {
			      	labels: [getWeek(6), getWeek(5), getWeek(4), getWeek(3), getWeek(2), getWeek(1), getWeek(0)],
			      	datasets: [
				      	{
					        label: '전 주 대비 가입자수 증감',
					        data: [
					        	Number(`\${json.before_6days}`) - Number(`\${json.before_13days}`),
					        	Number(`\${json.before_5days}`) - Number(`\${json.before_12days}`),
					        	Number(`\${json.before_4days}`) - Number(`\${json.before_11days}`),
					        	Number(`\${json.before_3days}`) - Number(`\${json.before_10days}`),
					        	Number(`\${json.before_2days}`) - Number(`\${json.before_9days}`),
					        	Number(`\${json.before_1days}`) - Number(`\${json.before_8days}`),
					        	Number(`\${json.today}`) - Number(`\${json.before_7days}`)
					        	
					        ],
					        //borderWidth: 1,
					        borderColor: '#FF6384',
					        backgroundColor: '#FFB1C1',
				      	}
			      	]
			    	},
			    	options: {
			      		scales: {
				        	y: {
				          		beginAtZero: true
				        	}
			      		}
			    	}
			  	});// end of new Chart(ctx, {}) ---------------------------
			  	<%-- 주간 가입자 수 비교 차트 그리기 끝 --%>
				
			  	<%-- 월간 가입자 수 추이 차트 그리기 시작 --%>
				const ctx3 = document.getElementById('userMonth');
				// 차트가 그려져 있다면 삭제
				if (Chart.getChart(ctx3)) {
				    Chart.getChart(ctx3)?.destroy();
				}
				
			  	new Chart(ctx3, {
			    	type: 'line',
			    	data: {
			      	labels: [getMonth(6), getMonth(5), getMonth(4), getMonth(3), getMonth(2), getMonth(1), getMonth(0)],
			      	datasets: [{
			        label: '월간 가입자수 추이',
			        data: [
			        	`\${json.before_6months}`, `\${json.before_5months}`, `\${json.before_4months}`,
			        	`\${json.before_3months}`, `\${json.before_2months}`, `\${json.before_1months}`,
			        	`\${json.this_month}`
			        ],
			        borderWidth: 1,
			        borderColor:'rgb(0, 102, 255)'
			      	},	
			      		{
					        label: '전년도 가입자수 추이',
					        data: [
					        	`\${json.before_18months}`, `\${json.before_17months}`, `\${json.before_16months}`,
					        	`\${json.before_15months}`, `\${json.before_14months}`, `\${json.before_13months}`,
					        	`\${json.before_12months}`
					        ],
					        borderWidth: 1,
					        borderColor:'rgb(0, 102, 0)'
				        }
			      	]
			    	},
			    	options: {
			      		scales: {
				        	y: {
				          		beginAtZero: true
				        	}
			      		}
			    	}
			  	});// end of new Chart(ctx, {}) ---------------------------
			  	<%-- 월간 가입자 수 추이 차트 그리기 끝 --%>
			  	
			  	<%-- 월간 가입자 수 비교 차트 그리기 시작 --%>
				const ctx4 = document.getElementById('userMonthcompare');
				// 차트가 그려져 있다면 삭제
				if (Chart.getChart(ctx4)) {
				    Chart.getChart(ctx4)?.destroy();
				}
				
			  	new Chart(ctx4, {
			    	type: 'bar',
			    	data: {
			      	labels: [getMonth(6), getMonth(5), getMonth(4), getMonth(3), getMonth(2), getMonth(1), getMonth(0)],
			      	datasets: [
				      	{
					        label: '전년 동월 대비 가입자 수 증감',
					        data: [
					        	Number(`\${json.before_6months}`) - Number(`\${json.before_18months}`),
					        	Number(`\${json.before_5months}`) - Number(`\${json.before_17months}`),
					        	Number(`\${json.before_4months}`) - Number(`\${json.before_16months}`),
					        	Number(`\${json.before_3months}`) - Number(`\${json.before_15months}`),
					        	Number(`\${json.before_2months}`) - Number(`\${json.before_14months}`),
					        	Number(`\${json.before_1months}`) - Number(`\${json.before_13months}`),
					        	Number(`\${json.this_month}`) - Number(`\${json.before_12months}`)
					        ],
					        //borderWidth: 1,
					        borderColor: '#FF6384',
					        backgroundColor: '#FFB1C1',
				      	}
			      	]
			    	},
			    	options: {
			      		scales: {
				        	y: {
				          		beginAtZero: true
				        	}
			      		}
			    	}
			  	});// end of new Chart(ctx, {}) ---------------------------
			  	<%-- 월간 가입자 수 비교 차트 그리기 끝 --%>
			  	
			},
			error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
				// alert("경로를 어디로 가야함???");
			}
		});
	}// end of function regiStatistics() ---------------- 
	// === 가입자수 통계 정보를 출력해주는 함수 === //

	//////////////////////////////////////////////////////////////////
	
	// === 주간 주문건수 통계 정보를 출력해주는 함수 === //
	function orderCntStatistics() {

		$.ajax({
			url: "${pageContext.request.contextPath}/admin/statistics/orderCount.ddg",
			type: "GET",
			data: {"viewType": 1},
			dataType: "JSON",
			success: function(json) {
				//console.log (json);
				
				<%-- 주간 주문건수 추이 차트 그리기 시작 --%>
				const ctx1 = document.getElementById('userWeek');

				// 차트가 그려져 있다면 삭제
				if (Chart.getChart(ctx1)) {
				    Chart.getChart(ctx1)?.destroy();
				}
				
			  	new Chart(ctx1, {
			    	type: 'line',
			    	data: {
			      	labels: [getWeek(6), getWeek(5), getWeek(4), getWeek(3), getWeek(2), getWeek(1), getWeek(0)],
			      	datasets: [{
			        label: '주간 주문건수 추이',
			        data: [
			        	`\${json.before_6days}`, `\${json.before_5days}`, `\${json.before_4days}`,
			        	`\${json.before_3days}`, `\${json.before_2days}`, `\${json.before_1days}`,
			        	`\${json.today}`
			        ],
			        borderWidth: 1,
			        borderColor:'rgb(0, 102, 255)'
			      	},	
			      		{
					        label: '2주전 주문건수 추이',
					        data: [
					        	`\${json.before_13days}`, `\${json.before_12days}`, `\${json.before_11days}`,
					        	`\${json.before_10days}`, `\${json.before_9days}`, `\${json.before_8days}`,
					        	`\${json.before_7days}`
					        ],
					        borderWidth: 1,
					        borderColor:'rgb(0, 102, 0)'
				        }
			      	]
			    	},
			    	options: {
			      		scales: {
				        	y: {
				          		beginAtZero: false
				        	}
			      		}
			    	}
			  	});// end of new Chart(ctx, {}) ---------------------------
			  	<%-- 주간 주문건수 추이 차트 그리기 끝 --%>
			  	
			  	<%-- 주간 주문건수 비교 차트 그리기 시작 --%>
				const ctx2 = document.getElementById('userWeekcompare');

				// 차트가 그려져 있다면 삭제
				if (Chart.getChart(ctx2)) {
				    Chart.getChart(ctx2)?.destroy();
				}
			  	new Chart(ctx2, {
			    	type: 'bar',
			    	data: {
			      	labels: [getWeek(6), getWeek(5), getWeek(4), getWeek(3), getWeek(2), getWeek(1), getWeek(0)],
			      	datasets: [
				      	{
					        label: '전 주 대비 주문건수 증감',
					        data: [
					        	Number(`\${json.before_6days}`) - Number(`\${json.before_13days}`),
					        	Number(`\${json.before_5days}`) - Number(`\${json.before_12days}`),
					        	Number(`\${json.before_4days}`) - Number(`\${json.before_11days}`),
					        	Number(`\${json.before_3days}`) - Number(`\${json.before_10days}`),
					        	Number(`\${json.before_2days}`) - Number(`\${json.before_9days}`),
					        	Number(`\${json.before_1days}`) - Number(`\${json.before_8days}`),
					        	Number(`\${json.today}`) - Number(`\${json.before_7days}`)
					        	
					        ],
					        //borderWidth: 1,
					        borderColor: '#FF6384',
					        backgroundColor: '#FFB1C1',
				      	}
			      	]
			    	},
			    	options: {
			      		scales: {
				        	y: {
				          		beginAtZero: true
				        	}
			      		}
			    	}
			  	});// end of new Chart(ctx, {}) ---------------------------
			  	<%-- 주간 주문건수 비교 차트 그리기 끝 --%>
				
			  	<%-- 월간 주문건수 추이 차트 그리기 시작 --%>
				const ctx3 = document.getElementById('userMonth');
				// 차트가 그려져 있다면 삭제
				if (Chart.getChart(ctx3)) {
				    Chart.getChart(ctx3)?.destroy();
				}
				
			  	new Chart(ctx3, {
			    	type: 'line',
			    	data: {
			      	labels: [getMonth(6), getMonth(5), getMonth(4), getMonth(3), getMonth(2), getMonth(1), getMonth(0)],
			      	datasets: [{
			        label: '월간 주문건수 추이',
			        data: [
			        	`\${json.before_6months}`, `\${json.before_5months}`, `\${json.before_4months}`,
			        	`\${json.before_3months}`, `\${json.before_2months}`, `\${json.before_1months}`,
			        	`\${json.this_month}`
			        ],
			        borderWidth: 1,
			        borderColor:'rgb(0, 102, 255)'
			      	},	
			      		{
					        label: '전년도 주문건수 추이',
					        data: [
					        	`\${json.before_18months}`, `\${json.before_17months}`, `\${json.before_16months}`,
					        	`\${json.before_15months}`, `\${json.before_14months}`, `\${json.before_13months}`,
					        	`\${json.before_12months}`
					        ],
					        borderWidth: 1,
					        borderColor:'rgb(0, 102, 0)'
				        }
			      	]
			    	},
			    	options: {
			      		scales: {
				        	y: {
				          		beginAtZero: true
				        	}
			      		}
			    	}
			  	});// end of new Chart(ctx, {}) ---------------------------
			  	<%-- 월간 주문건수 추이 차트 그리기 끝 --%>
			  	
			  	<%-- 월간 주문건수 비교 차트 그리기 시작 --%>
				const ctx4 = document.getElementById('userMonthcompare');
				// 차트가 그려져 있다면 삭제
				if (Chart.getChart(ctx4)) {
				    Chart.getChart(ctx4)?.destroy();
				}
				
			  	new Chart(ctx4, {
			    	type: 'bar',
			    	data: {
			      	labels: [getMonth(6), getMonth(5), getMonth(4), getMonth(3), getMonth(2), getMonth(1), getMonth(0)],
			      	datasets: [
				      	{
					        label: '전년 동월 대비 주문건수 증감',
					        data: [
					        	Number(`\${json.before_6months}`) - Number(`\${json.before_18months}`),
					        	Number(`\${json.before_5months}`) - Number(`\${json.before_17months}`),
					        	Number(`\${json.before_4months}`) - Number(`\${json.before_16months}`),
					        	Number(`\${json.before_3months}`) - Number(`\${json.before_15months}`),
					        	Number(`\${json.before_2months}`) - Number(`\${json.before_14months}`),
					        	Number(`\${json.before_1months}`) - Number(`\${json.before_13months}`),
					        	Number(`\${json.this_month}`) - Number(`\${json.before_12months}`)
					        ],
					        //borderWidth: 1,
					        borderColor: '#FF6384',
					        backgroundColor: '#FFB1C1',
				      	}
			      	]
			    	},
			    	options: {
			      		scales: {
				        	y: {
				          		beginAtZero: true
				        	}
			      		}
			    	}
			  	});// end of new Chart(ctx, {}) ---------------------------
			  	<%-- 월간 계절별과일 주문건수 비교 차트 그리기 끝 --%>
			  				  	
			},
			error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
				// alert("경로를 어디로 가야함???");
			}
		});
	}// end of function regiStatistics() ---------------- 
	// === 주문건수 통계 정보를 출력해주는 함수 === //
	
	
	// === 계절별상품 주문건수 통계 정보를 출력해주는 함수 === //
	function seasonProdStatistics() {
		
		$.ajax({
			url: "${pageContext.request.contextPath}/admin/statistics/groupBySeason.ddg",
			type: "GET",
			data: {"viewType": 1},
			dataType: "JSON",
			success: function(json) {
				//console.log (json);
				
				<%-- 계절별상품 주문건수 추이 차트 그리기 시작 --%>
				const ctx1 = document.getElementById('userWeek');

				// 차트가 그려져 있다면 삭제
				if (Chart.getChart(ctx1)) {
				    Chart.getChart(ctx1)?.destroy();
				}
				
			  	new Chart(ctx1, {
			    	type: 'line',
			    	data: {
			      	labels: [getWeek(6), getWeek(5), getWeek(4), getWeek(3), getWeek(2), getWeek(1), getWeek(0)],
			      	datasets: [
			      		{
					        label: '봄',
					        data: [
					        	`\${json.spring_before_6days}`, `\${json.spring_before_5days}`, `\${json.spring_before_4days}`,
					        	`\${json.spring_before_3days}`, `\${json.spring_before_2days}`, `\${json.spring_before_1days}`,
					        	`\${json.spring_today}`
					        ],
					        borderWidth: 1,
					        borderColor:'rgb(0, 153, 0)'
			      		},
			      		{
					        label: '여름',
					        data: [
					        	`\${json.summer_before_6days}`, `\${json.summer_before_5days}`, `\${json.summer_before_4days}`,
					        	`\${json.summer_before_3days}`, `\${json.summer_before_2days}`, `\${json.summer_before_1days}`,
					        	`\${json.summer_today}`
					        ],
					        borderWidth: 1,
					        borderColor:'rgb(0, 138, 230)'
				      	},
				      	{
					        label: '가을',
					        data: [
					        	`\${json.autumn_before_6days}`, `\${json.autumn_before_5days}`, `\${json.autumn_before_4days}`,
					        	`\${json.autumn_before_3days}`, `\${json.autumn_before_2days}`, `\${json.autumn_before_1days}`,
					        	`\${json.autumn_today}`
					        ],
					        borderWidth: 1,
					        borderColor:'rgb(204, 102, 0)'
				      	},
				      	{
					        label: '겨울',
					        data: [
					        	`\${json.winter_before_6days}`, `\${json.winter_before_5days}`, `\${json.winter_before_4days}`,
					        	`\${json.winter_before_3days}`, `\${json.winter_before_2days}`, `\${json.winter_before_1days}`,
					        	`\${json.winter_today}`
					        ],
					        borderWidth: 1,
					        borderColor:'rgb(204, 153, 255)'
				      	}
				      	
			      	]
			    	},
			    	options: {
			      		scales: {
				        	y: {
				          		beginAtZero: false
				        	}
			      		},
			      		plugins: {
			                title: {
			                    display: true,
			                    text: '계절별 상품 주문건수'
			                }
			            }
			    	}
			  	});// end of new Chart(ctx, {}) ---------------------------
			  	<%-- 계절별상품 주문건수 추이 차트 그리기 끝 --%>
			  	
			  	<%-- 계절별상품 주문건수 비교 차트 그리기 시작 --%>
				const ctx2 = document.getElementById('userWeekcompare');

				// 차트가 그려져 있다면 삭제
				if (Chart.getChart(ctx2)) {
				    Chart.getChart(ctx2)?.destroy();
				}
			  	new Chart(ctx2, {
			    	type: 'bar',
			    	data: {
			      	labels: [getWeek(6), getWeek(5), getWeek(4), getWeek(3), getWeek(2), getWeek(1), getWeek(0)],
			      	datasets: [
			      		{
					        label: '봄',
					        data: [
					        	Number(`\${json.spring_before_6days}`) - Number(`\${json.spring_before_13days}`),
					        	Number(`\${json.spring_before_5days}`) - Number(`\${json.spring_before_12days}`),
					        	Number(`\${json.spring_before_4days}`) - Number(`\${json.spring_before_11days}`),
					        	Number(`\${json.spring_before_3days}`) - Number(`\${json.spring_before_10days}`),
					        	Number(`\${json.spring_before_2days}`) - Number(`\${json.spring_before_9days}`),
					        	Number(`\${json.spring_before_1days}`) - Number(`\${json.spring_before_8days}`),
					        	Number(`\${json.spring_today}`) - Number(`\${json.spring_before_7days}`)
					        ],
					        //borderWidth: 1,
					        backgroundColor:'rgb(0, 153, 0)'
			      		},
			      		{
					        label: '여름',
					        data: [
					        	Number(`\${json.summer_before_6days}`) - Number(`\${json.summer_before_13days}`),
					        	Number(`\${json.summer_before_5days}`) - Number(`\${json.summer_before_12days}`),
					        	Number(`\${json.summer_before_4days}`) - Number(`\${json.summer_before_11days}`),
					        	Number(`\${json.summer_before_3days}`) - Number(`\${json.summer_before_10days}`),
					        	Number(`\${json.summer_before_2days}`) - Number(`\${json.summer_before_9days}`),
					        	Number(`\${json.summer_before_1days}`) - Number(`\${json.summer_before_8days}`),
					        	Number(`\${json.summer_today}`) - Number(`\${json.summer_before_7days}`)
					        ],
					        //borderWidth: 1,
					        backgroundColor:'rgb(0, 138, 230)'
				      	},
				      	{
					        label: '가을',
					        data: [
					        	Number(`\${json.autumn_before_6days}`) - Number(`\${json.autumn_before_13days}`),
					        	Number(`\${json.autumn_before_5days}`) - Number(`\${json.autumn_before_12days}`),
					        	Number(`\${json.autumn_before_4days}`) - Number(`\${json.autumn_before_11days}`),
					        	Number(`\${json.autumn_before_3days}`) - Number(`\${json.autumn_before_10days}`),
					        	Number(`\${json.autumn_before_2days}`) - Number(`\${json.autumn_before_9days}`),
					        	Number(`\${json.autumn_before_1days}`) - Number(`\${json.autumn_before_8days}`),
					        	Number(`\${json.autumn_today}`) - Number(`\${json.autumn_before_7days}`)
					        ],
					        //borderWidth: 1,
					        backgroundColor:'rgb(204, 102, 0)'
				      	},
				      	{
					        label: '겨울',
					        data: [
					        	Number(`\${json.winter_before_6days}`) - Number(`\${json.winter_before_13days}`),
					        	Number(`\${json.winter_before_5days}`) - Number(`\${json.winter_before_12days}`),
					        	Number(`\${json.winter_before_4days}`) - Number(`\${json.winter_before_11days}`),
					        	Number(`\${json.winter_before_3days}`) - Number(`\${json.winter_before_10days}`),
					        	Number(`\${json.winter_before_2days}`) - Number(`\${json.winter_before_9days}`),
					        	Number(`\${json.winter_before_1days}`) - Number(`\${json.winter_before_8days}`),
					        	Number(`\${json.winter_today}`) - Number(`\${json.winter_before_7days}`)
					        ],
					        //borderWidth: 1,
					        backgroundColor:'rgb(204, 153, 255)'
				      	}
			      	]
			    	},
			    	options: {
			      		scales: {
				        	y: {
				          		beginAtZero: true
				        	}
			      		},
				    	plugins: {
			                title: {
			                    display: true,
			                    text: '주간 계절별 상품 주문건수 증감'
			                }
			            }
			    	}
			  	});// end of new Chart(ctx, {}) ---------------------------
			  	<%-- 계절별상품 주문건수 비교 차트 그리기 끝 --%>
				
			  	<%-- 월간 계절별상품 주문건수 추이 차트 그리기 시작 --%>
				const ctx3 = document.getElementById('userMonth');
				// 차트가 그려져 있다면 삭제
				if (Chart.getChart(ctx3)) {
				    Chart.getChart(ctx3)?.destroy();
				}
				
			  	new Chart(ctx3, {
			    	type: 'line',
			    	data: {
			      	labels: [getMonth(6), getMonth(5), getMonth(4), getMonth(3), getMonth(2), getMonth(1), getMonth(0)],
			      	datasets: [
			        	{
					        label: '봄',
					        data: [
					        	`\${json.spring_before_6months}`, `\${json.spring_before_5months}`, `\${json.spring_before_4months}`,
					        	`\${json.spring_before_3months}`, `\${json.spring_before_2months}`, `\${json.spring_before_1months}`,
					        	`\${json.spring_this_month}`
					        ],
					        borderWidth: 1,
					        borderColor:'rgb(0, 153, 0)'
			      		},
			      		{
					        label: '여름',
					        data: [
					        	`\${json.summer_before_6months}`, `\${json.summer_before_5months}`, `\${json.summer_before_4months}`,
					        	`\${json.summer_before_3months}`, `\${json.summer_before_2months}`, `\${json.summer_before_1months}`,
					        	`\${json.summer_this_month}`
					        ],
					        borderWidth: 1,
					        borderColor:'rgb(0, 138, 230)'
				      	},
				      	{
					        label: '가을',
					        data: [
					        	`\${json.autumn_before_6months}`, `\${json.autumn_before_5months}`, `\${json.autumn_before_4months}`,
					        	`\${json.autumn_before_3months}`, `\${json.autumn_before_2months}`, `\${json.autumn_before_1months}`,
					        	`\${json.autumn_this_month}`
					        ],
					        borderWidth: 1,
					        borderColor:'rgb(204, 102, 0)'
				      	},
				      	{
					        label: '겨울',
					        data: [
					        	`\${json.winter_before_6months}`, `\${json.winter_before_5months}`, `\${json.winter_before_4months}`,
					        	`\${json.winter_before_3months}`, `\${json.winter_before_2months}`, `\${json.winter_before_1months}`,
					        	`\${json.winter_this_month}`
					        ],
					        borderWidth: 1,
					        borderColor:'rgb(204, 153, 255)'
				      	}
			        ]
			    	},
			    	options: {
			      		scales: {
				        	y: {
				          		beginAtZero: true
				        	}
			      		},
			      		plugins: {
			                title: {
			                    display: true,
			                    text: '월간 계절별 상품 주문건수 추이'
			                }
			            }
			    	}
			  	});// end of new Chart(ctx, {}) ---------------------------
			  	<%-- 월간 계절별상품 주문건수 추이 차트 그리기 끝 --%>
			  	
			  	<%-- 월간 계절별상품 주문건수 비교 차트 그리기 시작 --%>
				const ctx4 = document.getElementById('userMonthcompare');
				// 차트가 그려져 있다면 삭제
				if (Chart.getChart(ctx4)) {
				    Chart.getChart(ctx4)?.destroy();
				}
				
			  	new Chart(ctx4, {
			    	type: 'bar',
			    	data: {
			      	labels: [getMonth(6), getMonth(5), getMonth(4), getMonth(3), getMonth(2), getMonth(1), getMonth(0)],
			      	datasets: [
			      		{
					        label: '봄',
					        data: [
					        	Number(`\${json.spring_before_6months}`) - Number(`\${json.spring_before_18months}`),
					        	Number(`\${json.spring_before_5months}`) - Number(`\${json.spring_before_17months}`),
					        	Number(`\${json.spring_before_4months}`) - Number(`\${json.spring_before_16months}`),
					        	Number(`\${json.spring_before_3months}`) - Number(`\${json.spring_before_15months}`),
					        	Number(`\${json.spring_before_2months}`) - Number(`\${json.spring_before_14months}`),
					        	Number(`\${json.spring_before_1months}`) - Number(`\${json.spring_before_13months}`),
					        	Number(`\${json.spring_this_month}`) - Number(`\${json.spring_before_12months}`)
					        ],
					        //borderWidth: 1,
					        backgroundColor:'rgb(0, 153, 0)'
			      		},
			      		{
					        label: '여름',
					        data: [
					        	Number(`\${json.summer_before_6months}`) - Number(`\${json.summer_before_18months}`),
					        	Number(`\${json.summer_before_5months}`) - Number(`\${json.summer_before_17months}`),
					        	Number(`\${json.summer_before_4months}`) - Number(`\${json.summer_before_16months}`),
					        	Number(`\${json.summer_before_3months}`) - Number(`\${json.summer_before_15months}`),
					        	Number(`\${json.summer_before_2months}`) - Number(`\${json.summer_before_14months}`),
					        	Number(`\${json.summer_before_1months}`) - Number(`\${json.summer_before_13months}`),
					        	Number(`\${json.summer_this_month}`) - Number(`\${json.summer_before_12months}`)
					        ],
					        //borderWidth: 1,
					        backgroundColor:'rgb(0, 138, 230)'
				      	},
				      	{
					        label: '가을',
					        data: [
					        	Number(`\${json.autumn_before_6months}`) - Number(`\${json.autumn_before_18months}`),
					        	Number(`\${json.autumn_before_5months}`) - Number(`\${json.autumn_before_17months}`),
					        	Number(`\${json.autumn_before_4months}`) - Number(`\${json.autumn_before_16months}`),
					        	Number(`\${json.autumn_before_3months}`) - Number(`\${json.autumn_before_15months}`),
					        	Number(`\${json.autumn_before_2months}`) - Number(`\${json.autumn_before_14months}`),
					        	Number(`\${json.autumn_before_1months}`) - Number(`\${json.autumn_before_13months}`),
					        	Number(`\${json.autumn_this_month}`) - Number(`\${json.autumn_before_12months}`)
					        ],
					        //borderWidth: 1,
					        backgroundColor:'rgb(204, 102, 0)'
				      	},
				      	{
					        label: '겨울',
					        data: [
					        	Number(`\${json.winter_before_6months}`) - Number(`\${json.winter_before_18months}`),
					        	Number(`\${json.winter_before_5months}`) - Number(`\${json.winter_before_17months}`),
					        	Number(`\${json.winter_before_4months}`) - Number(`\${json.winter_before_16months}`),
					        	Number(`\${json.winter_before_3months}`) - Number(`\${json.winter_before_15months}`),
					        	Number(`\${json.winter_before_2months}`) - Number(`\${json.winter_before_14months}`),
					        	Number(`\${json.winter_before_1months}`) - Number(`\${json.winter_before_13months}`),
					        	Number(`\${json.winter_this_month}`) - Number(`\${json.winter_before_12months}`)
					        ],
					        //borderWidth: 1,
					        backgroundColor:'rgb(204, 153, 255)'
				      	}
			      	]
			    	},
			    	options: {
			      		scales: {
				        	y: {
				          		beginAtZero: true
				        	}
			      		},
			      		plugins: {
			                title: {
			                    display: true,
			                    text: '월간 계절별 상품 주문건수 증감'
			                }
			            }
			    	}
			  	});// end of new Chart(ctx, {}) ---------------------------
			  	<%-- 월간 계절별상품 주문건수 비교 차트 그리기 끝 --%>
			  	
			},
			error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
				// alert("경로를 어디로 가야함???");
			}
		});
		
	}// end of function visitStatistics() ------------------- 
	// === 계절별상품 주문건수 통계 정보를 출력해주는 함수 === //
	
	
	// === 상품별 주문 건수 통계를 출력해주는 함수 === //
	function groupByProdOrder() {
		
		const orderSearchRange = $("select[name='orderSearchRange']").val();
		const orderSearchType  = $("select[name='orderSearchType']").val();
		const ordersearchMonth  = $("select[name='ordersearchMonth']").val();
		
		$.ajax({
			url: "${pageContext.request.contextPath}/admin/statistics/prodRevenueRank.ddg",
			type: "GET",
			data: {"orderSearchRange": orderSearchRange, "orderSearchType": orderSearchType, "ordersearchMonth": ordersearchMonth},
			dataType: "JSON",
			success: function(json) {
				console.log(JSON.stringify(json));
				/*
					[{"prod_no": "34", "prod_name": "고당도 멜론 10kg", "prod_price": "32000", "sold_cnt": "10", "prod_revenue": "320000", "revenue_pct": "13.87"}, {}, {}]
				*/
				
				let v_html = ``;
				
				if (json.length == 0) {
					$("div#chart_container").hide();
					v_html = `
						<tr>
							<td colspan="6">주문 내역이 존재하지 않습니다.</td>
						</tr>
					`;
				}
				
				
				else if (json.length > 0) {
					$("div#chart_container").show();
					
					let resultArr = [];
					
					for (let i=0; i<json.length; i++) {
						let obj;
	           	  		
	           	  		if (i == 0) {
	           	  			obj = {name: json[i].prod_name,
	           			           y: Number(json[i].revenue_pct),
	           			           sliced: true,
	           			           selected: true};
	           	  		}
	           	  		else {
	           	  			obj = {name: json[i].prod_name,
	       			        	   y: Number(json[i].revenue_pct)};
	           	  		}
	           	  		
	           	  		resultArr.push(obj);	// 배열 속에 객체 넣기
					}// end of for() ----------------------
					
					/////////////////////////////////////////////////
	        		Highcharts.chart('chart_container', {			// 'container' 는 id
	        		    chart: {
	        		        plotBackgroundColor: null,
	        		        plotBorderWidth: null,
	        		        plotShadow: false,
	        		        type: 'pie'
	        		    },
	        		    title: {	// 차트 제목
	        		        text: '월간 주문 Top 10 상품'
	        		    },
	        		    tooltip: {
	        		        pointFormat: '{series.name}: <b>{point.percentage:.2f}%</b>'	// 소수 몇 째 자리까지 표기할 것인지 1f: 첫 째자리, 2f: 둘째자리
	        		    },
	        		    accessibility: {
	        		        point: {
	        		            valueSuffix: '%'
	        		        }
	        		    },
	        		    plotOptions: {
	        		        pie: {
	        		            allowPointSelect: true,
	        		            cursor: 'pointer',
	        		            dataLabels: {
	        		                enabled: true,
	        		                format: '<b>{point.name}</b>: {point.percentage:.2f} %' // 소수 몇 째 자리까지 표기할 것인지 1f: 첫 째자리, 2f: 둘째자리
	        		            }
	        		        }
	        		    },
	        		    series: [{
	        		        name: '매출 비중',		// 마우스커서를 올렸을 때 비율에 표시되는 제목
	        		        colorByPoint: true,
	        		        data: resultArr
	        		    }]
	        		});
	        		/////////////////////////////////////////////////
					
	        		$.each(json, function(index, item) {
	        			//let prod_price = Number(\${item.prod_price});
	        			//let prod_revenue = Number(\${item.prod_revenue});
	        			//let revenue_pct = Number(\${item.revenue_pct});
	        			
	        			v_html += `
	        				<tr style="cursor: pointer;">
	        					<td>\${item.prod_no}</td>
	        					<td>\${item.prod_name}</td>
	        					<td>\${Number(item.prod_price).toLocaleString('en')}</td>
	        					<td>\${item.sold_cnt}</td>
	        					<td>\${Number(item.prod_revenue).toLocaleString('en')}</td>
	        					<td>\${Number(item.revenue_pct)}%</td>
	        				</tr>
	        			`;
	        		});// end of $.each(json, function(index, item) {}) ------------------- 
	        		
				}
				$("table#orderProdRank > tbody").html(v_html);
				
        		
			},
			error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
				// alert("경로를 어디로 가야함???");
			}
		});
		
	}// end of function func_choice(searchTypeVal) ------------------- 
	// === 상품별 주문 건수 통계를 출력해주는 함수 === //
	
	
	//////////////////////////////////////////////////////////////////
	// === 매출 통계 정보를 출력해주는 함수 === //
	function revenueStatistics() {

		$.ajax({
			url: "${pageContext.request.contextPath}/admin/statistics/getRevenue.ddg",
			type: "GET",
			data: {"viewType": 1},
			dataType: "JSON",
			success: function(json) {
				//console.log (json);
				
				<%-- 주간 매출액 추이 차트 그리기 시작 --%>
				const ctx1 = document.getElementById('userWeek');

				// 차트가 그려져 있다면 삭제
				if (Chart.getChart(ctx1)) {
				    Chart.getChart(ctx1)?.destroy();
				}
				
			  	new Chart(ctx1, {
			    	type: 'line',
			    	data: {
			      	labels: [getWeek(6), getWeek(5), getWeek(4), getWeek(3), getWeek(2), getWeek(1), getWeek(0)],
			      	datasets: [{
			        label: '주간 매출액 추이',
			        data: [
			        	`\${json.before_6days}`, `\${json.before_5days}`, `\${json.before_4days}`,
			        	`\${json.before_3days}`, `\${json.before_2days}`, `\${json.before_1days}`,
			        	`\${json.today}`
			        ],
			        borderWidth: 1,
			        borderColor:'rgb(0, 102, 255)'
			      	},	
			      		{
					        label: '2주전 매출액 추이',
					        data: [
					        	`\${json.before_13days}`, `\${json.before_12days}`, `\${json.before_11days}`,
					        	`\${json.before_10days}`, `\${json.before_9days}`, `\${json.before_8days}`,
					        	`\${json.before_7days}`
					        ],
					        borderWidth: 1,
					        borderColor:'rgb(0, 102, 0)'
				        }
			      	]
			    	},
			    	options: {
			      		scales: {
				        	y: {
				          		beginAtZero: false
				        	}
			      		}
			    	}
			  	});// end of new Chart(ctx, {}) ---------------------------
			  	<%-- 주간 매출액 추이 차트 그리기 끝 --%>
			  	
			  	<%-- 주간 매출액 비교 차트 그리기 시작 --%>
				const ctx2 = document.getElementById('userWeekcompare');

				// 차트가 그려져 있다면 삭제
				if (Chart.getChart(ctx2)) {
				    Chart.getChart(ctx2)?.destroy();
				}
			  	new Chart(ctx2, {
			    	type: 'bar',
			    	data: {
			      	labels: [getWeek(6), getWeek(5), getWeek(4), getWeek(3), getWeek(2), getWeek(1), getWeek(0)],
			      	datasets: [
				      	{
					        label: '전 주 대비 매출액 증감',
					        data: [
					        	Number(`\${json.before_6days}`) - Number(`\${json.before_13days}`),
					        	Number(`\${json.before_5days}`) - Number(`\${json.before_12days}`),
					        	Number(`\${json.before_4days}`) - Number(`\${json.before_11days}`),
					        	Number(`\${json.before_3days}`) - Number(`\${json.before_10days}`),
					        	Number(`\${json.before_2days}`) - Number(`\${json.before_9days}`),
					        	Number(`\${json.before_1days}`) - Number(`\${json.before_8days}`),
					        	Number(`\${json.today}`) - Number(`\${json.before_7days}`)
					        	
					        ],
					        //borderWidth: 1,
					        borderColor: '#FF6384',
					        backgroundColor: '#FFB1C1',
				      	}
			      	]
			    	},
			    	options: {
			      		scales: {
				        	y: {
				          		beginAtZero: true
				        	}
			      		}
			    	}
			  	});// end of new Chart(ctx, {}) ---------------------------
			  	<%-- 주간 매출액 비교 차트 그리기 끝 --%>
				
			  	<%-- 월간 매출액 추이 차트 그리기 시작 --%>
				const ctx3 = document.getElementById('userMonth');
				// 차트가 그려져 있다면 삭제
				if (Chart.getChart(ctx3)) {
				    Chart.getChart(ctx3)?.destroy();
				}
				
			  	new Chart(ctx3, {
			    	type: 'line',
			    	data: {
			      	labels: [getMonth(6), getMonth(5), getMonth(4), getMonth(3), getMonth(2), getMonth(1), getMonth(0)],
			      	datasets: [{
			        label: '월간 매출액 추이',
			        data: [
			        	`\${json.before_6months}`, `\${json.before_5months}`, `\${json.before_4months}`,
			        	`\${json.before_3months}`, `\${json.before_2months}`, `\${json.before_1months}`,
			        	`\${json.this_month}`
			        ],
			        borderWidth: 1,
			        borderColor:'rgb(0, 102, 255)'
			      	},	
			      		{
					        label: '전년도 매출액 추이',
					        data: [
					        	`\${json.before_18months}`, `\${json.before_17months}`, `\${json.before_16months}`,
					        	`\${json.before_15months}`, `\${json.before_14months}`, `\${json.before_13months}`,
					        	`\${json.before_12months}`
					        ],
					        borderWidth: 1,
					        borderColor:'rgb(0, 102, 0)'
				        }
			      	]
			    	},
			    	options: {
			      		scales: {
				        	y: {
				          		beginAtZero: true
				        	}
			      		}
			    	}
			  	});// end of new Chart(ctx, {}) ---------------------------
			  	<%-- 월간 매출액 추이 차트 그리기 끝 --%>
			  	
			  	<%-- 월간 매출액 비교 차트 그리기 시작 --%>
				const ctx4 = document.getElementById('userMonthcompare');
				// 차트가 그려져 있다면 삭제
				if (Chart.getChart(ctx4)) {
				    Chart.getChart(ctx4)?.destroy();
				}
				
			  	new Chart(ctx4, {
			    	type: 'bar',
			    	data: {
			      	labels: [getMonth(6), getMonth(5), getMonth(4), getMonth(3), getMonth(2), getMonth(1), getMonth(0)],
			      	datasets: [
				      	{
					        label: '전년 동월 대비 매출액 증감',
					        data: [
					        	Number(`\${json.before_6months}`) - Number(`\${json.before_18months}`),
					        	Number(`\${json.before_5months}`) - Number(`\${json.before_17months}`),
					        	Number(`\${json.before_4months}`) - Number(`\${json.before_16months}`),
					        	Number(`\${json.before_3months}`) - Number(`\${json.before_15months}`),
					        	Number(`\${json.before_2months}`) - Number(`\${json.before_14months}`),
					        	Number(`\${json.before_1months}`) - Number(`\${json.before_13months}`),
					        	Number(`\${json.this_month}`) - Number(`\${json.before_12months}`)
					        ],
					        //borderWidth: 1,
					        borderColor: '#FF6384',
					        backgroundColor: '#FFB1C1',
				      	}
			      	]
			    	},
			    	options: {
			      		scales: {
				        	y: {
				          		beginAtZero: true
				        	}
			      		}
			    	}
			  	});// end of new Chart(ctx, {}) ---------------------------
			  	<%-- 월간 매출액 비교 차트 그리기 끝 --%>
			  	
			},
			error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
				// alert("경로를 어디로 가야함???");
			}
		});
	}// end of function regiStatistics() ---------------- 
	// === 매출 통계 정보를 출력해주는 함수 === //
	
	
	// === 영업이익 통계 정보를 출력해주는 함수 === //
	function incomeStatistics() {

		$.ajax({
			url: "${pageContext.request.contextPath}/admin/statistics/getIncome.ddg",
			type: "GET",
			data: {"viewType": 1},
			dataType: "JSON",
			success: function(json) {
				//console.log (json);
				
				<%-- 주간 영업이익 추이 차트 그리기 시작 --%>
				const ctx1 = document.getElementById('userWeek');

				// 차트가 그려져 있다면 삭제
				if (Chart.getChart(ctx1)) {
				    Chart.getChart(ctx1)?.destroy();
				}
				
			  	new Chart(ctx1, {
			    	type: 'line',
			    	data: {
			      	labels: [getWeek(6), getWeek(5), getWeek(4), getWeek(3), getWeek(2), getWeek(1), getWeek(0)],
			      	datasets: [{
			        label: '주간 영업이익 추이',
			        data: [
			        	`\${json.before_6days}`, `\${json.before_5days}`, `\${json.before_4days}`,
			        	`\${json.before_3days}`, `\${json.before_2days}`, `\${json.before_1days}`,
			        	`\${json.today}`
			        ],
			        borderWidth: 1,
			        borderColor:'rgb(0, 102, 255)'
			      	},	
			      		{
					        label: '2주전 영업이익 추이',
					        data: [
					        	`\${json.before_13days}`, `\${json.before_12days}`, `\${json.before_11days}`,
					        	`\${json.before_10days}`, `\${json.before_9days}`, `\${json.before_8days}`,
					        	`\${json.before_7days}`
					        ],
					        borderWidth: 1,
					        borderColor:'rgb(0, 102, 0)'
				        }
			      	]
			    	},
			    	options: {
			      		scales: {
				        	y: {
				          		beginAtZero: false
				        	}
			      		}
			    	}
			  	});// end of new Chart(ctx, {}) ---------------------------
			  	<%-- 주간 영업이익 추이 차트 그리기 끝 --%>
			  	
			  	<%-- 주간 영업이익 비교 차트 그리기 시작 --%>
				const ctx2 = document.getElementById('userWeekcompare');

				// 차트가 그려져 있다면 삭제
				if (Chart.getChart(ctx2)) {
				    Chart.getChart(ctx2)?.destroy();
				}
			  	new Chart(ctx2, {
			    	type: 'bar',
			    	data: {
			      	labels: [getWeek(6), getWeek(5), getWeek(4), getWeek(3), getWeek(2), getWeek(1), getWeek(0)],
			      	datasets: [
				      	{
					        label: '전 주 대비 영업이익 증감',
					        data: [
					        	Number(`\${json.before_6days}`) - Number(`\${json.before_13days}`),
					        	Number(`\${json.before_5days}`) - Number(`\${json.before_12days}`),
					        	Number(`\${json.before_4days}`) - Number(`\${json.before_11days}`),
					        	Number(`\${json.before_3days}`) - Number(`\${json.before_10days}`),
					        	Number(`\${json.before_2days}`) - Number(`\${json.before_9days}`),
					        	Number(`\${json.before_1days}`) - Number(`\${json.before_8days}`),
					        	Number(`\${json.today}`) - Number(`\${json.before_7days}`)
					        ],
					        //borderWidth: 1,
					        borderColor: '#FF6384',
					        backgroundColor: '#FFB1C1',
				      	}
			      	]
			    	},
			    	options: {
			      		scales: {
				        	y: {
				          		beginAtZero: true
				        	}
			      		}
			    	}
			  	});// end of new Chart(ctx, {}) ---------------------------
			  	<%-- 주간 영업이익 비교 차트 그리기 끝 --%>
				
			  	<%-- 월간 영업이익 추이 차트 그리기 시작 --%>
				const ctx3 = document.getElementById('userMonth');
				// 차트가 그려져 있다면 삭제
				if (Chart.getChart(ctx3)) {
				    Chart.getChart(ctx3)?.destroy();
				}
				
			  	new Chart(ctx3, {
			    	type: 'line',
			    	data: {
			      	labels: [getMonth(6), getMonth(5), getMonth(4), getMonth(3), getMonth(2), getMonth(1), getMonth(0)],
			      	datasets: [{
			        label: '월간 영업이익 추이',
			        data: [
			        	`\${json.before_6months}`, `\${json.before_5months}`, `\${json.before_4months}`,
			        	`\${json.before_3months}`, `\${json.before_2months}`, `\${json.before_1months}`,
			        	`\${json.this_month}`
			        ],
			        borderWidth: 1,
			        borderColor:'rgb(0, 102, 255)'
			      	},	
			      		{
					        label: '전년도 영업이익 추이',
					        data: [
					        	`\${json.before_18months}`, `\${json.before_17months}`, `\${json.before_16months}`,
					        	`\${json.before_15months}`, `\${json.before_14months}`, `\${json.before_13months}`,
					        	`\${json.before_12months}`
					        ],
					        borderWidth: 1,
					        borderColor:'rgb(0, 102, 0)'
				        }
			      	]
			    	},
			    	options: {
			      		scales: {
				        	y: {
				          		beginAtZero: true
				        	}
			      		}
			    	}
			  	});// end of new Chart(ctx, {}) ---------------------------
			  	<%-- 월간 영업이익 추이 차트 그리기 끝 --%>
			  	
			  	<%-- 월간 영업이익 비교 차트 그리기 시작 --%>
				const ctx4 = document.getElementById('userMonthcompare');
				// 차트가 그려져 있다면 삭제
				if (Chart.getChart(ctx4)) {
				    Chart.getChart(ctx4)?.destroy();
				}
				
			  	new Chart(ctx4, {
			    	type: 'bar',
			    	data: {
			      	labels: [getMonth(6), getMonth(5), getMonth(4), getMonth(3), getMonth(2), getMonth(1), getMonth(0)],
			      	datasets: [
				      	{
					        label: '전년 동월 대비 영업이익 증감',
					        data: [
					        	Number(`\${json.before_6months}`) - Number(`\${json.before_18months}`),
					        	Number(`\${json.before_5months}`) - Number(`\${json.before_17months}`),
					        	Number(`\${json.before_4months}`) - Number(`\${json.before_16months}`),
					        	Number(`\${json.before_3months}`) - Number(`\${json.before_15months}`),
					        	Number(`\${json.before_2months}`) - Number(`\${json.before_14months}`),
					        	Number(`\${json.before_1months}`) - Number(`\${json.before_13months}`),
					        	Number(`\${json.this_month}`) - Number(`\${json.before_12months}`)
					        ],
					        //borderWidth: 1,
					        borderColor: '#FF6384',
					        backgroundColor: '#FFB1C1',
				      	}
			      	]
			    	},
			    	options: {
			      		scales: {
				        	y: {
				          		beginAtZero: true
				        	}
			      		}
			    	}
			  	});// end of new Chart(ctx, {}) ---------------------------
			  	<%-- 월간 영업이익 비교 차트 그리기 끝 --%>
			  	
			},
			error: function(request, status, error){
				alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
				// alert("경로를 어디로 가야함???");
			}
		});
	}// end of function regiStatistics() ---------------- 
	// === 영업이익 통계 정보를 출력해주는 함수 === //
	
</script>

<div style="margin-top: 3%;" id="container">

<%-- 페이지 도착 시 기본 조회 기간은 3개월 --%>
	<%-- 주문내역조회 메뉴 바 시작 --%>
	<div id="menu_filter">
	
		<div class="menu_title active">
			회원관련
		</div>
		<div class="menu_title">
			상품관련
		</div>
		<div class="menu_title">
			매출/이익
		</div>
		<div id="menu_title_nbsp">
		</div>
	</div>
	<%-- 주문내역조회 메뉴 바 끝 --%>
	
	<%-- 버튼메뉴 시작 --%>
	<div id="order_time">
		<div style=" margin: 0 auto; padding: 2%;" class="btn-group" role="group" aria-label="Date Select Filter">
			<button type="button" class="btn btn-outline-dark member" id="today">방문자수</button>
			<button type="button" class="btn btn-outline-dark member" id="week">가입자수</button>
		</div>
	</div>
	
	<div id="filter_desc">
		<ul>
			<li>메뉴의 버튼 클릭 시 해당하는 자료를 볼 수 있습니다.</li>
			<li>각 자료는 최근 일주일, 최근 6개월의 자료가 조회되며 특정 기간이 필요한 경우 개발팀에 요청하시기 바랍니다.</li>
		</ul>
	</div>
	<%-- 버튼메뉴 끝 --%>
	
	
<div id="statistics_container">	
	<%-- 통계 본문 보여주기 시작 --%>
	<div style="margin-left: 1%;" class="h6 mt-5">
		<form name="statistics_searchFrm">
			<span id="bodyTitle">그래프</span>
			<%--
			<select style="float: right; height: 30px;" name="searchRange">
				<option value="">정렬기준</option>			
				<option value="desc">높은순</option>			
				<option value="asc">낮은순</option>			
			</select>
			
			<select style="margin-right: 0.5%; float: right; height: 30px;" name="searchType">
				<option value="">메뉴필터</option>
				<option value="1">방문횟수</option>
				<option value="2">가입자수</option>
			</select>
			--%>
			
			<input type="hidden" name="fromDate" />
			<input type="hidden" name="toDate" />
		</form>
	</div>
	
	<hr style="border: solid 1px black;">	
	
	<div style="margin-bottom: 3%;display: flex;">
		<div class="statistics">
			<div>
  				<canvas id="userWeek"></canvas>
			</div>
		</div>
		<div class="statistics">
			<div>
  				<canvas id="userWeekcompare"></canvas>
			</div>
		</div>
	</div>
	
	<%-- <hr style="border: solid 1px black;">--%>
	
	<div style="margin-top: 3%; margin-bottom: 3%; display: flex;">
		<div class="statistics">
			<div>
  				<canvas id="userMonth"></canvas>
			</div>
		</div>
		<div class="statistics">
			<div>
  				<canvas id="userMonthcompare"></canvas>
			</div>
		</div>
	</div>
	
	<%-- 통계 본문 보여주기 끝 --%>
	
		
	
	<%-- 회원정보 테이블 보여주기 시작 --%>
	<div id="memberInfoTable">
	
		<div style="margin-left: 1%;" class="h6 mt-5">
			<form name="statistics_searchFrm">
				<span id="bodyTitle">방문자 Top 10</span>
				
				<select style="float: right; height: 30px;" name="searchRange">
					<option value="">정렬기준</option>			
					<option value="desc">높은순</option>			
					<option value="asc">낮은순</option>			
				</select>
				
				<select style="margin-right: 0.5%; float: right; height: 30px;" name="searchMonth">
					<option value="">월별필터</option>
					<option value="0">이번달</option>
					<option value="1">1개월전</option>
					<option value="2">2개월전</option>
					<option value="3">3개월전</option>
				</select>
			</form>
		</div>
		
		<hr style="border: solid 1px black;">
		<div id="filter_desc">
			<ul>
				<li>기본적으로 이번달의 자료가 조회됩니다.</li>
				<li>날짜 필터를 통해 최대 3개월이전 데이터를 월별로 조회할 수 있으며 그외 자료 필요시 개발팀으로 요청하시기 바랍니다.</li>
				<li>행을 클릭하면 해당 회원의 상세정보를 볼 수 있습니다.</li>
			</ul>
		</div>
		
		<div style="margin-top: 3%;">
			<table id="visitUserRank" class="table text-center table-hover">
				<thead>
					<tr>
						<th>회원번호</th>
						<th>이름</th>
						<th>연락처</th>
						<th>이메일</th>
						<th>방문횟수</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
	</div>
	<%-- 회원정보 테이블 보여주기 끝 --%>
</div>


<%-- 상품별 주문건수 차트 보여줄 곳 --%>
<div id="prodOrderStatistics">
	<div style="margin-left: 1%;" class="h6 mt-5">
		<form name="statistics_searchFrm">
			<span id="bodyTitle">판매량 Top 10</span>
			
			<%-- orderby 절에 들어감 --%>
			<select style="float: right; height: 30px;" name="orderSearchRange">
				<option value="">정렬기준</option>			
				<option value="desc">높은순</option>			
				<option value="asc">낮은순</option>			
			</select>
			
			<select style="margin-right: 0.5%; float: right; height: 30px;" name="orderSearchType">
				<option value="">주문필터</option>
				<option value="revenue_pct">매출비중</option>
				<option value="sold_cnt">판매량</option>
				<option value="prod_revenue">매출</option>
			</select>
			
			<select style="margin-right: 0.5%; float: right; height: 30px;" name="ordersearchMonth">
					<option value="">월별필터</option>
					<option value="0">이번달</option>
					<option value="1">1개월전</option>
					<option value="2">2개월전</option>
					<option value="3">3개월전</option>
				</select>
		</form>
	</div>
	
	<hr style="border: solid 1px black;">
	
	<div id="filter_desc">
			<ul>
				<li>기본적으로 이번달의 자료가 조회됩니다.</li>
				<li>날짜 필터를 통해 최대 3개월이전 데이터를 월별로 조회할 수 있으며 그외 자료 필요시 개발팀으로 요청하시기 바랍니다.</li>
			</ul>
		</div>
	<div id="chart_container"></div>
	<div id="table_container" style="margin-top: 2%;">
		<table id="orderProdRank" class="table text-center table-hover">
			<thead>
				<tr>
					<th>상품번호</th>
					<th>상품명</th>
					<th>상품가격</th>
					<th>판매량(개)</th>
					<th>매출(원)</th>
					<th>매출비중(%)</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</div>
</div>
<%-- 상품별 주문건수 차트 보여줄 곳 --%>


</div>