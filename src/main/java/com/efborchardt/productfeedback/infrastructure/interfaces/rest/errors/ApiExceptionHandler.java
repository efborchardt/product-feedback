package com.efborchardt.productfeedback.infrastructure.interfaces.rest.errors;

import com.efborchardt.productfeedback.domain._shared.exception.UnauthorizedActionException;
import com.efborchardt.productfeedback.domain.feedback.exception.FeedbackNotFoundException;
import com.efborchardt.productfeedback.domain.product.exception.ProductNotFoundException;
import com.efborchardt.productfeedback.domain.user.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        ApiErrorResponse apiError = new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), "Internal server error");
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Object> handleIllegalStateException(IllegalStateException ex) {
        ApiErrorResponse apiError = new ApiErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), "Illegal State");
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex) {
        ApiErrorResponse apiError = new ApiErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), "Failed to process the request");
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex) {
        ApiErrorResponse apiError = new ApiErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), "Resource not found");
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex) {
        ApiErrorResponse apiError = new ApiErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), "User not found");
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Object> handleProductNotFoundException(ProductNotFoundException ex) {
        ApiErrorResponse apiError = new ApiErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), "Product not found");
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FeedbackNotFoundException.class)
    public ResponseEntity<Object> handleFeedbackNotFoundException(FeedbackNotFoundException ex) {
        ApiErrorResponse apiError = new ApiErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), "Feedback not found");
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedActionException.class)
    public ResponseEntity<Object> handleUnauthorizedActionException(UnauthorizedActionException ex) {
        ApiErrorResponse apiError = new ApiErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(), "Unauthorized");
        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }
}
