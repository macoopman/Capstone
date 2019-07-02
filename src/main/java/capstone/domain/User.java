package capstone.domain;

import capstone.security.domain.Role;
import lombok.Data;
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

   private Date dateJoined;

   @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Person.class)
   private Person user_data = null;

   @ManyToMany(fetch = FetchType.EAGER, targetEntity = Role.class)
   private List<Role> roles;





   public User() {
   }

   public User(String username, String password, String firstName, String lastName) {

      this.username = username;
      this.password = password;
   }

   @PrePersist
   void joinDate(){
      this.dateJoined = new Date();
   }


}
