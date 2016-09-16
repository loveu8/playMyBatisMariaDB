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

public class Utils_Email {
  
  public void sendMail() {
    
    ClassLoader classLoader = utils.mail.Utils_Email.class.getClassLoader();
    Config config = ConfigFactory.load(classLoader);
    
    String host         = config.getString("mail.smtp.host");
    String port         = config.getString("mail.smtp.port");
    final String user         = config.getString("mail.smtp.user");
    final String auth         = config.getString("mail.smtp.auth");
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
    
    String to = "qkpigstar@gmail.com";// change accordingly

    // Get the session object
    Properties props = new Properties();
    props.put("mail.smtp.host", host);
    props.put("mail.smtp.socketFactory.port", port);
    props.put("mail.smtp.socketFactory.class", socketFactory_class);
    props.put("mail.smtp.port", port);
    props.put("mail.smtp.auth", auth);
    props.put("mail.smtp.ssl.trust", ssl_trust);

    Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(user, password);                                                      // accordingly
      }
    });

    // compose message
    try {
      MimeMessage message = new MimeMessage(session);
      message.setFrom(new InternetAddress("playStar@gmail.com"));// change accordingly
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
      message.setSubject("測試信件");
      message.setText("測試內容");

      // send message
      Transport.send(message);

      System.out.println("message sent successfully");

    } catch (MessagingException e) {
      throw new RuntimeException(e);
    }

  }

  public static void main(String args[]) {

    new Utils_Email().sendMail();
  }
}
