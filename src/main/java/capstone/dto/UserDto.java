package capstone.dto;


import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserDto {

   @NotNull
   private String username;

   @NotNull
   private Long userId;

   @NotNull
   private String jwtToken;

   @NotNull
   private String userType;

   private Long currentDepartmentId;

   private Long currentSessionNumberId;

   private Long currentClassNumberId;



   public UserDto() {
   }

   public UserDto(@NotNull String username, @NotNull Long userId, @NotNull String jwtToken, @NotNull String userType) {
      this.username = username;
      this.userId = userId;
      this.jwtToken = jwtToken;
      this.userType = userType;
   }
}
