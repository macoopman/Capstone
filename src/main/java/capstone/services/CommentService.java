package capstone.services;

import capstone.domain.Comment;
import capstone.domain.User;
import capstone.dto.AddCommentDto;
import capstone.dto.CommentReturnDto;
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

   public CommentReturnDto addReply(long comment_id, AddCommentDto dto) {
      Comment parentComment = commentRepository.findById(comment_id).orElseThrow(() -> new CommentServiceException("Parent Comment Not Found"));
      User user = userRepository.findById(Long.parseLong(dto.getUserId())).orElseThrow(() -> new CommentServiceException("User Not Found"));
      Comment reply = createComment(dto, parentComment, user);
      reply = commentRepository.save(reply);
      return new CommentReturnDto(String.valueOf(reply.getId()));
   }

   private Comment createComment(AddCommentDto dto, Comment parentComment, User user) {
      Comment reply = new Comment();
      reply.setMessage(dto.getMessage());
      reply.setUser(user);
      reply.setSession(parentComment.getSession());
      reply.setParentComment(parentComment);
      reply.setIsAnonymous(dto.getIsAnonymous());
      reply.setUserName(user.getUsername());
      reply.setParentId((int) parentComment.getId());
      reply.setParentSessionId(parentComment.getParentSessionId());
      reply.setSessionName(parentComment.getSessionName());
      parentComment.incrementReplies();

      commentRepository.save(reply);
      return reply;
   }

}
