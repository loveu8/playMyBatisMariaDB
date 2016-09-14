package controllers;

import javax.inject.Inject;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import pojo.db.User;
import services.UserService;

public class DbController extends Controller {

  private UserService userService;

  @Inject
  public DbController(UserService userService) {
    this.userService = userService;
  }

  // 取出所有使用者
  public Result listUsers() {
    return ok(Json.toJson(userService.all()));
  }

  // 根據id，找出對應的人
  public Result showUser(Long id) {
    User user = userService.getUserById(id);
    if (user != null) {
      return ok(Json.toJson(user));
    } else {
      return ok(Json.toJson("查無資料"));
    }

  }
}
