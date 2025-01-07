package admin.admincontroller;

import java.util.Map;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import admin.model.*;

public class VisitMember extends AbstractController {

	private StatisticsDAO sdao = new StatisticsDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// === 회원의 방문횟수 통계를 반환해주는 클래스 === //
		
		// 주소창으로 접근 시 메인페이지로 이동
		String referer = request.getHeader("referer");
		// request.getHeader("referer"); 은 이전 페이지의 URL을 가져오는 것이다.
		
//		System.out.println("~~~~~ 확인용 referer => "+ referer);
		
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
		// 최근 일주일 방문자수 통계
		Map<String, Integer> visitUserWeek = sdao.getvisitUserWeek();
		Map<String, Integer> visitUser2Week = sdao.getvisitUser2Week();
		Map<String, Integer> visitUserMonth = sdao.getvisitUserMonth();
		Map<String, Integer> visitUser2Month = sdao.getvisitUser2Month();

		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("before_6days", visitUserWeek.get("before_6days"));
		jsonObj.put("before_5days", visitUserWeek.get("before_5days"));
		jsonObj.put("before_4days", visitUserWeek.get("before_4days"));
		jsonObj.put("before_3days", visitUserWeek.get("before_3days"));
		jsonObj.put("before_2days", visitUserWeek.get("before_2days"));
		jsonObj.put("before_1days", visitUserWeek.get("before_1days"));
		jsonObj.put("today", visitUserWeek.get("today"));
		
		//////////////////////////////////////////////////////////////
		
		jsonObj.put("before_13days", visitUser2Week.get("before_13days"));
		jsonObj.put("before_12days", visitUser2Week.get("before_12days"));
		jsonObj.put("before_11days", visitUser2Week.get("before_11days"));
		jsonObj.put("before_10days", visitUser2Week.get("before_10days"));
		jsonObj.put("before_9days", visitUser2Week.get("before_9days"));
		jsonObj.put("before_8days", visitUser2Week.get("before_8days"));
		jsonObj.put("before_7days", visitUser2Week.get("before_7days"));
		
		//////////////////////////////////////////////////////////////
		
		jsonObj.put("before_6months", visitUserMonth.get("before_6months"));
		jsonObj.put("before_5months", visitUserMonth.get("before_5months"));
		jsonObj.put("before_4months", visitUserMonth.get("before_4months"));
		jsonObj.put("before_3months", visitUserMonth.get("before_3months"));
		jsonObj.put("before_2months", visitUserMonth.get("before_2months"));
		jsonObj.put("before_1months", visitUserMonth.get("before_1months"));
		jsonObj.put("this_month", visitUserMonth.get("this_month"));
		
		//////////////////////////////////////////////////////////////
				
		jsonObj.put("before_18months", visitUser2Month.get("before_18months"));
		jsonObj.put("before_17months", visitUser2Month.get("before_17months"));
		jsonObj.put("before_16months", visitUser2Month.get("before_16months"));
		jsonObj.put("before_15months", visitUser2Month.get("before_15months"));
		jsonObj.put("before_14months", visitUser2Month.get("before_14months"));
		jsonObj.put("before_13months", visitUser2Month.get("before_13months"));
		jsonObj.put("before_12months", visitUser2Month.get("before_12months"));

		String json = jsonObj.toString();
		
		request.setAttribute("json", json);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/common/jsonview.jsp");
		
		
	}// end of execute() ----------------

}
