package order.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.controller.AbstractController;
import coupon.domain.CouponVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import mypage.ship.domain.ShipVO;
import order.model.*;
import product.domain.ProductVO;

public class OrderCheckout extends AbstractController {

	private OrderDAO odao = new OrderDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		HttpSession session = request.getSession();
		// 상품번호, 유저번호, 상품개수
		String userNo = request.getParameter("userNo"); 	// 회원번호
		String prod_no = request.getParameter("prodNo"); 	// 상품번호
		String prodCnt = request.getParameter("prodCnt"); 	// 상품개수
		String cart_no = request.getParameter("cart_no"); 	// 장바구니번호
		
//		System.out.println("확인용 userno :"+ user_no);
		
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		String message = "";
		String loc = "";
		
		// 로그인하지 않은 경우
		if (loginuser == null) {
			message = "로그인 후 이용가능합니다!";
			loc = request.getContextPath()+"/login/login.ddg";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/common/msg.jsp");
			
			return;
		}
		
		// 본인 아이디가 아닌 경우
		try {
			if (loginuser.getUser_no() != Integer.parseInt(userNo)) {
				// 본인 아이디가 아닌 경우
				message = "주문자와 로그인한 유저가 일치해야 합니다!";
				loc = "javascript:history.back()";
				
				request.setAttribute("message", message);
				request.setAttribute("loc", loc);
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/common/msg.jsp");
				
				return;
			}
		} catch (NumberFormatException e) {
			
			message = "비정상적인 요청입니다.";
			loc = "javascript:history.back()";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/common/msg.jsp");
			
			return;
		}
	
		
		
		
		
		// 결제하기 버튼 클릭 여부에 따라 분기한다.
		if (!"POST".equalsIgnoreCase(request.getMethod())) {
			// "GET" 방식으로 요청했을 경우 (결제하기 버튼 클릭 전)
			
			try {
				// 해당 상품과 수량이 판매중인지, 재고보다 많은 요청인지 알아오기
				Map<String, String> paraMap = new HashMap<>();
						
				Integer.parseInt(prod_no);	// exception 발생 시 비정상접근 리턴
				Integer.parseInt(prodCnt);
				
				paraMap.put("prod_no", prod_no);
				
				ProductVO pvo = odao.checkProd(paraMap);
				
				if (pvo == null || Integer.parseInt(prodCnt) > pvo.getProd_inventory()) {
					message = "주문 상품이 올바르지 않거나 재고량보다 많습니다.\n상품 및 수량을 확인하세요.";
					loc = "javascript:history.back()";
					
					request.setAttribute("message", message);
					request.setAttribute("loc", loc);
					
					super.setRedirect(false);
					super.setViewPage("/WEB-INF/common/msg.jsp");
					
					return;
				}
				
				request.setAttribute("pvo", pvo);
				request.setAttribute("prodCnt", prodCnt);
				
				paraMap.put("user_no", String.valueOf(loginuser.getUser_no()));
				
				
				if (cart_no != null) {
					// 장바구니를 통한 주문일 경우
					// 해당 회원의 장바구니에 있는 품목을 가져온다
					List<Map<String, String>> cartList = odao.getCartList(paraMap);
					request.setAttribute("cartList", cartList);
				}
				
				
				// 해당 회원이 가지고 있는 쿠폰 목록을 가져온다
				List<CouponVO> couponList = odao.getCouponList(paraMap);
				
				if (couponList.size() > 0) {
					request.setAttribute("couponList", couponList);
				}
				
				// 해당 회원의 배송지 목록을 가져온다
				List<ShipVO> shipList = odao.getShipList(paraMap);
				
				if (shipList.size() > 0) {
					request.setAttribute("shipList", shipList);
				}
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/order/orderCheckout.jsp");
				
			} catch (NumberFormatException e) {
				
				message = "비정상적인 요청입니다.";
				loc = "javascript:history.back()";
				
				request.setAttribute("message", message);
				request.setAttribute("loc", loc);
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/common/msg.jsp");
				
				return;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else {
			// 결제하기 버튼을 클릭한 경우
			String name = request.getParameter("name");
			String postcode = request.getParameter("postcode");
			String address = request.getParameter("address");
			String detailaddress = request.getParameter("detailaddress");
			String extraaddress = request.getParameter("extraaddress");
			String hp1 = request.getParameter("hp1");
			String hp2 = request.getParameter("hp2");
			String hp3 = request.getParameter("hp3");
			String email = request.getParameter("email");
			String order_request = request.getParameter("order_request");
			String ship_default = request.getParameter("ship_default");
			String user_no = request.getParameter("user_no");
			String coupon_name = request.getParameter("coupon_name");
			String order_tprice = request.getParameter("order_tprice");
			String point = request.getParameter("point");
			
			String mobile = hp1 +"-"+ hp2 +"-"+ hp3;
			
			System.out.println("확인용 name => "+ name);
			System.out.println("확인용 postcode => "+ postcode);
			System.out.println("확인용 address => "+ address);
			System.out.println("확인용 detailaddress => "+ detailaddress);
			System.out.println("확인용 extraaddress => "+ extraaddress);
			System.out.println("확인용 mobile => "+ mobile);
			System.out.println("확인용 email => "+ email);
			System.out.println("확인용 order_request => "+ order_request);
			System.out.println("확인용 ship_default => "+ ship_default);
			System.out.println("확인용 user_no => "+ user_no);
			System.out.println("확인용 coupon_name => "+ coupon_name);			
			System.out.println("확인용 order_tprice => "+ order_tprice);			
			System.out.println("확인용 point => "+ point);			
			
		}
		
		
			
	}// end of execute() ------------------

}
