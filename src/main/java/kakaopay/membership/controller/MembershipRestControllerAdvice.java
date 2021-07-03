package kakaopay.membership.controller;

import java.util.NoSuchElementException;
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
import kakaopay.membership.common.exception.ParameterMissingException;

@RestControllerAdvice
public class MembershipRestControllerAdvice {
    
    //when there is no jpa entity with parameter
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<CustomResponse> noSuchElementHandler(){
        CustomResponse rep = new CustomResponse("no element found" , HttpStatus.NOT_FOUND);
        return new ResponseEntity<CustomResponse>(rep, HttpStatus.NOT_FOUND);
    }

    //when parameter is missing
    @ExceptionHandler(ParameterMissingException.class)
    public ResponseEntity<CustomResponse> parameterMissingHandler(){
        CustomResponse rep = new CustomResponse("parameter missing", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<CustomResponse>(rep, HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CustomResponse> illegarArgumentExceptionHandler(){
        CustomResponse rep = new CustomResponse("illegar argument(parameter) is passed", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<CustomResponse>(rep, HttpStatus.BAD_REQUEST);
    }


    // when entity is already exist while trying to post object
    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<CustomResponse> entityExistsExceptionHandler(){
        CustomResponse rep = new CustomResponse("it is already in database", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<CustomResponse>(rep, HttpStatus.BAD_REQUEST);
    }
 
    //when pathvariable is missing or not supported method is called
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<CustomResponse> httpRequestMethodNotSupportedExceptionHandler(){
        CustomResponse rep = new CustomResponse("not supported request, check its method or pathvariable parameter", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<CustomResponse>(rep, HttpStatus.BAD_REQUEST);
    }


    //request header is omitted
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

    //negative point, negative amount
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomResponse> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e){
        StringBuilder message = new StringBuilder();
        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
                                                      message.append(fieldError.getDefaultMessage());});
        CustomResponse rep = new CustomResponse(message.toString(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<CustomResponse>(rep, HttpStatus.BAD_REQUEST);
    }

    //not proper request header
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
