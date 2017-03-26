package utils.http;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

import play.libs.ws.*;

/**
 * <pre> 
 *  Http 工具
 * </pre>
 */
public class HttpHelper {
  
  private WSClient ws ;
  
  public HttpHelper(WSClient ws){
    this.ws = ws;
  }
  
  /**
   * <pre> 
   *  檢查是否是圖片
   * </pre>
   */
  public boolean checkImgUrl(String url) {
    WSResponse response = null;
    int responseStatus = 0;
    boolean isImg = false;
    String contentType = "";
    try {
      
      String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
      // 檢驗是否是網址，才執行檢查
      if(url.matches(regex)){
              
        // 設定要請求的資訊
        WSRequest request = ws.url(url);
        request.setRequestTimeout(10000);
        CompletionStage<WSResponse> responsePromise = request.get();
  
        if (responsePromise != null) {
          // 取得呼叫完的結果
          CompletableFuture<WSResponse> future = responsePromise.toCompletableFuture();
          response = future.get();
          responseStatus = response.getStatus(); // Http回覆碼
          contentType = response.getHeader("Content-Type");

          if( contentType == null || "".equals(contentType) || 
              contentType.indexOf("image") == -1 || responseStatus != 200){
            isImg = false;
          } else {
            isImg = true;
          }
        } else {
          isImg = false;
        }
      } else {
        isImg = false;
      }

    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }
    play.Logger.info("isImg = " + isImg + " , url = " + url + " , Header Content-Type = " + contentType);
    return isImg;
  }
  
}
