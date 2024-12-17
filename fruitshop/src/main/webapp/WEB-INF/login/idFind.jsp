<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:include page="../common/header.jsp"></jsp:include>

<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/login/idFind.css" />

<script type="text/javascript" src="<%= request.getContextPath() %>/js/login/idFind.js"></script>

<script type="text/javascript">
$(()=>{
	
	 const method = "${requestScope.method}";
	 
	 if(method == "GET") {
		 $("div#div_findResult").hide();
	 }
	 
	 if(method == "POST") {
		 $("input:text[name='name']").val("${requestScope.name}");
		 $("input:text[name='email']").val("${requestScope.email}");
	 }
	
	// 찾기 클릭시
	$("button#findUserid").click( e=>{
		goFind();
	});
	  
	$("input:text[name='email']").bind("keyup", e => {
		if(e.keyCode == 13) {
			goFind();
		}
	});
	
 
}); // end of window.onload ~








function goFind() {
	
	const name = $("input:text[name='name']").val().trim();
    
    if(name == "") {
       alert("성명을 입력하세요!!");
       return; // 종료
    }
	
    const email = $("input:text[name='email']").val();
    
    const regExp_email = new RegExp(/^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i);  
          
    const bool = regExp_email.test(email);
  
    if(!bool) {
    	alert("e메일을 올바르게 입력하세요!!");
    	return; 
    }    
    

    const frm = document.idFindFrm;
    
    frm.action = "<%= request.getContextPath() %>/login/idFind.ddg";
    frm.method = "post";
    frm.submit(); 
}


</script>

<div class="container">

	<form name="idFindFrm" action="<%=request.getContextPath()%>/login/idFind.ddg" method="post">
	
		<div style="width:450px; margin : 10px auto;">
		
			<div class="text-center" style="margin-top:100px; margin-bottom:50px;">
				<h2 style="font-weight:bold;">아이디 찾기</h2>
			</div>
			
			<div>
				<table style="margin : 10px auto;">
					<tbody>
						<tr>
							<td>
								<input type="text" name="name" id="name" placeholder="성명" style="width:450px; height:50px; line-height:50px; margin:5px 0; padding-left:10px; font-size: 16px;"/>
							</td>
						</tr>
						<tr>
							<td>
								<input type="text" name="email" id="email" placeholder="이메일" style="width:450px; height:50px; line-height:50px; margin:5px 0; padding-left:10px; font-size: 16px;"/>	
							</td>
						</tr>
					</tbody>
				</table>
			</div>

			<div class="text-center" style="margin-bottom:100px;" >
	        	<button type="button" id="findUserid" class="h5" onlick="" style="width:450px; height:50px; margin:10px 0; display:inline-block; line-height:50px; background-color:#000000; color:white;">찾기</button>
	        
				<button type="button" id="back" class="h5" onlick="" style="width:450px; height:50px; margin:10px 0; display:inline-block; line-height:50px; background-color:#000000; color:white;">취소</button>
			</div>
	
		</div>
		
	</form>
	
	<%-- 결과가 출력되는 곳 --%>
	<div class="my-3 text-center" id="div_findResult">
	   ID : <span style="color: red; font-size: 16pt; font-weight: bold;">${requestScope.userid}</span>
	</div>
		
</div>



<jsp:include page="../common/footer.jsp"></jsp:include>

