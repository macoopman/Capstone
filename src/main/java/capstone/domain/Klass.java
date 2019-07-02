package capstone.domain;

import capstone.enums.Subject;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;


@Entity
@Data
@RestResource(rel="classes", path="classes")
public class Klass {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private long id;

   @ManyToMany(targetEntity = Session.class)
   List<Session> sessionList;

   @NotNull
   private String subject;



}
