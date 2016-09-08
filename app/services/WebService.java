package services;

import org.apache.ibatis.annotations.Param;

import pojo.web.Member;
import pojo.web.signup.request.SignupRequest;

public interface WebService {
	int signupNewMember(@Param("signupRequest") SignupRequest signupRequest);

	Member findMemberByEmail(String email);
}