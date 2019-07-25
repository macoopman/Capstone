package capstone.dto;

import lombok.Data;

@Data
public class RegisterDto {

   private String username;
   private String password;
   private int userType;

   public RegisterDto() {
   }

   public RegisterDto(String username, String password, int userType) {
      this.username = username;
      this.password = password;
      this.userType = userType;
   }
}
