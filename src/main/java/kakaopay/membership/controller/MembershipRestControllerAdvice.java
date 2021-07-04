package kakaopay.membership.controller;

import java.util.Set;

import javax.persistence.EntityExistsException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import kakaopay.membership.common.CustomResponse;
import kakaopay.membership.common.exception.AlreadyRegisteredMembershipException;
import kakaopay.membership.common.exception.NotRegisteredMembershipException;
import kakaopay.membership.common.exception.NotRegisteredUserException;
import kakaopay.membership.common.exception.WrongMembershipIdException;

@RestControllerAdvice
public class MembershipRestControllerAdvice {
    

    @ExceptionHandler(NotRegisteredMembershipException.class)
    public ResponseEntity<CustomResponse> notRegisteredMembershipExceptionHandler(){
        CustomResponse rep = new CustomResponse("no membership registered with X-USER-ID" , HttpStatus.NOT_FOUND);
        return new ResponseEntity<CustomResponse>(rep, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(NotRegisteredUserException.class)
    public ResponseEntity<CustomResponse> notRegisteredUserExceptionHandler(){
        CustomResponse rep = new CustomResponse("no user registed with X-USER-ID" , HttpStatus.NOT_FOUND);
        return new ResponseEntity<CustomResponse>(rep, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyRegisteredMembershipException.class)
    public ResponseEntity<CustomResponse> alreadyRegisteredMembershipExceptionHandler(){
        CustomResponse rep = new CustomResponse("already registered membership", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<CustomResponse>(rep, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(WrongMembershipIdException.class)
    public ResponseEntity<CustomResponse> wrongMembershipIdExceptionHandler(){
        CustomResponse rep = new CustomResponse("check membership id", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<CustomResponse>(rep, HttpStatus.BAD_REQUEST);
    }


    // when entity is already exist while trying to post object
    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<CustomResponse> entityExistsExceptionHandler(){
        CustomResponse rep = new CustomResponse("it is already in database", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<CustomResponse>(rep, HttpStatus.BAD_REQUEST);
    }
 
    //when pathvariable is missing or not supported method is called  --> done
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<CustomResponse> httpRequestMethodNotSupportedExceptionHandler(){
        CustomResponse rep = new CustomResponse("check its method or pathvariable parameter", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<CustomResponse>(rep, HttpStatus.BAD_REQUEST);
    }


    //request header is omitted --> done
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<CustomResponse> missingRequestHeaderExceptionHandler(HttpServletRequest req){
        if(req.getHeader("content-type")==null){
            CustomResponse rep = new CustomResponse("content-type header is required", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<CustomResponse>(rep, HttpStatus.BAD_REQUEST);    
        }
        if(req.getHeader("X-USER-ID")==null){
            CustomResponse rep = new CustomResponse("X-USER-ID header is required", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<CustomResponse>(rep, HttpStatus.BAD_REQUEST);    
        }
        CustomResponse rep = new CustomResponse("Missing header", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<CustomResponse>(rep, HttpStatus.BAD_REQUEST);    
    }

    //negative point, negative amount --> d
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomResponse> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e){
        StringBuilder message = new StringBuilder();
        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
                                                      message.append(fieldError.getDefaultMessage());});
        CustomResponse rep = new CustomResponse(message.toString(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<CustomResponse>(rep, HttpStatus.BAD_REQUEST);
    }

    //not proper request header --> done
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CustomResponse> constraintViolationExceptionHandler(ConstraintViolationException e){
        StringBuilder message = new StringBuilder();
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
        message.append(violation.getMessage().concat(";"));}
        CustomResponse rep = new CustomResponse(message.toString(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<CustomResponse>(rep, HttpStatus.BAD_REQUEST);
    }


    //common exception catch
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomResponse> exceptionHandler(){
        CustomResponse rep = new CustomResponse("internal error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<CustomResponse>(rep, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    


}
