package pojo.web.signup.status;

public enum HeaderPicLinkStatus {

  /** 圖片網址不正確，請重新輸入。 */
  S1("1", "圖片網址不正確，請重新輸入。" , false),
  
  /** 圖片網址無法載入圖片，請重新輸入。 */
  S2("2", "圖片網址無法載入圖片，請重新輸入。" , false),
  
  /** 圖片網址可以使用。*/
  S200("200", "圖片網址可以使用。" , true) ,
  
  /** 圖片網址無輸入，不需要顯示說明*/
  S201("201", "" , true)
  ;
  
  HeaderPicLinkStatus(String status, String statusDesc , boolean pass) {
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
