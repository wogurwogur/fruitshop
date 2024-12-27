package notice.noticecontroller;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import notice.domain.NoticeVO;
import notice.model.NoticeDAO;
import notice.model.NoticeDAO_imple;

public class DeleteNotice extends AbstractController {

	NoticeDAO ndao = new NoticeDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		MemberVO loginuser = (MemberVO)(session.getAttribute("loginuser"));
		
		String method = request.getMethod();
		
		if("POST".equals(method) && loginuser.getRole() == 2) {
			
			String notice_no = request.getParameter("notice_no");
			
			int n = ndao.deleteNotice(notice_no);
			
			if(n == 1) {
					
				List<NoticeVO> noticeList = ndao.noticeSelectAll();
				
				request.setAttribute("noticeList", noticeList);
				
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/notice/notice.jsp");
						
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
