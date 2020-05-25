package com.art1985.orderList.web.controller.user;

import com.art1985.orderList.OrderListApplication;
import com.art1985.orderList.entities.EntityCreator;
import com.art1985.orderList.entities.User;
import com.art1985.orderList.service.user.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = OrderListApplication.class
)
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @LocalServerPort
    private int port;
    @MockBean
    private UserService userService;
    private User user;


    @BeforeEach
    void setUp() {
        user = EntityCreator.createUser();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void create() throws Exception {
        when(userService.create(user)).thenReturn(user);
        mockMvc.perform(
                MockMvcRequestBuilders.post("http://localhost:" + port + "/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(user))
        ).andDo(print()).andExpect(status().isCreated()).andExpect(redirectedUrl("http://localhost:" + port + "/api/v1/users/" + user.getId()));
        verify(userService, times(1)).create(user);
    }

    @Test
    @WithMockUser
    void createFail() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("http://localhost:" + port + "/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(user))
        ).andDo(print()).andExpect(status().isForbidden());
        verify(userService, never()).create(user);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAll() throws Exception {
        List<User> userList = new ArrayList<>(Arrays.asList(user));
        when(userService.findAll()).thenReturn(userList);
        String json = mapper.writeValueAsString(userList);
        mockMvc.perform(
                MockMvcRequestBuilders.get("http://localhost:" + port + "/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isOk()).andExpect(content().string(json));
        verify(userService, times(1)).findAll();
    }

    @Test
    @WithMockUser
    void getAllFail() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("http://localhost:" + port + "/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isForbidden());
        verify(userService, never()).findAll();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void findById() throws Exception {
        when(userService.findById(user.getId())).thenReturn(user);
        mockMvc.perform(
                MockMvcRequestBuilders.get("http://localhost:" + port + "/api/v1/users/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isOk()).andExpect(content().json(mapper.writeValueAsString(user)));
        verify(userService, times(1)).findById(user.getId());
    }

    @Test
    @WithMockUser
    void findByIdFail() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("http://localhost:" + port + "/api/v1/users/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isForbidden());
        verify(userService, never()).findById(user.getId());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void findByName() throws Exception {
        when(userService.findByName(user.getLastName())).thenReturn(user);
        mockMvc.perform(
                MockMvcRequestBuilders.get("http://localhost:" + port + "/api/v1/users/?name=" + user.getLastName())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isOk()).andExpect(content().json(mapper.writeValueAsString(user)));
        verify(userService, times(1)).findByName(user.getLastName());
    }

    @Test
    @WithMockUser
    void findByNameFail() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("http://localhost:" + port + "/api/v1/users/?name=" + user.getLastName())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isForbidden());
        verify(userService, never()).findByName(user.getLastName());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteById() throws Exception {
        when(userService.deleteById(user.getId())).thenReturn(user);
        mockMvc.perform(
                MockMvcRequestBuilders.delete("http://localhost:" + port + "/api/v1/users/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isNoContent());
        verify(userService, times(1)).deleteById(user.getId());
    }

    @Test
    @WithMockUser
    void deleteByIdFail() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("http://localhost:" + port + "/api/v1/users/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isForbidden());
        verify(userService, never()).deleteById(user.getId());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteByName() throws Exception {
        when(userService.deleteByName(user.getLastName())).thenReturn(user);
        mockMvc.perform(
                MockMvcRequestBuilders.delete("http://localhost:" + port + "/api/v1/users/?name=" + user.getLastName())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isNoContent());
        verify(userService, times(1)).deleteByName(user.getLastName());
    }

    @Test
    @WithMockUser
    void deleteByNameFail() throws Exception {
        when(userService.deleteByName(user.getLastName())).thenReturn(user);
        mockMvc.perform(
                MockMvcRequestBuilders.delete("http://localhost:" + port + "/api/v1/users/?name=" + user.getLastName())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isForbidden());
        verify(userService, never()).deleteByName(user.getLastName());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void update() throws Exception {
        doNothing().when(userService).update(user);
        mockMvc.perform(
                MockMvcRequestBuilders.put("http://localhost:" + port + "/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(user))
        ).andDo(print()).andExpect(status().isAccepted());
        verify(userService, times(1)).update(user);
    }

    @Test
    @WithMockUser
    void updateFail() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.put("http://localhost:" + port + "/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(user))
        ).andDo(print()).andExpect(status().isForbidden());
        verify(userService, never()).update(user);
    }
}