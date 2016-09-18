package pojo.web;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberAuth {

  private String authString;

  private String memberNo;

  private String sendDate;

  private boolean isUse;

  private String createDate;

  private String modifyDate;

  private String expiryDate;
  
  private String dbTime;

  public String getAuthString() {
    return authString;
  }

  public void setAuthString(String authString) {
    this.authString = authString;
  }

  public String getMemberNo() {
    return memberNo;
  }

  public void setMemberNo(String memberNo) {
    this.memberNo = memberNo;
  }

  public String getSendDate() {
    return sendDate;
  }

  public void setSendDate(String sendDate) {
    this.sendDate = sendDate;
  }

  public boolean getIsUse() {
    return isUse;
  }

  public void setIsUse(boolean isUse) {
    this.isUse = isUse;
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

  public void setUse(boolean isUse) {
    this.isUse = isUse;
  }
  
}
