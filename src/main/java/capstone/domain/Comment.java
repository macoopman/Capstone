package capstone.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Data
public class Comment {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;

   private String message;

   // TODO: 7/20/19 figure out how to assign a user
//   private User user;

   // TODO: 7/9/19 determine more attributes
   // TODO: 7/9/19 add list of other comments for replies

}
