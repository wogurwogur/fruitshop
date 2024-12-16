package member.controller;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

public class EmailDuplicateCheck extends AbstractController {
	
	MemberDAO mdao = new MemberDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String method = request.getMethod();
		
		if("post".equalsIgnoreCase(method)) {
			
			String email = request.getParameter("email");
			
			Boolean isExists = mdao.emailDuplicateCheck(email);
			
			JSONObject jsonObj = new JSONObject(); // 자바스크립트에 객체{} 만든것 의미
			
			jsonObj.put("isExists", isExists); // {"isExists":isExists}
			
			String json = jsonObj.toString(); // 문자열 형태인 "{"isExists":isExists}" 로 만들기
			
			request.setAttribute("json", json);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/common/jsonview.jsp");
		
	}

}
	
}

	
