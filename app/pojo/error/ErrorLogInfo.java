package pojo.error;

import java.util.List;

public class ErrorLogInfo {
  
  /** 錯誤原因 */
  private String casue;
  
  /** 錯誤訊息 */
  private String message;
  
  /** 錯誤訊息 */
  private String localMessage;
  
  /** 發生例外的類別，所擁有的inputs */
  private List<Object> inputs;
  
  /** 發生例外後，我們專案相關的類別 */
  private List<RelatedClass> relatedClasses;

  public String getCasue() {
    return casue;
  }

  public void setCasue(String casue) {
    this.casue = casue;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getLocalMessage() {
    return localMessage;
  }

  public void setLocalMessage(String localMessage) {
    this.localMessage = localMessage;
  }

  public List<Object> getInputs() {
    return inputs;
  }

  public void setInputs(List<Object> inputs) {
    this.inputs = inputs;
  }

  public List<RelatedClass> getRelatedClasses() {
    return relatedClasses;
  }

  public void setRelatedClasses(List<RelatedClass> relatedClasses) {
    this.relatedClasses = relatedClasses;
  }
  
  
}
