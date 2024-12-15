package login.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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

		String message = "";
        String loc = "";
		
		if("get".equalsIgnoreCase(request.getMethod())) {

			super.setRedirect(false);
			super.setViewPage("/WEB-INF/login/login.jsp");
		}
		
		if("post".equalsIgnoreCase(request.getMethod())) {
			
			String userid = request.getParameter("userid");
			String passwd = request.getParameter("passwd");
	      
			String clientip = request.getRemoteAddr();		
			
			Map<String, String> paraMap = new HashMap<>(); 
			paraMap.put("userid",userid);
			paraMap.put("passwd",passwd);
			paraMap.put("clientip",clientip);
			
			try {
				MemberVO loginuser = mdao.login(paraMap);
				
				if(loginuser != null) { 
			        	
			        if(loginuser.getIdle() == 0) { 
			        		
			        	message =  "로그인을 한지 1년이 지나서 휴면상태로 되었습니다.";
				        loc = request.getContextPath()+"/index.ddg";
			        		
				        request.setAttribute("message", message);
				        request.setAttribute("loc", loc);
				        
				        super.setRedirect(false);
				        super.setViewPage("/WEB-INF/common/msg.jsp");
				            
				        return;
			        }
			        
			        HttpSession session = request.getSession();
		        	
		        	session.setAttribute("loginuser", loginuser);
  
		        	if(loginuser.isRequirePwdChange()) { 
		        		
		        		message = "비밀번호를 변경하신지 3개월이 지났습니다."; 
		                loc = request.getContextPath()+"/index.ddg";

		                request.setAttribute("message", message);
		                request.setAttribute("loc", loc);
		                
		                super.setRedirect(false); 
		                super.setViewPage("/WEB-INF/common/msg.jsp");
		                
		                return;
			        
		        	}
		        	else { 
		        		super.setRedirect(true);
		                super.setViewPage(request.getContextPath()+"/index.ddg");

		                return;
		        	}
			        
			        	
			    }
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
			} catch (SQLException e) { // SQL 에러
				e.printStackTrace();
				message = "로그인 오류";
				
				// 자바 스크립트를 이용한 이전페이지로 이동하는것
				loc = "javascript:history.back()";
			} 			
		}	
	}
}
