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

public class ReviewCommentController extends AbstractController {

	private ReviewListDAO revdao = new ReviewListDAO_imple();
	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();
		
		if(!"POST".equalsIgnoreCase(method)) {
				
			System.out.println("겟방식입니다 꺼지쇼");
			 
			super.setRedirect(true);
			super.setViewPage("/WEB-INF/review/reviewList.jsp");
			
			return;
		
		} else {                 
			
			
			HttpSession session = request.getSession();
			MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
			String cuserid = loginuser.getUserid();
		
			
			String review_no = request.getParameter("review_no");
			String comment_Contents = request.getParameter("comment_Contents");
			String commentPwd = request.getParameter("commentPwd");
			int user_no = loginuser.getUser_no();
			
	        		        
	        ReviewListVO cmw = new ReviewListVO();
	        
	        cmw.setUserid(cuserid);
	        cmw.setFk_user_no(user_no);
	        cmw.setReview_no(Integer.parseInt(review_no));
	        cmw.setComment_contents(comment_Contents);
	        cmw.setComment_pwd(commentPwd);
	       	             
	        
	        int result = revdao.commentWrite(cmw);
	        
	  
	        request.setAttribute("review_no", review_no);
	        request.setAttribute("comment_Contents", comment_Contents);
	        request.setAttribute("commentPwd", commentPwd);
                
	        JSONObject jsonObj = new JSONObject();
	        jsonObj.put("result", result);

	        String json = jsonObj.toString();
	        
	        request.setAttribute("json", json);
					
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/common/jsonview.jsp");
					
				
			
		}
		
		
 
	} // end of public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

} // end of public class ReviewCommentController extends AbstractController {
