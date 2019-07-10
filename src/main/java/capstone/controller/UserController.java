package capstone.controller;


import capstone.domain.User;
import capstone.security.LoginDto;
import capstone.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import javax.validation.Valid;


@RestController
@RequestMapping("/users")
public class UserController {

   @Autowired
   private UserService userService;

   @PostMapping("/signin")
   public String login(@RequestBody @Valid LoginDto loginDto) {
      return userService.signin(loginDto.getUsername(), loginDto.getPassword()).orElseThrow(()->
         new HttpServerErrorException(HttpStatus.FORBIDDEN, "Login Failed"));

   }

   @PostMapping("/signup")
   public User signup(@RequestBody @Valid LoginDto loginDto){
      return userService.signup(loginDto.getUsername(), loginDto.getPassword()).orElseThrow(() -> new RuntimeException("User already exists"));
   }

}
