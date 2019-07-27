package capstone.controller;


import capstone.domain.User;
import capstone.dto.LoginDto;
import capstone.dto.PasswordRecoverDto;
import capstone.dto.RegisterDto;
import capstone.dto.UserDto;
import capstone.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.Optional;


@RestController
@RequestMapping("/users")
public class UserController {

   @Autowired
   private UserService userService;

   @PostMapping("/signin")
   public UserDto login(@RequestBody LoginDto loginDto) {
      String username = loginDto.getUsername();
      String password = loginDto.getPassword();
      return userService.signin(username, password);
   }

   @PostMapping("/register")
   public Optional<User> register(@RequestBody @Valid RegisterDto registerDto) throws Exception{
      return userService.register(registerDto.getUsername(), registerDto.getPassword(), registerDto.getUserType());
   }

   @PostMapping("/recover")
   public void recover(@RequestBody PasswordRecoverDto dto ) throws Exception {
         userService.recover(dto.getUsername(), dto.getEmail());
   }




}
