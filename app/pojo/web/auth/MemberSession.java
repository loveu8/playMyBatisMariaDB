package pojo.web.auth;

public class MemberSession {
  /** Session 編號，Key值*/
  private String sessionId;  
  /** 加簽過資料*/
  private String sessionSign;
  /** 隨機產生的Key值*/
  private String aseKey;
  /** 隨機產生的IV值*/
  private String aseIv;
  /** 會員編號*/
  private String memberNo;
  /** 逾期日期*/
  private String expiryDate;
  /** 建立日期*/
  private String createDate;
  /** 修改日期*/
  private String modifyDate;
  public String getSessionId() {
    return sessionId;
  }
  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }
  public String getSessionSign() {
    return sessionSign;
  }
  public void setSessionSign(String sessionSign) {
    this.sessionSign = sessionSign;
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
  public String getMemberNo() {
    return memberNo;
  }
  public void setMemberNo(String memberNo) {
    this.memberNo = memberNo;
  }
  public String getExpiryDate() {
    return expiryDate;
  }
  public void setExpiryDate(String expiryDate) {
    this.expiryDate = expiryDate;
  }
  public String getCreateDate() {
    return createDate;
  }
  public void setCreateDate(String createDate) {
    this.createDate = createDate;
  }
  public String getModifyDate() {
    return modifyDate;
  }
  public void setModifyDate(String modifyDate) {
    this.modifyDate = modifyDate;
  }
  
}
