package mypage.updateInfo.controller;

import java.sql.SQLException;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import mypage.updateInfo.model.UpdateInfoDAO;
import mypage.updateInfo.model.UpdateInfoDAO_imple;

public class MemberWithdrawal extends AbstractController {
	
	UpdateInfoDAO updatedao = new UpdateInfoDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		if(loginuser != null) {
			
			if("get".equalsIgnoreCase(request.getMethod())) {
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/mypage/updateInfo.jsp");
			} 
			else if("post".equalsIgnoreCase(request.getMethod())) {
				
				int user_no = loginuser.getUser_no();
				
				try {
					int n = updatedao.memberWithdrawal(user_no);
					
					if(n == 1) {
						
						session.removeAttribute("loginuser");
					    
					    super.setRedirect(true);
					    super.setViewPage(request.getContextPath()+"/index.ddg");
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
					String message = "회원 탈퇴 실패";
					String loc = request.getContextPath()+"/mypage/updateInfo.ddg";
					
					request.setAttribute("message", message);
				    request.setAttribute("loc", loc);
				        
				    super.setRedirect(false);
				    super.setViewPage("/WEB-INF/common/msg.jsp");
				}
				
				
				
				
			}
			
		}
		
	}

}
