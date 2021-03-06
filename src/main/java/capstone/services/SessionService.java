package capstone.services;

import capstone.domain.*;
import capstone.dto.*;
import capstone.exceptions.SessionServiceException;
import capstone.repositories.*;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class SessionService {

   private static final String ZERO = "0";

   private final SessionRepository sessionRepository;
   private final StudentRepository studentRepository;
   private final ProfessorRepository professorRepository;
   private final ELOAnswerRepository eloAnswerRepository;
   private final UserRepository userRepository;
   private final CommentRepository commentRepository;
   private final LearningStyleQuestionRepository learningStyleQuestionRepository;

   public SessionService(SessionRepository sessionRepository, StudentRepository studentRepository,
                         ProfessorRepository professorRepository, ELOAnswerRepository eloAnswerRepository,
                         UserRepository userRepository, CommentRepository commentRepository,
                         LearningStyleQuestionRepository learningStyleQuestionRepository) {
      this.sessionRepository = sessionRepository;
      this.studentRepository = studentRepository;
      this.professorRepository = professorRepository;
      this.eloAnswerRepository = eloAnswerRepository;
      this.userRepository = userRepository;
      this.commentRepository = commentRepository;
      this.learningStyleQuestionRepository = learningStyleQuestionRepository;
   }


   public void updateQuestionResponse(Long session_id, Long question_id, String response){
      Session session = sessionRepository.findById(session_id).orElseThrow( () -> new SessionServiceException("Session Id Not Found") );
      ELOAnswer answerItem = eloAnswerRepository.findById(question_id).orElseThrow( () -> new SessionServiceException("Question Id Not Found") );

      if( !session.getQuestionAndAnswers().contains(answerItem)){
         System.out.println("------------- Does not Contain");
      }

      answerItem.setAnswers(answerItem.getAnswers() + Long.parseLong(response));
      answerItem.setNumOfResponses( answerItem.getNumOfResponses() + 1);

      eloAnswerRepository.save(answerItem);
   }


   public List<ResultsDto> results(long session_id, long week_number){
      Session session = sessionRepository.findById(session_id).orElseThrow( () -> new SessionServiceException("Session Id Not Found") );
      List<ResultsDto> results = new LinkedList<>();

      for( ELOAnswer answer : session.getQuestionAndAnswers()){
         ResultsDto result = new ResultsDto();

         if (answer.getQuestion().getWeek() == week_number){
            result.setId(String.valueOf(answer.getId()));
            result.setQuestion(answer.getQuestion().getMessage());
            if(!(0 == answer.getNumOfResponses())){
               result.setAverage(String.valueOf( answer.getAnswers() / answer.getNumOfResponses() ));
            }
            else {
               result.setAverage(ZERO);
            }

            result.setTotalResponses(String.valueOf(answer.getNumOfResponses()));
            results.add(result);
         }
      }
      return results;
   }


   public void addStudent(long id, AddStudentProfessorDto dto) {
      Session session = sessionRepository.findById(id).orElseThrow( () -> new SessionServiceException("Session Id Not Found") );
      Student student = studentRepository.findById(Long.parseLong(dto.getUser_Id())).orElseThrow( () -> new SessionServiceException("Student Id Not Found") );
      student.setCurrentSession(session);
      studentRepository.save(student);
   }


   public void addProfessor(long id, AddStudentProfessorDto dto) {
      Session session = sessionRepository.findById(id).orElseThrow( () -> new SessionServiceException("Session Id Not Found") );
      Professor professor = professorRepository.findById(Long.parseLong(dto.getUser_Id())).orElseThrow( () -> new SessionServiceException("Professor Id Not Found") );
      professor.setCurrentSession(session);
      professorRepository.save(professor);
   }


   public CommentReturnDto addComment(long session_id, AddCommentDto dto) {
      User user = userRepository.findById(Long.parseLong(dto.getUserId())).orElseThrow( () -> new SessionServiceException("User Id Not Found") );
      Session session = sessionRepository.findById(session_id).orElseThrow( () -> new SessionServiceException("Session Id Not Found") );
      User currentUser = userRepository.findById(Long.parseLong(dto.getUserId())).orElseThrow(() -> new SessionServiceException("User id not found"));
      Comment comment = createComment(dto, user, session, currentUser);
      commentRepository.save(comment);
      updateComment(session, comment);
      comment = commentRepository.save(comment);
      return new CommentReturnDto(String.valueOf(comment.getId()));
   }

   private void updateComment(Session session, Comment comment) {
      comment.setParentId((int) comment.getId());
      comment.setSessionName(session.getSessionName());
      comment.setParentSessionId("" + session.getId());
   }

   private Comment createComment(AddCommentDto dto, User user, Session session, User currentUser) {
      Comment comment = new Comment();
      comment.setMessage(dto.getMessage());
      comment.setUser(user);
      comment.setSession(session);
      comment.setParentComment(null);
      comment.setIsAnonymous(dto.getIsAnonymous());
      comment.setUserName(currentUser.getUsername());
      return comment;
   }


   public List<LearningStyleResultsDto> learningStyleResults(long session_id) {
      Session session = sessionRepository.findById(session_id).orElseThrow(() -> new SessionServiceException("Session Id Not Found"));
      Iterable<LearningStyleQuestion> questions = learningStyleQuestionRepository.findAll();
      List<Student> students = session.getStudents();


      List<LearningStyleResultsDto> results = new ArrayList<>();

      // initialize results list
      for(LearningStyleQuestion question : questions){
         LearningStyleResultsDto dto = new LearningStyleResultsDto();
         dto.setAverage(0);
         dto.setQuestion(question.getQuestion());
         dto.setQuestionId(question.getId());
         results.add(dto);
      }

      for(Student student : students){
         List<LearningStyleAnswers> answersList = student.getLearningStyleAnswersList();
         for (LearningStyleAnswers answer : answersList){
            for (LearningStyleResultsDto result : results){
               if (result.getQuestionId() == answer.getQuestion().getId()){
                  int total = result.getTotal() + (int)answer.getAnswers();
                  int responses = result.getResponses() + 1;
                  double average = total /responses;

                  result.setTotal(total);
                  result.setResponses(responses);
                  result.setAverage(average);
               }
            }
         }

      }


      return results;
   }
}
