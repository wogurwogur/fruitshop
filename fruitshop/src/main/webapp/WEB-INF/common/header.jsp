<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>독독고 과일판매</title>
<link rel="shortcut icon" type="image⁄x-icon" href="/fruitshop/images/index/title_logo.png">
<!-- Bootstrap CSS -->
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/bootstrap-4.6.2-dist/css/bootstrap.min.css" > 

<!-- Font Awesome 6 Icons -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.1/css/all.min.css">

<!-- Optional JavaScript -->
<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/bootstrap-4.6.2-dist/js/bootstrap.bundle.min.js" ></script>

<%-- jQueryUI CSS 및 JS --%>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/jquery-ui-1.13.1.custom/jquery-ui.min.css" />
<script type="text/javascript" src="<%= request.getContextPath()%>/jquery-ui-1.13.1.custom/jquery-ui.min.js"></script>

<%-- Custom CSS --%>
<link rel="stylesheet" href="<%= request.getContextPath()%>/css/index/header.css">

<script type="text/javascript">

	$(document).ready(()=> {
		$("a.menu").hover(function(e) {
			//alert("야야호");
			$(e.target).css({"color": "red", "transition": '0.75s'});
		}, function(e) {
			$(e.target).css({"color": "black", "transition": '0.75s'});
		})
	});// end of $(document).ready(()=> {}) ---------------- 

	function openNav() {
		document.getElementById("mySidenav").style.width = "15%";
	}
	
	function closeNav() {
		document.getElementById("mySidenav").style.width = "0";
	}
</script>

</head>
<body>
	<!-- 상단 메뉴 시작 -->
    <nav class="navbar navbar-expand-lg navbar-light sticky-top mt-3 mb-5">
        <!-- 슬라이드 메뉴 시작 -->
        <div id="mySidenav" class="sidenav">
            <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
            <a href="<%= request.getContextPath()%>/product/productList.ddg">Fruit Shop</a>
            <a href="#">Team Story</a>
            <a href="<%= request.getContextPath()%>/notice/noticeList.ddg">Community</a>
            <br>
            
            <c:if test="${empty sessionScope.loginuser}">
            	<a href="<%= request.getContextPath()%>/login/login.ddg">Login</a>
            </c:if>
            <c:if test="${!empty sessionScope.loginuser}">
            	<a href="<%= request.getContextPath()%>/login/logout.ddg">Logout</a>
            </c:if>
            
            <c:if test="${empty sessionScope.loginuser or sessionScope.loginuser.role == 1}">
	            <a href="<%= request.getContextPath()%>/mypage/mypageIndex.ddg">My Page</a>
	            <a href="<%= request.getContextPath()%>/order/orderList.ddg">Order List</a>
	            <a href="<%= request.getContextPath()%>/cart/cartList.ddg">
	            	Cart<c:if test="${!empty sessionScope.loginuser}">(${sessionScope.loginuser.cart_cnt})</c:if>
	            </a>
            </c:if>
            <c:if test="${sessionScope.loginuser.role == 2}">
                <a href="<%= request.getContextPath()%>/admin/adminManagement.ddg">Admin Page</a>
            </c:if>
            
        </div>
        <!-- 슬라이드 메뉴 끝 -->
        
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        
        
        <%-- 메뉴 좌상단 시작 --%>
        <div style="margin-left: 0.22%;" class="container-fluid row">
	        <div class="col-md-5 collapse navbar-collapse text-center" id="navbarSupportedContent" style="padding-right: 2%;">
	        	
	            <ul class="navbar-nav mx-auto"> <!-- .mr-auto 는 css 의  margin-right: auto; 임. 즉, 가로축 미사용 공간 너비의 일부를 바깥 여백에 할당한다는 의미임. -->
	            	<span style="font-size:20px;cursor:pointer; margin-top:1.25%" onclick="openNav()">&#9776;</span>
	                <li class="nav-item active ml-4">
	                    <a class="nav-link menu" href="<%= request.getContextPath()%>/product/productList.ddg">Fruit shop</a>
	                </li>
	                <li class="nav-item active ml-3">
	                    <a class="nav-link menu" href="<%= request.getContextPath()%>/team/teamStory.ddg">Team Story</a>
	                </li>
	                <li class="nav-item active ml-3">
	                    <a class="nav-link menu" href="<%= request.getContextPath()%>/notice/noticeList.ddg">Community</a>
	                </li>
	            </ul>
	        </div>
	        <%-- 메뉴 좌상단 끝 --%>
	
			<%-- 로고 이미지 --%>
	        <div class="col-md-2 collapse navbar-collapse">
	        	<div class="mx-auto">
	            	<a href="<%= request.getContextPath()%>"><img style="text-align: center;" src="<%= request.getContextPath() %>/images/index/logo_header.png"></a>
	            </div>
	        </div>
	
	        <div class="col-md-5 collapse navbar-collapse" id="navbarSupportedContent">
	            <ul class="navbar-nav mx-auto"> <!-- .mr-auto 는 css 의  margin-right: auto; 임. 즉, 가로축 미사용 공간 너비의 일부를 바깥 여백에 할당한다는 의미임. -->
	            	
	            	<c:if test="${empty sessionScope.loginuser}">
		                <li class="nav-item active mr-3">
		                    <a class="nav-link menu" href="<%= request.getContextPath()%>/login/login.ddg">Login</a>
		                </li>
	                </c:if>
	                <c:if test="${!empty sessionScope.loginuser}">
		                <li class="nav-item active mr-3">
		                    <a class="nav-link menu" href="<%= request.getContextPath()%>/login/logout.ddg">Logout</a>
		                </li>
	                </c:if>
	                
	                <c:if test="${empty sessionScope.loginuser or sessionScope.loginuser.role == 1}">
		                <li class="nav-item active mr-3">
		                    <a class="nav-link menu" href="<%= request.getContextPath()%>/mypage/mypageIndex.ddg">My Page</a>
		                </li>
		                <li class="nav-item active mr-3">
		                    <a class="nav-link menu" href="<%= request.getContextPath()%>/order/orderList.ddg">Order List</a>
		                </li>
		                <li class="nav-item active">
		                    <a style="margin-top: 2%;" class="navbar-brand notification" href="<%= request.getContextPath()%>/cart/cartList.ddg">
		                    	<%-- 배지에 세션에 담긴 장바구니 개수 들어와야 함 --%>
		                    	<c:if test="${!empty sessionScope.loginuser}">
		                        	<i class="fa-solid fa-basket-shopping"></i><span class="badge">${sessionScope.loginuser.cart_cnt}</span>
		                        </c:if>
		                        <c:if test="${empty sessionScope.loginuser}">
		                        	<i class="fa-solid fa-basket-shopping"></i><span class="badge">0</span>
		                        </c:if>
		                    </a>
		                </li>
	                </c:if>
	                <c:if test="${sessionScope.loginuser.role == 2}">
		                <li class="nav-item active mr-3">
		                    <a class="nav-link menu" href="<%= request.getContextPath()%>/admin/adminManagement.ddg">Admin Page</a>
		                </li>
	                </c:if>
	            </ul>
	        </div>
        </div>
    </nav>
    <!-- 상단 메뉴 끝 -->
</body>
</html>