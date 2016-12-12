package pojo.error;

import java.util.List;

public class ErrorLogInfo {
  
  private String casue;
  
  private String message;
  
  private String localMessage;
  
  private List<Object> inputs;
  
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
