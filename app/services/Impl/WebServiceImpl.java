package services.Impl;

import org.apache.ibatis.annotations.Param;

import com.google.inject.Guice;
import com.google.inject.Injector;

import pojo.web.Member;
import pojo.web.signup.request.SignupRequest;
import services.WebService;

public class WebServiceImpl implements WebService {
	
	private WebService webService;
		
	public WebServiceImpl(){
		// 建立Inject modules.MyBatisModule()
		// 讓WebService藉由 injector 可以跟DB建立連線
		Injector injector = Guice.createInjector(new modules.MyBatisModule());
		this.webService = injector.getInstance(WebService.class);
	}
	
	public int signupNewMember(@Param("signupRequest") SignupRequest signupRequest){
		return this.webService.signupNewMember(signupRequest);
	}
	
	public boolean checkMemberByEmail(String email){
		return this.webService.checkMemberByEmail(email);
	}

	public Member findMemberByEmail(String email){
		return this.webService.findMemberByEmail(email);
	}
}