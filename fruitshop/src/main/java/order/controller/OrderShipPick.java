package order.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mypage.ship.domain.ShipVO;
import order.model.*;

public class OrderShipPick extends AbstractController {

	private OrderDAO odao = new OrderDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String user_no = request.getParameter("user_no");
		
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("user_no", user_no);
		
		// 회원이 등록한 배송지 목록을 가져온다.
		List<ShipVO> shipList = odao.getShipList(paraMap);
		
		JSONArray jsonArr = new JSONArray();
		
		if (shipList.size() > 0) {
			
			for (ShipVO svo : shipList ) {
				
				JSONObject jsonObj = new JSONObject();	// {}
				
				jsonObj.put("ship_name", svo.getShip_name());
				jsonObj.put("ship_postcode", svo.getShip_postcode());
				jsonObj.put("ship_address", svo.getShip_address());
				jsonObj.put("ship_detailaddress", svo.getShip_detailaddress());
				jsonObj.put("ship_extraadress", svo.getShip_extraadress());
				jsonObj.put("ship_default", svo.getShip_default());
				jsonObj.put("ship_receiver", svo.getShip_receiver());
				jsonObj.put("ship_receivertel", svo.getShip_receivertel());
				
				jsonArr.put(jsonObj);
			}// end of for() -----------------
			
		}
		
		String json = jsonArr.toString();		// 문자열로 변환
      	
//  	System.out.println("~~~ 확인용 json => "+ json);
  	
	  	request.setAttribute("json", json);
	  	
	  	super.setRedirect(false);
	  	super.setViewPage("/WEB-INF/common/jsonview.jsp");
		
		
	}// end of execute() -----------------------------

}
