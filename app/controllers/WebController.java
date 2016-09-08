package controllers;

import javax.inject.Inject;
import services.WebService;
import play.Logger;
import play.data.FormFactory;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import pojo.web.Member;
import pojo.web.signup.request.SignupRequest;
import views.html.web.*;
import views.html.web.loginSignup.*;

public class WebController extends Controller{
	
	// 首頁
	public Result index(){
		return ok(index.render());
	}
	
	// 登入
	public Result login(){
		return ok(login.render());
	}
	
	// 註冊頁面
	public Result signup(){
		return ok(signup.render());
	}
	
	
	@Inject 
	// 相依性注入Play的formFactory，可參考reference介紹
	FormFactory formFactory;
	
	@Inject
	WebService  webService;
	
    @Inject
    public WebController(WebService webService) {
        this.webService = webService;
    }
	
	// 進行註冊 
	public Result goToSignup(){
		SignupRequest request = formFactory.form(SignupRequest.class).bindFromRequest().get();	
		Logger.info("before , new member request data = " + Json.toJson(request));
		
		try{
			webService.signupNewMember(request);
		} catch (Exception e){
			e.printStackTrace();
		} 
		
		Member member = webService.findMemberByEmail(request.getEmail());
		Logger.info("after , new member data = " + Json.toJson(member));
		return ok(Json.toJson(member));
	}
	
	
	public Result findMemberByEmail(String email){
		return ok(Json.toJson(webService.findMemberByEmail(email)));
	}
	
}
