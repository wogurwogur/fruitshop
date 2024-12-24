package order.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.controller.AbstractController;
import coupon.domain.CouponVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import mypage.ship.domain.ShipVO;
import order.model.*;

public class OrderCheckout extends AbstractController {

	private OrderDAO odao = new OrderDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
//		로그인을 한 유저만 주문목록 페이지로 진입할 수 있게 한다.
		
		HttpSession session = request.getSession();
		
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
//		String loginuser = "";
		
		
		if (loginuser != null) {
//			유저가 정상적으로 로그인 된 경우
			
			
			
			
			try {
				Map<String, String> paraMap = new HashMap<>();
				paraMap.put("user_no", String.valueOf(loginuser.getUser_no()));
				
				// 해당 회원의 장바구니에 있는 품목을 가져온다
				List<Map<String, String>> cartList = odao.getCartList(paraMap);
				request.setAttribute("cartList", cartList);
				
				// 해당 회원이 가지고 있는 쿠폰 목록을 가져온다
				List<CouponVO> couponList = odao.getCouponList(paraMap);
				
				if (couponList.size() > 0) {
					request.setAttribute("couponList", couponList);
				}
				
				// 해당 회원의 배송지 목록을 가져온다
				List<ShipVO> shipList = odao.getShipList(paraMap);
				
				if (shipList.size() > 0) {
					request.setAttribute("shipList", shipList);
				}
				
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/order/orderCheckout.jsp");
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else {
//			로그인 상태가 아닌 경우
			String message = "로그인 후 이용가능합니다!";
			String loc = request.getContextPath()+"/login/login.ddg";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/common/msg.jsp");
		}
		

	}// end of execute() ------------------

}
