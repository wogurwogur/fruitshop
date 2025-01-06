package admin.admincontroller;

import java.io.File;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import member.domain.MemberVO;
import product.domain.ProductVO;
import product.model.ProductDAO;
import product.model.ProductDAO_imple;

public class AdminProductRegister extends AbstractController {

	private ProductDAO prdao = new ProductDAO_imple();

	private String extractFileName(String partHeader) { // 파일명만 얻어오는 메소드
		for (String cd : partHeader.split("\\;")) {
			if (cd.trim().startsWith("filename")) {
				String fileName = cd.substring(cd.indexOf("=") + 1).trim().replace("\"", "");
				int index = fileName.lastIndexOf(File.separator); // File.separator 란? OS가 Windows 이라면 \ 이고, OS가 Mac,
																	// Linux, Unix 이라면 / 을 말하는 것이다.
				return fileName.substring(index + 1);
			}
		}
		return null;
	}// end of private String extractFileName(String partHeader)-------------------

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 관리자(admin)로 로그인 했을 때만 제품등록이 가능하도록 한다. //
		HttpSession session = request.getSession();

		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");

		if (loginuser != null && "admin".equals(loginuser.getUserid())) { // 관리자(admin)로 로그인 했을 경우

			String method = request.getMethod();

			if (!"post".equalsIgnoreCase(method)) { // "GET" 인 경우
				
				// 계절 테이블 정보를 알아온다.
				List<ProductVO> seasonInfo = prdao.seasonInfo();
				request.setAttribute("seasonInfo", seasonInfo);
				
				String goBackURL = request.getParameter("goBackURL");
				request.setAttribute("goBackURL", goBackURL);

				super.setRedirect(false);
				super.setViewPage("/WEB-INF/admin_page/admin_product_register.jsp");

			} else { // "POST" 인 경우
				

				// 파일을 업로드 해준다.
				String prod_thumnail = null;
				String prod_descript = null;

				Collection<Part> parts = request.getParts(); //  getParts()를 사용하여 form 태그로 부터 넘어온 데이터들을 각각의 Part로 하나하나씩 받는다.

				for (Part part : parts) {

					if (part.getHeader("Content-Disposition").contains("filename=")) { // form 태그에서 전송되어온 것이 파일일 경우

						String fileName = extractFileName(part.getHeader("Content-Disposition")); // 업로드한 파일명을 구하려면
																									// Content-Disposition
																									// 헤더의 값을 사용한다.

						if (part.getSize() > 0) {
							// 서버에 저장할 새로운 파일명을 만든다.
							// 서버에 저장할 새로운 파일명이 동일한 파일명이 되지 않고 고유한 파일명이 되도록 하기 위해
							// 현재의 년월일시분초에다가 현재 나노세컨즈nanoseconds 값을 결합하여 확장자를 붙여서 만든다.
							String newFilename = fileName.substring(0, fileName.lastIndexOf(".")); // 확장자를 뺀 파일명 알아오기

							newFilename += "_"
									+ String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", Calendar.getInstance());
							newFilename += System.nanoTime();
							newFilename += fileName.substring(fileName.lastIndexOf(".")); // 확장자 붙이기


							if ("prod_thumnail".equals(part.getName())) { // 썸네일 이미지
								
								// 첨부되어진 파일을 디스크의 어느 경로에 업로드 할 것인지 그 경로를 설정해야 한다.
								ServletContext svlCtx = session.getServletContext();
								String uploadFileDir = svlCtx.getRealPath("/images/product/thumnail"); 
								
								// 파일을 지정된 디스크 경로에 저장해준다. 이것이 파일을 업로드 해주는 작업이다.
								part.write(uploadFileDir + File.separator + newFilename);

								// 임시저장된 파일 데이터를 제거해준다.
								part.delete();
								prod_thumnail = newFilename;
								
							} else if ("prod_descript".equals(part.getName())) { // 상세 이미지
								
								// 첨부되어진 파일을 디스크의 어느 경로에 업로드 할 것인지 그 경로를 설정해야 한다.
								ServletContext svlCtx = session.getServletContext();
								String uploadFileDir = svlCtx.getRealPath("/images/product/description");
								
								// 파일을 지정된 디스크 경로에 저장해준다. 이것이 파일을 업로드 해주는 작업이다.
								part.write(uploadFileDir + File.separator + newFilename);

								// 임시저장된 파일 데이터를 제거해준다.
								part.delete();
							
								prod_descript = newFilename;
							}
							

						} // end of if(part.getSize() > 0)

					} // end of if(part.getHeader("Content-Disposition").contains("filename="))
					else { // form 태그에서 전송되어온 것이 파일이 아닐 경우
						String formValue = request.getParameter(part.getName());
					}
				} // end of for (Part part : parts)

				// 제품 정보를 DB에 insert 해준다.
				String prod_name = request.getParameter("prod_name"); // 상품명
				String prod_cost = request.getParameter("prod_cost"); // 원가
				String prod_price = request.getParameter("prod_price"); // 가격
				String prod_inventory = request.getParameter("prod_inventory"); // 재고
				String fk_season_no = request.getParameter("fk_season_no"); // 계절번호

				ProductVO prdvo = new ProductVO();
				prdvo.setProd_name(prod_name); // 상품명
				prdvo.setProd_cost(Integer.parseInt(prod_cost)); // 원가
				prdvo.setProd_price(Integer.parseInt(prod_price)); // 가격
				prdvo.setProd_inventory(Integer.parseInt(prod_inventory)); // 재고
				prdvo.setFk_season_no(Integer.parseInt(fk_season_no)); // 계절번호
				prdvo.setProd_thumnail(prod_thumnail); // 썸네일 이미지
				prdvo.setProd_descript(prod_descript); // 상세 이미지

				// 상품 테이블에 제품 정보 insert 하기
				int n = prdao.productRegister(prdvo);

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

		} // end of if( loginuser != null && "admin".equals(loginuser.getUserid()) )

		else {
			// 로그인을 안한 경우 또는 일반사용자로 로그인 한 경우
			String message = "관리자만 접근이 가능합니다.";
			String loc = "javascript:history.back()";

			request.setAttribute("message", message);
			request.setAttribute("loc", loc);

			super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
		}

	}

}
