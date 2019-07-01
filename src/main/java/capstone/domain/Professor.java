package capstone.domain;


import lombok.Data;
import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;


@Entity
@Data
public class Professor extends Person {

   // TODO: 6/30/19 determine rating scale
   private double rating;


   public Professor(@NotNull String firstName, @NotNull String lastName, @Email String email, double rating) {
      super(firstName, lastName, email);
      this.rating = rating;
   }
}

