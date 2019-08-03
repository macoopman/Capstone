package capstone.dto;

import lombok.Data;

@Data
public class ResultsDTO {
   private String id;
   private String question;
   private String average;
   private String totalResponses;

   public ResultsDTO() {
   }

   public ResultsDTO(String id, String question, String average, String totalResponses) {
      this.id = id;
      this.question = question;
      this.average = average;
      this.totalResponses = totalResponses;
   }
}
