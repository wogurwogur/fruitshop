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
    	
    	String referer = request.getHeader("referer");
		// request.getHeader("referer"); 은 이전 페이지의 URL을 가져오는 것이다.
		
		// System.out.println("~~~~ 확인용 referer : " + referer);
		
		if(referer == null) { 
		// referer == null 은 웹브라우저 주소창에 URL을 직접 입력하고 들어온 경우이다.
		   super.setRedirect(true);
		   super.setViewPage(request.getContextPath()+"/");  // 홈화면으로 돌아간다.
		   return;
		}
    	
    	
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
