package capstone.dto;

import lombok.Data;

@Data
public class NewAdminDto {
   private String firstName;
   private String lastName;
   private String password;
   private String email;
}
