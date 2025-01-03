package community.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import my.util.MyUtil;
import product.domain.ProductVO;
import review.domain.ReviewListVO;
import review.model.ReviewListDAO;
import review.model.ReviewListDAO_imple;

public class ProductFindController extends AbstractController {
	
	
	private ReviewListDAO revdao = new ReviewListDAO_imple();
	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		
		String method = request.getMethod();
		
		if("POST".equalsIgnoreCase(method)) {
			
			List<ReviewListVO> rproductList = revdao.rproductFind();
						
			request.setAttribute("rproductList", rproductList);
						
			
		}
		else {
			List<ReviewListVO> rproductList = revdao.rproductFind();
			
			request.setAttribute("rproductList", rproductList);
		}
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/community/productFind.jsp");

				

	} // end of public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException {
	
	
	
	

} // end of public class ProductFindController extends AbstractController {
