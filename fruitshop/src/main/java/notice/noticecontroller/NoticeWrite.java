package notice.noticecontroller;

import java.util.List;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import notice.domain.NoticeVO;
import notice.model.NoticeDAO;
import notice.model.NoticeDAO_imple;

public class NoticeWrite extends AbstractController {

	NoticeDAO ndao = new NoticeDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		MemberVO loginuser = (MemberVO)(session.getAttribute("loginuser"));
		
		String method = request.getMethod();
		
		if(loginuser.getRole() == 2 && "GET".equals(method)) {
			
			 	
			super.setRedirect(false);
	        super.setViewPage("/WEB-INF/notice/noticeWrite.jsp");
			
		}else if(loginuser.getRole() == 2 && "POST".equals(method)){
			
			String notice_title = request.getParameter("notice_title");
			String notice_contents = request.getParameter("notice_contents");
			
			System.out.println(notice_title);
			
			int n = ndao.noticeInsert(notice_title, notice_contents);
			
			if(n == 1) {
				
				
				super.setViewPage("/notice/noticeList.ddg");
				
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
