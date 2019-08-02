package capstone.services;

import capstone.domain.ELOQuestion;
import capstone.domain.Klass;
import capstone.error_message.ErrorMessages;
import capstone.exceptions.InvalidIdException;
import capstone.repositories.ELOQuestionRepository;
import capstone.repositories.KlassRepository;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class QuestionService {

   private ELOQuestionRepository eloQuestionRepository;
   private KlassRepository klassRepository;

   public QuestionService(ELOQuestionRepository eloQuestionRepository, KlassRepository klassRepository) {
      this.eloQuestionRepository = eloQuestionRepository;
      this.klassRepository = klassRepository;
   }

   public void addQuestion(String classId, String weekNumber, String message){
      Optional<Klass> klass = klassRepository.findById(Long.parseLong(classId));
      if(!klass.isPresent()) throw new InvalidIdException(ErrorMessages.NO_RECORED_FOUND.getErrorMessage());
      eloQuestionRepository.save(new ELOQuestion(klass.get(), Integer.parseInt(weekNumber), message));
   }
}
