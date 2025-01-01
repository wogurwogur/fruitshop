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

<form name="updateFrm" enctype="multipart/form-data">

<%-- div top nav end --%>

<div class="container">
	
	<table class="table" style="text-align: center; margin-top:2%;">
		<tbody>
			<tr>
				<td style="vertical-align: middle; text-align: left; margin-left:2%;" colspan="2" ><span>현재 이미지</span></td>
				<td><img src="<%=ctxPath%>/images/index/${requestScope.mvo.imgfilename}" width="300" height="200"></td>
			</tr>

			<tr>
				<td style="vertical-align: middle; text-align: left; margin-left:2%;"><span>수정할 이미지</span></td>
				<td style="vertical-align: middle;"><input type="file" name="updateimg"></td>
				<td><img width="300" height="200" id = "previewImg"></td>
			</tr>
			<tr>
				<td colspan="3">
					<button type="button" class="btn btn-outline-secondary" style="float: right; margin-right:5%;" onclick="history.back()">목록</button>
					<button type="button" class="btn btn-outline-success" style="float: right; margin-right:3%;">수정</button>
					<button type="button" class="btn btn-outline-danger" style="float: right; margin-right:3%;">삭제</button>
				</td>
			</tr>
		</tbody>
	</table>

</div>
</form>

