package capstone.dto;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class NewQuestionDto {

   @Size(max = 255)
   private String question;

   private String weekNumber;

}
