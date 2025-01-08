package admin.admincontroller;

import java.io.File;

import admin.model.AdminDAO;
import admin.model.AdminDAO_imple;
import common.controller.AbstractController;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;

public class MainPageDelete extends AbstractController {

	AdminDAO adao = new AdminDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();
		
		HttpSession session = request.getSession();
		
		ServletContext svlCtx = session.getServletContext();
		String uploadFileDir = svlCtx.getRealPath("/images/index");
		System.out.println("현재 디렉터리: " + uploadFileDir);
		
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		if("POST".equals(method) && loginuser.getRole() == 2) {
			
			String imgno = request.getParameter("imgno");
			String imgfilename = request.getParameter("imgfilename");
			
			int n = 0;
			int u = 0;
			
			
			
			File imgfile1 = new File(request.getContextPath()+"/images/index/"+imgfilename);
			File imgfile2 = new File(uploadFileDir+"/"+imgfilename);
			
			if(imgfile1.exists()) {
				
				if(imgfile1.delete()) {
						
					n = adao.mainPageDelete(imgno);										
				}
				
			}
			
			if(imgfile2.exists()) {
				
				if(imgfile2.delete()) {
					
					u = adao.mainPageDelete(imgno);
									
				}
				
			}
			
			if(n+u != 1) {
				
				
				request.setAttribute("loc", request.getContextPath()+"/admin/pageManagement.ddg");
				request.setAttribute("message","알수 없는 이유로 삭제에 실패하였습니다.");
				
				super.setRedirect(true);
				super.setViewPage("/WEB-INF/common/msg.jsp");
				
			}else {
				
				super.setRedirect(false);
				super.setViewPage("/admin/pageManagement.ddg");
				
			}
			
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
