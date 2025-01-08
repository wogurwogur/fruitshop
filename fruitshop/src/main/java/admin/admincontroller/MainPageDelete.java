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
			
			File imgfile = new File(uploadFileDir+"/"+imgfilename);
			
			
			if(imgfile.exists()) {
				
				if(imgfile.delete()) {
					
					n = adao.mainPageDelete(imgno);
									
				}
				
			}else {
				
				adao.mainPageDelete(imgno);
				
			}
			
			if(n != 1) {
				
				
				request.setAttribute("loc", request.getContextPath()+"/admin/pageManagement.ddg");
				request.setAttribute("message","경로에 이미지 파일이 존재하지 않습니다.");
				
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
