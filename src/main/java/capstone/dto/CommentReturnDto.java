package capstone.dto;

import lombok.Data;

@Data
public class CommentReturnDto {
   private String commentId;

   public CommentReturnDto() {
   }

   public CommentReturnDto(String commentId) {
      this.commentId = commentId;
   }
}
