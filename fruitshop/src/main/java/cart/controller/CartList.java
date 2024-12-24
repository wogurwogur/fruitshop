package cart.controller;

import java.sql.SQLException;
import java.util.List;
import cart.domain.CartVO;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;
import cart.model.CartDAO;
import cart.model.CartDAO_imple;

public class CartList extends AbstractController {

    private CartDAO cdao = new CartDAO_imple();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	HttpSession session = request.getSession();
    	MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
    	String userid = request.getParameter("userid");
    	String message = "";
    	
    	if(loginuser != null) {
    		
    		try {
                // DAO에서 장바구니 데이터 가져오기
    			int user_no = loginuser.getUser_no();
                List<CartVO> cartList = cdao.cartListSelectAll(user_no);
                
                request.setAttribute("cartList", cartList);

            } catch (Exception e) {
                e.printStackTrace(); 
                super.setRedirect(true);	// redirect 시킴
    			super.setViewPage(request.getContextPath()+"/error.ddg");
            }

            // View 설정
            super.setRedirect(false);
            super.setViewPage("/WEB-INF/cart/cartList.jsp");
    		
    	}
    	
    	else {
    		//	로그인 상태가 아닌 경우
			message = "로그인 후 볼수 있습니다!!";
			String loc = request.getContextPath()+"/login/login.ddg";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/common/msg.jsp");
		}
    	
    	//////////////////////////////////////////////////////////////////////////////////////////
    	
    	String cart_pno = request.getParameter("cart_no");

        if (cart_pno != null) {
        	
            // 장바구니 삭제(X버튼 누를때) 
            try {
            	
                int cart_no = Integer.parseInt(cart_pno);
                boolean isDeleted = cdao.deleteCartItem(cart_no);

                if (isDeleted) {
                    request.setAttribute("message", "장바구니 상품이 삭제되었습니다.");
                } else {
                    request.setAttribute("message", "장바구니 상품 삭제에 실패하였습니다.");
                }

       
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("message", "삭제에 실패하였습니다.");
            }

            // 메시지를 보여주고 장바구니 리스트로 리다이렉트
            request.setAttribute("redirectUrl", request.getContextPath() + "/cart/cartList.ddg");
            super.setRedirect(false);
            super.setViewPage("/WEB-INF/common/msg.jsp");
            return;
        }
    	
        
		////////////////////////////////////////////////////////////////////////////////////////////
		        
		String deleteAll = request.getParameter("delete_all");
		
		if ("true".equals(deleteAll)) {
		
		// 장바구니 비우기
		try {
		int user_no = loginuser.getUser_no();
		boolean isDeleted = cdao.CartDeleteAll(user_no);
		
		if (isDeleted) {
		request.setAttribute("message", "장바구니 비우기 완료했습니다.");
		} else {
		request.setAttribute("message", "장바구니 비우기 실패하였습니다.");
		}
		
		
		} catch (SQLException e) {
		e.printStackTrace();
		request.setAttribute("message", "삭제에 실패하였습니다.");
		}
		
		// 메시지를 보여주고 관심상품 리스트로 리다이렉트
		request.setAttribute("redirectUrl", request.getContextPath() + "/mypage/wishList.ddg");
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/common/msg.jsp");
		return;
		
		}
        
		
		
		
    }
        
}
