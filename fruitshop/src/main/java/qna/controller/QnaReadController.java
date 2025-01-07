package qna.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import qna.domain.QnaListVO;
import qna.model.*;

public class QnaReadController extends AbstractController {

	
	private QnaListDAO qdao = new QnaListDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
				
		super.goBackURL(request);
		
		String userid = request.getParameter("userid");
		HttpSession session  = request.getSession();
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		String method = request.getMethod();
		
		
		if("GET".equalsIgnoreCase(method)) {
			
			//GET 방식일때			
			
			String qna_no = request.getParameter("qna_no");
			
			// System.out.println("확인용"+qna_no);
			
			QnaListVO qvo = qdao.qnaReadAll(qna_no);												
							
				
				if(loginuser != null && loginuser.getRole() == 1) {
					
					int n = qdao.setViewCount(qna_no);
					
					qvo.setQna_viewcount(qvo.getQna_viewcount()+1);
					
				}
				
			request.setAttribute("qvo", qvo);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/qna/qnaRead.jsp");

		}

		
		
		
			

	}

}
