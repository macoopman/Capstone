package capstone.controller;


import capstone.domain.User;
import capstone.dto.LoginDto;
import capstone.dto.UserDto;
import capstone.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/users")
public class UserController {

   @Autowired
   private UserService userService;

//   @PostMapping("/signin")
//   public LoginDtoData login(@RequestBody @Valid LoginDto loginDto) {
//      return userService.signin(loginDto.getUsername(), loginDto.getPassword()).orElseThrow(()->
//         new HttpServerErrorException(HttpStatus.FORBIDDEN, "Login Failed"));
//
//   }



   @PostMapping("/signin")
   public UserDto login(@RequestBody @Valid LoginDto loginDto) {
      return userService.signin(loginDto.getUsername(), loginDto.getPassword());



   }

   @PostMapping("/signup")
   public User signup(@RequestBody @Valid LoginDto loginDto){
      return userService.signup(loginDto.getUsername(), loginDto.getPassword()).orElseThrow(() -> new RuntimeException("User already exists"));
   }

   @ResponseStatus(HttpStatus.NOT_FOUND)
   @ExceptionHandler(NoSuchElementException.class)
   public ResponseEntity return400(NoSuchElementException ex) {
        return new ResponseEntity(null, HttpStatus.FORBIDDEN);

   }

}
