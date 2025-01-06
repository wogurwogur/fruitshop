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
   		<c:if test="${empty requestScope.adminpage_val || requestScope.adminpage_val == 'admin_member_management' || requestScope.adminpage_val == 'admin_member_detail'}"><a href="<%=ctxPath%>/admin/adminManagement.ddg" style="color: black; border-bottom: solid black 2px;">회원관리</a></c:if>
   		<c:if test="${!empty requestScope.adminpage_val && requestScope.adminpage_val != 'admin_member_management' && requestScope.adminpage_val != 'admin_member_detail'}"><a href="<%=ctxPath%>/admin/adminManagement.ddg">회원관리</a></c:if>
   		<c:if test="${requestScope.adminpage_val == 'admin_product_management' || requestScope.adminpage_val == 'admin_product_onedetail' || requestScope.adminpage_val == 'admin_product_register'}"><a href="<%=ctxPath%>/admin/adminProduct.ddg" style="color: black; border-bottom: solid black 2px;">상품관리</a></c:if>
   		<c:if test="${requestScope.adminpage_val != 'admin_product_management' && requestScope.adminpage_val != 'admin_product_onedetail' && requestScope.adminpage_val != 'admin_product_register'}"><a href="<%=ctxPath%>/admin/adminProduct.ddg">상품관리</a></c:if>
   		<c:if test="${requestScope.adminpage_val == 'admin_delivery_status'}"><a href="<%=ctxPath%>/admin/adminDeliveryStatus.ddg" style="color: black; border-bottom: solid black 2px;">주문관리</a></c:if>
   		<c:if test="${requestScope.adminpage_val != 'admin_delivery_status'}"><a href="<%=ctxPath%>/admin/adminDeliveryStatus.ddg">주문관리</a></c:if>
        <c:if test="${requestScope.adminpage_val == 'MainPageDetail' || requestScope.adminpage_val == 'admin_page_management'}"><a href="<%=ctxPath%>/admin/pageManagement.ddg" style="color: black; border-bottom: solid black 2px;">메인페이지관리</a></c:if>
        <c:if test="${requestScope.adminpage_val != 'MainPageDetail' && requestScope.adminpage_val != 'admin_page_management'}"><a href="<%=ctxPath%>/admin/pageManagement.ddg">메인페이지관리</a></c:if>
        <c:if test="${requestScope.adminpage_val == 'admin_statistics'}"><a href="<%=ctxPath%>/admin/adminStatistics.ddg" style="color: black; border-bottom: solid black 2px;">통계</a></c:if>
        <c:if test="${requestScope.adminpage_val != 'admin_statistics'}"><a href="<%=ctxPath%>/admin/adminStatistics.ddg">통계</a></c:if>
    </div>
