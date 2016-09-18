package pojo.web;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public enum MemberLoginStatus {
  
  S1("1", "加入會員成功"), 
  S2("2", "登入成功"), 
  S3("3", "登入失敗");

  MemberLoginStatus(String status, String statusDesc) {
    this.status = status;
    this.statusDesc = statusDesc;
  }

  public final String status; // 狀態代碼

  public final String statusDesc; // 狀態說明

  public String getStatus() {
    return status;
  }

  public String getStatusDesc() {
    return statusDesc;
  }
  
}
