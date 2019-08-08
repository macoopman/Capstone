package capstone.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
@Data
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
//@JsonIdentityReference(alwaysAsId=true)
public class Comment {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long Id;

   private Date dateCreated;

   private String message;


   private Integer parentId;

   private Integer numOfRelies;

   @OneToOne
   private User user;

   @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   private Session session;

   @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   private Comment parentComment;

   @OneToMany(mappedBy = "parentComment")
   private List<Comment> replies;

   public Comment() {
      this("", null, null, null);
   }

   public Comment(String message, User user, Session session, Comment parentComment) {
      this.message = message;
      this.user = user;
      this.session = session;
      this.parentComment = parentComment;
      this.numOfRelies = 0;
   }

   @PrePersist
   void prePersist(){
      this.dateCreated = new Date();
   }

   public long getId() {
      return Id;
   }

   public void incrementRelies(){
      this.numOfRelies++;
   }
}
