package capstone.controller;

import capstone.domain.User;
import capstone.dto.*;
import capstone.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Optional;


@RestController
@RequestMapping("/users")
public class UserController {

   private final UserService userService;

   public UserController(UserService userService) {
      this.userService = userService;
   }

   @PostMapping("/signin")
   public UserDto login(@RequestBody LoginDto loginDto) {
      return userService.signin(loginDto.getUsername(), loginDto.getPassword());
   }

   @PostMapping("/addStudent")
   public Optional<User> addStudent(@RequestBody @Valid NewStudentDto dto){
      return userService.addStudent(dto.getFirstName(), dto.getLastName(), dto.getPassword(), dto.getEmail(), dto.getGpa(), dto.getMajor());
   }

   @PostMapping("/addProfessor")
   public Optional<User> addProfession(@RequestBody @Valid NewProfessorDto dto){
      return userService.addProfessor(dto.getFirstName(), dto.getLastName(), dto.getPassword(), dto.getEmail(), dto.getRating());
   }

   @PostMapping("/addAdmin")
   public Optional<User> addAdmin(@RequestBody @Valid NewAdminDto dto){
      return userService.addAdmin(dto.getFirstName(), dto.getLastName(), dto.getPassword(), dto.getEmail());
   }

   @PostMapping("/{userId}/updatelearningstyle/{questionId}")
   public void updateLearningStyle(@PathVariable("userId") long userId, @PathVariable("questionId") long questionId, @RequestBody UpdateLearningStyleDto dto){
      userService.updateLearningStyle(userId, questionId, dto.getUpdatedValue());
   }

   @PostMapping("/recover")
   public RecoverReturnDto recover(@RequestBody PasswordRecoverDto dto ) throws Exception {
         return userService.recoverPassword(dto.getUsername(), dto.getEmail());
   }

   @PostMapping("/recover/passwordChange")
   public void reset(@RequestBody PasswordResetDto dto) {
      userService.resetPassword(dto.getUserId(), dto.getTempPassword(), dto.getNewPassword());
   }

}
