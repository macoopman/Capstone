package capstone.controller;

import capstone.dto.LearningStyleDto;
import capstone.services.LearningStyleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
}
