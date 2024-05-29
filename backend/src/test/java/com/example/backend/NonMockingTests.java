package com.example.backend;

import com.example.backend.jwt.auth.AuthenticationRequest;
import com.example.backend.jwt.config.JWTService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class NonMockingTests {

    private static final String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNzE2OTY3MjczLCJleHAiOjE3MTY5NjcyNzM5MX0.CH-RoqMpHZyjYDW-9IueHom5qs3h6PwnrFJLdOBMv9o";

    @MockBean
    private static UserRepository userRepo;

    @Mock
    private JWTService jwtService;

    @MockBean
    private CooldownRepository cooldownRepo;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        when(jwtService.generateToken(any(UserDetails.class))).thenReturn("test");
        when(userRepo.findByEmail(any(String.class))).thenReturn(Optional.of(new UserData(0, "test", "test", "test", Role.USER, "", 0, 0, 0, false)));
        when(userRepo.findByUsername(any(String.class))).thenReturn(Optional.of(new UserData(0, "test", "test", "test", Role.USER, "", 0, 0, 0, false)));
        when(cooldownRepo.findCooldownByUserId(any(long.class))).thenReturn(Optional.of(new Cooldown(0, 0, 0)));
        when(jwtService.extractUsername(any(String.class))).thenReturn("test");
    }

    @Test
    public void testMailCheck() throws Exception {
        AuthenticationRequest request = new AuthenticationRequest("", "test", "");
        String jsonRequest = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/place/mailcheck")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    @SuppressWarnings("SpellCheckingInspection")
    public void testCheckTokenExpired() throws Exception {
        mockMvc.perform(get("/place/checktokenexpired")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("false"));
    }

    @Test
    public void testGetCooldownForUser() throws Exception {
        mockMvc.perform(get("/user/cooldown")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("0"));
    }

}
