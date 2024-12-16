package review.controller;

import java.sql.SQLException;
import java.util.List;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import review.domain.ReviewListVO;
import review.model.ReviewListDAO;
import review.model.ReviewListDAO_imple;

public class ReviewListController extends AbstractController {

	private ReviewListDAO revdao;
	
	public ReviewListController() {
		// System.out.println("### 확인용 ReviewListController 클래스 생성자 호출함 ###");
		revdao = new ReviewListDAO_imple();	// WAS 가 구동 될 때 (기본생성자가 생성될 때) 객체를 생성하여 넣어 줌
	}

			
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		//System.out.println("ReviewListController 실행됨");
		
		List<ReviewListVO> revList = revdao.reviewListall();
		
		
		
		
		
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/review/reviewList.jsp");
		

	}

}
