package com.softserve.itacademy.controller;

import com.softserve.itacademy.model.Role;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.RoleService;
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
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class UserControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Test
    @DisplayName("Test that GET  /create   create  user in db")
    @Transactional
    public void testThatGetCreateCreateUserInDb() throws Exception {
        User expected  = new User();
        expected.setEmail("valid@cv.edu.ua");
        expected.setFirstName("Firstname");
        expected.setLastName("Lastname");
        expected.setPassword("qwQW12");
        expected.setRole(roleService.readById(2));
        userService.create(expected);

        mockMvc.perform(MockMvcRequestBuilders.post("/users/create"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
            .andExpect(MockMvcResultMatchers.view().name("create-user"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
            .andExpect(MockMvcResultMatchers.model().attribute("user", expected));
    }








}