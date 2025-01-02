package order.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import order.model.*;

public class UpdateOrderStatus extends AbstractController {

	private OrderDAO odao = new OrderDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		if (!"POST".equalsIgnoreCase(request.getMethod())) {
			String message = "비정상적인 접근입니다 !!";
			String loc = "javascript:history.go(-1)";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/common/msg.jsp");
			
			return;
		}
		
		// 로그인 했는지
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
		
		if (loginuser == null) {
			String message = "로그인 후 이용가능합니다!";
			String loc = request.getContextPath()+"/login/login.ddg";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/common/msg.jsp");
			
			return;
		}
		
		// 관리자인지
		if (loginuser.getRole() != 2) {
			String message = "관리자만 접근 가능합니다!";
			String loc = request.getContextPath()+"/index.ddg";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/common/msg.jsp");
			
			return;
		}
		
		
		String order_no = request.getParameter("order_no");	 		// 각 테이블 조건절
		String order_status = request.getParameter("order_status");	// 주문의 주문상태 설정을 위함
		String ship_status = request.getParameter("ship_status");	// 상품별 배송상태 설정을 위함
		String prod_no = request.getParameter("prod_no");			// 상품번호 주문상세 조건절
		
		
		System.out.println("확인용 order_no => "+ order_no);
		System.out.println("확인용 order_status => "+ order_status);
		System.out.println("확인용 ship_status => "+ ship_status);
		System.out.println("확인용 prod_no => "+ prod_no);
		
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("order_no", order_no);
		paraMap.put("order_status", order_status);
		paraMap.put("ship_status", ship_status);
		paraMap.put("prod_no", prod_no);
		
		
		try {
			int n = odao.updateOrderStatus(paraMap);
			
			if (n == 1) {
				request.setAttribute("message", "변경에 성공했습니다.");
				request.setAttribute("loc", request.getContextPath()+ "/admin/adminDeliveryStatus.ddg");
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/common/msg.jsp");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
			super.setRedirect(true);
			super.setViewPage(request.getContextPath()+ "/error.ddg");
		}
	}// end of execute() -----------------------------

}
