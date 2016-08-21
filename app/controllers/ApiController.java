package controllers;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import pojo.game.PokemonDB;
import pojo.game.Player;
import pojo.game.Pokemon;

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
		} else {
			return ok (Json.toJson("查無神奇寶貝資料"));
		}
	}
	
	
	// 根據JSON傳過來要查詢的神奇寶貝姓名，回傳查詢結果
	public Result findPokemons(){
		
		// 把傳過的Body資料，轉換成Json格式
		JsonNode 	 requestJson  = request().body().asJson();
		
		// 預定要找的神奇寶貝資料
		List<String> pokemonNames = new ArrayList<String>();
		
		// 我們的Json格式 key 是 pokemonNames , 裡面儲存的是一個陣列
		if(requestJson.get("pokemonNames").isArray()){
			// 把所有要找的神奇寶貝，放到List pokemonNames裡
		    for (JsonNode objNode : requestJson.get("pokemonNames")) {
		    	pokemonNames.add(objNode.asText());
		    }
		}
		
		play.Logger.info("Request json data = " + Json.toJson(pokemonNames));
		
		//開始尋找對映的神奇寶貝，把結果放到 pokemonResult
		List<Object> pokemonResult = new ArrayList<Object>();
		
		for(int index = 0 ; index < pokemonNames.size() ; index ++){
			if(PokemonDB.findPokemon(pokemonNames.get(index)) != null){
				pokemonResult.add(PokemonDB.valueOf(pokemonNames.get(index)).getPokemon());
			} else {
				pokemonResult.add("查無神奇寶貝資料");
			}
		}
		
		play.Logger.info("Response json data = " + Json.toJson(pokemonResult));
		
		// 回傳查詢結果
		return ok(Json.toJson(pokemonResult));
	}
	
	
}
