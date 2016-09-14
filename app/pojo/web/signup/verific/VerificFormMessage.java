package pojo.web.signup.verific;

/**
 * 預設表單錯誤訊息
 */
public class VerificFormMessage {

  // 錯誤的欄位名稱
  private String inputName;

  // 狀態碼
  private String status;

  // 狀態碼描述
  private String statusDesc;

  public String getInputName() {
    return inputName;
  }

  public void setInputName(String inputName) {
    this.inputName = inputName;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getStatusDesc() {
    return statusDesc;
  }

  public void setStatusDesc(String statusDesc) {
    this.statusDesc = statusDesc;
  }

}
