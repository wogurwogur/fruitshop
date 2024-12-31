package order.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import order.model.*;

public class OrderDetail extends AbstractController {

	private OrderDAO odao = new OrderDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		if (loginuser == null) {
//			로그인 상태가 아닌 경우
			String message = "로그인 후 이용가능합니다!";
			String loc = request.getContextPath()+"/login/login.ddg";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/common/msg.jsp");
			
			return;
		}
		
		String order_no = request.getParameter("order_no");
		
		Map<String, String> paraMap = new HashMap<>();
		
		paraMap.put("order_no", order_no);
		paraMap.put("user_no", String.valueOf(loginuser.getUser_no()));
		
		// 해당 주문번호의 상세내역을 가져온다.
		List<Map<String, String>> orderDetailList =  odao.getOrderDetail(paraMap);
		
		/*
		for (int i=0; i<orderDetailList.size(); i++) {
			System.out.println("확인용 주문상세 회원번호: "+ orderDetailList.get(i).get("fk_user_no")); 
		}// end of for() ----------------------
		*/
		
		request.setAttribute("orderDetailList", orderDetailList);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/order/orderDetail.jsp");
		
	}// end of execute() -----------------------------

}
