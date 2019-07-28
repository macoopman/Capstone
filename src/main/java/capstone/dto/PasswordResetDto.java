package capstone.dto;

import lombok.Data;

@Data
public class PasswordResetDto {

   private long userId;
   private String tempPassword;
   private String newPassword;
}
