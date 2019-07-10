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
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;

   @NotNull
   private String subject;

   @Length(max = 3)
   private String classNumber;


   @ManyToMany(targetEntity = Session.class)
   List<Session> sessionList;


}
