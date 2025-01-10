package community.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import review.domain.ReviewListVO;
import review.model.ReviewListDAO;
import review.model.ReviewListDAO_imple;

public class ProductCarrierController extends AbstractController {

	
	private ReviewListDAO revdao = new ReviewListDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
			
		// 구매후기로 옮겨주는 캐리어임 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		
		String method = request.getMethod();
		
		if("GET".equalsIgnoreCase(method)) {
			
			String prod_no = request.getParameter("prod_no");
			
			ReviewListVO pcarrier = revdao.ProductCarrier(prod_no);
			
			request.setAttribute("pcarrier", pcarrier);
			

			super.setViewPage("/WEB-INF/review/reviewWrite.jsp");
			
		}
		


	}

}
