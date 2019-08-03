package capstone.dto;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Data
public class SessionAddDto {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;
   private LocalDate startDate;
   private LocalDate endDate;

   public SessionAddDto() {
   }

   public SessionAddDto(LocalDate startDate, LocalDate endDate) {
      this.startDate = startDate;
      this.endDate = endDate;
   }
}
