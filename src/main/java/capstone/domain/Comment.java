package capstone.domain;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Comment {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long Id;
   private Date dateCreated;
   private String userName;
   private String message;
   private Integer parentId;
   private Integer numOfRelies;
   private String isAnonymous;
   private String sessionName;

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

   private Comment(String message, User user, Session session, Comment parentComment) {
      this.message = message;
      this.user = user;
      this.session = session;
      this.parentComment = parentComment;
      this.numOfRelies = 0;
      this.isAnonymous = "0";
   }

   @PrePersist
   void prePersist(){
      this.dateCreated = new Date();
   }

   public long getId() {
      return Id;
   }

   public void incrementReplies(){
      this.numOfRelies++;
   }
}
