package capstone.services;

import capstone.domain.ELOAnswer;
import capstone.domain.ELOQuestion;
import capstone.domain.Klass;
import capstone.domain.Session;
import capstone.error_message.ErrorMessages;
import capstone.exceptions.InvalidIdException;
import capstone.repositories.ELOAnswerRepository;
import capstone.repositories.ELOQuestionRepository;
import capstone.repositories.KlassRepository;
import capstone.repositories.SessionRepository;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.*;

@Service
public class ClassService {

   private ELOQuestionRepository eloQuestionRepository;
   private KlassRepository klassRepository;
   private SessionRepository sessionRepository;
   private ELOAnswerRepository eloAnswerRepository;

   public ClassService(ELOQuestionRepository eloQuestionRepository, KlassRepository klassRepository,
                       SessionRepository sessionRepository, ELOAnswerRepository eloAnswerRepository ) {
      this.eloQuestionRepository = eloQuestionRepository;
      this.klassRepository = klassRepository;
      this.sessionRepository = sessionRepository;
      this.eloAnswerRepository = eloAnswerRepository;
   }

   public void addQuestion(Long classId, String weekNumber, String message){
      Optional<Klass> klass = klassRepository.findById(classId);
      if(!klass.isPresent()) throw new InvalidIdException(ErrorMessages.NO_RECORED_FOUND.getErrorMessage());
      eloQuestionRepository.save(new ELOQuestion(klass.get(), Integer.parseInt(weekNumber), message));
   }

   public void addSession(Long id, LocalDate startDate, LocalDate endDate) {
      Optional<Klass> klass = klassRepository.findById(id);
      if(!klass.isPresent()) throw new InvalidIdException(ErrorMessages.NO_RECORED_FOUND.getErrorMessage());
      Session session = createSession(startDate, endDate, klass);
      klass.get().appendSession(session);
      klassRepository.save(klass.get());
      sessionRepository.save(session);

   }


   // Helper Methods
   private Session createSession(LocalDate startDate, LocalDate endDate, Optional<Klass> klass) {
      Session session = new Session();
      session.setStartDate(startDate);
      session.setEndDate(endDate);

      updateList(klass, session);
      session.setKlass(klass.get());
      session.setKlass(klass.get());
      return session;
   }

   private void updateList(Optional<Klass> klass, Session session) {
      List<ELOAnswer> answers = new LinkedList<>();
      for (ELOQuestion question : klass.get().getQuestions()){
         ELOAnswer answer = new ELOAnswer(question, 0,0);
         answers.add(answer);
         eloAnswerRepository.save(answer);
      }
      session.setQuestionAndAnswers(answers);
   }


}
