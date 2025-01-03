package mypage.recent.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;

public class Recent_Viewproduct extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
    	MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
    	String userid = request.getParameter("userid");
    	String message = "";
    	if(loginuser != null  ) {	// 로그인 했을때
    		
    		super.setRedirect(false);
    		super.setViewPage("/WEB-INF/mypage/recent_Viewproduct.jsp");
    		
    	}
    	else {
    		//	로그인 상태가 아닌 경우
			message = "로그인 후 볼수 있습니다!!";
			String loc = request.getContextPath()+"/login/login.ddg";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/common/msg.jsp");
		}
		

	}

}
