package pojo.web.signup.status;

public enum EmailStatus {


  S1("1", "請輸入信箱。"), 
  S2("2", "信箱格式錯誤。"), 
  S3("3", "該信箱已經註冊過，請更換信箱。"), 
  S200("200", "該信箱可以使用。");


  EmailStatus(String status, String statusDesc) {
    this.status = status;
    this.statusDesc = statusDesc;
  }

  public final String status; // 狀態代碼

  public final String statusDesc; // 狀態說明

}
