<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="<%= request.getContextPath()%>/css/mypage/wishlist.css">


<jsp:include page="../common/header.jsp"></jsp:include>

<jsp:include page="mypage_list.jsp"></jsp:include>

<div id=container>

<div class="jumbotron" style="border: solid 1px #cccccc; background-color: white; margin-top: 60px; font-weight: bold;">
    <p align="center">관심상품 내역이 없습니다.</p>
</div>

</div>

<jsp:include page="../common/footer.jsp"></jsp:include>