<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript">
	
	// 메세지 출력
	alert("${requestScope.message}");
	location.href = "${requestScope.loc}";
	
</script>