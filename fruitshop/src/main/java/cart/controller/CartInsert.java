package cart.controller;

import java.util.HashMap;
import java.util.Map;

import cart.model.CartDAO;
import cart.model.CartDAO_imple;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CartInsert extends AbstractController {
	
	private CartDAO cdao = new CartDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 상품정보 받아와서 장바구니에 넣기
 	    String fk_prod_no = request.getParameter("prodNo");	
 	    String fk_user_no = request.getParameter("userNo");	
 	    String cart_prodcount = request.getParameter("prdCnt");	
 	    
 	    Map<String,String> paraMap = new HashMap<>(); 
 	    
 	    paraMap.put("fk_user_no", fk_user_no);
 	    paraMap.put("fk_prod_no", fk_prod_no);
 	    paraMap.put("cart_prodcount", cart_prodcount);
		
 	    int n  = cdao.insertCart(paraMap);
 	    
 	    if(n == 1) {
 	    	super.setRedirect(true);
 	    	super.setViewPage(request.getContextPath()+ "/cart/cartList.ddg");
 	    }
 	    
	}

}
