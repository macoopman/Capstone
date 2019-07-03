package capstone.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Entity
@Data
public class Session {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private long id;


   private String sessionName;

   private LocalDate start_date;

   private LocalDate end_date;



//   private double rating;


   @ManyToMany(targetEntity = Student.class)
   private List<Student> students;

   @ManyToMany(targetEntity = Professor.class)
   private List<Professor> professors;

   @ManyToMany(targetEntity = Comment.class)
   private List<Comment> comments;




   @PostPersist
   void buildSessionName(){
      String month = cleanMonth(start_date.getMonth());
      int beginYear = start_date.getYear();
      this.sessionName = month + "_" + beginYear;;
   }


   /**
    * Translates full months in to three/four character representation
    * @param month Month object
    * @return three or four char representation of Month
    */
   private static String cleanMonth(Month month){
      String cleanMonth = month.getDisplayName(TextStyle.FULL, Locale.US);
      cleanMonth = cleanMonth.toUpperCase();
      if (cleanMonth.length() <= 4){
         return cleanMonth;
      }
      else {
         return cleanMonth.substring(0,3);
      }

   }

}
