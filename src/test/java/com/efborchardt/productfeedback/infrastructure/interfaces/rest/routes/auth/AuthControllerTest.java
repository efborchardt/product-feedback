package com.efborchardt.productfeedback.infrastructure.interfaces.rest.routes.auth;

import com.efborchardt.productfeedback.domain.user.repository.UserRepository;
import com.efborchardt.productfeedback.infrastructure.interfaces.rest.security.SecurityConfigs;
import com.efborchardt.productfeedback.infrastructure.interfaces.rest.security.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Import(SecurityConfigs.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private UserRepository userRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void whenLoginWithValidCredentials_thenReturns200AndToken() throws Exception {
        AuthRequestDTO requestDTO = new AuthRequestDTO("user", "password");
        String token = "some.jwt.token";

        given(authenticationManager.authenticate(any())).willReturn(mock(Authentication.class));
        given(tokenService.generateToken(any())).willReturn(token);

        mockMvc.perform(MockMvcRequestBuilders.post("/public/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(token));
    }

    @Test
    void whenLoginWithInvalidCredentials_thenReturns401() throws Exception {
        AuthRequestDTO requestDTO = new AuthRequestDTO("user", "wrongPassword");

        given(authenticationManager.authenticate(any())).willThrow(new BadCredentialsException("Bad credentials"));

        mockMvc.perform(MockMvcRequestBuilders.post("/public/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isUnauthorized());
    }
}