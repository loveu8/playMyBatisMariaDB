package utils.session;


import play.cache.CacheApi;
import play.mvc.Http;
import play.mvc.Http.Cookie;

public class Utils_Session {

  /**
   * @param response
   * @param cacheApi 
   * @param sessionId 
   * @param sessionSign
   * @param maxAge
   * @param path
   * @param domain
   * @param secure
   */
  public void setMemberCookie(Http.Response response , CacheApi cache ,String sessionId , String sessionSign , int maxAge ,String path , String domain , boolean secure ){
    this.setCache(cache , sessionId , sessionSign , maxAge);
    this.setCookie(response , sessionId , sessionSign , maxAge , path , domain , secure);
  }
  
  
  public void setCache(CacheApi cache , String key , String value , int expiration){
    cache.set(key, value, expiration);
  }

  
  public void setCookie(Http.Response response  ,String sessionId , String sessionSign , int maxAge ,String path , String domain , boolean secure ){
    response.setCookie(new Cookie("sessionId" , sessionId , maxAge , path , domain , secure , true));
    response.setCookie(new Cookie("sessionSign" , sessionSign , maxAge , path , domain , secure , true));
  }
  
  
  public String getCacheSessionSign(CacheApi cache ,String name){
    return cache.get(name);
  }
 
  /** 比對 server cache 與 cookie session 是否一致*/
  public boolean isCookieSameAsCacheData(CacheApi cache , String sessionId , String sessionSign){
    if("".equals(sessionId) || "".equals(sessionSign)){
      return false;
    }
    return sessionSign.equals(cache.get(sessionId));
  }
  
  /** 檢查是否有我們的session*/
  public boolean isClinetHaveCookie(Http.Request request){
    return !"".equals(request.cookie("sessionId")) && !"".equals(request.cookie("sessionSign"));
  }
  
}

