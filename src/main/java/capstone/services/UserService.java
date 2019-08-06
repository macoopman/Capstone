package capstone.services;

import capstone.domain.*;
import capstone.dto.RecoverReturnDto;
import capstone.dto.UserDto;
import capstone.email.EmailService;
import capstone.error_message.ErrorMessages;
import capstone.exceptions.*;
import capstone.repositories.*;
import capstone.jwt.JwtProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class UserService {

   private static final String DUMMY_STRING = "dummy";
   private static final String DUMMY_EMAIL = "dummy@dum.com";
   private static final int DUMMY_DOUBLE = 2;
   private static final Long TEMP_ID = 999999L;
   private static final String ROLE_USER = "ROLE_USER";
   private static final String ROLE_ADMIN = "ROLE_ADMIN";
   public static final int RECOVERY_PASSWORD_LENGTH = 10;

   private UserRepository userRepository;
   private ProfessorRepository professorRepository;
   private StudentRepository studentRepository;
   private AuthenticationManager authenticationManager;
   private RoleRepository roleRepository;
   private PasswordEncoder passwordEncoder;
   private JwtProvider jwtProvider;
   private EmailService emailService;
   private AdminRepository adminRepository;
   private LearningStyleQuestionRepository learningStyleQuestionRepository;
   private LearningStyleAnswerRepository learningStyleAnswerRepository;




   @Autowired
   public UserService(UserRepository userRepository, AuthenticationManager authenticationManager,
                      RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider,
                      ProfessorRepository professorRepository,StudentRepository studentRepository,
                      EmailService emailService, AdminRepository adminRepository,
                      LearningStyleQuestionRepository learningStyleQuestionRepository,
                      LearningStyleAnswerRepository learningStyleAnswerRepository) {
      this.userRepository = userRepository;
      this.professorRepository = professorRepository;
      this.studentRepository = studentRepository;
      this.authenticationManager = authenticationManager;
      this.roleRepository = roleRepository;
      this.passwordEncoder = passwordEncoder;
      this.jwtProvider = jwtProvider;
      this.emailService = emailService;
      this.adminRepository = adminRepository;
      this.learningStyleQuestionRepository = learningStyleQuestionRepository;
      this.learningStyleAnswerRepository = learningStyleAnswerRepository;

   }

   public UserDto signin(String username, String password){
      Optional<User> user = userRepository.findByUsername(username);
      if(null == username || null == password) throw new MissingFieldsException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
      if(!user.isPresent()) throw new FailedLoginException(ErrorMessages.AUTHENTICATION_FAILED.getErrorMessage());
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password ));
      Optional<String> token = Optional.of(jwtProvider.createToken(username, user.get().getRoles()));
      return buildUserDto(user, token);
   }


   public Optional<User> addStudent(String firstName, String lastName, String password, String email, String gpa, String major) {
      validateEmail(email);
      Optional<Role> role = roleRepository.findByRoleName(ROLE_USER);
      List<LearningStyleAnswers> learningStyleAnswers = new LinkedList<>();
      User user = userRepository.save(new User(DUMMY_STRING, passwordEncoder.encode(password), role.get(),
         new Student(TEMP_ID, DUMMY_STRING, DUMMY_STRING, DUMMY_EMAIL, DUMMY_DOUBLE, DUMMY_STRING, learningStyleAnswers
         )));

      Iterable<LearningStyleQuestion> learningStyleQuestions = learningStyleQuestionRepository.findAll();

      for (LearningStyleQuestion question : learningStyleQuestions ){
         LearningStyleAnswers answer = new LearningStyleAnswers(question,0);
         learningStyleAnswers.add(answer);
         learningStyleAnswerRepository.save(answer);
      }

      Student student = new Student(user.getId(), firstName, lastName, email, Double.parseDouble(gpa), major, learningStyleAnswers);

      user.setUserData(student);
      user.setUsername(buildUniqueUserName(user));

      Optional<Student> deleteMe = studentRepository.findById(TEMP_ID);
      studentRepository.delete(deleteMe.get());

      return userRepository.findByUsername(user.getUsername());
   }


   public Optional<User> addProfessor(String firstName, String lastName, String password, String email, String rating) {
      validateEmail(email);
      Optional<Role> role = roleRepository.findByRoleName(ROLE_USER);

      User user = userRepository.save( new User(DUMMY_STRING,passwordEncoder.encode(password), role.get(), new Professor(TEMP_ID, DUMMY_STRING, DUMMY_STRING, DUMMY_EMAIL, DUMMY_DOUBLE)));

      user.setUserData( new Professor(user.getId(),firstName, lastName, email, Double.parseDouble(rating)));
      user.setUsername(buildUniqueUserName(user));

      Optional<Professor> deleteMe = professorRepository.findById(TEMP_ID);
      professorRepository.delete(deleteMe.get());

      return userRepository.findByUsername(user.getUsername());
   }




   public Optional<User> addAdmin(String firstName, String lastName, String password, String email) {
      Optional<Role> role = roleRepository.findByRoleName(ROLE_ADMIN);
      User user = userRepository.save(new User(DUMMY_STRING,passwordEncoder.encode(password), role.get(), new Admin(TEMP_ID,DUMMY_STRING, DUMMY_STRING,DUMMY_EMAIL)));
      user.setUserData(new Admin(user.getId(),firstName, lastName, email));
      user.setUsername(buildUniqueUserName(user));


      Optional<Admin> deleteMe = adminRepository.findById(TEMP_ID);
      adminRepository.delete(deleteMe.get());

      return userRepository.findByUsername(user.getUsername());
   }




   private String buildUniqueUserName(User user) {
      StringBuilder username = new StringBuilder();
      username.append(user.getUserData().getFirstName().substring(0,1));
      username.append(user.getUserData().getLastName());
      username.append(user.getId());
      return username.toString();
   }

   /**
    * Recover password and update database
    * only username OR email are required
    *
    * @param username username of user
    * @param email email of users
    * @throws Exception
    */
   public RecoverReturnDto recover(String username, String email) throws Exception{

      User recoverUser = getRecoverUser(username, email);
      String tempPassword = RandomStringUtils.random(RECOVERY_PASSWORD_LENGTH, true, true);
      String hashedPassword = passwordEncoder.encode(tempPassword);
      recoverUser.setPassword(hashedPassword);
      userRepository.save(recoverUser);
      emailService.sendRecoveryMessage(recoverUser, tempPassword);

      return new RecoverReturnDto(recoverUser.getId());
   }


   /**
    * Provided a valid temp password and a new password reset password in database
    *
    * @param tempPass
    * @param newPass
    */
   public void reset(long userId, String tempPass, String newPass){
      Optional<User> user = userRepository.findById(userId);
      if(!user.isPresent()) throw new InvalidIdException(ErrorMessages.NO_RECORED_FOUND.getErrorMessage());
      if(!isTempMatch(tempPass, user.get())) throw new InvalidTempPassException(ErrorMessages.INVALID_TEMP_PASS.getErrorMessage());
      user.get().setPassword(passwordEncoder.encode(newPass));
      userRepository.save(user.get());
   }

   public void updateLearningStyle(long userId, long learningStyleId, String updatedValue) {
      Optional<Student> student = studentRepository.findById(userId);

      List<LearningStyleAnswers> answersList = student.get().getLearningStyleAnswersList();
      boolean isFound = false;
      for(LearningStyleAnswers answer : answersList){

         if(answer.getId() == learningStyleId){
            answer.setAnswers(Long.parseLong(updatedValue));
            learningStyleAnswerRepository.save(answer);
            isFound = true;
         }
      }
      if(!isFound) throw new UserServiceException(ErrorMessages.NO_RECORED_FOUND.getErrorMessage());


   }






   // helper methods

   private UserDto buildUserDto(Optional<User> user, Optional<String> token) {
      UserDto userDto = new UserDto();
      userDto.setUsername(user.get().getUsername());
      userDto.setUserId(user.get().getId());
      userDto.setJwtToken(token.get());
      String userType = user.get().getUserData().toString();
      userType = userType.substring(0, userType.indexOf("(")).toLowerCase();
      userDto.setUserType(userType + "s");


      if(null == user.get().getUserData().getCurrentSession()) {
         userDto.setCurrentDepartmentId(null);
         userDto.setCurrentSessionNumberId(null);
         userDto.setCurrentClassNumberId(null);
      }
      return userDto;
   }

   private boolean isTempMatch(String tempPass, User user){
      return BCrypt.checkpw(tempPass, user.getPassword());
   }

   private User getRecoverUser(String username, String email) {
      Optional<User> user = userRepository.findByUsername(username);
      Optional<Student> student = studentRepository.findStudentByEmail(email);
      Optional<Professor> professor = professorRepository.findProfessorByEmail(email);
      User recoverUser = null;

      if(null != username){
         if(!user.isPresent()) throw new InvalidRecoverException(ErrorMessages.RECOVERY_FAILED.getErrorMessage());
         recoverUser = user.get();
      }

      if(null != email){
         if (!student.isPresent() && !professor.isPresent()) throw new InvalidRecoverException(ErrorMessages.RECOVERY_FAILED.getErrorMessage());

         if(student.isPresent()){
            recoverUser = student.get().getUser();
         }
         else{
            recoverUser = professor.get().getUser();
         }
      }
      return recoverUser;
   }





   private void validateEmail(String email) {
      if(studentRepository.findStudentByEmail(email).isPresent()) throw new UserServiceException(ErrorMessages.RECORED_ALREADY_EXISTS.getErrorMessage());
   }



}
