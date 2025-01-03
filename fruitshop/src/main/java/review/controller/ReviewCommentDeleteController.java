package review.controller;


import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import review.domain.ReviewListVO;
import review.model.ReviewListDAO;
import review.model.ReviewListDAO_imple;

public class ReviewCommentDeleteController extends AbstractController {

	private ReviewListDAO revdao = new ReviewListDAO_imple();
	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		HttpSession session = request.getSession();		
		MemberVO loginuser = (MemberVO)(session.getAttribute("loginuser"));	
		
		String method = request.getMethod();
		
		if("POST".equalsIgnoreCase(method) ) {
			
			String comment_no = request.getParameter("comment_no");
			String review_no = request.getParameter("review_no");			
			String commentPwdED = request.getParameter("commentPwdED");
						
			
			
			int n = revdao.commentDelete(comment_no, review_no, commentPwdED);
			
			
			if(n == 1) {
				
												
				super.setRedirect(false);
				super.setViewPage("/review/reviewRead.ddg");
						
			}
			else {
				
				
					
				String message = "수정실패";
		        String loc = request.getContextPath()+"/review/reviewLRead.ddg";
		        
		        request.setAttribute("message", message);
		        request.setAttribute("loc", loc);
		        
		        super.setRedirect(false);
		        super.setViewPage("/WEB-INF/common/msg.jsp");
				
			}
			
		}
	
  
}
	
}
