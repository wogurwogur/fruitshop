package cart.controller;

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
    private MemberDAO mdao = new MemberDAO_imple();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	HttpSession session = request.getSession();
    	MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
    	String userid = request.getParameter("userid");
    	
    	if(loginuser != null  ) {
    		
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
    	
    }
}
