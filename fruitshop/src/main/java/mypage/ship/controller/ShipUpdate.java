package mypage.ship.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import mypage.ship.domain.ShipVO;
import mypage.ship.model.ShipDAO;
import mypage.ship.model.ShipDAO_imple;

public class ShipUpdate extends AbstractController {

	ShipDAO sdao = new ShipDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		

		if(loginuser != null) {
			
			if("get".equalsIgnoreCase(request.getMethod())) {
				
				String ship_no = request.getParameter("inputValue");
				
				ShipVO svo = new ShipVO();
				
				svo = sdao.shipSelectOne(ship_no);
				
				request.setAttribute("svo", svo);
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/mypage/shipUpdate.jsp");
				
			} 
			if("post".equalsIgnoreCase(request.getMethod())) {
				
				
				
			}
		}
		
	}
	
	

}
