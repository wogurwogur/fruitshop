package mypage.wish.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
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
    	
    	if(loginuser != null) {	// 로그인을 했을때 
    		
    	}
		
	}

}
