package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
@Tag(name = "Notification Service API")
public class UserNotificationController {

    @Operation (summary = "Send message to email")
    @PostMapping
    public String sendEmail(@Parameter(description = "User email") @RequestParam String email,
                            @Schema(
                                    description = "Action with user",
                                    allowableValues = {"CREATE", "DELETE"},
                                    example = "CREATE"
                            ) @RequestParam String action) {
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
