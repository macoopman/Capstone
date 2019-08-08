package capstone.dto;

import lombok.Data;

@Data
public class CommentReturnDto {
//   private Long commentId;
   private String comment;
   private String parentId;
   private String userId;
   private String sessionId;
}
