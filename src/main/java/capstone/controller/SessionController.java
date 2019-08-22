package capstone.controller;

import capstone.dto.*;
import capstone.services.SessionService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/sessions")
public class SessionController {


   private SessionService sessionService;

   public SessionController(SessionService sessionService) {
      this.sessionService = sessionService;
   }

   @PostMapping("/{session_id}/updateQuestion/{questionAnswer_id}")
   public void updateQuestion(@PathVariable("session_id") Long session_id,
                              @PathVariable("questionAnswer_id") Long question_id,
                              @RequestBody @Valid QuestionResponseDto dto){
      sessionService.updateQuestionResponse(session_id, question_id, dto.getResponse());
   }

   @GetMapping("/{session_id}/results")
   public List<ResultsDto> results(@PathVariable("session_id") long session_id){
      List<ResultsDto> results = sessionService.results(session_id);
      return results;
   }

   @PostMapping("/{session_id}/addStudent")
   public void addStudent(@PathVariable("session_id") long id, @RequestBody @Valid AddStudentProfessorDto dto){
         sessionService.addStudent(id, dto);
   }

   @PostMapping("/{session_id}/addProfessor")
   public void addProfessor(@PathVariable("session_id") long user_Id, @RequestBody @Valid AddStudentProfessorDto dto){
      sessionService.addProfessor(user_Id, dto);
   }


   @PostMapping("/{session_id}/addComment")
   private void addComment(@PathVariable("session_id") long session_id, @RequestBody @Valid AddCommentDto dto){
      sessionService.addComment(session_id, dto);
   }

   @GetMapping("/{session_id}/learningstyleresults")
   private List<LearningStyleResultsDto> learningStyleResults(@PathVariable("session_id") long session_id){
      return sessionService.learningStyleResults(session_id);
   }


}
