package pojo.web.auth;

public class ServerCacheData{
  // 加簽的會員資料
  private String sessionSign;
  // cookie 逾期日期
  private String expiryDate;
  // 隨機產生加密KEY
  private String key;
  //隨機產生加密IV
  private String iv;
  
  public String getSessionSign() {
    return sessionSign;
  }
  public void setSessionSign(String sessionSign) {
    this.sessionSign = sessionSign;
  }
  public String getExpiryDate() {
    return expiryDate;
  }
  public void setExpiryDate(String expiryDate) {
    this.expiryDate = expiryDate;
  }
  public String getKey() {
    return key;
  }
  public void setKey(String key) {
    this.key = key;
  }
  public String getIv() {
    return iv;
  }
  public void setIv(String iv) {
    this.iv = iv;
  }
}
