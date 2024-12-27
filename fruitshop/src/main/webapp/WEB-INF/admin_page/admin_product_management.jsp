<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 

    
       
<jsp:include page="../common/header.jsp"></jsp:include>
<jsp:include page="adminpage_header.jsp"></jsp:include>


<%-- Custom CSS --%>
<link rel="stylesheet" href="<%= request.getContextPath()%>/css/adminpage/product_management.css">



<script type="text/javascript">

	$(document).ready(function(){
		
		// 검색 시 엔터를 치는 경우
		$("input[name='searchFruit']").bind("keydown", function(e){
			if(e.keyCode == 13) {
				goSearch();
			}
			
		}); // end of $("input[name='searchFruit']").bind("keydown", function(e)
				
				
				
		// 등록하기 클릭
		$("div#prdRegister").click(function(){
			location.href =`${pageContext.request.contextPath}/admin/adminProductRegister.ddg`;
		});		
	
	
	}); // end of $(document).ready(function()
			
	// 상품을 검색한다.
	function goSearch() {
		
		const searchFruit = $("input[name='searchFruit']").val();
		
		if(searchFruit == ""){
			alert("검색 내용을 입력해주시기 바랍니다.")
			return // goSearch() 함수를 종료한다.
		}
		
		const frm = document.fruit_search_frm;
		frm.action = "adminProduct.ddg"; 
		frm.method = "get"; 
		frm.submit();	
		
			
	} // end of function goSearch()	
	
	
	// 특정 상품을 클릭하면 그 상품의 상세정보를 보여주도록 한다.
	function goDetail(prod_no) {	
		
		const frm = document.productOneDetail_frm
		frm.prod_no.value = prod_no; // 상품 번호 값 데이터 가지고 URL 이동
		
		frm.action = "adminProductOneDetail.ddg";
		frm.method = "post"; 
		frm.submit();
		
	}//  end of function goDetail(prod_no)
	
	
	
	// 일괄 삭제 버튼을 클릭하면 삭제 처리를 해준다.
	function goDelete(prod_no) {	
		
		alert("삭제버튼 클릭했습니당")
/* 		const frm = document.productOneDetail_frm
		frm.prod_no.value = prod_no; // 상품 번호 값 데이터 가지고 URL 이동
		
		frm.action = "adminProductOneDetail.ddg";
		frm.method = "post"; 
		frm.submit(); */
		
	}//  end of function goDetail(prod_no)
	

</script>





<div id="container">

	
	<div id="fruitSearch">
		<%-- 검색창 시작 --%>
		<form name="fruit_search_frm">
			<input type="text" name="searchFruit" placeholder="과일명으로 검색하기" maxlength="50" />
			<input type="text" style="display: none;" />
			<i class="fa-solid fa-magnifying-glass searchIcon" onclick="goSearch()"></i>
		</form>
		<%-- 검색창 끝 --%>
		
		<a id="totalList" href="adminProduct.ddg">전체 목록 보기</a>
	</div>
	

	<div id="table">
		<table class="table table-borderless">
			<colgroup> <%-- 테이블 간 간격 설정 --%>
	    	<col style="width: 4%;">
	    	<col style="width: 4%;">
	    	<col style="width: 50%;">
	    	<col style="width: 10%;">
	    	<col style="width: 10%;">
	    	<colgroup>
			<thead>
				<tr class="prdInfoTitle">	
					<th scope="col"><input type="checkbox" id="checkAll"></input></th>
					<th scope="col">번호</th>
					<th scope="col">상품명</th>
					<th scope="col">재고</th>
					<th scope="col">등록일자</th>					
				</tr>
			</thead>
			<tbody>
				<c:if test="${not empty requestScope.prdList}">
					<c:forEach var="prdvo" items="${requestScope.prdList}" varStatus="status">
						<tr class="prdInfo" onclick="goDetail(${prdvo.prod_no})">	
						
							<td><input type="checkbox" id="checkEach"></td>
							
							<fmt:parseNumber var="currentShowPageNo" value="${requestScope.currentShowPageNo}" />
							<%-- fmt:parseNumber 은 문자열을 숫자형식으로 형변환 시키는 것이다. --%>
							<td>${(requestScope.totalProductCount) - (currentShowPageNo - 1) * 10 - (status.index)}</td> <%-- 10개씩 보여줌 --%>
							<td>${prdvo.prod_name}</td>
							<td>${prdvo.prod_inventory}</td>
							<td>${prdvo.prod_regidate}</td>
						</tr>
					</c:forEach>
				</c:if>
					
				<c:if test="${empty requestScope.prdList}"> <%-- 데이터가 없을 경우 --%>
		      		<tr>
		            	<td colspan="6" style="text-align: center; padding: 5% 0; font-size: 20pt;">데이터가 존재하지 않습니다.</td>
		            </tr>
		      	</c:if>
					
			</tbody>
		</table>
	</div>
	
	
	<div id="prdmanageBtn" style="display: flex;">
		<div id="prdRegister">
			상품등록
		</div>
	
		<div id="totalDelete" onclick="goDelete(${prdvo.prod_no})">
			일괄삭제
		</div>
	</div>	
	
	<c:if test="${not empty requestScope.prdList}">
		<div id="pageBar">
			${requestScope.pageBar}
		</div>
	</c:if>

	

</div>


<form name="productOneDetail_frm">
	<input type="hidden" name="prod_no" />
	<input type="hidden" name="goBackURL" value="${requestScope.currentURL}" />
</form>


<jsp:include page="../common/footer.jsp"></jsp:include>