package capstone.domain;


import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
//@DiscriminatorValue("1")
public class Student extends Person {

   private double gpa;

   private String major;


   public Student(Long id, String firstName, String lastName, String email, double gpa, String major) {
      super(id, firstName, lastName, email);
      this.gpa = gpa;
      this.major = major;
   }
}
