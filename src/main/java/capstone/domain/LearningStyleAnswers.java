package capstone.domain;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class LearningStyleAnswers {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long Id;

   @OneToOne(targetEntity = LearningStyleQuestion.class)
   private LearningStyleQuestion question;

   private long answers;

   public LearningStyleAnswers() {
   }

   public LearningStyleAnswers(LearningStyleQuestion question, long answers) {
      this.question = question;
      this.answers = answers;
   }

   public long getId() {
      return Id;
   }
}
