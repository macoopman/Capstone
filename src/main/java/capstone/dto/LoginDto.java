package capstone.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginDto {

   private String username;

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