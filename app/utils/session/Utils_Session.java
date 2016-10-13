package utils.session;


import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.databind.JsonNode;

import play.cache.CacheApi;
import play.cache.DefaultCacheApi;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Http.Cookie;
import play.mvc.Http.Request;
import pojo.web.Member;
import pojo.web.auth.ClientCookie;
import pojo.web.auth.MemberSession;
import pojo.web.auth.ServerCache;
import utils.enc.AESEncrypter;

public class Utils_Session {
  
  /** Cookie and Session 設定14天的存活時間*/
  public final int maxAge =  (60 * 60 * 24) * 14;
  
  /**Cookie and Session for Java Date getTime use*/
  public final long maxAgeLong =  (60 * 60 * 24) * 14 * 1000;
  
  /** 
   * <pre>
   * 傳入查詢出來的會員資料，產生會員Session資料
   * @param member
   * @return MemberSession
   * </pre>
   */
  public MemberSession genMemberSession(Member member){
    
    AESEncrypter aes = new AESEncrypter();
    String clientSessionId = java.util.UUID.randomUUID().toString();
    String memberNo = member.getMemberNo();
    Format formatter = new SimpleDateFormat("yyyyMMddHHmmss");
    String expiryDate = formatter.format(new Date(new Date().getTime() + maxAgeLong ));
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
   * 登入之後，設定瀏覽器Cookie與伺服的Cache，儲存會員登入資訊
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
 
  
  /** 比對 server cache 與 cookie session 是否一致*/
  public boolean isCookieSameAsCacheData(CacheApi cache , String sessionId , String sessionSign){
    if("".equals(sessionId) || "".equals(sessionSign)){
      return false;
    }
    return sessionSign.equals(cache.get(sessionId));
  }
  
  
  /** 檢查是否有我們的session*/
  public boolean isClinetHaveCookie(Http.Request request){
    return request.cookies().get("sessionId")!=null && 
           request.cookies().get("sessionSign")!=null &&
           !"".equals(request.cookies().get("sessionId").value()) && 
           !"".equals(request.cookies().get("sessionSign").value());
  }

  
  /** 取得使用者瀏覽器的Cookie 加簽資料*/
  public String getClientSession(Request request) {
    if(request.cookies().get("sessionId")==null){
      return "";
    }
    return request.cookies().get("sessionId").value();
  }

  /** 取得使用者瀏覽器的Cookie 加簽資料*/
  public String getClientSessionSign(Request request) {
    if(request.cookies().get("sessionSign")==null){
      return "";
    }
    return request.cookies().get("sessionSign").value();
  }

  /** 伺服器是否使用者的cookie資料*/
  public boolean isCacheHaveThisSession(DefaultCacheApi cache, String sessionId) {
    play.Logger.info("cache get sessionId = " + cache.get(sessionId));
    return cache.get(sessionId) != null && !"".equals(cache.get(sessionId));
  }

  
  /**取得目前伺服器的Cache資料*/
  public MemberSession getServerCacheData(DefaultCacheApi cache, String sessionId) {
    try{
      MemberSession memberSession = new MemberSession();
      JsonNode sessionNode = Json.parse(cache.get(sessionId).toString());
      memberSession.setAseIv(sessionNode.get("aseIv").textValue());
      memberSession.setAseKey(sessionNode.get("aseKey").textValue());
      memberSession.setSessionId(sessionId);
      memberSession.setSessionSign(sessionNode.get("sessionSign").textValue());
      AESEncrypter aes = new AESEncrypter();
      JsonNode rawData = Json.parse(aes.decrypt(memberSession.getAseKey(), 
                                                memberSession.getAseIv(), 
                                                memberSession.getSessionSign()).toString());
      memberSession.setMemberNo(rawData.get("memberNo").textValue());
      memberSession.setExpiryDate(rawData.get("expiryDate").textValue());
      return memberSession;
    } catch(Exception e){
      e.printStackTrace();
      return null;
    }
  }

  
  /**清除瀏覽器Cookie*/
  public void clearErrorClientCookie(Http.Response response){
    response.discardCookie("sessionId");
    response.discardCookie("sessionSign");
  }
  
  
  /** 
   * 檢查是否超過24小時 
   * note : 目前預設cookie 14天，只要expiryDate小於13天，代表超過一天沒更新
   */
  public boolean isRewriteCookie(String expiryDate){
    Format formatter = new SimpleDateFormat("yyyyMMddHHmmss");
    System.out.println("expiryDate = " + expiryDate);
    System.out.println("now        = " + Long.valueOf(formatter.format(new Date())));
    return Long.valueOf(expiryDate) - Long.valueOf(formatter.format(new Date())) < 13 * 60 * 60 * 24 ;
  }
  
}

