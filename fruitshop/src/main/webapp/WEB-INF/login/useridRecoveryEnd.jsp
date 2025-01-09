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
    	
    	if( ${empty sessionScope.verifyCertification} && "${requestScope.method}" == "GET") { 	
    		location.href="<%=request.getContextPath()%>/index.ddg";
    	}
    	
    	if( ${!empty sessionScope.loginuser} ) { 	
    		location.href="<%=request.getContextPath()%>/index.ddg";
    	}
    	
    	
    	if( "${requestScope.method}" == "GET" ) {
    		const frm = document.userRecoveryEndFrm;
        	frm.userid.value = "${requestScope.userid}";
        	frm.method="POST";
        	frm.action="<%=request.getContextPath()%>/login/useridRecoveryEnd.ddg";
        	frm.submit();
   
    	}

    	// 계정복구 완료시 5초뒤 자동으로 로그인페이지로 이동시킴
    	if( "${requestScope.method}" == "POST" ) {
    			
    	    setTimeout("location.href='<%=request.getContextPath()%>/login/login.ddg'", 5000);
    	}

});// end of $(document).ready(function(){})--------------------



</script>


<div style="width:1000px; margin: 10px auto; text-align: center;">
	<c:if test="${requestScope.method == 'POST'}">
		<div style="text-align: center; font-size: 14pt; color: navy;">
			<c:if test="${requestScope.n == 1}">
				<div style="height:300px; margin:160px auto;">
	            	<span class="h3" style="color:black;">${requestScope.userid}님의 계정이 복구되었습니다.</span><br><br>
	            	<span class="h4" style="color:black;"><span style="color:red;">5초</span>뒤 로그인 페이지로 이동합니다.</span>
	            </div>
	        </c:if>
	
			<c:if test="${requestScope.n == 0}">
	            <span class="h3">SQL구문 오류가 발생되어 계정을 복구 할 수 없습니다.</span>
	        </c:if>
		</div>
	</c:if>
</div>





<form name="userRecoveryEndFrm">
	<input type="hidden" name="userid" value="${requestScope.userid}" style="display:none;" />
	<input type="text" style="display: none;" />
	<input type="text" style="display: none;" />
</form>


<jsp:include page="../common/footer.jsp"></jsp:include>
