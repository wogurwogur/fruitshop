package qna.controller;

import java.sql.SQLException;
import java.util.List;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import qna.domain.QnaListVO;
import qna.model.*;


public class QnaWriteController extends AbstractController {
	
	
	private QnaListDAO qdao = new QnaListDAO_imple();
	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();		
		MemberVO loginuser = (MemberVO)(session.getAttribute("loginuser"));	
		
		// === 로그인 유무 검사하기 === //
	      /*if( loginuser == null) {	         	    	  
	    	  
	    	 request.setAttribute("message", "로그인이 필요합니다 !!");
	         request.setAttribute("loc", "javascript:history.back()");
	         
	         super.setRedirect(false);
	         super.setViewPage("/WEB-INF/login/login.jsp");
	         
	         return;
	      }*/

		
		String method = request.getMethod(); // "GET" 또는 "POST"
		
		if(!"GET".equalsIgnoreCase(method)) {
		
				
			String qna_title = request.getParameter("qna_title");
			String qna_contents = request.getParameter("qna_contents");
			int fk_user_no = loginuser.getUser_no();
			int prod_no = Integer.parseInt(request.getParameter("prodNo"));
					
			
			String qna_contents_result = qna_contents.replaceAll("\r\n", "<br>");
			
			QnaListVO qnaWrite = new QnaListVO();
  	        
		
			qnaWrite.setQna_title(qna_title);
			qnaWrite.setQna_contents(qna_contents_result);
			qnaWrite.setFk_user_no(fk_user_no);
			qnaWrite.setProd_no(prod_no);
			
						
			try {
		         
		         int result = qdao.qnaWrite(qnaWrite);
		         
		         System.out.println(result);
		         
		         if(result==1) {		        	
		        	 request.setAttribute("qnaWrite", qnaWrite);
		        
		        	 
		        	 super.setRedirect(false);
		        	 super.setViewPage("/WEB-INF/qna/qnaList.jsp");
		        	 
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
		
		
		}
		
		else {
			
			List<QnaListVO> qproductList = qdao.qproductFind();			
			request.setAttribute("qproductList", qproductList);
			super.setViewPage("/WEB-INF/qna/qnaWrite.jsp");
			
		}
		


	}

}
