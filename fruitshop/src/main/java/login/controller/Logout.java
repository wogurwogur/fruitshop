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
	    String goBackURL = (String) session.getAttribute("goBackURL");

	    if(goBackURL != null ) {
	    	goBackURL = request.getContextPath()+goBackURL;

	    }
	    
	    MemberVO loginuser = (MemberVO) session.getAttribute("loginuser"); 
	    String login_userid = loginuser.getUserid();
	    
	    // -------------------------------------------------------------------------------- //
	    
	    session.invalidate();
	    
	    super.setRedirect(true);
	    
	    if(goBackURL != null) {
	    	super.setViewPage(goBackURL);
	    }
	    else {
	    	super.setViewPage(request.getContextPath() +"/index.ddg");
	    }
	    
	}

}
