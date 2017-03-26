package pojo.web;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberProfile {

  private String username;
  
  private String nickname;

  private String birthday;

  private String cellphone;

  private String headerPicLink;
  
  private boolean editable;
    
  private String systemMessage;
  
  public MemberProfile(){
    this.username = "";
    this.nickname = "";
    this.birthday = "";
    this.cellphone = "";
    this.headerPicLink = "";
    this.editable = false;
    this.systemMessage = "系統異常，請稍候再嘗試。";
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getBirthday() {
    return birthday;
  }

  public void setBirthday(String birthday) {
    this.birthday = birthday;
  }

  public String getCellphone() {
    return cellphone;
  }

  public void setCellphone(String cellphone) {
    this.cellphone = cellphone;
  }

  public String getHeaderPicLink() {
    return headerPicLink;
  }

  public void setHeaderPicLink(String headerPicLink) {
    this.headerPicLink = headerPicLink;
  }

  public boolean isEditable() {
    return editable;
  }

  public void setEditable(boolean editable) {
    this.editable = editable;
  }
  
  public String getSystemMessage() {
    return systemMessage;
  }

  public void setSystemMessage(String systemMessage) {
    this.systemMessage = systemMessage;
  }
  
}
