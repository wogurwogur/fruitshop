package mypage.index.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import common.controller.AbstractController;
import coupon.domain.CouponVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;
import mypage.index.model.MypageIndexDAO;
import mypage.index.model.MypageIndexDAO_imple;

public class MypageIndex extends AbstractController {

	MemberDAO mdao = new MemberDAO_imple();
	MypageIndexDAO mpidao = new MypageIndexDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		if(loginuser != null ) {
			
			int user_no = loginuser.getUser_no();
			
			List<CouponVO> couponList = new ArrayList<>();
			couponList = mpidao.couponInfo(user_no);
			
			request.setAttribute("couponList", couponList);
			request.setAttribute("couponList_length", couponList.size());
			
			
			Map<String, String> shipStatus_count = mpidao.shipStatus(user_no);
			
			String cnt_1 = shipStatus_count.get("cnt_1");
			String cnt_2 = shipStatus_count.get("cnt_2");
			String cnt_3 = shipStatus_count.get("cnt_3");
			
			request.setAttribute("cnt_1", cnt_1);
			request.setAttribute("cnt_2", cnt_2);
			request.setAttribute("cnt_3", cnt_3);
			
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/mypage/mypageIndex.jsp");
			
		}
		else {
			super.setRedirect(true);
			super.setViewPage(request.getContextPath()+"/index.ddg");
		}
		

		
	}

}
