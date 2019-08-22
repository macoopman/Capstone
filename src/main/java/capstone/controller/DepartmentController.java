package capstone.controller;

import capstone.dto.NewClassDto;
import capstone.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

   @Autowired
   private DepartmentService departmentService;

   @PostMapping("/{department_id}/addClass")
   public void addClass(@PathVariable("department_id") long id, @RequestBody @Valid NewClassDto dto){
      departmentService.addClass(id, dto);
   }
}
