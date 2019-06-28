package capstone.domain;


import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Student extends User {

   @Size(min=0, max=4)
   private double gpa;

   private String major;



   public Student(String username, String password, String firstName, String lastName, double gpa, String major) {
      super(username, password, firstName, lastName);
      this.gpa = gpa;
      this.major = major;
   }
}
