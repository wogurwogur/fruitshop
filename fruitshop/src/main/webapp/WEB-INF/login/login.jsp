<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>   

<jsp:include page="../common/header.jsp"></jsp:include>

<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/css/login/login.css" />

<script type="text/javascript" src="<%= request.getContextPath() %>/js/login/login.js"></script>

<script type="text/javascript">
	$(()=>{
		
		$("button#btnRegister").click(()=>{
			location.href="<%=request.getContextPath()%>/member/memberRegister.ddg";
		});

		if( ${!empty sessionScope.loginuser} ) { 
			
			location.href="<%=request.getContextPath()%>/index.ddg";
		}
		
		
		if( ${empty sessionScope.loginuser} ) {
			
			const loginUserid = localStorage.getItem('saveid');
			
			if(loginUserid != null) { 
				$("input#loginUserid").val(loginUserid);
				$("input:checkbox[id='saveid']").prop("checked", true);
				$("input#loginPasswd").focus();
			}
		}
		
	});

	
	
</script>

<div class="container">

	<form name="loginFrm" action="<%=request.getContextPath()%>/login/login.ddg" method="post">
	
		<div style="width:450px; margin : 10px auto;">
		
			<div class="text-center" style="margin-top:100px; margin-bottom:50px;">
				<h2 style="font-weight:bold;">로그인</h2>
			</div>
			
			<div>
				<table style="margin : 10px auto;">
					<tbody>
						<tr>
							<td>
								<input type="text" name="userid" id="loginUserid" placeholder="아이디" style="width:450px; height:50px; line-height:50px; margin:5px 0; padding-left:10px; font-size: 16px;"/>
							</td>
						</tr>
						<tr>
							<td>
								<input type="password" name="passwd" id="loginPasswd" placeholder="비밀번호" style="width:450px; height:50px; line-height:50px; margin:5px 0; padding-left:10px; font-size: 16px;"/>	
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="error" id="error" style="color:red;"></div>
			<div style="margin : 0 auto;">
				<span class="" style="height:50px; line-height:50px;">
					<input type="checkbox" id="saveid" />&nbsp;<label for="saveid">아이디 저장</label>&nbsp;&nbsp;
	        		<img src="//img.echosting.cafe24.com/design/skin/default/member/ico_access.gif" alt="보안접속"> 보안접속       
	        	</span>     
	        	<span class="btn float-right"  style="height:50px; line-height:40px;">
	            	<a href="" id="idFind" class="">아이디찾기</a> / 
	            	<a href="" id="passwdFind" class="">비밀번호찾기</a>
	            </span>
	        </div>
			<div class="text-center" style="margin-bottom:100px;" >
	        	<button type="button" id="btnLogin" class="h5" style="width:450px; height:50px; margin:10px 0; display:inline-block; line-height:50px; background-color:#000000; color:white;">로그인</button>
	        
				<button type="button" id="btnRegister" class="h5" style="width:450px; height:50px; margin:10px 0; display:inline-block; line-height:50px; background-color:#000000; color:white;">회원가입</button>
			</div>
	
		</div>
		
	</form>
	
</div>


<jsp:include page="../common/footer.jsp"></jsp:include>
    
    