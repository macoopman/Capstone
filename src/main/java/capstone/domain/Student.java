package capstone.domain;


import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Student extends User {

   private double gpa;

   private String major;



   public Student(String username, String password, String firstName, String lastName, double gpa, String major) {
      super(username, password, firstName, lastName);
      this.gpa = gpa;
      this.major = major;
   }
}
