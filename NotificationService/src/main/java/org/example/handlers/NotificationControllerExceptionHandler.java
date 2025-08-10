package org.example.handlers;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class NotificationControllerExceptionHandler {

    @ExceptionHandler(CallNotPermittedException.class)
    public ResponseEntity<String> handleCircuitBreakerException() {
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Сервис NotificationService временно не доступен");
    }
}
