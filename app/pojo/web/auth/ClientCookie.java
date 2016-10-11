package pojo.web.auth;

public class ClientCookie {
  // 會員編號
  private String memberNo;
  // 到期日
  private String expiryDate;
  public String getMemberNo() {
    return memberNo;
  }
  public void setMemberNo(String memberNo) {
    this.memberNo = memberNo;
  }
  public String getExpiryDate() {
    return expiryDate;
  }
  public void setExpiryDate(String expiryDate) {
    this.expiryDate = expiryDate;
  }
}
