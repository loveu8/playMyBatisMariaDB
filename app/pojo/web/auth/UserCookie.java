package pojo.web.auth;

public class UserCookie {
  // 會員編號
  private String no;
  // 到期日
  private String expiryDate;
  // 登入角色類型
  private String role;

  public String getNo() {
    return no;
  }
  public void setNo(String no) {
    this.no = no;
  }
  public String getRole() {
    return role;
  }
  public void setRole(String role) {
    this.role = role;
  }
  public String getExpiryDate() {
    return expiryDate;
  }
  public void setExpiryDate(String expiryDate) {
    this.expiryDate = expiryDate;
  }
}
