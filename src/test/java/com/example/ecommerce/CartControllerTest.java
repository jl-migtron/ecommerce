package com.example.ecommerce;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.aMapWithSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CartService cartService;

    @BeforeEach
    // clean carts repo before each test
    public void prepare() throws Exception {
        cartService.deleteAll();
    }

    @Test
    public void testCreateCart() throws Exception {

        // Send POST request for a new cart
        ResultActions result = mockMvc.perform(post("/api/carts"));

        // Assert that cart 1 is created
        result.andExpect(status().isOk())
            .andExpect(jsonPath("$.id", not(0)));
    }

    @Test
    public void testGetCartById() throws Exception {

        // Create new cart & get its id
        final Integer CART_ID = cartService.createCart().getId();
        // Send GET request for the new cart
        ResultActions result = mockMvc.perform(get("/api/carts/{id}", CART_ID));

        // Assert that the new cart is returned
        result.andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", is(CART_ID)));
    }

    @Test
    public void testUpdateCart() throws Exception {

        // Create new cart & get its id
        final Integer CART_ID = cartService.createCart().getId();
        String addedProductsJson = "[{\"id\": 1011, \"desc\": \"martillo\", \"amount\": 1}, {\"id\": 1022, \"desc\": \"tornillos\", \"amount\": 100}]";

        // Send PUT request with 2 products for the new cart
        ResultActions result = mockMvc.perform(put("/api/carts/{id}", CART_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(addedProductsJson));

        // Assert that the new cart now has 2 products
        result.andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", is(CART_ID)))
            .andExpect(jsonPath("$.products", aMapWithSize(2)));
    }

    @Test
    public void testDeleteCart() throws Exception {

        // Create new cart & get its id
        final Integer CART_ID = cartService.createCart().getId();
        // Send DELETE request for the new cart
        ResultActions result = mockMvc.perform(delete("/api/carts/{id}", CART_ID));

        // Assert that no content status is returned
        result.andExpect(status().isNoContent());
    }

    @Test
    public void testGetWrongCart() throws Exception {

        final Integer CART_ID = 99;
        // Send GET request for a non existing cart
        ResultActions result = mockMvc.perform(get("/api/carts/{id}", CART_ID));

        // Assert that not found returned
        result.andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateWrongCart() throws Exception {

        final Integer CART_ID = 99;
        String addedProductsJson = "[{\"id\": 1011, \"desc\": \"martillo\", \"amount\": 1}, {\"id\": 1022, \"desc\": \"tornillos\", \"amount\": 100}]";

        // Send PUT request with 2 products for a non existing cart
        ResultActions result = mockMvc.perform(put("/api/carts/{id}", CART_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(addedProductsJson));

        // Assert that not found returned
        result.andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteWrongCart() throws Exception {

        final Integer CART_ID = 99;
        // Send DELETE request for a non existing cart
        ResultActions result = mockMvc.perform(delete("/api/carts/{id}", CART_ID));

        // Assert that not found returned
        result.andExpect(status().isNotFound());
    }
}
