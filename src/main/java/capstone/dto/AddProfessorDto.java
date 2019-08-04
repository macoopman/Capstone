package capstone.dto;

import lombok.Data;

@Data
public class AddProfessorDto {
   private String firstName;
   private String lastName;
   private String password;
   private String email;
   private String rating;
}
