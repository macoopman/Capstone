package capstone.controller;

import capstone.dto.AddCommentDto;
import capstone.dto.CommentReturnDto;
import capstone.services.CommentService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/comments")
public class CommentController {

   private final CommentService commentService;

   public CommentController(CommentService commentService) {
      this.commentService = commentService;
   }

   @PostMapping("/{comment_id}/addReply")
   public CommentReturnDto addReply(@PathVariable("comment_id") long comment_id, @RequestBody @Valid AddCommentDto dto){
      return commentService.addReply(comment_id, dto);
   }
}
