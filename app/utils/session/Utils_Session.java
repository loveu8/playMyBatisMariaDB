package utils.session;



import org.springframework.stereotype.Component;
import annotation.Check;
import play.cache.CacheApi;
import play.mvc.Http;
import play.mvc.Http.Cookie;

@Component
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
    this.setCookie(response ,sessionId , sessionSign , maxAge , path, domain , secure);
  }
  
  public void setCache(CacheApi cache , String key , String value , int expiration){
    cache.set(key, value, expiration);
  }

  public void setCookie(Http.Response response  ,String sessionId , String sessionSign , int maxAge ,String path , String domain , boolean secure ){
    response.setCookie(new Cookie("sessionId", sessionId, maxAge, path , domain, secure, true));
    response.setCookie(new Cookie("sessionSign", sessionSign, maxAge, path , domain, secure, true));
  }
  
  public String getServerCacheData(CacheApi cache ,String name){
    return "server cache = " + cache.get(name);
  }
 
  
  public boolean isCookieOnCache(CacheApi cache , String sessionId , String sessionSign){
    if("".equals(sessionId) || "".equals(sessionSign)){
      return false;
    }
    return sessionSign.equals(cache.get(sessionId));
  }
  
  
  public boolean isClinetHaveCookie(Http.Request request){
    return !"".equals(request.cookie("sessionId")) && !"".equals(request.cookie("sessionSign"));
  }
  
  
}

