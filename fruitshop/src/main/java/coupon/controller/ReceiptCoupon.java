package coupon.controller;

import java.util.HashMap;
import java.util.Map;

import admin.model.AdminDAO;
import admin.model.AdminDAO_imple;
import common.controller.AbstractController;
import coupon.model.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;

public class ReceiptCoupon extends AbstractController {

	private CouponDAO cdao = new CouponDAO_imple();
	private AdminDAO adao = new AdminDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		HttpSession session = request.getSession();
		
		MemberVO loginuser = (MemberVO)(session.getAttribute("loginuser"));
		String detail_user_no = request.getParameter("detail_user_no");
		
		
		if(null == loginuser) {
			String message = "관리자만 접근이 가능합니다.";
	        String loc = "/WEB-INF/index/index.jsp";
	        
	        request.setAttribute("message", message);
	        request.setAttribute("loc", loc);
	        
	        super.setRedirect(false);
	        super.setViewPage("/WEB-INF/common/msg.jsp"); 
			
		}else if(loginuser.getRole() == 1){ 
			
			String message = "관리자만 접근이 가능합니다.";
	        String loc = "/WEB-INF/index/index.jsp";
	        
	        request.setAttribute("message", message);
	        request.setAttribute("loc", loc);
	        
	        super.setRedirect(false);
	        super.setViewPage("/WEB-INF/common/msg.jsp"); 
			
		}else {
			
			String user_no = request.getParameter("user_no");
			String coupon_name = request.getParameter("coupon_name");
			String coupon_descript = request.getParameter("coupon_descript");
			String coupon_expire = request.getParameter("coupon_expire");
			String coupon_discount = request.getParameter("coupon_discount");
			
			Map<String, String> paraMap = new HashMap<>();
			
			paraMap.put("user_no", user_no);
			paraMap.put("coupon_name", coupon_name);
			paraMap.put("coupon_descript", coupon_descript);
			paraMap.put("coupon_expire", coupon_expire);
			paraMap.put("coupon_discount", coupon_discount);
			
			int n = cdao.reciptCoupon(paraMap);
			
			if(n == 1) {
				
				
				MemberVO detailMember = adao.memberDetailInfo(user_no);
				
				request.setAttribute("detailMember", detailMember);
				request.setAttribute("adminpage_val", "admin_member_detail");
				
				String memberCoupon = adao.memberCouponCnt(detail_user_no);
				
				request.setAttribute("memberCoupon", memberCoupon);
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/admin_page/admin_page.jsp");
				
			}else {
				String message = "관리자 권한 부여에 실패했습니다.";
		        String loc = "javascript:history.back()";
		        
		        request.setAttribute("message", message);
		        request.setAttribute("loc", loc);
		        
		        super.setRedirect(false);
		        super.setViewPage("/WEB-INF/common/msg.jsp");
			}
			
		}
		
		
		
	}

}
