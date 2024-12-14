package admin.admincontroller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DeliveryStatusController extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setAttribute("adminpage_val", "admin_delivery_status");
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/admin_page/admin_page.jsp");

	}

}
