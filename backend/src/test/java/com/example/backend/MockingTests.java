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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class MockingTests {

    @MockBean
    private static UserRepository userRepo;

    @MockBean
    private JWTService jwtService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        when(jwtService.generateToken(any(UserDetails.class))).thenReturn("test");
        when(userRepo.findByUsername("test")).thenReturn(Optional.of(new UserData(0, "test", "$2a$10$I66PonpAZrJ0332SaUwVVO3geeOD19JOt.5PkD/Mbri7UjmnBgCTu", "test", Role.USER, "", 0, 0, 0, false)));
    }


    @Test
    void authenticateUserThatAlreadyExists_shouldReturnValidToken_whenUserWithNameEmailAndPassword_Test_Exists() throws Exception {
        AuthenticationRequest request = new AuthenticationRequest("test", "test", "test");

        String expectedResponse = objectMapper.writeValueAsString(Map.of("token", "test"));

        mockMvc.perform(post("/place/authenticate")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }
}
