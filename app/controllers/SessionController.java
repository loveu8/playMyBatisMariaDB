package controllers;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Controller;
import play.mvc.Result;
import pojo.login.User;
import views.html.session.*;

public class SessionController extends Controller {

  @Inject
  // 相依性注入Play的formFactory，可參考reference介紹
  FormFactory formFactory;

  // 登入頁面
  public Result login(String errorMessage) {
    // 發現Session有登入資訊，進入驗證登入
    if (!"".equals(session().get("account")) && !"".equals(session().get("password"))
        && session().get("account") != null && session().get("password") != null) {
      // 發現Session有資料，進入Session驗證登入
      play.Logger.info("goSessionAuth");
      return goSessionAuth();
    } else {
      if (!"".equals(errorMessage)) {
        // 若登出時，需要的提示訊息，印在頁面上
        return ok(login.render(errorMessage));
      } else {
        return ok(login.render(""));
      }
    }
  }

  // 登出頁面，登出時清除Play Session
  public Result logout() {
    session().clear();
    play.Logger.info("logout...bye!!");
    return redirect(controllers.routes.SessionController.login("登出成功，請重新登入。").url());
  }


  // 登入驗證
  public Result auth() {
    return goFromAuth();
  }

  // Session驗證登入
  private Result goSessionAuth() {
    User user = new User();

    user.setAccount(session().get("account"));

    user.setPassword(session().get("password"));

    // 使用內建play logger 印出資料
    play.Logger.info("account = " + user.getAccount() + " , password = " + user.getPassword());

    // 傳入預設帳號與密碼，驗證Session內的帳號與密碼
    if ("pass".equals(user.validate("tom", "123"))) {
      return ok(ok.render());
    } else {
      String errorMessage = user.validate("tom", "123");
      play.Logger.info("login errorMessage = " + errorMessage); // log 印出 錯誤訊息
      return ok(login.render(errorMessage)); // 回傳檢查後錯誤的資訊
    }
  }

  // 表單驗證登入
  private Result goFromAuth() {
    User user = formFactory.form(User.class).bindFromRequest().get(); // 使用Play內建formFactory來對應到User物件

    String account = user.getAccount(); // 找出對應物件後，就可以取得表單所儲存的值

    String password = user.getPassword();

    play.Logger.info("account = " + account + " , password = " + password); // 使用內建play logger
                                                                            // 印出表單資料

    // 預設帳號密碼是tom , 密碼123 , 測試帳號密碼是否正確
    if ("pass".equals(user.validate("tom", "123"))) {
      session().put("account", account);
      session().put("password", password);
      return ok(ok.render());
    } else {
      // 導回到登入頁面
      String errorMessage = user.validate("tom", "123");
      play.Logger.info("login errorMessage = " + errorMessage); // log 印出 錯誤訊息
      return ok(login.render(errorMessage)); // 回傳檢查後錯誤的資訊
    }
  }


  // flash測試頁面
  public Result flashPage() {
    // 清除暫存資訊
    flash().clear();
    return ok(flashTest.render(""));
  }


  // 檢查後，flash提示訊息
  public Result flashCheck() {

    DynamicForm requestData = formFactory.form().bindFromRequest(); // 動態取得頁面上的表單

    Map<String, String> formData = requestData.get().getData(); // 把form轉換成Map物件

    String account = formData.get("account"); // 根據表單欄位的name名稱，取出表單裡面的值

    String patternStr = "[a-zA-Z]{4,10}$"; // 只接受英文4的字串

    Pattern pattern = Pattern.compile(patternStr);

    Matcher matcher = pattern.matcher(account);

    boolean isMatcher = matcher.find(); // 是否符合規則

    // 清除上次訊息
    flash().clear();

    if (isMatcher) {
      flash().put("success", "帳號可以使用");
      play.Logger.info("success 	= " + flash().get("success"));
    } else {
      flash().put("error", "帳號格式錯誤");
      play.Logger.info("error 	= " + flash().get("error"));
    }

    return ok(flashTest.render(account));
  }


}
