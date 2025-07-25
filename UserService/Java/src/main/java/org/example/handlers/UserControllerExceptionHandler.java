package org.example.handlers;

import org.example.controllers.UserController;
import org.example.responses.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RestControllerAdvice(assignableTypes = UserController.class)
public class UserControllerExceptionHandler {

    //Обработчик для исключения, который сам выбрасываю в сервисе
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleIllegalArgument (IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(e.getMessage()));
    }

    //Обработчик для любых других непредвиденных исключений
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(e.getMessage()));
    }
}
