package capstone.security;

import capstone.jwt.JwtTokenFilter;
import capstone.services.CapstoneUserDetailsService;
import com.google.common.collect.ImmutableList;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

   @Autowired
   CapstoneUserDetailsService userDetailsService;


   @Override
   protected void configure(HttpSecurity http) throws Exception {

      http.authorizeRequests()
//         .antMatchers("/users/signin").permitAll()
//         .antMatchers("/swagger-ui.html").permitAll()
//         .anyRequest().authenticated();
            .anyRequest().permitAll();



      // Disable CSRF (cross site request forgery)
      http.csrf().disable();

      // Enable Cors
      http.cors().and().authorizeRequests().antMatchers("*").permitAll();

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

   @Bean
   public CorsConfigurationSource corsConfigurationSource() {
      final CorsConfiguration configuration = new CorsConfiguration();
      configuration.setAllowedOrigins(ImmutableList.of("*"));
      configuration.setAllowedMethods(ImmutableList.of("GET", "POST", "PUT", "PATCH", "DELETE"));
      configuration.setAllowCredentials(true);
      configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type"));
      final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      source.registerCorsConfiguration("/**", configuration);
      return source;
   }


}
