package mypage.wish.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import mypage.wish.domain.WishVO;
import mypage.wish.model.*;
import product.domain.ProductVO;
import product.model.ProductDAO;
import product.model.ProductDAO_imple;
public class WishList extends AbstractController {
	
	private WishDAO wdao = new WishDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
    	MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
    	String userid = request.getParameter("userid");
    	String message = "";
    	
    	String referer = request.getHeader("referer");
		// request.getHeader("referer"); 은 이전 페이지의 URL을 가져오는 것이다.
		
		// System.out.println("~~~~ 확인용 referer : " + referer);
		
		if(referer == null) {
		// referer == null 은 웹브라우저 주소창에 URL을 직접 입력하고 들어온 경우이다.
		   super.setRedirect(true);
		   super.setViewPage(request.getContextPath()+"/index.ddg");	// 홈화면으로 돌아간다.
		   return;
		}
    	
    	
    	if(loginuser != null  ) {	// 로그인 했을때
    		
    		try {
                // DAO에서 관심상품 리스트 데이터 가져오기
    			int user_no = loginuser.getUser_no();
                List<WishVO> wishList = wdao.wishListSelectAll(user_no);
                
                request.setAttribute("wishList", wishList);
                request.setAttribute("mypage_val", "wishList");
    
                    
            } catch (Exception e) {
                e.printStackTrace(); 
                super.setRedirect(true);	// redirect 시킴
    			super.setViewPage(request.getContextPath()+"/error.ddg");
            }
    		
    		super.setRedirect(false);
    		super.setViewPage("/WEB-INF/mypage/wishList.jsp");
    		
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
    	
    	///////////////////////////////////////////////////////////////////////////////////
    	
    	String wish_pno = request.getParameter("wish_no");

        if (wish_pno != null) {
        	
            // 관심상품 삭제(X버튼 누를때) 
            try {
                int wish_no = Integer.parseInt(wish_pno);
                boolean isDeleted = wdao.deleteWishItem(wish_no);

                if (isDeleted) {
                    request.setAttribute("message", "상품이 삭제되었습니다.");
                } else {
                    request.setAttribute("message", "상품 삭제에 실패하였습니다.");
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
    	
		////////////////////////////////////////////////////////////////////////////////////////////
        
        
        	String deleteAll = request.getParameter("delete_all");
        	
        	if ("true".equals(deleteAll)) {
        	
            // 관심상품 비우기
            try {
            	int user_no = loginuser.getUser_no();
                boolean isDeleted = wdao.WishDeleteAll(user_no);

                if (isDeleted) {
                    request.setAttribute("message", "관심상품 비우기 완료했습니다.");
                } else {
                    request.setAttribute("message", "관심상품 비우기 실패하였습니다.");
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
        	
        	//////////////////////////////////////////////////////////////////////////////////////////
        	
        	
        	

        	
        	
        	
        	
        	
        	
	}

}
