package login.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.oracle.wls.shaded.org.apache.xalan.trace.PrintTraceListener;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.jsp.tagext.TryCatchFinally;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

public class PasswdUpdateEnd extends AbstractController {
	
	private MemberDAO mdao = new MemberDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String userid = request.getParameter("userid");
	
	    String method = request.getMethod(); 
	      
	    if("POST".equalsIgnoreCase(method)) {
		
	    	String new_passwd = request.getParameter("passwd");
	    	
	    	Map<String, String> paraMap = new HashMap<>();
	    	paraMap.put("userid", userid);
	    	paraMap.put("new_passwd", new_passwd);
	    	
	    	int n = 0;
	    	try {
	    		n = mdao.pwdUpdate(paraMap);
	    	}
	    	catch (SQLException e) {
	    		e.printStackTrace();
			}
	    	
	    	request.setAttribute("n", n);
	    }
	
			
	    request.setAttribute("userid", userid);
		request.setAttribute("method", method);
	    
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/login/passwdUpdateEnd.jsp");
			
		
	    
		
		
	}

}
