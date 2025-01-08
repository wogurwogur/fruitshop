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

public class VisitMemberRank extends AbstractController {
	
	private StatisticsDAO sdao = new StatisticsDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// === 회원의 방문횟수 통계를 반환해주는 클래스 === //
		
		// 주소창으로 접근 시 메인페이지로 이동
		String referer = request.getHeader("referer");
		// request.getHeader("referer"); 은 이전 페이지의 URL을 가져오는 것이다.
		
//				System.out.println("~~~~~ 확인용 referer => "+ referer);
		
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
		
		
		
		String searchRange = request.getParameter("searchRange");		// 오름차순 내림차순
		String searchMonth = request.getParameter("searchMonth");		// 개월필터
		
		if (searchRange == "" || searchRange == null) {
			// 입력을 하지 않으면 오름차순 정렬
			searchRange = "DESC";
		}
		
		if (searchMonth == "" || searchMonth == null) {
			// 입력을 하지 않으면 오름차순 정렬
			searchMonth = "0";
		}

		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("searchRange", searchRange);
		paraMap.put("searchMonth", searchMonth);
		
		// 이번달 방문한 회원 순위
		List<Map<String, String>> visitUserRank = sdao.getVisitUserRank(paraMap);
		
		JSONArray jsonArr = new JSONArray();
		for (Map<String, String> map : visitUserRank) {
			JSONObject jsonObj = new JSONObject();
			
			jsonObj.put("user_no", map.get("user_no"));
			jsonObj.put("name", map.get("name"));
			jsonObj.put("tel", map.get("tel"));
			jsonObj.put("email", map.get("email"));
			jsonObj.put("visit_cnt", map.get("visit_cnt"));
			
			jsonArr.put(jsonObj);
		}// end of for() --------------------
		
		
		String json = jsonArr.toString();
		
		
		request.setAttribute("json", json);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/common/jsonview.jsp");
	}// end of execute() -----------------------------

}
