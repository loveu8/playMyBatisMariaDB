package pojo.web.signup.update;

import java.util.Map;

import pojo.web.signup.verific.VerificCheckMessage;

/**
 * 更新結果訊息 
 */
public class UpdateMessage {
  
  // 更新類型
  private String updateType;

  // 狀態碼
  private String status;

  // 狀態碼描述
  private String statusDesc;
  
  // 是否更新成功
  private boolean update;
  
  // 檢驗結果
  private Map<String , VerificCheckMessage> verificResults;
  
  // 耗費時間
  private String costTime;
  
  public String getUpdateType() {
    return updateType;
  }

  public void setUpdateType(String updateType) {
    this.updateType = updateType;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getStatusDesc() {
    return statusDesc;
  }

  public void setStatusDesc(String statusDesc) {
    this.statusDesc = statusDesc;
  }

  public boolean isUpdate() {
    return update;
  }

  public void setUpdate(boolean update) {
    this.update = update;
  }

  public Map<String, VerificCheckMessage> getVerificResults() {
    return verificResults;
  }

  public void setVerificResults(Map<String, VerificCheckMessage> verificResults) {
    this.verificResults = verificResults;
  }

  public String getCostTime() {
    return costTime;
  }

  public void setCostTime(String costTime) {
    this.costTime = costTime;
  }
  
}
