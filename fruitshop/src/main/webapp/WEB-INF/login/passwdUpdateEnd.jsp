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
    	


   	   	$("button#passwdUpdate").click(function(){
      	   
        	const passwd  = $("input:password[name='passwd']").val();
         	const passwd2 = $("input:password[id='passwd2']").val();
         
         	const regExp_passwd = new RegExp(/^.*(?=^.{8,20}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&*+=-]).*$/g);  
         	const bool = regExp_passwd.test(passwd);   
            
         	if(!bool) {
        	 	$("div#error").html("올바른 비밀번호가 아닙니다.");
             	$("input:password[name='passwd']").val("");
             	$("input:password[id='passwd2']").val("");
             	return; 
         	}
         	else {
         		if(passwd != passwd2) {
         	   		$("div#error").html("비밀번호가 일치하지 않습니다.");
                	$("input:password[name='passwd']").val("").focus();
                	$("input:password[id='passwd2']").val("");
                	return; 
            }
            const frm = document.pwdUpdateEndFrm;
            frm.action = "<%=request.getContextPath()%>/login/passwdUpdateEnd.ddg";
            frm.method = "post";
            frm.submit();
        	}
		
         
		});// end of $("button.btn-success").click(function(){})----

		
		
		// 비밀번호 변경 완료시 5초뒤 자동으로 로그인페이지로 이동시킴
		if( "${requestScope.method}" == "POST" ) {
			
	    	setTimeout("location.href='<%=request.getContextPath()%>/login/login.ddg'", 5000);
	    }
	
	
	
});// end of $(document).ready(function(){})--------------------
</script>

<div style="width:450px; margin: 10px auto;">
	<c:if test="${requestScope.method == 'GET'}">
		<form name="pwdUpdateEndFrm">
			
			<h2 style="font-weight:bold; text-align: center; margin-top:100px; margin-bottom:100px;">비밀번호 변경</h2>
		
			<input type="password" name="passwd" placeholder="새 비밀번호" style="width:450px; height:50px; line-height:50px; margin:5px 0; padding-left:10px; font-size: 16px;"/>
	
			<input type="password" id="passwd2" placeholder=" 새 비밀번호 확인" style="width:450px; height:50px; line-height:50px; margin:5px 0; padding-left:10px; font-size: 16px;"/>
		
			<div class="rule" id="rule" style="height:50px; font-size: 10pt; padding-left: 5px;">(영문 대소문자/숫자/특수문자 조합, 8자~20자)</div>
		
			<div class="error" id="error" style="color:red; height:30px; padding-left: 5px;"></div>
			
	
			
			<input type="hidden" name="userid" value="${requestScope.userid}" />
	
			<button type="button" id="passwdUpdate" class="h6" style="width:450px; height:50px; line-height:50px; margin-bottom: 110px; line-height:50px; background-color:#000000; color:white;">암호 변경하기</button>
			
		</form>
	</c:if>
</div>	

<div style="width:1000px; margin: 10px auto; text-align: center;">
	<c:if test="${requestScope.method == 'POST'}">
		<div style="text-align: center; font-size: 14pt; color: navy;">
			<c:if test="${requestScope.n == 1}">
				<div style="height:300px; margin:160px auto;">
	            	<span class="h3" style="color:black;">${requestScope.userid}님의 비밀번호가 변경되었습니다.</span><br><br>
	            	<span class="h4" style="color:black;"><span style="color:red;">5초</span>뒤 로그인 페이지로 이동합니다.</span>
	            </div>
	        </c:if>
	
			<c:if test="${requestScope.n == 0}">
	            <span class="h3">SQL구문 오류가 발생되어 비밀번호 변경을 할 수 없습니다.</span>
	        </c:if>
		</div>
	</c:if>
</div>


<jsp:include page="../common/footer.jsp"></jsp:include>
