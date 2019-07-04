package capstone.services;

import capstone.domain.User;
import capstone.repositories.UserRepository;
import capstone.security.repository.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

   @Autowired
   public UserService(UserRepository userRepository, AuthenticationManager authenticationManager,
                      RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
      this.userRepository = userRepository;
      this.authenticationManager = authenticationManager;
      this.roleRepository = roleRepository;
      this.passwordEncoder = passwordEncoder;
   }

   public Authentication signin(String username, String password){
      return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
   }




}
