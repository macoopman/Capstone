package capstone.domain;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
public class ELOAnswer {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private long Id;

   private long answers;

   private long numOfResponses;

   @OneToOne(targetEntity = ELOQuestion.class)
   private ELOQuestion question;


   public ELOAnswer() {
   }

   public ELOAnswer(ELOQuestion question, long answers, long numOfResponses) {
      this.question = question;
      this.answers = answers;
      this.numOfResponses = numOfResponses;
   }

   public long getId() {
      return Id;
   }
}
