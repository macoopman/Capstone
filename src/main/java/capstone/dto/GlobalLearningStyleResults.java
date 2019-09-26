package capstone.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class GlobalLearningStyleResults {
   private int questionId;
   private String question;
   private double average;

   @JsonIgnore
   private long runningTotal;

   @JsonIgnore
   private int count;

}
