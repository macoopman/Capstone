package capstone.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class LearningStyleResultsDto {
   private long questionId;
   private String question;
   private double average;

   @JsonIgnore
   private int total;
   @JsonIgnore
   private int responces;
}
