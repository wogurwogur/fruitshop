package admin.admincontroller;

import java.util.List;
import java.util.Map;

import admin.model.AdminDAO;
import admin.model.AdminDAO_imple;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.domain.MemberVO;

public class ManagementController extends AbstractController {

	private AdminDAO adao = new AdminDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String ctxPath = request.getContextPath();
		
		List<MemberVO> member_allList = adao.MemberSelectAll();
		
		request.setAttribute("member_allList", member_allList);
		
		request.setAttribute("adminpage_val", "admin_member_management");
		
		System.out.println(member_allList.size());
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/admin_page/admin_page.jsp");
		
		

	}

}
