package order.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import order.model.*;

public class OrderCommit extends AbstractController {

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
		
		if (!"POST".equalsIgnoreCase(request.getMethod())) {
			
			String message = "비정상적 요청입니다아아아아";
			String loc = "javascript:history.back()";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/common/msg.jsp");
			
			return;
		}
		
		
		
		// 주문확정 버튼을 클릭했을 경우
		String order_no = request.getParameter("order_no");
		String user_no = request.getParameter("user_no");
		
		System.out.println("확인용 order_no =>"+ order_no);
		try {
			Integer.parseInt(user_no);
			
			boolean isExist = odao.isExistOrder(order_no);
			
			if (!isExist) {
				String message = "비정상적인 접근입니다.!";
				String loc = "javascript:history.back()";
				
				request.setAttribute("message", message);
				request.setAttribute("loc", loc);
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/common/msg.jsp");
				
				return;
			}
			
		} catch (NumberFormatException e) {
			String message = "비정상적인 요청입니다.!";
			String loc = "javascript:history.back()";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/common/msg.jsp");
			
			return;
		}
		
		if (loginuser.getUser_no() != Integer.parseInt(user_no)) {
			// url로 다른유저 정보에 접근 시
			
			String message = "비정상적인 요청입니다.!";
			String loc = "javascript:history.back()";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/common/msg.jsp");
			
			return;
		}
		
		
		// 정상적인 접근일 경우 주문상태를 변경한다.
		try {
			
			Map<String, String> paraMap = new HashMap<>();
			
			paraMap.put("order_no", order_no);
			paraMap.put("user_no", user_no);
			
			int n = odao.orderCommit(paraMap);
			
			if (n == 1) {
				JSONObject jsonObj = new JSONObject();
				
				jsonObj.put("isComplete", true);
				
				String json = jsonObj.toString();
				
				request.setAttribute("json", json);
				super.setRedirect(false);
			  	super.setViewPage("/WEB-INF/common/jsonview.jsp");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
	}// end of execute() ---------------------------------

}
