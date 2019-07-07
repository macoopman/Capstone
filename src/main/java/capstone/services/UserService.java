package capstone.services;

import capstone.domain.Role;
import capstone.domain.User;
import capstone.repositories.UserRepository;
import capstone.repositories.RoleRepository;

import capstone.security.JwtProvider;
import capstone.security.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
   //private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

   private UserRepository userRepository;
   private AuthenticationManager authenticationManager;
   private RoleRepository roleRepository;
   private PasswordEncoder passwordEncoder;
   private JwtProvider jwtProvider;

   @Autowired
   public UserService(UserRepository userRepository, AuthenticationManager authenticationManager,
                      RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
      this.userRepository = userRepository;
      this.authenticationManager = authenticationManager;
      this.roleRepository = roleRepository;
      this.passwordEncoder = passwordEncoder;
      this.jwtProvider = jwtProvider;
   }

   public Optional<String> signin(String username, String password){
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
            // good place for a logger
         }
      }
      return token;
   }


   public Optional<User> signup(String username, String password){
      if(!userRepository.findByUsername(username).isPresent()){
         Optional<Role> role = roleRepository.findByRoleName("ROLE_USER");
         return Optional.of(userRepository.save(new User(username, passwordEncoder.encode(password), role.get())));

      }
      return Optional.empty();
   }




}
