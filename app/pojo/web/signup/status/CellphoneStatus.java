package pojo.web.signup.status;

public enum CellphoneStatus {

  S1("1", "手機號碼格式不正確，請重新輸入。"),
  
  S2("2", "該手機號碼已在使用中，請重新輸入。"),
  
  S200("200", "手機號碼可以使用。") ,
  
  /** 手機號碼無輸入，不需要顯示說明 */
  S201("201", "")
  ;
  
  CellphoneStatus(String status, String statusDesc) {
    this.status = status;
    this.statusDesc = statusDesc;
  }

  public final String status; // 狀態代碼

  public final String statusDesc; // 狀態說明

}
