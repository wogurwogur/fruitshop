package order.controller;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import login.controller.GoogleMail;
import member.domain.MemberVO;
import order.model.*;
import cart.model.*;

public class OrderCheckoutEnd extends AbstractController {

	private OrderDAO odao = new OrderDAO_imple();
	private CartDAO  cdao = new CartDAO_imple();
	
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
			String email 			= request.getParameter("email");			// 이메일(결제)
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
			String coupon_name 		= request.getParameter("coupon_name");		// 쿠폰명(쿠폰DB)
			String coupon_discount 	= request.getParameter("coupon_discount");	// 할인액(쿠폰DB)
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
			paraMap.put("coupon_name", coupon_name);			
			paraMap.put("coupon_discount", coupon_discount);			
			paraMap.put("user_no", user_no);			
			
			
			try {
				// 각 테이블에 insert
				int n = odao.insertOrder(paraMap);
				
				// 쿠폰을 사용한 경우
				if (!"".equalsIgnoreCase(coupon_no)) {
					n = 0;
					
					n = odao.isUseCoupon(paraMap);
				}
				
				int result = 0;
				if (n == 1) {
					// 모든 테이블에 정보가 정상적으로 입력 됐을 경우
					
					// 장바구니 개수 변경
					HttpSession session = request.getSession();
					MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
					loginuser.setCart_cnt(cdao.getcartCount(Integer.parseInt(user_no)));

					
					////////// === 주문이 완료되었다는 email 보내기 시작 === ///////////
					GoogleMail mail = new GoogleMail();
					
					// 주문한 제품에 대해 email 보내기시 email 내용에 넣을 주문한 제품번호들에 대한 제품정보를 얻어오는 것.
					List<Map<String, String>> orderDetailList =  odao.getOrderDetailList(paraMap);
					Map<String, String> orderDetail =  odao.getOrderDetail(paraMap);
					
					String hp1 = orderDetail.get("order_receivertel").substring(0, 3);
					String hp2 = orderDetail.get("order_receivertel").substring(3, 7);
					String hp3 = orderDetail.get("order_receivertel").substring(7, 11);
					
					String omobile = hp1 + "-" + hp2 + "-" + hp3;
					
					String oaddress = orderDetail.get("order_address") +" "+ orderDetail.get("order_extraadress") +" "+ orderDetail.get("order_detailaddress") +" ("+ orderDetail.get("order_postcode") +")";
					
					StringBuilder sb = new StringBuilder();
					
					sb.append("<div style=\"margin-bottom: 5%;\"> "
							+ "        <span style=\"font-family: 'Noto Sans KR', sans-serif; font-size: 16px; font-weight: 500;\">안녕하세요 <span style=\"color:blue; font-family: 'Noto Sans KR', sans-serif; font-size: 18px; font-weight: 500;\">"+orderDetail.get("name")+"</span>님 싱싱한 과일을 전하는 과일 쇼핑몰 싱싱입니다.</span><br> "
							+ "        <span style=\"font-family: 'Noto Sans KR', sans-serif; font-size: 16px; font-weight: 500;\">고객님의 소중한 주문에 감사드리며 아래의 주문내역을 확인해 주시기 바랍니다.</span> "
							+ "    </div>");
					
					sb.append("<div style=\"width: 45%; border: solid 0.5px #c0c0c0;\">");
					sb.append("<div style=\"background-color: black; color: white; text-align: center;\" >");
					sb.append("<span style=\"font-family: 'Noto Sans KR', sans-serif; font-size: 22px; font-weight: 500;\">"+orderDetail.get("name")+"님의 주문 내역</span></div>");
					
					
					// 주문번호 시작
					sb.append("<div style=\"border-top: solid 0.5px #c0c0c0; border-bottom: solid 0.5px #c0c0c0; margin-top: 2%;\">");
					sb.append("<div><div style=\"color: #555555; padding: 15px; text-decoration: none; font-size: 12pt; font-family: 'Noto Sans KR', sans-serif;\">");
					sb.append("<span style=\"font-family: 'Noto Sans KR', sans-serif; font-size: 15px; font-weight: 500; color: black;\">주문일시</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					sb.append("<span style=\"font-family: 'Noto Sans KR', sans-serif; font-size: 15px; font-weight: 300; color: black;\">"+ orderDetail.get("order_date") +"</span></div>");
					
					sb.append("<div style=\"color: #555555; padding: 15px; text-decoration: none; font-size: 12pt; font-family: 'Noto Sans KR', sans-serif;\">");
					sb.append("<span style=\"font-family: 'Noto Sans KR', sans-serif; font-size: 15px; font-weight: 500; color: black;\">주문번호</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					sb.append("<span style=\"font-family: 'Noto Sans KR', sans-serif; font-size: 15px; font-weight: 300; color: black;\">"+ orderDetail.get("order_no") +"</span> </div></div></div>");
					// 주문번호 끝

					
					// 주문 상품 정보 시작
					sb.append("<div style=\"margin-top: 5%;\">");
					sb.append("<div style=\"margin-left: 1%; font-family: 'Noto Sans KR', sans-serif; font-weight: 400;\">주문 상품 정보</div>");
					sb.append("<hr style=\"border: solid 1px black;\">");
					sb.append("<div style=\"margin-top: 2%;\">");
					
					sb.append("<table style=\"text-align: center; border-collapse: collapse; width: 100%;\"><thead>");
					sb.append("<tr style=\"background-color: #eee; border: solid 1px black; font-family: 'Noto Sans KR', sans-serif; font-weight: 400;\">");
					sb.append("<th style=\"border: solid 1px black; font-family: 'Noto Sans KR', sans-serif; font-size: 15px;\"></th>");
					sb.append("<th style=\"width: 50%; border: solid 1px black; font-family: 'Noto Sans KR', sans-serif; font-size: 15px;\">상품명</th>");
					sb.append("<th style=\"border: solid 1px black; font-family: 'Noto Sans KR', sans-serif; font-size: 15px;\">수량</th>");
					sb.append("<th style=\"border: solid 1px black; font-family: 'Noto Sans KR', sans-serif; font-size: 15px;\">가격</th> </tr></thead><tbody>");
					
					
					DecimalFormat df = new DecimalFormat("###,###");
					
					int price_sum = 0;
					// 상품정보
					for (Map<String, String> map :orderDetailList) {
						sb.append("<tr style=\"border: solid 1px black;\">");
						sb.append("<td style=\"border: solid 1px black; font-family: 'Noto Sans KR', sans-serif; font-weight: 400; font-size: 14px;\"><a href='http://127.0.0.1:9090/fruitshop/product/productDetail.ddg?prodNo="+map.get("fk_prod_no")+"'><img style= \"padding-top: 3px; width: 50px; height: 40px;\" src='http://127.0.0.1:9090/fruitshop/images/product/thumnail/"+map.get("prod_thumnail")+"' /></a></td>");
						sb.append("<td style=\"border: solid 1px black; font-family: 'Noto Sans KR', sans-serif; font-weight: 400; font-size: 14px;\">"+ map.get("prod_name") +"</td>");
						sb.append("<td style=\"border: solid 1px black; font-family: 'Noto Sans KR', sans-serif; font-weight: 400; font-size: 14px;\">"+ df.format(Integer.parseInt(map.get("ordetail_count"))) +"</td>");
						sb.append("<td style=\"border: solid 1px black; font-family: 'Noto Sans KR', sans-serif; font-weight: 400; font-size: 14px;\">"+ df.format(Integer.parseInt(map.get("ordetail_price"))) +"</td></tr>");
						price_sum +=  Integer.parseInt(map.get("ordetail_price"));
					}// end of for() -----------------------
					sb.append("</tbody></table></div>");
					// 주문 상품 정보 끝
					
					// 배송지정보 시작
					sb.append("<div style=\"margin-top:5%; margin-left: 1%; font-family: 'Noto Sans KR', sans-serif; font-weight: 400;\">받으실 분</div>");
					sb.append("<hr style=\"border: solid 1px black;\">");
					sb.append("<div style=\"width: 90%; margin: 0 auto;\">");
					sb.append("<span style=\"display: block; font-family: 'Noto Sans KR', sans-serif; font-weight: 600; font-size: 18px; margin:1.5% 0 1.5% 0;\">"+orderDetail.get("order_receiver")+"</span>");
					sb.append("<span style=\"display: block; font-family: 'Noto Sans KR', sans-serif; font-weight: 400; font-size: 14px; color: #555555; margin-bottom: 1.5%;\">"+ omobile +"</span>");
					sb.append("<span style=\"display: block; font-family: 'Noto Sans KR', sans-serif; font-weight: 500; font-size: 16px; color: #555555;\">"+ oaddress +"</span></div>");
					// 배송지정보 끝
					
					
					// 결제 정보 확인 시작
					sb.append("<div style=\"margin-top:5%; margin-left: 1%; font-family: 'Noto Sans KR', sans-serif; font-weight: 400;\">결제 정보</div>");
					sb.append("<hr style=\"border: solid 1px black;\">");
					sb.append("<div style=\"width: 90%; margin: 0 auto;\">");
					sb.append("<span style=\"font-family: 'Noto Sans KR', sans-serif; font-weight: 400; font-size: 14px;\">주문상품</span><span style=\"float: right; font-family: 'Noto Sans KR', sans-serif; font-weight: 400; font-size: 14px;\">"+df.format(price_sum)+" 원</span><br><br>");
					sb.append("<span style=\"font-family: 'Noto Sans KR', sans-serif; font-weight: 400; font-size: 14px;\">배송비</span><span style=\"float: right; font-family: 'Noto Sans KR', sans-serif; font-weight: 400; font-size: 14px;\">2,500 원</span><br><br>");
					sb.append("<span style=\"font-family: 'Noto Sans KR', sans-serif; font-weight: 400; font-size: 14px;\">할인액</span><span style=\"float: right; font-family: 'Noto Sans KR', sans-serif; font-weight: 400; font-size: 14px;\">"+ df.format(Integer.parseInt(orderDetail.get("order_dicount"))) + " 원</span><br></div><hr>");
					// 결제 정보 확인 끝
					
					
					sb.append("<div style=\"width: 90%; margin: 0 auto;\">");
					sb.append("<span style=\"font-family: 'Noto Sans KR', sans-serif; font-weight: 400; font-size: 14px;\">총 결제액</span><span style=\"float: right; font-family: 'Noto Sans KR', sans-serif; font-weight: 400; font-size: 14px;\">"+ df.format(Integer.parseInt(orderDetail.get("order_tprice"))) +" 원</span><br><br>");
					sb.append("<span style=\"font-family: 'Noto Sans KR', sans-serif; font-weight: 400; font-size: 14px;\">적립금</span><span style=\"float: right; font-family: 'Noto Sans KR', sans-serif; font-weight: 400; font-size: 14px;\">"+ df.format((price_sum-2500)*0.01) +" 원</span>");
					sb.append("</div><div style=\"margin-top: 2%; background-color: black; color: white;\">");
					
					sb.append("<div style=\"padding-top: 1.5%; margin-left: 7.5%; margin-bottom: 1.5%;\">");
					sb.append("<a href='http://127.0.0.1:9090/fruitshop/'><img style=\"top:20px; width: 120px; \" id=\"footer_img\" src=\"http://127.0.0.1:9090/fruitshop/images/index/logo_footer.png\" /></a></div>");
					sb.append("<hr style=\"border: solid 1px white;\">");
					
					sb.append("<div style=\"width: 100%; margin:0 auto; margin:2.3% 0;\">");
					sb.append("<p style=\" text-align: center; font-size: 12px; font-family: 'Noto Sans KR', sans-serif; font-weight: 400;\">고객센터 2222-2222&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;09:00 ~ 18:00</p>");
					sb.append("<p style=\" text-align: center; font-size: 12px; font-family: 'Noto Sans KR', sans-serif; font-weight: 400;\">서울 마포구 월드컵북로 21 풍성빌딩 쌍용강북교육센터 3층 G강의실</p>");
					sb.append("</div><hr style=\"border: solid 1px white;\">");
					
					sb.append("<div style=\"text-align: center; padding: 0.1% 0 1% 0;\">");
					sb.append("<p style=\"font-size: 12px; font-family: 'Noto Sans KR', sans-serif; font-weight: 400;\"><span style=\"font-weight: bold;\">Copyright</span> &copy; 싱싱 과일쇼핑몰. All right reserved.</p>");
					sb.append("</div></div></div></div>");
					
					sb.append("<br><br>이용해 주셔서 감사합니다.");
					
					String emailContents = sb.toString();
		            mail.sendmail_OrderFinish(email, loginuser.getName(), emailContents);
		            
		            result = 1;
				}
				
				
				
				
				JSONObject jsonObj = new JSONObject();
				
				jsonObj.put("isComplete", result);	// "{"isComplete": 1}
				
				String json = jsonObj.toString();
//				System.out.println("확인용: "+ json);
				
				
				request.setAttribute("json", json);
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/common/jsonview.jsp");
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		
	}// end of execute() --------------------------------------------

}
