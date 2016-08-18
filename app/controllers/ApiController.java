package controllers;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import pojo.game.Player;

public class ApiController extends Controller{
	
	// 取得遊戲管理者角色資料
	public Result getGameMasterData(){
		Player player = new Player();
		player.setName("GM");
		player.setSex("Male");
		player.setRace("human");
		return ok (Json.toJson(player));
	}
	
	
}
