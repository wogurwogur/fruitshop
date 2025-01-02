package order.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import order.model.*;

public class OrderListController extends AbstractController {

	private OrderDAO odao = new OrderDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
//		로그인을 한 유저만 주문목록 페이지로 진입할 수 있게 한다.
		
		HttpSession session = request.getSession();
		
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		
		if (loginuser == null) {
//			로그인 상태가 아닌 경우
			String message = "로그인 후 이용가능합니다!";
			String loc = request.getContextPath()+"/login/login.ddg";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/common/msg.jsp");
			
			return;
		}
		
		String fromDate = request.getParameter("fromDate");
		
		// 첫 링크 접속 시
		if (fromDate == null) {
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/order/orderlist.jsp");
			
			return;
		}
		
		String toDate 	= request.getParameter("toDate");
		String start 	= request.getParameter("start");
		String len   	= request.getParameter("len");
		String searchType = request.getParameter("searchType");					// 주문상태필터
		String searchWord = request.getParameter("searchWord");					// 검색어
		String searchShip = request.getParameter("searchShip");					// 배송상태필터
//		System.out.println("확인용 start: "+ start);
//		System.out.println("확인용 len: "+ len);
//		System.out.println("확인용 searchType: "+ searchType);
//		System.out.println("확인용 searchWord: "+ searchWord);
//		System.out.println("확인용 searchShip: "+ searchShip);
		
		String end = String.valueOf(Integer.parseInt(start) + Integer.parseInt(len) - 1);
		
		// ship_status 필터조건
		if (searchShip == null || 
		   (!"1".equals(searchShip)   &&
			!"2".equals(searchShip) && 
			!"3".equals(searchShip))) {
			searchShip = "";
		}
		
		
		// order_status 필터조건
		if (searchType == null || 
		   (!"1".equals(searchType)   &&
			!"2".equals(searchType) && 
			!"5".equals(searchType))) {
			searchType = "";
		}
		
		// 주문번호 검색조건
		if (searchWord == null) {
			searchWord = "";
		}
		
		
		// 유저가 정상적으로 로그인 된 경우
		Map<String, String> paraMap = new HashMap<>();
		
		paraMap.put("fromDate", fromDate);
		paraMap.put("toDate", toDate);
		paraMap.put("user_no", String.valueOf(loginuser.getUser_no()));
		paraMap.put("start", start);
		paraMap.put("end", end);
		paraMap.put("len", len);
		paraMap.put("searchType", searchType);
		paraMap.put("searchWord", searchWord);
		paraMap.put("searchShip", searchShip);
		
		
//		System.out.println("fromDate: "+ fromDate);
//		System.out.println("toDate: "+ toDate);
//		System.out.println("start: "+ start);
//		System.out.println("end: "+ end);
//		System.out.println("len: "+ len);
		
		// 해당 회원이 주문한 내역을 담아 전송한다.
		// List<OrderVO> orderList = odao.getOrderList(paraMap);
		try {
			String json = odao.getOrderList(paraMap);
			
			//int totalOrderCount = odao.getTotalOrderCount(paraMap);
			
	//		System.out.println("확인용 json :"+ json);
			
			/*
				 [{"order_no":"20241228133047-37",
				 "prod_no":33,
				 "order_receiver":"이원모",
				 "prod_name":"성주 꿀참외 3kg",
				 "ordetail_price":20000,
				 "order_tprice":22500,
				 "order_date":"2024-12-28",
				 "order_status":1,
				 "order_detailaddress":"9번 출구",
				 "prod_thumnail":"koreanmelon.png",
				 "fk_user_no":18,"ordetail_count":1,
				 "order_extraadress":" (동교동)",
				 "order_address":"서울 마포구 양화로 지하 160",
				 "order_receivertel":"01065757897",
				 "order_postcode":"04050"},{}, {} ...
			 */
			
			request.setAttribute("json", json);
			request.setAttribute("searchType", searchType);
			request.setAttribute("searchWord", searchWord);
			request.setAttribute("searchShip", searchShip);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/common/jsonview.jsp");
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		

	}// end of execute() ---------------------------

}
