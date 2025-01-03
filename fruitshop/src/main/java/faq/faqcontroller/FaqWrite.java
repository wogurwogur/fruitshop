package faq.faqcontroller;

import common.controller.AbstractController;
import faq.model.FaqDAO;
import faq.model.FaqDAO_imple;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;

public class FaqWrite extends AbstractController {

	FaqDAO fdao = new FaqDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		String method = request.getMethod();
		
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		if(loginuser.getRole() == 2 && "POST".equals(method)) {
			
			String faq_title = request.getParameter("faq_title");
			String faq_contents = request.getParameter("faq_contents");
			
			
			
			int n = fdao.FaqWrite(faq_title, faq_contents);
			
			if(n == 1) {
				
				System.out.println("sql문 성공");
				
				super.setRedirect(false);
				super.setViewPage("/faq/faqList.ddg");
				
			}else {
				System.out.println("sql문 실패");
			}
			
			
			
		}else if(loginuser.getRole() == 2 && "GET".equals(method)) {
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/faq/faqWrite.jsp");
			
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
