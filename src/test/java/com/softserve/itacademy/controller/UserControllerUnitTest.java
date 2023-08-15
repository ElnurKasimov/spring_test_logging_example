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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


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
        verifyNoInteractions(userService);
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
        when(roleService.readById(anyLong())).thenReturn(role);
        when(userService.create(any(User.class))).thenReturn(mockUser);
        mockMvc.perform(MockMvcRequestBuilders.post("/users/create")
                        .param("firstName","Firstname" )
                        .param("lastName", "Lastname")
                        .param("email", "valid@cv.edu.ua")
                        .param("password", "qwQW12")
                )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/todos/all/users/" + mockUser.getId()));
        verify(userService, times(1)).create(mockUser);
    }

    @Test
    @DisplayName("Test that POST  /create  works correctly with invalid user")
    public void testThatPostCreateWorksProperWithInvalidUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/users/create")
                        .param("firstName","InvalidFirstname" )
                        .param("lastName", "Lastname")
                        .param("email", "valid@cv.edu.ua")
                        .param("password", "qwQW12") )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("create-user"));
        verifyNoInteractions(userService);
    }

    @Test
    @DisplayName("Test that GET  /all  works correctly")
    public void testThatGetAllWorksProperWithInvalidUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("users-list"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("users"));
        verify(userService, times(1)).getAll();
    }

    @Test
    @DisplayName("Test that GET  /{id}/read   works correctly")
    public void testThatGetReadWorksProper() throws Exception {
        User mockUser  = new User();
        mockUser.setEmail("valid@cv.edu.ua");
        mockUser.setFirstName("Firstname");
        mockUser.setLastName("Lastname");
        mockUser.setPassword("qwQW12");
        Role mockRole = new Role();
        mockRole.setName("Role");
        when(roleService.create(mockRole)).thenReturn(mockRole);
        roleService.create(mockRole);
        when(roleService.readById(anyLong())).thenReturn(mockRole);
        mockUser.setRole(roleService.readById(1));
        when(userService.readById(anyLong())).thenReturn(mockUser);
        mockMvc.perform(MockMvcRequestBuilders.get("/users/1/read"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.view().name("user-info"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"));
        verify(userService, times(1)).readById(1);
    }

    @Test
    @DisplayName("Test that GET  /{id}/delete   works correctly")
    public void testThatGetDeleteWorksProper() throws Exception {
        User mockUser  = new User();
        mockUser.setEmail("valid@cv.edu.ua");
        mockUser.setFirstName("Firstname");
        mockUser.setLastName("Lastname");
        mockUser.setPassword("qwQW12");
        Role mockRole = new Role();
        mockRole.setName("Role");
        when(roleService.create(mockRole)).thenReturn(mockRole);
        roleService.create(mockRole);
        when(roleService.readById(anyLong())).thenReturn(mockRole);
        mockUser.setRole(roleService.readById(1));
        when(userService.readById(anyLong())).thenReturn(mockUser);
        mockMvc.perform(MockMvcRequestBuilders.get("/users/1/delete"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/users/all"));
        verify(userService, times(1)).delete(1);
    }

    @Test
    @DisplayName("Test that POST /{id}/update   works correctly")
    public void testThatGetUpdateWorksProper() throws Exception {
        User mockUser  = new User();
        mockUser.setEmail("valid@cv.edu.ua");
        mockUser.setFirstName("Firstname");
        mockUser.setLastName("Lastname");
        mockUser.setPassword("qwQW12");
        Role mockRole = new Role();
        mockRole.setName("Role");
        when(roleService.create(mockRole)).thenReturn(mockRole);
        roleService.create(mockRole);
        when(roleService.readById(anyLong())).thenReturn(mockRole);
        mockUser.setRole(roleService.readById(1));
        when(userService.readById(anyLong())).thenReturn(mockUser);
        mockMvc.perform(MockMvcRequestBuilders.get("/users/1/update"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("roles"))
                .andExpect(MockMvcResultMatchers.view().name("update-user"));
        verify(userService, times(1)).readById(1);
    }

}