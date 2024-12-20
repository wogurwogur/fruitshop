package mypage.updateInfo.controller;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;
import mypage.updateInfo.model.UpdateInfoDAO;
import mypage.updateInfo.model.UpdateInfoDAO_imple;

public class EmailDuplicateCheck extends AbstractController {

	UpdateInfoDAO updatedao = new UpdateInfoDAO_imple();

	MemberDAO mdao = new MemberDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String method = request.getMethod();

		HttpSession session = request.getSession();

		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");

		if (loginuser != null && "post".equalsIgnoreCase(method)) {

			Boolean isExists = false;
			
			String newEmail = request.getParameter("email");

			String oldEmail = loginuser.getEmail();
		
			if(newEmail.equals(oldEmail)) { // 로그인한 이메일과 새 이메일이 같다면 중복이 아닌것으로
				
				isExists = false;
			}
			else {
				isExists = updatedao.emailDuplicateCheck(newEmail);
			}
		
			JSONObject jsonObj = new JSONObject(); 

			jsonObj.put("isExists", isExists); 

			String json = jsonObj.toString();

			request.setAttribute("json", json);

			super.setRedirect(false);
			super.setViewPage("/WEB-INF/common/jsonview.jsp");

		} else {
			super.setRedirect(true);
			super.setViewPage(request.getContextPath() + "/login/login.ddg");
		}

	}

}


	
