package kakaopay.membership.common;

import org.springframework.http.HttpStatus;

public class CustomResponse {

    boolean success;
    Object response;
    ErrorResponse error;

    public CustomResponse(){};
    public CustomResponse(boolean success, Object response, ErrorResponse error){
        this.success = success;
        this.response = response;
        this.error = error;
    };
    //for error case
    public CustomResponse(ErrorResponse error){
        this.success = false;
        this.response = null;
        this.error = error;
    }
    
    //for success case
    public CustomResponse(Object response){
        this.success = true;
        this.response = response;
        this.error = null;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    public ErrorResponse getError() {
        return error;
    }

    public void setError(ErrorResponse error) {
        this.error = error;
    }

    public CustomResponse(String message, HttpStatus httpStatus){
        ErrorResponse errRep = new ErrorResponse(message, httpStatus);
        this.success = false;
        this.response = null;
        this.error = errRep;


    }



    
}