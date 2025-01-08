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


public class QnaEditController extends AbstractController {
	
	private QnaListDAO qdao = new QnaListDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
			String method = request.getMethod(); // "GET" 또는 "POST"
			
			if(!"GET".equalsIgnoreCase(method)) {
		
				
				HttpSession session  = request.getSession();
				MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
	
				String qna_title = request.getParameter("qna_title");
				String qna_contents = request.getParameter("qna_contents");
				int fk_user_no = loginuser.getUser_no();
				String prod_no = request.getParameter("prodNo");
				String qna_no = request.getParameter("qnaNo");


				
				String qna_contents_result = qna_contents.replaceAll("\r\n", "<br>");
	
				QnaListVO qvo = new QnaListVO();
	  	        
				
				qvo.setQna_title(qna_title);
				qvo.setQna_contents(qna_contents_result);
				qvo.setFk_user_no(fk_user_no);
				qvo.setProd_no(Integer.parseInt(prod_no));
				qvo.setQna_no(Integer.parseInt(qna_no));
				
				try {
			         
			         int result = qdao.qnaEdit(qvo);
			         
			         if(result==1) {
			        	
			        	 request.setAttribute("qvo", qvo);
			        
			        	 
			        	 super.setRedirect(false);
			        	 super.setViewPage("/WEB-INF/qna/qnaList.jsp");
			         }
		         
		         } catch(SQLException e) {
		        	 e.printStackTrace();
		        	 
	       	 	 String message = "";
	       	 	 String loc = "javascript:history.back()";// 자바스크립트를 이용한 이전페이지로 이동하는것"
	       	 	 
	       	 	request.setAttribute("message", message);
	       	 	request.setAttribute("loc", loc);
	  	         
	       	 	super.setRedirect(false);
	       	 	super.setViewPage("/WEB-INF/msg.jsp");
		        	 	 		
	
		         }
			
			
			}
			
			else {
				
				
				String prod_no = request.getParameter("prodNo");			
				
				QnaListVO qvo = qdao.productSelect(prod_no);
				
				request.setAttribute("qvo", qvo);
				
				
				
				List<QnaListVO> qproductList = qdao.qproductFind();
				
				request.setAttribute("qproductList", qproductList);			
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/qna/qnaEdit.jsp");
				
			}
		

	

	}

}
