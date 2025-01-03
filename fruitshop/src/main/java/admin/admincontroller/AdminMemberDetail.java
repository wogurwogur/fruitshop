package admin.admincontroller;

import admin.model.AdminDAO;
import admin.model.AdminDAO_imple;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.domain.MemberVO;

public class AdminMemberDetail extends AbstractController {

	private AdminDAO adao = new AdminDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		String method = request.getMethod();
		
		if("POST".equals(method)) {
			
			String detail_user_no = request.getParameter("detail_user_no");
			
			System.out.println("admindetail detail_user_no=>" + detail_user_no);
			
			MemberVO detailMember = adao.memberDetailInfo(detail_user_no);
			
			String memberCoupon = adao.memberCouponCnt(detail_user_no);
			
			request.setAttribute("memberCoupon", memberCoupon);
			
			request.setAttribute("detail_user_no", detail_user_no);
			
			request.setAttribute("detailMember", detailMember);
			
			request.setAttribute("adminpage_val", "admin_member_detail");
			
			super.setRedirect(false);
			super.setViewPage("/admin/pageManagement.ddg");
			
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
