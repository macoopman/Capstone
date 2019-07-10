package capstone.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Person {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;

//   @NotNull
   private String firstName;

   private String lastName;

//   @Email
   private String email;

   private Date dateJoined;




   public Person(@NotNull String firstName, @NotNull String lastName, @Email String email) {
      this.firstName = firstName;
      this.lastName = lastName;
      this.email = email;
   }

   @PrePersist
   void joinDate(){
      this.dateJoined = new Date();
   }

}
