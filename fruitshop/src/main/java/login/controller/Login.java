package login.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

public class Login extends AbstractController {

	MemberDAO mdao = new MemberDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		boolean isExists = false;
		
		HttpSession session = request.getSession();
		
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		if(loginuser == null ) {

			if ("get".equalsIgnoreCase(request.getMethod())) {
	
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/login/login.jsp");
			}
	
			if ("post".equalsIgnoreCase(request.getMethod())) {
	
				String userid = request.getParameter("userid");
				String passwd = request.getParameter("passwd");
	
				String clientip = request.getRemoteAddr();
	
				Map<String, String> paraMap = new HashMap<>();
				paraMap.put("userid", userid);
				paraMap.put("passwd", passwd);
				paraMap.put("clientip", clientip);
	
				try {
	
					loginuser = mdao.login(paraMap);
	
					JSONObject jsonObj = new JSONObject();
	
					if (loginuser != null) {
						
						isExists = true;
						
						if(loginuser.getIdle() == 0) { // 휴면 계정인경우
						
							String message = "휴면 계정입니다. \\n복구 페이지로 이동합니다.";
							String loc = request.getContextPath()+"/login/useridRecovery.ddg";
							
							request.setAttribute("message", message);
							request.setAttribute("loc", loc);
							
							super.setRedirect(false);
							super.setViewPage("/WEB-INF/common/msg.jsp");
							
						}
						else { 
							session.setAttribute("loginuser", loginuser);
						}
						
						jsonObj.put("requirePwdChange", loginuser.isRequirePwdChange());
						jsonObj.put("idle", loginuser.getIdle());
						jsonObj.put("userid", loginuser.getUserid());
	
					} 
				
					jsonObj.put("isExists", isExists);
					
					String json = jsonObj.toString(); 
					
					request.setAttribute("json", json);
					
					super.setRedirect(false);
					super.setViewPage("/WEB-INF/common/jsonview.jsp");
					
	
				} catch (SQLException e) { // SQL 에러
					e.printStackTrace();
					String message = "로그인 오류";
					String loc = "javascript:history.back()";
					
					request.setAttribute("message", message);
					request.setAttribute("loc", loc);
					
					super.setRedirect(false);
					super.setViewPage("/WEB-INF/common/msg.jsp");
				}
	
			} // post
		}
		else {
			super.setRedirect(true);
			super.setViewPage(request.getContextPath()+"/index.ddg");
		}
	}
}
