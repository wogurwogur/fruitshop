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
		
		String notice_no = request.getParameter("notice_no");
		
		NoticeVO noticeDetail = ndao.oneNoticeDetail(notice_no);
		
		request.setAttribute("loginuser", loginuser);
		
		request.setAttribute("noticeDetail", noticeDetail);
		
		super.setViewPage("/WEB-INF/notice/noticeDetail.jsp");

	}

}
