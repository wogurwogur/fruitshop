<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../common/header.jsp"></jsp:include>
<link rel="stylesheet" href="<%= request.getContextPath()%>/css/index/index.css">


<%-- 상품 표시 AOS CSS / JS 시작 --%>
<link rel="stylesheet" href="https://unpkg.com/aos@2.3.1/dist/aos.css" >
<script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
<%-- 상품 표시 AOS CSS / JS 끝 --%>


<script type="text/javascript">

	
	// ---------------------------- 상품 관련 부분 시작 ---------------------------- //
	// 상품 클릭시 상품 정보 페이지로 넘어간다.
	function goDetail(prodNo) {	
		
		const frm = document.productDetail_frm;
		frm.prodNo.value = prodNo; // 상품 번호 값 데이터 가지고 URL 이동
		
		frm.action = `${pageContext.request.contextPath}/product/productDetail.ddg`;
		frm.method = "get"; 
		frm.submit();
		
	}//  end of function goDetail(prodNo)



	// 하트 모양 클릭 시 
	function wishToggle(e, prodNo, userNo) {
  
	
		if( ${not empty sessionScope.loginuser} ) {	// 로그인 한 경우에
	        $.ajax({
	              url: `${pageContext.request.contextPath}/mypage/wishInsert.ddg`,
	              type: "post",
	              data: {
	                 "prodNo": prodNo,
	                 "userNo": userNo
	              },
	              success: function(response) {
	                  if ($(e).hasClass("fa-regular")) { // 흰 하트
	                      $(e).removeClass("fa-regular").addClass("fa-solid"); // 하트 검정으로
	                      alert("관심상품에 상품을 추가했습니다.");
	                  } 
	                  else { // 이미 검정 하트라면
	                      $(e).removeClass("fa-solid").addClass("fa-regular"); // 흰 하트로
	                      alert("관심상품에서 상품을 제거했습니다.");
	                  }
	              },
	              error: function(request, status, error){
	                 alert("code: "+request.status+"\n"+"message: "+request.responseText+"\n"+"error: "+error);
	              }
	         });
		}
	    else{   // 로그인 안했을때 
	       
	       alert("로그인 후 이용가능합니다!");
	       location.href=`${pageContext.request.contextPath}/login/login.ddg`;
	    }
          
 	}// function wishToggle(e, prodNo, userNo) {}-----------------------------------------------------
 	// ---------------------------- 상품 관련 부분 끝 ---------------------------- //
 

</script>

<div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
	
	  <ol class="carousel-indicators">
	    <c:if test="${!empty requestScope.imgList}">
	    	<c:forEach items="${requestScope.imgList}" varStatus="status">
	    		<c:if test="${status.index == 0}">
	    			<li data-target="#carouselExampleIndicators" data-slide-to="${status.index}" class="active"></li>
	    		</c:if>
	    		
	    		<c:if test="${status.index > 0}">
	    			<li data-target="#carouselExampleIndicators" data-slide-to="${status.index}"></li>
	    		</c:if>
	    	</c:forEach>
	    </c:if>
	  </ol>
	  
	  <div class="carousel-inner">
	     <c:if test="${!empty requestScope.imgList}">
	     	<c:forEach var="imgvo" items="${requestScope.imgList}" varStatus="status">
	     		<c:if test="${status.index == 0}">
	     			<div class="carousel-item active">
	     				<a href="#" class="product"><img src="<%= request.getContextPath() %>/images/index/${imgvo.imgfilename}" class="d-block w-100" alt="..."></a>
	     				<div class="carousel-caption d-none d-md-block">
	     					<%-- <h5>${imgvo.imgname}</h5> --%>
	     				</div>
	     			</div>
	     		</c:if>
	     		
	     		<c:if test="${status.index > 0}">
	    			<div class="carousel-item">
	     				<a href="#" class="product"><img src="<%= request.getContextPath() %>/images/index/${imgvo.imgfilename}" class="d-block w-100" alt="..."></a>
	     				<div class="carousel-caption d-none d-md-block">
	     					<%-- <h5>${imgvo.imgname}</h5> --%>
	     				</div>
	     			</div>
	    		</c:if>
	     	</c:forEach>
	     </c:if>
	  </div>
	  
	  <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
	    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
	    <span class="sr-only">Previous</span>
	  </a>
	  <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
	    <span class="carousel-control-next-icon" aria-hidden="true"></span>
	    <span class="sr-only">Next</span>
	  </a>
</div>


<%--------------------------- 상품 관련 부분 시작 ---------------------------%>

<%-- 계절테마 메뉴 시작 --%>
<div style="width: 70%; margin: 0 auto;" class="my-5">
    <%-- <p style="margin-left: 14.5%;" class="h2 mb-5">계절테마</p>--%>
    <div id="categoty_season" class="h2 mb-5">계절 과일</div>
    <div style="display: flex;" data-aos="fade-up" data-aos-delay="0">
    	<c:forEach var="seasonInfo" items="${requestScope.seasonInfo}" varStatus="status">
    		<c:if test="${status.index < 4}">
		        <div class="mx-auto">
		        	<a href="<%= request.getContextPath() %>/product/productList.ddg?seasonNo=${seasonInfo.season_no}" class="product">
			            <img src="<%= request.getContextPath() %>/images/index/${seasonInfo.season_image}" style="width: 158px; height: 157px; border-radius: 50%;">
			            <span style="display: block;" class="text-center h4 mt-3 season">${seasonInfo.season_name}</span>
		            </a>
		        </div>
	        </c:if>
        </c:forEach>
    </div>
</div>
<%-- 계절테마 메뉴 끝 --%>

<%-- 신상품 메뉴 시작 --%>
<div style="width: 70%; margin:0 auto;" class="my-5">
	<div class="mb-5" style="display: flex;">
    	<div class="h2" style="font-family: 'Noto Sans KR', sans-serif; font-weight: 500;" >New Arrivals</div>
    	<div style="margin-left: auto;">
      		<a href="<%= request.getContextPath() %>/product/productList.ddg"><span class="btn btn-outline-secondary" style="font-family: 'Noto Sans KR', sans-serif;">상품보러가기&nbsp;&gt;</span></a>
      	</div>
	</div>
      <div style="display: flex;">
	
		  <c:forEach var="prdvo" items="${requestScope.prdList}" varStatus="status">
			  <c:if test="${status.index < 4}">
			        <div class="mx-auto" style="position: relative;" data-aos="fade-up" data-aos-delay="0">
			                <img src="<%= request.getContextPath() %>/images/product/thumnail/${prdvo.prod_thumnail}" style="width: 250px; height: 250px; cursor: pointer;" onclick="goDetail(${prdvo.prod_no})">
		
							<%-- 재고 수량이 0이라면, 이미지 상단에 품절 이미지 표시 시작 --%>
		                    <c:if test="${prdvo.prod_inventory == 0}">   
		                    	<img src="<%=request.getContextPath()%>/images/product/soldout.png" style="position: absolute; top: 0; left: 0; width: 37px; height: 20px; opacity: 1.0; cursor: pointer;" onclick="goDetail(${prdvo.prod_no})">
		                    </c:if>
		                    <%-- 재고 수량이 0이라면, 이미지 상단에 품절 이미지 표시 끝 --%>
		                    
			           	<div style="display: flex;">
			            	<div class="mt-3">
				            	<span class="h5 mt-3 product" onclick="goDetail(${prdvo.prod_no})">${prdvo.prod_name}</span>
				            </div>
				            <div class ="wishbutton">
			            		<c:set var="heartCheck" value="false"/> <%-- 하트 체크 여부 변수 --%>
										
		                          <c:if test="${not empty sessionScope.wishList}"> <%-- 세션에 저장된 wish 테이블 값이 있다면 --%>
		                          	<c:forEach var="wsvo" items="${sessionScope.wishList}">
				                        <c:if test="${wsvo.fk_user_no == sessionScope.loginuser.user_no && wsvo.fk_prod_no == prdvo.prod_no}"> <%-- wish 테이블 로그인 번호와 상품 번호를 세션 로그인 번호와 페이지에 뿌려진 상품번호와 대조한다. --%>
			                            	<span><i onclick="wishToggle(this, ${prdvo.prod_no}, ${sessionScope.loginuser.user_no})" class="fa-solid fa-heart fa-lg heart mt-3"></i></span>
			                            	<c:set var="heartCheck" value="true"/> <%-- 하트 체크 여부 체크로 변경 --%>
				           				</c:if>
		          					</c:forEach>
		          				</c:if>
		          						
		          				<c:if test="${!heartCheck}"> <%-- 하트 체크된 적 없으면 흰 하트로 표시 한다. --%>
						              <span><i onclick="wishToggle(this, ${prdvo.prod_no}, ${sessionScope.loginuser.user_no})" class="fa-regular fa-heart fa-lg heart mt-3"></i></span>
						        </c:if>
				            </div>
			            </div>
			            
			            <span style="display: block; font-family: 'Noto Sans KR', sans-serif;" class="mt-3">
			            	<fmt:formatNumber value="${prdvo.prod_price}" pattern="###,###" /> 원
			            </span>
			            
			        </div>
		      </c:if>	
	      </c:forEach>
	      
    </div>
</div>
<%-- 베스트셀러 메뉴 끝 --%>


<%-- 상품 상세페이지 이동을 위한 --%>
<form name="productDetail_frm">
	<input type="hidden" name="prodNo" />
</form>

<%--------------------------- 상품 관련 부분 끝 ---------------------------%>

<jsp:include page="../common/footer.jsp"></jsp:include>


<%-- 상품 관련 부분 --%>
<%-- AOS 초기화 --%>
<script>
  // AOS 초기화
  AOS.init({
    duration: 1000,  // 애니메이션 지속 시간
    once: false,     // 애니메이션을 한 번만 실행
    offset: 0        // 스크롤 위치에 따라 애니메이션 시작 지점 설정 */
  });
</script>
<%-- 상품 관련 부분 --%>

