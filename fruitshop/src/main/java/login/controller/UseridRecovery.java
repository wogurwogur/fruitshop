package login.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

public class UseridRecovery extends AbstractController {
	
	private MemberDAO mdao = new MemberDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		
		String method = request.getMethod(); // "GET" 또는 "POST"

		if ("POST".equalsIgnoreCase(method)) {
			
			String userid = request.getParameter("userid");
			String email = request.getParameter("email");
			
			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("userid", userid);
			paraMap.put("email", email);
			
			boolean isUserExist = mdao.findPwd(paraMap);
			
			boolean sendMailSuccess = false;
			
			if(isUserExist) {
				
				Random rnd = new Random();
				
				String certification_code = ""; // 영소문자 4자 + 숫자 4자
				
				char randchar = ' ';
				
				for(int i=0; i<4; i++) {
					randchar = (char)(rnd.nextInt('z' - 'a' + 1) + 'a');
					certification_code += randchar;
				}
				
				int randint = 0;
				for(int i=0; i<4; i++) {
					randint = rnd.nextInt(10);
					certification_code += randint;
				}
				
				GoogleMail mail = new GoogleMail();
				
				try {
					mail.send_certification_code(email, certification_code);
					sendMailSuccess = true;
					
					HttpSession session = request.getSession();
					session.setAttribute("certification_code", certification_code);
				
				} catch (Exception e) {
					sendMailSuccess = false;
					e.printStackTrace();	
				}
			}
	
			request.setAttribute("isUserExist", isUserExist);
			request.setAttribute("sendMailSuccess", sendMailSuccess);
			request.setAttribute("email", email);
			request.setAttribute("userid", userid);

		} // end of post
	
		request.setAttribute("method", method);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/login/useridRecovery.jsp");
	


	}
}
