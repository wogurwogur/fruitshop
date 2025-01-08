package review.controller;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import review.domain.ReviewListVO;
import review.model.*;

public class ReviewCommentRegister extends AbstractController {

	private ReviewListDAO revdao = new ReviewListDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
		
		
		String method = request.getMethod();
		
		if("POST".equalsIgnoreCase(method)) {
			// **** POST 방식으로 넘어온 것이라면 **** //

			
			String comment_contents = request.getParameter("comment_contents");
			String cuserid = request.getParameter("userid");
			String review_no = request.getParameter("review_no");
			int user_no = Integer.parseInt(request.getParameter("user_no"));
			
			// **** 크로스 사이트 스크립트 공격에 대응하는 안전한 코드(시큐어 코드) 작성하기 **** // 
			comment_contents = comment_contents.replaceAll("<", "&lt;");
			comment_contents = comment_contents.replaceAll(">", "&gt;");
			
			// 입력한 내용에서 엔터는 <br>로 변환시키기
			comment_contents = comment_contents.replaceAll("\r\n", "<br>");
			
			ReviewListVO commentvo = new ReviewListVO();
			
			commentvo.setFk_user_no(user_no);
			commentvo.setComment_contents(comment_contents);
			commentvo.setCuserid(cuserid);
			commentvo.setReview_no(Integer.parseInt(review_no));
			
			
			int n = 0;
			try {
				n = revdao.commentWrite(commentvo);
			} catch(SQLIntegrityConstraintViolationException e) { // 제약조건에 위배된 경우 (동일한 제품에 대하여 동일한 회원이 제품후기를 2번 쓴 경우 unique 제약에 위배됨)  
				e.printStackTrace();
				n = -1;
			} catch(SQLException e) {
				e.printStackTrace();
			}
			
			
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("n", n);
			
			String json = jsonObj.toString();  // {"n":1} 또는 {"n":-1} 또는 {"n":0}
			
			System.out.println(json);
			
			request.setAttribute("json", json);
			
		//	super.setRedirect(false);
			super.setViewPage("/WEB-INF/common/jsonview.jsp");
			
		}
		
		else {
			// **** POST 방식으로 넘어온 것이 아니라면 **** //
			
			String message = "비정상적인 경로를 통해 들어왔습니다.!!";
			String loc = "javascript:history.back()";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
		}		

	}

}
