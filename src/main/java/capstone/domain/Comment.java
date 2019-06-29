package capstone.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Comment {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private long id;

   private String message;

   // needs much more work
}
