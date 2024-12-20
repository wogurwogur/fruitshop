package member.controller;

import java.sql.SQLException;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import member.model.MemberDAO;
import member.model.MemberDAO_imple;

public class MemberRegister extends AbstractController {

	MemberDAO mdao = new MemberDAO_imple();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		if(loginuser == null ) {
		
			if("get".equalsIgnoreCase(request.getMethod())) {
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/member/memberRegister.jsp");
				
			} else {
				
				
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
		        if(extraaddress == null) {
		        	extraaddress = " ";
		        }
		        String detailaddress = request.getParameter("detailaddress");
		        if(detailaddress == null) {
		        	detailaddress = " ";
		        }
		        String gender = request.getParameter("gender");
		        String birthday = request.getParameter("birthday");
		        
		        MemberVO member = new MemberVO();
		        member.setUserid(userid);
		        member.setPasswd(passwd);
		        member.setName(name);
		        member.setEmail(email);
		        member.setTel(tel);
		        member.setPostcode(postcode);
		        member.setAddress(address);
		        member.setDetailaddress(detailaddress);
		        member.setExtraaddress(extraaddress);
		        member.setGender(gender);
		        member.setBirthday(birthday);
		  
		        try {
		        	int n = mdao.registerMember(member);
			
			        if(n==1) {
			        	
			        	request.setAttribute("userid", member.getUserid());
			        	request.setAttribute("passwd", member.getPasswd());
			        	
			        	super.setRedirect(false);
					    super.setViewPage("/WEB-INF/login/memberRegister_after_autoLogin.jsp");
			        }
			        
				} catch (SQLException e) {
					e.printStackTrace();
					String message = "회원가입 실패";
					
					String loc = request.getContextPath()+"/login/login.ddg";
					
					request.setAttribute("message", message);
				    request.setAttribute("loc", loc);
				        
				    super.setRedirect(false);
				    super.setViewPage("/WEB-INF/common/msg.jsp");
				}

			}
		}
		else {
			super.setRedirect(true);
			super.setViewPage(request.getContextPath()+"/index.ddg");
		}

	}

}
