package org.example;

import org.example.controllers.UserController;
import org.example.models.User;
import org.example.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private UserService userService;


    private final String testUserJson = """
            {
                "name": "Test",
                "email": "test@example.com",
                "age": 35,
                "created_at": "2025-07-17T12:00:00"
            }
            """;
    private final String invalidTestUserJson = """
            {
                "name": "InvalidUser",
                "email": "invalidEmail",
                "age": 150,
                "created_at": "2025-07-17T12:00:00"
            }
            
            """;

    private static User createTestUser(int id) {
        User testUser = new User("Test", "test@example.com", 35);
        testUser.setId(id);
        return testUser;

    }

    @Test
    public void findById_ValidId_ReturnUser() throws Exception {
        User testUser = createTestUser(1);
        Mockito.when(userService.findById(1)).thenReturn(Optional.of(testUser));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value(testUser.getEmail()));

    }

    @Test
    public void findById_InvalidId_ReturnNotFound() throws Exception {
        Mockito.when(userService.findById(-1)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/-1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findByEmail_validEmail_ReturnUser() throws Exception {
        User testUser = createTestUser(1);
        Mockito.when(userService.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/email/test@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value(testUser.getEmail()));

    }

    @Test
    public void findByEmail_invalidEmail_ReturnNotFound() throws Exception {
        Mockito.when(userService.findByEmail("unknown@example.com")).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/email/unknown@example.com"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findAll_NotEmptyUsersList_ReturnUsersList() throws Exception {
        User testUser1 = createTestUser(1);
        User testUser2 = new User("test2", "test2@example.com", 27);
        Mockito.when(userService.findAll()).thenReturn(List.of(testUser1, testUser2));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].email").value(testUser1.getEmail()))
                .andExpect(jsonPath("$[1].email").value(testUser2.getEmail()));
    }

    @Test
    public void save_validUser_ReturnCreated() throws Exception {
        User savedUser = createTestUser(1);
        Mockito.when(userService.save(Mockito.any(User.class))).thenReturn(savedUser);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users").contentType(MediaType.APPLICATION_JSON)
                .content(testUserJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value(savedUser.getEmail()));

    }

    @Test
    public void save_InvalidUser_ReturnInternalServerError() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidTestUserJson))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    public void update_ValidUser_ReturnOk() throws Exception {
        User updatedUser = createTestUser(1);
        Mockito.when(userService.update(Mockito.any(User.class))).thenReturn(updatedUser);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testUserJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(updatedUser.getEmail()));
    }

    @Test
    public void update_InvalidUser_ReturnInternalServerError() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidTestUserJson))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void delete_ValidUser_ReturnNoContent() throws Exception {
        User toDeleteUser = createTestUser(1);
        Mockito.doNothing().when(userService).delete(toDeleteUser);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void delete_InvalidUser_ReturnNoContent() throws Exception {
        Mockito.when(userService.findById(1)).thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/1"))
                .andExpect(status().isNoContent());
    }


}
