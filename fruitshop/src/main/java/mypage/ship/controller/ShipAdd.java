package mypage.ship.controller;

import java.util.ArrayList;
import java.util.List;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import mypage.ship.domain.ShipVO;
import mypage.ship.model.ShipDAO;
import mypage.ship.model.ShipDAO_imple;

public class ShipAdd extends AbstractController {

	ShipDAO sdao = new ShipDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		if(loginuser != null) {
			
			if("get".equalsIgnoreCase(request.getMethod())) {
				
				
				
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/mypage/shipAdd.jsp");
				
			} 
			else if("post".equalsIgnoreCase(request.getMethod())) {
				
				
			}
			
		}
		else {
			super.setRedirect(true);
			super.setViewPage(request.getContextPath()+"/login/login.ddg");
		}

	}

}
