package utils.mail;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import play.libs.Json;
import pojo.web.Member;
import pojo.web.email.Email;
import pojo.web.email.MemberSendChangeEmail;


public class Utils_Email {
  
  public Email genSinupAuthEmail(Member member , String authString){
    Email email = new Email();
    email.setFrom("littleqkstar@gmail.com");
    email.setTo(member.getEmail());
    email.setSubject("[STAR] - 註冊認證信");
    email.setText("");
    email.setContent("<h2>您好 "+ member.getUsername()+"，你已經成功註冊，請在24小時內，點選以下驗證連結後，便會啟用帳號，謝謝!!</h2> "
                      + "<a href='" +"http://127.0.0.1:9000/web/authMember?auth="+authString+"'>認證連結</a>");
    play.Logger.info("auth email = " + Json.toJson(email));
    return email;
  }
  
  public Email genForgotPasswordEmail(Member member , String forgotPasswordTokenString){
    Email email = new Email();
    email.setFrom("littleqkstar@gmail.com");
    email.setTo(member.getEmail());
    email.setSubject("[STAR] - 忘記密碼重設");
    email.setText("");
    email.setContent("<h2>您好 "+ member.getUsername()+"，請在24小時內，點選以下重設密碼連結後，進行設定新密碼，謝謝!!</h2> "
                      + "<a href='" +"http://127.0.0.1:9000/web/resetPassword?token="+forgotPasswordTokenString+"'>重設密碼連結</a>");
    play.Logger.info("forgotPassword email = " + Json.toJson(email));
    return email;
  }
  
  public Email genResetPasswordOk(Member member){
    Format formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    String time = formatter.format(new Date());
    Email email = new Email();
    email.setFrom("littleqkstar@gmail.com");
    email.setTo(member.getEmail());
    email.setSubject("[STAR] - 密碼重設成功");
    email.setText("");
    email.setContent("<h2>您好 "+ member.getUsername()+"您的密碼已在"+ time +" , 重設成功。</h2> ");
    play.Logger.info("genResetPasswordOk email = " + Json.toJson(email));
    return email;
  }
  
  public Email genEditPasswordOk(Member member){
    Format formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    String time = formatter.format(new Date());
    Email email = new Email();
    email.setFrom("littleqkstar@gmail.com");
    email.setTo(member.getEmail());
    email.setSubject("[STAR] - 密碼重新修改成功");
    email.setText("");
    email.setContent("<h2>您好 "+ member.getUsername()+"您的密碼已在"+ time +" , 修改成功。</h2> ");
    play.Logger.info("genEditPasswordOk email = " + Json.toJson(email));
    return email;
  }
  
  public Email genMemberSendChangeEmailData(String userName , MemberSendChangeEmail sendData) {
    Email email = new Email();
    email.setFrom("littleqkstar@gmail.com");
    email.setTo(sendData.getNewEmail());
    email.setSubject("[STAR] - 修改註冊信箱");
    email.setText("");
    email.setContent("<h2>您好 "+ userName +"，請在24小時內，點選以下修改註冊信箱連結後，輸入驗證碼，完成認證動作，謝謝!!</h2> " +
                      "<h3>驗證碼 : " + sendData.getCheckCode() + "</h3>" +
                      "<a href='" +"http://127.0.0.1:9000/web/authNewEmail?token="+sendData.getToken()+"'>修改註冊信箱連結</a>");
    play.Logger.info("forgotPassword email = " + Json.toJson(email));
    return email;
  }
  
  // 寄信
  public boolean sendMail(Email email) {

    Session session  = null;
    
    try {
      // 取得SMTP設定檔
      Properties props = this.getMailSMTPConf();

      // 進行授權認證動作
      session  = Session.getInstance(props, new javax.mail.Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
          return new PasswordAuthentication(props.getProperty("user"), props.getProperty("password"));                                                      
        }
      });
      
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
      return true;
    } catch (MessagingException e) {
      play.Logger.error("信件寄送失敗");
      e.printStackTrace();
      return false;
    } finally {
      // 成功寄信完，清除Session
      session = null;
    }
  }
  
  
  // 取得conf/application.conf設定檔
  public Properties getMailSMTPConf(){
    ClassLoader classLoader = utils.mail.Utils_Email.class.getClassLoader();
    Config config = ConfigFactory.load(classLoader);
    
    String host         = config.getString("mail.smtp.host");
    int port            = config.getInt("mail.smtp.port");
    final String user   = config.getString("mail.smtp.user");
    String password     = config.getString("mail.smtp.password");
    final boolean auth  = config.getBoolean("mail.smtp.auth");
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
    props.put("mail.smtp.port", port);
    props.put("mail.smtp.socketFactory.port", port);
    props.put("mail.smtp.socketFactory.class", socketFactory_class);
    props.put("mail.smtp.auth", auth);
    props.put("mail.smtp.ssl.trust", ssl_trust);
    props.put("user", user);
    props.put("password", password);
    
    return props;
  }

  
 
}
