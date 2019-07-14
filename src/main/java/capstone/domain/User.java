package capstone.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.List;


@Data
@Entity
@Table(name = "security_user")
public class User {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;


   private String username;

   @JsonIgnore
   private String password;

   @JsonIgnore
   private String salt;

   @ManyToMany(fetch = FetchType.EAGER, targetEntity = Role.class)
   private List<Role> roles;

   @OneToOne(targetEntity = Person.class)
   private Person userData;






   public User() { }


   public User(@Size(min = 2) @NotNull String username, @NotNull @Size(min = 8, max = 20, message = "Password must be b/t {min} and {max}") String password, Role role) {
      this.username = username;
      this.password = password;
      this.roles = Arrays.asList(role);
   }


   public List<Role> getRoles() {
      return roles;
   }


}
