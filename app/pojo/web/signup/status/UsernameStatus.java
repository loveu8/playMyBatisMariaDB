package pojo.web.signup.status;

public enum UsernameStatus {

  /** 請輸入使用者名稱。*/
  S1("1", "請輸入使用者名稱。" , false),
  
  /** 使用者名稱只能使用英文字，且介於4個字~15字之間。 */
  S2("2", "使用者名稱只能使用英文字，且介於4個字~15字之間。", false), 
  
  /** 該使用者名稱已被使用，請改用其它名稱。 */
  S3("3", "該使用者名稱已被使用，請改用其它名稱。" , false), 
  
  /** 使用者名稱可以使用。 */
  S200("200", "使用者名稱可以使用。" , true) ,
  
  /** 使用者名稱與原本相同，不顯示任何文字訊息*/
  S201("201", "" , true),
  ;


  UsernameStatus(String status, String statusDesc , boolean pass) {
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
