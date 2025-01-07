package cart.controller;

import java.util.HashMap;
import java.util.Map;

import cart.domain.CartVO;
import cart.model.CartDAO;
import cart.model.CartDAO_imple;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;

public class CartInsert extends AbstractController {
	
	private CartDAO cdao = new CartDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
    	MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
    	String message = "";
    	String loc = "";
    	
    	
    	if(loginuser != null) {
    		// 로그인을 했을때 
    		
    		String fk_prod_no = request.getParameter("prodNo");	
	 	    String fk_user_no = request.getParameter("userNo");	
	 	    String cart_prodcount = request.getParameter("prdCnt");	
	 	    System.out.println("수량: " + cart_prodcount);
    		
	 	    if(loginuser.getUser_no() == Integer.parseInt(fk_user_no)){
	 	    	
    		
	    		try {
	    			// 상품정보 받아와서 장바구니에 넣기
	    	 	    Map<String,String> paraMap = new HashMap<>(); 
	    	 	    
	    	 	    paraMap.put("fk_user_no", fk_user_no);
	    	 	    paraMap.put("fk_prod_no", fk_prod_no);
	    	 	    paraMap.put("cart_prodcount", cart_prodcount);
	    			
	    	 	    // 장바구니안에 같은 상품이 있는지 정보 보기
	    	 	    CartVO select = cdao.selectproduct(paraMap);
	    	 	    
	    	 	    // 장바구니안에 같은 상품이 있을때
	    	 	    if(select != null) {
	    	 	    	
	    	 	    	// 같은 상품 있을때 상품수량 업데이트
	    	 	    	int update = cdao.updatecount(paraMap);
	    	 	    	
	    	 	    	if(update == 1) {
	    	 	    		super.setRedirect(true);
	    	 	    		super.setViewPage(request.getContextPath()+ "/cart/cartList.ddg");
	    	 	    	}
	    	 	    	
	    	 	    }
	    	 	    else {
	    	 	    	int n = cdao.insertCart(paraMap);
	    	 	    	
	    	 	    	if(n == 1) {
	    	 	    		
	    	 	    		loginuser.setCart_cnt(cdao.getcartCount(loginuser.getUser_no()));
	    	 	    		
		    	 	    	super.setRedirect(true);
		    	 	    	super.setViewPage(request.getContextPath()+ "/cart/cartList.ddg");
		    	 	    }
	    	 	    	
	    	 	    }
	    	 	    
	    	     } catch (Exception e) {
	    	    	e.printStackTrace(); 
		            super.setRedirect(true);	// redirect 시킴
					super.setViewPage(request.getContextPath()+"/error.ddg");
	               }
	    		
	 	    	}// end of if(loginuser.getUser_no() == Integer.parseInt(fk_user_no))-----------------------------
	 	    
	 	    else {
	 	    	message = "다른사람의 장바구니에 상품을 넣을 수 없습니다 !!";
	 	    	loc = "javascript:history.back()";
	 	    	
	 	    	request.setAttribute("message", message);
				request.setAttribute("loc", loc);
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/common/msg.jsp");
	 	    }
	 	    
    	  }// end of if(loginuser != null ) {}-----------------------------------------------
    	
	    	else {
	    		//	로그인 상태가 아닌 경우
				message = "로그인 후에 상품을 장바구니에 넣을 수 있습니다 !!";
				loc = request.getContextPath()+"/login/login.ddg";
				
				request.setAttribute("message", message);
				request.setAttribute("loc", loc);
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/common/msg.jsp");
			}
    		
    		
    	
    
	}// end of public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {}----------------

}
