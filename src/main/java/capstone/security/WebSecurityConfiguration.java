package capstone.security;

import capstone.jwt.JwtTokenFilter;
import capstone.services.CapstoneUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {


   @Autowired
   CapstoneUserDetailsService userDetailsService;



   @Override
   protected void configure(HttpSecurity http) throws Exception {

      // Entry Points
      // Only opens signin url - all else require login
      http.authorizeRequests()
//         .antMatchers("/users/signin").permitAll()
//         .antMatchers("/swagger-ui.html").permitAll()
//         .anyRequest().authenticated();
            .anyRequest().permitAll();



      // Disable CSRF (cross site request forgery)
      http.csrf().disable();


      // No session will be created or used
      http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);


      // Start back up implementing the jwt filter
      http.addFilterBefore(new JwtTokenFilter(userDetailsService), UsernamePasswordAuthenticationFilter.class);
   }

   @Bean
   @Override
   public AuthenticationManager authenticationManagerBean() throws Exception {
      return super.authenticationManagerBean();
   }

   @Bean
   public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder(12);
   }




}
