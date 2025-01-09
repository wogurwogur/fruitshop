<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 

<jsp:include page="../common/header.jsp"></jsp:include>

<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/login/passwdFind.css" />

<style type="text/css">

* {
	font-family: 'Noto Sans KR', sans-serif;
}
</style>

<script type="text/javascript">

$(()=>{
	
	if( ${!empty sessionScope.loginuser} ) { 	
		location.href="<%=request.getContextPath()%>/index.ddg";
	}
	
	const method = "${requestScope.method}";

	if(method == "GET") {
     $("div#div_findResult").hide();
    }

    if(method == "POST"){
        $("input:text[name='userid']").val("${requestScope.userid}");
        $("input:text[name='email']").val("${requestScope.email}");
        
        if(${requestScope.isUserExist == true && requestScope.sendMailSuccess == true}) {
             $("button#findPasswd").hide();  
             $("button#back").hide();
        }
     }

	$("button#findPasswd").click(function(){
		goFind(); 
	});
	     
	$("input:text[name='email']").bind("keyup", function(e){
		if(e.keyCode == 13) {
			goFind();
	 	}
	});

	     
	     
	$("input:text[name='input_confirmCode']").bind("keyup", function(e){
		if(e.keyCode == 13) {
			
			const input_confirmCode = $("input:text[name='input_confirmCode']").val().trim();
	    	 
			if(input_confirmCode =="") {
				$("span#error").html("인증코드를 입력하세요.");
				return;
		    }	 
			const frm =document.verifyCertificationFrm;
			frm.userCertificationCode.value = input_confirmCode;
			frm.userid.value = $("input:text[name='userid']").val();
			frm.page.value = "비밀번호찾기";
				    	 
			frm.action = "<%=request.getContextPath()%>/login/verifyCertification.ddg";
			frm.method = "post";
			frm.submit();
			
			
			
	 	}
	});
	
	
	
	
	$("button#verifyCertification").click( ()=>{
	    	 
		const input_confirmCode = $("input:text[name='input_confirmCode']").val().trim();
	    	 
		if(input_confirmCode =="") {
			$("span#error").html("인증코드를 입력하세요.");
			return;
	    }	 
		const frm =document.verifyCertificationFrm;
		frm.userCertificationCode.value = input_confirmCode;
		frm.userid.value = $("input:text[name='userid']").val();
		frm.page.value = "비밀번호찾기";
			    	 
		frm.action = "<%=request.getContextPath()%>/login/verifyCertification.ddg";
		frm.method = "post";
		frm.submit();

	});
	
	
	
	
	
	$("button#back").click( e=>{
		location.href="<%=request.getContextPath() %>/login/login.ddg";
	});
	
	
	     
	     
}); // end of window.onload ~

  
function goFind() {
	    
    const userid = $("input:text[name='userid']").val().trim();
    if(userid == "") {
    	$("span#error").html("아이디를 입력하세요.");
        return;
    }
    
    const email = $("input:text[name='email']").val();
    if(email == "") {
        $("span#error").html("이메일을 입력하세요.");
      	return; 
    }    
      
    const frm = document.pwdFindFrm;
    frm.action = "<%=request.getContextPath()%>/login/passwdFind.ddg";
    frm.method = "post";
    frm.submit();
    
 }// end of function goFind(){}-----------------------



</script>

<div class="container" >

	<form name="pwdFindFrm" action="<%=request.getContextPath()%>/login/passwdFind.ddg" method="post">
	
		<div style="width:450px; margin : 10px auto 100px auto;">
		
		
			<div class="text-center" style="margin-top:100px; margin-bottom:50px;">
				<h2 style="font-weight:bold;">비밀번호 찾기</h2>
			</div>
			
			
			
			<div>
				<table style="margin : 10px auto;">
					<tbody>
						<tr>
							<td>
								<input type="text" name="userid" id="userid" placeholder="아이디" style="width:450px; height:50px; line-height:50px; margin:5px 0; padding-left:10px; font-size: 16px;"/>
							</td>
						</tr>
						<tr>
							<td>
								<input type="text" name="email" id="email" placeholder="이메일" style="width:450px; height:50px; line-height:50px; margin-top:5px; margin-bottom:7px; padding-left:10px; font-size: 16px;"/>	
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			
			
			
			<div style="vertical-align:top; height:50px;">
			
				<%-- 결과가 출력되는 곳 --%>
				<div class="mt-3 text-center" id="div_findResult">
					<c:if test="${requestScope.isUserExist == false}">
	      				<span style="color: red;">검색되는 회원이 존재하지 않습니다.</span>
	   				</c:if>
					
					<c:if test="${requestScope.isUserExist == true && requestScope.sendMailSuccess == true}">
				    	<span style="font-size: 10pt;">
					          인증코드가 ${requestScope.email}로 발송되었습니다.<br>
					          인증코드를 입력해주세요
				     	</span>
				     <br>
				     <input type="text" name="input_confirmCode" />
				     <br><br> 
				     <div>
				     <button type="button" id="verifyCertification" class="h5" style=" width:450px; height:50px; margin:10px 0; display:inline-block; line-height:50px; background-color:#000000; color:white;">인증하기</button><br><br><br><br>
				   	 </div>
				   </c:if>
				</div>
				<%-- 에러가 출력되는 곳 --%>
				<div class="my-3 text-center">
					<span id="div_error" style="color:red; font-size: 14pt; font-weight: bold;"></span>
				</div>
			
			</div>
			
			
			
			

			<div class="text-center" style="margin-bottom:100px;" >
	        	<button type="button" id="findPasswd" class="h5" style="width:450px; height:50px; margin:10px 0; display:inline-block; line-height:50px; background-color:#000000; color:white;">인증번호 발송</button>
	        
				<button type="button" id="back" class="h5" style="width:450px; height:50px; margin:10px 0; display:inline-block; line-height:50px; background-color:#000000; color:white;">취소</button>
				
			</div>
	
		</div>
		
	</form>
	
	
		
		
</div>



<%-- 인증하기 form --%>
<form name="verifyCertificationFrm">
   <input type="hidden" name="userCertificationCode" />
   <input type="hidden" name="userid" />
   <input type="hidden" name="page" />
</form>



<jsp:include page="../common/footer.jsp"></jsp:include>


