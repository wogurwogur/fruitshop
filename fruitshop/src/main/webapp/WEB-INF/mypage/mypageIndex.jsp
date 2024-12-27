<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../common/header.jsp"></jsp:include>

<jsp:include page="mypage_list.jsp"></jsp:include>

<style type="text/css">

* {
	font-family: 'Noto Sans KR', sans-serif;
}
</style>

<script type="text/javascript">
	
	function modalOpen() {
		$("div#modalContainer").removeClass("hidden");
	}
	
	
	function modalClose() {
		$("div#modalContainer").addClass("hidden");
	}
	

</script>

<style>

#modalOpenButton, #modalCloseButton {
  cursor: pointer;
}

#modalContainer {
  width: 100%;
  height: 100%;
  position: fixed;
  top: -70px;
  left: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  background: rgba(0, 0, 0, 0.5);
}

#modalContainer.hidden {
  display: none;
}

#modalContent {
  position: absolute;
  background-color: #ffffff;
  width: 800px;
  height: 500px;
  padding: 15px;
  border-radius: 20px;
}


.goOrderInfo:hover {
	cursor: pointer;
}

.hidden {
  display: none;
}

</style>


<div class="container" style="padding-top: 110px; padding-bottom: 110px;">
	
	<div class="row border" style="">
	
		<div class="col-md-5 m-3" style="">
		
			<div class="mt-3">
				<img src="<%= request.getContextPath()%>/images/mypage/빈프로필.jpg" class="img-fluid" style="border-radius: 50%" alt="round" width="20%">
				<span class="ml-3" style="font-size:14pt;">안녕하세요. <span style="font-weight: bold; font-size:16pt;">${sessionScope.loginuser.name}</span> 회원님</span>
			</div>
			
			<div class="mt-3">
	
				<div class="p-3" style="line-height:30px; font-size:14pt">
		  			<ul>
		  				<li class="my-2">포인트 : <span class="text-primary"><fmt:formatNumber value="${sessionScope.loginuser.point}" pattern="#,###" /></span>&nbsp;원</li>
		  				<li class="my-2"><a id="couponModalOpen" style="cursor: pointer;" onclick="modalOpen()">쿠&nbsp;&nbsp;&nbsp;폰 : <span class="text-primary">${requestScope.couponList_cnt}</span> 개</a></li>
		  			</ul>
				</div>

			</div>	
		</div>
		<div class="col-md-1 text-center"></div>
		<div class="col-md-1 text-center my-auto">
			<div style="font-size:12pt; font-weight: bold;">준비중</div>
			<div style="margin-top:20px; fontsize:20pt; font-weight: bold;" class="goOrderInfo" onclick="location.href='<%= request.getContextPath() %>/order/orderList.ddg'">${requestScope.cnt_1}</div>
		</div>
		
		<div class="col-md-1 text-center my-auto" style="font-size:20pt; ">▶</div>
			
		<div class="col-md-1 text-center my-auto">
			<div style="font-size:12pt; font-weight: bold;" >배송중</div>
			<div style="margin-top:20px; fontsize:20pt; font-weight: bold;" class="goOrderInfo" onclick="location.href='<%= request.getContextPath() %>/order/orderList.ddg'">${requestScope.cnt_2}</div>
		</div>
		
		<div class="col-md-1 text-center my-auto" style="font-size:20pt; ">▶</div>
		
		<div class="col-md-1 text-center my-auto">
			<div style="font-size:12pt; font-weight: bold;">배송완료</div>
			<div style="margin-top:20px; fontsize:20pt; font-weight: bold;" class="goOrderInfo" onclick="location.href='<%= request.getContextPath() %>/order/orderList.ddg'">${requestScope.cnt_3}</div>
		</div>
		
		<div class="col-md-1 text-center"></div>
	</div>
	
</div>		




<%-- 모달 --%>
<div id="modalContainer" class="hidden">
	  <div id="modalContent">
	    <div class="container mt-5">
	    
	    	<h3 class="my-3 ml-1">쿠폰함</h3>
	    	
	    	<div style="overflow: auto; width:750px; height:300px; margin: auto;">
		    	<table class="table" style="text-align:center;">
		    		
		    		<thead class="thead-light">
						<tr>
							<th style="">쿠폰명</th>
							<th style="">쿠폰내용</th>
							<th style="">할인금액</th>
							<th style="">유효기간</th>			
						</tr>
					</thead>
					
		    		<tbody>
		    		
		    			<c:if test="${!empty requestScope.couponList}">
		    			
							<c:forEach var="cvo" items="${requestScope.couponList}">
								<tr>
									<th>${cvo.coupon_name}</th>
									<th>${cvo.coupon_descript}</th>
									<th><fmt:formatNumber value="${cvo.coupon_discount}" pattern="#,###" />&nbsp;원</th>
									<th>${cvo.coupon_expire}</th>
								</tr>
							</c:forEach>
							
						</c:if>
	
						<c:if test="${empty requestScope.couponList}">
							<tr>
								<td colspan="4" class="text-center">쿠폰이 없습니다.</td>
							</tr>
						</c:if>
						
		    		</tbody>
		    	</table>
	    	</div>
	    	
	    	<div style="float: right; margin-top:20px;">
	    		<button class="btn btn-outline-danger mr-3" type="button" onclick="modalClose()">돌아가기</button>
	    	</div>
	    </div>
	</div>
</div>






<jsp:include page="../common/footer.jsp"></jsp:include>