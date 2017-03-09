package pojo.web.signup.verific;

/**
 * 預設會員明細錯誤訊息
 * 並新增boolean值檢視欄位是否通過
 */
public class VerificMemberDetailMessage extends VerificFormMessage {
  
  private boolean pass;

  public boolean isPass() {
    return pass;
  }

  public void setPass(boolean pass) {
    this.pass = pass;
  }

}
