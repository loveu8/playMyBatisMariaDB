package pojo.web.signup.request;

public class SignupRequest {

  private String email;

  private String username;

  private String password;

  private String retypePassword;


  public void setEmail(String email) {
    this.email = email;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setRetypePassword(String retypePassword) {
    this.retypePassword = retypePassword;
  }

  public String getEmail() {
    return email;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getRetypePassword() {
    return retypePassword;
  }

}
