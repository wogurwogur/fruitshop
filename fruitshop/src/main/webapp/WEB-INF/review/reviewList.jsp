<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String ctxPath = request.getContextPath();
%>   
 

<%-- Custom CSS --%>
<link rel="stylesheet" href="<%= request.getContextPath()%>/css/review/reviewList.css">

<jsp:include page="../common/header.jsp"></jsp:include>






<script type="text/javascript">




$(document).ready(function(){
	
	
	const isClick = false;
		
	$("select[name='searchType']").val("${requestScope.searchType}");
	
	$("input:text[name='searchWord']").val("${requestScope.searchWord}");
	
	$("select[name='sizePerPage']").val("${requestScope.sizePerPage}");
	
	// **** select 태그에 대한 이벤트는 click 이 아니라 change 이다. **** // 
	$("select[name='sizePerPage']").bind("change", function(){
		const frm = document.review_search_frm;
		// frm.action = "memberList.up"; // form 태그에 action 이 명기되지 않았으면 현재보이는 URL 경로로 submit 되어진다.
		// frm.method = "get"; // form 태그에 method 를 명기하지 않으면 "get" 방식이다.
		frm.submit();

	});
		
	<%--
	$("table#reviewReadTbl td.etcRead").click( e=> {
		
		return false;
	)};
			
		const review_no = $(e.target).parent().children(".review_no").text();

		
		const frm = document.reviewRead_frm;
		frm.review_no.value = review_no;
		frm.currentShowPageNo.value = ${requestScope.currentShowPageNo};
		
		frm.action = "${pageContext.request.contextPath}/review/reviewRead.ddg";
		frm.method = "get"
		frm.submit(); 
		
	});
 		
		const review_no = $(e.target).parent().parent().children(".review_no").text();
		
		alert(review_no)
		
		const frm = document.reviewEtcRead_frm;
		frm.review_no.value = review_no;
		frm.currentShowPageNo.value = ${requestScope.currentShowPageNo};
		
		frm.action = "${pageContext.request.contextPath}/review/reviewRead.ddg";
		frm.method = "get"
		frm.submit();
		}

		
	});
--%>	


/* 	$("table#reviewReadTbl tr.reviewRead").click( e=> {
		
		event.stopPropagation();
		
		const review_no = $(e.target).parent().children(".review_no").text();

		
		const frm = document.reviewRead_frm;
		frm.review_no.value = review_no;
		frm.currentShowPageNo.value = ${requestScope.currentShowPageNo};
		
		frm.action = "${pageContext.request.contextPath}/review/reviewRead.ddg";
		frm.method = "get"
		frm.submit();
		
	
	});
		 */
	

	
	
	
});
	
function trclick1(review_no){
		
	const frm = document.tr_frm1;
	frm.review_noo.value = review_no;
	frm.currentShowPageNo.value = '${requestScope.currentShowPageNo}';
	
	frm.action = "${pageContext.request.contextPath}/review/reviewRead.ddg";
	frm.method = "get"
	frm.submit();
	
		
}	

function trclick2(review_no){
	
	const frm = document.tr_frm2;
	frm.review_noo.value = review_no;
	frm.currentShowPageNo.value = '${requestScope.currentShowPageNo}';
	
	frm.action = "${pageContext.request.contextPath}/review/reviewRead.ddg";
	frm.method = "get"
	frm.submit();
	
		
}	

function trclick3(review_no){
	
	const frm = document.tr_frm3;
	frm.review_noo.value = review_no;
	frm.currentShowPageNo.value = '${requestScope.currentShowPageNo}';
	
	frm.action = "${pageContext.request.contextPath}/review/reviewRead.ddg";
	frm.method = "get"
	frm.submit();
	
		
}	
	
	
	
	
function listSearch(){
		
		const searchType = $("select[name='searchType']").val();				
		
		if(searchType == ""){
			alert("검색 대상을 선택하세요 !! ");
			return; // goSearch()함수를 종료한다.
		}
		const frm = document.review_search_frm;
		// frm.action = "memberList.up"; // form 태그에 action 이 명기되지 않았으면 현재보이는 URL 경로로 submit 되어진다.
		// frm.method = "get"; // form 태그에 method 를 명기하지 않으면 "get" 방식이다.
		frm.submit();
		
	} // end of function goSearch(){ 


</script>

<style type="text/css">

div.pagination {
   border:solid 0px red;
   margin: 0 a uto;
}

div.pagination a {
  color: black;

  padding: 8px 16px;
  text-decoration: none;
  transition: background-color .3s;
  border-radius: 5px;
}

div.pagination a.active {
  /*background-color: #4CAF50;*/
  background-color: black;
  color: white;
  border-radius: 5px;
}

div.pagination a:hover:not(.active) {background-color: #ddd;}

</style>





<div class="container-fluid">

	<div>
		<div class="text-center" style="margin-top: 3.5%; font-size:30pt">Community</div>
	<div class="font-weight-lighter text-center my-3" style="font-size:13pt">우리함께 나누는 싱싱한 이야기</div>
	</div>
	<div style="font-size:14pt; font-weight:500;">
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
		    <a class="nav-link" href="#" style="color: black;" tabindex="-1" aria-disabled="true">자주하는 질문</a>
		  </li>
		</ul>
		
	</div>
		
<div>		
<div class="col-md-7 mx-auto my-4">
			<form name="review_search_frm">	
				<div id ="Listsearch" style="float:right;">
					<select name="searchType" class = "text-center" style="height:4%">
						<option value="">검색기준</option>
						<option value="review_title">제목</option>
						<option value="review_contents">내용</option>				
					</select>
					<input type="text" name="searchWord" style="height:4%"></input>
					<input type="text" style="display: none;" />
					<button type="button" class="mb-1 btn btn-outline-dark" style="height:4.1%" onclick="listSearch()">검색</button>				
				</div>
			</form>
			<div class="table-responsive">
			    <!-- .table-responsive 반응형 테이블(테이블의 원래 크기를 보존해주기 위한 것으로써, 디바이스의 width가 작아지면 테이블 하단에 스크롤이 생김) -->
			 
				<table class="table text-center" id="reviewReadTbl">
					<tr>
						
						<th>글 번호</th>
						<th class="w-55">제목</th>
						<th>작성자</th>
						<th>작성일자</th>
						<th>조회수</th>
					</tr>
					<%-- 글 리스트 --%>
			<c:if test="${not empty requestScope.reviewList}">
			<form name="tr_frm1">                
				<input type="text" name="currentShowPageNo" style="display:none" />
				<input type="text" style="display:none"  />			
				<c:forEach var="brevvo" items="${requestScope.brevList}" varStatus="status">
					<c:if test="${brevvo.review_viewcount > 0}">
						<tr class ="reviewRead" onclick="trclick1('${brevvo.review_no}')">											
							<td class="review_no" style="border-top: none">${brevvo.review_no}</td>	 				
							<%-- <td><a href="#"><span class="text-body font-weight-bold">[${revvo.prod_name}]</span></a>--%>						
							<td style="border-top: none">
							<input type="hidden" name="review_noo"/>	
								<span class="text-danger font-weight-bold">[이달 가장 많은 조회수]</span>&nbsp;&nbsp;
								<span style="font-weight:bold;">[${brevvo.prod_name}]</span>
								&nbsp;${brevvo.review_title}
								<c:if test="${brevvo.comment_count ne '0'}"><span class="text-danger">[${brevvo.comment_count}]</span></c:if>							
							</td>						
							<td style="border-top: none">${brevvo.userid}</td>
							<td style="border-top: none">${brevvo.review_regidate}</td>
							<td style="border-top: none">${brevvo.review_viewcount}</td>
						</tr>
					</c:if>						
				</c:forEach>
				
			</form>	
			
				
			<form name="tr_frm2">                
				<input type="text" name="currentShowPageNo" style="display:none" />
				<input type="text" style="display:none"  />
				<c:forEach var="crevvo" items="${requestScope.crevList}" varStatus="status">
					<c:if test="${crevvo.comment_count > 0}">
						<tr class ="reviewRead" onclick="trclick2('${crevvo.review_no}')">
							<td class="review_no" style="border-top: none">${crevvo.review_no}</td>	 				
							<%-- <td><a href="#"><span class="text-body font-weight-bold">[${revvo.prod_name}]</span></a>--%>						
							<td style="border-top: none">
							<input type="hidden" name="review_noo"/>
								<span class="text-danger font-weight-bold">[이달 가장 많은 댓글]</span>&nbsp;&nbsp;
								<span style="font-weight:bold;">[${crevvo.prod_name}]</span>
								&nbsp;${crevvo.review_title}
								<c:if test="${crevvo.comment_count ne '0'}"><span class="text-danger">[${crevvo.comment_count}]</span></c:if>							
							</td>						
							<td style="border-top: none">${crevvo.userid}</td>
							<td style="border-top: none">${crevvo.review_regidate}</td>
							<td style="border-top: none">${crevvo.review_viewcount}</td>
						</tr>
					</c:if>
				</c:forEach>
			</form>
					
				
			<form name="tr_frm3">                
				<input type="text" name="currentShowPageNo" style="display:none" />
				<input type="text" style="display:none"  />				
				<c:forEach var="revvo" items="${requestScope.reviewList}" varStatus="status">						
					<tr class="reviewRead" onclick="trclick3('${revvo.review_no}')">										
						<td class="review_no" style="border-top: none">${revvo.review_no}</td>	 				
						<td style="border-top: none">
						
						<span style="font-weight:bold;">[${revvo.prod_name}]</span>
							${revvo.review_title}
							&nbsp;<c:if test="${revvo.comment_count ne '0'}"><span class="text-danger">[${revvo.comment_count}]</span></c:if>							
						</td>						
						<td style="border-top: none">${revvo.userid}</td>
						<td style="border-top: none">${revvo.review_regidate}</td>
						<td style="border-top: none">${revvo.review_viewcount}</td>
					</tr>									
				</c:forEach>
				<input type="hidden" name="review_noo"/>
			</form>
				
			</c:if>
						
			
			
			<c:if test="${empty requestScope.reviewList }">
	      		<tr>
	                <td colspan="6">데이터가 존재하지 않습니다.</td>
	            </tr>
      		</c:if>
      		
      		
      			
      																	

			</table>
			<a href="<%= ctxPath%>/review/reviewWrite.ddg"><button type="button" class="btn btn-outline-dark float-right" style="height:4.1%">글쓰기</button></a>
		
								
			<!-- 페이지네이션 -->
			 <div style="margin-top: 5%; display: flex; ">
      			<div class='pagination' style="margin: 0 auto;">
         			${requestScope.pageBar}
      			</div>
      		</div>
		</div>
	</div>
</div>
</div>



<form name = "reviewEtcRead_frm">
	<input type="text" name="currentShowPageNo" style="display:none;"/>
	<input type="hidden" name="review_no" />
	<input type="text" style="display:none" />
</form>

<form name = "reviewRead_frm">
	<input type="text" name="currentShowPageNo" style="display:none;"/>
	<input type="hidden" name="review_no" />
	<input type="text" style="display:none" />
</form>

<jsp:include page="../common/footer.jsp"></jsp:include>
