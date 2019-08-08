package capstone.services;

import capstone.domain.*;
import capstone.dto.AddCommentDto;
import capstone.dto.AddStudentProfessorDto;
import capstone.dto.CommentReturnDto;
import capstone.dto.ResultsDTO;
import capstone.repositories.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class SessionService {

   private SessionRepository sessionRepository;
   private StudentRepository studentRepository;
   private ProfessorRepository professorRepository;
   private ELOAnswerRepository eloAnswerRepository;
   private UserRepository userRepository;
   private CommentRepository commentRepository;

   public SessionService(SessionRepository sessionRepository, StudentRepository studentRepository,
                         ProfessorRepository professorRepository, ELOAnswerRepository eloAnswerRepository,
                         UserRepository userRepository, CommentRepository commentRepository) {
      this.sessionRepository = sessionRepository;
      this.studentRepository = studentRepository;
      this.professorRepository = professorRepository;
      this.eloAnswerRepository = eloAnswerRepository;
      this.userRepository = userRepository;
      this.commentRepository = commentRepository;
   }

   /**
    * Perform update to a specific class/session ELO question
    *
    * @param session_id Session user_Id
    * @param question_id Id of question
    * @param response   Students response
    */
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


   /**
    * Return JSON data showing the current class responses and averages for the ELO questions
    *
    * @param session_id
    * @return JSON list of results
    */
   public List<ResultsDTO> results(long session_id){
      Optional<Session> question = sessionRepository.findById(session_id);
      return getResultsDTOS(question);
   }


   /**
    * Add a new student to a session
    *
    * @param id Session Id
    * @param dto Student Add Data Transfer Object
    */
   public void addStudent(long id, AddStudentProfessorDto dto) {
      Optional<Session> session = sessionRepository.findById(id);

      Optional<Student> student = studentRepository.findById(Long.parseLong(dto.getUser_Id()));

      student.get().setCurrentSession(session.get());
      studentRepository.save(student.get());
   }


   public void addProfessor(long id, AddStudentProfessorDto dto) {
      Optional<Session> session = sessionRepository.findById(id);
      Optional<Professor> professor = professorRepository.findById(Long.parseLong(dto.getUser_Id()));

      professor.get().setCurrentSession(session.get());
      professorRepository.save(professor.get());
   }


   public void addComment(long session_id, AddCommentDto dto) {
      Optional<User> user = userRepository.findById(Long.parseLong(dto.getUserId()));
      Optional<Session> session = sessionRepository.findById(session_id);


      Comment comment = new Comment();
      comment.setMessage(dto.getMessage());
      comment.setUser(user.get());
      comment.setSession(session.get());
      comment.setParentComment(null);


      commentRepository.save(comment);
      comment.setParentId((int) comment.getId());
      commentRepository.save(comment);
   }



   // Helper Methods

   private List<ResultsDTO> getResultsDTOS(Optional<Session> question) {
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
