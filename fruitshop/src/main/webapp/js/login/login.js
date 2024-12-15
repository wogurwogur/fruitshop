/**
 * 
 */

$(()=>{
	
	



	
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
		alert("아이디 저장 체크");
		localStorage.setItem('saveid', userid.val());
		
	}
	else {
		localStorage.removeItem("saveid");
	}
	
	
	
    const frm = document.loginFrm;
    frm.method="post";
    frm.submit();
}





// 로그아웃을 처리하는 함수
function goLogOut(ctx_Path) {

    // 로그아웃을 처리해주는 페이지로 이동
    location.href=`${ctx_Path}/login/logout.ddg`;
    
}






