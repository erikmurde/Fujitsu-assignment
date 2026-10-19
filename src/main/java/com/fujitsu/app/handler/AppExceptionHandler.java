package com.fujitsu.app.handler;

import com.fujitsu.app.domain.exception.AppException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppExceptionHandler {

    /**
     * Handler for custom exception class.
     *
     * @param ex exception to handle
     * @return error message with response code defined in the exception.
     * If an internal error occurs, return generic error message (no need to send internal error to user)
     */
    @ExceptionHandler(AppException.class)
    public ResponseEntity<String> handleAppException(AppException ex) {
        HttpStatus statusCode = ex.getStatusCode();

        if (statusCode.equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
            return new ResponseEntity<>("Something went wrong", statusCode);
        }
        return new ResponseEntity<>(ex.getMessage(), statusCode);
    }

    /**
     * Handler for Jakarta validation exceptions.
     * These exceptions occur when a user provides invalid input data to controller methods.
     *
     * @param ex exception to handle
     * @return HTTP 400 response
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
