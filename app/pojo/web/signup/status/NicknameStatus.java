package pojo.web.signup.status;

public enum NicknameStatus {

  S1("1", "暱稱長度只能且介於1個字~15字之間。"),
  S2("2", "暱稱內容不能含有特殊字元，請重新輸入"), 
  S200("200", "暱稱可以使用。") ,
  /** 暱稱無輸入，不需要顯示說明 */
  S201("201", "")
  ;
  
  NicknameStatus(String status, String statusDesc) {
    this.status = status;
    this.statusDesc = statusDesc;
  }

  public final String status; // 狀態代碼

  public final String statusDesc; // 狀態說明

}
