package capstone.controller;

import capstone.dto.QuestionResponseDto;
import capstone.dto.ResultsDTO;
import capstone.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/sessions")
public class SessionController {

   @Autowired
   private SessionService sessionService;

   @PostMapping("/{session_id}/updateQuestion/{questionAnswer_id}")
   public void updateQuestion(@PathVariable("session_id") Long session_id,
                              @PathVariable("questionAnswer_id") Long question_id,
                              @RequestBody @Valid QuestionResponseDto dto){
      sessionService.updateQuestion(session_id, question_id, dto.getResponse());
   }

   @GetMapping("/{session_id}/results")
   public List<ResultsDTO> results(@PathVariable("session_id") long session_id){
      List<ResultsDTO> results = sessionService.results(session_id);
      return results;
   }
}
