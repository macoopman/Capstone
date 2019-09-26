package capstone.controller;

import capstone.domain.Comment;
import capstone.dto.AddCommentDto;
import capstone.dto.CommentReturnDto;
import capstone.exceptions.CommentServiceException;
import capstone.repositories.CommentRepository;
import capstone.services.CommentService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/comments")
public class CommentController {

   private final CommentService commentService;
   private final CommentRepository commentRepository;

   public CommentController(CommentService commentService, CommentRepository commentRepository) {
      this.commentService = commentService;
      this.commentRepository = commentRepository;
   }

   @PostMapping("/{comment_id}/addReply")
   public CommentReturnDto addReply(@PathVariable("comment_id") long comment_id, @RequestBody @Valid AddCommentDto dto){
      return commentService.addReply(comment_id, dto);
   }

   @DeleteMapping("/{comment_id}/delete")
   public void deleteReply(@PathVariable("comment_id") long comment_id){
      Comment comment = commentRepository.findById(comment_id).orElseThrow(() -> new CommentServiceException("Comment Id Not Found"));
      commentService.deleteComment(comment);
   }
}
