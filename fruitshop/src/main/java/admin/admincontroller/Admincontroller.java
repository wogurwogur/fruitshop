package admin.admincontroller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Admincontroller extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		super.setViewPage(request.getContextPath()+"/WEB-INF/admin_page/admin_page.jsp");

	}

}
