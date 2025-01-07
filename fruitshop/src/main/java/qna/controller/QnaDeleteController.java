package qna.controller;

import java.util.List;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import qna.domain.QnaListVO;
import qna.model.*;
import review.domain.ReviewListVO;

public class QnaDeleteController extends AbstractController {
	
	private QnaListDAO qdao = new QnaListDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();	
		
		MemberVO loginuser = (MemberVO)(session.getAttribute("loginuser"));	
				
		String method = request.getMethod();
				
		if("POST".equalsIgnoreCase(method) ) {
			
			String qna_no = request.getParameter("qna_no");				
			
			int n = qdao.qnaDelete(qna_no);

			if(n == 1) {
					
				List<QnaListVO> qvo = qdao.qnaListall();
				
				request.setAttribute("qvo", qvo);
				
				
				
				super.setRedirect(false);
				super.setViewPage("/qna/qnaList.ddg");
						
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


