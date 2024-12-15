package admin.admincontroller;

import common.controller.AbstractController;

import java.util.List;
import java.util.Map;

import admin.model.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.domain.MemberVO;

public class AdminMemberlistController extends AbstractController {

	private AdminDAO adao = new AdminDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		List<MemberVO> memberList = adao.MemberSelectAll();
		
		request.setAttribute("memberList", memberList);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/admin_page/admin_page.jsp");

	}

}
