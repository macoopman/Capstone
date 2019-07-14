package capstone.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.parameters.P;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Person {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;

   private String firstName;

   private String lastName;

   @Email
   private String email;


   private Date dateJoined;


   @OneToOne(targetEntity = Department.class)
   private Department currentDepartment;

   @OneToOne(targetEntity = Klass.class)
   private Klass currentClass;

   @OneToOne(targetEntity = Session.class)
   private Session sessionsList;


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
