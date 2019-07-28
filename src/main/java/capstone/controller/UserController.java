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

   @Autowired
   private UserService userService;

   @PostMapping("/signin")
   public UserDto login(@RequestBody LoginDto loginDto) {
      return userService.signin(loginDto.getUsername(), loginDto.getPassword());
   }

   @PostMapping("/register")
   public Optional<User> register(@RequestBody @Valid RegisterDto registerDto) throws Exception{
      return userService.register(registerDto.getUsername(), registerDto.getPassword(), registerDto.getUserType());
   }

   @PostMapping("/recover")
   public RecoverReturnDto recover(@RequestBody PasswordRecoverDto dto ) throws Exception {
         return userService.recover(dto.getUsername(), dto.getEmail());
   }

   @PostMapping("/recover/passwordChange")
   public void reset(@RequestBody PasswordResetDto dto) {
      userService.reset(dto.getUserId(), dto.getTempPassword(), dto.getNewPassword());
   }




}
