package capstone.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Comment {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;

   private String message;

   // TODO: 7/9/19 determine more attributes
   // TODO: 7/9/19 add list of other comments for replies
}
