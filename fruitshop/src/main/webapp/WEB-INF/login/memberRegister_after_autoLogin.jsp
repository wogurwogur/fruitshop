<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">

	window.onload = () => {
		
		alert("회원가입에 갑사드립니다.");
		
		const frm = document.LoginFrm;
		
		frm.action = "<%= request.getContextPath() %>/login/login.ddg";
		frm.method="post";
		frm.submit();
	}
	
</script>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form name="LoginFrm">
		<input type="hidden" name="userid" value="${requestScope.userid}"/>
		<input type="hidden" name="passwd" value="${requestScope.passwd}"/>
	</form>
</body>
</html>