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



   @OneToMany(mappedBy = "department")
   private List<Klass> departmentClasses;


   public Department(){}

   public Department(String departmentAbbreviation, String departmentName, Klass departmentClasses) {
      this.departmentAbbreviation = departmentAbbreviation;
      this.departmentName = departmentName;
      this.departmentClasses = Arrays.asList(departmentClasses);
   }

   @Override
   public String toString() {
      return "Department{" +
         "id=" + id +
         ", departmentAbbreviation='" + departmentAbbreviation + '\'' +
         ", departmentName='" + departmentName + '\'' +
         ", departmentClasses=" + departmentClasses +
         '}';
   }
}
