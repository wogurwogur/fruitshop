<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	String ctxPath = request.getContextPath();
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- Custom CSS --%>
<link rel="stylesheet" href="<%= ctxPath%>/css/adminpage/adminpage_header.css">



<div class="title">
        <h2>Admin page</h2> 
    </div>
    

    <div id="Adminpage" class="manu container">
   		<a href="<%=ctxPath%>/admin/adminManagement.ddg">회원관리</a>
   		<a href="<%=ctxPath%>/product/registerProduct.ddg">상품관리</a>
   		<a href="<%=ctxPath%>/admin/adminDeliveryStatus.ddg">주문관리</a>
        <a href="<%=ctxPath%>/admin/pageManagement.ddg">메인페이지관리</a>
        <a href="<%=ctxPath%>/admin/adminStatistics.ddg">통계</a>
    </div>
