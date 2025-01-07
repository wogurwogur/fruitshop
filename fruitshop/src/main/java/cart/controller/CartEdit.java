package cart.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;

import java.util.HashMap;
import java.util.Map;

import cart.model.*;

public class CartEdit extends AbstractController {

	 private CartDAO cdao = new CartDAO_imple();
	 
	 
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
        
        String message = "";
        String loc = "";

        if (loginuser != null) {
            // 로그인된 경우
            String cart_no = request.getParameter("cart_no");
            String newQuantity = request.getParameter("cart_prodcount");

            try {
                int cartId = Integer.parseInt(cart_no);
                int quantity = Integer.parseInt(newQuantity);

                if (quantity > 0) {
                    // 수량 업데이트 요청
                    Map<String, String> paraMap = new HashMap<>();
                    paraMap.put("cart_no", String.valueOf(cartId));
                    paraMap.put("cart_prodcount", String.valueOf(quantity));

                    boolean isUpdated = cdao.updateCartQuantity(paraMap);
                    if (isUpdated) {
                        loginuser.setCart_cnt(cdao.getcartCount(loginuser.getUser_no())); // 세션 업데이트
                        message = "장바구니 수량이 성공적으로 업데이트되었습니다.";
                        loc = request.getContextPath() + "/cart/cartList.ddg";
                    } else {
                        message = "수량 업데이트에 실패했습니다.";
                        loc = "javascript:history.back();";
                    }
                } else {
                    message = "수량은 1 이상이어야 합니다.";
                    loc = "javascript:history.back();";
                }
            } catch (NumberFormatException e) {
                message = "잘못된 입력 값입니다.";
                loc = "javascript:history.back();";
            } catch (Exception e) {
                e.printStackTrace();
                message = "수량 업데이트 중 오류가 발생했습니다.";
                loc = "javascript:history.back();";
            }

        } else {
            // 로그인되지 않은 경우
            message = "로그인 후 이용할 수 있습니다.";
            loc = request.getContextPath() + "/login/login.ddg";
        }

        request.setAttribute("message", message);
        request.setAttribute("loc", loc);

        super.setRedirect(false);
        super.setViewPage("/WEB-INF/common/msg.jsp");


	}

}
