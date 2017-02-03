package pojo.web.email;

public class MemberChangeEmail {
  
  /** 原始信箱  */
  private String originalEmail;
  
  /** 尚未認證信箱 */
  private String unAuthEmail;
  
  /** 新信箱 */
  private String newEmail;

  public String getOriginalEmail() {
    return originalEmail;
  }

  public void setOriginalEmail(String originalEmail) {
    this.originalEmail = originalEmail;
  }

  public String getUnAuthEmail() {
    return unAuthEmail;
  }

  public void setUnAuthEmail(String unAuthEmail) {
    this.unAuthEmail = unAuthEmail;
  }

  public String getNewEmail() {
    return newEmail;
  }

  public void setNewEmail(String newEmail) {
    this.newEmail = newEmail;
  }
  
  
}
