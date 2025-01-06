package admin.admincontroller;

import java.util.Map;

import admin.model.StatisticsDAO;
import admin.model.StatisticsDAO_imple;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;

public class AdminStatisticsController extends AbstractController {

	private StatisticsDAO sdao = new StatisticsDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
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
		
		
//		String searchType = request.getParameter("searchType");					// 메뉴필터(방문횟수, 가입자수, 주문횟수, 주문금액)
//		String searchRange = request.getParameter("searchRange");				// 정렬기준 (오름차순, 내림차순)
//		String currentShowPageNo = request.getParameter("currentShowPageNo");	// 페이지 번호
//		String fromDate = request.getParameter("fromDate");						// 시작일
//		String toDate = request.getParameter("toDate");							// 마지막일

		
		// 최근 일주일 가입자수 통계
		Map<String, Integer> visitUserWeek = sdao.getvisitUserWeek();
		
		// 2주전 가입자 수 통계
		Map<String, Integer> visitUser2Week = sdao.getvisitUser2Week();
//		Map<String, Integer> visitUserMonth = sdao.getvisitUserMonth();
		
		
		request.setAttribute("adminpage_val", "admin_statistics");
		request.setAttribute("visitUserWeek", visitUserWeek);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/admin_page/admin_page.jsp");

	}

}
