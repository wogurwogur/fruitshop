package login.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

public class VerifyCertification extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();

		if ("POST".equalsIgnoreCase(method)) {
			
			String userCertificationCode = request.getParameter("userCertificationCode");
			String userid = request.getParameter("userid");
			
			
			HttpSession session = request.getSession();
			String certification_code = (String)session.getAttribute("certification_code");
			
			String message = "";
			String loc = "";
			
			if(certification_code.equals(userCertificationCode)) {
				
				message = "인증성공 되었습니다. \\n비밀번호를 설정하세요.";
				loc = request.getContextPath() + "/login/passwdUpdateEnd.ddg?userid=" + userid;
				
			}
			else {
				message = "인증실패 하였습니다. \\n다시 시도해주세요.";
				loc = request.getContextPath() + "/login/passwdFind.ddg";
			}
			
			session.removeAttribute("certification_code");
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			super.setRedirect(false); 
			super.setViewPage("/WEB-INF/common/msg.jsp");
			
			
		}
		
		
		
		
	
		
		
		
		
		
		
		
		
		
		
	}

}
