package capstone.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Session {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private long id;

   @NotNull
   private String sessionName;

   @NotNull
   private Date start_date;

   @NotNull
   private Date end_date;

   @ManyToMany(targetEntity = Student.class)
   private List<Student> students;

   @ManyToMany(targetEntity = Professor.class)
   private List<Professor> professors;

   @ManyToMany(targetEntity = Comment.class)
   private List<Comment> comments;

   private double rating;

   // IDEA: auto-create the session name based off the class name and the start date
      // how would it know its class?


}
