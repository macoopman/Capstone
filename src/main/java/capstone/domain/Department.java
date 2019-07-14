package capstone.domain;


import lombok.Data;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;


@Entity
@Data
public class Department {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private String departmentAbbreviation;
   private String departmentName;

   @ManyToMany
   List<Klass> departmentClasses;

   public Department(){}

   public Department(String departmentAbbreviation, String departmentName, Klass departmentClasses) {
      this.departmentAbbreviation = departmentAbbreviation;
      this.departmentName = departmentName;
      this.departmentClasses = Arrays.asList(departmentClasses);
   }
}
