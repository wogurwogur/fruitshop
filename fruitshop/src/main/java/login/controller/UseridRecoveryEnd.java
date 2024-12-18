package login.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

public class UseridRecoveryEnd extends AbstractController {
	
	private MemberDAO mdao = new MemberDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String userid = request.getParameter("userid");
		
	    String method = request.getMethod(); 
	      
	    if("POST".equalsIgnoreCase(method)) {
	    	
	    	int n = mdao.useridRecovery(userid);
	    	
	    	System.out.println("n : " + n);
	    	
	    	if(n==1) {
	    		
	    		String message = "휴면계정을 복구하였습니다.";
				String loc = request.getContextPath() + "/login/login.ddg";
	    		request.setAttribute("message", message);
				request.setAttribute("loc", loc);
				super.setRedirect(false); 
				super.setViewPage("/WEB-INF/common/msg.jsp");
				
				return;
	    	}
	    	else {
	    		String message = "휴면계정 복구 실패.";
				String loc = request.getContextPath() + "index.ddg";
	    		request.setAttribute("message", message);
				request.setAttribute("loc", loc);
				super.setRedirect(false); 
				super.setViewPage("/WEB-INF/common/msg.jsp");
				
				return;
	    	}
	    	
	    }
			
	    request.setAttribute("userid", userid);
	    
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/login/useridRecoveryEnd.jsp");
		
		
		
	}

}
