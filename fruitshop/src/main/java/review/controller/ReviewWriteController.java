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
	    	  
	    	  request.setAttribute("message", "돌아가");
	         
	         super.setRedirect(true);
	         super.setViewPage("/fruitshop/login/login.ddg");
	         
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
			
		         
		    int result = revdao.reviewWrite(reviewList);
		         
		         
		    if(result==1) {
		        
		    	System.out.println(result);
	        	 request.setAttribute("reviewList", reviewList);
      	 
	        	 super.setRedirect(false);
	        	 super.setViewPage("/review/reviewList.ddg");	        	 	        	 
		    }
		


		}else {
			/*
			List<ReviewListVO> rproductList = revdao.rproductFind();
			request.setAttribute("rproductList", rproductList);
			*/
			// String prod_no = request.getParameter("prodNo");	
			int fk_user_no = loginuser.getUser_no();
			// System.out.println("prod_no : "+ prod_no);
			System.out.println("fk_user_no :"+ fk_user_no);
			
			
			List<ReviewListVO> orproductList = revdao.orproductList(fk_user_no);
			System.out.println(orproductList);
			request.setAttribute("orproductList", orproductList);
			
			System.out.println("ㅇㄷ2");
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/review/reviewWrite.jsp");

		
		}
		
		/*				
		if(rproductList != null) {
				
			request.setAttribute("rproductList", rproductList);
			super.setRedirect(true);
			super.setViewPage("/WEB-INF/review/reviewList.jsp");
			}
		 */
		/*
		System.out.println("ㅇㄷ3");
		
		List<ReviewListVO> rproductList = revdao.rproductFind();
		request.setAttribute("rproductList", rproductList);
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/review/reviewWrite.jsp");
		*/
		
		
		

	
	}
}