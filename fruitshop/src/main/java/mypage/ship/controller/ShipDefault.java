package mypage.ship.controller;

import java.sql.SQLException;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import mypage.ship.model.ShipDAO;
import mypage.ship.model.ShipDAO_imple;

public class ShipDefault extends AbstractController {

	ShipDAO sdao = new ShipDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		if(loginuser != null) {
			
			
			try {
				int user_no = loginuser.getUser_no();
				
				int ship_no = Integer.parseInt(request.getParameter("inputValue"));
				
				sdao.noDefault(user_no); // 모든 배송지를 기본배송지가 아니도록 바꾼다.
				
				int n = sdao.oneDefault(ship_no);
				
				if(n==1) {
					
					super.setRedirect(true);
					super.setViewPage( request.getContextPath() + "/mypage/shipManagement.ddg");
				}
	
			} catch (NumberFormatException | SQLException e) {
				e.printStackTrace();
			}
		
		}
		else {
			super.setRedirect(true);
			super.setViewPage(request.getContextPath()+"/login/login.ddg");
		}
		
		
		
	}

}
