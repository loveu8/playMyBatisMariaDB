package controllers;

import javax.inject.Inject;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import services.UserService;

public class DbController extends Controller{
	
    private UserService userService;

    @Inject
    public DbController(UserService userMapper) {
        this.userService = userMapper;
    }

    public Result listUsers() {
        return ok(Json.toJson(userService.all()));
    }

    public Result showUser(Long id) {
        return ok(Json.toJson(userService.getUserById(id)));
}
}
