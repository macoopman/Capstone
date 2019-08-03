package capstone.controller;


import capstone.dto.SessionAddDto;
import capstone.dto.NewQuestionDto;
import capstone.services.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PostUpdate;
import javax.validation.Valid;

@RestController
@RequestMapping("/classes")
public class ClassController {

   @Autowired
   private ClassService classService;

   @PostMapping("/{id}/addQuestion")
   public void add(@PathVariable("id") Long id, @RequestBody @Valid NewQuestionDto dto) throws Exception{
      classService.addQuestion(id, dto.getWeekNumber(), dto.getQuestion());
   }

   @PostMapping("/{id}/addSession")
   public void addSession(@PathVariable("id") Long id, @RequestBody @Valid SessionAddDto dto){
      classService.addSession(id, dto.getStartDate(), dto.getEndDate());
   }


}
