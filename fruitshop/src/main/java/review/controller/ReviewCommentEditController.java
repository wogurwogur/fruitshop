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

public class ReviewCommentEditController extends AbstractController {

	
	private ReviewListDAO revdao = new ReviewListDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
		String cuserid = loginuser.getUserid();
	
		String comment_no = request.getParameter("comment_no");
		String review_no = request.getParameter("review_no");
		String comment_Edit_Contents = request.getParameter("comment_Edit_Contents");
		String comment_Edit_Pwd = request.getParameter("comment_Edit_Pwd");
		int user_no = loginuser.getUser_no();
		       		        
        ReviewListVO cmw = new ReviewListVO();
        
        cmw.setUserid(cuserid);
        cmw.setFk_user_no(user_no);
        cmw.setComment_no(Integer.parseInt(comment_no) );
        cmw.setReview_no(Integer.parseInt(review_no));
        cmw.setComment_contents(comment_Edit_Contents);
        cmw.setComment_pwd(comment_Edit_Pwd);
       	             
        
        int result = revdao.commentEdit(cmw); 
        
        System.out.println(result);
  
        request.setAttribute("review_no", review_no);
        request.setAttribute("comment_no", comment_no);
        request.setAttribute("comment_Edit_Contents", comment_Edit_Contents);
        request.setAttribute("comment_Edit_Pwd", comment_Edit_Pwd);
            
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("result", result);

        String json = jsonObj.toString();
        
        // System.out.println(json);
        
        request.setAttribute("json", json);
				
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/common/jsonview.jsp");

	}

}
