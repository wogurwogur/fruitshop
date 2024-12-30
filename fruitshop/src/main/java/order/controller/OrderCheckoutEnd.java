package order.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import order.model.*;
import cart.model.*;

public class OrderCheckoutEnd extends AbstractController {

	private OrderDAO odao = new OrderDAO_imple();
	private CartDAO  cdao = new CartDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		if (!"POST".equalsIgnoreCase(request.getMethod())) {
			// 비정상적으로 접근했을 경우
			String message = "비정상적인 요청입니다다다다 .";
			String loc = "javascript:history.back()";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/common/msg.jsp");
		}
		else {
			
			String order_no 		= request.getParameter("order_no");			// 주문번호(결제, DB) DB 컬럼용
			String order_tprice 	= request.getParameter("order_tprice");		// 주문금액(DB)
//			String email 			= request.getParameter("email");			// 이메일(결제)
//			String name 			= request.getParameter("name");				// 주문자이름(결제)
			String mobile 			= request.getParameter("mobile");			// 연락처(결제,배송지DB)
			String postcode 		= request.getParameter("postcode");			// 우편번호(주문DB)
			String address 			= request.getParameter("address");			// 주소(주문DB)
			String detailaddress 	= request.getParameter("detailaddress");	// 상세주소(주문DB)
			String extraaddress 	= request.getParameter("extraaddress");		// 참고사항(주문DB)
			String order_request 	= request.getParameter("order_request");	// 요청사항(주문DB)
			String ship_default 	= request.getParameter("ship_default");		// 기본배송지설정(배송지DB)
			String user_no 			= request.getParameter("user_no");			// 회원번호(주문DB)
			String coupon_no 		= request.getParameter("coupon_no");		// 쿠폰번호(쿠폰DB)
			String point 			= request.getParameter("point");			// 포인트(회원DB)
			String order_receiver 	= request.getParameter("order_receiver");	// 수신인(주문DB)
			String productArr 		= request.getParameter("productArr");		// 주문상품정보(주문상세DB)

			
			
			
//			System.out.println("!!확인용 order_no => "+ order_no);
//			System.out.println("!!확인용 order_tprice => "+ order_tprice);
//			System.out.println("!!확인용 email => "+ email);
//			System.out.println("!!확인용 name => "+ name);
//			System.out.println("!!확인용 mobile => "+ mobile);
//			System.out.println("!!확인용 postcode => "+ postcode);
//			System.out.println("!!확인용 address => "+ address);
//			System.out.println("!!확인용 detailaddress => "+ detailaddress);
//			System.out.println("!!확인용 extraaddress => "+ extraaddress);
//			System.out.println("!!확인용 order_request => "+ order_request);			
//			System.out.println("!!확인용 ship_default => "+ ship_default);			
//			System.out.println("!!확인용 user_no => "+ user_no);			
//			System.out.println("!!확인용 coupon_name => "+ coupon_name);			
//			System.out.println("!!확인용 point => "+ point);				
			
			
			// 테이블에 insert 할 데이터 준비
			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("order_no", order_no);
			paraMap.put("order_request", order_request);			
			paraMap.put("order_tprice", order_tprice);
			paraMap.put("postcode", postcode);
			paraMap.put("address", address);
			paraMap.put("detailaddress", detailaddress);
			paraMap.put("extraaddress", extraaddress);
			paraMap.put("fk_user_no", user_no);
			paraMap.put("order_receiver", order_receiver);			
			paraMap.put("ship_default", ship_default);			
			paraMap.put("coupon_no", coupon_no);			
			paraMap.put("point", point);			
			paraMap.put("productArr", productArr);			
			paraMap.put("mobile", mobile);			
			
			
			try {
				// 각 테이블에 insert
				int result = odao.insertOrder(paraMap);
				
				// 쿠폰을 사용한 경우
				if (!"".equalsIgnoreCase(coupon_no)) {
					result = 0;
					
					result = odao.isUseCoupon(paraMap);
				}
				
				if (result == 1) {
					// 모든 테이블에 정보가 정상적으로 입력 됐을 경우
					
					// 장바구니 개수 변경
					HttpSession session = request.getSession();
					MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
					loginuser.setCart_cnt(cdao.getcartCount(Integer.parseInt(user_no)));
				}
				
				JSONObject jsonObj = new JSONObject();
				
				jsonObj.put("isComplete", result);	// "{"isComplete": 1}
				
				String json = jsonObj.toString();
				System.out.println("확인용: "+ json);
				
				
				request.setAttribute("json", json);
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/common/jsonview.jsp");
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		
	}// end of execute() --------------------------------------------

}
