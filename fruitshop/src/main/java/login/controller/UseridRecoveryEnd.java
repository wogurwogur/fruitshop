package login.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

public class UseridRecoveryEnd extends AbstractController {
	
	private MemberDAO mdao = new MemberDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String userid = request.getParameter("userid");
		
	    String method = request.getMethod(); 
	      
	    if("POST".equalsIgnoreCase(method)) {
	    	
	    	String clientip = request.getRemoteAddr();
	    	
	    	Map<String, String> paraMap = new HashMap<>();
			paraMap.put("userid", userid);
			paraMap.put("clientip", clientip);
	    	
	    	
	    	int n = mdao.useridRecovery(paraMap);
	    	
	    	HttpSession session = request.getSession();

    	    session.removeAttribute("verifyCertification"); 
	    	
    	    request.setAttribute("n", n);

	    }
	    
	    request.setAttribute("userid", userid);
	    request.setAttribute("method", method);
	    
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/login/useridRecoveryEnd.jsp");
		
		
		
	}

}
