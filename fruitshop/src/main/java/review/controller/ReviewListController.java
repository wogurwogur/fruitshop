package review.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ReviewListController extends AbstractController {

	public ReviewListController() {
		//	System.out.println("### 확인용 Test1Controller 클래스 생성자 호출함 ###");
	}
	
	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("ReviewListController 실행됨");
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/review/reviewList.jsp");
		

	}

}
