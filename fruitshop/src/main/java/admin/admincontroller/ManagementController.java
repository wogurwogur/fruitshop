package admin.admincontroller;

import java.util.List;
import java.util.Map;

import admin.model.AdminDAO;
import admin.model.AdminDAO_imple;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;

public class ManagementController extends AbstractController {

	private AdminDAO adao = new AdminDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();

		MemberVO loginuser = (MemberVO)(session.getAttribute("loginuser"));

	
		if(null == loginuser) {
			String message = "관리자만 접근이 가능합니다.";
	        String loc = "javascript:history.back()";
	        
	        request.setAttribute("message", message);
	        request.setAttribute("loc", loc);
	        
	        super.setRedirect(false);
	        super.setViewPage("/WEB-INF/common/msg.jsp");
			
		}else if(loginuser.getRole() == 1){ 
			
			String message = "관리자만 접근이 가능합니다.";
	        String loc = "javascript:history.back()";
	        
	        request.setAttribute("message", message);
	        request.setAttribute("loc", loc);
	        
	        super.setRedirect(false);
	        super.setViewPage("/WEB-INF/common/msg.jsp"); 
			
		}else {
			
			String user_id = loginuser.getUserid();
			String memberlistSort = request.getParameter("memberlistSort");
			
			List<MemberVO> member_allList = adao.MemberSelectAll(user_id);

			request.setAttribute("member_allList", member_allList);

			request.setAttribute("adminpage_val", "admin_member_management");

			super.setRedirect(false);
			super.setViewPage("/WEB-INF/admin_page/admin_page.jsp");
		}
		

	}

}
