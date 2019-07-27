package capstone.error_message;

public enum ErrorMessages {

   MISSING_REQUIRED_FIELD("Missing required field. Please Check documentation fro required fields"),
   RECORED_ALREADY_EXISTS("Record already exists"),
   USERNAME_ALREADY_EXISTS("Username already exists"),
   INTERNAL_SERVER_ERROR("Internal server error"),
   NO_RECORED_FOUND("Record not found"),
   AUTHENTICATION_FAILED("Authentication failed"),
   COULD_NOT_UPDATE_RECORD("Could not update record"),
   COULD_NOT_DELETE_RECORD("Could not delete record"),
   EMAIL_ADDRESS_NOT_VERIFIED("Email address could not be verified");




   private String errorMessage;

   ErrorMessages(String errorMessage){
      this.errorMessage = errorMessage;
   }

   public String getErrorMessage() {
      return errorMessage;
   }

   public void setErrorMessage(String errorMessage) {
      this.errorMessage = errorMessage;
   }
}
