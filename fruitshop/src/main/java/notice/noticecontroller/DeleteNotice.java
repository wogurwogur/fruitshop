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
			
			JSONArray jsonArr = new JSONArray();
			
			String json = "";
			
			if(n == 1) {
				
				List<NoticeVO> noticeList = ndao.noticeSelectAll();
				
				request.setAttribute("noticeList", noticeList);
				
				if(noticeList.size() > 0) {
					
					for(NoticeVO nvo:noticeList) {
						JSONObject obj = new JSONObject();
						
						obj.put("notice_no", nvo.getNotice_no());
						obj.put("notice_contents", nvo.getNotice_contents());
						obj.put("notice_title", nvo.getNotice_title());
						obj.put("notice_regidate", nvo.getNotice_regidate());
						obj.put("notice_viewcount", nvo.getNotice_viewcount());
						
						jsonArr.put(obj);
						
					}
					
					json = jsonArr.toString();
					
				}
				
				
				
				request.setAttribute("json", json);
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/common/jsonview.jsp");
				
				
			}else {
			
				json = jsonArr.toString();
				request.setAttribute("json", json);
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/common/jsonview.jsp");
			}
			
		}

	}

}
