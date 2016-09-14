package controllers;

import java.util.Map;

import javax.inject.Inject;

import play.Logger;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import pojo.web.Member;
import pojo.web.signup.request.SignupRequest;
import pojo.web.signup.verific.VerificFormMessage;
import services.WebService;
import utils.signup.Utils_Signup;
import views.html.web.index;
import views.html.web.loginSignup.login;
import views.html.web.loginSignup.signup;
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
    return ok(signup.render());
  }


  @Inject
  // 相依性注入Play的formFactory，可參考reference介紹
  FormFactory formFactory;

  @Inject
  private WebService webService;


  // 進行註冊
  public Result goToSignup() {

    SignupRequest request = this.getSignupRequest();

    if (request == null) {
      return ok("錯誤");
    }

    Map<String, VerificFormMessage> verificInfo = checkSingupRequest(request);

    Logger.info("verificInfo = " + Json.toJson(verificInfo));

    for (String key : verificInfo.keySet()) {
      if (!"200".equals(verificInfo.get(key).getStatus())) {
        flash("", "");
        return ok(signup.render());
      }
    }

    // if(){
    //
    // }

    try {
      // webService.signupNewMember(request);
    } catch (Exception e) {
      e.printStackTrace();
    }

    // Member member = webService.findMemberByEmail(request.getEmail());
    // Logger.info("after , new member data = " + Json.toJson(member));
    return ok("");
  }

  // 取得註冊資訊請求
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

  // 檢查註冊資訊
  private Map<String, VerificFormMessage> checkSingupRequest(SignupRequest request) {
    return new Utils_Signup().checkSingupRequest(request, webService.checkMemberByEmail(request.getEmail()));
  }


  public Result findMemberByEmail(String email) {
    Member member = webService.findMemberByEmail(email);
    System.out.println("member = " + member);
    if (member != null) {
      return ok(Json.toJson(member));
    } else {
      return ok(Json.toJson("查無資料"));
    }
  }

}
