package capstone.controller;

import capstone.dto.GlobalLearningStyleResults;
import capstone.dto.LearningStyleDto;
import capstone.services.LearningStyleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/learningstyle")
public class LearningStyleController {

   final LearningStyleService learningStyleService;

   public LearningStyleController(LearningStyleService learningStyleService) {
      this.learningStyleService = learningStyleService;
   }


   @PostMapping("/add")
   public void addQuestion(@RequestBody @Valid LearningStyleDto dto){
      learningStyleService.addQuestion(dto.getQuestion());
   }

   @GetMapping("/globalResults")
   public List<GlobalLearningStyleResults> getGlobalResults(){
      return learningStyleService.getGlobalResults();
   }
}
