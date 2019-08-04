package capstone.domain;


import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Professor extends Person {

   // TODO: 6/30/19 determine rating scale
   private double rating;



   public Professor(Long id, String firstName, String lastName, String email, double rating) {
      super(id, firstName, lastName, email);
      this.rating = rating;
   }
}

