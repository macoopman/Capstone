package capstone.security;

import capstone.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

   @Autowired
   RoleRepository roleRepository;



   @Override
   protected void configure(HttpSecurity http) throws Exception {

      // Entry Points
      // Only opens signin url - all else require login
      http.authorizeRequests()
         .antMatchers("/users/signin").permitAll()
         .antMatchers("/users/signup").permitAll()
         .anyRequest().authenticated();


      // Disable CSRF (cross site request forgery)
      http.csrf().disable();

      // No session will be created or used
      http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
   }

   @Bean
   @Override
   public AuthenticationManager authenticationManagerBean() throws Exception {
      return super.authenticationManagerBean();
   }

   //todo MUST BE CHANGED ...
   @Bean
   public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder(12);
//      return  NoOpPasswordEncoder.getInstance();
   }

}
