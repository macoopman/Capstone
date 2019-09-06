package capstone.jwt;

import capstone.domain.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtProvider {
   private final String ROLES_KEY = "roles";
   private final String secretKey;
   private final long validityInMilliseconds;

   public JwtProvider(@Value("${security.jwt.token.secret-key}") String secretKey,
                      @Value("${security.jwt.token.expiration}")long validityInMilliseconds) {

      this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
      this.validityInMilliseconds = validityInMilliseconds;
   }

   public String createToken(String username, List<Role> roles){
      // Add username
      Claims claims = Jwts.claims().setSubject(username);
      // convert roles to SimpleGrantedAuthority objects, Add to SimpleGrantedAuthority object to claim
      claims.put(ROLES_KEY, roles.stream().map(role -> new SimpleGrantedAuthority(role.getAuthority()))
         .filter(Objects::nonNull)
         .collect(Collectors.toList()));

      Date now = new Date();
      return Jwts.builder()
                  .setClaims(claims)
                  .setIssuedAt(now)
                  .setExpiration(new Date(now.getTime() + validityInMilliseconds))
                  .signWith(SignatureAlgorithm.HS256, secretKey)
                  .compact();

   }

   public boolean isValidToken(String token){
      try{
         Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
         return true;
      } catch (JwtException | IllegalArgumentException e){
         return false;
      }
   }

   public String getUserDetailsByToken(String token){
      return Jwts.parser()
               .setSigningKey(secretKey)
               .parseClaimsJws(token)
               .getBody()
               .getSubject();
   }

//   // Get Roles
//   public List<GrantedAuthority> getRoles(String token){
//      List<Map<String, String>> roleClaims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get(ROLES_KEY, List.class);
//      return roleClaims.stream().map(roleClaim ->
//               new SimpleGrantedAuthority(roleClaim.get("authority"))).collect(Collectors.toList());
//   }





}


