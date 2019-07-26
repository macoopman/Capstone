package capstone.error_message;


import capstone.dto.ErrorMessageDto;
import capstone.exceptions.FailedLoginException;
import capstone.exceptions.InvaildEmailException;
import capstone.exceptions.MissingFieldsException;
import capstone.exceptions.UsernameExistException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice

public class AppExceptionsHandler {



   // Registration Errors -------------------------------------------------------------------------------------------
   /**
    * Handle Registration if non-unique username is requested
    *
    * @param ex
    * @param request
    * @return response message
    */
   @ExceptionHandler(value = {UsernameExistException.class})
   public ResponseEntity<Object> handleExistingUsernameException(Exception ex, WebRequest request){
      ErrorMessageDto message = new ErrorMessageDto(new Date(), ex.getMessage());
      System.out.println("------------------------------------------");
      return new ResponseEntity<>(message ,new HttpHeaders(), HttpStatus.CONFLICT );
   }


   // Login Errors -------------------------------------------------------------------------------------------
   /**
    * Handle Failed Login
    *
    * @param ex
    * @param request
    * @return response message
    */
   @ExceptionHandler(value = {FailedLoginException.class})
   public ResponseEntity<Object> handleFailedLoginException(Exception ex, WebRequest request){
      ErrorMessageDto message = new ErrorMessageDto(new Date(), ex.getMessage());
      return new ResponseEntity<>(message ,new HttpHeaders(), HttpStatus.BAD_REQUEST);
   }



   // Missing Fields Errors -------------------------------------------------------------------------------------------
   /**
    * Handle Missing Fields in Request
    *
    * @param ex
    * @param request
    * @return response message
    */
   @ExceptionHandler(value = {MissingFieldsException.class})
   public ResponseEntity<Object> handleMissingFieldsException(Exception ex, WebRequest request){
      ErrorMessageDto message = new ErrorMessageDto(new Date(), ex.getMessage());
      return new ResponseEntity<>(message ,new HttpHeaders(), HttpStatus.FORBIDDEN);
   }

   // Invalid Email Errors -------------------------------------------------------------------------------------------
   /**
    * Handle Missing Fields in Request
    *
    * @param ex
    * @param request
    * @return response message
    */
   @ExceptionHandler(value = {InvaildEmailException.class})
   public ResponseEntity<Object> handleInvalidEmailException(Exception ex, WebRequest request){
      ErrorMessageDto message = new ErrorMessageDto(new Date(), ex.getMessage());
      return new ResponseEntity<>(message ,new HttpHeaders(), HttpStatus.NOT_FOUND);
   }



   /**
    * Handle all other exceptions that do not have custom handling
    *
    * @param ex
    * @param request
    * @return response message
    */

//   @ExceptionHandler(value = {Exception.class})
//   public ResponseEntity<Object> handleOtherExceptions(Exception ex, WebRequest request){
//      ErrorMessageDto message = new ErrorMessageDto(new Date(), ex.getMessage());
//      return new ResponseEntity<>(message ,new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR );
//   }
}
