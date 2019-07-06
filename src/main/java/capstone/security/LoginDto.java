package capstone.security;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginDto {
   @NotNull
   private String username;

   @NotNull
   private String password;

   /**
    * Default constructor
    */
   protected LoginDto() {
   }

   /**
    * Full constructor
    * @param username
    * @param password
    */
   public LoginDto(String username, String password) {
      this.username = username;
      this.password = password;
   }
}