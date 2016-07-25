package controllers;

import play.mvc.Controller;
import play.mvc.Result;

public class Application extends Controller{
	
    public Result index() {
        return ok("Hi");
    }
    
    public Result name(String name) {
        return ok("Hi! " + name + " , welcome!!");
    }
    
    public Result userBirthday(String name ,int birthday ){
    	return ok(name + "'s birthday is " + birthday);
    }
    
}
