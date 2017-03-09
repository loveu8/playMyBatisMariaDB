package pojo.web.signup.status;

public enum NicknameStatus {

  /** 暱稱長度只能且介於1個字~15字之間。 */
  S1("1", "暱稱長度只能且介於1個字~15字之間。" , false),
  
  /** "暱稱不能含有特殊字元，請您重新輸入。 */
  S2("2", "暱稱不能含有特殊字元，請您重新輸入。" , false), 
  
  /** 暱稱可以使用。 */
  S200("200", "暱稱可以使用。" , true) ,
  
  /** 暱稱無輸入，不需要顯示說明 */
  S201("201", "" , true)
  ;
  
  NicknameStatus(String status, String statusDesc , boolean pass) {
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
