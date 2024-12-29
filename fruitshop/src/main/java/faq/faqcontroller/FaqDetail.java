package faq.faqcontroller;

import common.controller.AbstractController;
import faq.domain.FaqVO;
import faq.model.FaqDAO;
import faq.model.FaqDAO_imple;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import notice.domain.NoticeVO;

public class FaqDetail extends AbstractController {

	FaqDAO fdao = new FaqDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		MemberVO loginuser = (MemberVO)(session.getAttribute("loginuser"));
		
		String faq_no = request.getParameter("faq_no_detail");
		
		String method = request.getContextPath();
			
		FaqVO faqDetail = fdao.oneFaqDetail(faq_no);
		
		if(faqDetail != null) {
			
			if(loginuser != null && loginuser.getRole() == 1) {
				
				int n = fdao.setViewCountFaq(faq_no);
				
				faqDetail.setFaq_viewcount(faqDetail.getFaq_viewcount()+1);
				
			}
			
		}
		
		
		
		System.out.println(faqDetail.getFaq_title());
		
		request.setAttribute("loginuser", loginuser);
		
		faqDetail.setFaq_contents(faqDetail.getFaq_contents().replaceAll("\r\n", "<br>"));
		
		request.setAttribute("faqDetail", faqDetail);
		
		super.setViewPage("/WEB-INF/faq/faqDetail.jsp");

	}

}
