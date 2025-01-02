package admin.admincontroller;

import admin.model.AdminDAO;
import admin.model.AdminDAO_imple;
import common.controller.AbstractController;
import index.domain.MainVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;

public class MainPageDetail extends AbstractController {

	AdminDAO adao = new AdminDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		if(loginuser != null && loginuser.getRole() == 2) {
			
			String imgno = request.getParameter("imgno");
			String imgcount = request.getParameter("imgcount");
			
			MainVO mvo = adao.MainPageDetail(imgno);
			
			if(mvo != null) {
				
				request.setAttribute("imgcount", imgcount);
				request.setAttribute("mvo", mvo);
				request.setAttribute("adminpage_val", "MainPageDetail");
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/admin_page/admin_page.jsp");
				
			}
			
		}else {
			String message = "관리자만 접근이 가능합니다.";
	        String loc = "javascript:history.back()";
	        
	        request.setAttribute("message", message);
	        request.setAttribute("loc", loc);
	        
	        super.setRedirect(false);
	        super.setViewPage("/WEB-INF/common/msg.jsp");
		}
		
	}

}
