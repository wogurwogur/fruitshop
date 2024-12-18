<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<jsp:include page="../common/header.jsp"></jsp:include>

<style type="text/css">

* {
	font-family: 'Noto Sans KR', sans-serif;
}
</style>


<script type="text/javascript">
    $(document).ready(function(){
    	
    	if( ${empty sessionScope.verifyCertification} ) { 	
    		location.href="<%=request.getContextPath()%>/index.ddg";
    	}
    	
    	if( ${!empty sessionScope.loginuser} ) { 	
    		location.href="<%=request.getContextPath()%>/index.ddg";
    	}
    	
    	
    
    		const frm = document.userRecoveryEndFrm;
        	frm.userid.value = "${requestScope.userid}";
        	frm.method="POST";
        	frm.action="<%=request.getContextPath()%>/login/useridRecoveryEnd.ddg";
        	frm.submit();
   
    	

});// end of $(document).ready(function(){})--------------------



</script>

<form name="userRecoveryEndFrm">
	<input type="hidden" name="userid" value="${requestScope.userid}" style="display:none;" />
	<input type="text" style="display: none;" />
	<input type="text" style="display: none;" />
</form>


<jsp:include page="../common/footer.jsp"></jsp:include>
