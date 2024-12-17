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

		MemberVO loginuser = (MemberVO) (session.getAttribute("loginuser"));

	
		if(null == loginuser) {
			System.out.println("비정상적인 접근입니다.");
		}else if(loginuser.getRole() == 1){ 
			System.out.println("비정상적인 접근입니다."); 
		}else {
			String ctxPath = request.getContextPath();

			List<MemberVO> member_allList = adao.MemberSelectAll();

			request.setAttribute("member_allList", member_allList);

			request.setAttribute("adminpage_val", "admin_member_management");

			System.out.println(member_allList.size());

			super.setRedirect(false);
			super.setViewPage("/WEB-INF/admin_page/admin_page.jsp");
		}
		

	}

}
