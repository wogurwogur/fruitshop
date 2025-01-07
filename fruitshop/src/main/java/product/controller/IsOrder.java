package product.controller;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import product.model.ProductDAO;
import product.model.ProductDAO_imple;

public class IsOrder extends AbstractController {
	
	
	private ProductDAO prdao = new ProductDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String fk_prod_no = request.getParameter("fk_prod_no");
	    String fk_user_no = request.getParameter("fk_user_no");

	    Map<String, String> paraMap = new HashMap<>();
	      
	    paraMap.put("fk_prod_no", fk_prod_no);
	    paraMap.put("fk_user_no", fk_user_no);
	    
	    boolean bool = prdao.isOrder(paraMap);
	    
	    JSONObject jsonObj = new JSONObject();
	    jsonObj.put("isOrder", bool);
	    
	    String json = jsonObj.toString();
	    request.setAttribute("json", json);
      
	    super.setRedirect(false);
        super.setViewPage("/WEB-INF/common/jsonview.jsp");
		

	}

}
