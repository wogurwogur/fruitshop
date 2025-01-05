package notice.noticecontroller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.coyote.Request;
import org.apache.taglibs.standard.tag.el.fmt.RequestEncodingTag;
import notice.model.*;
import notice.domain.*;
import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;

public class NoticeController extends AbstractController {

	NoticeDAO ndao = new NoticeDAO_imple();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		String searchType = request.getParameter("searchType");
		String searchWord = request.getParameter("searchWord");
		String currentShowPageNo = request.getParameter("currentShowPageNo");
		String sizePerPage = "10";
		
		if(currentShowPageNo == null) {
			currentShowPageNo = "1";
		}
		
		if(searchType == null ||
				(!"notice_title".equals(searchType))&&(!"notice_contents".equals(searchType))) {
			searchType = "";
		}
		
		if(searchWord == null) {
			searchWord = "";
		}
		
		
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("sizePerPage", sizePerPage);
		paraMap.put("searchType", searchType);
		paraMap.put("searchWord", searchWord);			
		paraMap.put("currentShowPageNo", currentShowPageNo);
		List<NoticeVO> notice_allList = null;
		
		int totalPage = ndao.getTotalPage(paraMap);
		
		String pageBar = "";
		int blockSize = 10;
        // blockSize 는 블럭(토막)당 보여지는 페이지 번호의 개수이다.
		int loop = 1;
        // loop 는 1 부터 증가하여 1개 블럭을 이루는 페이지번호의 개수(지금은 10개)까지만 증가하는 용도이다.
		
		// ==== !!! 다음은 pageNo 구하는 공식이다. !!! ==== // 
		
		
		try {
			
			if(Integer.parseInt(currentShowPageNo) > totalPage ||
					Integer.parseInt(currentShowPageNo) <=0 ) {
				currentShowPageNo = "1";
			}
			
			
		}catch(NumberFormatException e) {
			currentShowPageNo = "1";
		}
		
        int pageNo  = ( (Integer.parseInt(currentShowPageNo) - 1)/blockSize ) * blockSize + 1; 
        // pageNo 는 페이지바에서 보여지는 첫번째 번호이다.
        
        
        
        pageBar += "<a href='"+request.getContextPath()+"/notice/noticeList.ddg?searchType="+searchType+"&sizePerPage="+sizePerPage+"&searchWord="+searchWord+"&currentShowPageNo="+pageNo+"'>&laquo;</a>";
        
        if(pageNo != 1) {
        	pageBar += "<a href='"+request.getContextPath()+"/notice/noticeList.ddg?searchType="+searchType+"&sizePerPage="+sizePerPage+"&searchWord="+searchWord+"&currentShowPageNo="+(pageNo-1)+"'>&lsaquo;</a>";
        }
        
         
        while(!(loop > blockSize ||pageNo > totalPage)) {
        	
        	if(pageNo == Integer.parseInt(currentShowPageNo)) {
        		pageBar += "<a href='#' class='active'>"+ pageNo +"</a>";
        	}else {
        		pageBar += "<a href='"+request.getContextPath()+"/notice/noticeList.ddg?searchType="+searchType+"&sizePerPage="+sizePerPage+"&searchWord="+searchWord+"&currentShowPageNo="+pageNo+"'>"+ pageNo +"</a>";
        	}
        	

        	
        	loop++;
        	pageNo++;
        	
        }// end of while()------------------
		

        
        // 다음 마지막 만들기
        
        if(pageNo <= totalPage ) {
        	pageBar += "<a href='"+request.getContextPath()+"/notice/noticeList.ddg?searchType="+searchType+"&sizePerPage="+sizePerPage+"&searchWord="+searchWord+"&currentShowPageNo="+pageNo+"'>&rsaquo;</a>";
        }
        
        
        pageBar += "<a href='"+request.getContextPath()+"/notice/noticeList.ddg?searchType="+searchType+"&sizePerPage="+sizePerPage+"&searchWord="+searchWord+"&currentShowPageNo="+totalPage+"'>&raquo;</a>";
		
        
        
		try {
			notice_allList = ndao.select_Notice_paging(paraMap);
		}catch(SQLException e) {
			e.printStackTrace();
			
			super.setRedirect(true);
			super.setViewPage(request.getContextPath()+"/WEB-INF/error.jsp");
		}
		
		
		request.setAttribute("pageBar", pageBar);
		
		request.setAttribute("notice_allList", notice_allList);
		
		request.setAttribute("searchType", searchType);
		
		request.setAttribute("searchWord", searchWord);
		
		request.setAttribute("sizePerPage", sizePerPage);
		
		request.setAttribute("currentShowPageNo", currentShowPageNo);
		
		

		super.setRedirect(false);
		super.setViewPage("/WEB-INF/notice/notice.jsp");

	}

}
