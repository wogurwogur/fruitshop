<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>독독고 과일판매</title>

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
<link rel="stylesheet" href="<%= request.getContextPath()%>/css/index/index.css">

<script type="text/javascript">
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
            <a href="#">Fruit Shop</a>
            <a href="#">Brand Story</a>
            <a href="#">Community</a>
            <br>
            <a href="#">Login</a>
            <a href="#">My Page</a>
            <a href="#">Order List</a>
            <a href="#">Cart</a>
        </div>
        <!-- 슬라이드 메뉴 끝 -->
        
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        
        
        
        <div style="margin-left: 11%;" class="collapse navbar-collapse" id="navbarSupportedContent">
        	<span style="font-size:20px;cursor:pointer;" onclick="openNav()">&#9776;</span>
            <ul class="navbar-nav mr-auto"> <!-- .mr-auto 는 css 의  margin-right: auto; 임. 즉, 가로축 미사용 공간 너비의 일부를 바깥 여백에 할당한다는 의미임. -->
                <li class="nav-item active ml-4">
                    <a class="nav-link menu" href="#">Fruit shop</a>
                </li>
                <li class="nav-item active ml-3">
                    <a class="nav-link menu" href="#">Team Stroy</a>
                </li>
                <li class="nav-item active ml-3">
                    <a class="nav-link menu" href="#">Community</a>
                </li>
            </ul>
        </div> 

        <div class="collapse navbar-collapse" style="margin-left: 1%;">
            <img src="<%= request.getContextPath() %>/images/index/logo_header.png">
        </div>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto"> <!-- .mr-auto 는 css 의  margin-right: auto; 임. 즉, 가로축 미사용 공간 너비의 일부를 바깥 여백에 할당한다는 의미임. -->
                <li class="nav-item active mr-3">
                    <a class="nav-link menu" href="#">Login</a>
                </li>
                <li class="nav-item active mr-3">
                    <a class="nav-link menu" href="#">My Page</a>
                </li>
                <li class="nav-item active mr-3">
                    <a class="nav-link menu" href="#">Order List</a>
                </li>
                <li class="nav-item active" style="margin-top: 2%;">
                    <a class="navbar-brand notification" href="#">
                    	<%-- 배지에 세션에 담긴 장바구니 개수 들어와야 함 --%>
                        <i class="fa-solid fa-basket-shopping"></i><span class="badge">0</span>
                    </a>
                </li>
            </ul>
        </div>
    </nav>
    <!-- 상단 메뉴 끝 -->
</body>
</html>