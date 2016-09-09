package services;

import org.apache.ibatis.annotations.Param;

import pojo.web.Member;
import pojo.web.signup.request.SignupRequest;

public interface WebService {
	
	public int signupNewMember(@Param("signupRequest") SignupRequest signupRequest);
	
	public boolean checkMemberByEmail(String email);

	public Member findMemberByEmail(String email);
}