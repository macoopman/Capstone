package capstone.domain;

import capstone.enums.Subject;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
@Data
// lock down only ADMIN should make changes
public class Klass {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private String id;

//   List<Session> sessionList;

   private Subject subject;

   private double rating;



}
