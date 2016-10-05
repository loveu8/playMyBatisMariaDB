package controllers;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import play.data.DynamicForm;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import pojo.login.User;
import views.html.auth.*;

public class Application extends Controller {

  public Result index() {
    return ok("Hi");
  }

  public Result name(String name) {
    return ok("Hi! " + name + " , welcome!!");
  }

  public Result nameJson(String name) {
    Map<String, String> user = new HashMap<String, String>();
    user.put("name", name);
    return ok(Json.toJson(user));
  }

  public Result userBirthday(String name, int birthday) {
    return ok(name + "'s birthday is " + birthday);
  }

  // 登入頁面
  public Result login() {
    return ok(login.render());
  }

  // 導頁到登入頁面
  public Result changeToLogin() {
    return redirect(controllers.routes.Application.login().url());
  }

  @Inject
  // 相依性注入Play的formFactory，可參考reference介紹
  FormFactory formFactory;

  // 開始檢查登入資訊
  public Result auth() {

    DynamicForm requestData = formFactory.form().bindFromRequest(); // 動態取得頁面上的表單

    Map<String, String> formData = requestData.get().getData(); // 把form轉換成Map物件

    String account = formData.get("account"); // 根據表單欄位的name名稱，取出表單裡面的值

    String password = formData.get("password");

    play.Logger.info("account = " + account + " , password = " + password); // 使用內建play logger
                                                                            // 印出表單資料

    // 預設帳號密碼是tom , 密碼123
    if ("tom".equals(account) && "123".equals(password)) { // 測試帳號密碼是否正確
      // 成功導到成功頁面
      return ok(ok.render());
    } else {
      // 失敗導到失敗頁面
      return ok(fail.render());
    }
  }



  // 登入頁面
  public Result advLogin(String errorMessage) {
    return ok(advLogin.render(""));
  }

  // 導頁到登入頁面
  public Result advChangeToLogin() {
    return redirect(controllers.routes.Application.advLogin("").url());
  }

  // 開始檢查登入資訊
  public Result advAuth() {

    User user = formFactory.form(User.class).bindFromRequest().get(); // 使用Play內建formFactory來對應到User物件

    String account = user.getAccount(); // 找出對應物件後，就可以取得表單所儲存的值

    String password = user.getPassword();

    play.Logger.info("account = " + account + " , password = " + password); // 使用內建play logger
                                                                            // 印出表單資料

    // 預設帳號密碼是tom , 密碼123 , 測試帳號密碼是否正確
    if ("pass".equals(user.validate("tom", "123"))) {
      return ok(advOk.render());
    } else {
      // 導回到登入頁面
      String errorMessage = user.validate("tom", "123");
      play.Logger.info("login errorMessage = " + errorMessage); // log 印出 錯誤訊息
      return ok(advLogin.render(errorMessage)); // 回傳檢查後錯誤的資訊
    }
  }


}
