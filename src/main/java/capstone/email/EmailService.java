package capstone.email;

import capstone.domain.User;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


@Component
public class EmailService {

   private final String username;
   private final String password;
   private final String host;
   private final String port;
   private final String auth;
   private final String isTLS;

   @Autowired
   public EmailService(
      @Value("${spring.mail.username}") String username,
      @Value("${spring.mail.password}") String password,
      @Value("${spring.mail.host}")String host,
      @Value("${spring.mail.port}") String port,
      @Value("${spring.mail.properties.mail.smtp.auth}") String auth,
      @Value("${spring.mail.properties.mail.smtp.starttls.enable}") String isTLS) {
         this.username = username;
         this.password = password;
         this.host = host;
         this.port = port;
         this.auth = auth;
         this.isTLS = isTLS;
   }



   public void sendRecoveryMessage(User user, String tempPassword){
      Properties prop = setProperties();
      Session session = setSession(prop);
      try {
         Message message = buildMessage(user, tempPassword, session);
         Transport.send(message);
      }
      catch (MessagingException e) {
         e.printStackTrace();
      }
   }

   private Message buildMessage(User user, String tempPassword, Session session) throws MessagingException {
      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress("passwordReset@whiteboard.com"));
      message.setRecipients(
         Message.RecipientType.TO,
         InternetAddress.parse(user.getUserData().getEmail())
      );
      message.setSubject("Password Reset - Whiteboard");
      message.setText(
         "Dear " + user.getUserData().getFirstName() + "\n\n" +
         "Forgot your password. That's ok. \n\n" +
         "Temp Password: " + tempPassword  + "\n\n" +
         "Enter temp password when in app \n\n\n " +
         "WhiteBoard Team ");
      return message;
   }

   private Session setSession(Properties prop) {
      return Session.getInstance(prop,
            new Authenticator() {
               protected PasswordAuthentication getPasswordAuthentication() {
                  return new PasswordAuthentication(username, password);
               }
            });
   }

   private Properties setProperties() {
      Properties prop = new Properties();
      prop.put("mail.smtp.host", host);
      prop.put("mail.smtp.port", port);
      prop.put("mail.smtp.auth", auth);
      prop.put("mail.smtp.starttls.enable", isTLS); //TLS
      return prop;
   }
}
