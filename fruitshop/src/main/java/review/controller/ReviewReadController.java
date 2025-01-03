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

public class ReviewReadController extends AbstractController {

	private ReviewListDAO revdao = new ReviewListDAO_imple();
	
	public ReviewReadController() {

		
	}

			
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		// System.out.println("ReviewReadController 실행됨");
		
		String userid = request.getParameter("userid");
		HttpSession session  = request.getSession();
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
				
		String method = request.getMethod();
		
		if("GET".equalsIgnoreCase(method)) {
			
			//GET 방식일때			
			
			String review_no = request.getParameter("review_no");
			
			// System.out.println("확인용"+review_no);
			
			ReviewListVO rvo = revdao.reviewReadall(review_no);												
			List<ReviewListVO> commentList = revdao.commentListAll(review_no);
			
			if(commentList==null) {
				
				request.setAttribute("rvo", rvo);
								
			}else {
				
				request.setAttribute("rvo", rvo);
				request.setAttribute("commentList", commentList);
			}
						
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/review/reviewRead.jsp");
			
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		/*
		 * else {
		 * 
		 * 
		 * 
		 * super.setRedirect(true); super.setViewPage("/WEB-INF/review/reviewList.jsp");
		 * }
		 */
		
	/*
	 		// 내정보(회원정보)를 수정 하기 위한 전제조건은 먼저 로그인을 해야 하는 것이다.
		if(super.checkLogin(request) ){
			// 로그인을 했으면
			String userid = request.getParameter("userid");
			HttpSession session  = request.getSession();
			MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
			
			if(loginuser.getUserid().equals(userid)) {
				// 로그인한 사용자가 자신의 정보를 수정하는 경우
				// super.setRedirect(false);
				super.setViewPage("/WEB-INF/member/memberEdit.jsp");
			}
			else {
				// 로그인한 사용자가 다른사용자의 정보를 수정하려고 시도하는 경우
				// 로그인을 안 했으면
				String message = "다른사용자의 정보변경은 불가합니다 !!";
				String loc = "javascript:history.back()";
				 
				request.setAttribute("message", message);
				request.setAttribute("loc", loc);
				     
				// super.setRedirect(false);
				super.setViewPage("/WEB-INF/msg.jsp");
			}
			
		}
		else {
			// 로그인을 안 했으면
			String message = "회원정보를 수정하기 위해서는 먼저 로그인을 하세요 !!";
			String loc = "javascript:history.back()";
			 
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			     
			// super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
		}
	 */
		
		
			
	} // end of public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		
			
} // end of public class ReviewListController extends AbstractController {


