<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% 
	String ctxPath = request.getContextPath();
%>

<script type="text/javascript">


</script>

<script src="<%=ctxPath %>/js/admin/admin_member_management.js"></script>
<style>

div#admin_top_nav{
	display: flex;
	margin-top: 1%;
}

div#admin_top_nav_center{
	margin: 0 auto;
}



</style>

<%-- div top nav start --%>
<div class="container" id="admin_top_nav">

	<%-- dropdown div start --%>
	<div>

	</div>
	<%-- dropdown div end --%>
	
	
	<%-- center div start --%>
	<div id="admin_top_nav_center">
		<span>메인페이지 관리</span>
	</div>
	<%-- center div end --%>
		
		
	<%-- search div start --%>
	<div>
		<form class="example" id="member_management_frm" style="margin:auto;max-width:300px">
		  <input type="text" placeholder="Search.." name="search2">
		  <button type="submit"><i class="fa fa-search"></i></button>
		</form>
	</div>
	<%-- search div end --%>
	
</div>
<%-- div top nav end --%>

<div class="container">
	
	<table class="table">
		<thead>
			<tr>
				<th></th>
			</tr>
		</thead>
		<tbody>
		
			
		
		</tbody>
	</table>
	
</div>