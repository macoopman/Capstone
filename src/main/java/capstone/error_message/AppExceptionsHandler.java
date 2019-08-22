package capstone.error_message;


import capstone.dto.ErrorMessageDto;
import capstone.exceptions.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.mail.Session;
import java.util.Date;

@ControllerAdvice
public class AppExceptionsHandler {


   // User Service Exceptions
   @ExceptionHandler(value = {UserServiceException.class})
   public ResponseEntity<Object> handleUserServiceException(Exception ex, WebRequest request){
      ErrorMessageDto message = new ErrorMessageDto(new Date(), ex.getMessage());
      return new ResponseEntity<>(message ,new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE );
   }

   // Session Service Exceptions
   @ExceptionHandler(value = {SessionServiceException.class})
   public ResponseEntity<Object> handleSessionServiceException(Exception ex, WebRequest request){
      ErrorMessageDto message = new ErrorMessageDto(new Date(), ex.getMessage());
      return new ResponseEntity<>(message ,new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE );
   }

   // Department Service Exceptions
   @ExceptionHandler(value = {DepartmentServiceException.class})
   public ResponseEntity<Object> handleDepartmentServiceException(Exception ex, WebRequest request){
      ErrorMessageDto message = new ErrorMessageDto(new Date(), ex.getMessage());
      return new ResponseEntity<>(message ,new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE );
   }

   // Comment Service Exceptions
   @ExceptionHandler(value = {CommentServiceException.class})
   public ResponseEntity<Object> handleCommentServiceException(Exception ex, WebRequest request){
      ErrorMessageDto message = new ErrorMessageDto(new Date(), ex.getMessage());
      return new ResponseEntity<>(message ,new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE );
   }

   // Class Service Exceptions
   @ExceptionHandler(value = {ClassServiceException.class})
   public ResponseEntity<Object> handleClassServiceException(Exception ex, WebRequest request){
      ErrorMessageDto message = new ErrorMessageDto(new Date(), ex.getMessage());
      return new ResponseEntity<>(message ,new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE );
   }



   // User Details Service Exceptions
   @ExceptionHandler(value = {UserDetailServiceException.class})
   public ResponseEntity<Object> handleUserDetailServiceException(Exception ex, WebRequest request){
      ErrorMessageDto message = new ErrorMessageDto(new Date(), ex.getMessage());
      return new ResponseEntity<>(message ,new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE );
   }


   /**
    * Handle all other exceptions that do not have custom handling
    *
    * @param ex
    * @param request
    * @return response message
    */

   @ExceptionHandler(value = {Exception.class})
   public ResponseEntity<Object> handleOtherExceptions(Exception ex, WebRequest request){
      ErrorMessageDto message = new ErrorMessageDto(new Date(), ex.getMessage());
      return new ResponseEntity<>(message ,new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE );
   }
}
