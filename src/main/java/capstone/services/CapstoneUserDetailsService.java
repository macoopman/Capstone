package capstone.services;

import capstone.domain.User;
import capstone.jwt.JwtProvider;
import capstone.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.springframework.security.core.userdetails.User.withUsername;

@Component
public class CapstoneUserDetailsService implements UserDetailsService {

   @Autowired
   private UserRepository userRepository;

   @Autowired
   JwtProvider jwtProvider;

   @Override
   public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
      User user = userRepository.findByUsername(s).orElseThrow(() ->
         new UsernameNotFoundException(String.format("User with name %s does not exist", s)));

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

   /**
    * Extract the username from the JWT then lookup the user in the database.
    *
    * @param jwtToken
    * @return
    */
   public Optional<UserDetails> loadUserByJwtTokenAndDatabase(String jwtToken) {
      if (jwtProvider.isValidToken(jwtToken)) {
         return Optional.of(loadUserByUsername(jwtProvider.getUsername(jwtToken)));
      } else {
         return Optional.empty();
      }
   }
}
