package capstone.services;

import capstone.domain.LearningStyleQuestion;
import capstone.repositories.LearningStyleQuestionRepository;
import org.springframework.stereotype.Component;

@Component
public class LearningStyleService {


   private final LearningStyleQuestionRepository learningStyleQuestionRepository;

   public LearningStyleService(LearningStyleQuestionRepository learningStyleQuestionRepository) {
      this.learningStyleQuestionRepository = learningStyleQuestionRepository;
   }

   public void addQuestion(String question) {
      learningStyleQuestionRepository.save(new LearningStyleQuestion(question));
   }
}
