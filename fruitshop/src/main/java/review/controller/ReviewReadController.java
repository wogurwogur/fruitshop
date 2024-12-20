package review.controller;

import java.sql.SQLException;
import java.util.List;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
		
		String method = request.getMethod();
		
		if("POST".equalsIgnoreCase(method)) {
			
			//POST 방식일때			
			
			String review_no = request.getParameter("review_no");
			
			// System.out.println("확인용"+review_no);
			
			ReviewListVO rvo = revdao.reviewReadall(review_no);
			
			request.setAttribute("rvo", rvo);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/review/reviewRead.jsp");
		}
		
	
			
	} // end of public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		
			
} // end of public class ReviewListController extends AbstractController {


