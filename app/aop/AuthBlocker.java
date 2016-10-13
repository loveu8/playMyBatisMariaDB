package aop;

import java.util.Map;

import org.aopalliance.intercept.MethodInvocation;

import play.Logger;
import play.libs.Json;
import play.mvc.Result;
import pojo.web.Member;
import pojo.web.MemberLoginStatus;
import pojo.web.MemberStatus;
import pojo.web.auth.MemberSession;
import pojo.web.auth.request.AuthRequest;
import utils.session.Utils_Session;
import utils.signup.Utils_Signup;

public class AuthBlocker extends CommonBlocker{

  /**
   * <pre>
   * Step 1     : 檢查使用者Cookie是否有資料
   * 
   * Step 1.1   : 沒有Cookie直接跳到Step2
   * 
   * Step 1.2   : 有Cookie ，檢查cache是否有資料
   * 
   * Step 1.2.1 : cache 有資料，根據sessionId , 查詢我們 cache , 解密sessionSign是否正確，是否逾期
   *              沒通過 => 清除使用者Cookie，重新Step2登入動作 (1.2.2.2)
   *              通過    => 24小時內更新過，不需要更新，直接登入 
   *                    => 24小時內尚未更新過，更新並延長期限
   *                       Session table, login log, server cache, bowser cookie
   * 
   * Step 1.2.2 : cache 沒資料，進行查詢Session表單, 是否正確，是否逾期
   *              沒資料   => 清除使用者Cookie，重新Step2登入動作 (1.2.2.1)
   *              沒通過   => 清除使用者Cookie，重新Step2登入動作 (1.2.2.2)
   *              通過       => 24小時內更新過，不需要更新，直接登入 
   *                     => 24小時內尚未更新過，更新並延長期限
   *                       Session table, login log, server cache, bowser cookie
   *          
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
    
    // Step 1
    Utils_Session utilsSession = new Utils_Session(); 
    boolean isClientHaveCookie = utilsSession.isClinetHaveCookie(request());
    play.Logger.info("isClientHaveCookie = " + isClientHaveCookie);
    
    // Step 1.2
    if(isClientHaveCookie){
      String clientSessionId = utilsSession.getClientSession(request());
      String clientSessionSign = utilsSession.getClientSessionSign(request());
      boolean isCacheHaveThisSession = utilsSession.isCacheHaveThisSession(cache,clientSessionId);
      
      play.Logger.info("clientSessionId   = " + clientSessionId);
      play.Logger.info("clientSessionSign = " + clientSessionSign);
      play.Logger.info("isCacheHaveThisSession = " + isCacheHaveThisSession);

      // Step 1.2.1
      if(isCacheHaveThisSession){
        
        MemberSession serverCacheMemberSession = utilsSession.getServerCacheData(cache,clientSessionId);

        // 沒通過 
        if(!clientSessionSign.equals(serverCacheMemberSession.getSessionSign())){
          utilsSession.clearErrorClientCookie(response());
          flash().put("errorLogin", "請先執行登入動作，謝謝!(0x1.2.1.1)");
          play.Logger.warn("Step 1.2.1 : cache 有資料，但與使用者Cookie比對不符，沒通過檢查。");
          return redirect(controllers.routes.WebController.login().url());
        } 
        
        play.Logger.info("origin server cache memberSession = " + Json.toJson(serverCacheMemberSession));
        
        // 通過，超過24小時
        if(utilsSession.isRewriteCookie(serverCacheMemberSession.getExpiryDate())){
          play.Logger.info("cache有資料比對通過，但超過24小時未更新Cookie，進行更新");
          try{
            Member member = this.webService.findMemberByMemberNo(serverCacheMemberSession.getMemberNo());
            this.cache.remove(clientSessionId);
            writeMemberCookieAndSession( utilsSession, member);
          } catch (Exception e){
            e.printStackTrace();
            flash().put("errorLogin", "系統忙碌中，請稍後再嘗試!");
            return redirect(controllers.routes.WebController.login().url());
          }
        }
        
        return (Result) invocation.proceed();
        
      } else {
        // Step 1.2.2
        MemberSession dbMemberSession = this.getMemberSession(clientSessionId);

        // 沒有資料
        if(dbMemberSession == null){
          utilsSession.clearErrorClientCookie(response());
          flash().put("errorLogin", "請先執行登入動作，謝謝!(0x1.2.2.1)");
          play.Logger.warn("Step 1.2.2.1 : db沒資料，無法比對使用者資料。");
          return redirect(controllers.routes.WebController.login().url());
        }

        // 沒通過
        if(!clientSessionSign.equals(dbMemberSession.getSessionSign())){
          utilsSession.clearErrorClientCookie(response());
          flash().put("errorLogin", "請先執行登入動作，謝謝!(0x1.2.2.2)");
          play.Logger.warn("Step 1.2.2.2 : db有資料，但與使用者Cookie比對不符，沒通過檢查。");
          return redirect(controllers.routes.WebController.login().url());
        }
        
        play.Logger.info("origin db memberSession = " + Json.toJson(dbMemberSession));
        
        // 通過，超過24小時
        if(utilsSession.isRewriteCookie(dbMemberSession.getExpiryDate())){
          play.Logger.info("db有資料比對通過，但超過24小時未更新Cookie，進行更新");
          try{
            Member member = this.webService.findMemberByMemberNo(dbMemberSession.getMemberNo());
            writeMemberCookieAndSession( utilsSession, member);
          } catch (Exception e){
            e.printStackTrace();
            flash().put("errorLogin", "系統忙碌中，請稍後再嘗試!");
            return redirect(controllers.routes.WebController.login().url());
          }
        }
        
        return (Result) invocation.proceed();
      }
    } else {
      // Step 1.1 to Next Step2
    }
    
    
    // Step 2 
    AuthRequest request = this.getAuthRequest();
        
    // Step 2.1
    if(request == null){
      flash().put("errorLogin", "請先執行登入動作，謝謝!(0x2.1)");
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
      flash().put("errorLogin", "您尚未註冊成為會員!(0x2.2)");
      return redirect(controllers.routes.WebController.login().url());
    }
    
    // Step 2.3
    Member member = null;
    try {
      member = webService.findMemberByEmail(email);
      play.Logger.info("email = " + email + ", password = " + password 
                       + ", member Status = " + member.getStatus() 
                       + ", db password = " +  member.getPassword());
    }catch (Exception e){
      e.printStackTrace();
      flash().put("errorLogin", "系統忙碌中，請稍後再嘗試!");
      return redirect(controllers.routes.WebController.login().url());
    }
    
    if(MemberStatus.S1.getStatus().equals(member.getStatus())){
      flash().put("errorLogin", "您的帳號尚未認證成功!(0x2.3)");
      return redirect(controllers.routes.WebController.login().url());
    }
    
    if(MemberStatus.S3.getStatus().equals(member.getStatus())){
      flash().put("errorLogin", "您的帳號已被停權!(0x2.3)");
      return redirect(controllers.routes.WebController.login().url());
    }
    
    if(!password.equals(member.getPassword())){
      flash().put("errorLogin", "密碼錯誤，請再次確認密碼是否正確(0x2.3)");
      return redirect(controllers.routes.WebController.login().url());
    }
    
    // Step 2.4
    try{
      writeMemberCookieAndSession(utilsSession,member);
    } catch (Exception e){
      e.printStackTrace();
      flash().put("errorLogin", "系統忙碌中，請稍後再嘗試!");
      return redirect(controllers.routes.WebController.login().url());
    }
    
    return (Result) invocation.proceed();
  }
  
  /** 
   * <pre>
   * 共用
   * 寫入Member Session Table 
   * 寫入Member Login log Table
   * 寫入Server Cache 與 Client cookie
   *</pre>
   */
  private void writeMemberCookieAndSession(Utils_Session utilsSession,Member member){
    // 寫入Member Session Table 
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
    utilsSession.setMemberCookieAndCache(response(), cache, memberSession, utilsSession.maxAge, "", "", false);
    play.Logger.info("isMemberSessionOk   = " + isMemberSessionOk);
    play.Logger.info("isMemberLoginLogOk  = " + isMemberLoginLogOk);
    play.Logger.info("clientSessionId     = " + utilsSession.getClientSession(request()));
    play.Logger.info("clientsessionSign   = " + utilsSession.getClientSessionSign(request()));
    play.Logger.info("serverCache         = " + this.cache.get(memberSession.getSessionId()));
  }
  
  
  // Step 2 : 取得登入請求
  private AuthRequest getAuthRequest() {
    try {
      AuthRequest authRequest = formFactory.form(AuthRequest.class).bindFromRequest().get();
      play.Logger.info("authRequest = "+ Json.toJson(authRequest));
      if(authRequest.getEmail() == null && authRequest.getPassword() == null){
        return null;
      }
      return authRequest;
    } catch (Exception e) {
      Logger.error("表單內容非登入資訊，轉換類別錯誤，回傳空物件");
    }
    return null;
  }
  
  
  // 查詢是否有該會員Session資料
  private MemberSession getMemberSession(String sessionId){
    try{
      MemberSession memberSession = this.webService.getMemberSession(sessionId);
      return memberSession != null ? memberSession : null;
    } catch (Exception e){
      e.printStackTrace();
      return null;
    }
  }
  
  
}

