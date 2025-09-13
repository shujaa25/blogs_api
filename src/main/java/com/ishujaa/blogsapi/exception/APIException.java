package com.ishujaa.blogsapi.exception;

import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;

public class APIException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public APIException(String message){
        super(message);
    }




}
