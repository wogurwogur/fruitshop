package review.controller;

import java.util.List;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import notice.domain.NoticeVO;
import review.domain.ReviewListVO;
import review.model.ReviewListDAO;
import review.model.ReviewListDAO_imple;

public class ReviewDeleteController extends AbstractController {

		private ReviewListDAO revdao = new ReviewListDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		
		
		
		HttpSession session = request.getSession();		
		MemberVO loginuser = (MemberVO)(session.getAttribute("loginuser"));		
		
		
		String method = request.getMethod();
		
		if("POST".equalsIgnoreCase(method) ) {
			
			String review_no = request.getParameter("review_no");
			
			int n = revdao.reviewDelete(review_no);
			
			if(n == 1) {
					
				List<ReviewListVO> rvo = revdao.reviewListall();
				
				request.setAttribute("rvo", rvo);
				
				
				super.setRedirect(false);
				super.setViewPage("/review/reviewList.ddg");
						
			}

		
		}else {
			
			String message = "관리자만 접근이 가능합니다.";
	        String loc = "javascript:history.back()";
	        
	        request.setAttribute("message", message);
	        request.setAttribute("loc", loc);
	        
	        super.setRedirect(false);
	        super.setViewPage("/WEB-INF/common/msg.jsp");
		}

	}

}
