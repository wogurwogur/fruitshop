<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  


<jsp:include page="../common/header.jsp"></jsp:include>


<%-- Custom CSS --%>
<link rel="stylesheet" href="<%= request.getContextPath()%>/css/adminpage/product_onedetail.css">    
    
    
<div id="container">
	
	<div id="title">
		<span> 상품 정보 </span>
	</div>
	
	<div id="table">
		<table class="table table-borderless">
			<colgroup>
			<col style="width: 20%;">
			<col style="width: 80%;">
			<colgroup>
			<tbody>
				<tr>
					<td class="tdTitle">상품명</td>
					<td>
						<input type="text" name="prod_name" id="prod_name" maxlength="20" value="" />
						<span class="error">상품명은 필수입력 사항입니다.</span>
					</td>
				</tr>
				<tr>	
					<td class="tdTitle">원가</td>
					<td>
						<input type="text" name="prod_cost" id="prod_cost" value="" />
						<span class="error">원가는 필수입력 사항입니다.</span>
					</td>
				</tr>
				<tr>
					<td class="tdTitle">가격</td>
					<td>
						<input type="text" name="prod_price" id="prod_price" value="" />
						<span class="error">가격은 필수입력 사항입니다.</span>
					</td>
				</tr>
				<tr>
					<td class="tdTitle">상품이미지</td>
					<td>
						<input type="file" name="prod_thumnail" accept='image/*' />
					</td>
				</tr>
				<tr>
					<td class="tdTitle">상품상세이미지</td>
					<td>
						<input type="file" name="prod_descript" accept='image/*' />
					</td>
				</tr>
				<tr>
					<td class="tdTitle">재고</td>
					<td>
						<input name="prod_inventory" id="spinnerPqty" value="1"> 개
					</td>
				</tr>
				<tr>
					<td class="tdTitle">계절분류</td>
					<td>
						<select name="fk_season_no">
							 <option value="">선택</option>
		                     <option value="1">봄</option>
		                     <option value="2">여름</option>
		                     <option value="3">가을</option>
		                     <option value="4">겨울</option> 
			              
			                 <%--  <c:forEach var="spvo" items="${requestScope.specList}">
			                  		<option value="${spvo.snum}">${spvo.sname}</option>
			                  </c:forEach> --%>    
						</select>
					</td>
				</tr>
				
				<tr>
                    <td colspan="2" class="text-center" style="padding: 30px 0 10px 0;">
                       <input type="button" class="btn btn-success mr-5" value="수정하기" onclick="" />
                       <input type="button" class="btn btn-success mr-5" value="삭제하기" onclick="" />
                       <input type="reset"  class="btn btn-danger" value="돌아가기" onclick="" />
                    </td>
                </tr>
				
			</tbody>
		</table>
	</div>
</div>


<jsp:include page="../common/footer.jsp"></jsp:include>