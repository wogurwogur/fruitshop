<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String ctxPath = request.getContextPath();
%>  

<script type="text/javascript">

	function productSelect(prod_no){
		
		
		
		
		$.ajax({
			
			url: "<%= ctxPath%>/community/productSelect.ddg",
			type:"get",
			datatype:"json",
			data:{
				
				"prod_no":prod_no
				
			}
			success:function(json){
				
				
				
			}
			error{
				
			}
			
			
		})	;
		
	};

</script>





<form name="productFind">
      <select name="searchType" style="width:30%; height:10%;">
         <option value="fruitname">과일명</option>
         <option value="seasons">계절</option>         
      </select>
      <input type="text" name="searchWord" style="width:68.5%; height:10%;"/>
      <input type="text" style="display: none;" />
</form>

<hr>
<table class="table table-bordered">
	<thead>
		<tr>
			<th style="">이미지</th>
			<th style="">과일명</th>
			<th style="">판매가</th>	
		</tr>	
	</thead>
	
	

			<c:forEach var="rpl" items="${requestScope.rproductList}" varStatus="status">
				<tr style="text-align:center" onclick="productSelect('${rpl.prod_no}')">			
					<td><img src="<%= ctxPath%>/images/product/thumnail/${rpl.prod_thumnail}" style ="width:45px; height: 45px;"/></td>
					<td>${rpl.prod_name}</td>
					<td><fmt:formatNumber pattern="###,###">${(rpl.prod_price)}</fmt:formatNumber>원</td>
				</tr>
			</c:forEach>		


</table>


















