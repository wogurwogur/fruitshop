<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- Custom CSS --%>
<link rel="stylesheet" href="<%= request.getContextPath()%>/css/mypage/mypage_header.css">

<!-- Bootstrap CSS -->
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/bootstrap-4.6.2-dist/css/bootstrap.min.css" >



<div class="title container" style="margin-top: 3.5%;">
        <h2>My page</h2> 
    </div>
    
    <div class="container" style="margin-top: 2.5%;">

    <div id="mypage" class="menu">
            <a href="<%= request.getContextPath()%>/mypage/mypageIndex.ddg">마이페이지</a>
            <a href="<%= request.getContextPath()%>/mypage/updateInfo.ddg">회원정보수정</a>
            <a href="#">주문내역조회</a>
            <a href="<%= request.getContextPath()%>/mypage/wishList.ddg">관심상품</a>
            <a href="<%= request.getContextPath()%>/mypage/shipManagement.ddg">배송주소록관리</a>
            <a href="<%= request.getContextPath()%>/mypage/recent_Viewproduct.ddg">최근본상품</a>
    </div>
	
	</div>
