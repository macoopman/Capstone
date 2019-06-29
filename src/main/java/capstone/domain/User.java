package capstone.domain;

import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@Entity

public class User {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private long id;

   @Size(min = 2)
   @NotNull
   private String username;

   @NotNull
   @Size(min=8, max=20, message = "Password must be b/t {min} and {max}")
   private String password;

   @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
   @JoinColumn(name = "person_id", referencedColumnName = "id")
   private Student person = null;




   public User() {
   }

   public User(String username, String password, String firstName, String lastName) {

      this.username = username;
      this.password = password;
   }


}
