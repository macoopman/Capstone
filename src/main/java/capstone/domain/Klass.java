package capstone.domain;

import capstone.enums.Subject;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


@Entity
@Data
@RestResource(rel="classes", path="classes")
public class Klass {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private long id;

   @NotNull
   private String subject;

   @Length(max = 3)
   private String classNumber;

//   @NotNull
//   private String subjectAbbreviation;


   @ManyToMany(targetEntity = Session.class)
   List<Session> sessionList;

   // TODO: 7/2/19 Build library of all subject abbrevations  ?

}
