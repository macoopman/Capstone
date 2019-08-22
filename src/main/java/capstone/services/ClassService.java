package capstone.services;

import capstone.domain.ELOAnswer;
import capstone.domain.ELOQuestion;
import capstone.domain.Klass;
import capstone.domain.Session;
import capstone.exceptions.ClassServiceException;
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
      Klass klass = klassRepository.findById(classId).orElseThrow(() -> new ClassServiceException("Class Not Found"));
      eloQuestionRepository.save(new ELOQuestion(klass, Integer.parseInt(weekNumber), message));
   }

   public void addSession(Long classId, LocalDate startDate, LocalDate endDate) {
      Klass klass = klassRepository.findById(classId).orElseThrow(() -> new ClassServiceException("Class Not Found"));
      Session session = createSession(startDate, endDate, klass);
      klass.appendSession(session);
      klassRepository.save(klass);
      sessionRepository.save(session);

   }


   // Helper Methods
   private Session createSession(LocalDate startDate, LocalDate endDate, Klass klass) {
      Session session = new Session();
      session.setStartDate(startDate);
      session.setEndDate(endDate);

      updateList(klass, session);
      session.setKlass(klass);
      session.setKlass(klass);
      return session;
   }

   private void updateList(Klass klass, Session session) {
      List<ELOAnswer> answers = new LinkedList<>();
      for (ELOQuestion question : klass.getQuestions()){
         ELOAnswer answer = new ELOAnswer(question, 0,0);
         answers.add(answer);
         eloAnswerRepository.save(answer);
      }
      session.setQuestionAndAnswers(answers);
   }


}
