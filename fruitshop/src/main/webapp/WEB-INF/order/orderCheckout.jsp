<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<jsp:include page="../common/header.jsp"></jsp:include>

<%-- Custom CSS --%>
<link rel="stylesheet" href="<%= request.getContextPath()%>/css/order/orderCheckout.css">

<script type="text/javascript">
	$(document).ready(() => {
		var acc = document.getElementsByClassName("accordion");
		var i;
		
		for (i = 0; i < acc.length; i++) {
		  acc[i].addEventListener("click", function() {
		    this.classList.toggle("activeArcodian");
		    var panel = this.nextElementSibling;
		    if (panel.style.display === "block") {
		      panel.style.display = "none";
		    } else {
		      panel.style.display = "block";
		    }
		  });
		}
		$("button.accordion").trigger("click");
		
	});// end of $(document).ready(() => {}) --------------------- 
	
</script>



<div class="container" style="border: solid 1px red;">
	<%-- 주문/결제 타이틀 --%>
	<div style="background-color: black; color: white;" class="text-center">
		<span id="order_title">주문/결제</span>
	</div>

	
	
	<div style="border: solid 1px #c0c0c0; margin-top: 1%;">
		<button class="accordion">배송지<i style="float: right;" class="fa-solid fa-chevron-up"></i></button>
		<div class="panel">
		
			<div id ="selectInputBox">
				<div class="text-center selectInput">
					<span class="h6">최근 배송지</span>
				</div>
				
				<div class="text-center selectInput">
					<span class="h6">직접 입력</span>
				</div>
			</div>
			<form>
				<label>받는사람</label><input type="text"></input>
			</form>
	
		</div>
		
		<button style="border-top:solid 1px #c0c0c0;" class="accordion">주문상품<i style="float: right;" class="fa-solid fa-chevron-up"></i></button>
		<div class="panel">
		  <p>Lorem ipsum...</p>
		</div>
		
		<button style="border-top:solid 1px #c0c0c0;" class="accordion">Section 3</button>
		<div class="panel">
		  <p>Lorem ipsum...</p>
		</div>
	</div>
	
</div>




<jsp:include page="../common/footer.jsp"></jsp:include>