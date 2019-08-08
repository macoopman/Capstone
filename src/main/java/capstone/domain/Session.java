package capstone.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;

@Entity
@Data
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
//@JsonIdentityReference(alwaysAsId=true)
public class Session {



   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;
   private String sessionName;
   private LocalDate startDate;
   private LocalDate endDate;


   @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   @JoinColumn(name="class_id")
   private Klass klass;


   @OneToMany(mappedBy = "currentSession")
   private List<Student> students;


   @OneToMany(mappedBy = "currentSession")
   private List<Professor> professors;



   @OneToMany(mappedBy = "session")
   private List<Comment> comments;




   @ElementCollection(targetClass=ELOAnswer.class)
   private List<ELOAnswer> questionAndAnswers;






   @PostPersist
   void postPersist(){
      buildDate();
   }




   private void buildDate() {
      String month = cleanMonth(startDate.getMonth());
      int beginYear = startDate.getYear();
      this.sessionName =  month + "_" + beginYear;
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
