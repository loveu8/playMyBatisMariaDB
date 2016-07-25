package controllers;

import java.util.HashMap;
import java.util.Map;

import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class Application extends Controller{
	
    public Result index() {
        return ok("Hi");
    }
    
    public Result name(String name) {
        return ok("Hi! " + name + " , welcome!!");
    }
    
    public Result nameJson(String name) {
    	Map <String , String> user = new HashMap<String , String>();
    	user.put("name", name);
        return ok(Json.toJson(user));
    }
    
    public Result userBirthday(String name ,int birthday ){
    	return ok(name + "'s birthday is " + birthday);
    }
    
    public Result login( ){
    	return ok(login.render());
    }
    
    public Result changeToLogin( ){
    	return redirect(controllers.routes.Application.login().url());
    }
    
    // 預設帳號密碼是tom , 密碼123
    public Result auth(){
		// 動態取得頁面上的表單
		DynamicForm form = Form.form().bindFromRequest();

		// 取得Request的內容,表單回傳會是Map資料
		Map<String, String> formMap = form.get().getData();
		
		controllers.routes.Application.auth().url();
		
    	return ok();
    }
    
}
