package admin.admincontroller;

import java.io.File;
import java.io.FilenameFilter;
import java.sql.SQLException;
import java.util.List;

import common.controller.AbstractController;
import index.domain.MainVO;
import index.model.MainDAO;
import index.model.MainDAO_imple;
import member.domain.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class PageManagementController extends AbstractController {
	
	private MainDAO maindao = new MainDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		MemberVO loginuser = (MemberVO)(session.getAttribute("loginuser"));
		
		String ctxPath = request.getContextPath();
		
		if(loginuser != null && loginuser.getRole() != 1) {
			
			
			try {
				List<MainVO> imgList = maindao.imgaeSelectAll();
				
				if (imgList.size() > 0) {
					request.setAttribute("imgList", imgList);
				} 
				
				request.setAttribute("imgListLength", imgList.size());
				
				request.setAttribute("adminpage_val", "admin_page_management");
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/admin_page/admin_page.jsp");
				
			} catch (SQLException e) {	// 쿼리문 오류 발생 시
				e.printStackTrace();	
				super.setRedirect(true);	// redirect 시킴
				super.setViewPage(request.getContextPath()+"/error.ddg");
			}
			

			// 로그인을 안한 상태일시
		}else {
			
			String message = "관리자만 접근이 가능합니다.";
	        String loc = request.getContextPath()+"/index.ddg";
	        
	        request.setAttribute("message", message);
	        request.setAttribute("loc", loc);
	        
	        super.setRedirect(false);
	        super.setViewPage("/WEB-INF/common/msg.jsp");
			
		}
		
		
	}

}
