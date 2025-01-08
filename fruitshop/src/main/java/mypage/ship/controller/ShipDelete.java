package mypage.ship.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import mypage.ship.model.ShipDAO;
import mypage.ship.model.ShipDAO_imple;

public class ShipDelete extends AbstractController {

	ShipDAO sdao = new ShipDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");

		if(loginuser != null && loginuser.getRole() == 1) {
			
			if("get".equalsIgnoreCase(request.getMethod())) {
				
				super.setRedirect(true);
				super.setViewPage(request.getContextPath()+"/index.ddg");
				
			} 
			if("post".equalsIgnoreCase(request.getMethod())) {
				
				String str_ship_no = request.getParameter("inputValue");
				
				String[] arr_ship_no = str_ship_no.split(",");

				try {

					if(arr_ship_no.length > 0) {
				
						int n = 0;
						
						for(String ship_no : arr_ship_no) {
							n += sdao.deleteShipInfo(ship_no);
						}
						
						if(n == arr_ship_no.length) {
							
							super.setRedirect(true);
							super.setViewPage(request.getContextPath()+"/mypage/shipManagement.ddg");	
						}
						else {
							
							
							
							
						}
		
					}
				} catch (SQLException e) {
					e.printStackTrace();
					String message = "배송지 삭제 실패";
					
					String loc = request.getContextPath()+"/mypage/shipManagement.ddg";
					
					request.setAttribute("message", message);
				    request.setAttribute("loc", loc);
				        
				    super.setRedirect(false);
				    super.setViewPage("/WEB-INF/common/msg.jsp");
				}
				
				
			}
			
			
			
			
			
			
			
		}
		
	}

}
