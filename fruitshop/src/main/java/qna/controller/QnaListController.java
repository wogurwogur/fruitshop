package qna.controller;

import java.sql.SQLException;
import java.util.List;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import qna.model.*;

public class QnaListController extends AbstractController {

	private QnaListDAO qdao = new QnaListDAO_imple();
	
	public QnaListController() {

		
	}

			
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		//System.out.println("ReviewListController 실행됨");

		/*
		try {
			List<QnaListVO> revList = revdao.reviewListall();
			
			if(revList.size() > 0) {
				request.setAttribute("revList", revList);
				*/
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/qna/qnaList.jsp");
	/*			
			}
						
		} catch(SQLException e) {
			e.printStackTrace();
			
			super.setRedirect(true);
			super.setViewPage(request.getContextPath()+"/error.ddg"); // 아직없음
		  }
			
	*/		
	} // end of public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		

} // end of public class ReviewListController extends AbstractController {


