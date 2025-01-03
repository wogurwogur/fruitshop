package admin.admincontroller;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import product.domain.ProductVO;
import product.model.ProductDAO;
import product.model.ProductDAO_imple;

public class AdminProductNoImgUpdate extends AbstractController {
	
	
	private ProductDAO prdao = new ProductDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		// 관리자(admin)로 로그인 했을때만 조회가 가능하도록 한다.
		HttpSession session = request.getSession();
		
		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
		
		
		if(loginuser != null && "admin".equals(loginuser.getUserid())) {
			
			String method = request.getMethod();
			String prod_no = request.getParameter("prod_no"); // 상품번호
			
			if("POST".equalsIgnoreCase(method)) {
		
				// 제품 정보를 DB에 Update 해준다.
				
				prod_no = request.getParameter("prod_no"); // 상품 번호 값 다시 가져오기
				
				String prod_name = request.getParameter("prod_name"); // 상품명
				String prod_cost = request.getParameter("prod_cost"); // 원가
				String prod_price = request.getParameter("prod_price"); // 가격
				String prod_inventory = request.getParameter("prod_inventory"); // 재고
				String fk_season_no = request.getParameter("fk_season_no"); // 계절번호
				String prod_status = request.getParameter("prod_status"); // 판매여부 번호
				String org_prod_thumnail = request.getParameter("org_prod_thumnail"); // 기존 썸네일 이미지
				String org_prod_descript = request.getParameter("org_prod_descript"); // 기존 상세 이미지
				
				
				ProductVO prdvo = new ProductVO();
				prdvo.setProd_name(prod_name); // 상품명
				prdvo.setProd_cost(Integer.parseInt(prod_cost)); // 원가
				prdvo.setProd_price(Integer.parseInt(prod_price)); // 가격
				prdvo.setProd_inventory(Integer.parseInt(prod_inventory)); // 재고
				prdvo.setFk_season_no(Integer.parseInt(fk_season_no)); // 계절번호
				prdvo.setProd_status((Integer.parseInt(prod_status))); // 판매여부 번호
				prdvo.setProd_thumnail(org_prod_thumnail); // 썸네일 이미지
				prdvo.setProd_descript(org_prod_descript); // 상세 이미지
				
				
				// 상품 테이블에 제품 정보 Update 하기
				int n = prdao.productUpdate(prdvo, prod_no);
				
				int result = 0;
		
				if (n == 1) {
					result = 1;
				}
				
		        JSONObject jsonObj = new JSONObject(); 
		        jsonObj.put("result", result);
		       
		        String json = jsonObj.toString(); // 문자열로 변환 
		        request.setAttribute("json", json);
		       
		        super.setRedirect(false);
		        super.setViewPage("/WEB-INF/common/jsonview.jsp");
		}
		}
		else {
			// 로그인을 안하거나 또는 관리자(admin)가 아닌 사용자로 로그인 했을 경우
			String message = "관리자만 접근이 가능합니다.";
			String loc = "javascript:history.back()";

			request.setAttribute("message", message);
			request.setAttribute("loc", loc);

			super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");	
		}
	
	}
	

}
