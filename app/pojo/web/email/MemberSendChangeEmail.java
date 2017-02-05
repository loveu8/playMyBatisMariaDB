package pojo.web.email;

public class MemberSendChangeEmail {
  private String memberNo;
  
  private String oldEmail;
  
  private String newEmail;
  
  private String token;
  
  private String checkCode;
  
  private boolean isUse;
  
  private String createDate;
  
  private String expiryDate;
  
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
