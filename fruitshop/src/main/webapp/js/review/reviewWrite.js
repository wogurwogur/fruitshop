

$(document).ready(()=> {
	
	
	
	
	
	
});



// 글쓰기 등록버튼 누르면 DB에 글 넣어주는 함수
function rvRegister(){
	
	const frm = document.reviewWriteFrm;
	    frm.action = "reviewWrite.ddg";
	    frm.method = "post";
	    frm.submit();
	
	
};
