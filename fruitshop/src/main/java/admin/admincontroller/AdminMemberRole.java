package admin.admincontroller;

import admin.model.AdminDAO;
import admin.model.AdminDAO_imple;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;

public class AdminMemberRole extends AbstractController {

	private AdminDAO adao = new AdminDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getContextPath();
		
		HttpSession session = request.getSession();
		
		MemberVO loginuser = (MemberVO)(session.getAttribute("loginuser"));
		
		String role = request.getParameter("role");
		String user_no = request.getParameter("user_no");
		
		
		if(null == loginuser) {
			String message = "관리자만 접근이 가능합니다.";
	        String loc = "/WEB-INF/admin_page/admin_page.jsp";
	        
	        request.setAttribute("message", message);
	        request.setAttribute("loc", loc);
	        
	        super.setRedirect(false);
	        super.setViewPage("/WEB-INF/common/msg.jsp");
			
		}else if(loginuser.getRole() == 1){ 
			
			String message = "관리자만 접근이 가능합니다.";
	        String loc = "/WEB-INF/admin_page/admin_page.jsp";
	        
	        request.setAttribute("message", message);
	        request.setAttribute("loc", loc);
	        
	        super.setRedirect(false);
	        super.setViewPage("/WEB-INF/common/msg.jsp"); 
			
		}else {
			
			int n = adao.roleAddandRemove(role, user_no);
			
			if(n == 1) {
				
				
				MemberVO detailMember = adao.memberDetailInfo(user_no);
				
				request.setAttribute("detailMember", detailMember);
				request.setAttribute("adminpage_val", "admin_member_detail");
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/admin_page/admin_page.jsp");
				
			}else {
				String message = "관리자 설정에 주는데 실패했습니다.";
		        String loc = "javascript:history.back()";
		        
		        request.setAttribute("message", message);
		        request.setAttribute("loc", loc);
		        
		        super.setRedirect(false);
		        super.setViewPage("/WEB-INF/common/msg.jsp");
			}
			
		}
	}

}
