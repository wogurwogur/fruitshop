<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<jsp:include page="../common/header.jsp"></jsp:include>

<script type="text/javascript">
	$(()=>{
		
		$("button#btnRegister").click(()=>{
			
			location.href="<%= request.getContextPath()%>/member/memberRegister.ddg";
		});
		
	});


</script>

<div class="container">

	<form class="loginFrm" action="" method="post">
	
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
								<input type="password" name="pwd" id="loginPwd" placeholder="비밀번호" style="width:450px; height:50px; line-height:50px; margin:5px 0; padding-left:10px; font-size: 16px;"/>	
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			
			<div style="margin : 10px auto;">
				<span class="" style="height:50px; line-height:50px;">
	        		&nbsp;<img src="//img.echosting.cafe24.com/design/skin/default/member/ico_access.gif" alt="보안접속"> 보안접속       
	        	</span>     
	        	<span class="btn float-right"  style="height:50px; line-height:40px;">
	            	<a href="" id="idFind" class="">아이디찾기</a> / 
	            	<a href="" id="pwdFind" class="">비밀번호찾기</a>
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
    
    