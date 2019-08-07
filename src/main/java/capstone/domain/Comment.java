package capstone.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
@Data
public class Comment {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;

   private Date dateCreated;

   private String message;

   @OneToOne
   private User user;

   @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   private Session session;

   @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   private Comment parentComment;

   // TODO: 7/9/19 add list of other comments for replies


   @OneToMany(mappedBy = "parentComment")
   private List<Comment> replies;



   @PrePersist
   void prePersist(){
      this.dateCreated = new Date();
   }

}
