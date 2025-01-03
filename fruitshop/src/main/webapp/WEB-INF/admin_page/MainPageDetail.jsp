<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="admin.model.AdminDAO, admin.model.AdminDAO_imple" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<% 
	String ctxPath = request.getContextPath();
%>

<script src="<%=ctxPath %>/js/admin/admin_member_detail.js"></script>
<script type="text/javascript">
const reader = new FileReader();
	
$(document).ready(function(){
	$("input[name='updateimg']").bind("change",function(e){
		
		const input_file = $(e.target).get(0);
		
		console.log(input_file.files);
		
		reader.readAsDataURL(input_file.files[0]);
		
		reader.onload = (e) => {
	        let img_src = reader.result;
	        
	        document.getElementById('previewImg').src = img_src;
	        
	        total_fileSize = input_file.files[0].size;
	        
	        console.log(total_fileSize);
	        
		}
	});
});

function mainPageDel(imgno, imgfilename){
	
	if(!confirm("정말 삭제하시겠습니까?")){
		return;
	}
	
	const frm = document.mainPageDetailFrm;
	
	frm.imgno.value = imgno;
	frm.imgfilename.value = imgfilename;
	
	frm.action = "<%=ctxPath%>/admin/mainPageDelete.ddg";
	
	frm.method = "post";
	
	frm.submit();
	
}

</script>
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

#couponAllReceiptOpen{
	height: 36px;
}

#modalContainer.hidden {
  display: none;
}

div#admin_top_nav{
	display: flex;
	margin-top: 3%;
	margin-bottom: 0.5%;
}

div#admin_top_nav_center{
	margin: 0 1% 0 auto;
}

button#couponAllReceipt{
	vertical-align: middle;
	border: 0px;
}

select#searchType{
	height: 36px;
	vertical-align: middle;
}

#searchTypeWord{
	vertical-align: middle;
	height: 36px;
	padding: 0px;
}
#searchButton{
	vertical-align: middle;
	height: 36px;
	width: 35px;
	border: 0px;
}
#admin_top_nav_right{
	margin-right: 1%;
}

   div#pageBar {
      border: solid 0px red;
      width: 80%;
      margin: 3% auto 0 auto;
      display: flex;
   }
   
   div#pageBar > nav {
      margin: auto;
   }

/* search end  */
</style>

<%-- div top nav start --%>

<form name="mainPageDetailFrm">

<input type="text" style="display:none;" name="imgno" />
<input type="text" style="display:none;" name="imgfilename" />

<%-- div top nav end --%>

<div class="container">
	
	<table class="table" style="text-align: center; margin-top:2%;">
		<thead>
			<tr>
				<th colspan="2" style="text-align: center;">순번</th>
				<th>현재 이미지</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td style="vertical-align: middle; text-align: center; margin-left:2%;" colspan="2" >${requestScope.imgcount}</td>
				<td><img src="<%=ctxPath%>/images/index/${requestScope.mvo.imgfilename}" width="300" height="200"></td>
			</tr>

			<tr>
				<td colspan="3">

					<button type="button" class="btn btn-outline-secondary" style="float: right; margin-right:5%;" onclick="history.back()">목록</button>
					<button type="button" class="btn btn-outline-danger" style="float: right; margin-right:3%;" onclick="mainPageDel('${requestScope.mvo.imgno}','${requestScope.mvo.imgfilename}')">삭제</button>
				</td>
				
			</tr>
		</tbody>
	</table>
				
</div>
</form>

