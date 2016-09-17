package pojo.web.email;

public class Email {
  // 寄件者
  private String From;
  
  // 收件者
  private String to;
  
  // 信件主題
  private String subject;
  
  // 文字訊息
  private String text;
  
  // 網頁訊息
  private String content;

  public String getFrom() {
    return From;
  }

  public void setFrom(String from) {
    From = from;
  }

  public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
  
}
