package login.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;

public class Logout extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

	    HttpSession session = request.getSession();

	    // -------------------------------------------------------------------------------- //
	    // 로그아웃을 하면 시작페이지로 가는 것이 아니라 방금 보았던 그 페이지로 그대로 가기 위한 것임.
	    String goBackURL = (String) session.getAttribute("goBackURL");

	    if(goBackURL != null ) {
	    	goBackURL = request.getContextPath()+goBackURL;

	    }
	    
	    MemberVO loginuser = (MemberVO) session.getAttribute("loginuser"); 
	    String login_userid = loginuser.getUserid();
	    
	    // -------------------------------------------------------------------------------- //
	    
	    session.removeAttribute("loginuser");
	    
	    super.setRedirect(true);
	    
	    if(goBackURL != null) {
	    	super.setViewPage(goBackURL);
	    }
	    else {
	    	super.setViewPage(request.getContextPath() +"/index.ddg");
	    }
	    
	}

}
