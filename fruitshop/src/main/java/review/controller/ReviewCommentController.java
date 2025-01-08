package review.controller;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import review.domain.ReviewListVO;
import review.model.ReviewListDAO;
import review.model.ReviewListDAO_imple;

public class ReviewCommentController extends AbstractController {

	private ReviewListDAO revdao = new ReviewListDAO_imple();
	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		String review_no = request.getParameter("review_no"); // 제품번호
		
		List<ReviewListVO> commentList = revdao.commentListAll(review_no);
		
		// System.out.println(commentList);
		
		JSONArray jsArr = new JSONArray(); // []
		
		if(commentList.size() > 0) {
			
			for (ReviewListVO commentvo :commentList) {
				
				JSONObject jsobj = new JSONObject();
				
				jsobj.put("comment_contents", commentvo.getComment_contents());
				jsobj.put("comment_regidate", commentvo.getComment_regidate());
				jsobj.put("userid", commentvo.getCuserid());
				jsobj.put("user_no", commentvo.getFk_user_no());
				jsobj.put("comment_no", commentvo.getComment_no());
				
				jsArr.put(jsobj); // [{"contents:"옷이 너무너무 좋아요", "name":"김성곤","writeDate":"2025-01-07 09-40:50", "review_seq":1, "userid":"ksg6423"}]
				
				
				
			} // end of for
		}
		
		String json = jsArr.toString();  // 문자열 형태로 변환해줌.
	     
		
		
	    request.setAttribute("json", json);
	    // System.out.println(json);
	    //super.setRedirect(false);
	    super.setViewPage("/WEB-INF/common/jsonview.jsp");
		
		
 
	} // end of public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

} // end of public class ReviewCommentController extends AbstractController {
