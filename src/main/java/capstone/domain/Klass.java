package capstone.domain;

import capstone.enums.Subject;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;


@Entity
@Data
public class Klass {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private long ids;

   @ManyToMany(targetEntity = Session.class)
   List<Session> sessionList;

   @NotNull
   private String subject;

   // TODO: 6/30/19 Determine rating system
   private double rating;



}
