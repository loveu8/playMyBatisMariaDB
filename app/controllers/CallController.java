package controllers;

import play.mvc.*;
import play.libs.ws.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

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
				responseStatus 	= response.getStatus();	// 回覆帶碼
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
