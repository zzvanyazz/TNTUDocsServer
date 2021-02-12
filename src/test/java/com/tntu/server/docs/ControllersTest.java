package com.tntu.server.docs;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tntu.server.docs.communication.models.dto.UserDto;
import com.tntu.server.docs.communication.models.responses.AuthResponseData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllersTest {

    private static String authHeader = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiZXhwIjoxNjE0MzIwMjA2LCJpc3MiOiJ0bnR1LWRvY3MiLCJpYXQiOjE2MTMxMTA2MDZ9.BkMoPwmrivGSJ7sAvkwHEaXNyllaKpukF73ZZAD6hQ_rtz4Lj2Wtcn2ZAJjN-EL--BC3ojgXyGArqg3Lpf7bmg";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void authorize() throws Exception {
        String content = mockMvc.perform(post("/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Contents.LOGIN_CONTENT))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();
        var response = objectMapper.readValue(content, AuthResponseData.class);
        var token = response.getAccessToken();
        var refreshToken = response.getRefreshToken();
        assert token != null : "Access token is null";
        assert refreshToken != null : "Refresh token is null";
    }

    @Test
    public void getRoles() throws Exception {
        assert authHeader != null : "Can not auth";
        mockMvc.perform(get("/v1/roles")
                .header("Authorization", authHeader))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getUsers() throws Exception {
        var content = mockMvc.perform(get("/v1/users")
                .header("Authorization", authHeader))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        var response = objectMapper.readValue(content, UserDto[].class);
        assert response != null : "Response null";
        assert response.length >= 1 : "Response empty";
    }


}
