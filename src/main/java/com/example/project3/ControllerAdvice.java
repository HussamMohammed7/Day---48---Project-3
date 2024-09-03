package com.example.project3;

import com.example.project3.Api.ApiException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@org.springframework.web.bind.annotation.ControllerAdvice

public class ControllerAdvice {




    @ExceptionHandler(value = ApiException.class)


    public ResponseEntity ApiException (ApiException e ){
        String message = e.getMessage();

        return ResponseEntity.status(400 ).body(message);
    }


    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity DataIntegrityViolationException (DataIntegrityViolationException e ){
        String message = e.getMessage();
        return ResponseEntity.status(400 ).body(message);
    }

}
