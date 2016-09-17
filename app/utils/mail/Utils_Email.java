package utils.mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import pojo.web.Member;
import pojo.web.email.Email;

public class Utils_Email {
  
  public Email genSinupAuthEmail(Member member , String authString){
    Email email = new Email();
    email.setFrom("playStar@gmail.com");
    email.setTo(member.getEmail());
    email.setContent("您好你已經註冊成功，請點選以下連結，驗證信箱，謝謝!! "
                      + "<a href='" +"http://127.0.0.1:9000/web/authMember?auth="+authString+"'>認證連結</a>");
    return email;
  }
  
  public void sendMail(Email email) {
    
    // 取得SMTP設定檔
    Properties props = this.getMailSMTPConf();

    // 進行授權認證動作
    Session session  = this.createAuthSession(props);

    try {
      MimeMessage message = new MimeMessage(session);
      
      String to = email.getTo();
      message.setFrom(new InternetAddress(email.getFrom()));
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
      message.setSubject(email.getSubject());
      if(!"".equals(email.getText())){
        message.setText(email.getText());
      }
      if(!"".equals(email.getContent())){
        message.setContent(email.getContent(), "text/html; charset=utf-8");
      }
      
      // 寄送訊息
      Transport.send(message);
      play.Logger.info("信件寄送成功");

    } catch (MessagingException e) {
      play.Logger.error("信件寄送失敗");
      throw new RuntimeException(e);
    }
  }
  
  
  // 取得設定檔
  public Properties getMailSMTPConf(){
    ClassLoader classLoader = utils.mail.Utils_Email.class.getClassLoader();
    Config config = ConfigFactory.load(classLoader);
    
    String host         = config.getString("mail.smtp.host");
    String port         = config.getString("mail.smtp.port");
    final String user   = config.getString("mail.smtp.user");
    final String auth   = config.getString("mail.smtp.auth");
    String password     = config.getString("mail.smtp.password");
    String ssl_trust    = config.getString("mail.smtp.ssl.trust");
    String socketFactory_class = config.getString("mail.smtp.socketFactory.class");
    
    play.Logger.info("host        = " + host);
    play.Logger.info("port        = " + port);
    play.Logger.info("user        = " + user);
    play.Logger.info("password    = " + password);
    play.Logger.info("auth        = " + auth);
    play.Logger.info("ssl_trust   = " + ssl_trust);
    play.Logger.info("socketFactory_class    = " + socketFactory_class);
    
    Properties props = new Properties();
    props.put("mail.smtp.host", host);
    props.put("mail.smtp.socketFactory.port", port);
    props.put("mail.smtp.socketFactory.class", socketFactory_class);
    props.put("mail.smtp.port", port);
    props.put("mail.smtp.auth", auth);
    props.put("mail.smtp.ssl.trust", ssl_trust);
    
    return props;
  }
  
  
  
  public Session createAuthSession(Properties props){
   return Session.getDefaultInstance(props, new javax.mail.Authenticator() {
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(props.getProperty("user"), props.getProperty("password"));                                                      
      }
    });
  }
  

}
