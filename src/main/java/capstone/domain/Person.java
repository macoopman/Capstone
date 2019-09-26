package capstone.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.*;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Person {

   @Id
   private long id;
   private String username;
   private String firstName;
   private String lastName;
   private String email;
   private Date dateJoined;

   @OneToOne(mappedBy="userData")
   private User user;

   @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   @JoinColumn(name = "user_Id")
   private Session currentSession;

//   @OneToMany(mappedBy = "klass")
//   List<ELOQuestion> questions;

   Person(Long id, String firstName, String lastName, String email) {
      this.id = id;
      this.firstName = firstName;
      this.lastName = lastName;
      this.email = email;
   }

   @PrePersist
   void joinDate(){
      this.dateJoined = new Date();
   }












}
