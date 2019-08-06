package capstone.domain;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Admin extends Person {


   public Admin() { }

   public Admin(Long id, String firstName, String lastName, String email) {
      super(id, firstName, lastName, email);
   }
}
