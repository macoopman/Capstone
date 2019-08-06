package capstone.dto;

import lombok.Data;

@Data
public class NewStudentDto {
   private String firstName;
   private String lastName;
   private String password;
   private String email;
   private String gpa;
   private String major;
}
