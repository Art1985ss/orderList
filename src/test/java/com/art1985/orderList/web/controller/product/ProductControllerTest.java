package com.art1985.orderList.web.controller.product;

import com.art1985.orderList.OrderListApplication;
import com.art1985.orderList.entities.EntityCreator;
import com.art1985.orderList.entities.Product;
import com.art1985.orderList.service.product.ProductService;
import com.art1985.orderList.web.dto.DtoConverter;
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
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @LocalServerPort
    private int port;
    @MockBean
    private ProductService productService;
    private Product product;
    private String url;


    @BeforeEach
    void setUp() {
        product = EntityCreator.createProduct();
        url = "http://localhost:" + port + "/api/v1/products";
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void create() throws Exception {
        when(productService.create(product)).thenReturn(product);
        mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(product))
        ).andDo(print()).andExpect(status().isCreated()).andExpect(redirectedUrl(url + "/" + product.getId()));
        verify(productService, times(1)).create(product);
    }

    @Test
    @WithMockUser
    void createFail() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(product))
        ).andDo(print()).andExpect(status().isForbidden());
        verify(productService, never()).create(product);
    }

    @Test
    void getAll() throws Exception {
        List<Product> productList = new ArrayList<>(Collections.singletonList(product));
        List<DtoProduct> dtoProductList = new ArrayList<>(Collections.singletonList(DtoConverter.toDto(product)));
        when(productService.findAll()).thenReturn(productList);
        String json = mapper.writeValueAsString(dtoProductList);
        mockMvc.perform(
                MockMvcRequestBuilders.get(url)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isOk()).andExpect(content().string(json));
        verify(productService, times(1)).findAll();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void findById() throws Exception {
        when(productService.findById(product.getId())).thenReturn(product);
        mockMvc.perform(
                MockMvcRequestBuilders.get(url + "/" + product.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isOk()).andExpect(content().json(mapper.writeValueAsString(DtoConverter.toDto(product))));
        verify(productService, times(1)).findById(product.getId());
    }

    @Test
    @WithMockUser
    void findByIdFail() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(url + "/" + product.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isForbidden());
        verify(productService, never()).findById(product.getId());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void findByName() throws Exception {
        when(productService.findByName(product.getName())).thenReturn(product);
        mockMvc.perform(
                MockMvcRequestBuilders.get(url + "/?name=" + product.getName())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isOk()).andExpect(content().json(mapper.writeValueAsString(DtoConverter.toDto(product))));
        verify(productService, times(1)).findByName(product.getName());
    }

    @Test
    @WithMockUser
    void findByNameFail() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(url + "/?name=" + product.getName())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isForbidden());
        verify(productService, never()).findByName(product.getName());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteById() throws Exception {
        when(productService.deleteById(product.getId())).thenReturn(product);
        mockMvc.perform(
                MockMvcRequestBuilders.delete(url + "/" + product.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isNoContent());
        verify(productService, times(1)).deleteById(product.getId());
    }

    @Test
    @WithMockUser
    void deleteByIdFail() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete(url + "/" + product.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isForbidden());
        verify(productService, never()).deleteById(product.getId());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteByName() throws Exception {
        when(productService.deleteByName(product.getName())).thenReturn(product);
        mockMvc.perform(
                MockMvcRequestBuilders.delete(url + "/?name=" + product.getName())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isNoContent());
        verify(productService, times(1)).deleteByName(product.getName());
    }

    @Test
    @WithMockUser
    void deleteByNameFail() throws Exception {
        when(productService.deleteByName(product.getName())).thenReturn(product);
        mockMvc.perform(
                MockMvcRequestBuilders.delete(url + "/?name=" + product.getName())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isForbidden());
        verify(productService, never()).deleteByName(product.getName());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void update() throws Exception {
        doNothing().when(productService).update(product);
        mockMvc.perform(
                MockMvcRequestBuilders.put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(product))
        ).andDo(print()).andExpect(status().isAccepted());
        verify(productService, times(1)).update(product);
    }

    @Test
    @WithMockUser
    void updateFail() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(product))
        ).andDo(print()).andExpect(status().isForbidden());
        verify(productService, never()).update(product);
    }
}