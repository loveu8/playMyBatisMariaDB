package aop;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.aopalliance.intercept.MethodInvocation;

import play.Logger;
import play.libs.Json;
import play.mvc.Result;
import pojo.web.Member;
import pojo.web.MemberLoginStatus;
import pojo.web.MemberStatus;
import pojo.web.auth.ClientCookie;
import pojo.web.auth.MemberSession;
import pojo.web.auth.request.AuthRequest;
import utils.enc.AESEncrypter;
import utils.session.Utils_Session;
import utils.signup.Utils_Signup;

public class AuthBlocker extends CommonBlocker{

  /**
   * <pre>
   * Step 1     : 檢查使用者Cookie是否有資料
   * 
   * Step 1.2   : cache 檢查是否有資料
   * 
   * Step 1.2.1 : cache 沒資料，進行查詢Session表單, 是否正確，是否逾期
   *              沒資料   => 執行Step2登入動作
   *              沒通過   => 執行Step2登入動作
   *              通過       => 更新並延長期限Session table, login log, server cache, bowser cookie
   *          
   *              
   * Step 1.2.2 : cache 有資料，根據sessionId , 查詢我們 cache , sessionSign是否正確，是否逾期
   *              沒通過 => 執行Step2登入動作
   *              通過    => 24小時內更新過，不需要更新，直接登入 
   *                       寫入登入紀錄(login log)
   *                    => 24小時內尚未更新過，更新並延長期限
   *                       Session table, login log, server cache, bowser cookie
   *            
   * Step 2     : 普通一般登入步驟
   * 
   * Step 2.1   : 檢察登入資訊是否符合格式
   *              不符合表單 => 顯示提示訊息
   *              
   * Step 2.2   : 檢查是否有該會員資料
   *              無會員資料  => 畫面顯示無註冊資料
   *
   * Step 2.3   : 有會員資料，認證尚未通過，停權，或密碼錯誤
   *              => 顯示認證尚未通過，或停權，或密碼錯誤
   *              
   * Step 2.4   : 通過以上檢查
   *              => 新增 Session table, login log, server cache, bowser cookie
   *
   * Note : 停權時，要特別注意，要刪除掉cache與session表單的登入資料，以免錯誤
   *</pre>
   */
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
    try{
      
      // 寫入Member Session Table
      Utils_Session utilsSession = new Utils_Session();  
      MemberSession memberSession = utilsSession.genMemberSession(member);
      int isMemberSessionOk = this.webService.genMemberSession(memberSession);  
      
      // 寫入Member Login log Table
      Utils_Signup utilsSignup = new Utils_Signup();
      Map<String , String> memberLoginLogData      = utilsSignup.genMemberLoginData(member.getMemberNo() ,
                                        "PC" , 
                                        request().remoteAddress() , 
                                        MemberLoginStatus.S2.getStatus());
      int isMemberLoginLogOk = this.webService.genMemberLoginLog(memberLoginLogData);

      // 寫入Server Cache 與 Client cookie
      utilsSession.setMemberCookieAndCache(response(), cache, memberSession, utilsSession.expiryTime, "", "", false);
      play.Logger.info("isMemberSessionOk   = " + isMemberSessionOk);
      play.Logger.info("isMemberLoginLogOk  = " + isMemberLoginLogOk);
      play.Logger.info("clientSessionId     = " + session().get("sessionId"));
      play.Logger.info("clientsessionSign   = " + session().get("sessionSign"));
      play.Logger.info("serverCache         = " + this.cache.get(memberSession.getSessionId()));
    } catch (Exception e){
      e.printStackTrace();
      flash().put("errorLogin", "系統忙碌中，請稍後再嘗試!");
      return redirect(controllers.routes.WebController.login().url());
    }
    
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

