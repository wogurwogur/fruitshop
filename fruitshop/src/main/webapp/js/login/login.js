/**
 * 
 */

$(()=>{
	
	$("button#btnLogin").click( e => {
		goLogin();
	});

	$("input#loginPasswd").bind("keyup", e => {
		if(e.keyCode == 13) { 
			goLogin();
		} 
	});



	
}); // end of $(()=>{}); ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~



// 로그인을 처리하는 함수
function goLogin() {

    const userid = $("input#loginUserid");
    if(userid.val().trim() == "") {
        alert("아이디를 입력하세요");
        return;
    }

    const pwd = $("input#loginPasswd");
    if(pwd.val().trim() == "") {
        alert("암호를 입력하세요");
        return;
    }

	if($("input:checkbox[id='saveid']").prop("checked")) {
		localStorage.setItem('saveid', userid.val());
		
	}
	else {
		localStorage.removeItem("saveid");
	}
	
	$.ajax({
		url: "login.ddg",
		data: { "userid": $("input#loginUserid").val(), "passwd": $("input#loginPasswd").val() },
		type: "post",
		async: true,
		dataType: "JSON",
		success: function(json) {
			
			if(json.isExists) {
				
				if(json.idle == 0) {
					alert("휴면 계정입니다."+"\n"+"복구 페이지로 이동합니다.");
					
					location.href="/fruitshop/login/useridRecovery.ddg";
				}
				else {
					if(json.requirePwdChange) {
						alert("비밀번호를 변경한지 3개월이 지났습니다."+"\n"+"비밀번호 변경 페이지로 이동합니다.");
						
						location.href="/fruitshop/login/passwdUpdateEnd.ddg?userid="+json.userid;
					}
					else { 		
						location.href="/fruitshop/index.ddg";
					}
				}		
			}
			else {
				$("div#error").html("아이디 혹은 비밀번호가 틀렸습니다.");
			}
		},
		error: function(request, status, error) {
			alert("code: " + request.status + "\n" + "message: " + request.responseText + "\n" + "error: " + error);
		}
	});

}


// 로그아웃을 처리하는 함수
function goLogOut(ctx_Path) {

    // 로그아웃을 처리해주는 페이지로 이동
    location.href=`${ctx_Path}/login/logout.ddg`;
    
}






