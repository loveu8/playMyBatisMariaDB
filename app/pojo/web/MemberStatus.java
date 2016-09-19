package pojo.web;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public enum MemberStatus {
  
  S1("1", "尚未認證"), 
  S2("2", "已認證"), 
  S3("3", "帳號停用");

  MemberStatus(String status, String statusDesc) {
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
