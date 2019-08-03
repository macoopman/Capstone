package capstone.services;

import capstone.domain.ELOAnswer;
import capstone.domain.ELOQuestion;
import capstone.domain.Session;
import capstone.dto.ResultsDTO;
import capstone.repositories.ELOAnswerRepository;
import capstone.repositories.SessionRepository;
import org.apache.commons.math3.stat.StatUtils;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class SessionService {

   private SessionRepository sessionRepository;
   private ELOAnswerRepository eloAnswerRepository;

   public SessionService(SessionRepository sessionRepository, ELOAnswerRepository eloAnswerRepository) {
      this.sessionRepository = sessionRepository;
      this.eloAnswerRepository = eloAnswerRepository;
   }

   public void updateQuestion(Long session_id, Long question_id, String response) {
      Optional<Session> session = sessionRepository.findById(session_id);
      Optional<ELOAnswer> question = eloAnswerRepository.findById(question_id);

      if( !session.get().getQuestionAndAnswers().contains(question)){
         // TODO: 8/2/19 add some exceptions
         System.out.println("------------- Does not Contain");
      }

      question.get().setAnswers(question.get().getAnswers() + Long.parseLong(response));
      question.get().setNumOfResponses( question.get().getNumOfResponses() + 1);

      eloAnswerRepository.save(question.get());
   }


   public List<ResultsDTO> results(long session_id){
      Optional<Session> question = sessionRepository.findById(session_id);

      List<ResultsDTO> results = new LinkedList<>();
      for( ELOAnswer answer : question.get().getQuestionAndAnswers()){
         ResultsDTO result = new ResultsDTO();
         result.setId(String.valueOf(answer.getId()));
         result.setQuestion(answer.getQuestion().getMessage());
         if(!(0 == answer.getNumOfResponses())){
            result.setAverage(String.valueOf( answer.getAnswers() / answer.getNumOfResponses() ));
         }
         else {
            result.setAverage("0");
         }

         result.setTotalResponses(String.valueOf(answer.getNumOfResponses()));
         results.add(result);
      }
      return results;
   }
}
