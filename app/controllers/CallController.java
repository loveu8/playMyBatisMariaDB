package controllers;

import play.mvc.*;
import play.libs.Json;
import play.libs.ws.*;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

import play.mvc.Controller;

public class CallController extends Controller{
	
	// 相依性注入 play.libs.ws.WSClient，可以用來呼叫別人寫好的Http服務
	@Inject WSClient ws;
	
	public Result gameMasterData(){
				
		WSResponse response = null;
		
		int responseStatus  = 0;
		
		String body         = "";
		try {
			// 設定要請求的資訊
			WSRequest request = ws.url("http://127.0.0.1:9000/api/getGameMasterData");
			
			// 設定10秒後，沒友回覆的話，斷開連線
			request.setRequestTimeout(10000);
			
			// 呼叫對方寫好的Api
			CompletionStage<WSResponse> responsePromise = request.get();
			
			if(responsePromise != null){
				// 取得呼叫完的結果
				CompletableFuture<WSResponse> future = responsePromise.toCompletableFuture();
				response 		= future.get();
				responseStatus 	= response.getStatus();	// Http回覆碼
				body 			= response.getBody();	// 回覆的資訊
			} else {
				responseStatus  = 503;
				body			= "\"呼叫逾時\"";
			}
		
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		return ok("responseStatus = " + responseStatus + " , body = " + body);
	}
	
	
	public Result findPokemon(String pokemonName){
		
		WSResponse response = null;
		
		int responseStatus  = 0;
		
		String body         = "";
		try {
			// 設定要請求的資訊，如果要帶參數，可以使用QueryParameter方式給值
			WSRequest request = ws.url("http://127.0.0.1:9000/api/findPokemon")
					 			  .setQueryParameter("pokemonName", pokemonName);
			
			// 設定10秒後，沒友回覆的話，斷開連線
			request.setRequestTimeout(10000);
			
			// 呼叫對方寫好的Api
			CompletionStage<WSResponse> responsePromise = request.get();
			
			if(responsePromise != null){
				// 取得呼叫完的結果
				CompletableFuture<WSResponse> future = responsePromise.toCompletableFuture();
				response 		= future.get();
				responseStatus 	= response.getStatus();	// Http回覆碼
				body 			= response.getBody();	// 回覆的資訊
			} else {
				responseStatus  = 503;
				body			= "\"呼叫逾時\"";
			}
		
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		return ok("responseStatus = " + responseStatus + " , body = " + body);
	}
	
	
	public Result findPokemons(){
		
		WSResponse response = null;
		
		int responseStatus  = 0;
		
		String body         = "";
		try {
			
		    ArrayNode arrayNode = new ArrayNode(JsonNodeFactory.instance);
		    
		    arrayNode.add("Pikachu");
		    arrayNode.add("Bulbasaur");		
		    arrayNode.add("XXXX");
		    arrayNode.add("Charmander");
		    arrayNode.add("Squirtle");
		    
		    // 組出 json 格式
		    String jsonStr = "{\"pokemonNames\" : " + arrayNode.toString() + "}";
		    
			JsonNode json = Json.parse(jsonStr);

			// 設定要請求的資訊，設定我們要使用json格式跟對方請求資料
			WSRequest request = ws.url("http://127.0.0.1:9000/api/findPokemons")
					 			  .setHeader("Content-Type", "application/json");
			
			// 設定10秒後，沒友回覆的話，斷開連線
			request.setRequestTimeout(10000);
			
			play.Logger.info("data = " + Json.toJson(json));
			
			// 呼叫對方寫好的Api
			CompletionStage<WSResponse> responsePromise = request.post(json);
			
			if(responsePromise != null){
				// 取得呼叫完的結果
				CompletableFuture<WSResponse> future = responsePromise.toCompletableFuture();
				response 		= future.get();
				responseStatus 	= response.getStatus();	// Http回覆碼
				body 			= response.getBody();	// 回覆的資訊
			} else {
				responseStatus  = 503;
				body			= "\"呼叫逾時\"";
			}
		
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		return ok("responseStatus = " + responseStatus + " , body = " + body);
	}
	 
}
