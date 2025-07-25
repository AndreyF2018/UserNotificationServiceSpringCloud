package org.example.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = UserNotificationController.class)
@AutoConfigureMockMvc
public class UserNotificationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private final String URL = "/api/notifications";

    @Test
    public void sendEmail_CreateAction_ReturnCorrectString () throws Exception {
        mockMvc.perform(post(URL)
                .param("email", "test@example.com")
                .param("action", "create")
                        .characterEncoding("UTF-8")
                        .accept("text/plain;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().string("Sending to test@example.com\nЗдравствуйте! Ваш аккаунт на сайте был успешно создан"));
    }

    @Test
    public void sendEmail_DeleteAction_ReturnCorrectString () throws Exception {
        mockMvc.perform(post(URL)
                        .param("email", "test@example.com")
                        .param("action", "delete")
                        .characterEncoding("UTF-8")
                        .accept("text/plain;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().string("Sending to test@example.com\nЗдравствуйте! Ваш аккаунт был удалён"));
    }

    @Test
    public void sendEmail_UnknownAction_ReturnCorrectString () throws Exception {
        mockMvc.perform(post(URL)
                        .param("email", "test@example.com")
                        .param("action", "unknown")
                        .characterEncoding("UTF-8")
                        .accept("text/plain;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().string("Sending to test@example.com\nЗдравствуйте! Вам чего?"));
    }
}