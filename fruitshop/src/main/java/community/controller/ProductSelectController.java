package community.controller;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import review.domain.ReviewListVO;
import review.model.ReviewListDAO;
import review.model.ReviewListDAO_imple;

public class ProductSelectController extends AbstractController {

	
	private ReviewListDAO revdao = new ReviewListDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();
		
		if("GET".equalsIgnoreCase(method)) {
		
			String prod_no = request.getParameter("prod_no");
			
			
			ReviewListVO rvo = revdao.productSelect(prod_no);
			
			JSONObject jsobj = new JSONObject();
			
			jsobj.put("prod_no", rvo.getProd_no());
			jsobj.put("prod_name", rvo.getProd_name());
			jsobj.put("prod_price", rvo.getProd_price());
			jsobj.put("prod_thumnail", rvo.getProd_thumnail());
			
			
			String json = jsobj.toString();
			
			request.setAttribute("json", json);
			
			
			//super.setRedirect(true);
			super.setViewPage("/WEB-INF/common/jsonview.jsp");
			
		
		}
		
		
		

	}

}
