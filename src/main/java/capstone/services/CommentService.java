package capstone.services;

import capstone.domain.Comment;
import capstone.domain.Session;
import capstone.domain.User;
import capstone.dto.AddCommentDto;
import capstone.repositories.CommentRepository;
import capstone.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService {

   private CommentRepository commentRepository;
   private UserRepository userRepository;


   public CommentService(CommentRepository commentRepository, UserRepository userRepository) {
      this.commentRepository = commentRepository;
      this.userRepository = userRepository;
   }

   public void addReply(long comment_id, AddCommentDto dto) {
      Optional<Comment> parentComment = commentRepository.findById(comment_id);
      Optional<User> user = userRepository.findById(Long.parseLong(dto.getUserId()));

      Comment reply = new Comment();
      reply.setParentComment(parentComment.get());
      reply.setSession(parentComment.get().getSession());
      reply.setUser(user.get());
      reply.setMessage(dto.getMessage());
      reply.setParentId((int) parentComment.get().getId());
      parentComment.get().incrementRelies();


      commentRepository.save(reply);

   }


   private Session findRootSession(Comment comment){
      if (null != comment.getSession()){
         return comment.getSession();
      }
      else {
         findRootSession(comment.getParentComment());
      }
      return null;
   }
}
