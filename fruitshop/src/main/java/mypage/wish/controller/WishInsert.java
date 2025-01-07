package mypage.wish.controller;

import java.util.HashMap;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import mypage.wish.domain.WishVO;
import mypage.wish.model.WishDAO;
import mypage.wish.model.WishDAO_imple;

public class WishInsert extends AbstractController {
	
	private WishDAO wdao = new WishDAO_imple();
	
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

    		
	 	    if(loginuser.getUser_no() == Integer.parseInt(fk_user_no)){
	 	    	
    		
	    		try {
	    			// 상품정보 받아와서 관심상품에 넣기
	    	 	    Map<String,String> paraMap = new HashMap<>(); 
	    	 	    
	    	 	    paraMap.put("fk_user_no", fk_user_no);
	    	 	    paraMap.put("fk_prod_no", fk_prod_no);
	    			
	    	 	    // 관심상품안에 같은 상품이 있는지 정보 보기
	    	 	    WishVO select = wdao.selectproduct(paraMap);
	    	 	    
	    	 	    
	    	 	    // 관심상품안에 같은 상품이 있을때
	    	 	    if(select != null) {
	    	 	    	
	    	 	    	// 관심상품에 이미 있다면 삭제
	    	 	    	int delete = wdao.deleteWish(paraMap);
	    	 	    	
	    	 	    	if(delete == 1) {
	    	 	    		super.setRedirect(true);
	    	 	    		super.setViewPage(request.getContextPath()+ "/mypage/wishList.ddg");
	    	 	    	}
	    	 	    	
	    	 	    	
	    	 	    }
	    	 	    else {
	    	 	
	    	 	    	// 관심상품에 없다면 추가
	    	 	    	int n = wdao.insertWish(paraMap);
	    	 	    	
	    	 	    	if(n == 1) {
	    	 	    		
		    	 	    	super.setRedirect(true);
		    	 	    	super.setViewPage(request.getContextPath()+ "/mypage/wishList.ddg");
		    	 	    }
	    	 	    }
	    	 	    
	    	     } catch (Exception e) {
	    	    	e.printStackTrace(); 
		            super.setRedirect(true);	// redirect 시킴
					super.setViewPage(request.getContextPath()+"/error.ddg");
	               }
	    		
	 	    	}// end of if(loginuser.getUser_no() == Integer.parseInt(fk_user_no))-----------------------------
	 	    
	 	    else {
	 	    	message = "다른사람의 관심상품에 상품을 넣을 수 없습니다 !!";
	 	    	loc = "javascript:history.back()";
	 	    	
	 	    	request.setAttribute("message", message);
				request.setAttribute("loc", loc);
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/common/msg.jsp");
	 	    }
	 	    
    	  }// end of if(loginuser != null ) {}-----------------------------------------------
    	
	    	else {
	    		//	로그인 상태가 아닌 경우
				message = "로그인 후 이용가능합니다!";
				loc = request.getContextPath()+"/login/login.ddg";
				
				request.setAttribute("message", message);
				request.setAttribute("loc", loc);
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/common/msg.jsp");
			}
		
	}

}
