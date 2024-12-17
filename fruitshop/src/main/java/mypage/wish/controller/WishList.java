package mypage.wish.controller;

import java.util.List;

import cart.domain.CartVO;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mypage.wish.domain.WishVO;
import mypage.wish.model.*;
public class WishList extends AbstractController {
	
	
	private WishDAO wdao = new WishDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		try {
            // DAO에서 관심상품 리스트 데이터 가져오기
            List<WishVO> wishList = wdao.wishListSelectAll();
            
                request.setAttribute("wishList", wishList);

        } catch (Exception e) {
            e.printStackTrace(); 
            super.setRedirect(true);	// redirect 시킴
			super.setViewPage(request.getContextPath()+"/error.ddg");
        }
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/mypage/wishList.jsp");

	}

}
