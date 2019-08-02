package capstone.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class ELOAnswer {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private long id;

//   private ELOQuestion question;

   private long answers;

   private long numOfAnswers;


   public ELOAnswer() {
   }

//   public ELOAnswer(ELOQuestion question, long answers, long numOfAnswers) {
//      this.question = question;
//      this.answers = answers;
//      this.numOfAnswers = numOfAnswers;
//   }
}
