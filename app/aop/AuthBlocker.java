package aop;

import org.aopalliance.intercept.MethodInvocation;

import play.Logger;
import play.mvc.Result;
import pojo.web.Member;
import pojo.web.MemberStatus;
import pojo.web.auth.request.AuthRequest;

public class AuthBlocker extends CommonBlocker{

  
  @Override
  public Result invoke(MethodInvocation invocation) throws Throwable {
    super.invoke(invocation);
    flash().clear();
    
    // Step 2 
    AuthRequest request = this.getAuthRequest();
        
    // Step 2.1
    if(request == null){
      flash().put("errorLogin", "請確認輸入的登入帳號與密碼是否正確");
      return redirect(controllers.routes.WebController.login().url());
    }
    
    
    // Step 2.2
    String email = request.getEmail();
    String password = request.getPassword();
    boolean isMember = false;
    
    try{
      isMember = webService.checkMemberByEmail(email);
    } catch(Exception e){
      e.printStackTrace();
      flash().put("errorLogin", "系統忙碌中，請稍後再嘗試!");
      return redirect(controllers.routes.WebController.login().url());
    }
    
    if(!isMember){
      flash().put("errorLogin", "您尚未註冊成為會員!");
      return redirect(controllers.routes.WebController.login().url());
    }
    
    // Step 2.3
    Member member = webService.findMemberByEmail(email);
    play.Logger.info("email = " + email + ", password = " + password 
                     + ", member Status = " + member.getStatus() 
                     + ", db password = " +  member.getPassword());
    
    if(MemberStatus.S1.getStatus().equals(member.getStatus())){
      flash().put("errorLogin", "您的帳號尚未認證成功!");
      return redirect(controllers.routes.WebController.login().url());
    }
    
    if(MemberStatus.S3.getStatus().equals(member.getStatus())){
      flash().put("errorLogin", "您的帳號已被停權!");
      return redirect(controllers.routes.WebController.login().url());
    }
    
    if(!password.equals(member.getPassword())){
      flash().put("errorLogin", "密碼錯誤，請再次確認密碼是否正確");
      return redirect(controllers.routes.WebController.login().url());
    }
    
    // Step 2.4
    
    return (Result) invocation.proceed();
  }
  
  
  // Step 2 : 取得登入請求
  private AuthRequest getAuthRequest() {
    AuthRequest request = null;
    try {
      request = formFactory.form(AuthRequest.class).bindFromRequest().get();
    } catch (Exception e) {
      Logger.error("表單內容非登入資訊，轉換類別錯誤，回傳空物件");
    }
    return request;
  }
  
}

