package capstone.domain;


import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Student extends Person {

   private double gpa;

   private String major;







   public Student(String firstName, String lastName,  double gpa, String major) {
      super(firstName, lastName);
      this.gpa = gpa;
      this.major = major;
   }
}
