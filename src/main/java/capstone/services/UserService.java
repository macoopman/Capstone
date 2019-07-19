package capstone.services;

import capstone.domain.*;
import capstone.dto.UserDto;
import capstone.error_message.ErrorMessage;
import capstone.error_message.ErrorMessages;
import capstone.exceptions.FailedLoginException;
import capstone.exceptions.MissingFieldsException;
import capstone.exceptions.UserServiceException;
import capstone.exceptions.UsernameExistException;
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

      Optional<String> token = Optional.empty();
      Optional<User> user = userRepository.findByUsername(username);

      // Check if Username and Password were given
      if(null == username || null == password) throw new MissingFieldsException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

      // Check if User Exists
      if(!user.isPresent()) throw new FailedLoginException(ErrorMessages.AUTHENTICATION_FAILED.getErrorMessage());

      // Authenticate User
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

      // create a new token for the user
      token = Optional.of(jwtProvider.createToken(username, user.get().getRoles()));


      // Build Custom Return Data
      UserDto userDto = new UserDto();

      if( user.get().getUserData() != null){
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
      }

      return userDto;
   }


   /**
    * Register new user
    *
    * @param username
    * @param password
    * @return a new user object to be returned by the request
    * @throws Exception thows exception if user already exists
    */
   public Optional<User> register(String username, String password) throws Exception{
      // Check if user exists
      if(userRepository.findByUsername(username).isPresent()) throw new UsernameExistException(ErrorMessages.USERNAME_ALREADY_EXISTS.getErrorMessage());

      Optional<Role> role = roleRepository.findByRoleName("ROLE_USER");
      userRepository.save(new User(username, passwordEncoder.encode(password), role.get()));

      return userRepository.findByUsername(username);

   }




}
