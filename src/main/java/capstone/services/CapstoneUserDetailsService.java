package capstone.services;

import capstone.domain.User;
import capstone.exceptions.UserDetailServiceException;
import capstone.jwt.JwtProvider;
import capstone.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.springframework.security.core.userdetails.User.withUsername;

@Component
public class CapstoneUserDetailsService implements UserDetailsService {

   private final UserRepository userRepository;
   private final JwtProvider jwtProvider;

   public CapstoneUserDetailsService(UserRepository userRepository, JwtProvider jwtProvider) {
      this.userRepository = userRepository;
      this.jwtProvider = jwtProvider;
   }

   @Override
   public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
      User user = userRepository.findByUsername(userName).orElseThrow(() -> {
         return new UserDetailServiceException(String.format("User with name %s does not exist", userName));
      });

      // User details builder
      return withUsername(user.getUsername())
         .password(user.getPassword())
         .authorities(user.getRoles())
         .accountExpired(false)
         .accountLocked(false)
         .credentialsExpired(false)
         .disabled(false)
         .build();
   }



   public Optional<UserDetails> loadUserByToken(String jwtToken) {
      if (jwtProvider.isValidToken(jwtToken)) {
         return Optional.of(loadUserByUsername(jwtProvider.getUserDetailsByToken(jwtToken)));
      } else {
         return Optional.empty();
      }
   }
}
