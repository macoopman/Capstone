package capstone.domain;


import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class Professor extends User {

   private double rating;

   protected Professor() {
   }

   public Professor(double rating) {
      this.rating = rating;
   }

   public Professor(String username, String password, String firstName, String lastName, double rating) {
      super(username, password, firstName, lastName);
      this.rating = rating;
   }
}
