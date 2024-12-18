package product.controller;

import java.sql.SQLException;
import java.util.List;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import product.domain.ProductVO;
import product.model.ProductDAO;
import product.model.ProductDAO_imple;

public class SeasonProduct extends AbstractController {
	
	private ProductDAO prdao = new ProductDAO_imple();

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
	
		try {
			
			String seasonNo = request.getParameter("seasonNo");
			
			
			if(seasonNo == null || 
			  (!"1".equals(seasonNo) &&  
		       !"2".equals(seasonNo) && 
			   !"3".equals(seasonNo) &&
			   !"4".equals(seasonNo) ) ) {
			 		seasonNo = "";
			}
			
			List<ProductVO> prdList = prdao.seasonProduct(seasonNo);
			
			if (prdList.size() > 0) {
				request.setAttribute("prdList", prdList);
			}
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/product/productlist.jsp");
			
		} catch (SQLException e) {	// 쿼리문 오류 발생 시
			e.printStackTrace();
			super.setRedirect(true);	// redirect 시킴
			super.setViewPage(request.getContextPath()+"/error.ddg");
		}
		
	}

}
