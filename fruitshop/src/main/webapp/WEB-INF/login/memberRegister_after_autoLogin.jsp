<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath()%>/bootstrap-4.6.2-dist/js/bootstrap.bundle.min.js" ></script>

<script type="text/javascript">


	window.onload = () => {
		
		$.ajax({
			url: "<%= request.getContextPath()%>/login/login.ddg",
			data: { "userid": "${requestScope.userid}", "passwd": "${requestScope.passwd}" },
			type: "post",
			async: true,
			dataType: "JSON",
			success: function(json) { 
				
				console.log("확인용 : "+ json);
				
				location.href="/fruitshop/index.ddg";
				
			},
			error: function(request, status, error) {
				alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
			}
		});
	
	}
	
</script>
