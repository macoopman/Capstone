package capstone.error_message;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data

public class ErrorMessage {

   private Date timestamp;
   private String message;

   public ErrorMessage() {
   }

   public ErrorMessage(Date timestamp, String message) {
      this.timestamp = timestamp;
      this.message = message;
   }
}
