package admin.admincontroller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import admin.model.*;

public class ProdRevenueRank extends AbstractController {

	private StatisticsDAO sdao = new StatisticsDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// === 상품별 주문 통계를 반환해주는 클래스 === //
		
		// 주소창으로 접근 시 메인페이지로 이동
		String referer = request.getHeader("referer");
		// request.getHeader("referer"); 은 이전 페이지의 URL을 가져오는 것이다.
		
//						System.out.println("~~~~~ 확인용 referer => "+ referer);
		
		if (referer == null) {
			// referer == null 은 웹브라우저 주소창에 직접 URL을 입력하여 들어온 것이다.
			super.setRedirect(true);
			super.setViewPage(request.getContextPath()+ "/index.ddg");
			return;
		}
		
		HttpSession session = request.getSession();
		
		MemberVO loginuser = (MemberVO)(session.getAttribute("loginuser"));
		
		if (loginuser == null) {
			String message = "로그인 후 이용가능합니다!";
			String loc = request.getContextPath()+"/login/login.ddg";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/common/msg.jsp");
			
			return;
		}
		
		if (loginuser.getRole() == 1) {
			String message = "관리자만 접근 가능합니다!";
			String loc = request.getContextPath()+"/index.ddg";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/common/msg.jsp");
			
			return;
		}
		
		String orderSearchRange = request.getParameter("orderSearchRange");		// 정렬방식 (오름,내림)
		String orderSearchType  = request.getParameter("orderSearchType");		// 정렬방식 (매출, 판매량, 매출비중)
		String ordersearchMonth  = request.getParameter("ordersearchMonth");		// 정렬방식 (매출, 판매량, 매출비중)
		
		if ("".equals(orderSearchRange) || orderSearchRange == null) {
			// 입력을 하지 않으면 오름차순 정렬
			orderSearchRange = "DESC";
		}
		
		if ("".equals(orderSearchType) || orderSearchType == null) {
			// 입력을 하지 않으면 오름차순 정렬
			orderSearchType = "revenue_pct";
		}
		
		if ("".equals(ordersearchMonth) || ordersearchMonth == null) {
			// 입력을 하지 않으면 이번달
			ordersearchMonth = "0";
		}
		
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("orderSearchRange", orderSearchRange);
		paraMap.put("orderSearchType", orderSearchType);
		paraMap.put("ordersearchMonth", ordersearchMonth);
		
		
		// 상품의 판매량 랭킹 정보를 가져온다.
		List<Map<String, String>> prodListRank = sdao.getProdListRank(paraMap);
		
		JSONArray jsonArr = new JSONArray();
		
		for (Map<String, String> prod : prodListRank) {
			
			JSONObject jsonObj = new JSONObject();
			
			jsonObj.put("prod_no", prod.get("prod_no"));
			jsonObj.put("prod_name", prod.get("prod_name"));
			jsonObj.put("prod_price", prod.get("prod_price"));
			jsonObj.put("sold_cnt", prod.get("sold_cnt"));
			jsonObj.put("prod_revenue", prod.get("prod_revenue"));
			jsonObj.put("revenue_pct", prod.get("revenue_pct"));
			
			jsonArr.put(jsonObj);
			
		}// end of for() ----------------------
		
		String json = jsonArr.toString();

		request.setAttribute("json", json);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/common/jsonview.jsp");
		
	}// end of execute() --------------------------

}
