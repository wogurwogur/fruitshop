<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String ctxPath = request.getContextPath();
%>  


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
			<th style="width:15%;">이미지</th>
			<th style="width:25%;">과일명</th>
			<th style="width:10%;">판매가</th>	
		</tr>	
	</thead>
	
	
	<tbody style="text-align:center;">
		<td><img /></td>
		<td>수박 5kg</td>	
		<td>75,900원</td>
	</tbody>



</table>


















