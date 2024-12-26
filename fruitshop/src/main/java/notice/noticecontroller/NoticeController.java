package notice.noticecontroller;

import java.util.List;

import org.apache.coyote.Request;
import org.apache.taglibs.standard.tag.el.fmt.RequestEncodingTag;
import notice.model.*;
import notice.domain.*;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;

public class NoticeController extends AbstractController {

	NoticeDAO ndao = new NoticeDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		MemberVO loginuser = (MemberVO)(session.getAttribute("loginuser"));
		
		// 공지사항 글을 모두 가져오는 메소드
		List<NoticeVO> noticeList = ndao.noticeSelectAll();
		
		request.setAttribute("noticeList", noticeList);
		
		super.setViewPage("/WEB-INF/notice/notice.jsp");

	}

}
