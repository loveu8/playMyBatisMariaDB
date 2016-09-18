package controllers;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;



import play.Logger;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import pojo.web.Member;
import pojo.web.MemberLoginStatus;
import pojo.web.email.Email;
import pojo.web.signup.request.SignupRequest;
import pojo.web.signup.verific.VerificFormMessage;
import services.WebService;
import services.Impl.WebServiceImpl;
import utils.signup.Utils_Signup;
import views.html.web.index;
import views.html.web.loginSignup.login;
import views.html.web.loginSignup.signup;
import views.html.web.loginSignup.signupOk;
import utils.mail.Utils_Email;
import utils.signup.*;

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
  private WebService webService;


  /**
   * <pre>
   * 進行註冊
   * 
   * Step 1 : 取得表單註冊資訊，若錯誤，回到註冊頁面，彈跳錯誤訊息。
   * Step 2 : 進行表單驗證，是否正確。若錯誤，回到註冊頁面顯示錯誤訊息。
   * Step 3 : 檢核通過，新增會員資料，且尚未認證動作。
   * Step 4 : 註冊新增成功，新增認證記錄資料。
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
      // 發現驗證沒過，擺入錯誤訊息
      if (!"200".equals(verificInfo.get(key).getStatus())) {
        flash().put(key, verificInfo.get(key).getStatusDesc());
      }
    }
    if(!flash().isEmpty()){
      return ok(signup.render());
    }
 
    
    try {
      // Step 3
      int isSignOk = webService.signupNewMember(request);
      
      if(isSignOk > 0){
        
        Utils_Signup utils_Signup = new Utils_Signup();
        
        // Step 4
        Member newMember = webService.findMemberByEmail(request.getEmail());
        String authString = utils_Signup.genAuthString(newMember.getEmail());
        
        Map<String , String> memberAuth = new HashMap<String , String>();
        memberAuth.put("memberNo", newMember.getMemberNo());
        memberAuth.put("authString", authString);
        int isSingAuthStringOk = webService.genSignupAuthData(memberAuth);
        
        // Step 5
        Map<String , String> memberLoginData 
            = utils_Signup.genMemberLoginData(newMember.getMemberNo() ,
                                              "PC" , 
                                              request().remoteAddress() , 
                                              MemberLoginStatus.S1.getStatus());
        int isMemberLoginLogOk = webService.genMemberLoginLog(memberLoginData);
        
        // Step 6
        Utils_Email utils_Email = new Utils_Email();
        Email email = utils_Email.genSinupAuthEmail(newMember, authString);
        boolean isSeadMailOk = utils_Email.sendMail(email);
        
        // Step 7
        if(isSingAuthStringOk > 0 && isSeadMailOk && isMemberLoginLogOk > 0){
          return ok(signupOk.render());
        } else {
          flash().put("signupError", "註冊會員失敗，請重新註冊，謝謝。");
          return ok(signup.render());
        }
      } else {
        flash().put("signupError", "註冊會員失敗，請重新註冊，謝謝。");
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
      // 測試錯誤訊息
      // request = null;
    } catch (Exception e) {
      Logger.error("表單內容非註冊資訊，轉換類別錯誤，回傳空物件");
    }
    return request;
  }

  
  // Step 2 : 檢查註冊資訊
  private Map<String, VerificFormMessage> checkSingupRequest(SignupRequest request) {
    Map<String, VerificFormMessage> verificInfo 
        = new Utils_Signup().checkSingupRequest(request, webService.checkMemberByEmail(request.getEmail()));
    Logger.info("verificInfo = " + Json.toJson(verificInfo));
    return verificInfo;
  }
  
  
  // 檢查註冊認證信
  public Result authMember(String auth){
    return ok(auth);
  }
  
}
