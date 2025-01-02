package product.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import mypage.wish.domain.WishVO;
import product.domain.ProductVO;
import product.model.ProductDAO;
import product.model.ProductDAO_imple;

public class ProductListController extends AbstractController {
	
	private ProductDAO prdao = new ProductDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		//*** 페이징 처리한 모든 과일 목록 , 검색되어진 과일목록 또는 계절 카테고리 클릭 시 과일 목록 보여주기 ***//
	
		String searchFruit = request.getParameter("searchFruit"); 			  // 과일 검색명
		String currentShowPageNo = request.getParameter("currentShowPageNo"); // 현재 페이지 번호
		String seasonNo = request.getParameter("seasonNo"); // 계절 카테고리 클릭 시 계절 번호 1.봄 2.여름 3.가을 4.겨울
		
		
		if(searchFruit == null) {
			searchFruit = "";
		}
			
		if(currentShowPageNo == null) {
			currentShowPageNo = "1";
		}
		
		if(seasonNo == null || 
		  (!"1".equals(seasonNo) &&  
	       !"2".equals(seasonNo) && 
		   !"3".equals(seasonNo) &&
		   !"4".equals(seasonNo) ) ) {
				seasonNo = "";
		}
		
		
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("searchFruit", searchFruit);
		paraMap.put("currentShowPageNo", currentShowPageNo);
		paraMap.put("seasonNo", seasonNo);
		
		
		// 페이징 처리를 위해 검색이 있는 경우, 검색이 없는 경우, 계절을 클릭 한 경우에 대한 총페이지수 알아오기 //
		int totalPage2 = prdao.getTotalPage2(paraMap);
		
		// System.out.println("확인용 totalPage" + totalPage);
		
		// === GET 방식이므로 사용자가 웹브라우저 주소창에서 currentShowPageNo 에 totalPage 값 보다 더 큰값을 입력하여 장난친 경우
		// === GET 방식이므로 사용자가 웹브라우저 주소창에서 currentShowPageNo 에 0 또는 음수를 입력하여 장난친 경우
		// === GET 방식이므로 사용자가 웹브라우저 주소창에서 currentShowPageNo 에 숫자가 아닌 문자열을 입력하여 장난친 경우 
		// 아래처럼 막아주도록 하겠다.

		try {
		      if(Integer.parseInt(currentShowPageNo) > totalPage2 || 
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
		
		int blockSize = 5; // blockSize 는 블럭(토막)당 보여지는 페이지 번호의 개수이다.
		
		int loop = 1; // loop 는 1 부터 증가하여 1개 블럭을 이루는 페이지번호의 개수(지금은 5개)까지만 증가하는 용도이다.
		
		
		// *** ==== pageNo 구하는 공식 ==== *** //
		int pageNo  = ( (Integer.parseInt(currentShowPageNo) - 1)/blockSize ) * blockSize + 1; 
		// pageNo 는 페이지바에서 보여지는 첫번째 번호이다.
			
		pageBar += "<div class='pagination'>";
		
		// [맨처음]
		pageBar += "<a href='productList.ddg?searchFruit="+searchFruit+"&seasonNo="+seasonNo+"&currentShowPageNo=1'>&laquo;</a>"; 
		
		// [이전]
		
		pageBar += "<a href='productList.ddg?searchFruit="+searchFruit+"&seasonNo="+seasonNo+"&currentShowPageNo="+(pageNo-1)+"'>&lsaquo;</a>"; 
		
	
		
		while( !(loop > blockSize || pageNo > totalPage2) ) {
			
			if(pageNo == Integer.parseInt(currentShowPageNo)) {
				pageBar += "<a class='active' href='#'>"+pageNo+"</a>";
			}
			else {
				pageBar += "<a href='productList.ddg?searchFruit="+searchFruit+"&seasonNo="+seasonNo+"&currentShowPageNo="+pageNo+"'>"+pageNo+"</a></li>"; 
			}
			
			loop++;   // 1 2 3 4 5 6 7 8 9 10
			pageNo++;
	
			
		} // end of while( !(loop > blockSize || pageNo > totalPage2) 
		
		// [다음]
		pageBar += "<a href='productList.ddg?searchFruit="+searchFruit+"&seasonNo="+seasonNo+"&currentShowPageNo="+pageNo+"'>&rsaquo;</a>"; 

		
		// [맨마지막]
		pageBar += "<a href='productList.ddg?searchFruit="+searchFruit+"&seasonNo="+seasonNo+"&currentShowPageNo="+totalPage2+"'>&raquo;</a>"; 
		
		
		pageBar += "</div>";
		
		try {
			// List<ProductVO> prdList = prdao.productListSelectAll();
			
			List<ProductVO> prdList = prdao.prdListPaging2(paraMap);
			request.setAttribute("prdList", prdList);	
			request.setAttribute("searchFruit", searchFruit);
			request.setAttribute("pageBar", pageBar);
			request.setAttribute("currentShowPageNo", currentShowPageNo);
			request.setAttribute("seasonNo", seasonNo);
			
			
			// 찜 목록 확인해오기
			List<WishVO> wishList = prdao.wishList();
			HttpSession session = request.getSession();
			session.setAttribute("wishList", wishList);

			super.setRedirect(false);
			super.setViewPage("/WEB-INF/product/productlist.jsp");
			
			
		} catch (SQLException e) {	// 쿼리문 오류 발생 시
			e.printStackTrace();
			super.setRedirect(true);	// redirect 시킴
			super.setViewPage(request.getContextPath()+"/error.ddg");
		}
		
	} // end of public void execute(HttpServletRequest request, HttpServletResponse response)

}


