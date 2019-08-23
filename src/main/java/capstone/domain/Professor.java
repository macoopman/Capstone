package capstone.domain;


import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Professor extends Person {
   private double rating;

   public Professor(Long id, String firstName, String lastName, String email, double rating) {
      super(id, firstName, lastName, email);
      this.rating = rating;
   }
}

