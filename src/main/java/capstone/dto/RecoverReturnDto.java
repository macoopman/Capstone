package capstone.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data

public class RecoverReturnDto {
   private long userId;


   public RecoverReturnDto(long userId) {
      this.userId = userId;
   }
}
