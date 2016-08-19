package controllers;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import pojo.game.PokemonDB;
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
	
	// 取得神奇寶貝基本資料
	public Result findPokemon(String monsterName){

		// 利用神奇寶貝姓名，找出對應的怪獸資料
		if(PokemonDB.findPokemon(monsterName) != null){
			return ok (Json.toJson(PokemonDB.valueOf(monsterName).getPokemon()));
		} else{
			return ok (Json.toJson("查無神奇寶貝資料"));
		}
	}
	
	
	
}
