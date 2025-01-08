package admin.admincontroller;

import java.util.Map;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import admin.model.*;

public class GetRevenue extends AbstractController {

	private StatisticsDAO sdao = new StatisticsDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// === 매출 통계를 반환해주는 클래스 === //
		
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
	
		
		//String viewType = request.getParameter("viewType");
		
		// 방문횟수 통계를 필터링 했을 경우
		//System.out.println("확인용 viewType : "+ viewType);
		
		// 최근 일주일 매출액 통계
		Map<String, Integer> revenueWeek = sdao.getRevenueWeek();
		
		// 2주전 매출액 통계
		Map<String, Integer> revenue2Week = sdao.getRevenue2Week();
		
		// 최근 6개월 매출액 통계
		Map<String, Integer> revenueMonth = sdao.getRevenueMonth();
		
		// 전년 동월 매출액 통계
		Map<String, Integer> revenueMonthAgo = sdao.getRevenueMonthAgo();
	
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("before_6days", revenueWeek.get("before_6days"));
		jsonObj.put("before_5days", revenueWeek.get("before_5days"));
		jsonObj.put("before_4days", revenueWeek.get("before_4days"));
		jsonObj.put("before_3days", revenueWeek.get("before_3days"));
		jsonObj.put("before_2days", revenueWeek.get("before_2days"));
		jsonObj.put("before_1days", revenueWeek.get("before_1days"));
		jsonObj.put("today", revenueWeek.get("today"));
		
		//////////////////////////////////////////////////////////////
		
		jsonObj.put("before_13days", revenue2Week.get("before_13days"));
		jsonObj.put("before_12days", revenue2Week.get("before_12days"));
		jsonObj.put("before_11days", revenue2Week.get("before_11days"));
		jsonObj.put("before_10days", revenue2Week.get("before_10days"));
		jsonObj.put("before_9days", revenue2Week.get("before_9days"));
		jsonObj.put("before_8days", revenue2Week.get("before_8days"));
		jsonObj.put("before_7days", revenue2Week.get("before_7days"));
		
		//////////////////////////////////////////////////////////////
		
		jsonObj.put("before_6months", revenueMonth.get("before_6months"));
		jsonObj.put("before_5months", revenueMonth.get("before_5months"));
		jsonObj.put("before_4months", revenueMonth.get("before_4months"));
		jsonObj.put("before_3months", revenueMonth.get("before_3months"));
		jsonObj.put("before_2months", revenueMonth.get("before_2months"));
		jsonObj.put("before_1months", revenueMonth.get("before_1months"));
		jsonObj.put("this_month", revenueMonth.get("this_month"));
		
		//////////////////////////////////////////////////////////////
				
		jsonObj.put("before_18months", revenueMonthAgo.get("before_18months"));
		jsonObj.put("before_17months", revenueMonthAgo.get("before_17months"));
		jsonObj.put("before_16months", revenueMonthAgo.get("before_16months"));
		jsonObj.put("before_15months", revenueMonthAgo.get("before_15months"));
		jsonObj.put("before_14months", revenueMonthAgo.get("before_14months"));
		jsonObj.put("before_13months", revenueMonthAgo.get("before_13months"));
		jsonObj.put("before_12months", revenueMonthAgo.get("before_12months"));
	
		String json = jsonObj.toString();
		
		request.setAttribute("json", json);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/common/jsonview.jsp");
		
	}// end of execute() --------------------------

}
