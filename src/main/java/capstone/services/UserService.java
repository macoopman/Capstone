package capstone.services;

import capstone.domain.*;
import capstone.dto.RecoverReturnDto;
import capstone.dto.UserDto;
import capstone.email.EmailService;
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
   private static final int RECOVERY_PASSWORD_LENGTH = 10;

   private final UserRepository userRepository;
   private final ProfessorRepository professorRepository;
   private final StudentRepository studentRepository;
   private final AdminRepository adminRepository;
   private final RoleRepository roleRepository;
   private final AuthenticationManager authenticationManager;
   private final PasswordEncoder passwordEncoder;
   private final JwtProvider jwtProvider;
   private final EmailService emailService;
   private final LearningStyleQuestionRepository learningStyleQuestionRepository;
   private final LearningStyleAnswerRepository learningStyleAnswerRepository;


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
      Optional<User> user = findUser(username);
      validateUser(username, password, user);
      authenticateUser(username, password);
      Optional<String> token = addTokenToUser(username, user);
      return buildUserDto(user, token);
   }


         private Optional<User> findUser(String username) {
            return userRepository.findByUsername(username);
         }

         private void validateUser(String username, String password, Optional<User> user) {
            if(null == username || null == password) throw new UserServiceException("Missing Field");
            if(!user.isPresent()) throw new UserServiceException("Invalid Username");
         }


         private void authenticateUser(String username, String password) {
            try{
               authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password ));
            } catch (Exception e){
               throw new UserServiceException("Invalid Password"); }
         }

         private Optional<String> addTokenToUser(String username, Optional<User> user) {
            return Optional.of(jwtProvider.createToken(username, user.get().getRoles()));
         }







   public Optional<User> addStudent(String firstName, String lastName, String password, String email, String gpa, String major) {
      validateEmail(email);
      Optional<Role> role = findRole(ROLE_USER);
      List<LearningStyleAnswers> learningStyleAnswers = new LinkedList<>();
      return Optional.of(createNewStudent(firstName, lastName, password, email, gpa, major, role, learningStyleAnswers));
   }

         private User createNewStudent(String firstName, String lastName, String password, String email,
                                       String gpa, String major, Optional<Role> role, List<LearningStyleAnswers> learningStyleAnswers) {

            User user = userRepository.save(new User(DUMMY_STRING, passwordEncoder.encode(password), role.get(),
               new Student(TEMP_ID, DUMMY_STRING, DUMMY_STRING, DUMMY_EMAIL, DUMMY_DOUBLE, DUMMY_STRING, learningStyleAnswers
               )));

            appendLearningStyleQuestions(learningStyleAnswers);
            Student student = new Student(user.getId(), firstName, lastName, email, Double.parseDouble(gpa), major, learningStyleAnswers);
            user.setUserData(student);
            user.setUsername(buildUniqueUserName(user));
            student.setUsername(user.getUsername());
            cleanUpStudentRepo();
            return user;
         }

         private void appendLearningStyleQuestions(List<LearningStyleAnswers> learningStyleAnswers) {
            Iterable<LearningStyleQuestion> learningStyleQuestions = learningStyleQuestionRepository.findAll();

            for (LearningStyleQuestion question : learningStyleQuestions ){
               LearningStyleAnswers answer = new LearningStyleAnswers(question,0);
               learningStyleAnswers.add(answer);
               learningStyleAnswerRepository.save(answer);
            }
         }

         private Optional<Role> findRole(String roleUser) {
            return roleRepository.findByRoleName(roleUser);
         }

         private void cleanUpStudentRepo() {
            Optional<Student> deleteMe = studentRepository.findById(TEMP_ID);
            studentRepository.delete(deleteMe.get());
         }


   public Optional<User> addProfessor(String firstName, String lastName, String password, String email, String rating) {
      validateEmail(email);
      Optional<Role> role = findRole(ROLE_USER);

      User user = userRepository.save( new User(DUMMY_STRING,passwordEncoder.encode(password), role.get(), new Professor(TEMP_ID, DUMMY_STRING, DUMMY_STRING, DUMMY_EMAIL, DUMMY_DOUBLE)));
      Professor professor = new Professor(user.getId(),firstName, lastName, email, Double.parseDouble(rating));
      user.setUserData(professor);
      user.setUsername(buildUniqueUserName(user));
      professor.setUsername(user.getUsername());
      cleanUpProfessorRepo();
      return Optional.of(user);
   }

         private void cleanUpProfessorRepo() {
            Optional<Professor> deleteMe = professorRepository.findById(TEMP_ID);
            professorRepository.delete(deleteMe.get());
         }


   public Optional<User> addAdmin(String firstName, String lastName, String password, String email) {
      Optional<Role> role = findRole(ROLE_ADMIN);
      User user = userRepository.save(new User(DUMMY_STRING,passwordEncoder.encode(password), role.get(), new Admin(TEMP_ID,DUMMY_STRING, DUMMY_STRING,DUMMY_EMAIL)));
      user.setUserData(new Admin(user.getId(),firstName, lastName, email));
      user.setUsername(buildUniqueUserName(user));
      cleanUpAdminRepo();
      return Optional.of(user);
   }

         private void cleanUpAdminRepo() {
            Optional<Admin> deleteMe = adminRepository.findById(TEMP_ID);
            adminRepository.delete(deleteMe.get());
         }


         private String buildUniqueUserName(User user) {
            StringBuilder username = new StringBuilder();
            username.append(user.getUserData().getFirstName().substring(0,1));
            username.append(user.getUserData().getLastName());
            username.append(user.getId());
            return username.toString();
         }


   public RecoverReturnDto recoverPassword(String username, String email) {

      User recoverUser = getUserForRecovery(username, email);
      String tempPassword = RandomStringUtils.random(RECOVERY_PASSWORD_LENGTH, true, true);
      String hashedPassword = passwordEncoder.encode(tempPassword);
      recoverUser.setPassword(hashedPassword);
      userRepository.save(recoverUser);
      emailService.sendRecoveryMessage(recoverUser, tempPassword);
      return new RecoverReturnDto(recoverUser.getId());
   }

   private User getUserForRecovery(String username, String email) {
      Optional<User> user = findUser(username);
      Optional<Student> student = studentRepository.findStudentByEmail(email);
      Optional<Professor> professor = professorRepository.findProfessorByEmail(email);
      User recoverUser = null;

      if(null != username && !username.equals("")){
         if(!user.isPresent()) throw new UserServiceException("Invalid Username");
         recoverUser = user.get();
      }

      if(null != email && !email.equals("")){
         if (!student.isPresent() && !professor.isPresent()) throw new UserServiceException("Invalid Email Address");

         if(student.isPresent()){
            recoverUser = student.get().getUser();
         }
         else{
            recoverUser = professor.get().getUser();
         }
      }
      return recoverUser;
   }


   public void resetPassword(long userId, String tempPass, String newPass){
      Optional<User> user = userRepository.findById(userId);
      if(!user.isPresent()) throw new UserServiceException("Invalid User Id");
      if(!isTempMatch(tempPass, user.get())) throw new UserServiceException("Invalid Temporary Password");
      user.get().setPassword(passwordEncoder.encode(newPass));
      userRepository.save(user.get());
   }

   public void updateLearningStyle(long userId, long questionId, String updatedValue) {
      Optional<Student> student = studentRepository.findById(userId);
      if(!student.isPresent()) throw new UserServiceException("User Id Not Found");

      List<LearningStyleAnswers> answersList = student.get().getLearningStyleAnswersList();


      boolean isFound = false;
      for(LearningStyleAnswers answer : answersList){

         if(answer.getQId() == questionId){
            answer.setAnswers(Long.parseLong(updatedValue));
            learningStyleAnswerRepository.save(answer);
            isFound = true;
         }
      }
      if(!isFound) throw new UserServiceException("Invalid Learning Style Id");


   }






   // helper methods

   private UserDto buildUserDto(Optional<User> user, @SuppressWarnings("OptionalUsedAsFieldOrParameterType") Optional<String> token) {
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
      else{
         userDto.setCurrentDepartmentId(user.get().getUserData().getCurrentSession().getKlass().getDepartment().getId());
         userDto.setCurrentSessionNumberId(user.get().getUserData().getCurrentSession().getId());
         userDto.setCurrentClassNumberId(user.get().getUserData().getCurrentSession().getKlass().getId());

      }
      return userDto;
   }

   private boolean isTempMatch(String tempPass, User user){
      return BCrypt.checkpw(tempPass, user.getPassword());
   }







   private void validateEmail(String email) {
      if(studentRepository.findStudentByEmail(email).isPresent()) throw new UserServiceException("Email Address Already Exists");
   }



}
