package community.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import review.domain.ReviewListVO;
import review.model.ReviewListDAO;
import review.model.ReviewListDAO_imple;

public class ProductCarrierController2 extends AbstractController {
	
	
	private ReviewListDAO revdao = new ReviewListDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// QnA로 옮겨주는 캐리어임 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		
			String method = request.getMethod();
			
			if("GET".equalsIgnoreCase(method)) {
				
				String prod_no = request.getParameter("prod_no");
				
				ReviewListVO qpcarrier = revdao.qProductCarrier(prod_no);
				
				request.setAttribute("qpcarrier", qpcarrier);
				

				super.setViewPage("/WEB-INF/qna/qnaWrite.jsp");
				
			}

	}

}
