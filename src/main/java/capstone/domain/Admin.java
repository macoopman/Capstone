package capstone.domain;


import capstone.dto.AddCommentDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Admin extends Person {


   public Admin(){
   }

   public Admin(Long id, String firstName, String lastName, String email) {
      super(id, firstName, lastName, email);
   }
}
