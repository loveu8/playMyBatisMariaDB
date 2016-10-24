package services.Impl;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.google.inject.Guice;
import com.google.inject.Injector;

import pojo.web.Member;
import pojo.web.MemberToken;
import pojo.web.signup.request.SignupRequest;
import services.WebService;

public class WebServiceImpl implements WebService {

  private WebService webService;

  public WebServiceImpl() {
    // 建立Inject modules.MyBatisModule()
    // 讓WebService藉由 injector 可以跟DB建立連線
    Injector injector = Guice.createInjector(new modules.MyBatisModule());
    this.webService = injector.getInstance(WebService.class);
  }

  public int signupNewMember(@Param("signupRequest") SignupRequest signupRequest) {
    return this.webService.signupNewMember(signupRequest);
  }

  public boolean checkMemberByEmail(String email) {
    return this.webService.checkMemberByEmail(email);
  }

  public boolean checkMemberByUsername(String username){
    return this.webService.checkMemberByUsername(username);
  }
  
  public Member findMemberByEmail(String email) {
    return this.webService.findMemberByEmail(email);
  }

  public Member findMemberByEmailAndUserName(@Param("email")String email , @Param("username")String username) {
    return this.webService.findMemberByEmailAndUserName(email , username );
  }
  
  public int genSignupAuthData(@Param("membetToken") Map<String , String> membetToken){
    return this.webService.genSignupAuthData(membetToken);
  }
  
  public int genMemberLoginLog(@Param("memberLoginData") Map<String , String> memberLoginData){
    return this.webService.genMemberLoginLog(memberLoginData);
  }
  
  public MemberToken getMemberTokenData(@Param("token") String token , @Param("type") String type){
    return this.webService.getMemberTokenData(token , type);
  }
  
  public Member findMemberByMemberNo(String memberNo){
    return this.webService.findMemberByMemberNo(memberNo);
  }
  
  public int updateMemberToken(@Param("memberNo") String memberNo , @Param("type") String type){
    return this.webService.updateMemberToken(memberNo,type);
  }
  
  public int updateMemberToAuthOk(String memberNo){
    return this.webService.updateMemberToAuthOk(memberNo);
  }
  
  public int genMemberChangeLog(Member member){
    return this.webService.genMemberChangeLog(member);
  }

}
