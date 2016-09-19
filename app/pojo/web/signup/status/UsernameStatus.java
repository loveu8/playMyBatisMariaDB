package pojo.web.signup.status;

public enum UsernameStatus {


  S1("1", "請輸入使用者名稱。"),
  S2("2", "使用者名稱只能使用英文字，且介於4個字~15字之間。"), 
  S3("3", "該使用者名稱已被使用，請改用其它名稱。"), 
  S200("200", "使用者名稱可以使用。");


  UsernameStatus(String status, String statusDesc) {
    this.status = status;
    this.statusDesc = statusDesc;
  }


  public final String status; // 狀態代碼

  public final String statusDesc; // 狀態說明

}
