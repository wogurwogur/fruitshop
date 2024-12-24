package mypage.updateInfo.model;

import java.sql.SQLException;
import java.util.Map;

import member.domain.MemberVO;

public interface UpdateInfoDAO {

	// 이메일 중복 검사를 처리해주는 메소드 + 원래 본인 이메일 제외
	Boolean emailDuplicateCheck(String newEmail) throws SQLException;

	// 회원정보 수정 메소드
	int updateMember(MemberVO member) throws SQLException;

	// 회원 탈퇴 메소드 (status 변경)
	int memberWithdrawal(int user_no) throws SQLException;
	
}
