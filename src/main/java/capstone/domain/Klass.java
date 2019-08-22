package capstone.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;


@Entity
@Data
@RestResource(rel="classes", path="classes")
public class Klass {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;

   @NotNull
   private String subject;

   @Length(max = 4)
   private String classNumber;

   @Lob
   private String description;


   @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   @JoinColumn(name="department_id")
   private Department department;


   @OneToMany(mappedBy = "klass")
   private List<Session> sessions;



   @OneToMany(mappedBy = "klass")
   private List<ELOQuestion> questions;

   public void appendSession(Session session){

      sessions.add(session);
   }

}
