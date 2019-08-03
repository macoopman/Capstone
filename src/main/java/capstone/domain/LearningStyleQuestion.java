package capstone.domain;


import lombok.Data;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@RestResource(rel="learningstyle", path="learningstyle")
public class LearningStyleQuestion {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private long id;

   private String question;

   public LearningStyleQuestion() {
   }

   public LearningStyleQuestion(String question) {
      this.question = question;
   }
}
