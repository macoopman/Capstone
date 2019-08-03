package capstone.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Data
public class ELOAnswer {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private long Id;

   @OneToOne(targetEntity = ELOQuestion.class)
   private ELOQuestion question;

   private long answers;

   private long numOfResponses;


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
