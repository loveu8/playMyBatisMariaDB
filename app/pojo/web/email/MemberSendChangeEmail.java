package pojo.web.email;

public class MemberSendChangeEmail {
  
  /** 會員編號*/
  private String memberNo;
  
  /** 原本信箱*/
  private String oldEmail;
  
  /** 新信箱*/
  private String newEmail;
  
  /** 信件token*/
  private String token;
  
  /** 驗證碼*/
  private String checkCode;
  
  /** 是否使用過*/
  private boolean isUse;
  
  /** 創立日期*/
  private String createDate;
  
  /** 逾期日期*/
  private String expiryDate;
  
  /** 資料庫時間*/
  private String dbTime;

  public String getMemberNo() {
    return memberNo;
  }

  public void setMemberNo(String memberNo) {
    this.memberNo = memberNo;
  }

  public String getOldEmail() {
    return oldEmail;
  }

  public void setOldEmail(String oldEmail) {
    this.oldEmail = oldEmail;
  }

  public String getNewEmail() {
    return newEmail;
  }

  public void setNewEmail(String newEmail) {
    this.newEmail = newEmail;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getCheckCode() {
    return checkCode;
  }

  public void setCheckCode(String checkCode) {
    this.checkCode = checkCode;
  }

  public boolean isUse() {
    return isUse;
  }

  public void setUse(boolean isUse) {
    this.isUse = isUse;
  }

  public String getCreateDate() {
    return createDate;
  }

  public void setCreateDate(String createDate) {
    this.createDate = createDate;
  }

  public String getExpiryDate() {
    return expiryDate;
  }

  public void setExpiryDate(String expiryDate) {
    this.expiryDate = expiryDate;
  }

  public String getDbTime() {
    return dbTime;
  }

  public void setDbTime(String dbTime) {
    this.dbTime = dbTime;
  }
  
}
