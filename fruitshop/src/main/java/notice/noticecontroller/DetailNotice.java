package notice.noticecontroller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import notice.domain.NoticeVO;
import notice.model.NoticeDAO;
import notice.model.NoticeDAO_imple;

public class DetailNotice extends AbstractController {

	NoticeDAO ndao = new NoticeDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		MemberVO loginuser = (MemberVO)(session.getAttribute("loginuser"));
		
		String notice_no = request.getParameter("notice_no_detail");
		
		String method = request.getContextPath();
			
		NoticeVO noticeDetail = ndao.oneNoticeDetail(notice_no);
		
		if(noticeDetail != null) {
			
			if(loginuser != null && loginuser.getRole() == 1) {
				
				int n = ndao.setViewCount(notice_no);
				
				noticeDetail.setNotice_viewcount(noticeDetail.getNotice_viewcount()+1);
				
			}
			
		}
		
		
		
		System.out.println(noticeDetail.getNotice_title());
		
		request.setAttribute("loginuser", loginuser);
		
		noticeDetail.setNotice_contents(noticeDetail.getNotice_contents().replaceAll("\r\n", "<br>"));
		
		request.setAttribute("noticeDetail", noticeDetail);
		
		super.setViewPage("/WEB-INF/notice/noticeDetail.jsp");
		
		

	}

}