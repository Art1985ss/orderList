package com.art1985.orderList.web.controller.order;

import com.art1985.orderList.OrderListApplication;
import com.art1985.orderList.entities.EntityCreator;
import com.art1985.orderList.entities.Order;
import com.art1985.orderList.entities.Product;
import com.art1985.orderList.entities.User;
import com.art1985.orderList.service.order.OrderService;
import com.art1985.orderList.web.dto.DtoConverter;
import com.art1985.orderList.web.dto.DtoOrder;
import com.art1985.orderList.web.dto.DtoProduct;
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
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = OrderListApplication.class
)
@AutoConfigureMockMvc
class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @LocalServerPort
    private int port;
    @MockBean
    private OrderService orderService;
    private Order order;
    private String url;


    @BeforeEach
    void setUp() {
        order = EntityCreator.createOrder();
        url = "http://localhost:" + port + "/api/v1/orders";
    }

    //TODO fix exception org.springframework.web.HttpMediaTypeNotSupportedException: Content type 'application/json;charset=UTF-8' not supported
    //don't understand why this error appears
    @Test
    @WithMockUser(roles = "ADMIN")
    void create() throws Exception {
        when(orderService.create(order)).thenReturn(order);
        String json = mapper.writeValueAsString(DtoConverter.toDto(order));
        System.out.println(json);
        mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andDo(print()).andExpect(status().isCreated()).andExpect(redirectedUrl(url + "/" + order.getId()));
        verify(orderService, times(1)).create(order);
    }

    @Test
    @WithMockUser
    void createFail() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(order))
        ).andDo(print()).andExpect(status().isForbidden());
        verify(orderService, never()).create(order);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAll() throws Exception {
        List<Order> orderList = new ArrayList<>(Collections.singletonList(order));
        List<DtoOrder> dtoOrderList = new ArrayList<>(Collections.singletonList(DtoConverter.toDto(order)));
        when(orderService.findAll()).thenReturn(orderList);
        String json = mapper.writeValueAsString(dtoOrderList);
        mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isOk()).andExpect(content().string(json));
        verify(orderService, times(1)).findAll();
    }

    @Test
    @WithMockUser
    void getAllFail() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isForbidden());
        verify(orderService, never()).findAll();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void findById() throws Exception {
        when(orderService.findById(order.getId())).thenReturn(order);
        mockMvc.perform(
                MockMvcRequestBuilders.get(url + "/" + order.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isOk()).andExpect(content().json(mapper.writeValueAsString(DtoConverter.toDto(order))));
        verify(orderService, times(1)).findById(order.getId());
    }

    @Test
    @WithMockUser
    void findByIdFail() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(url + "/" + order.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isForbidden());
        verify(orderService, never()).findById(order.getId());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void findByName() throws Exception {
        when(orderService.findByName(order.getName())).thenReturn(order);
        mockMvc.perform(
                MockMvcRequestBuilders.get(url + "/?name=" + order.getName())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isOk()).andExpect(content().json(mapper.writeValueAsString(DtoConverter.toDto(order))));
        verify(orderService, times(1)).findByName(order.getName());
    }

    @Test
    @WithMockUser
    void findByNameFail() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(url + "/?name=" + order.getName())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isForbidden());
        verify(orderService, never()).findByName(order.getName());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteById() throws Exception {
        when(orderService.deleteById(order.getId())).thenReturn(order);
        mockMvc.perform(
                MockMvcRequestBuilders.delete(url + "/" + order.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isNoContent());
        verify(orderService, times(1)).deleteById(order.getId());
    }

    @Test
    @WithMockUser
    void deleteByIdFail() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete(url + "/" + order.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isForbidden());
        verify(orderService, never()).deleteById(order.getId());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteByName() throws Exception {
        when(orderService.deleteByName(order.getName())).thenReturn(order);
        mockMvc.perform(
                MockMvcRequestBuilders.delete(url + "/?name=" + order.getName())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isNoContent());
        verify(orderService, times(1)).deleteByName(order.getName());
    }

    @Test
    @WithMockUser
    void deleteByNameFail() throws Exception {
        when(orderService.deleteByName(order.getName())).thenReturn(order);
        mockMvc.perform(
                MockMvcRequestBuilders.delete(url + "/?name=" + order.getName())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isForbidden());
        verify(orderService, never()).deleteByName(order.getName());
    }

    //TODO fix exception org.springframework.web.HttpMediaTypeNotSupportedException: Content type 'application/json;charset=UTF-8' not supported
    //don't understand why this error appears
    @Test
    @WithMockUser(roles = "ADMIN")
    void update() throws Exception {
        doNothing().when(orderService).update(order);
        mockMvc.perform(
                MockMvcRequestBuilders.put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(order))
        ).andDo(print()).andExpect(status().isAccepted());
        verify(orderService, times(1)).update(order);
    }

    @Test
    @WithMockUser
    void updateFail() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(order))
        ).andDo(print()).andExpect(status().isForbidden());
        verify(orderService, never()).update(order);
    }
}