package kakaopay.membership.common;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
    String message;
    int status;

    public ErrorResponse(){};

    public ErrorResponse(String message, HttpStatus httpStatus){
        this.message = message;
        this.status = httpStatus.value();
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }




}
