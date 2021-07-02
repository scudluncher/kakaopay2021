package kakaopay.membership.controller;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import kakaopay.membership.common.CustomResponse;
import kakaopay.membership.common.ErrorResponse;
import kakaopay.membership.common.exception.ParameterMissingException;

@RestControllerAdvice
public class MembershipRestControllerAdvice {
    
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<CustomResponse> noSuchElementHandler(){
        ErrorResponse errRep = new ErrorResponse("no element found", HttpStatus.NOT_FOUND);
        CustomResponse rep = new CustomResponse(errRep);
        return new ResponseEntity<CustomResponse>(rep, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ParameterMissingException.class)
    public ResponseEntity<CustomResponse> parameterMissingHandler(){
        ErrorResponse errRep = new ErrorResponse("parameter missing", HttpStatus.BAD_REQUEST);
        CustomResponse rep = new CustomResponse(errRep);
        return new ResponseEntity<CustomResponse>(rep, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomResponse> exceptionHandler(){
        ErrorResponse errRep = new ErrorResponse("internal error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        CustomResponse rep = new CustomResponse(errRep);
        return new ResponseEntity<CustomResponse>(rep, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CustomResponse> illegarArgumentExceptionHandler(){
        ErrorResponse errRep = new ErrorResponse("illegar argument(parameter) is passed", HttpStatus.BAD_REQUEST);
        CustomResponse rep = new CustomResponse(errRep);
        return new ResponseEntity<CustomResponse>(rep, HttpStatus.BAD_REQUEST);
    }

}
