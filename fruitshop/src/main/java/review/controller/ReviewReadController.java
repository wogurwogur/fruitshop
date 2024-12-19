package review.controller;

import java.sql.SQLException;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import review.model.ReviewListDAO;
import review.model.ReviewListDAO_imple;

public class ReviewReadController extends AbstractController {

	
	public ReviewReadController() {

		
	}

			
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		System.out.println("ReviewReadController 실행됨");


			super.setRedirect(false);
			super.setViewPage("/WEB-INF/review/reviewRead.jsp");

	
			
	} // end of public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		
			
} // end of public class ReviewListController extends AbstractController {


