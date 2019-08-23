package capstone.services;

import capstone.domain.Comment;
import capstone.domain.Session;
import capstone.domain.User;
import capstone.dto.AddCommentDto;
import capstone.exceptions.CommentServiceException;
import capstone.repositories.CommentRepository;
import capstone.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

   private final CommentRepository commentRepository;
   private final UserRepository userRepository;


   public CommentService(CommentRepository commentRepository, UserRepository userRepository) {
      this.commentRepository = commentRepository;
      this.userRepository = userRepository;
   }

   public void addReply(long comment_id, AddCommentDto dto) {
      Comment parentComment = commentRepository.findById(comment_id).orElseThrow(() -> new CommentServiceException("Parent Comment Not Found"));
      User user = userRepository.findById(Long.parseLong(dto.getUserId())).orElseThrow(() -> new CommentServiceException("User Not Found"));
      Comment reply = createComment(dto, parentComment, user);
      commentRepository.save(reply);
   }

   private Comment createComment(AddCommentDto dto, Comment parentComment, User user) {
      Comment reply = new Comment();
      reply.setParentComment(parentComment);
      reply.setSession(parentComment.getSession());
      reply.setUser(user);
      reply.setMessage(dto.getMessage());
      reply.setParentId((int) parentComment.getId());
      reply.setIsAnonymous(dto.getIsAnonymous());
      parentComment.incrementReplies();
      return reply;
   }

}
