package pojo.web.signup.status;

public enum HeaderPicLinkStatus {

  S1("1", "圖片網址不正確，請重新輸入。"),
  
  S2("2", "圖片網址無法載入圖片，請重新輸入。"),
  
  S200("200", "圖片網址可以使用。") ,
  
  /** 大頭照網址無輸入，不需要顯示說明 */
  S201("201", "")
  ;
  
  HeaderPicLinkStatus(String status, String statusDesc) {
    this.status = status;
    this.statusDesc = statusDesc;
  }

  public final String status; // 狀態代碼

  public final String statusDesc; // 狀態說明

}
