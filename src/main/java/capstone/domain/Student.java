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
public class Student extends Person {

   private double gpa;

   private String major;






   public Student(@NotNull String firstName, @NotNull String lastName, @Email String email, double gpa, String major) {
      super(firstName, lastName, email);
      this.gpa = gpa;
      this.major = major;
   }




}
