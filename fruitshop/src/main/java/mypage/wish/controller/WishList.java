package mypage.wish.controller;

import java.util.List;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import mypage.wish.domain.WishVO;
import mypage.wish.model.*;
public class WishList extends AbstractController {
	
	
	private WishDAO wdao = new WishDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
    	MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
    	String userid = request.getParameter("userid");
    	
    	if(loginuser != null  ) {
    		
    		try {
                // DAO에서 관심상품 리스트 데이터 가져오기
    			int user_no = loginuser.getUser_no();
                List<WishVO> wishList = wdao.wishListSelectAll(user_no);
                
                request.setAttribute("wishList", wishList);
                    
                /*
                // === 특정 상품을 삭제하기전 삭제할 상품의 정보를 먼저 알아온다.
				  String wish_no = request.getParameter("wish_no");
				  
				  WishVO wishvo = wdao.selectOne(wish_no);
				  request.setAttribute("wishvo", wishvo);
				  
				  int n = wdao.deletePerson(wish_no);
				
				
				  if(n==1) {
					 
				  } 
                */
                    
            } catch (Exception e) {
                e.printStackTrace(); 
                super.setRedirect(true);	// redirect 시킴
    			super.setViewPage(request.getContextPath()+"/error.ddg");
            }
    		
    		super.setRedirect(false);
    		super.setViewPage("/WEB-INF/mypage/wishList.jsp");
    		
    	}
		
		

	}

}
