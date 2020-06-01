package com.wedevol.springbootjunit4.core.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import java.io.IOException;
import java.util.Arrays;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;
import com.wedevol.springbootjunit5.core.EntryPoint;
import com.wedevol.springbootjunit5.core.dao.UserRepository;
import com.wedevol.springbootjunit5.core.entity.UserEntity;

/**
 * Test the User Controller: test the endpoints directly
 *
 * @author Charz++
 */

@SpringBootTest(classes = EntryPoint.class)
@AutoConfigureMockMvc
public class UserControllerTest {

    private static final Long USER_ONE_ID = 1L;
    private static final Long USER_TWO_ID = 2L;
    private static MediaType CONTENT_TYPE =
            new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype());

    private MockMvc mockMvc;
    @SuppressWarnings("rawtypes")
    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public void setConverters(HttpMessageConverter<?>[] converters) {
        mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().orElse(null);
        Assertions.assertNotNull(this.mappingJackson2HttpMessageConverter,
                "the JSON message converter must not be null");
    }

    @BeforeEach
    public void init() {
        mockMvc = webAppContextSetup(webApplicationContext).build();
        UserEntity user1 = new UserEntity("Carlos", 29, "carlos@yopmail.com");
        UserEntity user2 = new UserEntity("Luis", 25, "luis@yopmail.com");
        userRepository.create(user1);
        userRepository.create(user2);
    }

    @AfterEach
    public void tearDown() {
        userRepository.resetDb();
    }

    @Test
    public void getNonExistingUser() throws Exception {
        Assertions.assertThrows(NestedServletException.class, () -> {
            mockMvc.perform(get("/users/100")).andExpect(status().isNotFound())
                    .andExpect(content().contentType(CONTENT_TYPE));
        });
    }

    @Test
    public void getExistingUser() throws Exception {
        mockMvc.perform(get("/users/" + USER_ONE_ID)).andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE)).andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.name", Matchers.is("Carlos"))).andExpect(jsonPath("$.age", Matchers.is(29)))
                .andExpect(jsonPath("$.email", Matchers.is("carlos@yopmail.com")));
    }

    @Test
    public void createUser() throws Exception {
        UserEntity newUser = new UserEntity("Alfredo", 40, "alfredo@yopmail.com");
        mockMvc.perform(post("/users").contentType(CONTENT_TYPE).content(json(newUser)))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateUser() throws Exception {
        UserEntity user2 = new UserEntity("Louis");
        mockMvc.perform(put("/users/" + USER_TWO_ID).contentType(CONTENT_TYPE).content(json(user2)))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void existUser() throws Exception {
        mockMvc.perform(get("/users/" + USER_ONE_ID + "/exists")).andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE)).andExpect(jsonPath("$", Matchers.is(true)));
    }

    @Test
    public void getExistingUserByEmail() throws Exception {
        mockMvc.perform(get("/users/find/email").param("email", "luis@yopmail.com")).andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE)).andExpect(jsonPath("$.id", Matchers.is(2)))
                .andExpect(jsonPath("$.email", Matchers.is("luis@yopmail.com")));
    }

    @Test
    public void countAll() throws Exception {
        mockMvc.perform(get("/users/count/all")).andExpect(status().isOk())
                .andExpect(content().contentType(CONTENT_TYPE)).andExpect(jsonPath("$", Matchers.is(2)));
    }

    @SuppressWarnings("unchecked")
    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

}
