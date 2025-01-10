package qna.controller;

import java.sql.SQLException;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import qna.domain.QnaListVO;
import qna.model.QnaListDAO;
import qna.model.QnaListDAO_imple;

public class QnaReplyController extends AbstractController {

	private QnaListDAO qdao = new QnaListDAO_imple();
	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();		
		MemberVO loginuser = (MemberVO)(session.getAttribute("loginuser"));	
		
		// === 로그인 유무 검사하기 === //
	      if( loginuser.getRole() != 2) {	         	    	  
	    	  
	    	 request.setAttribute("message", "로그인이 필요합니다 !!");
	         request.setAttribute("loc", "javascript:history.back()");
	         
	         super.setRedirect(false);
	         super.setViewPage("/WEB-INF/login/login.jsp");
	         
	         return;
	      }
	     
	      String method = request.getMethod(); // "GET" 또는 "POST"
			
			if(!"GET".equalsIgnoreCase(method)) {
					
				int qna_no = Integer.parseInt(request.getParameter("qna_no"));
				int fk_user_no = loginuser.getUser_no();
				int prod_no = Integer.parseInt(request.getParameter("prodNo"));
				String qna_answer  = request.getParameter("qna_answer");
				
				System.out.println(qna_answer + " qna_answer");
				
				String qna_answer_result = qna_answer.replaceAll("\r\n", "<br>");
				
				QnaListVO qnaReply = new QnaListVO();
	  	        
				qnaReply.setQna_answer(qna_answer_result);
				qnaReply.setQna_no(qna_no);
				qnaReply.setFk_user_no(fk_user_no);
				qnaReply.setProd_no(prod_no);
				
							
				try {
			         
			         int result = qdao.qnaReply(qnaReply);
			         
			         if(result==1) {			        	 
			        	System.out.println(result);
			        	request.setAttribute("qna_answer", qna_answer);			        
			        	
			     		super.setRedirect(true);
			     		super.setViewPage(request.getContextPath()+"/qna/qnaList.ddg");
			        	 //request.getContextPath()+"/qna/qnaList.ddg"
			         }
		         
		         } catch(SQLException e) {
		        	 e.printStackTrace();
		        	 
	       	 	String message = "회원가입 실패";
	       	 	String loc = "javascript:history.back()";// 자바스크립트를 이용한 이전페이지로 이동하는것"
	       	 	 
	       	 	request.setAttribute("message", message);
	       	 	request.setAttribute("loc", loc);
	  	         
	       	 	super.setRedirect(false);
	       	 	super.setViewPage("/WEB-INF/msg.jsp");
		        	 	 		

		        }
			
			
				

			}else {
			
			int qna_no = Integer.parseInt(request.getParameter("qna_no"));
			int fk_user_no = loginuser.getUser_no();
			int prod_no = Integer.parseInt(request.getParameter("prodNo"));
			String qna_answer  = request.getParameter("qna_answer");
			
			request.setAttribute("qna_no", qna_no);
			request.setAttribute("fk_user_no", fk_user_no);
			request.setAttribute("prodNo", prod_no);
			request.setAttribute("qna_answer", qna_answer);
			
     		super.setRedirect(false);
     		super.setViewPage("/WEB-INF/qna/qnaReply.jsp");
			}


	}

}
