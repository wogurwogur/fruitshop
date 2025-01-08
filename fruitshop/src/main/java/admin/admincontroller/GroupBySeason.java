package admin.admincontroller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;

import java.util.Map;

import org.json.JSONObject;

import admin.model.*;

public class GroupBySeason extends AbstractController {

	private StatisticsDAO sdao = new StatisticsDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// === 계절별 상품 주문건수 반환해주는 클래스 === //
		
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

		// 주간 계절별 주문건수 
		Map<String, Integer> seasonOrderWeek = sdao.getSeasonOrderWeek();
		
		// 월간 계절별 주문건수
		Map<String, Integer> seasonOrderMonth = sdao.getSeasonOrderMonth();
		
//		Map<String, Integer> visitUser2Week = sdao.getvisitUser2Week();
//		Map<String, Integer> visitUserMonth = sdao.getvisitUserMonth();
//		Map<String, Integer> visitUser2Month = sdao.getvisitUser2Month();
		
		
		JSONObject jsonObj = new JSONObject();
		
		// 주간 계절별 주문건수
		jsonObj.put("spring_before_6days", seasonOrderWeek.get("spring_before_6days"));
		jsonObj.put("spring_before_5days", seasonOrderWeek.get("spring_before_5days"));
		jsonObj.put("spring_before_4days", seasonOrderWeek.get("spring_before_4days"));
		jsonObj.put("spring_before_3days", seasonOrderWeek.get("spring_before_3days"));
		jsonObj.put("spring_before_2days", seasonOrderWeek.get("spring_before_2days"));
		jsonObj.put("spring_before_1days", seasonOrderWeek.get("spring_before_1days"));
		jsonObj.put("spring_today", seasonOrderWeek.get("spring_today"));
		
		jsonObj.put("summer_before_6days", seasonOrderWeek.get("summer_before_6days"));
		jsonObj.put("summer_before_5days", seasonOrderWeek.get("summer_before_5days"));
		jsonObj.put("summer_before_4days", seasonOrderWeek.get("summer_before_4days"));
		jsonObj.put("summer_before_3days", seasonOrderWeek.get("summer_before_3days"));
		jsonObj.put("summer_before_2days", seasonOrderWeek.get("summer_before_2days"));
		jsonObj.put("summer_before_1days", seasonOrderWeek.get("summer_before_1days"));
		jsonObj.put("summer_today", seasonOrderWeek.get("summer_today"));

		jsonObj.put("autumn_before_6days", seasonOrderWeek.get("autumn_before_6days"));
		jsonObj.put("autumn_before_5days", seasonOrderWeek.get("autumn_before_5days"));
		jsonObj.put("autumn_before_4days", seasonOrderWeek.get("autumn_before_4days"));
		jsonObj.put("autumn_before_3days", seasonOrderWeek.get("autumn_before_3days"));
		jsonObj.put("autumn_before_2days", seasonOrderWeek.get("autumn_before_2days"));
		jsonObj.put("autumn_before_1days", seasonOrderWeek.get("autumn_before_1days"));
		jsonObj.put("autumn_today", seasonOrderWeek.get("autumn_today"));
		
		jsonObj.put("winter_before_6days", seasonOrderWeek.get("winter_before_6days"));
		jsonObj.put("winter_before_5days", seasonOrderWeek.get("winter_before_5days"));
		jsonObj.put("winter_before_4days", seasonOrderWeek.get("winter_before_4days"));
		jsonObj.put("winter_before_3days", seasonOrderWeek.get("winter_before_3days"));
		jsonObj.put("winter_before_2days", seasonOrderWeek.get("winter_before_2days"));
		jsonObj.put("winter_before_1days", seasonOrderWeek.get("winter_before_1days"));
		jsonObj.put("winter_today", seasonOrderWeek.get("winter_today"));
		
		
		// 2주전 계절별 주문건수
		jsonObj.put("spring_before_13days", seasonOrderWeek.get("spring_before_13days"));
		jsonObj.put("spring_before_12days", seasonOrderWeek.get("spring_before_12days"));
		jsonObj.put("spring_before_11days", seasonOrderWeek.get("spring_before_11days"));
		jsonObj.put("spring_before_10days", seasonOrderWeek.get("spring_before_10days"));
		jsonObj.put("spring_before_9days", seasonOrderWeek.get("spring_before_9days"));
		jsonObj.put("spring_before_8days", seasonOrderWeek.get("spring_before_8days"));
		jsonObj.put("spring_before_7days", seasonOrderWeek.get("spring_before_7days"));
		
		jsonObj.put("summer_before_13days", seasonOrderWeek.get("summer_before_13days"));
		jsonObj.put("summer_before_12days", seasonOrderWeek.get("summer_before_12days"));
		jsonObj.put("summer_before_11days", seasonOrderWeek.get("summer_before_11days"));
		jsonObj.put("summer_before_10days", seasonOrderWeek.get("summer_before_10days"));
		jsonObj.put("summer_before_9days", seasonOrderWeek.get("summer_before_9days"));
		jsonObj.put("summer_before_8days", seasonOrderWeek.get("summer_before_8days"));
		jsonObj.put("summer_before_7days", seasonOrderWeek.get("summer_before_7days"));

		jsonObj.put("autumn_before_13days", seasonOrderWeek.get("autumn_before_13days"));
		jsonObj.put("autumn_before_12days", seasonOrderWeek.get("autumn_before_12days"));
		jsonObj.put("autumn_before_11days", seasonOrderWeek.get("autumn_before_11days"));
		jsonObj.put("autumn_before_10days", seasonOrderWeek.get("autumn_before_10days"));
		jsonObj.put("autumn_before_9days", seasonOrderWeek.get("autumn_before_9days"));
		jsonObj.put("autumn_before_8days", seasonOrderWeek.get("autumn_before_8days"));
		jsonObj.put("autumn_before_7days", seasonOrderWeek.get("autumn_before_7days"));
		
		jsonObj.put("winter_before_13days", seasonOrderWeek.get("winter_before_13days"));
		jsonObj.put("winter_before_12days", seasonOrderWeek.get("winter_before_12days"));
		jsonObj.put("winter_before_11days", seasonOrderWeek.get("winter_before_11days"));
		jsonObj.put("winter_before_10days", seasonOrderWeek.get("winter_before_10days"));
		jsonObj.put("winter_before_9days", seasonOrderWeek.get("winter_before_9days"));
		jsonObj.put("winter_before_8days", seasonOrderWeek.get("winter_before_8days"));
		jsonObj.put("winter_before_7days", seasonOrderWeek.get("winter_before_7days"));
		
		////////////////////////////////////////////////////////////////////////////////
		
		// 월간 계절별 주문건수
		jsonObj.put("spring_before_6months", seasonOrderMonth.get("spring_before_6months"));
		jsonObj.put("spring_before_5months", seasonOrderMonth.get("spring_before_5months"));
		jsonObj.put("spring_before_4months", seasonOrderMonth.get("spring_before_4months"));
		jsonObj.put("spring_before_3months", seasonOrderMonth.get("spring_before_3months"));
		jsonObj.put("spring_before_2months", seasonOrderMonth.get("spring_before_2months"));
		jsonObj.put("spring_before_1months", seasonOrderMonth.get("spring_before_1months"));
		jsonObj.put("spring_this_month", seasonOrderMonth.get("spring_this_month"));
		
		jsonObj.put("summer_before_6months", seasonOrderMonth.get("summer_before_6months"));
		jsonObj.put("summer_before_5months", seasonOrderMonth.get("summer_before_5months"));
		jsonObj.put("summer_before_4months", seasonOrderMonth.get("summer_before_4months"));
		jsonObj.put("summer_before_3months", seasonOrderMonth.get("summer_before_3months"));
		jsonObj.put("summer_before_2months", seasonOrderMonth.get("summer_before_2months"));
		jsonObj.put("summer_before_1months", seasonOrderMonth.get("summer_before_1months"));
		jsonObj.put("summer_this_month", seasonOrderMonth.get("summer_this_month"));
		
		jsonObj.put("autumn_before_6months", seasonOrderMonth.get("autumn_before_6months"));
		jsonObj.put("autumn_before_5months", seasonOrderMonth.get("autumn_before_5months"));
		jsonObj.put("autumn_before_4months", seasonOrderMonth.get("autumn_before_4months"));
		jsonObj.put("autumn_before_3months", seasonOrderMonth.get("autumn_before_3months"));
		jsonObj.put("autumn_before_2months", seasonOrderMonth.get("autumn_before_2months"));
		jsonObj.put("autumn_before_1months", seasonOrderMonth.get("autumn_before_1months"));
		jsonObj.put("autumn_this_month", seasonOrderMonth.get("autumn_this_month"));
		
		
		jsonObj.put("winter_before_6months", seasonOrderMonth.get("winter_before_6months"));
		jsonObj.put("winter_before_5months", seasonOrderMonth.get("winter_before_5months"));
		jsonObj.put("winter_before_4months", seasonOrderMonth.get("winter_before_4months"));
		jsonObj.put("winter_before_3months", seasonOrderMonth.get("winter_before_3months"));
		jsonObj.put("winter_before_2months", seasonOrderMonth.get("winter_before_2months"));
		jsonObj.put("winter_before_1months", seasonOrderMonth.get("winter_before_1months"));
		jsonObj.put("winter_this_month", seasonOrderMonth.get("winter_this_month"));
		
		
		// 전년도 월간 계절별 주문건수
		jsonObj.put("spring_before_18months", seasonOrderMonth.get("spring_before_18months"));
		jsonObj.put("spring_before_17months", seasonOrderMonth.get("spring_before_17months"));
		jsonObj.put("spring_before_16months", seasonOrderMonth.get("spring_before_16months"));
		jsonObj.put("spring_before_15months", seasonOrderMonth.get("spring_before_15months"));
		jsonObj.put("spring_before_14months", seasonOrderMonth.get("spring_before_14months"));
		jsonObj.put("spring_before_13months", seasonOrderMonth.get("spring_before_13months"));
		jsonObj.put("spring_before_12months", seasonOrderMonth.get("spring_before_12months"));
		
		jsonObj.put("summer_before_18months", seasonOrderMonth.get("summer_before_18months"));
		jsonObj.put("summer_before_17months", seasonOrderMonth.get("summer_before_17months"));
		jsonObj.put("summer_before_16months", seasonOrderMonth.get("summer_before_16months"));
		jsonObj.put("summer_before_15months", seasonOrderMonth.get("summer_before_15months"));
		jsonObj.put("summer_before_14months", seasonOrderMonth.get("summer_before_14months"));
		jsonObj.put("summer_before_13months", seasonOrderMonth.get("summer_before_13months"));
		jsonObj.put("summer_before_12months", seasonOrderMonth.get("summer_before_12months"));

		jsonObj.put("autumn_before_18months", seasonOrderMonth.get("autumn_before_18months"));
		jsonObj.put("autumn_before_17months", seasonOrderMonth.get("autumn_before_17months"));
		jsonObj.put("autumn_before_16months", seasonOrderMonth.get("autumn_before_16months"));
		jsonObj.put("autumn_before_15months", seasonOrderMonth.get("autumn_before_15months"));
		jsonObj.put("autumn_before_14months", seasonOrderMonth.get("autumn_before_14months"));
		jsonObj.put("autumn_before_13months", seasonOrderMonth.get("autumn_before_13months"));
		jsonObj.put("autumn_before_12months", seasonOrderMonth.get("autumn_before_12months"));
		
		jsonObj.put("winter_before_18months", seasonOrderMonth.get("winter_before_18months"));
		jsonObj.put("winter_before_17months", seasonOrderMonth.get("winter_before_17months"));
		jsonObj.put("winter_before_16months", seasonOrderMonth.get("winter_before_16months"));
		jsonObj.put("winter_before_15months", seasonOrderMonth.get("winter_before_15months"));
		jsonObj.put("winter_before_14months", seasonOrderMonth.get("winter_before_14months"));
		jsonObj.put("winter_before_13months", seasonOrderMonth.get("winter_before_13months"));
		jsonObj.put("winter_before_12months", seasonOrderMonth.get("winter_before_12months"));
		
		
		String json = jsonObj.toString();
		
		request.setAttribute("json", json);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/common/jsonview.jsp");
		
	}// end of execute() ------------------------

}
