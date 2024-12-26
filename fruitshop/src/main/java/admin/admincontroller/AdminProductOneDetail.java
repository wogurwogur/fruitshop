package admin.admincontroller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import product.model.ProductDAO;
import product.model.ProductDAO_imple;

public class AdminProductOneDetail extends AbstractController {
	
	private ProductDAO prdao = new ProductDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 관리자(admin)로 로그인 했을때만 조회가 가능하도록 한다.
		HttpSession session = request.getSession();
		
		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
		
		if(loginuser != null && "admin".equals(loginuser.getUserid())) {
			// 관리자(admin)로 로그인 했을 경우 
			
			String method = request.getMethod();
			
			if("POST".equalsIgnoreCase(method)) {
				// POST 방식일때
//				String userid = request.getParameter("userid");
//				String goBackURL = request.getParameter("goBackURL");
//				
//				MemberVO mvo = mdao.selectOneMember(userid);
//				
//				request.setAttribute("mvo", mvo);
//				request.setAttribute("goBackURL", goBackURL);
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/admin_page/admin_product_onedetail.jsp");
				
			}
			
		}
		else {
			// 로그인을 안하거나 또는 관리자(admin)가 아닌 사용자로 로그인 했을 경우
		}

	}
}