package pojo.web.auth;

public class ServerCache{
  // 加簽的會員資料
  private String sessionSign;
  // cookie 逾期日期
  private String expiryDate;
  // 隨機產生加密KEY
  private String aseKey;
  //隨機產生加密IV
  private String aseIv;
  
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
  public String getAseKey() {
    return aseKey;
  }
  public void setAseKey(String aseKey) {
    this.aseKey = aseKey;
  }
  public String getAseIv() {
    return aseIv;
  }
  public void setAseIv(String aseIv) {
    this.aseIv = aseIv;
  }
}
