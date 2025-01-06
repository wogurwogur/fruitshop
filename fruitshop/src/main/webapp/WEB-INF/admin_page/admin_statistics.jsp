<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.Calendar" %>

<%
	Calendar time = Calendar.getInstance();
	int year = time.get(Calendar.YEAR);
%>

<link rel="stylesheet" href="<%= request.getContextPath()%>/css/adminpage/statistics.css">
<script type="text/javascript" src="<%= request.getContextPath()%>/js/admin/statistics.js"></script>

<%-- chart.js --%>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<script type="text/javascript">
	$(document).ready(() => {
		
		$("input#fromDate").val("${requestScope.fromDate}");
		$("input#toDate").val("${requestScope.toDate}");
		
		
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
	
		<%-- 주간 가입자 수 추이 차트 그리기 시작 --%>
		const ctx1 = document.getElementById('visitUserWeek');

	  	new Chart(ctx1, {
	    	type: 'line',
	    	data: {
	      	labels: ['6일전', '5일전', '4일전', '3일전', '2일전', '1일전', '오늘'],
	      	datasets: [{
	        label: '주간 가입자수 추이',
	        data: [
	        	"${requestScope.visitUserWeek.before_6days}", "${requestScope.visitUserWeek.before_5days}", "${requestScope.visitUserWeek.before_4days}",
	        	"${requestScope.visitUserWeek.before_3days}", "${requestScope.visitUserWeek.before_2days}", "${requestScope.visitUserWeek.before_1days}",
	        	"${requestScope.visitUserWeek.today}"
	        ],
	        borderWidth: 1
	      	}]
	    	},
	    	options: {
	      		scales: {
		        	y: {
		          		beginAtZero: true
		        	}
	      		}
	    	}
	  	});// end of new Chart(ctx, {}) ---------------------------
	  	<%-- 주간 가입자 수 추이 차트 그리기 끝 --%>
		
	  	
	  	<%-- 주간 가입자 수 비교 차트 그리기 시작 --%>
		const ctx2 = document.getElementById('userWeekcompare');

	  	new Chart(ctx2, {
	    	type: 'bar',
	    	data: {
	      	labels: ['6일전', '5일전', '4일전', '3일전', '2일전', '1일전', '오늘'],
	      	datasets: [{
	        label: '전주 대비 가입자 수 증감률',
	        data: [
	        	Number("${requestScope.visitUserWeek.before_6days}") - Number("${requestScope.visitUser2Week.before_13days}"),
	        	Number("${requestScope.visitUserWeek.before_5days}") - Number("${requestScope.visitUser2Week.before_12days}"),
	        	Number("${requestScope.visitUserWeek.before_4days}") - Number("${requestScope.visitUser2Week.before_11days}"),
	        	Number("${requestScope.visitUserWeek.before_3days}") - Number("${requestScope.visitUser2Week.before_10days}"),
	        	Number("${requestScope.visitUserWeek.before_2days}") - Number("${requestScope.visitUser2Week.before_9days}"),
	        	Number("${requestScope.visitUserWeek.before_1days}") - Number("${requestScope.visitUser2Week.before_8days}"),
	        	Number("${requestScope.visitUserWeek.today}") - Number("${requestScope.visitUser2Week.before_7days}"),
	        ],
	        borderWidth: 1
	      	}]
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
	  	
		
		$("div.order_title").click(e => {
			// alert("야야호"+ $(e.target).index());
			$("div.order_title").removeClass("active");
			$(e.target).addClass("active");
		});
		
		$("select#searchRange").on("change", function() {
			
		});// end of $("select#searchRange").on("change", function() {}) -----------------------
		
	});// end of $(document).ready(() => {}) -------------------------
	
	//Function Declaration 
	function goSearch() {
		
		const fromDate = document.querySelector("input#fromDate").value;
		const toDate = document.querySelector("input#toDate").value;
		
		// alert("시작일: "+ fromDate+"\n마지막일: "+toDate);
		
		document.querySelector("input[name='fromDate']").value 	= fromDate;
		document.querySelector("input[name='toDate']").value 	= toDate;
		
		const frm  = document.order_searchFrm;
		// frm.action = "memberList.up";	// form 태그에 action 이 명기되지 않았으면 현재보이는 URL 경로로 submit 되어진다.
		// frm.method = "GET";				// form 태그에 method 를 명기하지 않으면 "get" 방식이다.
		frm.submit();
			
	}// end of function goSearch() -------------------
	
	
</script>

<div style="margin-top: 3%;" id="container">

<%-- 페이지 도착 시 기본 조회 기간은 3개월 --%>
	<%-- 주문내역조회 메뉴 바 시작 --%>
	<div id="menu_filter">
	
		<div class="menu_title active">
			회원관련<%-- url 필요 --%>
		</div>
		<div class="menu_title">
			상품관련<%-- url 필요 --%>
		</div>
		<div class="menu_title">
			매출/이익<%-- url 필요 --%>
		</div>
		<div id="menu_title_nbsp">
		</div>
	</div>
	<%-- 주문내역조회 메뉴 바 끝 --%>
	
	<%-- 기간 필터 시작 --%>
	<div id="order_time">
		<div style="padding: 2%;" class="btn-group" role="group" aria-label="Date Select Filter">
			<button type="button" class="btn btn-outline-dark" id="today">오늘</button>
			<button type="button" class="btn btn-outline-dark" id="week">일주일</button>
			<button type="button" class="btn btn-outline-dark" id="month">1개월</button>
			<button type="button" class="btn btn-outline-dark" id="3month">3개월</button>
			<button type="button" class="btn btn-outline-dark" id="6month">6개월</button>
			<select id="searchYear">
	        	<option value="">연도선택</option>
	         	<option><%= year %></option>
	         	<option><%= year-1 %></option>
	         	<option><%= year-2 %></option>
	         	<option><%= year-3 %></option>
	      	</select>
		</div>
		
		<div style="margin-top: 1%; padding: 1%;">
			<form name="dateFilter">
				<input style="width: 120px; height: 40px; text-align: center;" type="text" id="fromDate" maxlength="10" />
				<label style="cursor:pointer" for="fromDate"><img src="<%= request.getContextPath() %>/images/order/calendar.png" /></label>
				&nbsp;~&nbsp;&nbsp;
				<input style="width: 120px; height: 40px; text-align: center;" type="text" id="toDate" maxlength="10" />
				<label style="cursor:pointer" for="toDate"><img src="<%= request.getContextPath() %>/images/order/calendar.png" /></label>
			</form>
		</div>
		
		<div style="margin-top: 2%;">
			<button style="width: 80px; height: 40px;" type="button" class="btn btn-secondary">조회</button>
		</div>
	</div>
	<div id="filter_desc">
		<ul>
			<li>기본적으로 최근 3개월간의 자료가 조회되며, 특정 기간을 선택하여 조회할 수 있습니다.</li>
		</ul>
	</div>
	<%-- 기간 필터 끝 --%>
	
	<%-- 통계 본문 보여주기 시작 --%>
	<div style="margin-left: 1%;" class="h6 mt-5">
		<form name="statistics_searchFrm">
			<span id="bodyTitle">회원 통계 정보</span>
			
			<select style="float: right; height: 30px;" name="searchRange">
				<option value="">정렬기준</option>			
				<option value="desc">높은순</option>			
				<option value="asc">낮은순</option>			
			</select>
			<select style="margin-right: 0.5%; float: right; height: 30px;" name="searchType">
				<option value="">메뉴필터</option>			
				<option value="1">방문횟수</option>			
				<option value="2">가입자수</option>			
				<option value="3">주문횟수</option>
				<option value="4">주문금액</option>
			</select>
			
			
			<input type="hidden" name="fromDate" />
			<input type="hidden" name="toDate" />
		</form>
	</div>
	<hr style="border: solid 1px black;">	
	<div style="display: flex;">
		<div class="" style="width: 50%; border: solid 1px green;">
			<div>
  				<canvas id="visitUserWeek"></canvas>
			</div>
		</div>
		<div class="" style="width: 50%; border: solid 1px green;">
			<div>
  				<canvas id="userWeekcompare"></canvas>
			</div>
		</div>
	</div>
	<%-- 통계 본문 보여주기 끝 --%>
	
</div>