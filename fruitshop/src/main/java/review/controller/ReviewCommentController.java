package review.controller;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.domain.MemberVO;
import review.domain.ReviewListVO;
import review.model.ReviewListDAO;
import review.model.ReviewListDAO_imple;

public class ReviewCommentController extends AbstractController {

	private ReviewListDAO revdao = new ReviewListDAO_imple();
	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();
		
		if("GET".equalsIgnoreCase(method)) {
					
			super.setRedirect(true);
			super.setViewPage("/WEB-INF/review/reviewList.jsp");
		
		}else {
		
			String cuserid = request.getParameter("fk_userid");
			String creview_no = request.getParameter("fk_review_no");
			String comment_contents = request.getParameter("comment_contents");
	        String comment_pwd = request.getParameter("comment_pwd");
	        String comment_regidate = request.getParameter("comment_regidate");
	        
	        ReviewListVO cmw = new ReviewListVO(); // 객체 구현
		        
	        cmw.setUserid(cuserid);
	        cmw.setReview_no(Integer.parseInt(creview_no));
	        cmw.setComment_contents(comment_contents);
	        cmw.setComment_pwd(comment_pwd);
	        cmw.setComment_regidate(comment_regidate);
	        
	        
	        
	        int n = revdao.commentWrite(cmw);
                
				if(n == 1) {
					
					request.setAttribute("cmw", cmw);
					
					super.setRedirect(false);
					super.setViewPage("/WEB-INF/review/reviewRead.jsp");
					
					JSONObject jsonObj = new JSONObject();  // {}
					jsonObj.put("result", result);
					
					String json = jsonObj.toString(); // 문자열로 변환 
					request.setAttribute("json", json);
					
				}
				else {
					
					super.setRedirect(false);
					super.setViewPage("/WEB-INF/review/reviewList.jsp");
				}
			
		}
		
		
 
	} // end of public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

} // end of public class ReviewCommentController extends AbstractController {
