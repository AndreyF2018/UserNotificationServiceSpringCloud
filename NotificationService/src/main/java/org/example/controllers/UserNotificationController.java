package org.example.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
public class UserNotificationController {

    @PostMapping
    public String sendEmail(@RequestParam String email, @RequestParam String action) {
        if (action.equalsIgnoreCase("CREATE")){
            return "Sending to " + email + "\nЗдравствуйте! Ваш аккаунт на сайте был успешно создан";
        }
        else if (action.equalsIgnoreCase("DELETE")) {
            return "Sending to " + email + "\nЗдравствуйте! Ваш аккаунт был удалён";
        }
        else {
            return "Sending to " + email + "\nЗдравствуйте! Вам чего?";
        }
    }


}
