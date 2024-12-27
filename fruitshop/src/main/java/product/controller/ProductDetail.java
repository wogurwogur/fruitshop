package product.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import product.domain.ProductVO;
import product.model.ProductDAO;
import product.model.ProductDAO_imple;

public class ProductDetail extends AbstractController {
	
	private ProductDAO prdao = new ProductDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String prodNo = request.getParameter("prodNo"); // 상품 번호
		String currentShowPageNo = request.getParameter("currentShowPageNo"); // 현재 페이지 번호
		
		
		try {
	         Integer.parseInt(prodNo);
	    } catch(NumberFormatException e) { // 유저가 URL에 상품번호를 숫자 입력하지 않은 경우 
	    	 super.setRedirect(true);	// redirect 시킴
			 super.setViewPage(request.getContextPath()+"/product/productList.ddg");
	         return;
	    }
		
		
		
		if(prodNo == null) {
			prodNo = "";
		}
		
		if(currentShowPageNo == null) {
			currentShowPageNo = "1";
		}
		
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("prodNo", prodNo);
		paraMap.put("currentShowPageNo", currentShowPageNo);
		
		
		
		// ------------------ 후기 페이징 처리 ------------------ //
		// 페이징 처리를 위해 해당 상품 번호에 대한 후기가 있는 경우 총페이지수 알아오기
//		int reviewTotalPage = prdao.getReviewTotalPage(paraMap);
		
		
		
		// 페이징 처리를 위해 해당 상품 번호에 대한 후기가 있는 경우 총페이지수 알아오기
//		int qnaTotalPage = prdao.getQnaTTotalPage(paraMap);
			
		try {
			ProductVO prdvo = prdao.prdDetails(prodNo);
			
			if (prdvo == null) { // 유저가 URL 상품번호에 없는 번호를 입력했을때 상품 목록화면으로 복귀 시킨다.
				super.setRedirect(true);	// redirect 시킴
				super.setViewPage(request.getContextPath()+"/product/productList.ddg");
				return;
			}
			
			request.setAttribute("prdvo", prdvo);
			
			List<ProductVO> prd_reviewList = prdao.prd_reviewList(paraMap); // 입력받은 상품번호에 대한 후기 리스트를 조회해온다.
			request.setAttribute("prd_reviewList", prd_reviewList);
			
			List<ProductVO> prd_qnaList = prdao.prd_qnaList(paraMap); // 입력받은 상품번호에 대한 Q&A 리스트를 조회해온다.
			request.setAttribute("prd_qnaList", prd_qnaList);
			
			int review_cnt = prdao.review_cnt(prodNo); // 상품 상세페이지 내 입력받은 상품번호에 후기 수량 표시를 위해 후기 개수를 조회해온다.
			request.setAttribute("review_cnt", review_cnt);
			
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/product/productdetail.jsp");
			
		} catch (SQLException e) {	// 쿼리문 오류 발생 시
			e.printStackTrace();
			super.setRedirect(true);	// redirect 시킴
			super.setViewPage(request.getContextPath()+"/error.ddg");
		} 
			


	}

}
