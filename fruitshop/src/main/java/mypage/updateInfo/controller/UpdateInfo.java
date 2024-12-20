package mypage.updateInfo.controller;

import java.sql.SQLException;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;

import mypage.updateInfo.model.UpdateInfoDAO;
import mypage.updateInfo.model.UpdateInfoDAO_imple;

public class UpdateInfo extends AbstractController {
	
	UpdateInfoDAO updatedao = new UpdateInfoDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		if(loginuser != null) {
			
			if("get".equalsIgnoreCase(request.getMethod())) {
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/mypage/updateInfo.jsp");
				
			} 
			else if("post".equalsIgnoreCase(request.getMethod())) {
			
				String userid = request.getParameter("userid");
				String passwd = request.getParameter("passwd");
				String name = request.getParameter("name");
		        String email = request.getParameter("email");
		        
		        String tel1 = request.getParameter("tel1");
		        String tel2 = request.getParameter("tel2");
		        String tel3 = request.getParameter("tel3");
		        String tel = tel1+tel2+tel3;
		        
		        String postcode = request.getParameter("postcode");
		        String address = request.getParameter("address");
		        String extraaddress = request.getParameter("extraaddress");
		        if(extraaddress == null || "".equals(extraaddress.trim())) {
		        	extraaddress = " ";
		        }
		        String detailaddress = request.getParameter("detailaddress");
		        if(detailaddress == null || "".equals(detailaddress.trim())) {
		        	detailaddress = " ";
		        }

		        MemberVO member = new MemberVO();
		        member.setUserid(userid);
		        member.setPasswd(passwd);
		        member.setName(name);
		        member.setEmail(email);
		        member.setTel(tel);
		        member.setPostcode(postcode);
		        member.setAddress(address);;
		        member.setDetailaddress(detailaddress);
		        member.setExtraaddress(extraaddress);

		        try {
		        	int n = updatedao.updateMember(member);
			
			        if(n==1) {
			  
			        	loginuser.setName(member.getName());
			        	loginuser.setEmail(member.getEmail());
			        	loginuser.setTel(member.getTel());
			        	loginuser.setPostcode(member.getPostcode());
			        	loginuser.setAddress(member.getAddress());
			        	loginuser.setDetailaddress(member.getDetailaddress());
			        	loginuser.setExtraaddress(member.getExtraaddress());
			     
						super.setRedirect(false);
						super.setViewPage("/WEB-INF/mypage/updateInfo.jsp");
			        }
			        
				} catch (SQLException e) {
					e.printStackTrace();
					String message = "정보 수정 실패";
					String loc = request.getContextPath()+"/login/login.ddg";
					
					request.setAttribute("message", message);
				    request.setAttribute("loc", loc);
				        
				    super.setRedirect(false);
				    super.setViewPage("/WEB-INF/common/msg.jsp");
				}
		      
			}
			else {
				super.setRedirect(true);
				super.setViewPage(request.getContextPath()+"/index.ddg");
			}

		}
		else {
			super.setRedirect(true);
			super.setViewPage(request.getContextPath()+"/login/login.ddg");
		}
		
		
		
	}

}
