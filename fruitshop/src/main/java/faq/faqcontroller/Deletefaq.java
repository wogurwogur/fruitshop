package faq.faqcontroller;

import java.util.List;

import common.controller.AbstractController;
import faq.domain.FaqVO;
import faq.model.FaqDAO;
import faq.model.FaqDAO_imple;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import notice.domain.NoticeVO;

public class Deletefaq extends AbstractController {

	FaqDAO fdao = new FaqDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		MemberVO loginuser = (MemberVO)(session.getAttribute("loginuser"));
		
		String method = request.getMethod();
		
		if("POST".equals(method) && loginuser.getRole() == 2) {
			
			String faq_no = request.getParameter("faq_no");
			
			int n = fdao.deleteFaq(faq_no);
			
			if(n == 1) {
					
				List<FaqVO> faqList = fdao.faqListAll();
				
				request.setAttribute("faqList", faqList);
				
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/faq/faqList.jsp");
						
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
