package com.example.backend;

import com.example.backend.jwt.auth.AuthenticationRequest;
import com.example.backend.jwt.config.JWTService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class AuthTests {

    private static final String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNzE2MjkxNDEzLCJleHAiOjE3MTY5MTY1ODZ9.XACEM9zVK5pgd53wbPaWe1n_uBt7u3m0zJT7uPW73MM";
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BackendService backendService;

    @Autowired
    private ObjectMapper objectMapper;

    private JWTService jwtService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testMailCheck() throws Exception {

        AuthenticationRequest request = new AuthenticationRequest("", "test", "");

        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/place/mailcheck")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    public void testCheckTokenExpired() throws Exception {
        mockMvc.perform(get("/place/checktokenexpired")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("false"));
    }

    @Test
    public void testTestPw() throws Exception {
        mockMvc.perform(post("/user/testpw")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .content("test"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));
    }

    @Test
    public void testGetCooldownForUser() throws Exception {
        mockMvc.perform(get("/user/cooldown")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("0"));
    }

}
