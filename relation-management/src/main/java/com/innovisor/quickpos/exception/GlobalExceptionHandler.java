package com.innovisor.quickpos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(NotFoundException.class)
//    public ResponseEntity<?> handleCustomerNotFoundException(NotFoundException ex){
//        return ResponseEntity
//                .status(HttpStatus.NOT_FOUND)
//                .body(ex.getMessage());
//    }
//    @ExceptionHandler(BadRequestException.class)
//    public ResponseEntity<?> handleCustomerNotFoundException(BadRequestException ex){
//        return ResponseEntity
//                .status(HttpStatus.BAD_REQUEST)
//                .body(ex.getMessage());
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentException(MethodArgumentNotValidException ex) {
        StringBuilder stringBuilder = new StringBuilder();

        ex.getBindingResult()
                .getAllErrors()
                .forEach(e -> {
                    String fieldName;
                    try {
                        fieldName = ((FieldError) e).getField();

                    } catch (ClassCastException _e) {
                        fieldName = e.getObjectName();
                    }
                    String message = e.getDefaultMessage();
                    stringBuilder.append("%s: %s\n".formatted(fieldName, message));
                });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(stringBuilder.substring(0,stringBuilder.length() - 1));
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<?> handleMethodValidationException(HandlerMethodValidationException ex) {
        Map<String, String> errors = new HashMap<>();

        for (var parameterError : ex.getAllValidationResults()) {
            String parameterName = parameterError.getMethodParameter().getParameterName();

            for (var error : parameterError.getResolvableErrors()) {
                if (error instanceof FieldError fieldError) {
                    String fieldName = fieldError.getField();
                    String errorMessage = fieldError.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                } else {
                    errors.put(parameterName, error.getDefaultMessage());
                }
            }
        }

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Bad Request");
        problemDetail.setProperty("Errors", errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

}
