<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:if test="${empty requestScope.adminpage_val}">



<jsp:include page="admin_member_management.jsp">
</jsp:include></c:if>

<c:if test="${!empty requestScope.adminpage_val}"><jsp:include page="${requestScope.adminpage_val}.jsp"></jsp:include></c:if>