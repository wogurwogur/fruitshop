<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:include page="../common/header.jsp"></jsp:include>

<jsp:include page="mypage_list.jsp"></jsp:include>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style type="text/css">

* {
	font-family: 'Noto Sans KR', sans-serif;
}
</style>

<script type="text/javascript" src="<%= request.getContextPath()%>/js/mypage/shipInfo.js"></script>

<script type="text/javascript">
	$(()=>{
		
		

	});

</script>

	<div class="container">
		
			<div class="table-responsive">
			
				<table class="table table-striped table-hover">
				
					<div class="h4 p-2 mt-5">배송지 목록</div>
					
					<thead class="thead-light text-center">
						
						<tr>
							<th style="width:2%; "><input type="checkbox" id="checkAll" class="" /></th>
							<th style="width:8%; ">배송지명</th>
							<th style="width:8%; ">수령인</th>
							<th style="width:8%; ">연락처</th>
							<th style="width:40%; ">주소</th>
							<th style="width:10%; ">기본배송지</th>
							<th style="width:10%; "></th>
							<th style="width:10%; "></th>
						</tr>
					</thead>

					<tbody id="" class="">
					
					
					
					
						<c:if test="${!empty requestScope.shipList}">
						
							<c:forEach var="svo" items="${requestScope.shipList}">
						
								<c:if test="${svo.ship_default == 1}">
									<tr>
										<td><input type="checkbox" name="select" class="checkOne" value="${svo.ship_no}"/></td>
										<td class="text-center">${svo.ship_name}</td>
										<td class="text-center">${svo.ship_receiver}</td>
										<td class="text-center">${svo.ship_receivertel}</td>
										<td>${svo.ship_postcode} ${svo.ship_address} ${svo.ship_detailAddress} ${svo.ship_extraAddress}</td>
										<td class="text-center"><input type="radio" name="default" value="${svo.ship_no}" checked/></td>
										<td class="text-center"><button class="" type="button" onclick="goUpdate('${svo.ship_no}')" >수정</button></td>
										<td class="text-center"><button class="" type="button" onclick="goDelete('${svo.ship_no}')" >삭제</button></td>
									</tr>
							
								</c:if>
						
								<c:if test="${svo.ship_default == 0}">
					
									<tr>
										<td><input type="checkbox" name="select" class="checkOne" value="${svo.ship_no}"/></td>
										<td class="text-center">${svo.ship_name}</td>
										<td class="text-center">${svo.ship_receiver}</td>
										<td class="text-center">${svo.ship_receivertel}</td>
										<td>${svo.ship_postcode} ${svo.ship_address} ${svo.ship_detailAddress} ${svo.ship_extraAddress}</td>
										<td class="text-center"><input type="radio" name="default" value="${svo.ship_no}"/></td>
										<td class="text-center"><button class="" type="button" onclick="goUpdate('${svo.ship_no}')" >수정</button></td>
										<td class="text-center"><button class="" type="button" onclick="goDelete('${svo.ship_no}')" >삭제</button></td>
									</tr>
								
								</c:if>
								
							</c:forEach>
							
						</c:if>
						
						<c:if test="${empty requestScope.shipList}">
							<tr>
								<td colspan="8" class="text-center">저장된 배송지가 없습니다.</td>
							</tr>
						</c:if>
						
					</tbody>

					<tfoot class="text-center" style="border: 0px;">
						<tr>
							<td colspan="6" class="border" ></td>
							<td class="border" ><button type="button" id="shipAdd" class="">추가</button></td>
							<td class="border" ><button type="button" id="checkDelete" onclick="goCheckDelete()">선택 삭제</button></td>
						</tr>
					</tfoot>
		
				</table>
			</div>
		
	</div>
	
	<form name="shipInfoFrm">
		<input type="hidden" name="inputValue" />
		<input type="text" style="display: none;" />
	</form>
	
<jsp:include page="../common/footer.jsp"></jsp:include>