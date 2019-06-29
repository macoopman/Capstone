package capstone.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

public class Session {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private long id;

   @Column(unique = true)
   private String sessionName;

   private Date start_date;

   private Date end_date;

   @ManyToMany(targetEntity = Student.class)
   private List<Student> students;

   @ManyToMany(targetEntity = Professor.class)
   private List<Professor> professors;

   // implement Comment

   private double rating;

}
