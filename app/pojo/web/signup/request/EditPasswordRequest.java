package pojo.web.signup.request;

/**
 * 修改密碼表單請求
 */
public class EditPasswordRequest {

  private String oldPassword;

  private String password;

  private String retypePassword;
  

  public String getOldPassword() {
    return oldPassword;
  }

  public void setOldPassword(String oldPassword) {
    this.oldPassword = oldPassword;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRetypePassword() {
    return retypePassword;
  }

  public void setRetypePassword(String retypePassword) {
    this.retypePassword = retypePassword;
  }

}
