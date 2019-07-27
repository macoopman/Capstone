package capstone.dto;

import lombok.Data;

@Data
public class PasswordRecoverDto {
   private String username;
   private String email;

   public PasswordRecoverDto() {
   }

   public PasswordRecoverDto(String username, String email) {
      this.username = username;
      this.email = email;
   }
}
