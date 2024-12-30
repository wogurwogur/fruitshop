package order.controller;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import order.model.*;

public class GetOrderCount extends AbstractController {

	private OrderDAO odao = new OrderDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		String fromDate = request.getParameter("fromDate");
		
		if (fromDate == null) {
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/order/orderlist.jsp");
			
			return;
		}
		
		String toDate 	= request.getParameter("toDate");
		
		Map<String, String> paraMap = new HashMap<>();
		
		paraMap.put("user_no", String.valueOf(loginuser.getUser_no()));
		paraMap.put("fromDate", fromDate);
		paraMap.put("toDate", toDate);
		
		
		int totalOrderCount = odao.totalOrderCount(paraMap);
		
		
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("totalOrderCount", totalOrderCount);
		
		String json = jsonObj.toString();
		
		request.setAttribute("json", json);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/common/jsonview.jsp");
		
	}// end of execute() ------------------

}
