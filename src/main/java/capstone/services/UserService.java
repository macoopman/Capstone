package capstone.services;

import capstone.domain.*;
import capstone.dto.UserDto;
import capstone.error_message.ErrorMessage;
import capstone.error_message.ErrorMessages;
import capstone.exceptions.FailedLoginException;
import capstone.exceptions.MissingFieldsException;
import capstone.exceptions.UserServiceException;
import capstone.exceptions.UsernameExistException;
import capstone.repositories.*;
import capstone.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
   //private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

   private UserRepository userRepository;
   private ProfessorRepository professorRepository;
   private StudentRepository studentRepository;
   private AuthenticationManager authenticationManager;
   private RoleRepository roleRepository;
   private PasswordEncoder passwordEncoder;
   private JwtProvider jwtProvider;
   private SessionRepository sessionRepository;
   private KlassRepository klassRepository;



   @Autowired
   public UserService(UserRepository userRepository, AuthenticationManager authenticationManager,
                      RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider,
                      SessionRepository sessionRepository, KlassRepository klassRepository,
                      ProfessorRepository professorRepository,StudentRepository studentRepository ) {
      this.userRepository = userRepository;
      this.professorRepository = professorRepository;
      this.studentRepository = studentRepository;
      this.authenticationManager = authenticationManager;
      this.roleRepository = roleRepository;
      this.passwordEncoder = passwordEncoder;
      this.jwtProvider = jwtProvider;
      this.sessionRepository = sessionRepository;
      this.klassRepository = klassRepository;
   }

   public UserDto signin(String username, String password){

      Optional<User> user = userRepository.findByUsername(username);

      // Check if Username and Password were given
      if(null == username || null == password) throw new MissingFieldsException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

      // Check if User Exists
      if(!user.isPresent()) throw new FailedLoginException(ErrorMessages.AUTHENTICATION_FAILED.getErrorMessage());

      String salt = user.get().getSalt();
      // Authenticate User
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password + salt ));

      // create a new token for the user
      Optional<String> token = Optional.of(jwtProvider.createToken(username, user.get().getRoles()));



      // Build Custom Return Data

      UserDto userDto = new UserDto();
      userDto.setUsername(user.get().getUsername());
      userDto.setUserId(user.get().getId());
      userDto.setJwtToken(token.get());

      if(null != user.get().getUserData().getCurrentSession()){
         String userType = user.get().getUserData().toString();
         userType = userType.substring(0, userType.indexOf("(")).toLowerCase();
         userDto.setUserType(userType + "s");
         userDto.setCurrentDepartmentId(user.get().getUserData().getCurrentSession().getKlass().getId());
         userDto.setCurrentSessionNumberId(user.get().getUserData().getCurrentSession().getId());
         userDto.setCurrentClassNumberId(user.get().getUserData().getCurrentSession().getKlass().getId());
      } else {
         userDto.setUserType("admin");
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
   public Optional<User> register(String username, String password, int userType) throws Exception{

      if(userRepository.findByUsername(username).isPresent()) throw new UsernameExistException(ErrorMessages.USERNAME_ALREADY_EXISTS.getErrorMessage());

      Optional<Role> role = roleRepository.findByRoleName("ROLE_USER");
      Long tempId = 9999999L;


      // create professor
      if(0 == userType){
         userRepository.save(new User(username, passwordEncoder.encode(password), role.get(), new Professor(tempId, "", "", "", 0)));
         Optional<User> newUser = userRepository.findByUsername(username);
         newUser.get().setPassword(passwordEncoder.encode(password + newUser.get().getSalt()));
         newUser.get().setUserData(new Professor(newUser.get().getId(), "dummy", "dummy", "dummy@gmail.com", 1.0));

         Optional<Professor> deleteMe = professorRepository.findById(tempId);
         professorRepository.delete(deleteMe.get());
      }
      else if(1 == userType){
         userRepository.save(new User(username, passwordEncoder.encode(password), role.get(), new Student(tempId, "", "", "", 0, "")));
         Optional<User> newUser = userRepository.findByUsername(username);
         newUser.get().setPassword(passwordEncoder.encode(password + newUser.get().getSalt()));
         newUser.get().setUserData(new Student(newUser.get().getId(), "dummy", "dummy", "dummy@gmail.com", 1.0,  "dummy_major"));

         Optional<Student> deleteMe = studentRepository.findById(tempId);
         studentRepository.delete(deleteMe.get());
      }
      else{ // create student
         throw new UserServiceException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage());
      }

      return userRepository.findByUsername(username);

   }

   public void recover(String email){



   }




}
