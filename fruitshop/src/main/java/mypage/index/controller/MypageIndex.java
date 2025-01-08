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
		
		if(loginuser != null && loginuser.getRole() == 1 ) {
			
			super.goBackURL(request);

			int user_no = loginuser.getUser_no();
			
			List<CouponVO> couponList = new ArrayList<>();
			couponList = mpidao.couponInfo(user_no);
			
			request.setAttribute("couponList", couponList);
			request.setAttribute("couponList_cnt", couponList.size());
			request.setAttribute("mypage_val", "mypageIndex");
			
			List<Map<String, Integer>> shipStatus_count = mpidao.shipStatus(user_no);
			
			int cnt_1 = 0;
			int cnt_2 = 0;
			int cnt_3 = 0;
			
			for(int i=0; i<shipStatus_count.size(); i++) {
				
				int ship_status = shipStatus_count.get(i).get("ship_status");
				int ship_cnt = shipStatus_count.get(i).get("ship_cnt");
				
				switch (ship_status) {
				case 1:
					cnt_1=ship_cnt;
					break;
				case 2:
					cnt_2=ship_cnt;
					break;
				case 3:
					cnt_3=ship_cnt;
					break;
				}
				
			}

			request.setAttribute("cnt_1", cnt_1);
			request.setAttribute("cnt_2", cnt_2);
			request.setAttribute("cnt_3", cnt_3);

			super.setRedirect(false);
			super.setViewPage("/WEB-INF/mypage/mypageIndex.jsp");
			
		}
		else {
			String message = "로그인 후 이용가능합니다!";
			String loc = request.getContextPath()+"/login/login.ddg";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/common/msg.jsp");
		}
		

		
	}

}
