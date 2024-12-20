/**
 * 
 */



$(()=>{
	
	$("input:text[name='ship_name']").focus();


    // 배송지명
    $("input:text[name='ship_name']").bind('blur', e => { 

        const ship_name = $(e.target).val(); 

        const ship_nameReg = /^[가-힣a-zA-Z0-9]{1,10}$/; 

        const bool = ship_nameReg.test(ship_name);
    
        if(!bool) { 

            $(e.target).parent().find("span.error").html("올바른 배송지명이 아닙니다.").addClass("red");
        }
        else { 
            $(e.target).parent().find("span.error").html("").removeClass("red");
        }
      
    });
	
	// 받는사람
	    $("input:text[name='ship_receiver']").bind('blur', e => { 

	        const ship_receiver = $(e.target).val(); 

	        const ship_receiverReg = /^[가-힣]{2,6}$/; 

	        const bool = ship_receiverReg.test(ship_receiver);
	    
	        if(!bool) { 

	            $(e.target).parent().find("span.error").html("올바른 성명이 아닙니다.").addClass("red");
	        }
	        else { 
	            $(e.target).parent().find("span.error").html("").removeClass("red");
	        }
	      
	    });
	

    // 연락처2 (국번)
    $("input:text[name='ship_receivertel2']").bind('keyup', e => { 

        const ship_receivertel2_length = $(e.target).val().length;

        if(ship_receivertel2_length == 4) {

            $("input:text[ship_name='ship_receivertel3']").focus();

        }
      
    });


    // 연락처3 
    $("input:text[name='ship_receivertel3']").bind('blur', e => { 

        const ship_receivertel2 = $("input:text[name='ship_tel2']").val(); 

        const regExp_ship_receivertel2 = new RegExp(/^[1-9][0-9]{3}$/); 

        const bool2 = regExp_ship_receivertel2.test(ship_receivertel2);

        const ship_receivertel3 = $(e.target).val(); 

        const regExp_ship_receivertel3 = new RegExp(/^\d{4}$/); 

        const bool3 = regExp_ship_receivertel3.test(ship_receivertel3);

        if(!bool2 || !bool3) {

            $(e.target).parent().find("span.error").html("올바른 연락처가 아닙니다.").addClass("red");

        }
        else {

            $(e.target).parent().find("span.error").hide();
        }
    });

    // 우편번호를 읽기전용(readonly)으로 만들기 (못쓰게 하기)
    $("input:text[name='ship_postcode']").attr("readonly", true);

    // 주소를 읽기전용(readonly)으로 만들기 (못쓰게 하기)
    $("input:text[name='ship_address']").attr("readonly", true);

    // 참고항목을 읽기전용(readonly)으로 만들기 (못쓰게 하기)
    $("input:text[name='ship_extraadress']").attr("readonly", true);


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
                    document.getElementById("ship_extraaddress").value = extraAddr;
                
                } else {
                    document.getElementById("ship_extraaddress").value = '';
                }
    
                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('ship_postcode').value = data.zonecode;
                document.getElementById("ship_address").value = addr;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("ship_detailaddress").focus();
            }
    
        }).open(); 

    });

   


}); // end of $(()=>{}); ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~








// 추가하기 클릭시 goAdd() 함수
function goAdd() {
	
    let bool = true;
	
    // 배송지명을 입력했는지 검사   
	const ship_name = $("input:text[name='ship_name']").val(); 
	const ship_nameReg = /^[가-힣a-zA-Z0-9]{1,10}$/;
	let regbool = ship_nameReg.test(ship_name);
	if(!regbool) {
		$("input#ship_name").parent().find("span.error").html("올바른 배송지명이 아닙니다.").addClass("red");
		bool = false;
	}

	
	
	// 받는사람을 입력했는지 검사   
	const ship_receiver = $("input:text[name='ship_receiver']").val(); 
	const ship_receiverReg = /^[가-힣]{2,6}$/;
	regbool = ship_receiverReg.test(ship_receiver);
	if(!regbool) {
		$("input#ship_name").parent().find("span.error").html("올바른 성명이 아닙니다.").addClass("red");
		bool = false;
	}
	
	

    // 연락처를 입력했는지 검사
	const ship_receivertel2 = $("input:text[name='ship_receivertel2']").val(); 
	const regExp_ship_receivertel2 = new RegExp(/^[1-9][0-9]{3}$/); 
	const regbool2 = regExp_ship_receivertel2.test(ship_receivertel2);
	const ship_receivertel3 = $("input:text[name='ship_receivertel3']").val(); 
	const regExp_ship_receivertel3 = new RegExp(/^\d{4}$/); 
	const regbool3 = regExp_ship_receivertel3.test(ship_receivertel3);
	if(!regbool2 || !regbool3) {
		$("input:text[name='ship_receivertel3']").parent().find("span.error").html("올바른 연락처가 아닙니다.").addClass("red");
		bool = false;
	}
	else {
		$("input:text[name='ship_receivertel3']").parent().find("span.error").html("").removeClass("red");
	}

    // 주소 입력했는지 검사
    if($("input#ship_postcode").val().trim() == "" || $("input#ship_address").val().trim() == "") {
        $("input#ship_address").parent().find("span.error").html("주소를 입력하세요.").addClass("red");
        bool = false;
    }
	else {
		$("input#ship_address").parent().find("span.error").html("").removeClass("red");
	}



    // 조건중 하나라도 만족하지 않는다면 함수를 빠져나간다.
    if(!bool) {
        return false;
    }

    const frm = document.addFrm

    frm.action = "shipAdd.ddg";
    frm.method = "post";
    frm.submit();

} //

// 취소하기 클릭시 리셋하는 함수
function goBack() {

    location.href="/fruitshop/mypage/shipManagement.ddg";

}// end of function goReset()---------------------












