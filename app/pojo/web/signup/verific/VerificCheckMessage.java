package pojo.web.signup.verific;

/**
 * 檢查欄位資訊是否正確
 * 並新增boolean值檢視欄位是否通過
 */
public class VerificCheckMessage extends VerificFormMessage {
  
  private boolean pass;

  public boolean isPass() {
    return pass;
  }

  public void setPass(boolean pass) {
    this.pass = pass;
  }

}
