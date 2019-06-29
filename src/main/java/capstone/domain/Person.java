package capstone.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Person {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;

   private String firstName;

   private String lastName;

   private Date dateJoined;





   @PrePersist
   void joinDate(){
      this.dateJoined = new Date();
   }

   public Person() {
   }

   public Person(String firstName, String lastName) {
      this.firstName = firstName;
      this.lastName = lastName;
   }
}
