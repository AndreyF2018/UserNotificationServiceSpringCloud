package org.example.listeners;

import org.example.controllers.UserNotificationController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserListener {

    private static final Logger logger = LoggerFactory.getLogger(UserListener.class);

    @KafkaListener(topics = "users.service.topic")
    public void handle(String event) {
        String [] eventParts = event.split(":");
        String action = eventParts[0];
        String email = eventParts[1];
        if (eventParts.length != 2) {
            logger.error("Invalid event: {}", event);
            return;
        }
        if (action.equals("CREATED")){
            logger.info("Sending to: {}", email + "\n Здравствуйте! Ваш аккаунт на сайте был успешно создан" );
            System.out.println("Sending to " + email + "\nЗдравствуйте! Ваш аккаунт был удалён");
        }
        else {
            logger.info("Sending to: {}", email + "\n Здравствуйте! Ваш аккаунт был удалён" );
            System.out.println("Sending to " + email + "\nЗдравствуйте! Ваш аккаунт был удалён");
        }
    }
}
