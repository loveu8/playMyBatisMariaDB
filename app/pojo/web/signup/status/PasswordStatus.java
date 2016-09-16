package pojo.web.signup.status;

public enum PasswordStatus {


  S1("1", "請輸入密碼。"), 
  S2("2", "請輸入確認密碼。"), 
  S3("3", "密碼兩次輸入不相符。"), 
  S4("4", "密碼需要在長度4~15個字元之間，且含有大小寫英文字與數字。"), 
  S200("200", "密碼可以使用。");


  PasswordStatus(String status, String statusDesc) {
    this.status = status;
    this.statusDesc = statusDesc;
  }


  public final String status; // 狀態代碼

  public final String statusDesc; // 狀態說明

}
