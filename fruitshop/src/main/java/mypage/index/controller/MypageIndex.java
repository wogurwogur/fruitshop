package mypage.index.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

public class MypageIndex extends AbstractController {

	MemberDAO mdao = new MemberDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		if(loginuser != null ) {
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/mypage/mypageIndex.jsp");
			
		}
		else {
			super.setRedirect(true);
			super.setViewPage(request.getContextPath()+"/index.ddg");
		}
		
		
		
		
		
		
	}

}
