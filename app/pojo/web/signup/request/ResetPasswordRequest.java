package pojo.web.signup.request;

/**
 * 忘記密碼表單請求
 */
public class ResetPasswordRequest {

  private String token;

  private String password;

  private String retypePassword;

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
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
