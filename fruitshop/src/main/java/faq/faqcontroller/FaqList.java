package faq.faqcontroller;

import java.util.List;
import faq.domain.*;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;
import member.domain.MemberVO;
import notice.model.NoticeDAO;
import notice.model.NoticeDAO_imple;
import faq.model.*;
import faq.domain.*;

public class FaqList extends AbstractController {

	FaqDAO fdao = new FaqDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		MemberVO loginuser = (MemberVO)(session.getAttribute("loginuser"));
		
		request.setAttribute("loginuser", loginuser);
		
		List<FaqVO> faqList = fdao.faqListAll();
		
		request.setAttribute("faqList", faqList);
		
		super.setViewPage("/WEB-INF/faq/faqList.jsp");

	}

}
