package member.model;

import java.sql.SQLException;
import java.util.Map;

import member.domain.MemberVO;

public interface MemberDAO {

	// 로그인 처리해주는 메소드
	MemberVO login(Map<String, String> paraMap) throws SQLException;

	// 회원가입을 처리해주는 메소드
	int registerMember(MemberVO member) throws SQLException;

}
