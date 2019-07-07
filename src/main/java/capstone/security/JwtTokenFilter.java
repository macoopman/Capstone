package capstone.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

public class JwtTokenFilter extends GenericFilterBean {
   private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenFilter.class);
   private static final String BEARER = "Bearer";

   private CapstoneUserDetailsService userDetailsService;

   public JwtTokenFilter(CapstoneUserDetailsService userDetailsService) {
      this.userDetailsService = userDetailsService;
   }

   /**
    * Determin if there is a JWT as part of the HTTP Request Header.
    * If it is vaild then set the current context with the Authentication (user and roles) found in the token
    *
    * @param servletRequest
    * @param servletResponse
    * @param filterChain
    * @throws IOException
    * @throws ServletException
    */
   @Override
   public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
      LOGGER.info("Process request to check for JSON Web Token");

      // Check for Authorization: Bearer JWT
      String headerValue = ((HttpServletRequest) servletRequest).getHeader("Authorization");

      getBearerToken(headerValue).ifPresent(token -> {
         //Pull the username and roles from the JWT to construct the user details
         userDetailsService.loadUserByJwtTokenAndDatabase(token).ifPresent(userDetails -> {
            //Add the user details (permissions) to the context for just this API invocation
            SecurityContextHolder.getContext().setAuthentication(
               new PreAuthenticatedAuthenticationToken(userDetails, "", userDetails.getAuthorities()));
         });
      });

      // move on to the next filter in the chains
      filterChain.doFilter(servletRequest, servletResponse);

   }

   /**
    * if present, extract the jwt token from the "Bearer <jwt>" header value.
    *
    * @param headerVal
    * @return jwt if present, empty otherwise
    */
   private Optional<String> getBearerToken(String headerVal) {
      if (headerVal != null && headerVal.startsWith(BEARER)) {
         return Optional.of(headerVal.replace(BEARER, "").trim());
      }
      return Optional.empty();
   }
}
