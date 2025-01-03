package review.controller;

import java.sql.SQLException;
import java.util.List;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import review.domain.ReviewListVO;
import review.model.ReviewListDAO;
import review.model.ReviewListDAO_imple;

public class ReviewWriteController extends AbstractController {
	
	
	private ReviewListDAO revdao = new ReviewListDAO_imple();
	
	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();		
		MemberVO loginuser = (MemberVO)(session.getAttribute("loginuser"));	
		
		// === 로그인 유무 검사하기 === //
	      if( loginuser == null) {
	         	    	  
	    	  
	    	 request.setAttribute("message", "로그인이 필요합니다 !!");
	         request.setAttribute("loc", "javascript:history.back()");
	         
	         super.setRedirect(false);
	         super.setViewPage("/WEB-INF/login/login.jsp");
	         
	         return;
	      }

		
		String method = request.getMethod(); // "GET" 또는 "POST"
		
		if(!"GET".equalsIgnoreCase(method)) {
		
	
			
			String review_title = request.getParameter("review_title");
			String review_contents = request.getParameter("review_contents");
			int fk_user_no = loginuser.getUser_no();
			int prod_no = Integer.parseInt(request.getParameter("prodNo"));
					
			
			String review_contents_result = review_contents.replaceAll("\r\n", "<br>");
			
			ReviewListVO reviewList = new ReviewListVO();
  	        
		
			reviewList.setReview_title(review_title);
			reviewList.setReview_contents(review_contents_result);
			reviewList.setFk_user_no(fk_user_no);
			reviewList.setProd_no(prod_no);
			
						
			try {
		         
		         int result = revdao.reviewWrite(reviewList);
		         
		         if(result==1) {
		        	
		        	 request.setAttribute("reviewList", reviewList);
		        
		        	 
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
		
		else {
			
			List<ReviewListVO> rproductList = revdao.rproductFind();
			
			request.setAttribute("rproductList", rproductList);
			
			

			super.setViewPage("/WEB-INF/review/reviewWrite.jsp");
			
		}
		
		
		
		
		
		
		
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/review/reviewWrite.jsp");

	}

}