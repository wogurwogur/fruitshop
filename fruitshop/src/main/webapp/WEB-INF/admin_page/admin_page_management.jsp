<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% 
	String ctxPath = request.getContextPath();
%>

<script type="text/javascript">
let total_fileSize = 0;
const reader = new FileReader();

$(document).ready(function(){
	
	const mainImgModalOpen = document.getElementById('mainImgModalOpen');
	const mainImgModalClose = document.getElementById('mainImgModalClose');
	const modal = document.getElementById('modalContainer');
	
	mainImgModalOpen.addEventListener('click', () => {
		modal.classList.remove('hidden');
	});

	mainImgModalClose.addEventListener('click', () => {
		modal.classList.add('hidden');
	});
	
	const previewImg = $("#imgbody > tr > td:nth-child(3) > img ");
	
	previewImg.hover(function(e){
		
		$(e.target).removeClass("nomalimg");
		$(e.target).addClass("bigimg");
		
	},function(e){
		
		$(e.target).removeClass("bigimg");
		$(e.target).addClass("nomalimg");
	});
	
	
	
		
	$("input[name='mainImg_file']").bind("change",function(e){
		
		const input_file = $(e.target).get(0);
		
		console.log(input_file.files);
		
		reader.readAsDataURL(input_file.files[0]);
		
		reader.onload = (e) => {
	        let img_src = reader.result;
	        
	        document.getElementById('uploadPreview').src = img_src;
	        
	        total_fileSize = input_file.files[0].size;
	        
	        console.log(total_fileSize);
	        
	 }
		
	   // 제품등록하기

	});
	
    $("button#mainImgModalSubmit").click(function(){
 	   
 	   let is_infoData_OK = true;
 	   console.log("시작부");
 		   
 		/* 
            FormData 객체는 ajax 로 폼 전송을 가능하게 해주는 자바스크립트 객체이다.
            즉, FormData란 HTML5 의 <form> 태그를 대신 할 수 있는 자바스크립트 객체로서,
            자바스크립트 단에서 ajax 를 사용하여 폼 데이터를 다루는 객체라고 보면 된다. 
            FormData 객체가 필요하는 경우는 ajax로 파일을 업로드할 때 필요하다.
         */ 
         
         
 		/*
            === FormData 의 사용방법 2가지 ===
            <form id="myform">
               <input type="text" id="title"   name="title" />
               <input type="file" id="imgFile" name="imgFile" />
            </form>
            
            첫번째 방법, 폼에 작성된 전체 데이터 보내기   
            var formData = new FormData($("form#myform").get(0));  // 폼에 작성된 모든것       
            또는
            var formData = new FormData($("form#myform")[0]);  // 폼에 작성된 모든것
            // jQuery선택자.get(0) 은 jQuery 선택자인 jQuery Object 를 DOM(Document Object Model) element 로 바꿔주는 것이다. 
            // DOM element 로 바꿔주어야 순수한 javascript 문법과 명령어를 사용할 수 있게 된다. 
     
            또는
            var formData = new FormData(document.getElementById('myform'));  // 폼에 작성된 모든것
      	   
            두번째 방법, 폼에 작성된 것 중 필요한 것만 선택하여 데이터 보내기 
            var formData = new FormData();
            // formData.append("key", value값);  // "key" 가 name 값이 되어진다.
            formData.append("title", $("input#title").val());
            formData.append("imgFile", $("input#imgFile")[0].files[0]);
          */ 
            
 		   var formData = new FormData($("form[name='mainPageInputFrm']").get(0));  // $("form[name='prodInputFrm']").get(0) 폼 에 작성된 모든 데이터 보내기
 		   
 		    
    ///////////////////////////////////////////////////
 			// 첨부한 파일의 총량이 20MB 초과시 //
 			if(total_fileSize > 20*1024*1024) {
 				alert("ㅋㅋㅋ 첨부한 파일의 총합의 크기가 20MB를 넘어서 제품등록을 할 수 없습니다.!!");
 	            return; // 종료
 			}
    ///////////////////////////////////////////////////
    
    			$.ajax({
    		   <%-- url : "<%= ctxPath%>/shop/admin/productRegister.up", --%>
                 url : "${pageContext.request.contextPath}/admin/adminPageRegister.ddg",
                 type : "post",
                 data : formData,
                 processData:false,  // 파일 전송시 설정 
                 contentType:false,  // 파일 전송시 설정
                 dataType:"json",
                 success:function(json){
                    console.log("~~~ 확인용 : " + JSON.stringify(json));
                    // ~~~ 확인용 : {"result":1}
                    
                    if(json.result == 1) {
                       location.href="${pageContext.request.contextPath}/admin/pageManagement.ddg";
                    }
                    
                 },
                 error: function(request, status, error){
                    // alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
                    alert("첨부된 파일의 크기의 총합이 20MB 를 초과하여 제품등록이 실패했습니다.ㅜㅜ");
                 }
                 
    			});
    
    		/*
             processData 관련하여, 일반적으로 서버에 전달되는 데이터는 query string(쿼리 스트링)이라는 형태로 전달된다. 
             ex) http://localhost:9090/board/list.action?searchType=subject&searchWord=안녕
                 ? 다음에 나오는 searchType=subject&searchWord=안녕 이라는 것이 query string(쿼리 스트링) 이다. 
   
             data 파라미터로 전달된 데이터를 jQuery에서는 내부적으로 query string 으로 만든다. 
             하지만 파일 전송의 경우 내부적으로 query string 으로 만드는 작업을 하지 않아야 한다.
             이와 같이 내부적으로 query string 으로 만드는 작업을 하지 않도록 설정하는 것이 processData: false 이다.
        	*/
          
         /*
             contentType 은 default 값이 "application/x-www-form-urlencoded; charset=UTF-8" 인데, 
             "multipart/form-data" 로 전송이 되도록 하기 위해서는 false 로 해야 한다. 
             만약에 false 대신에 "multipart/form-data" 를 넣어보면 제대로 작동하지 않는다.
         */
    
 		   
 	   // end of if(is_infoData_OK)--------------------------------------------
 	   
 	   
    });// end of 제품등록하기------------------------------------------------

	
});

function mainImgDetail(imgno){
	
	const frm = document.imgFrm;
	
	frm.imgno.value = imgno;
	
	frm.action = "<%=ctxPath%>/admin/mainPageDetail.ddg";
	
	frm.submit();
	
}

</script>

<script src="<%=ctxPath %>/js/admin/admin_member_management.js"></script>
<style>



#modalOpenButton, #modalCloseButton {
  cursor: pointer;
}

#modalContainer {
  width: 100%;
  height: 100%;
  position: fixed;
  top: 0;
  left: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  background: rgba(0, 0, 0, 0.5);
}

#modalContent {
  position: absolute;
  background-color: #ffffff;
  width: 500px;
  height: 480px;
  padding: 15px;
  border-radius: 20%;
}

#modalContainer.hidden {
  display: none;
}
#modalContainer {
  width: 100%;
  height: 100%;
  position: fixed;
  top: 0;
  left: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  background: rgba(0, 0, 0, 0.5);
}

#modalContainer.hidden {
  display: none;
}

#uploadPreview{
	border: 0px;
}

div#admin_top_nav{
	display: flex;
	margin-top: 1%;
}

div#admin_top_nav_center{
	margin: 0 auto;
}


#button_posi{
	text-align: center;
}

#imgbody > tr > td{
	vertical-align: center;
}

.bigimg{
	position:relative;
	z-index: 10;
	transform: scale(4.0);
	transition: transform .5s;
}

.nomalimg{
	position:relative;
	z-index: 0;
	transform: scale(1.0);
	transition: transform .5s;
}

</style>

<%-- div top nav start --%>
<div class="container" id="admin_top_nav">

	<%-- dropdown div start --%>
	<div>

	</div>
	<%-- dropdown div end --%>
	
	
	<%-- center div start --%>
	<div id="admin_top_nav_center">
	</div>
	<%-- center div end --%>
		
		
	<%-- search div start --%>
	<div>
	</div>
	<%-- search div end --%>
	
</div>
<%-- div top nav end --%>

<div class="container">
<form name="imgFrm">
	<input type="text" name="imgno" style="display:none;">
	<input type="text" style="display:none;">
</form>
	
	<table class="table table-hover" style="text-align: center;">
		<thead>
			<tr>
				<th>사진 이름</th>
				<th>사진 파일이름</th>
				<th>사진 미리보기</th>
			</tr>
		</thead>
		<tbody id="imgbody">
			<c:forEach items="${requestScope.imgList}" var="imgs" varStatus="index">
				<tr style="cursor:pointer;" onclick="mainImgDetail('${imgs.imgno}')">
					<td>${imgs.imgname}</td>
					<td>${imgs.imgfilename}</td>
					<td><img class="" alt="" src="<%=ctxPath %>/images/index/${imgs.imgfilename}" width="100" height="100" style="border-radius: 10%"></td>
				</tr>
				
			</c:forEach>
			
		</tbody>
	</table> 
		<table style="width:100%;" id="button_posi">
		<tbody>
			<tr>
				<td colspan="2" style="float: right; margin-right:3%;"><button class="btn btn-outline-success" id="mainImgModalOpen" >등록하기</button></td>
			</tr>
		</tbody>
	</table>
	
	<div id="modalContainer" class="hidden">
	  <div id="modalContent">
	    <div class="container mt-5">
	    	<table class="table" style="text-align:center;">
	    		<thead>
	    			<tr>
	    				<th colspan="2">메인페이지 이미지 등록하기</th>
	    			</tr>
	    		</thead>
	    		<tbody>
	    			<tr>
	    				
	    				<td>사진파일</td>
	    				<td><form name="mainPageInputFrm" enctype="multipart/form-data"><input type="file" name="mainImg_file" size="8" accept='image/*' ></form></td>
	    				
	    			</tr>
	    			<tr>
	    				<td colspan="2">사진 미리보기
	    					<img  width="100" height="100"  id="uploadPreview"/>
	    				</td>
	    			</tr>
	    		</tbody>
	    	</table>
	    	<table class="table" style="text-align:center;">
	    		<tbody>
	    			<tr>
	    				<td><button class="btn btn-outline-success" type="button" id="mainImgModalSubmit" name="mainImgModalSubmit">이미지 등록</button></td>
	    				<td><button class="btn btn-outline-secondary" type="button" id="mainImgModalClose">나가기</button></td>
	    			</tr>
	    		</tbody>
	    	</table>
	    	
		    <input type="text" style="display:none;"/>
	    </div>
	  </div>
	</div>
	
</div>
