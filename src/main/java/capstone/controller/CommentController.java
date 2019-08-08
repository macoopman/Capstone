package capstone.controller;

import capstone.dto.AddCommentDto;
import capstone.services.CommentService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/comments")
public class CommentController {

   private CommentService commentService;

   public CommentController(CommentService commentService) {
      this.commentService = commentService;
   }

   @PostMapping("/{comment_id}/addReply")
   public void addReply(@PathVariable("comment_id") long comment_id, @RequestBody @Valid AddCommentDto dto){
      commentService.addReply(comment_id, dto);
   }
}
