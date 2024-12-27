package admin.admincontroller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import product.controller.ProductURL;
import product.domain.ProductVO;
import product.model.ProductDAO;
import product.model.ProductDAO_imple;

public class AdminProductController extends AbstractController {
	
	private ProductDAO prdao = new ProductDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 관리자일 경우에만 가능하다.
		
		
		
		
		//*** 페이징 처리한 모든 과일 목록 , 검색되어진 과일목록 보여주기 ***//
		
		String searchFruit = request.getParameter("searchFruit"); 			  // 과일 검색명
		String currentShowPageNo = request.getParameter("currentShowPageNo"); // 현재 페이지 번호
		
		
		if(searchFruit == null) {
			searchFruit = "";
		}
		
		if(currentShowPageNo == null) {
			currentShowPageNo = "1";
		}
		
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("searchFruit", searchFruit);
		paraMap.put("currentShowPageNo", currentShowPageNo);
		
		
		
		// 페이징 처리를 위해 검색이 있는 경우, 검색이 없는 경우에 대한 총페이지수 알아오기 //
		int totalPage = prdao.getTotalPage(paraMap);
		
		
		// === GET 방식이므로 사용자가 웹브라우저 주소창에서 currentShowPageNo 에 totalPage 값 보다 더 큰값을 입력하여 장난친 경우
		// === GET 방식이므로 사용자가 웹브라우저 주소창에서 currentShowPageNo 에 0 또는 음수를 입력하여 장난친 경우
		// === GET 방식이므로 사용자가 웹브라우저 주소창에서 currentShowPageNo 에 숫자가 아닌 문자열을 입력하여 장난친 경우 
		// 아래처럼 막아주도록 하겠다.
		
		
		try {
		      if(Integer.parseInt(currentShowPageNo) > totalPage || 
		         Integer.parseInt(currentShowPageNo) <= 0) {
		    	  currentShowPageNo = "1";
				  paraMap.put("currentShowPageNo", currentShowPageNo);
		      }
		      

		} catch(NumberFormatException e) {
			currentShowPageNo = "1";
			paraMap.put("currentShowPageNo", currentShowPageNo); 
		}
		
		
		// *** ===== 페이지바 만들기 시작 ===== *** //
		String pageBar = "";
		
		int blockSize = 10; // blockSize 는 블럭(토막)당 보여지는 페이지 번호의 개수이다.
		
		int loop = 1; // loop 는 1 부터 증가하여 1개 블럭을 이루는 페이지번호의 개수(지금은 5개)까지만 증가하는 용도이다.
		
		
		// *** ==== pageNo 구하는 공식 ==== *** //
		int pageNo  = ( (Integer.parseInt(currentShowPageNo) - 1)/blockSize ) * blockSize + 1; 
		// pageNo 는 페이지바에서 보여지는 첫번째 번호이다.
		
		
		pageBar += "<div class='pagination'>";
		
		// [맨처음]
		pageBar += "<a href='adminProduct.ddg?searchFruit="+searchFruit+"&currentShowPageNo=1'>&laquo;</a>"; 
		// [이전]
		
		pageBar += "<a href='adminProduct.ddg?searchFruit="+searchFruit+"&currentShowPageNo="+(pageNo-1)+"'>&lsaquo;</a>"; 
		
		
		while( !(loop > blockSize || pageNo > totalPage) ) {
			
			if(pageNo == Integer.parseInt(currentShowPageNo)) {
				pageBar += "<a class='active' href='#'>"+pageNo+"</a>";
			}
			else {
				pageBar += "<a href='adminProduct.ddg?searchFruit="+searchFruit+"&currentShowPageNo="+pageNo+"'>"+pageNo+"</a></li>"; 
			}
			
			loop++;   // 1 2 3 4 5 6 7 8 9 10
			pageNo++;
	
			
		} // end of while( !(loop > blockSize || pageNo > totalPage) 
		
		
		// [다음]
		pageBar += "<a href='adminProduct.ddg?searchFruit="+searchFruit+"&currentShowPageNo="+pageNo+"'>&rsaquo;</a>"; 

		
		// [맨마지막]
		pageBar += "<a href='adminProduct.ddg?searchFruit="+searchFruit+"&currentShowPageNo="+totalPage+"'>&raquo;</a>"; 
		
		
		pageBar += "</div>";
		
		// *** ===== 페이지바 만들기 끝 ===== *** //
		
		
		// *** ====== 현재 페이지를 돌아갈 페이지(goBackURL)로 주소 지정하기 ======= *** //
		String currentURL = ProductURL.getCurrentURL(request);
		// 검색하고 나서 특정 상품 클릭 하고 나서 다시 현재 그 페이지로 그대로 되돌아가길 위한 용도로 쓰임.

		
		try {

			List<ProductVO> prdList = prdao.prdListPaging(paraMap);
			request.setAttribute("prdList", prdList);	
			request.setAttribute("searchFruit", searchFruit);
			request.setAttribute("pageBar", pageBar);
			request.setAttribute("currentShowPageNo", currentShowPageNo);
			
			
			
			// 뷰단(admin_product_management.jsp)에서 "페이징 처리시 보여주는 순번 공식" 에서 사용하기 위해 검색이 있는 또는 검색이 없는 회원의 총개수 알아오기 시작 //
			int totalProductCount = prdao.getTotalProductCount(paraMap);
			
			request.setAttribute("totalProductCount", totalProductCount);
			// 검색이 있는 또는 검색이 없는 회원의 총개수 알아오기 끝 //

			
			request.setAttribute("currentURL", currentURL);	
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/admin_page/admin_product_management.jsp");
			
			
		} catch (SQLException e) {	// 쿼리문 오류 발생 시
			e.printStackTrace();
			super.setRedirect(true);	// redirect 시킴
			super.setViewPage(request.getContextPath()+"/error.ddg");
		}
		

	}

}
