package com.hack_SOAT_9.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hack_SOAT_9.auth.controller.TestSecurityConfig;
import com.hack_SOAT_9.auth.dto.request.LoginRequestDTO;
import com.hack_SOAT_9.auth.dto.request.RegisterRequestDTO;
import com.hack_SOAT_9.auth.entity.RegisterEntity;
import com.hack_SOAT_9.auth.infra.security.TokenService;
import com.hack_SOAT_9.auth.repository.RegisterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@ContextConfiguration(classes = {
        AuthController.class,
        AuthControllerTest.MockConfig.class,
        TestSecurityConfig.class
})
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RegisterRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public RegisterRepository repository() {
            return mock(RegisterRepository.class);
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return mock(PasswordEncoder.class);
        }

        @Bean
        public TokenService tokenService() {
            return mock(TokenService.class);
        }
    }

    @BeforeEach
    void resetMocks() {
        reset(repository, passwordEncoder, tokenService);
    }

    @Test
    void shouldLoginSuccessfully() throws Exception {
        RegisterEntity user = RegisterEntity.builder()
                .email("user@test.com")
                .password("encoded123")
                .username("UserTest123")
                .build();

        when(repository.findByEmail("user@test.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("raw123", "encoded123")).thenReturn(true);
        when(tokenService.generateToken(user)).thenReturn("fake-jwt-token");

        LoginRequestDTO request = new LoginRequestDTO("user@test.com", "raw123");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("fake-jwt-token"))
                .andExpect(jsonPath("$.userName").value("UserTest123"));
    }

    @Test
    void shouldFailLoginWithWrongPassword() throws Exception {
        RegisterEntity user = RegisterEntity.builder()
                .email("user@test.com")
                .password("encoded123")
                .build();

        when(repository.findByEmail("user@test.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongPass", "encoded123")).thenReturn(false);

        LoginRequestDTO request = new LoginRequestDTO("user@test.com", "wrongPass");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldRegisterSuccessfully() throws Exception {
        RegisterRequestDTO request = new RegisterRequestDTO("Larissa", "Mayumi", "larissa@test.com", "abc123");

        when(repository.findByEmail("larissa@test.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("abc123")).thenReturn("encoded123");
        when(tokenService.generateToken(any())).thenReturn("jwt-created");

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwt-created"))
                .andExpect(jsonPath("$.userName").isNotEmpty());
    }

    @Test
    void shouldFailRegisterIfEmailExists() throws Exception {
        RegisterRequestDTO request = new RegisterRequestDTO("Jonathan", "Ferreira", "jonathan@test.com", "flor");

        when(repository.findByEmail("jonathan@test.com")).thenReturn(Optional.of(new RegisterEntity()));

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
