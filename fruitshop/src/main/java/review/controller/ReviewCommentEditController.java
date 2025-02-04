package review.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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
		
		
		String method = request.getMethod();
		
		if("POST".equalsIgnoreCase(method)) {
			// **** POST 방식으로 넘어온 것이라면 **** //
			
			String comment_no = request.getParameter("comment_no");
			String comment_contents = request.getParameter("comment_contents");
			
			// !!!! 크로스 사이트 스크립트 공격에 대응하는 안전한 코드(시큐어코드) 작성하기 !!!! //
			comment_contents = comment_contents.replaceAll("<", "&lt;");
			comment_contents = comment_contents.replaceAll(">", "&gt;");
		    
		    // 입력한 내용에서 엔터는 <br>로 변환시키기 
			comment_contents = comment_contents.replaceAll("\r\n", "<br>");
			
		    Map<String, String> paraMap = new HashMap<>();
			paraMap.put("comment_no", comment_no);
			paraMap.put("comment_contents", comment_contents);
			
			int n = 0;
			try {
				 n = revdao.commentEdit(paraMap);
			} catch(SQLException e) {
				
			}
			
			JSONObject jsobj = new JSONObject(); // {} 
			jsobj.put("n", n); // {"n":1} 또는 {"n":0}
			
			String json = jsobj.toString(); // 문자열 형태로 변환해줌.
			// "{"n":1}" 또는 "{"n":0}"
			
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
			
			super.setViewPage("/WEB-INF/msg.jsp");
		}

	}

}
