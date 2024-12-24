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

	$(()=>{
		
		$("button.couponInfoClose").click(e=>{

			const iframe_couponInfo = document.getElementById("iframe_couponInfo"); 
	        // 대상 아이프레임을 선택한다.
	        <%-- 선택자를 잡을때 jQuery를 사용한 ${} 으로 잡으면 안되고, 순수한 자바스크립트를 사용하여 선택자를 잡아야 한다. --%> 
	        <%-- .jsp 파일속에 주석문을 만들때 ${} 을 넣고자 한다라면 반드시 JSP 주석문으로 해야 하지, 스크립트 주석문으로 해주면 ${} 때문에 오류가 발생한다. --%>
	        
	        const iframe_window = iframe_couponInfo.contentWindow;
	        
	        iframe_window.func_form_reset_empty();
	        // iframe 태그 안의 내용물의 함수 func_form_reset_empty() 실행 
		});
		
		
		
		
	}
	

</script>

<div class="container" style="padding-top: 110px; padding-bottom: 110px;">
	
	<div class="row" style="">
	
		<div class="col-md-5 m-3" style="">
		
			<div class="mt-3">
				<img src="<%= request.getContextPath()%>/images/mypage/빈프로필.jpg" class="img-fluid" style="border-radius: 50%" alt="round" width="20%">
				<span class="ml-3" style="font-size:14pt;">안녕하세요. <span style="font-weight: bold; font-size:16pt;">${sessionScope.loginuser.name}</span> 회원님</span>
			</div>
			
			<div class="mt-3">
	
				<div class="p-3" style="line-height:30px; font-size:14pt">
		  			<ul>
		  				<li class="my-2">포인트 : <fmt:formatNumber value="${sessionScope.loginuser.point}" pattern="#,###" />&nbsp;원</li>
		  				<li class="my-2"><a style="cursor: pointer;" data-toggle="modal" data-target="#userCouponInfo" data-dismiss="modal">쿠폰 : </a></li>
		  			</ul>
				</div>

			</div>	
		</div>
		<div class="col-md-1 text-center"></div>
		<div class="col-md-1 text-center my-auto">
			<div style="font-size:12pt; font-weight: bold;">주문완료</div>
			<button type="button" style="border-radius: 50%; width:30px; height:30px; margin-top:20px;" class="btn btn-outline-primary" onclick="location.href='<%= request.getContextPath() %>/order/orderList.ddg'"></button>
		</div>
		
		<div class="col-md-1 text-center my-auto" style="font-size:24pt; ">▶</div>
			
		<div class="col-md-1 text-center my-auto">
			<div style="font-size:12pt; font-weight: bold;" >배송중</div>
			<button type="button" style="border-radius: 50%; width:30px; height:30px; margin-top:20px;" class="btn btn-outline-primary" onclick="location.href='<%= request.getContextPath() %>/order/orderList.ddg'"></button>
		</div>
		
		<div class="col-md-1 text-center my-auto" style="font-size:24pt; ">▶</div>
		
		<div class="col-md-1 text-center my-auto">
			<div style="font-size:12pt; font-weight: bold;">배송완료</div>
			<button type="button" style="border-radius: 50%; width:30px; height:30px; margin-top:20px;" class="btn btn-outline-primary" onclick="location.href='<%= request.getContextPath() %>/order/orderList.ddg'"></button>
		</div>
		
		<div class="col-md-1 text-center"></div>
	</div>
	
</div>		


<div class="modal fade" id="userCouponInfo" data-backdrop="static">
	
	<div class="modal-dialog">
		<div class="modal-content">

			<!-- Modal header -->
			<div class="modal-header">
				<h4 class="modal-title">쿠폰함</h4>
				<button type="button" class="close couponInfoClose" data-dismiss="modal">&times;</button>
			</div>

			<!-- Modal body -->
			<div class="modal-body">
				<div id="couponInfo">
					<iframe id="iframe_couponInfo" style="border: none; width: 100%; height: 350px;" src="<%=request.getContextPath()%>/mypage/couponInfo.ddg">
	
					</iframe>
				</div>
			</div>

			<!-- Modal footer -->
			<div class="modal-footer">
				<button type="button" class="btn btn-danger couponInfoClose" data-dismiss="modal">Close</button>
			</div>
		</div>

	</div>

</div>






<jsp:include page="../common/footer.jsp"></jsp:include>