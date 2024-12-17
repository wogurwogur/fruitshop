package admin.admincontroller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AdminMemberDetail extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();
		if("POST".equals(method)) {
			
			
			
		}else {
			
			super.setRedirect(false);
			super.setViewPage(request.getContextPath()+"/WEB-INF/admin_page/admin_member_detail.jsp");
			
		}
		

	}

}
