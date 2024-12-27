<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% 
	String ctxPath = request.getContextPath();
%>

<script type="text/javascript">

$(document).ready(function(){
	
	const modalOpenButton = document.getElementById('mainImgModalOpen');
	const modalCloseButton = document.getElementById('mainImgModalClose');
	const modal = document.getElementById('modalContainer');
	
	modalOpenButton.addEventListener('click', () => {
		modal.classList.remove('hidden');
	});

	modalCloseButton.addEventListener('click', () => {
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
	
	const reader = new FileReader();
	
		
	$("input[name='mainImg_file']").bind("change",function(e){
		
		const input_file = $(e.target).get(0);
		
		console.log(input_file.files);
		
		reader.readAsDataURL(input_file.files[0]);
		
		reader.onload = (e) => {
	        let img_src = reader.result;
	        
	        document.getElementById('uploadPreview').src = img_src;
	        
	    }
		
	})

	
});

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
	
	<table class="table" style="text-align: center;">
		<thead>
			<tr>
				<th>사진 이름</th>
				<th>사진 파일이름</th>
				<th>사진 미리보기</th>
			</tr>
		</thead>
		<tbody id="imgbody">
			<c:forEach items="${requestScope.imgList}" var="imgs" varStatus="index">
				<tr>
					<td>${imgs.imgname}</td>
					<td>${imgs.imgfilename}</td>
					<td><img class="" alt="" src="<%=ctxPath %>/images/index/${imgs.imgfilename}" width="100" height="100" style="border-radius: 10%"></td>
				</tr>
			</c:forEach>
		</tbody>
	</table> 
		<table class="table" id="button_posi">
		<tbody>
			<tr>
				<td><button class="btn btn-outline-success" id="mainImgModalOpen" >등록하기</button></td>
				<td><button class="btn btn-outline-danger" id="modalCloseButton">삭제하기</button></td>
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
	    				<td><input type="file" name="mainImg_file" size="8"></td>
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
	    				<td><button class="btn btn-outline-success" type="button" id="couponModalSubmit">이미지 등록</button></td>
	    				<td><button class="btn btn-outline-secondary" type="button" id="mainImgModalClose">나가기</button></td>
	    			</tr>
	    		</tbody>
	    	</table>
	    	
		    <input type="text" style="display:none;"/>
	    </div>
	  </div>
	</div>
	
</div>
