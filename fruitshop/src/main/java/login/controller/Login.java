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
				boolean isExistUser = mdao.isExistUser(userid);
				
				if(isExistUser) { //존재하는 아이디인 경우
					
					
					MemberVO loginuser = mdao.login(paraMap);
					
					if(loginuser != null) { // 비밀번호가 맞을때
				        	
				        if(loginuser.getIdle() == 0) { 
				        		
				        	message = "로그인을 한지 1년이 지나서 휴면상태로 되었습니다.";
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
					else { // 비밀번호가 틀렸을때
					      
						message = "비밀번호가 틀렸습니다.";
			            loc = "javascript:history.back()";
			            
			            request.setAttribute("message", message);
			            request.setAttribute("loc", loc);
			            
			            super.setRedirect(false); 
			            super.setViewPage("/WEB-INF/common/msg.jsp");
			        }
					
				}
				else { // 존재하지 않는 아이디 일때
					
					message = "존재하지않는 아이디입니다.";
		            loc = "javascript:history.back()";
		            
		            request.setAttribute("message", message);
		            request.setAttribute("loc", loc);
		            
		            super.setRedirect(false); 
		            super.setViewPage("/WEB-INF/common/msg.jsp");
				}
				
				
			} catch (SQLException e) { // SQL 에러
				e.printStackTrace();
				message = "로그인 오류";
				
				loc = "javascript:history.back()";
				super.setRedirect(false); 
                super.setViewPage("/WEB-INF/common/msg.jsp");
			} 		
			
			
		} // post
	}
}
