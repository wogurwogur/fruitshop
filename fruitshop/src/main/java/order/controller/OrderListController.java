package order.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class OrderListController extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
//		로그인을 한 유저만 주문목록 페이지로 진입할 수 있게 한다.
		
		HttpSession session = request.getSession();
		
//		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		String loginuser = "dfs";
		
		if (loginuser != null) {
//			유저가 정상적으로 로그인 된 경우	
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/order/orderlist.jsp");
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
		
		
		
	}// end of execute() ---------------------------

}
