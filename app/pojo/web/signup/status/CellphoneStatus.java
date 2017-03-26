package pojo.web.signup.status;

public enum CellphoneStatus {

  S1("1", "手機號碼格式不正確，請重新輸入。" , false),
  
  S2("2", "該手機號碼已在使用中，請重新輸入。" , false),
  
  S200("200", "手機號碼可以使用。" , true) ,
  
  /** 手機號碼無輸入，不需要顯示說明 */
  S201("201", ""  , true) ,
  
  /** 手機號碼為同一人，不需要顯示說明 */
  S202("202", ""  , true)
  ;
  
  CellphoneStatus(String status, String statusDesc , boolean pass) {
    this.status = status;
    this.statusDesc = statusDesc;
    this.pass = pass;
  }

  /** 狀態代碼 */
  public final String status;

  /** 狀態說明 */
  public final String statusDesc;
  
  /** 欄位驗證是否可以通過 */
  public final boolean pass;   
  
}
