package com.ishujaa.blogsapi.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;

@Getter
@Setter
public class APIError {

    private LocalDateTime timestamp;
    private String error;
    private HttpStatus httpStatus;

    public APIError(){
        this.timestamp = LocalDateTime.now();
    }

    public APIError(String error, HttpStatus httpStatus){
        this();
        this.error = error;
        this.httpStatus = httpStatus;
    }

}
