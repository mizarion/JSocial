package com.mizarion.jsocial.controller.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mizarion.jsocial.model.dto.JwtDto;
import com.mizarion.jsocial.model.dto.LoginDto;
import com.mizarion.jsocial.model.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AuthRestControllerTest {

//    @MockBean
//    private UserService service;

    @Autowired
    private MockMvc mockMvc;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final static UserDto registerDto = new UserDto("testauth@mail.com", "registerTest", "registerTest");

    /**
     * Create new user
     */
    @Test
    void registerTest() throws Exception {
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDto))
                )
                .andExpect(status().is2xxSuccessful());
    }

    /**
     * Login with invalid credentials (user not exist)
     */
    @Test
    void invalidLoginTest() throws Exception {
        LoginDto loginDto = new LoginDto("invalidLoginTest", "invalidLoginTest");
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto))
                )
                .andExpect(status().is4xxClientError());
    }

    @Test
    void notAuthTest() throws Exception {
        mockMvc.perform(get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    void registerAndLoginTest() throws Exception {
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDto))
                )
                .andExpect(status().is2xxSuccessful());

        LoginDto loginDto = new LoginDto(registerDto.getUsername(), registerDto.getPassword());
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto))
                )
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void AuthTest() throws Exception {
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDto))
                )
                .andExpect(status().is2xxSuccessful());

        LoginDto loginDto = new LoginDto(registerDto.getUsername(), registerDto.getPassword());
        MvcResult loginResult = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto))
                )
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        JwtDto jwtDto = objectMapper.readValue(loginResult.getResponse().getContentAsString(), JwtDto.class);

        mockMvc.perform(get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtDto.getJwt())
                )
                .andExpect(status().is2xxSuccessful());
    }
}