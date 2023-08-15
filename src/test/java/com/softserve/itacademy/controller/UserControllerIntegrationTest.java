package com.softserve.itacademy.controller;

import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.RoleService;
import com.softserve.itacademy.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


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
    @DisplayName("Test that POST /create   create  user in db")
    @Transactional
    public void testThatGetCreateCreateUserInDb() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/create")
                        .param("firstName","Firstname" )
                        .param("lastName", "Lastname")
                        .param("email", "valid@cv.edu.ua")
                        .param("password", "qwQW12")
                )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/todos/all/users/1" ));

        User expected  = userService.readById(1);

        assertEquals("valid@cv.edu.ua", expected.getEmail());
        }

    @Test
    @DisplayName("Test that GET  /all  gets  all users from DB")

    public void testThatGetAllGetsUsersFRomDB() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("users-list"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("users"));
        List<User> expected = userService.getAll();
        assertNotNull(expected);
    }


    }


