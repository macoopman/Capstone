package capstone.dto;

import lombok.Data;
import java.util.Date;

@Data
public class ErrorMessageDto {

   private Date timestamp;
   private String message;

   public ErrorMessageDto() {
   }

   public ErrorMessageDto(Date timestamp, String message) {
      this.timestamp = timestamp;
      this.message = message;
   }
}
