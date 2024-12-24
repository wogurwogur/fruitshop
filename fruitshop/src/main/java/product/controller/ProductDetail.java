package product.controller;

import java.sql.SQLException;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import product.domain.ProductVO;
import product.model.ProductDAO;
import product.model.ProductDAO_imple;
import review.domain.ReviewListVO;

public class ProductDetail extends AbstractController {
	
	private ProductDAO prdao = new ProductDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String prodNo = request.getParameter("prodNo"); // 상품 번호
		
		if(prodNo == null) {
			prodNo = "";
		}
		
		try {
			ProductVO prdvo = prdao.prdDetails(prodNo);
			request.setAttribute("prdvo", prdvo);
			
			
			ReviewListVO revo = prdao.reviewList(prodNo); // 입력받은 상품번호에 대한 reivew 리스트를 조회해온다.
			
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/product/productdetail.jsp");
			
		} catch (SQLException e) {	// 쿼리문 오류 발생 시
			e.printStackTrace();
			super.setRedirect(true);	// redirect 시킴
			super.setViewPage(request.getContextPath()+"/error.ddg");
		}
			


	}

}
