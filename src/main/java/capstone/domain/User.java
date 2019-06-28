package capstone.domain;

import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private long id;

   @Size(min = 2)
   @UniqueElements
   @NotNull
   private String username;

   @NotNull
   @Size(min=8, max=20, message = "Password must be b/t {min} and {max}")
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
