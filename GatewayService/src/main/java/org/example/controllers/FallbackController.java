package org.example.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {
    @RequestMapping("/fallback/users")
    public Mono<String> usersFallback () {
        return Mono.just("Сервис UserService временно недоступен.");
    }

    @RequestMapping("/fallback/notifications")
    public String notificationsFallback () {
        return "Сервис NotificationService временно недоступен.";
    }


}
