package capstone.controller;

import capstone.domain.User;
import capstone.dto.NewQuestionDto;
import capstone.dto.RegisterDto;
import capstone.services.QuestionService;
import capstone.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;


@RestController
@RequestMapping("/eloquestion")
public class QuestionController {


   @Autowired
   private QuestionService questionService;

   @PostMapping("/add")
   public void add(@RequestBody @Valid NewQuestionDto dto) throws Exception{
      questionService.addQuestion(dto.getClassId(),dto.getWeekNumber(), dto.getQuestion());
   }

}
