<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">

	// 메시지 출력해주기
	alert("${requestScope.message}");

	// 페이지 이동
	location.href = "${requestScope.loc}";
	
</script>