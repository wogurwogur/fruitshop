<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% 
	String ctxPath = request.getContextPath();
%>

<%-- Custom CSS --%>
<link rel="stylesheet" href="<%= ctxPath%>/css/mypage/mypage_header.css">

<!-- Bootstrap CSS -->
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath()%>/bootstrap-4.6.2-dist/css/bootstrap.min.css" >



<div class="title container" style="margin-top: 3.5%;">
        <h2>My page</h2> 
    </div>
    
    <div class="container" style="margin-top: 2.5%;">

    <div id="mypage" class="menu">
        <c:if test="${requestScope.mypage_val == 'mypageIndex'}"><a href="<%= request.getContextPath()%>/mypage/mypageIndex.ddg" style="color: black; border-bottom: solid black 2px;">마이페이지 </a></c:if>
   		<c:if test="${requestScope.mypage_val != 'mypageIndex'}"><a href="<%= request.getContextPath()%>/mypage/mypageIndex.ddg">마이페이지 </a></c:if>
   		<c:if test="${requestScope.mypage_val == 'updateInfo'}"><a href="<%= request.getContextPath()%>/mypage/updateInfo.ddg" style="color: black; border-bottom: solid black 2px;">회원정보수정</a></c:if>
   		<c:if test="${requestScope.mypage_val != 'updateInfo'}"><a href="<%= request.getContextPath()%>/mypage/updateInfo.ddg">회원정보수정</a></c:if>
   		<c:if test="${requestScope.mypage_val == 'orderList'}"><a href="<%= request.getContextPath()%>/order/orderList.ddg" style="color: black; border-bottom: solid black 2px;">주문내역조회</a></c:if>
   		<c:if test="${requestScope.mypage_val != 'orderList'}"><a href="<%= request.getContextPath()%>/order/orderList.ddg">주문내역조회</a></c:if>
        <c:if test="${requestScope.mypage_val == 'wishList'}"><a href="<%= request.getContextPath()%>/mypage/wishList.ddg" style="color: black; border-bottom: solid black 2px;">관심상품</a></c:if>
        <c:if test="${requestScope.mypage_val != 'wishList'}"><a href="<%= request.getContextPath()%>/mypage/wishList.ddg">관심상품</a></c:if>
        <c:if test="${requestScope.mypage_val == 'shipList'}"><a href="<%= request.getContextPath()%>/mypage/shipManagement.ddg" style="color: black; border-bottom: solid black 2px;">배송주소록관리</a></c:if>
        <c:if test="${requestScope.mypage_val != 'shipList'}"><a href="<%= request.getContextPath()%>/mypage/shipManagement.ddg">배송주소록관리</a></c:if>
        <c:if test="${requestScope.mypage_val == 'recent_view'}"><a href="<%= request.getContextPath()%>/mypage/recent_Viewproduct.ddg" style="color: black; border-bottom: solid black 2px;">최근본상품</a></c:if>
        <c:if test="${requestScope.mypage_val != 'recent_view'}"><a href="<%= request.getContextPath()%>/mypage/recent_Viewproduct.ddg">최근본상품</a></c:if>
    </div>

    </div>
	
	
