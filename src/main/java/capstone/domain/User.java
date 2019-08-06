package capstone.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.SimpleObjectIdResolver;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.List;


@Data
@Entity
@Table(name = "security_user")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "user_Id")
public class User {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;


   private String username;

   @JsonIgnore
   private String password;


   @ManyToMany(fetch = FetchType.EAGER, targetEntity = Role.class)
   private List<Role> roles;


   @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   @JoinColumn(name = "person_id", unique = true)
   private Person userData;



   public User() { }


   public User(@NotNull String username, @NotNull @Size(min = 8, max = 20, message = "Password must be b/t {min} and {max}") String password, Role role, Person userData) {
      this.username = username;
      this.password = password;
      this.roles = Arrays.asList(role);
      this.userData = userData;
      this.userData.setUser(this);
   }


   public List<Role> getRoles() {
      return roles;
   }



}
