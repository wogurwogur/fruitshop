package notice.noticecontroller;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import notice.domain.NoticeVO;
import notice.model.NoticeDAO;
import notice.model.NoticeDAO_imple;

public class UpdateNotice extends AbstractController {

	NoticeDAO ndao = new NoticeDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		String method = request.getMethod();
		
		if("POST".equals(method) && loginuser.getRole() == 2) {
			
			String notice_no = request.getParameter("notice_no");
			String notice_title = request.getParameter("notice_title");
			String notice_contents = request.getParameter("notice_contents");
			
			Map<String,String> map = new HashMap<>();
			
			map.put("notice_no", notice_no);
			map.put("notice_title", notice_title);
			map.put("notice_contents", notice_contents);
			
			int n = ndao.updateNotice(map);
			
			JSONObject json = new JSONObject();
			
			json.put("n", n);
			json.put("notice_no", notice_no);
			
			request.setAttribute("json", json.toString());
			
			super.setViewPage("/WEB-INF/common/jsonview.jsp");
			
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
