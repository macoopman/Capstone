package capstone.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "security_user")
public class User {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private long id;

   @Size(min = 2)
   @NotNull
   private String username;

   @JsonIgnore
   @NotNull
//   @Size(min=8, max=20, message = "Password must be b/t {min} and {max}")
   private String password;

   private Date dateJoined;

   @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Person.class)
   private Person user_data = null;

   @ManyToMany(fetch = FetchType.EAGER, targetEntity = Role.class)
   private List<Role> roles;





   public User() {
   }

   public User(@Size(min = 2) @NotNull String username, @NotNull @Size(min = 8, max = 20, message = "Password must be b/t {min} and {max}") String password, Role role) {
      this.username = username;
      this.password = password;
      //this.user_data = user_data;
      this.roles = Arrays.asList(role);
   }

   @PrePersist
   void joinDate(){
      this.dateJoined = new Date();
   }

   public List<Role> getRoles() {
      return roles;
   }


}
