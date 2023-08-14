package com.softserve.itacademy.controller;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class HomeControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;

    @Test
    @DisplayName("Test that controller get all users")
    public void testThatHomeControllerGetAllUsers() throws Exception {
        List<User> expected = userService.getAll();
    mockMvc.perform(MockMvcRequestBuilders.get("/home"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(MockMvcResultMatchers.view().name("home"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("users"))
            .andExpect(MockMvcResultMatchers.model().attribute("users", expected));
    }

}