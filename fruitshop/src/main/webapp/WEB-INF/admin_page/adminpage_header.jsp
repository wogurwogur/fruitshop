<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	String ctxPath = request.getContextPath();
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- Custom CSS --%>
<link rel="stylesheet" href="<%= ctxPath%>/css/adminpage/adminpage_header.css">



<div class="title">
        <h2>My page</h2> 
    </div>
    

    <div id="mypage" class="menu">
            <a href="<%=ctxPath%>/admin/admin_management.ddg">회원관리</a>
            <a href="<%=ctxPath%>/admin/admin_community.ddg">게시판관리</a>
            <a href="<%=ctxPath%>/admin/admin_statistics.ddg">통계</a>
            <a href="<%=ctxPath%>/admin/admin_delivery_status.ddg">배송상태</a>
            <a href="<%=ctxPath%>/admin/page_management.ddg">메인페이지관리</a>
    </div>
