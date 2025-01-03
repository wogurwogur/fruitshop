package mypage.ship.controller;

import java.sql.SQLException;
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
				
				String ship_name = request.getParameter("ship_name");
				String ship_receiver = request.getParameter("ship_receiver");
				
				String ship_receivertel1 = request.getParameter("ship_receivertel1");
				String ship_receivertel2 = request.getParameter("ship_receivertel2");
				String ship_receivertel3 = request.getParameter("ship_receivertel3");
				String ship_receivertel = ship_receivertel1 + ship_receivertel2 + ship_receivertel3;
				
		        String ship_postcode = request.getParameter("ship_postcode");
		        String ship_address = request.getParameter("ship_address");
		        String ship_extraAddress = request.getParameter("ship_extraAddress");
		        if(ship_extraAddress == null) {
		        	ship_extraAddress = " ";
		        }
		        String ship_detailAddress = request.getParameter("ship_detailAddress");
		        if(ship_detailAddress == null) {
		        	ship_detailAddress = " ";
		        }
		        
		        ShipVO svo = new ShipVO();
		        svo.setFk_user_no(loginuser.getUser_no());
		        svo.setShip_name(ship_name);
		        svo.setShip_receiver(ship_receiver);
		        svo.setShip_receivertel(ship_receivertel);
		        svo.setShip_postcode(ship_postcode);
		        svo.setShip_address(ship_address);
		        svo.setShip_extraAddress(ship_extraAddress);
		        svo.setShip_detailAddress(ship_detailAddress);
		        
		        try {
					
		        	int n = sdao.shipAdd(svo);
		        	
		        	if(n==1) {
		        		
		        		super.setRedirect(true);
						super.setViewPage( request.getContextPath() + "/mypage/shipManagement.ddg");
		        		
		        	}

		        	
				} catch (SQLException e) {
					e.printStackTrace();
					String message = "배송지 추가 실패";
					
					String loc = request.getContextPath()+"/mypage/shipManagement.ddg";
					
					request.setAttribute("message", message);
				    request.setAttribute("loc", loc);
				        
				    super.setRedirect(false);
				    super.setViewPage("/WEB-INF/common/msg.jsp");
				}

			}
			
		}
		else {
			super.setRedirect(true);
			super.setViewPage(request.getContextPath()+"/login/login.ddg");
		}

	}

}
