package com.travix.medusa.busyflights.exception.handler;

import com.travix.medusa.busyflights.exception.exceptions.BusyFlightsNotFoundException;
import com.travix.medusa.busyflights.exception.utils.MessageUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BusyFlightsNotFoundException.class)
    public ResponseEntity<String> handleBusyFlightsNotFoundException(BusyFlightsNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MessageUtils.getMessage(exception.getLanguage(), exception.getMessageCode()));
    }

}
