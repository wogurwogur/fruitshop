package order.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
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
		
		String referer = request.getHeader("referer");
		// request.getHeader("referer"); 은 이전 페이지의 URL을 가져오는 것이다.
		
//		System.out.println("~~~~~ 확인용 referer => "+ referer);
		
		if (referer == null) {
			// referer == null 은 웹브라우저 주소창에 직접 URL을 입력하여 들어온 것이다.
			super.setRedirect(true);
			super.setViewPage(request.getContextPath()+ "/index.ddg");
			return;
		}
		
		HttpSession session = request.getSession();
		// 상품번호, 유저번호, 상품개수
		String userNo = request.getParameter("userNo"); 	// 회원번호
		String prod_no = request.getParameter("prodNo"); 	// 상품번호
		String prodCnt = request.getParameter("prodCnt"); 	// 상품개수
		String cartTotal = request.getParameter("cartTotal"); 	// 장바구니번호
		String selectedItems = request.getParameter("selectedItem"); 	// 장바구니 일부 상품 번호
		
//		for (int i=0; i<)
		
//		System.out.println("확인용 userno :"+ user_no);
//		System.out.println("확인용 selectedItems :"+ selectedItems);
//		System.out.println("확인용 cartTotal :"+ cartTotal);
		
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
				paraMap.put("user_no", String.valueOf(loginuser.getUser_no()));
				
				// 상품 상세페이지에서 주문 시
				if (prodCnt != null) {
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
				}
		
								
				if ("Y".equalsIgnoreCase(cartTotal)) {
					// 장바구니 전체 주문일 경우
					// 해당 회원의 장바구니에 있는 품목을 가져온다
					List<Map<String, String>> cartList = odao.getCartList(paraMap);
					request.setAttribute("cartList", cartList);
				}
				
				if (selectedItems != null) {
					// 장바구니 선택 주문일 경우
					String[] arr_product;
					arr_product = selectedItems.split(",");	// 상품 번호
					List<Map<String, String>> cartList  = new ArrayList<>();
					
					for (int i=0; i<arr_product.length; i++) {						
						paraMap.put("cart_no", arr_product[i]);
						
						// 상품의 정보를 가져옴
						Map<String, String> cartItem = odao.getCartItem(paraMap);
						
						cartList.add(cartItem);
						
					}// end of for() ----------------------
					
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
				
				message = "비정상적인 요청입니다다다다 .";
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
			String order_receiver = request.getParameter("name");
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
			String user_no = request.getParameter("userNo");
			String coupon_no = request.getParameter("coupon_no");
			String order_tprice = request.getParameter("order_tprice");
			String point = request.getParameter("point");
			String productArr = request.getParameter("productArr");
			String coupon_discount = request.getParameter("coupon_discount");
			String coupon_name = request.getParameter("coupon_name");
//			String[] arr_prodNo = request.getParameterValues("prod_no");
			String[] arr_prod_name = request.getParameterValues("prod_name");
			
			
			String mobile = hp1 + hp2 + hp3;
			
			if ("".equals(coupon_name)) {
				coupon_name = "없음";
			}
			if ("".equals(coupon_discount)) {
				coupon_discount = "0";
			}
			
//			System.out.println("확인용 name => "+ name);
//			System.out.println("확인용 postcode => "+ postcode);
//			System.out.println("확인용 address => "+ address);
//			System.out.println("확인용 detailaddress => "+ detailaddress);
//			System.out.println("확인용 extraaddress => "+ extraaddress);
//			System.out.println("확인용 mobile => "+ mobile);
//			System.out.println("확인용 email => "+ email);
//			System.out.println("확인용 order_request => "+ order_request);
//			System.out.println("확인용 ship_default => "+ ship_default);
//			System.out.println("확인용 user_no => "+ user_no);
			System.out.println("확인용 coupon_no => "+ coupon_no);			
			System.out.println("확인용 coupon_discount => "+ coupon_discount);			
			System.out.println("확인용 coupon_name => "+ coupon_name);			
//			System.out.println("확인용 order_tprice => "+ order_tprice);			
//			System.out.println("확인용 point => "+ point);			
//			System.out.println("확인용 arr_prodNo => "+ String.join(",", arr_prodNo));			
//			System.out.println("확인용 arr_prod_name => "+ String.join(",", arr_prod_name));			
//			System.out.println("확인용 productArr => "+ productArr);			
			
			
			/*
			// 상품 객체 배열을 다시 객체화
			JSONArray jsonArr = new JSONArray(productArr);
			
			System.out.println("jsonArr: "+ jsonArr);
			
			for (int i=0; i<jsonArr.length(); i++) { 
				JSONObject jsonObj = jsonArr.getJSONObject(i);
				
				String prodNo = jsonObj.getString("prod_no");
				String prod_name = jsonObj.getString("prod_name");
				String prod_count = jsonObj.getString("prod_count");
				String prod_price = jsonObj.getString("prod_price");
				
				
				System.out.println("확인용 prodNo => "+ prodNo);
				System.out.println("확인용 prod_name => "+ prod_name);
				System.out.println("확인용 prod_count => "+ prod_count);
				System.out.println("확인용 prod_price => "+ prod_price);
				
			}// end of for() -------------------
			*/
			// 주문번호 생성하기 (채번) (오늘날짜-시퀀스번호)
			int order_no = odao.getOrderNo();
			
			String productName = (arr_prod_name.length > 1)? arr_prod_name[0] +"외 "+ (arr_prod_name.length-1)+ "건" : arr_prod_name[0];	// 결제창 표기용
			
			String orderNo = String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", Calendar.getInstance()) +"-"+ order_no;
			
//			System.out.println("확인용 주문번호: "+ orderNo);
//			System.out.println("확인용 상품명: "+ productName);
			
			request.setAttribute("productName", productName);		// 주문번호(결제, DB) 상품명 결제창 용
			request.setAttribute("order_no", orderNo);				// 주문번호(결제, DB) DB 컬럼용
			request.setAttribute("order_tprice", Integer.parseInt(order_tprice));	// 주문금액(DB)
			request.setAttribute("productPrice", 100);				// 실제결제금액(결제)
//			request.setAttribute("productPrice", Integer.parseInt(order_tprice));				// 실제결제금액(결제)
			request.setAttribute("email", email);					// 이메일(결제)
			request.setAttribute("name", loginuser.getName());		// 주문자이름(결제)
			request.setAttribute("mobile", mobile);					// 연락처(결제)
			request.setAttribute("postcode", postcode);				// 우편번호(주문DB)
			request.setAttribute("address", address);				// 주소(주문DB)
			request.setAttribute("detailaddress", detailaddress);	// 상세주소(주문DB)
			request.setAttribute("extraaddress", extraaddress);		// 참고사항(주문DB)
			request.setAttribute("order_request", order_request);	// 요청사항(주문DB)
			request.setAttribute("ship_default", ship_default);		// 기본배송지설정(배송지DB)
			request.setAttribute("user_no", user_no);				// 회원번호(주문DB)
			request.setAttribute("coupon_no", coupon_no);			// 쿠폰번호(쿠폰DB)
			request.setAttribute("coupon_discount", coupon_discount); // 쿠폰할인액(쿠폰DB)
			request.setAttribute("coupon_name", coupon_name); 		// 쿠폰명(쿠폰DB)
			request.setAttribute("point", point);					// 포인트(회원DB)
			request.setAttribute("order_receiver", order_receiver);	// 받는사람(주문DB)
			request.setAttribute("productArr", productArr);			// 주문상품정보(주문상세DB)
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/order/paymentGateway.jsp");
			
		}
		
		
			
	}// end of execute() ------------------

}
