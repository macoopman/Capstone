package capstone.controller;


import capstone.domain.User;
import capstone.security.LoginDto;
import capstone.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

   @Autowired
   private UserService userService;

   @PostMapping("/signin")
   public Authentication login(@RequestBody @Valid LoginDto loginDto){
      return userService.signin(loginDto.getUsername(), loginDto.getPassword());
   }

   @PostMapping("/signup")
   //@PreAuthorize("hasRole('ROLE_ADMIN')")
   public User signup(@RequestBody @Valid LoginDto loginDto){
      return userService.signup(loginDto.getUsername(), loginDto.getPassword(), loginDto.getFirstName(),
         loginDto.getLastName()).orElseThrow(() -> new RuntimeException("User already exists"));
   }


}
