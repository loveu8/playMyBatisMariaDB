package utils.session;


import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import play.cache.CacheApi;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Http.Cookie;
import pojo.web.Member;
import pojo.web.auth.ClientCookie;
import pojo.web.auth.MemberSession;
import pojo.web.auth.ServerCache;
import utils.enc.AESEncrypter;

public class Utils_Session {
  
  /** Cookie and Session 設定14天的存活時間*/
  public final Integer expiryTime =  (60 * 60 * 24) * 14;
  
  /** 
   * <pre>
   * 傳入查詢出來的會員資料，產生會員Session資 
   * @param member
   * @return MemberSession
   * </pre>
   */
  public MemberSession genMemberSession(Member member){
    
    AESEncrypter aes = new AESEncrypter();
    String clientSessionId = java.util.UUID.randomUUID().toString();
    String memberNo = member.getMemberNo();
    Format formatter = new SimpleDateFormat("yyyyMMddHHmmss");
    String expiryDate = formatter.format(new Date(new Date().getTime() + expiryTime));
    String createDate = formatter.format(new Date());
    ClientCookie clientSessionUnsign = new ClientCookie();
    clientSessionUnsign.setMemberNo(memberNo);
    clientSessionUnsign.setExpiryDate(expiryDate);
    String strClientSessionUnsign = Json.toJson(clientSessionUnsign).toString();
    String aseKey = aes.randomString(16);
    String aseIv = aes.randomString(16);
    String strClieantSessionSign = aes.encrypt(aseKey, aseIv, strClientSessionUnsign);
    
    MemberSession memberSession = new MemberSession();
    memberSession.setSessionId(clientSessionId);
    memberSession.setSessionSign(strClieantSessionSign);
    memberSession.setAseKey(aseKey);
    memberSession.setAseIv(aseIv);
    memberSession.setMemberNo(memberNo);
    memberSession.setExpiryDate(expiryDate);
    memberSession.setCreateDate(createDate);
    memberSession.setModifyDate(createDate);
    return memberSession;
  }

  /**
   * <pre>
   * @param response , play response
   * @param cache  , server Cache
   * @param memberSession , 會員Session表
   * @param maxAge , 設定存活時間
   * @param path , 使用路徑範圍(本機設定空字串)
   * @param domain , 網域(本機設定空字串)
   * @param secure , 是否是Https網站
   *</pre>
   */
  public void setMemberCookieAndCache(Http.Response response , CacheApi cache , MemberSession memberSession , int maxAge ,String path , String domain , boolean secure ){
    this.setCache(cache, memberSession , maxAge);
    this.setCookie(response , memberSession , maxAge , path , domain , secure);
  }
  
  /**
   *寫入Server Cache 
   */
  public void setCache(CacheApi cache , MemberSession memberSession , int expiration){
    ServerCache data = new ServerCache();
    data.setExpiryDate(memberSession.getExpiryDate());
    data.setAseKey(memberSession.getAseKey());
    data.setAseIv(memberSession.getAseIv());
    data.setSessionSign(memberSession.getSessionSign());
    cache.set(memberSession.getSessionId(), Json.toJson(data), expiration);
  }

  
  /**
   * 寫入使用者瀏覽器Cookie 
   */
  public void setCookie(Http.Response response  ,MemberSession memberSession , int maxAge ,String path , String domain , boolean secure ){
    response.setCookie(new Cookie("sessionId" , memberSession.getSessionId() , maxAge , path , domain , secure , true));
    response.setCookie(new Cookie("sessionSign" , memberSession.getSessionSign() , maxAge , path , domain , secure , true));
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

