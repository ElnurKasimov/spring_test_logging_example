package com.softserve.itacademy.controller;

import com.softserve.itacademy.model.Role;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.RoleService;
import com.softserve.itacademy.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;


@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private RoleService roleService;


    @Test
    @DisplayName("Test that GET  /create   works correctly")
    public void testThatGetCreateWorksProper() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/users/create"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.view().name("create-user"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"));
    }

    @Test
    @DisplayName("Test that POST  /create  works correctly with valid user")
    public void testThatPostCreateWorksProperWithValidUser() throws Exception {
        User mockUser  = new User();
        mockUser.setEmail("valid@cv.edu.ua");
        mockUser.setFirstName("Firstname");
        mockUser.setLastName("Lastname");
        mockUser.setPassword("qwQW12");
        mockUser.setRole(roleService.readById(2));
        Role role = new Role();
        role.setName("Role");
        Mockito.when(roleService.readById(anyLong())).thenReturn(role);
        Mockito.when(userService.create(any(User.class))).thenReturn(mockUser);
        mockMvc.perform(MockMvcRequestBuilders.post("/users/create"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/todos/all/users/" + mockUser.getId()));
    }

    @Test
    @DisplayName("Test that POST  /create  works correctly with invalid user")
    public void testThatPostCreateWorksProperWithInvalidUser() throws Exception {
        User mockUser = new User();
        mockUser.setEmail("valid@cv.edu.ua");
        mockUser.setFirstName("FirstnameInvalid");
        mockUser.setLastName("Lastname");
        mockUser.setPassword("qwQW12");
        mockUser.setRole(roleService.readById(2));
        Role role = new Role();
        role.setName("Role");
        Mockito.when(roleService.readById(anyLong())).thenReturn(role);
        Mockito.when(userService.create(any(User.class))).thenReturn(mockUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/users/create"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("create-user"));
    }

    @Test
    @DisplayName("Test that GET  /all  works correctly")
    public void testThatGetAllWorksProperWithInvalidUser() throws Exception {
        List<User> expected = new ArrayList<>();
        expected.add(new User());
        expected.add(new User());
        Mockito.when(userService.getAll()).thenReturn(expected);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("users-list"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("users"));
    }

}