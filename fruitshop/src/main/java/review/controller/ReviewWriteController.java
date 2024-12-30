package review.controller;

import java.sql.SQLException;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.domain.MemberVO;
import review.domain.ReviewListVO;
import review.model.ReviewListDAO;
import review.model.ReviewListDAO_imple;

public class ReviewWriteController extends AbstractController {
	
	
	private ReviewListDAO revdao = new ReviewListDAO_imple();
	
	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod(); // "GET" 또는 "POST"
		
		if("GET".equalsIgnoreCase(method)) {
		
			
		
		super.setViewPage("/WEB-INF/review/reviewWrite.jsp");
		
		}
		
		else {
			
			String review_title = request.getParameter("review_title");
			String review_contents = request.getParameter("review_contents");
			 
			ReviewListVO reviewList = new ReviewListVO();
  	        
			reviewList.setReview_title(review_title);
			reviewList.setReview_contents(review_contents);
			
			
			try {
		         
		         int n = revdao.reviewWrite(reviewList);
		         
		         if(n==1) {
		        	
		        	 request.setAttribute("review_title", review_title);
		        	 request.setAttribute("review_contents", review_contents);
		        	 
		        	 super.setRedirect(false);
		        	 super.setViewPage("/WEB-INF/review/reviewList.jsp");
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
		
		
		
		
		
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/review/reviewWrite.jsp");

	}

}