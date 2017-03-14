package pojo.web.signup.status;

public enum UpdateMemberProfileStatus {

  /** 系統發生錯誤，請稍候更新。 */
  SE1("SE1", "系統發生錯誤，請稍候更新。" , false),
  
  /** 無法更新您的會員資料，請檢查以上的輸入資訊，是否正確，謝謝。 */
  E1("E1" , "無法更新您的會員資料，請檢查以上的輸入資訊，是否正確，謝謝。" , false),
  
  /** 您的會員資料，更新成功。*/
  S200("S200", "您的會員資料，更新成功。" , true)
  
  ;

  UpdateMemberProfileStatus(String status, String statusDesc , boolean update) {
    this.status = status;
    this.statusDesc = statusDesc;
    this.update = update;
  }

  /** 狀態代碼 */
  public final String status;

  /** 狀態說明 */
  public final String statusDesc;
  
  /** 是否更新成功 */
  public final boolean update;   

}
