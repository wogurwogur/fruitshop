package login.controller;

import java.util.HashMap;
import java.util.Map;



import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

public class PasswdFind extends AbstractController {
	
	private MemberDAO mdao = new MemberDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		
		if("get".equalsIgnoreCase(request.getMethod())) {

			super.setRedirect(false);
			super.setViewPage("/WEB-INF/login/PasswdFind.jsp");
		}
		

		if ("POST".equalsIgnoreCase(request.getMethod())) {
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/login/PasswdFind.jsp");
		
		} // end of post 방식
	


	}
}
