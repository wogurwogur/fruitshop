<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" href="<%= request.getContextPath()%>/css/mypage/recent_Viewproduct.css">


<jsp:include page="../common/header.jsp"></jsp:include>

<jsp:include page="mypage_list.jsp"></jsp:include>

<script type="text/javascript">


$(document).ready(function(){
	
	
    let html = ``;
    
    $("div.info").hide();
    
    const arr_product = JSON.parse(sessionStorage.getItem("arr_product"));
    
    console.log(arr_product.length);
    
    if (arr_product.length === 0) {  // 최근본상품이 없다면
    
    	$("div.info").hide();
        html = `<p style="font-size: 14pt; font-weight: 400; font-family: 'Noto Sans KR', sans-serif; text-align: center; padding: 15%; border: 1px solid #ccc; ">최근본 상품 내역이 없습니다.</p>`;
    
    }
    else { // 최근 본 상품이 있다면
    	
    	$("div.info").show();
        
	    arr_product.forEach(function(item, index) {
	    	
	    	const price = new Intl.NumberFormat('en').format(item.price);
	    	// console.log(price);
	    	
	        html += `
	        
	        <div class="recent_product" style="display: flex; align-items: center; padding: 3%; border-bottom: 1px solid #ccc; font-weight: 400; ">
	        
	        <%-- 상품 번호 숨기기 --%>
            <input type="hidden" name="prodno" value="\${item.prod_no}" />
	        
	            <%-- 상품 번호 --%>
	            <div class="number" style="flex: 0.4; text-align: center; cursor:pointer;">
	                <p style="font-size: 14pt; font-family: 'Noto Sans KR', sans-serif;">\${item.prod_no}</p>
	            </div>
	
	            <%-- 상품 이미지 --%>
	            <div class="thumnail" style="flex: 1.8; text-align: center; cursor:pointer;">
	            	<img src="<%= request.getContextPath() %>/images/product/thumnail/\${item.thumbnail}" style="width: 70%; height: auto; margin-left:3%;">
	            </div>
	
	            <%-- 상품 이름 --%>
	            <div class="name" style="flex: 2; text-align: center; cursor:pointer;">
	                <p style="font-size: 14pt; font-family: 'Noto Sans KR', sans-serif;">\${item.name}</p>
	            </div>
	
	            <%-- 상품 가격 --%>
	            <div class="price" style="flex: 1.8; text-align: center; cursor:pointer;">
	            	<p style="font-size: 14pt; font-family: 'Noto Sans KR', sans-serif;">\${price}원</p>
	            </div>
	
	            <%-- 삭제 버튼 --%>
	            <div style="flex: 0.4; text-align: center;">
	                <form method="post" action="<%= request.getContextPath() %>/mypage/recent_Viewproduct.ddg" onsubmit="return confirm('정말 삭제하시겠습니까?');">
	                    <button class="delete-btn" style="background-color: white; color: black; font-size: 20pt; border: solid 1px white; color: gray; margin-right:20%;">X</button>
	                </form>
	            </div>
	        </div>`;
	    });
    	
    }

    $("div.recent_view").html(html);
    
    // 상품 디테일로 가는 함수
    function goProductDetail(e) {
    	
    	const prod_no = $(e.target).parents(".recent_product").find("input[name='prodno']").val(); // 상품번호
		
	    location.href = "${pageContext.request.contextPath}" + "/product/productDetail.ddg?prodNo=" + prod_no;
    }

    // 썸네일 클릭
    $("div.number").click(function (prod_no) {
    	goProductDetail(prod_no);
    });
    
    // 썸네일 클릭
    $("div.thumnail").click(function (prod_no) {
    	goProductDetail(prod_no);
    });

    // 상품명 클릭
    $("div.name").click(function (prod_no) {
    	goProductDetail(prod_no); 
    });

    // 가격 클릭
    $("div.price").click(function (prod_no) {
    	goProductDetail(prod_no);
    });
    
    
 	// X 버튼 눌렀을때 그 상품을 삭제
    $(".delete-btn").click(function () {
        const product = $(this).closest(".recent_product");
        const index = product.data("index");

        // 배열에서 해당 인덱스 삭제
        arr_product.splice(index, 1);

        // 세션스토리지에 담아준다.
        sessionStorage.setItem("arr_product", JSON.stringify(arr_product));

        
        product.remove(); // 상품 삭제
        
        alert("상품을 삭제했습니다.");
        location.href="javascript:history.go(0)";
        
        if (arr_product.length === 0) {	// 상품이 없다면
        	$("div.info").hide();
            $("div.recent_view").html(`<p style="font-size: 14pt; font-family: 'Noto Sans KR', sans-serif; text-align: center; padding: 15%; border: 1px solid #ccc;">최근본 상품 내역이 없습니다.</p>`);
        }
        
    });
 	
 	
 	if(arr_product.length > 5) {  // 최근본 상품안에 상품이 5개가 넘는다면 
 		const product = $(this).closest(".recent_product");
        const index = product.data("index");

        arr_product.splice(index, 1);

        // 세션스토리지에 담아준다.
        sessionStorage.setItem("arr_product", JSON.stringify(arr_product));

        product.remove(); // 상품 삭제
        
        location.href="javascript:history.go(0)";
 	}
 	
 	
 
 	
    
});// end of $(document).ready(function(){}-------------------------------------------------------------


</script>



<div class="container" style="margin-top: 1%; margin-bottom: 4%;">
    <div class="titleArea" >
        <h3>최근본상품</h3>
    </div>

    <div class="info" style="display: flex; font-family: 'Noto Sans KR', sans-serif; font-weight: bold; text-align: center; border-bottom: 1px solid #ccc; padding-bottom: 3%;">
        <div style="flex: 0.7;">상품번호</div>
        <div style="flex: 1;">상품사진</div>
        <div style="flex: 2;">상품명</div>
        <div style="flex: 1;">판매가</div>
        <div style="flex: 0.7;">선택</div>
    </div>

        <div class="recent_view"></div>
</div>




<jsp:include page="../common/footer.jsp"></jsp:include>

