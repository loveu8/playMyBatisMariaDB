package pojo.web.signup.status;

public enum UpdateMemberProfileStatus {

  /** 系統忙碌中，請稍後再編輯您的會員資料，謝謝。 */
  SE1("SE1", "系統忙碌中，請稍後再編輯您的會員資料。" , false),
  
  /** 您的會員資料修改有誤，請檢查以上填寫資訊是否正確，謝謝。 */
  E1("E1" , "您的會員資料修改有誤，請檢查以上填寫資訊是否正確，謝謝。" , false),
  
  /** 您的會員資料，更新成功。*/
  S200("S200", "會員資料編輯成功。" , true)
  
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
