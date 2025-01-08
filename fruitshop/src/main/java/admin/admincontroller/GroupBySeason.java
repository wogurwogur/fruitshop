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
		
		String json = jsonObj.toString();
		
		request.setAttribute("json", json);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/common/jsonview.jsp");
		
	}// end of execute() ------------------------

}
