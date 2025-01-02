package admin.admincontroller;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import order.model.*;

public class DeliveryStatusController extends AbstractController {

	private OrderDAO odao = new OrderDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		HttpSession session = request.getSession();
		
		MemberVO loginuser = (MemberVO)(session.getAttribute("loginuser"));
		
		if (loginuser == null) {
			String message = "로그인 후 이용가능합니다!";
			String loc = request.getContextPath()+"/login/login.ddg";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/common/msg.jsp");
			
			return;
		}
		
		if (loginuser.getRole() == 1) {
			String message = "관리자만 접근 가능합니다!";
			String loc = request.getContextPath()+"/index.ddg";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/common/msg.jsp");
			
			return;
		}
		
		
		String searchType = request.getParameter("searchType");					// 주문상태필터
		String searchWord = request.getParameter("searchWord");					// 검색어
		String searchShip = request.getParameter("searchShip");					// 배송상태필터
		String currentShowPageNo = request.getParameter("currentShowPageNo");	// 페이지 번호
		String fromDate = request.getParameter("fromDate");						// 시작일
		String toDate = request.getParameter("toDate");							// 마지막일
		
//		System.out.println("확인용 searchType => "+ searchType);
//		System.out.println("확인용 searchWord => "+ searchWord);
//		System.out.println("확인용 currentShowPageNo => "+ currentShowPageNo);
//		System.out.println("확인용 fromDate => "+ fromDate);
//		System.out.println("확인용 toDate => "+ toDate);
		
		
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

		// 현재 페이지
		if (currentShowPageNo == null) {
//			System.out.println("currentShowPageNocurrentShowPageNo => 왜안바뀜?");
			currentShowPageNo = "1";
//			System.out.println("currentShowPageNocurrentShowPageNo => ?"+ currentShowPageNo);
		}
		
		SimpleDateFormat sdfmt = new SimpleDateFormat("yyyy-MM-dd");
		if (fromDate == null) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, -3);
			cal.add(Calendar.DATE, 1);
			fromDate = sdfmt.format(cal.getTime());
		}
		
		if (toDate == null) {
			Calendar cal = Calendar.getInstance();
			toDate = sdfmt.format(cal.getTime());
		}
		
		
		
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("searchType", searchType);
		paraMap.put("searchWord", searchWord);
		paraMap.put("currentShowPageNo", currentShowPageNo);
		paraMap.put("fromDate", fromDate);
		paraMap.put("toDate", toDate);
		paraMap.put("searchShip", searchShip);

//		System.out.println("!!확인용 searchType => "+ searchType);
//		System.out.println("!!확인용 searchWord => "+ searchWord);
//		System.out.println("!!확인용 currentShowPageNo => "+ currentShowPageNo);
//		System.out.println("!!확인용 fromDate => "+ fromDate);
//		System.out.println("!!확인용 toDate => "+ toDate);

		
		int totalPage = odao.getTotalPage(paraMap);
		System.out.println("확인용 totalPage => "+ totalPage);
		
		// === GET 방식이므로 사용자가 웹브라우저 주소창에서 currentShowPageNo 에 totalPage 값 보다 더 큰값을 입력하여 장난친 경우
		// === GET 방식이므로 사용자가 웹브라우저 주소창에서 currentShowPageNo 에 0 또는 음수를 입력하여 장난친 경우
		// === GET 방식이므로 사용자가 웹브라우저 주소창에서 currentShowPageNo 에 숫자가 아닌 문자열을 입력하여 장난친 경우 
		// 아래처럼 막아주도록 하겠다.
		try {
			if (Integer.parseInt(currentShowPageNo) > totalPage || Integer.parseInt(currentShowPageNo) <= 0) {
				currentShowPageNo = "1";
				paraMap.put("currentShowPageNo", currentShowPageNo);
			}
			
		} catch (NumberFormatException e) {
			currentShowPageNo = "1";
			paraMap.put("currentShowPageNo", currentShowPageNo);
		}
		
		
		
		// *** === 페이지 바 만들기 시작 === *** //
		String pageBar = "";
		
		int blockSize = 10;
        // blockSize 는 블럭(토막)당 보여지는 페이지 번호의 개수이다.
		
		int loop = 1;
        // loop 는 1 부터 증가하여 1개 블럭을 이루는 페이지번호의 개수(지금은 10개)까지만 증가하는 용도이다.
		
		// ==== !!! 다음은 pageNo 구하는 공식이다. !!! ==== // 
		int pageNo  = ( (Integer.parseInt(currentShowPageNo) - 1)/blockSize ) * blockSize + 1; 
		// pageNo 는 페이지바에서 보여지는 첫번째 번호이다.
		
		pageBar += "<div class='pagination'>";
		
		// *** [처음]&[이전] 만들기 *** //
		pageBar += "<a href='adminDeliveryStatus.ddg?searchType="+searchType+"&searchWord="+searchWord+"&fromDate="+fromDate+"&toDate="+toDate+"&currentShowPageNo=1'>&laquo;</a>";
		pageBar += "<a href='adminDeliveryStatus.ddg?searchType="+searchType+"&searchWord="+searchWord+"&fromDate="+fromDate+"&toDate="+toDate+"&currentShowPageNo="+(pageNo-1)+"'>&lsaquo;</a>"; 
		
		
		while( !(loop > blockSize || pageNo > totalPage) ) {
			
			if(pageNo == Integer.parseInt(currentShowPageNo)) {
				pageBar += "<a class='active' href='adminDeliveryStatus.ddg?searchType="+searchType+"&searchWord="+searchWord+"&fromDate="+fromDate+"&toDate="+toDate+"&currentShowPageNo="+pageNo+"'>"+pageNo+"</a>";
			}
			else {
				pageBar += "<a href='adminDeliveryStatus.ddg?searchType="+searchType+"&searchWord="+searchWord+"&fromDate="+fromDate+"&toDate="+toDate+"&currentShowPageNo="+pageNo+"'>"+pageNo+"</a>";
			}
			
			loop++;   // 1 2 3 4 5 6 7 8 9 10
			pageNo++;
			
		} // end of while( !(loop > blockSize || pageNo > totalPage) 
		
		// [다음]
		pageBar += "<a href='adminDeliveryStatus.ddg?searchType="+searchType+"&searchWord="+searchWord+"&fromDate="+fromDate+"&toDate="+toDate+"&currentShowPageNo="+pageNo+"'>&rsaquo;</a>";

		// [맨마지막]
		pageBar += "<a href='adminDeliveryStatus.ddg?searchType="+searchType+"&searchWord="+searchWord+"&fromDate="+fromDate+"&toDate="+toDate+"&currentShowPageNo="+totalPage+"'>&raquo;</a>";		
		pageBar += "</div>";
		// *** === 페이지 바 만들기 끝 === *** //
		
		
		try {
			// === 데이터 목록 가져오기 시작 === //
			
			List<Map<String, String>> orderList = odao.getAdminOrderList(paraMap);
			// === 데이터 목록 가져오기 끝 === //
			
//			System.out.println("확인용 orderList.size => "+ orderList.size());
			
			request.setAttribute("adminpage_val", "admin_delivery_status");
			request.setAttribute("searchType", searchType);
			request.setAttribute("searchWord", searchWord);
			request.setAttribute("searchShip", searchShip);
			request.setAttribute("orderList", orderList);
			request.setAttribute("pageBar", pageBar);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/admin_page/admin_page.jsp");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}// end of execute() ------------------------

}
