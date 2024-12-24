/**
 * 
 */

let emailcheck = true;


$(()=>{
	
	$("span#pwdError").html("미입력시 비밀번호가 유지됩니다.").addClass("blue");
	
    // 비밀번호
    $("input#passwd").bind('blur', e => { 

        const passwd = $(e.target).val(); 

        const regExp_passwd = new RegExp(/^.*(?=^.{8,20}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&*+=-]).*$/g);

        const bool = regExp_passwd.test(passwd);

		if(passwd == "") {
			$("span#pwdError").html("미입력시 비밀번호가 유지됩니다.").addClass("blue").removeClass("red");
		}
		else {
			if(!bool) {
				$("span#pwdError").html("올바른 비밀번호가 아닙니다.").addClass("red").removeClass("blue");
			}
			else {
			    $("span#pwdError").html("").removeClass("red").removeClass("blue");
			}
		}
        
    });


    // 비밀번호 확인
    $("input#passwdcheck").bind('blur', e => { 
		
		
		const passwd_1 = $("input#passwd").val(); 

		const passwd_2 = $(e.target).val();

		const regExp_passwd = new RegExp(/^.*(?=^.{8,20}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&*+=-]).*$/g); 

		const bool = regExp_passwd.test(passwd_1);
		
		if(passwd_1 == "" && passwd_2 == "" ) {
			$("span#pwdError").html("미입력시 비밀번호가 유지됩니다.").addClass("blue").removeClass("red");
		}
		else {
			if(!bool) {
						$("span#pwdError").html("올바른 비밀번호가 아닙니다.").addClass("red").removeClass("blue");	
					}
			else {
				if(passwd_1 != passwd_2) {
	
					$("input#passwd").val("").focus(); 
	
					$(e.target).val(""); 
	
					$("span#pwdError").html("비밀번호가 일치하지 않습니다.").addClass("red").removeClass("blue");
				}
				else {
					$("span#pwdError").html("").removeClass("red").removeClass("blue");
				}
			}
		}
		
	});



    // 성명
    $("input:text[name='name']").bind('blur', e => { 

        const name = $(e.target).val(); 

        const nameReg = /^[가-힣]{2,6}$/; 

        const bool = nameReg.test(name);
    
        if(!bool) { 

            $(e.target).parent().find("span.error").html("올바른 성명이 아닙니다.").addClass("red");
        }
        else { 
            name_check = true;
            $(e.target).parent().find("span.error").html("").removeClass("red");
        }
      
    });
	


    // 이메일
    $("input:text[name='email']").bind('blur', e => { 

        const email = $(e.target).val(); 

        const regExp_email = new RegExp(/^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i); 

        const bool = regExp_email.test(email);

        if(!bool) {

            $(e.target).parent().find("span.error").html("올바른 이메일이 아닙니다.").addClass("red").removeClass("blue");
        }
        else {
            $.ajax({
                url : "/fruitshop/mypage/emailDuplicateCheck.ddg",
                data : {"email":$("input#email").val()}, 
                type : "post", 
                dataType : "JSON",     
                success : function(json) { 
                    if(json.isExists) {
                        $(e.target).parent().find("span.error").html($(e.target).val()+" 은 이미 사용중입니다.").addClass("red").removeClass("blue");
						emailcheck = false;
					}
                    else { 
                        $(e.target).parent().find("span.error").html($(e.target).val()+" 은 사용가능합니다.").addClass("blue").removeClass("red");
                        emailcheck = true;
                    }
                },
                error: function(request, status, error) {
                    alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
                }
            });

        }
    });


    // 연락처2 (국번)
    $("input:text[name='tel2']").bind('keyup', e => { 

        const tel2_length = $(e.target).val().length;

        if(tel2_length == 4) {

            $("input:text[name='tel3']").focus();

        }
      
    });


    // 연락처3 
    $("input:text[name='tel3']").bind('blur', e => { 

        const tel2 = $("input:text[name='tel2']").val(); 

        const regExp_tel2 = new RegExp(/^[1-9][0-9]{3}$/); 

        const bool2 = regExp_tel2.test(tel2);

        const tel3 = $(e.target).val(); 

        const regExp_tel3 = new RegExp(/^\d{4}$/); 

        const bool3 = regExp_tel3.test(tel3);

        if(!bool2 || !bool3) {

            $(e.target).parent().find("span.error").html("올바른 연락처가 아닙니다.").addClass("red");

        }
        else {

            $(e.target).parent().find("span.error").hide();
        }
    });

    // 우편번호를 읽기전용(readonly)으로 만들기 (못쓰게 하기)
    $("input:text[name='postcode']").attr("readonly", true);

    // 주소를 읽기전용(readonly)으로 만들기 (못쓰게 하기)
    $("input:text[name='address']").attr("readonly", true);

    // 참고항목을 읽기전용(readonly)으로 만들기 (못쓰게 하기)
    $("input:text[name='extraaddress']").attr("readonly", true);


    // 우편번호 찾기 클릭시
    $("img#zipcodeSearch").click(function(e) {

        new daum.Postcode({
        
            oncomplete: function(data) {

                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
    
                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                let addr = ''; // 주소 변수
                let extraAddr = ''; // 참고항목 변수
    
                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }
    
                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if(data.userSelectedType === 'R'){
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if(extraAddr !== ''){
                        extraAddr = ' (' + extraAddr + ')';
                    }
                    // 조합된 참고항목을 해당 필드에 넣는다.
                    document.getElementById("extraAddress").value = extraAddr;
                
                } else {
                    document.getElementById("extraAddress").value = '';
                }
    
                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('postcode').value = data.zonecode;
                document.getElementById("address").value = addr;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("detailAddress").focus();
            }
    
        }).open(); 

    });


}); // end of $(()=>{}); ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~








// 가입하기 클릭시 goRegister() 함수
function goUpdate() {
	
    let bool = true;

    // 비밀번호를 입력했는지 검사
	const passwd = $("input#passwd").val(); 
	const passwd2 = $("input#passwdcheck").val(); 
	const regExp_passwd = new RegExp(/^.*(?=^.{8,20}$)(?=.*\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&*+=-]).*$/g);
	let regbool = regExp_passwd.test(passwd);
	
	if( passwd == "" && passwd2 == "" ) {
		$("span#pwdError").html("미입력시 비밀번호가 유지됩니다.").addClass("red");
	}
	else {
		if(!regbool) {
			$("span#pwdError").html("올바른 비밀번호가 아닙니다.").addClass("red");
			bool = false;
		}
		else {
			if(passwd != passwd2) {
				$("span#pwdError").html("비밀번호가 일치하지 않습니다.").addClass("red");
				bool = false;
			}
		}
	}
	

    // 성명을 입력했는지 검사   
	const name = $("input:text[name='name']").val(); 
	const nameReg = /^[가-힣]{2,6}$/;
	regbool = nameReg.test(name);
	if(!regbool) {
		$("input#name").parent().find("span.error").html("올바른 성명이 아닙니다.").addClass("red");
		bool = false;
	}

    // 이메일을 입력했는지 검사
	if( !emailcheck ) {
		$("span#emailError").html("올바른 이메일이 아닙니다.").addClass("red").removeClass("blue");
		bool = false;
	}

    // 연락처를 입력했는지 검사
	const tel2 = $("input:text[name='tel2']").val(); 
	const regExp_tel2 = new RegExp(/^[1-9][0-9]{3}$/); 
	const regbool2 = regExp_tel2.test(tel2);
	const tel3 = $("input:text[name='tel3']").val(); 
	const regExp_tel3 = new RegExp(/^\d{4}$/); 
	const regbool3 = regExp_tel3.test(tel3);
	if(!regbool2 || !regbool3) {
		$("input:text[name='tel3']").parent().find("span.error").html("올바른 연락처가 아닙니다.").addClass("red");
		bool = false;
	}
	else {
		$("input:text[name='tel3']").parent().find("span.error").html("").removeClass("red");
	}

    // 주소 입력했는지 검사
    if($("input#postcode").val().trim() == "" || $("input#address").val().trim() == "") {
        $("input#address").parent().find("span.error").html("주소를 입력하세요.").addClass("red");
        bool = false;
    }
	else {
		$("input#address").parent().find("span.error").html("").removeClass("red");
	}



    // 조건중 하나라도 만족하지 않는다면 함수를 빠져나간다.
    if(!bool) {
        return false;
    }

    const frm = document.updateInfoFrm

    frm.action = "updateInfo.ddg";
    frm.method = "post";
    frm.submit();

} //

// 취소하기 클릭시 리셋하는 함수
function goBack() {

    location.href="/fruitshop/login/login.ddg";

}// end of function goReset()---------------------


function goWithdrawal() {
	
	if(confirm("정말로 탈퇴 하시겠습니까?")) {
		
		const frm = document.updateInfoFrm;
					
		frm.action = "memberWithdrawal.ddg";
		frm.method = "post";
		frm.submit();
		
	}
}










