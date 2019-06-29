package capstone.domain;


import lombok.Data;
import javax.persistence.Entity;


@Entity
@Data
public class Professor extends Person {

   private double rating;

   protected Professor() {
   }


   public Professor(String firstName, String lastName, double rating) {
      super(firstName, lastName);
      this.rating = rating;
   }
}
