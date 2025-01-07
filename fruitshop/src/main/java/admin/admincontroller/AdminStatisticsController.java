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
		
		
		// 최근 일주일 가입자수 통계
		Map<String, Integer> regiUserWeek = sdao.getregiUserWeek();
		
		// 2주전 가입자 수 통계
		Map<String, Integer> regiUser2Week = sdao.getregiUser2Week();
		
		// 최근 6개월 가입자 수 통계
		Map<String, Integer> regiUserMonth = sdao.getregiUserMonth();
		
		// 전년 동월 가입자수 통계
		Map<String, Integer> regiUserMonthAgo = sdao.getregiUserMonthAgo();
		
		
		request.setAttribute("adminpage_val", "admin_statistics");
		request.setAttribute("regiUserWeek", regiUserWeek);
		request.setAttribute("regiUser2Week", regiUser2Week);
		request.setAttribute("regiUserMonth", regiUserMonth);
		request.setAttribute("regiUserMonthAgo", regiUserMonthAgo);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/admin_page/admin_page.jsp");

	}

}
