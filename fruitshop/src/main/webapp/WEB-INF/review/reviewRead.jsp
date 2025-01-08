<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    String ctxPath = request.getContextPath();
%>

<% 
    String currentShowPageNo = request.getParameter("currentShowPageNo");
%>   
 

<%-- Custom CSS --%>
<link rel="stylesheet" href="<%= request.getContextPath()%>/css/review/reviewRead.css">

<jsp:include page="../common/header.jsp"></jsp:include>

<script type="text/javascript">

$(document).ready(function () {
    $(document).on('click', '.editModalOpen', function () {
        const modalIndex = $(this).attr('data-index').trim(); // Get clicked button's unique index
        console.log(this)
        console.log("modalIndex : ", modalIndex);
        const modalId = `modalContainer_\${modalIndex}`; // Construct modal ID
        console.log("modalId : ", modalId);  // modalId 생성 시점 확인

        const modal = document.querySelector();
        console.log(modal);
        
        modal.classList.remove('hidden');
        
    });

    // Close modal when clicking close button
    $(document).on('click', '[id^="editModalClose_"]', function () {
        const modalIndex = this.id.split('_')[1];
        const modalId = `modalContainer_\${modalIndex}`;
        const modal = document.getElementById(modalId);

        if (modal) {
            modal.classList.add('hidden');
        } else {
            console.error(`Modal with ID ${modalId} not found.`);
        }
    });
    
    
    goCommentListView();
    
 // **** 제품후기 쓰기(로그인하여 실제 해당 제품을 구매했을 때만 딱 1번만 작성할 수 있는 것. 제품후기를 삭제했을 경우에는 다시 작성할 수 있는 것임.) ****// 
    $("button#btnCommentOK").click(function(){
    	    	
    	
       if(${empty sessionScope.loginuser}) {
            alert("제품사용 후기를 작성 하시려면 먼저 로그인 하셔야 합니다.");
            return; // 종료
       }       
               
         // >>> 보내야할 데이터를 선정하는 첫번째 방법 <<<
         <%--
            const queryString = {"fk_userid":"${sessionScope.loginuser.userid}"
                                ,"fk_pnum":"${requestScope.pvo.pnum}"
                                ,"contents":$("textarea[name='contents']").val()};
         --%>
         
         // >>> 보내야할 데이터를 선정하는 두번째 방법 <<<
         // jQuery 에서 사용하는 것으로써,
         // form태그의 선택자.serialize(); 을 해주면 form 태그내의 모든 값들을 name 속성값을 키값으로 만들어서 보내준다.
         const queryString = $("form[name='commentFrm']").serialize();
         /*
            queryString 은 아래와 같이 되어진다.
            {"contents":$("textarea[name='contents']").val(),
            "fk_userid":$("input[name='fk_userid']").val(),
             "fk_pnum":$("input[name='fk_pnum']").val() }
         */
         
         $.ajax({
        	 
         
            url:"<%= ctxPath%>/review/reviewCommentRegister.ddg",
             type:"post",
             data:queryString,
             dataType:"json",
             success:function(json){ 
                console.log(JSON.stringify(json));
                /*
                   {"n":1} 또는 {"n":-1} 또는 {"n":0}
                */
                
                if(json.n == 1) {
                    // 제품후기 등록(insert)이 성공했으므로 제품후기글을 새로이 보여줘야(select) 한다.
                    goCommentListView(); // 제품후기글을 보여주는 함수 호출하기 
                  }
                  else if(json.n == -1)  {
                // 동일한 제품에 대하여 동일한 회원이 제품후기를 2번 쓰려고 경우 unique 제약에 위배됨 
                alert("이미 후기를 작성하셨습니다.\n작성하시려면 기존의 제품후기를\n삭제하시고 다시 쓰세요.");
                   // swal("이미 후기를 작성하셨습니다.\n작성하시려면 기존의 제품후기를\n삭제하시고 다시 쓰세요.");
                }
                  else  {
                     // 제품후기 등록(insert)이 실패한 경우 
                     alert("제품후기 글쓰기가 실패했습니다.");
                  }
                
                $("textarea[name='comment_contents']").val("").focus();
             },
             error: function(request, status, error){
                alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
             }
            
            
         });// end of  $.ajax({})------------------------
  
   });// end of  $("button#btnCommentOK").click(function(){}----------------------------------
    
    
});


/* $(document).ready(function(){
	// const editModalOpen = document.getElementById('editModalOpen');
	const editModalClose = document.getElementById('editModalClose');
	const modal = document.getElementById('modalContainer'); 

	
    $(document).on('click', '[id^="editModalOpen"]', function() {
        // 클릭한 버튼의 id를 가져와서 확인
        const modalId = $(this).attr('id'); // 'editModalOpen0', 'editModalOpen1' 등의 값
        
        modal.classList.remove('hidden');
        
        // 모달에서 수정 버튼을 클릭한 항목에 맞는 작업 수행
        // 예를 들어, 클릭한 id로 특정 내용을 채운다던지 할 수 있음
    });

	editModalClose.addEventListener('click', () => {
	  modal.classList.add('hidden');
	});	
		
}); */

// 상품 상세보기
function productDetail(prod_no){
	
	const frm = document.readProductFrm;

	
	frm.prodNo.value = prod_no;
	frm.method = "get";
	frm.action = "<%=ctxPath%>/product/productDetail.ddg";
	
	frm.submit();
	
	
}

	// 댓글 보여주기 메소드
	function goCommentListView(){
	 	   
   		$.ajax({
            url:"<%= ctxPath%>/review/reviewComment.ddg",
            type:"get",
            data:{"review_no":"${requestScope.rvo.review_no}"},
            dataType:"json",
            success:function(json){

            	/*
            		[ {"contents:"옷이 너무너무 좋아요", "name":"김성곤","writeDate":"2025-01-07 09-40:50", "review_seq":1, "userid":"ksg6423"}
            		 ,{"contents:"가성비가 좋아요", "name":"엄정화","writeDate":"2025-01-08 09-40:50", "review_seq":2, "userid":"eomjh"}		
            		]
            		또는
            		[]
            	*/            	
            	let v_html = "";
            	
            	if(json.length > 0 ){
            		$.each(json, function(index, item){
            			
            			let writeuserid = item.userid;
                        let loginuserid = "${sessionScope.loginuser.userid}";            			
                        
                        v_html += "<div id='comment"+index+"'><span class=''>▶</span>&nbsp;"+item.comment_contents+"</div>"
                        		+ "<div class='customDisplay'>"+item.userid+"</div>"      
                        		+ "<div class='customDisplay'>"+item.comment_regidate+"</div>";
                        		
                        if( loginuserid == "") { 
                            // 로그인을 안한 경우 
                            v_html += "<div class='customDisplay spacediv'>&nbsp;</div>";
                        }
                        else if( loginuserid != "" && writeuserid != loginuserid ) { 
                            // 로그인을 했으나 후기글이 로그인한 사용자 쓴 글이 아니라 다른 사용자 쓴 후기글 이라면  
                            v_html += "<div class='customDisplay spacediv'>&nbsp;</div>";
                        }
                        else if( loginuserid != "" && writeuserid == loginuserid ) {
                            // 로그인을 했고 후기글이 로그인한 사용자 쓴 글 이라면
                            v_html += "<div class='customDisplay spacediv commentDel' onclick='delMyComment("+item.comment_no+")'>삭제</div>"; 
                            v_html += "<div class='customDisplay spacediv commentDel commentUpdate' onclick='updateMyReview("+index+","+item.comment_no+")'>수정</div>"; 
                        }
                        
                        
            		});
            		
            		
              	} // end of if(json.length > 0 ){             	
              	else{
              	
              		v_html += "<div>등록된 상품후기가 없습니다.</div>";	
              		
              	} // end of else ------------------
              	$("div#viewComments").html(v_html);
              		
            },
            error:function(request, status, error){
	            // alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);	            
	       	}
            
            
            
   		}); // end of $.ajax({
   	}


 




//특정 제품의 제품후기를 수정하는 함수
	function updateMyReview(index, comment_no) {
	   
	  const origin_elmt = $("div#comment"+index).html(); // 원래의 제품후기 엘리먼트를 알아오
	// alert(origin_elmt); 
	// <span class="markColor">▶</span>&nbsp;옷이 너무너무 좋아요~~ㅎㅎ 
	  
	// alert($("div#review"+index).text());
	// ▶ 옷이 너무너무 좋아요~~ㅎㅎ 
	
	   const comment_contents = $("div#comment"+index).text().substring(2);  // 원래의 제품후기 내용 
	// alert(review_contents);
	// 옷이 너무너무 좋아요~~ㅎㅎ 
	
	   $("div.commentUpdate").hide(); // "후기수정" 글자 감추기
	  
	// "후기수정" 을 위한 엘리먼트 만들기 
	   let v_html = "<textarea id='edit_textarea' style='font-size: 12pt; width: 40%; height: 50px;'>"+comment_contents+"</textarea>";
	   v_html += "<div style='display: inline-block; position: relative; top: -20px; left: 10px;'><button type='button' class='btn btn-sm btn-outline-secondary' id='btnReviewUpdate_OK'>수정완료</button></div>"; 
	   v_html += "<div style='display: inline-block; position: relative; top: -20px; left: 20px;'><button type='button' class='btn btn-sm btn-outline-secondary' id='btnReviewUpdate_NO'>수정취소</button></div>";
	   
	// 원래의 제품후기 엘리먼트에 위에서 만든 "후기수정" 을 위한 엘리먼트로 교체하기
	   $("div#comment"+index).html(v_html);
	     
	// 수정취소 버튼 클릭시
	   $(document).on("click", "button#btnReviewUpdate_NO", function(){
	      $("div#comment"+index).html(origin_elmt); // 원래의 제품후기 엘리먼트로 복원하기
	      $("div.commentUpdate").show();// "후기수정" 글자 보여주기
	   });
	
	// 수정완료 버튼 클릭시
	   $(document).on("click", "button#btnReviewUpdate_OK", function(){
	      
	      $.ajax({
	           url:"<%= ctxPath%>/review/reviewCommentEdit.ddg",
	           type:"post",
	           data:{"comment_no":comment_no
	                ,"comment_contents":$("textarea#edit_textarea").val()},
	           dataType:"json",
	           success:function(json){
	           console.log(JSON.stringify(json));
	           // {"n":1} 또는 {"n":0}
	           
	              if(json.n == 1) {
	                 goCommentListView(); // 특정 제품의 제품후기글들을 보여주는 함수 호출하기 
	              } 
	              else {
	                 alert("제품후기 수정이 실패했습니다.");
	                 goCommentListView(); // 특정 제품의 제품후기글들을 보여주는 함수 호출하기 
	              }
	           
	           },
	           error: function(request, status, error){
	              // alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
	           }
	        });
	      
	   });

}// end of function updateMyReview(index,review_seq) {}-------------------------------


// 댓글 삭제하기
function delMyComment(comment_no){

	
    if(confirm("정말로 댓글을 삭제하시겠습니까?")) {
          $.ajax({
             url:"<%= ctxPath%>/review/reviewCommentDelete.ddg",
             type:"post",
             data:{"comment_no":comment_no},
             dataType:"json",
             success:function(json){
             	
            	alert(json.n) 
            	 
                if(json.n == 1) {
                   alert("댓글 삭제가 성공하였습니다.");
                   goCommentListView(); // 특정 제품의 제품후기글들을 보여주는 함수 호출하기 
                } 
                else {
                   alert("댓글 삭제에 실패하였습니다.");
                   
                }
             
             },
             error: function(request, status, error){
                alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
             }
          });
       } 
    
 }// end of function delMyReview(review_seq){}-----------------------------





// 구매후기 글 수정하기 버튼 누르면
function reviewEdit(prod_no, review_no) {
	
	
	const frm = document.readProductFrm;
	frm.review_no.value = review_no;
	frm.prodNo.value = prod_no;
	frm.method = "get";
	frm.action = "<%=ctxPath%>/review/reviewEdit.ddg";
	
	const userid = '${requestScope.rvo.userid}';
	const loginuserid = '${sessionScope.loginuser.userid}';
	
	if(${empty sessionScope.loginuser.userid}) {
		alert("후기 수정은 로그인이 필요합니다.");
		location.href ="<%= ctxPath %>/login/login.ddg"
		return;
	}
	
	if (userid != loginuserid){
		alert("본인 글만 수정할 수 있습니다");
		return;
	}
	
	
	frm.submit();

	
}


// 구매후기 글 삭제하기
function reviewDelete() {
	
const frm = document.commentWriteFrm;
	
	frm.action = "<%=ctxPath%>/review/reviewDelete.ddg";
	frm.method = "post";
	
	const userid = '${requestScope.rvo.userid}';
	const loginuserid = '${sessionScope.loginuser.userid}';
	
	if(${empty sessionScope.loginuser.userid}) {
		alert("후기 삭제는 로그인이 필요합니다.");
		location.href ="<%= ctxPath %>/login/login.ddg"
		return;
	}
	
	if (userid != loginuserid){
		alert("본인 글만 삭제할 수 있습니다");
		return;
	}
	
	frm.review_no.value = "${requestScope.rvo.review_no}";
	
	if(!confirm("정말 삭제하시겠습니까?")){
		return;
	}
	
	frm.submit();
	
	setTimeout(() => {
	    location.href = "<%= ctxPath %>/review/reviewList.ddg";
	}, 100);
	
	
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

.hidden {
  display: none;
}


.modal-open {
  display: block !important;
}


li {margin-bottom: 10px;} 
   
   div#viewComments {width: 80%;
                     margin: 1% 0 0 20%; 
                     text-align: left;
                     max-height: 1000px;
                     overflow: auto;
                     /* border: solid 1px red; */
   }
   
   span.markColor {color: #ff0000; }
   
   div.customDisplay {display: inline-block;
                      margin: 1% 3% 0 0;
   }
                   
   div.spacediv {margin-bottom: 5%;}
   
   div.commentDel {font-size: 8pt;
                   font-style: italic;
                   cursor: pointer; }
   
   div.commentDel:hover {background-color: navy;
                         color: white;   }



</style>




<div class="container-fluid">

	<div>
	<h1 class="text-center" style="margin-top: 4%;">Community</h1>
	<div class="font-weight-lighter text-center my-3">우리함께 나누는 싱싱한 이야기</div>
	</div>
	<div class="mb-4"; style="font-size:14pt; font-weight:500;">
		<ul class="nav nav-pills navbar-light nav justify-content-center mt-4">
		  <li class="nav-item">
		    <a class="nav-link mr-5" href="<%= ctxPath%>/notice/noticeList.ddg" style="color: black;">공지사항</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link mx-5" href="<%= ctxPath%>/review/reviewList.ddg" style="color: black; border-bottom: solid black 2px;">구매후기</a>
		  </li>
		  <li class="nav-item">
		    <a class="nav-link mx-5" href="<%= ctxPath%>/qna/qnaList.ddg" style="color: black;">QnA</a>
		  </li>
		  <li class="nav-item ml-5">	
		    <a class="nav-link" href="<%= ctxPath%>/faq/faqList.ddg" style="color: black;" tabindex="-1" aria-disabled="true">자주하는 질문</a>
		  </li>
		</ul>		
	</div>
	
	
	
	<%-- <c:if test="${not empty requestScope.rvo}">--%>
		<form id="readProductForm" name="readProductFrm">
			<div id="productpage" class="justify-content-center" style="display:flex; height:10%; cursor:pointer;" onclick="productDetail('${rvo.prod_no}');" >			
				<div id = "productimg" class="mt-1 ml-5" style="display:inline-block; height:120px; width:120px">
					<div style=""><span style="line-height: 170px"><img src="<%= ctxPath%>/images/product/thumnail/${rvo.prod_thumnail}" style="width:100%; height: 100%;"/></span></div>			
				</div>
				<div style="text-align:center; width:25%;">
					<div style="font-size:20pt; margin-left:10%; margin-top:8%; ">${rvo.prod_name}</div>
				</div>								
			</div>
			<input type="text" name="review_no" style="display: none"/>
			<input type="text" name="prodNo" style="display: none"/>
		</form>
		


	<div class="container mt-5">
			<div class="table-responsive">
			    <!-- .table-responsive 반응형 테이블(테이블의 원래 크기를 보존해주기 위한 것으로써, 디바이스의 width가 작아지면 테이블 하단에 스크롤이 생김) -->			 
				<table class="table">
					<thead style="text-align: center;">
						<tr>
							<th width="33%;"></th>
							<th style="text-align: center;" width="33%">
								${rvo.review_title}
							</th>
							<td style="text-align: right; border-bottom: solid #DEE2E6 2px;" width="33%">
								작성일&nbsp;:&nbsp;${rvo.review_regidate}
							</td>
						</tr>
					</thead>
					<tbody>
						<tr style="border: solid #DEE2E6 2px;">
							<td colspan="3" height="400">
								<div class="container">
									${rvo.review_contents}
								</div>
							</td>
						</tr>
					</tbody>
				
				</table>				
				<%-- <button type="button" class="btn btn-outline-secondary" onclick="location.href='<%=ctxPath%>/notice/noticeList.ddg'" style="float: right; ">돌아가기</button>
			<c:if test="${sessionScope.loginuser.role == 2}">
				<button type="button" class="btn btn-outline-danger" onclick="deleteNotice()" style="float: right; margin-right:0.8%">글삭제</button>
				<button type="button" class="btn btn-outline-success" style="float: right; margin-right:0.8%" id="NoticeModalUpdateOpen">글수정</button>
				</c:if>
				<form name="noticeDetailForm">
					<input type="hidden" name="notice_no">				
			</form>--%>
				
			<!-- 페이지네이션 -->
			 
		</div>
	</div>
	
	
		<div style ="margin-left:20.5%;">
			<tr>
				<td class=""><button class="btn btn-outline-dark" onclick="location.href='<%=ctxPath%>/review/reviewList.ddg?searchType=&sizePerPage=10&searchWord=&currentShowPageNo=<%= currentShowPageNo%> '">목록</button></td>
				<td class=""><button class="btn btn-outline-dark" onclick="reviewEdit('${rvo.prod_no}', '${rvo.review_no }')">후기 수정</button></td>
				<td class=""><button class="btn btn-outline-dark" onclick="reviewDelete()">후기 삭제</button></td>
			</tr>						
		</div>	
		
		<hr style="width:69%; margin-left:15.5%;">



<div class="text-center">
	    <p class="h5 text-dark">${requestScope.rvo.prod_name} 댓글 목록</p><hr style="width:69%; margin-left:15.5%;">
	    
	    <div id="viewComments">
	    	<%-- 여기가 제품사용 후기 내용이 들어오는 곳이다. --%>
	    </div>
</div>
     
    <div class="">
        <div class="" style="width: 54%; margin-left:20%;">
    		<form name="commentFrm" style="width: 100%;">
    		    <textarea name="comment_contents" style="font-size: 12pt; width: 100%; height: 100px; resize:none;"></textarea>
    		    <input type="hidden" name="userid" value="${sessionScope.loginuser.userid}" />
    		    <input type="hidden" name="user_no" value="${sessionScope.loginuser.user_no}" />
   	            <input type="hidden" name="review_no" value="${requestScope.rvo.review_no}" />
    		</form>
	    </div>
	    <div class="" style="display: flex; margin-left:20%;">
	    	<button type="button" class="btn btn-outline-dark" id="btnCommentOK" style="height:40px;"><span>후기쓰기</span></button>
	    </div>
    </div>
							
	





<form name="commentWriteFrm">
	<input type="text" value="${sessionScope.loginuser.userid}" style="display:none;"/>
	<input type="text" name="review_no" style="display:none;"/>
</form>


<jsp:include page="../common/footer.jsp"></jsp:include>