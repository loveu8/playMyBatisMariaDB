package controllers;

import java.util.Map;

import javax.inject.Inject;



import play.Logger;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import pojo.web.signup.request.SignupRequest;
import pojo.web.signup.verific.VerificFormMessage;
import services.WebService;
import utils.signup.Utils_Signup;
import views.html.web.index;
import views.html.web.loginSignup.login;
import views.html.web.loginSignup.signup;
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
   * 進行註冊檢查
   * 
   * Step 1 : 取得表單註冊資訊，若錯誤，回到註冊頁面，彈跳錯誤訊息。
   * Step 2 : 進行表單驗證，是否正確。若錯誤，回到註冊頁面顯示錯誤訊息。
   * Step 3 : 檢核通過，進行寄送認證信動作。
   * 
   */
  public Result goToSignup() {

    // 清除暫存錯誤訊息
    flash().clear();
    
    SignupRequest request = this.getSignupRequest();
    if (request == null) {
      flash().put("errorForm","註冊資料錯誤，請重新嘗試!!");
      return ok(signup.render());
    }

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
      // webService.signupNewMember(request);
    } catch (Exception e) {
      e.printStackTrace();
    }

    // Member member = webService.findMemberByEmail(request.getEmail());
    // Logger.info("after , new member data = " + Json.toJson(member));
    return ok("");
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


  
  public Result sendSignMail(){
    new Utils_Email().sendMail();
    return ok("");
  }
}
