package capstone.services;

import capstone.domain.*;
import capstone.dto.UserDto;
import capstone.repositories.KlassRepository;
import capstone.repositories.SessionRepository;
import capstone.repositories.UserRepository;
import capstone.repositories.RoleRepository;

import capstone.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class UserService {
   //private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

   private UserRepository userRepository;
   private AuthenticationManager authenticationManager;
   private RoleRepository roleRepository;
   private PasswordEncoder passwordEncoder;
   private JwtProvider jwtProvider;
   private SessionRepository sessionRepository;
   private KlassRepository klassRepository;

   @Autowired
   public UserService(UserRepository userRepository, AuthenticationManager authenticationManager,
                      RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider,
                      SessionRepository sessionRepository, KlassRepository klassRepository) {
      this.userRepository = userRepository;
      this.authenticationManager = authenticationManager;
      this.roleRepository = roleRepository;
      this.passwordEncoder = passwordEncoder;
      this.jwtProvider = jwtProvider;
      this.sessionRepository = sessionRepository;
      this.klassRepository = klassRepository;
   }

   public UserDto signin(String username, String password){
      //return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
      Optional<String> token = Optional.empty();
      // Query for security_users table for the username
      Optional<User> user = userRepository.findByUsername(username);

      if(user.isPresent()) {
         try{
            // authenticate the user
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            // create a new token for the user
            token = Optional.of(jwtProvider.createToken(username, user.get().getRoles()));

         } catch (AuthenticationException e) {
            // log ?
         }
      }

      // Build Login DTO Data
      UserDto userDto = new UserDto();
         userDto.setUsername(user.get().getUsername());
         userDto.setUserId(user.get().getId());
         userDto.setJwtToken(token.get());

         // Set UserType
         String userType  = user.get().getUserData().getClass().toString();
         userType = userType.substring(userType.lastIndexOf('.') + 1);
         userDto.setUserType(userType.toLowerCase() + "s");

         // Set Current Associated Department
         userDto.setCurrentDepartmentId(user.get().getUserData().getCurrentDepartment().getId());

         // Set Current Session
         Optional<List<Session>> session = sessionRepository.findSessionByStudentsIdOrderByStartDateDesc(user.get().getId());
         userDto.setCurrentSessionNumberId(session.get().get(0).getId());


         // Set Current Class
         Optional<Klass> currentClass = klassRepository.findKlassBySessionListId(session.get().get(0).getId());
         userDto.setCurrentClassNumberId((currentClass.get().getId()));



      return userDto;
   }


   public Optional<User> signup(String username, String password){
      if(!userRepository.findByUsername(username).isPresent()){
         Optional<Role> role = roleRepository.findByRoleName("ROLE_USER");
         return Optional.of(userRepository.save(new User(username, passwordEncoder.encode(password), role.get())));

      }
      return Optional.empty();
   }


   // helper methods


}
