package faq.faqcontroller;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import common.controller.AbstractController;
import faq.domain.FaqVO;
import faq.model.FaqDAO;
import faq.model.FaqDAO_imple;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import notice.domain.NoticeVO;

public class Updatefaq extends AbstractController {

	FaqDAO fdao = new FaqDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		String method = request.getMethod();
		
		if("POST".equals(method) && loginuser.getRole() == 2) {
			
			String faq_no = request.getParameter("faq_no");
			String faq_title = request.getParameter("faq_title");
			String faq_contents = request.getParameter("faq_contents");
			
			Map<String,String> map = new HashMap<>();
			
			map.put("faq_no", faq_no);
			map.put("faq_title", faq_title);
			map.put("faq_contents", faq_contents);
			
			int n = fdao.updateFaq(map);
			
			JSONObject json = new JSONObject();
			
			json.put("n", n);
			json.put("faq_no", faq_no);
			
			request.setAttribute("json", json.toString());
			
			super.setViewPage("/WEB-INF/common/jsonview.jsp");
			
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
