package controllers;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import annotation.AuthCheck;
import play.Logger;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import pojo.web.Member;
import pojo.web.MemberToken;
import pojo.web.MemberLoginStatus;
import pojo.web.MemberStatus;
import pojo.web.MemberTokenType;
import pojo.web.email.Email;
import pojo.web.signup.request.ResetPasswordRequest;
import pojo.web.signup.request.SignupRequest;
import pojo.web.signup.verific.VerificFormMessage;
import services.WebService;
import utils.signup.Utils_Signup;
import views.html.web.index;
import views.html.web.loginSignup.*;
import utils.mail.Utils_Email;
import utils.session.Utils_Session;


public class WebController extends Controller {

  
  // 首頁
  public Result index() {
    return ok(index.render());
  }
  
  
  // 登入
  public Result login() {
    return ok(login.render());
  }

  // 註冊頁面
  public Result signup() {
    // 清除暫存錯誤訊息
    // flash().clear();
    return ok(signup.render());
  }


  @Inject
  // 相依性注入Play的formFactory，可參考reference介紹
  FormFactory formFactory;

  @Inject
  WebService webService;

  /**
   * <pre>
   * 進行註冊
   * 
   * Step 1 : 取得表單註冊資料，若錯誤，回到註冊頁面，警告錯誤訊息。
   * Step 2 : 進行表單驗證，是否正確。若錯誤，回到註冊頁面顯示錯誤訊息。
   * Step 3 : 檢核通過，新增會員資料，且尚未認證。
   * Step 4 : 註冊新增成功，新增認證連結資料。
   * Step 5 : 新增會員記錄檔。
   * Step 6 : 進行寄送認證信動作。
   * Step 7 : 以上都順利完成，導入成功註冊頁面。
   * 
   * </pre>
   */
  public Result goToSignup() {

    // 清除暫存錯誤訊息
    flash().clear();
    
    // Step 1
    SignupRequest request = this.getSignupRequest();
    if (request == null) {
      flash().put("errorForm","註冊資料錯誤，請重新嘗試!!");
      return ok(signup.render());
    }
    
    // Step 2
    Map<String, VerificFormMessage> verificInfo = this.checkSingupRequest(request);
    for (String key : verificInfo.keySet()) {
      // 發現驗證沒過，放入錯誤訊息
      if (!"200".equals(verificInfo.get(key).getStatus())) {
        flash().put(key, verificInfo.get(key).getStatusDesc());
      }
    }
    if(!flash().isEmpty()){
      return ok(signup.render());
    }
 
    try {
      // Step 3
      int isSignupOk = webService.signupNewMember(request);
      if(isSignupOk == 0){
        flash().put("signupError", "註冊會員失敗，請重新註冊，謝謝。");
        return ok(signup.render());
      }
            
      // Step 4
      Utils_Signup utils_Signup = new Utils_Signup();
      Member newMember = webService.findMemberByEmail(request.getEmail());
      String signupAuthString = utils_Signup.genSignupAuthString(newMember.getEmail());
      
      Map<String , String> memberToken = new HashMap<String , String>();
      memberToken.put("memberNo", newMember.getMemberNo());
      memberToken.put("tokenString", signupAuthString);
      memberToken.put("type", MemberTokenType.Signup.toString());
      int isSingAuthStringOk = webService.genTokenData(memberToken);
      
      // Step 5
      Map<String , String> memberLoginData 
          = utils_Signup.genMemberLoginData(newMember.getMemberNo() ,
                                            "PC" , 
                                            request().remoteAddress() , 
                                            MemberLoginStatus.S1.getStatus());
      int isMemberLoginLogOk = webService.genMemberLoginLog(memberLoginData);
      
      // Step 6
      Utils_Email utils_Email = new Utils_Email();
      Email email = utils_Email.genSinupAuthEmail(newMember, signupAuthString);
      boolean isSeadMailOk = utils_Email.sendMail(email);
      
      // Step 7
      if(isSingAuthStringOk > 0 && isMemberLoginLogOk > 0 && isSeadMailOk ){
        return ok(signupOk.render());
      } else {
        flash().put("signupError", "Opss...寄送認證信件發生錯誤，請使用重發認證信功能，完成認證動作，謝謝。");
        return ok(signup.render());
      }
    } catch (Exception e) {
      e.printStackTrace();
      flash().put("signupError", "註冊會員失敗，請重新註冊，謝謝。");
      return ok(signup.render());
    }
  }

  
  // Step 1 : 取得註冊資訊請求
  private SignupRequest getSignupRequest() {
    SignupRequest request = null;
    try {
      request = formFactory.form(SignupRequest.class).bindFromRequest().get();
      Logger.info("before , new member request data = " + Json.toJson(request));
    } catch (Exception e) {
      Logger.error("表單內容非註冊資訊，轉換類別錯誤，回傳空物件");
    }
    return request;
  }

  
  // Step 2 : 檢查註冊資訊
  private Map<String, VerificFormMessage> checkSingupRequest(SignupRequest request) {
    
    boolean isRegEmail = true;
    boolean isUsedUsername = true;
    try{
      isRegEmail = webService.checkMemberByEmail(request.getEmail());
      isUsedUsername = webService.checkMemberByUsername(request.getUsername());
    } catch(Exception e){
      e.printStackTrace();
    }
    Map<String, VerificFormMessage> verificInfo 
        = new Utils_Signup().checkSingupRequest(request , isRegEmail , isUsedUsername);
    Logger.info("verificInfo = " + Json.toJson(verificInfo));
    return verificInfo;
  }
  
  
  // test signupOk
  public Result signupOk(){
    return ok(signupOk.render());
  }
  
  
  /**
   * <pre>
   * 檢查註冊認證信連結
   * 
   * Step1   : 檢查認證是否存在。
   * Step2   : 檢查會員是否已經認證過。
   * Step3   : 檢查連結是否使用過。
   * Step4   : 檢查是否有逾期。
   * Step5   : 檢查通過，開始更新與新增相關表單。
   * Step5.1 : 更新會員認證表單
   * Step5.2 : 更新會員表單
   * Step5.3 : 新增會員紀錄表單
   * 
   * </pre>
   */
  public Result authMember(String auth){
    
    // 清除暫存錯誤訊息
    flash().clear();

    // Step 1
    MemberToken memberToken = null ;
    try{
     memberToken = webService.getMemberTokenData(auth , MemberTokenType.Signup.toString());
    } catch(Exception e){
      e.printStackTrace();
    }
    
    if(memberToken == null){
      flash().put("authError", "認證連結有誤，請重新點選信中認證連結，或使用重發認證信，謝謝。");
      play.Logger.warn("memberToken  = " + Json.toJson(memberToken));
      return ok(checkMemberAuth.render());
    }
    
    // Step 2
    Member member = null;
    try{
      // 認證連結有資料，使用會員編號，查詢會員資料。
     member = webService.findMemberByMemberNo(memberToken.getMemberNo());
    } catch(Exception e){
      e.printStackTrace();
      flash().put("authError", "系統忙碌中，請稍後再次嘗試!");
      return ok(checkMemberAuth.render());
    }
    
    if(!MemberStatus.S1.getStatus().equals(member.getStatus())){
      flash().put("authError", "您的帳號，已經認證成功，不需再認證，謝謝。");
      play.Logger.warn("member      = " + Json.toJson(member));
      return ok(checkMemberAuth.render());
    }
    
    // Step 3
    boolean isUse       = memberToken.getIsUse(); // 認證字串是否使用過
    if (isUse){
      flash().put("authError", "該連結已成功認證，不需要再次認證，謝謝。");
      return ok(checkMemberAuth.render());
    } 
    
    // Step 4
    long    dbTime      = Long.parseLong(memberToken.getDbTime());       // 資料庫時間
    long    expiryDate  = Long.parseLong(memberToken.getExpiryDate());   // 逾期時間
    if(dbTime > expiryDate){
      flash().put("authError", "認證時間已經逾期，請重新使用重發認證信功能謝謝。");
      play.Logger.warn("dbTime      = " + dbTime);
      play.Logger.warn("expiryDate  = " + expiryDate);
      return ok(checkMemberAuth.render());
    } 

    // Step 5
    try{
      int isUpdateMemberTokenOk = webService.updateMemberToken(member.getMemberNo(), MemberTokenType.Signup.toString());
      int isUpdateMemberMainOk = webService.updateMemberToAuthOk(member.getMemberNo());
      int isGenMemberChangeLogOk = webService.genMemberChangeLog(member);
      play.Logger.info("isUpdateMemberAuthOk  = " + isUpdateMemberTokenOk);
      play.Logger.info("isUpdateMemberMainOk  = " + isUpdateMemberMainOk);
      play.Logger.info("isGenMemberChangeLogOk = " + isGenMemberChangeLogOk);
    } catch(Exception e){
      e.printStackTrace();
    }
    return ok(checkMemberAuth.render());
  }
  
  
  /**
   *  重發認證信頁面
   */
  public Result resendAuthEmail(){
    return ok(resendAuthEmail.render());
  }
  
  
  /**
   * <pre>
   *  重發認證信檢查與寄送
   *  
   *  Step 1 : 檢查輸入的資料是否符合需要的格式
   *  Step 2 : 檢查輸入的信箱與使用名稱，是否有該會員資料
   *  Step 3 : 會員資料，檢查是否已認證
   *  Step 4 : 尚未認證，再次補寄送認證信
   * 
   * </pre>
   */
  public Result goToResendAuthEmail(){
    
    // 清除暫存錯誤訊息
    flash().clear();
    
    SignupRequest request = this.getSignupRequest();
    if(request==null){
      flash().put("error", "輸入資料錯誤，請重新嘗試!!");
      return ok(resendAuthEmail.render());
    }
    
    // Step 2
    Member member = null;
    String email = request.getEmail();
    String username = request.getUsername();
    try{
      member = webService.findMemberByEmailAndUserName(email, username);
    } catch(Exception e){
      e.printStackTrace();
      flash().put("error", "系統忙碌中，請稍候再嘗試，謝謝。");
      return ok(resendAuthEmail.render());
    }
    
    // Step 2 
    if(member == null){
      flash().put("error", "查無註冊資料，請確認資料是否填寫正確，謝謝。");
      return ok(resendAuthEmail.render());
    }
    
    // Step 3
    if(!MemberStatus.S1.getStatus().equals(member.getStatus())){
      flash().put("error", "您的帳號，已經認證成功，不需再重新認證，謝謝。");
      play.Logger.warn("member      = " + Json.toJson(member));
      return ok(resendAuthEmail.render());
    }
    
    // Step 4
    try{
      String signupAuthString = new Utils_Signup().genSignupAuthString(member.getEmail());
      Map<String , String> memberToken = new HashMap<String , String>();
      memberToken.put("memberNo", member.getMemberNo());
      memberToken.put("tokenString", signupAuthString);
      memberToken.put("type", MemberTokenType.Signup.toString());
      int isSignupAuthStringOk = webService.genTokenData(memberToken);
      
      Utils_Email utils_Email = new Utils_Email();
      Email authMail = utils_Email.genSinupAuthEmail(member, signupAuthString);
      boolean isSeadMailOk = utils_Email.sendMail(authMail);
      
      play.Logger.info("isSignupAuthStringOk = " + isSignupAuthStringOk +" , isSeadMailOk = " + isSeadMailOk);
    } catch(Exception e){
      e.printStackTrace();
    }
    flash().put("ok", "已重發認證信，請至註冊信箱收取認證信，謝謝。");
    return ok(resendAuthEmail.render());
  }
      
  @AuthCheck
  public Result doLogin(){
    return ok("登入成功");
  }
  
  // 登出
  public Result logout(){
    new Utils_Session().clearClientCookie(response());
    return redirect(controllers.routes.WebController.index().url());
  }
  
  // 忘記密碼頁面
  public Result forgotPassword(){
    return ok(forgotPassword.render());
  }
  
  /** 
   * <pre>
   * 執行忘記密碼檢查與寄送動作
   * 
   * Step 1 : 確認表單，填寫是信箱
   * Step 2 : 確認是否是否存在該會員資料
   * Step 3 : 確認是否停權
   * Step 4 : 確認是否尚未認證
   * OK : 確認會員正常使用中，產生忘記密碼Token連結後，寄送信箱
   * </pre>
   */
  public Result doForgotPassword(){
    
    // 清除暫存錯誤訊息
    flash().clear();
    
    // Step 1
    String email = null;
    try {
      email = formFactory.form().bindFromRequest().get().getData().get("email").toString();
      Logger.info("before , new forgotPassword request email = " +  email);
    } catch (Exception e) {
      e.printStackTrace();
      flash().put("error", "系統忙碌中，請稍候再嘗試，謝謝。");
      return redirect(controllers.routes.WebController.forgotPassword().url());
    } finally {
      if(email==null){
        Logger.error("表單內容非填寫信箱內容");
        flash().put("error", "請重新填寫註冊信箱，謝謝。");
        return redirect(controllers.routes.WebController.forgotPassword().url());
      }
    }
    
    // Step 2
    Member member = null;
    try{
      member = webService.findMemberByEmail(email);
    } catch(Exception e){
      e.printStackTrace();
      flash().put("error", "系統忙碌中，請稍候再嘗試，謝謝。");
      return ok(forgotPassword.render());
    } finally {
      if(member == null){
        Logger.error("查無註冊資料");
        flash().put("error", "查無註冊資料，請確認資料是否填寫正確，謝謝。");
        return redirect(controllers.routes.WebController.forgotPassword().url());
      }
    }

    // Step 3
    if(MemberStatus.S3.getStatus().equals(member.getStatus())){
      flash().put("error", "您的帳號，已被停權使用，無法使用忘記密碼功能，謝謝。");
      play.Logger.warn("member      = " + Json.toJson(member));
      return redirect(controllers.routes.WebController.forgotPassword().url());
    }

    // Step 4
    if(MemberStatus.S1.getStatus().equals(member.getStatus())){
      flash().put("error", "您的帳號，尚未認證成功，無法使用忘記密碼功能，謝謝。");
      play.Logger.warn("member      = " + Json.toJson(member));
      return redirect(controllers.routes.WebController.forgotPassword().url());
    }
    
    //OK
    String forgotPasswordTokenString = "";
    try{
      forgotPasswordTokenString = new Utils_Signup().genForgotPasswordTokenString(member);
      Map<String , String> memberToken = new HashMap<String , String>();
      memberToken.put("memberNo", member.getMemberNo());
      memberToken.put("tokenString", forgotPasswordTokenString);
      memberToken.put("type", MemberTokenType.ForgotPassword.toString());
      int isforgotPasswordStringOk = webService.genTokenData(memberToken);
      
      Utils_Email utils_Email = new Utils_Email();
      Email forgotPasswordMail = utils_Email.genForgotPasswordEmail(member, forgotPasswordTokenString);
      boolean isSeadMailOk = utils_Email.sendMail(forgotPasswordMail);
      play.Logger.info("isforgotPasswordStringOk = " + isforgotPasswordStringOk +" , isSeadMailOk = " + isSeadMailOk);
      
    } catch (Exception e){
      e.printStackTrace();
      flash().put("error", "系統忙碌中，請稍候再嘗試，謝謝。");
      play.Logger.info("token = " + forgotPasswordTokenString);
      return redirect(controllers.routes.WebController.forgotPassword().url());
    }
    
    flash().put("ok", "已發送重設密碼信件至您的信箱，謝謝。");
    return redirect(controllers.routes.WebController.forgotPassword().url());
  }
  
  
  /**
   * <pre>
   * 重設密碼信件寄送回來後
   * Step 1 : 檢查重設密碼信件連結是否有資料
   * Step 2 : 檢查Token，是否可以查詢到會員資料
   * Step 3 : 檢查Token，是否使用過了
   * OK : 檢查通過，可以進行重設密碼動作，並把Token儲存在表單裡
   * </pre>
   */
  public Result resetPassword(){
    
    // 清除暫存錯誤訊息
    flash().clear();
    
    // Step 1
    String token = "";
    try{
      token = request().getQueryString("token");
    } catch (Exception e){
      e.printStackTrace();
      flash().put("error", "重設密碼連結有誤，請確認是否有點選正確，謝謝。0x21");
      return ok(resetPassword.render(""));
    }

    // Step 2
    MemberToken memberToken = null ;
    try{
      token = request().getQueryString("token");
      memberToken = webService.getMemberTokenData(token , MemberTokenType.ForgotPassword.toString());
    } catch(Exception e){
      e.printStackTrace();
      flash().put("error", "系統忙碌中，請稍候再嘗試，謝謝。");
      return ok(resetPassword.render(""));
    } finally {
      if(memberToken == null){
        flash().put("error", "重設密碼連結有誤，請確認是否有點選正確，謝謝。0x22");
        play.Logger.warn("memberToken  = " + Json.toJson(memberToken));
        return ok(resetPassword.render(""));
      }
    }
    
    // Step 3
    if(memberToken.getIsUse()){
      flash().put("error", "該忘記密碼連結已失效，若要重設密碼，請使用忘記密碼功能，謝謝。");
      play.Logger.warn("memberToken  = " + Json.toJson(memberToken));
      return ok(resetPassword.render(""));
    }
    
    // Ok
    return ok(resetPassword.render(memberToken.getTokenString()));
  }
  
  
  /**
   * <pre>
   * Step 1 : 檢查表單Token是否還存在
   * Step 2 : 檢查Token，是否可以查詢到會員資料
   * Step 3 : 檢查Token，是否使用過了
   * Step 4 : 檢查兩次輸入密碼，是否正確
   * 
   * OK 1 : 確認完畢，進行修改密碼
   * OK 2 : 把該會員所有忘記密碼Token，且尚未使用中的Token，全部更新成使用過
   * OK 3 : 修改密碼動作，寄信給使用者
   * OK 4 : 會員更新動作，都需要記錄下來，寫入member_main_log
   * OK 5 : 以上動作完成，顯示修改成功訊息     
   * </pre>
   */
  public Result doResetPassword(){
    // 清除暫存錯誤訊息
    flash().clear();
    
    // Step 1
    ResetPasswordRequest request = null;
    try{
      request = formFactory.form(ResetPasswordRequest.class).bindFromRequest().get();
    } catch (Exception e){
      e.printStackTrace();
      flash().put("error", "資料錯誤，請重新點選忘記密碼信件連結，謝謝。0x31");
      return ok(resetPassword.render(""));
    }
    
    // Step 2
    MemberToken memberToken = null ;
    try{
      memberToken = webService.getMemberTokenData(request.getToken() , MemberTokenType.ForgotPassword.toString());
    } catch(Exception e){
      e.printStackTrace();
      flash().put("error", "系統忙碌中，請稍候再嘗試，謝謝。");
      return ok(resetPassword.render(request.getToken()));
    } finally {
      if(memberToken == null){
        flash().put("error", "資料錯誤，請重新點選忘記密碼信件連結，謝謝。0x32");
        play.Logger.warn("memberToken  = " + Json.toJson(memberToken));
        return ok(resetPassword.render(""));
      }
    }
    
    // Step 3
    if(memberToken.getIsUse()){
      flash().put("error", "該忘記密碼連結已失效，若要重設密碼，請使用忘記密碼功能，謝謝。");
      play.Logger.warn("memberToken  = " + Json.toJson(memberToken));
      return ok(resetPassword.render(""));
    }
    
    // Step 4
    try{
      VerificFormMessage message = new Utils_Signup().checkPassword(request.getPassword(), request.getRetypePassword());
      if(!"200".equals(message.getStatus())){
        flash().put("error", message.getStatusDesc());
        return ok(resetPassword.render(request.getToken()));
      }
    }catch(Exception e){
      e.printStackTrace();
      flash().put("error", "系統忙碌中，請重新再次嘗試，謝謝。");
      return ok(resetPassword.render(request.getToken()));
    }
    
    
    // Ok
    try{
      String password = request.getPassword();
      String memberNo = memberToken.getMemberNo();
      Member member = this.webService.findMemberByMemberNo(memberNo);
      
      int isGenMemberChangeLogOk = webService.genMemberChangeLog(member);
      int updateMemberPassword = this.webService.updateMemberPassword(memberNo , password);
      int updateMemberToken = this.webService.updateMemberToken(memberNo, MemberTokenType.ForgotPassword.toString());
      
      Utils_Email utils_Email = new Utils_Email();
      Email email = new Utils_Email().genResetPasswordOk(member);
      boolean isGenResetPasswordOk = utils_Email.sendMail(email);
      
      play.Logger.info("updateMemberPassword = " + updateMemberPassword +
                       " , updateMemberToken = " + updateMemberToken  +
                       " , isGenResetPasswordOk = " + isGenResetPasswordOk + 
                       " , isGenMemberChangeLogOk = " + isGenMemberChangeLogOk);
    } catch (Exception e){
      e.printStackTrace();
      flash().put("error", "系統忙碌中，請重新再次嘗試，謝謝。");
      return ok(resetPassword.render(request.getToken()));
    }
    
    return ok(resetPasswordOk.render());
    
  }
  
  // 修改密碼頁面
  public Result editPassword(){
    return ok(editPassword.render());
  }
  
  
  
  
  
  
  
  
}
