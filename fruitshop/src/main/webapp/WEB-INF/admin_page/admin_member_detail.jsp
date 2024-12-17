<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<% 
	String ctxPath = request.getContextPath();
%>
<style>

tbody > tr:nth:child(1){
	colspan:2;
}

</style>

<div class="container">
	
	<table class="table" style="text-align:center; margin-top:2%;">
		<thead>
			<tr>
				<th colspan="3">${requestScope.detailMember.name} 님의 회원정보 입니다.</th>
			</tr>
		</thead>
		<tbody>
		
			<tr>
				<td colspan="2">회원번호</td>
				<td>${requestScope.detailMember.user_no}</td>
			</tr>
			<tr>
				<td colspan="2">생년월일</td>
				<td>${requestScope.detailMember.birthday}</td>
			</tr>
			<tr>
				<td colspan="2">이메일</td>
				<td>${requestScope.detailMember.email}</td>
			</tr>
			<tr>
				<td colspan="2">연락처</td>
				<c:if test="${not empty requestScope.detailMember.tel}"><td>${requestScope.detailMember.tel}</td></c:if>
				<c:if test="${empty requestScope.detailMember.tel}">없음</c:if>
			</tr>
			<tr>
				<td colspan="2">우편번호</td>
				<td>${requestScope.detailMember.postcode}</td>
			</tr>
			<tr>
				<td colspan="2">주소</td>
				<td>
				<c:if test="${empty requestScope.detailMember.address}"></c:if>
				<c:if test="${not empty requestScope.detailMember.address}">${requestScope.detailMember.address}</c:if>				
				<c:if test="${empty requestScope.detailMember.detailaddress}"></c:if>
				<c:if test="${not empty requestScope.detailMember.detailaddress}">${requestScope.detailMember.detailaddress}</c:if>
				<c:if test="${empty requestScope.detailMember.extraaddress}"></c:if>
				<c:if test="${not empty requestScope.detailMember.extraaddress}">${requestScope.detailMember.extraaddress}</c:if>
				</td>
			</tr>
			<tr>
				<td colspan="2">성별</td>
				<td>
					${requestScope.detailMember.gender}
				</td>
			</tr>
			<tr>
				<td colspan="2">적립금</td>
				
				<td><fmt:parseNumber value="${requestScope.detailMember.point}" pattern="#,###"></fmt:parseNumber></td>
			</tr>
			<tr>
				<td colspan="2">가입일자</td>
				<td>${requestScope.detailMember.registerday}</td>
			</tr>
			<tr>
				<td colspan="2">마지막 비밀번호 변경일자</td>
				<td>${requestScope.detailMember.lastpwdchangedate}</td>
			</tr>
			<tr>
				<td colspan="2">회원상태</td>
				<c:if test="${requestScope.detailMember.idle eq '0'}"><td>휴면상태</td></c:if>
				<c:if test="${requestScope.detailMember.idle eq '1'}"><td>활동중</td></c:if>
			</tr>
			<tr>
				<td colspan="2">회원가입 상태</td>
				<c:if test="${requestScope.detailMember.status eq '0'}"><td>탈퇴</td></c:if>
				<c:if test="${requestScope.detailMember.status eq '1'}"><td>가입중</td></c:if>
			</tr>
			<tr>
				<td colspan="2">회원 권한</td>
				<c:if test="${requestScope.detailMember.status eq '1'}"><td>일반회원</td></c:if>
				<c:if test="${requestScope.detailMember.status eq '2'}"><td>관리자</td></c:if>
			</tr>
		</tbody>
	
	</table>
	<table class="table" style="text-align:center;">
		<tbody>
			<tr>
				<td><button type="button" class="btn btn-outline-success">쿠폰수령</button></td>
				<td><button type="button" class="btn btn-outline-success">징계처분</button></td>
				<c:if test="${requestScope.detailMember.status eq '1'}"><td><button type="button" class="btn btn-outline-success">관리자권한부여</button></td></c:if>
				<c:if test="${requestScope.detailMember.status eq '2'}"><td><button type="button" class="btn btn-outline-danger">관리자권한박탈</button></td></c:if>
				<td><button type="button" class="btn btn-outline-secondary">돌아가기</button></td>
			</tr>
		</tbody>
	</table>
	
</div>
