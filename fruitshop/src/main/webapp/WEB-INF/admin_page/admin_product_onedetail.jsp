<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  


<jsp:include page="../common/header.jsp"></jsp:include>


<%-- Custom CSS --%>
<link rel="stylesheet" href="<%= request.getContextPath()%>/css/adminpage/product_onedetail.css">    


<script type="text/javascript">
	
	let total_fileSize = 0; // 첨부한 파일의 총량을 누적하는 용도
	let file_arr = []; // 첨부되어진 파일 정보를 담아둘 배열
	

	$(document).ready(function(){
		
		
		   // 처음 페이지 들어오면 에러 문구 숨기기 	
		   $("span.error").hide();
		   
			// 상품명에 포커스
			$("input#prod_name").focus();
		   
		   // "제품수량" 에 스피너 달아주기
		   $("input#spinnerPqty").spinner({
			   spin:function(event,ui){
				   
		            if(ui.value > 100) {
		               $(this).spinner("value", 100);
		               return false;
		            }
		            else if(ui.value < 1) {
		               $(this).spinner("value", 1);
		               return false;
		            }
		       }
		   }); // end of $("input#spinnerPqty").spinner
		   
		   
		   
		    // -------- 썸네일 이미지 및 상세이미지 파일선택을 선택하면 화면에 이미지를 미리 보여주기 시작 -------- //
			
		    // 썸네일 //
		    $(document).on("change", "input.thumnail_img_file", function(e){
		    	
		    	const input_file =  $(e.target).get(0);
		    	const fileReader = new FileReader();
				  
			    fileReader.readAsDataURL(input_file.files[0]);  // FileReader.readAsDataURL() --> 파일을 읽고, result속성에 파일을 나타내는 URL을 저장 시켜준다.
		    	
			    
			    // 첨부 파일의 파일 형식 제한
			    if( !(input_file.files[0].type == 'image/jpeg' || input_file.files[0].type == 'image/png') ) {
			    	alert("jpg 또는 png 파일만 업로드 가능합니다.");
			    	$("input.thumnail_img_file").val("") // input 값 초기화
			    	document.getElementById("previewthumnail").src = '';
			    	return;
			    	
			    }
			    
			    fileReader.onload = function() { // FileReader.onload --> 파일 읽기 완료 성공시에만 작동하도록 하는 것임.
			    	document.getElementById("previewthumnail").src = fileReader.result;
			    };
				
			 	// 첨부한 파일의 총량을 누적하는 용도
			 	total_fileSize += input_file.files[0].size;
			 	
			 	// 첨부 파일 정보 담는다.
			 	file_arr.push(input_file.files[0]);
			 
			 			    	
		    }); // end of $(document).on("change", "input.img_file", function(e)
		   
		 	
		    // 상세 이미지 //		
		    $(document).on("change", "input.prodDescript_img_file", function(e){
		    	
		    	const input_file =  $(e.target).get(0);
		    	const fileReader = new FileReader();
		    	
		    	fileReader.readAsDataURL(input_file.files[0]);  // FileReader.readAsDataURL() --> 파일을 읽고, result속성에 파일을 나타내는 URL을 저장 시켜준다.
		    	
			    // 첨부 파일의 파일 형식 제한
			    if( !(input_file.files[0].type == 'image/jpeg' || input_file.files[0].type == 'image/png') ) {
			    	alert("jpg 또는 png 파일만 업로드 가능합니다.");
			    	$("input.prodDescript_img_file").val("") // input 값 초기화
			    	document.getElementById("previewdescript").src = ''; 
			    	
			    }	  
		    	
			    fileReader.onload = function() { // FileReader.onload --> 파일 읽기 완료 성공시에만 작동하도록 하는 것임.
			    	document.getElementById("previewdescript").src = fileReader.result;
			    };
 
			 	// 첨부한 파일의 총량을 누적하는 용도
			    total_fileSize += input_file.files[0].size;
			 	
			 	// 첨부 파일 정보 담는다.
			    file_arr.push(input_file.files[0]);
				
			 	
		    }); // end of $(document).on("change", "input.img_file", function(e)	
		    		
		 	// -------- 썸네일 이미지 및 상세이미지 파일선택을 선택하면 화면에 이미지를 미리 보여주기 끝 -------- //

		 	
		 	
		 	
		 	
		 	// -------------------------- 제품 수정하기 -------------------------- //
		    
		 	$("input:button[id='btnUpdate']").click(function(){
		 				 		
		 		$("span.error").hide();	 	
		 		
		 		// ------------------ 유효성 검사 ------------------ //
		 		let prod_infoData_OK = true; // 유효성 여부 확인하는 용도
		 		
		 		
		 		// 상품명 확인
		 		const prod_name = $("input#prod_name").val().trim();
		 		if (prod_name == "") {
		 			$("input#prod_name").parent().find("span.error").show();
		 			prod_infoData_OK = false;
      				return false;
		 		}
		 		
		 		
		 		// 원가 확인
		 		const regExp_prod_cost = /^[0-9]+$/g;
				const prod_cost = regExp_prod_cost.test($("input#prod_cost").val());
				if(!prod_cost) {
					$("input#prod_cost").parent().find("span.error").show();
					prod_infoData_OK = false;
      				return false;
				}
				
				
				// 가격확인
				const regExp_prod_price = /^[0-9]+$/g;
				const prod_price = regExp_prod_price.test($("input#prod_price").val())
				
				if(!prod_price) {
					$("input#prod_price").parent().find("span.error").show();
					prod_infoData_OK = false;
      				return false;
				}
		 		
				
				// 재고확인
				const regExp_spinnerPqty = /^[0-9]+$/g;
				const spinnerVal = $("input#spinnerPqty").val();
				const prod_inventory = regExp_spinnerPqty.test($("input#spinnerPqty").val())
				
		 		if(!prod_inventory) {
		 			$("span#prod_inventory").show();
		 			prod_infoData_OK = false;
		 			return false;
		 		}
		 		else {
		 			if(spinnerVal > 100 || spinnerVal < 1) {
		 				alert("재고는 최소 1개 이상 최대 100개 이하까지 가능합니다.");
		 				prod_infoData_OK = false;
		 				return false;
		 			}	
		 		}
				
				
				// 계절값
				const fk_season_no = $("select#fk_season_no").val();
				if(fk_season_no == 0) {
					$("select#fk_season_no").parent().find("span.error").show();
					prod_infoData_OK = false;
					return false;
				}
				
				if (total_fileSize > 0) {
					// 상품이미지
					const prod_thumnail = $("input#prod_thumnail").val();
					if(prod_thumnail == "") {
						$("input#prod_thumnail").parent().find("span.error").show();
			 			prod_infoData_OK = false;
	      				return false;
					}
					
					
					// 상품 상세이미지
					const prod_descript = $("input#prod_descript").val();
					if(prod_descript == "") {
						$("input#prod_descript").parent().find("span.error").show();
			 			prod_infoData_OK = false;
	      				return false;
					}
				}
				
				// ------------------ 유효성 검사 끝 ------------------ //
							
		 		// form 태그 전송하기		
		 		if(prod_infoData_OK) {
		 			
		 			// $("form[name='prodUpdateFrm']").get(0) 폼 에 작성된 모든 데이터 보내기
		 			var formData = new FormData($("form[name='prodUpdateFrm']").get(0));
		 			
	 			
		 			if (total_fileSize >= 10*1024*1024) { // 첨부 파일의 크기 총합이 10MB 이상이라면
		 				alert("첨부 파일의 총합 크기는 최대 10MB 입니다.");
		 				return; // 종료
		 			}
		 			else { // 첨부한 파일의 총합의 크기가 10MB 미만 이라면, formData 속에 첨부파일 넣어주기
		 				file_arr.forEach(function(item, index){
		 					formData.append("attach"+index, item); // 첨부파일 추가하기
		 				});
		 			}
		 			
		 			
		 			if (total_fileSize > 0) {
			 			$.ajax({
			 				url: "${pageContext.request.contextPath}/admin/adminProductOneDetail.ddg",
			 				type: "post",
			 				data: formData,
		                    processData: false,  // 파일 전송시 설정 
		                    contentType: false,  // 파일 전송시 설정
		                    dataType: "json",
		                    success: function(json){
		                    	if(json.result == 1) {
		                         	location.href="${pageContext.request.contextPath}/admin/adminProduct.ddg"; 
		                       }
		                    },
			 				error: function(request, status, error){
	                      		alert("첨부된 파일의 크기의 총합이 10MB 를 초과하여 제품등록이 실패했습니다.");
	                 		}
			 			});
		 			} 
		 			
		 			else { // 이미지 파일 첨부가 없는 경우에
		 				$.ajax({
			 				url: "${pageContext.request.contextPath}/admin/adminProductNoImgUpdate.ddg",
			 				type: "post",
			 				data: formData,
		                    processData: false,  // 파일 전송시 설정 
		                    contentType: false,  // 파일 전송시 설정
		                    dataType: "json",
		                    success: function(json){
		                    	if(json.result == 1) {
		                         	location.href="${pageContext.request.contextPath}/admin/adminProduct.ddg"; 
		                       }
		                    },
			 				error: function(request, status, error){
	                      		alert("상품 수정에 실패하였습니다.");
	                 		}
			 			});
		 			}
		 			
		 		} // end of if(prod_infoData_OK)
		 		
		 	}); // end of $("input:button[id='btnUpdate']").click(function()
		 	
		 	// -------------------------- 제품 수정하기 끝 -------------------------- //
		 				   
	}); // $(document).ready(function()   
			
			
	function goReset() { // 취소 버튼 클릭

	    location.href="${pageContext.request.contextPath}/admin/adminProduct.ddg";

	}// end of function goReset()---------------------	



</script>
    
    
<div id="container">
	
	<div id="title">
		<span> 상품 정보 </span>
	</div>
	
	
	<form name="prodUpdateFrm" enctype="multipart/form-data">
		<div id="table">
			<table class="table table-borderless">
				<tbody>
					<tr>
						<td class="tdTitle">상품명</td>
						<td class="tdContents">
							<input type="text" name="prod_name" id="prod_name" maxlength="20" class="prod_infoData" value="${requestScope.prdvo.prod_name}"/>
							<span class="error">상품명은 필수입력 사항입니다.</span>
						</td>
					</tr>
					<tr>	
						<td class="tdTitle">원가</td>
						<td class="tdContents">
							<input type="text" name="prod_cost" id="prod_cost" class="prod_infoData" value="${requestScope.prdvo.prod_cost}" />
							<span class="error">원가는 필수 입력사항이며, 숫자로만 입력 가능합니다.</span>
						</td>
					</tr>
					<tr>
						<td class="tdTitle">가격</td>
						<td class="tdContents">
							<input type="text" name="prod_price" id="prod_price" class="prod_infoData" value="${requestScope.prdvo.prod_price}" />
							<span class="error">가격은 필수 입력사항이며, 숫자로만 입력 가능합니다.</span>
						</td>
					</tr>
					<tr>
						<td class="tdTitle">재고</td>
						<td lass="tdContents">
							<input name="prod_inventory" id="spinnerPqty" value="${requestScope.prdvo.prod_inventory}" class="prod_infoData" />
							<span id="prod_inventory" class="error">재고는 필수 입력사항이며, 숫자로만 입력 가능합니다.</span>
						</td>
					</tr>
					<tr>
						<td class="tdTitle">계절분류</td>
						<td class="tdContents">
							<select name="fk_season_no" class="prod_infoData" >
								<option value="">선택</option>	
								<c:forEach var="seasonvo" items="${requestScope.seasonInfo}">
									<option value="${seasonvo.season_no}">${seasonvo.season_name}</option>  
								</c:forEach>  
							</select>
							<span class="error">계절분류는 필수 입력사항입니다.</span>
						</td>
					</tr>
					
					<tr>
						<td class="tdTitle">판매여부</td>
						<td class="tdContents">
							<select name="prod_status" class="prod_infoData" >
								 <option value="">선택</option>
								 <option value="0">미판매</option> 
								 <option value="1">판매중</option> 
							</select>
							<span class="error">판매여부는 필수 입력사항입니다.</span>
						</td>
					</tr>
	
					<tr>
						<td class="tdTitle">상품이미지</td>
						<td class="tdContents">
							<input type="file" id="prod_thumnail" name="prod_thumnail" class="thumnail_img_file prod_infoData" accept='image/*' />
							<input type="hidden" name ="org_prod_thumnail" value="${requestScope.prdvo.prod_thumnail}" />
							<span class="error">상품 이미지는 필수 입력사항입니다.</span>
						</td>
					</tr>
					<tr>
		                <td class="tdTitle">이미지 미리보기</td>
		                <td class="tdContents">
	               			<img id="previewthumnail" width="300" height="300" src="<%=request.getContextPath()%>/images/product/thumnail/${requestScope.prdvo.prod_thumnail}" />
		                </td>
		          	</tr>
					<tr>
						<td class="tdTitle">상품상세이미지</td>
						<td class="tdContents"> 
							<input type="file" name="prod_descript" class="prodDescript_img_file prod_infoData" accept='image/*' />
							<input type="hidden" name ="org_prod_descript" value="${requestScope.prdvo.prod_descript}" />
							<span class="error">상품 상세이미지는 필수 입력사항입니다.</span>
						</td>
					</tr>
					<tr>
		                <td class="tdTitle">상세이미지 미리보기</td>
		                <td class="tdContents">
			            	<img id="previewdescript" width="300" height="300" src="<%=request.getContextPath()%>/images/product/description/${requestScope.prdvo.prod_descript}"/>
		                </td>
	          		</tr>
					
					<tr>
	                    <td colspan="2" class="text-center" style="padding: 30px 0 10px 0;">
	                       <input type="button" id="btnUpdate" class="btn btn-success mr-5" value="수정하기" />
	                       <input type="reset"  class="btn btn-danger" value="돌아가기" onclick="goReset()" />
	                    </td>
	                </tr>
					
				</tbody>
			</table>
		</div>
		
		<%-- 상품 수정 시 fomr-data로 다시 넘기기 위한 --%>
		<input type="hidden" name="prod_no" value="${requestScope.prod_no}" />
		
	</form>
</div>


<jsp:include page="../common/footer.jsp"></jsp:include>