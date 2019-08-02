package capstone.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Data
@RestResource(rel="eloquestion", path="eloquestion")
public class ELOQuestion {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private long id;


   @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   @JoinColumn(name="question_class")
   private Klass klass;


   private int week;

   @Size(max=255)
   private String message;


   public ELOQuestion() {
   }

   public ELOQuestion(Klass klass, @Size(min = 0, max = 8) int week, @Size(max = 255) String message) {
      this.klass = klass;
      this.week = week;
      this.message = message;
   }
}


