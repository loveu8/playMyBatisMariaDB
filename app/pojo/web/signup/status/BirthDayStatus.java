package pojo.web.signup.status;

public enum BirthDayStatus {

  /** 生日格式不正確，請重新選擇。 */
  S1("1", "生日格式不正確，請重新選擇。" , false),
  
  /** 生日超過現在時間，請重新選擇。 */
  S2("2", "生日超過現在時間，請重新選擇。" , false),
  
  /** 生日正確。*/
  S200("200", "生日正確。" , true) ,
  
  /** 生日無輸入，不需要顯示說明*/
  S201("201", "" , true) ,
  
  /** 生日與DB生日一致，不需要顯示說明*/
  S202("202", "" , true)
  ;
  
  BirthDayStatus(String status, String statusDesc , boolean pass) {
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
