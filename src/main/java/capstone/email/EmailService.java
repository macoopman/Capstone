package capstone.email;

import capstone.domain.User;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Data
@Component
public class EmailService {




//   @Value("${spring.mail.username}")
//   private String userNameApp;

//   @Value("${spring.mail.password")
//   private String passwordApp;
//
//   @Value("${spring.mail.username")
//   private String host;
//
//   @Value("${spring.mail.username")
//   private String port;



   public void sendRecoveryMessage(User user, String tempPassword){
      Properties prop = new Properties();
      prop.put("mail.smtp.host", "smtp.gmail.com");
      prop.put("mail.smtp.port", "587");
      prop.put("mail.smtp.auth", "true");
      prop.put("mail.smtp.starttls.enable", "true"); //TLS


      Session session = Session.getInstance(prop,
         new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication("coopmanmike@gmail.com", "PX82LYv&vhG9xt$");
            }
         });

      try {

         Message message = new MimeMessage(session);
         message.setFrom(new InternetAddress("passwordReset@whiteboard.com"));
         message.setRecipients(
            Message.RecipientType.TO,
            InternetAddress.parse(user.getUserData().getEmail())
         );
         message.setSubject("Password Reset - Whiteboard");
         message.setText(
            "Dear " + user.getUserData().getFirstName() + "\n\n" +
            "\tForgot your password. That's ok. \n\n" +
            "Temp Password: " + tempPassword  + "\n\n" +
            "Enter temp password when in app \n\n\n " +
            "WhiteBoard Team ");


         Transport.send(message);


      } catch (MessagingException e) {
         e.printStackTrace();
      }
   }
}
