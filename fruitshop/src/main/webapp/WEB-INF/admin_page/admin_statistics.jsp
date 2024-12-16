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

/* drop down start */
.dropbtn {
  background-color: #04AA6D;
  color: white;
  padding: 16px;
  font-size: 16px;
  border: none;
}

.dropdown {
  position: relative;
  display: inline-block;
}

.dropdown-content {
  display: none;
  position: absolute;
  background-color: #f1f1f1;
  min-width: 160px;
  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
  z-index: 1;
}

.dropdown-content button {
  color: black;
  padding: 12px 16px;
  text-decoration: none;
  display: block;
  border: none;
}

.dropdown-content a:hover {background-color: #ddd;}

.dropdown:hover .dropdown-content {display: block;}

.dropdown:hover .dropbtn {background-color: #3e8e41;}

/* drop down end */


/* search start */
form.example input[type=text] {
  padding: 10px;
  font-size: 17px;
  border: 1px solid grey;
  float: left;
  width: 80%;
  background: #f1f1f1;
}

form.example button {
  width: 20%;
  padding: 10px;
  background: #2196F3;
  color: white;
  font-size: 17px;
  border: 1px solid grey;
  border-left: none;
  cursor: pointer;
}
/* search end  */
</style>

<%-- div top nav start --%>
<div class="container-fluid " id="admin_top_nav">

	<%-- dropdown div start --%>
	<div class="dropdown">
	  <button class="dropbtn">정렬</button>
	  <div class="dropdown-content">
	    <button type="button">징계받은 회원</button>
	    <button type="button">----</button>
	    <button type="button">----</button>
	  </div>
	</div>
	<%-- dropdown div end --%>
	
	
	<%-- center div start --%>
	<div id="admin_top_nav_center">
		<span>통계</span>
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

<div class="container-fluid ">
	
	회원리스트
	
</div>