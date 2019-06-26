package capstone.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private long id;

   private String username;

   private String password;

   private String firstName;

   private String lastName;

   private Date dateJoined;





   public User() {
   }

   public User(String username, String password, String firstName, String lastName) {
      this.username = username;
      this.password = password;
      this.firstName = firstName;
      this.lastName = lastName;
   }

   @PrePersist
   void joinDate(){
      this.dateJoined = new Date();
   }
}
